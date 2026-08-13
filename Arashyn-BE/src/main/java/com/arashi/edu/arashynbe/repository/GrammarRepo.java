package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Grammar;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GrammarRepo extends
        JpaRepository<Grammar, UUID>,
        JpaSpecificationExecutor<Grammar> {

  @Modifying
  @Transactional
  @Query(value = """
        INSERT INTO grammar_filter(grammar_id, filter_id)
        VALUES (:grammarId, :filterId)
        ON CONFLICT DO NOTHING
        """, nativeQuery = true)
  void assignFilter(
          UUID grammarId,
          UUID filterId
  );

  @Query("""
    SELECT g.owner.id
    FROM Grammar g
    WHERE g.id = :grammarId
    """)
  Optional<UUID> getOwnerId(UUID grammarId);

  @Modifying
  @Transactional
  @Query("""
    UPDATE Grammar g
    SET g.owner = null,
        g.isPublic = false
    WHERE g.id = :grammarId
    """)
  int softDelete(UUID grammarId);

  @Modifying
  @Transactional
  @Query("""
    UPDATE Grammar g
    SET g.owner.id = :userId,
        g.isPublic = true
    WHERE g.id = :grammarId
      AND g.owner IS NULL
    """)
  int restoreGrammar(
          UUID grammarId,
          UUID userId
  );

  @Query(
          value = """
                    SELECT g.*
                    FROM grammar g
                    LEFT JOIN user_grammar ug
                        ON ug.grammar_id = g.id
                    WHERE g.owner_id IS NOT NULL
                    GROUP BY g.id
                    ORDER BY COUNT(ug.id) DESC,
                             g.updated_at DESC
                    """,
          countQuery = """
                    SELECT COUNT(*)
                    FROM grammar g
                    WHERE g.owner_id IS NOT NULL
                    """,
          nativeQuery = true
  )
  Page<Grammar> findMostPopularGrammars(Pageable pageable);

  @Query(
          value = """
                    SELECT g.*
                    FROM grammar g
                    WHERE g.owner_id IS NOT NULL
                      AND LOWER(g.title) LIKE LOWER(CONCAT('%', :query, '%'))
                    ORDER BY g.updated_at DESC
                    """,
          countQuery = """
                    SELECT COUNT(*)
                    FROM grammar g
                    WHERE g.owner_id IS NOT NULL
                      AND LOWER(g.title) LIKE LOWER(CONCAT('%', :query, '%'))
                    """,
          nativeQuery = true
  )
  Page<Grammar> searchByTitle(
          @Param("query") String query,
          Pageable pageable
  );

  @Query(
          value = """
                    SELECT g.*
                    FROM grammar g
                    JOIN grammar_filter gf
                        ON gf.grammar_id = g.id
                    WHERE g.owner_id IS NOT NULL
                      AND LOWER(g.title) LIKE LOWER(CONCAT('%', :query, '%'))
                      AND gf.filter_id IN (:filterIds)
                    GROUP BY g.id
                    HAVING COUNT(DISTINCT gf.filter_id) = :filterCount
                    ORDER BY g.updated_at DESC
                    """,
          countQuery = """
                    SELECT COUNT(*)
                    FROM (
                        SELECT g.id
                        FROM grammar g
                        JOIN grammar_filter gf
                            ON gf.grammar_id = g.id
                        WHERE g.owner_id IS NOT NULL
                          AND LOWER(g.title) LIKE LOWER(CONCAT('%', :query, '%'))
                          AND gf.filter_id IN (:filterIds)
                        GROUP BY g.id
                        HAVING COUNT(DISTINCT gf.filter_id) = :filterCount
                    ) result
                    """,
          nativeQuery = true
  )
  Page<Grammar> searchByTitleAndFilters(
          @Param("query") String query,
          @Param("filterIds") List<UUID> filterIds,
          @Param("filterCount") long filterCount,
          Pageable pageable
  );
}