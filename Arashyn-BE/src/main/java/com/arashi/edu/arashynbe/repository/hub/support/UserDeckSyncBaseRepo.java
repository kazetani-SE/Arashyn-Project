package com.arashi.edu.arashynbe.repository.hub.support;

import com.arashi.edu.arashynbe.entity.hub.support.UserDeckSyncBase;
import com.arashi.edu.arashynbe.entity.hub.support.UserDeckSyncBaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserDeckSyncBaseRepo extends JpaRepository<UserDeckSyncBase, UserDeckSyncBaseId> {
  @Query("select s.id.grammarId from UserDeckSyncBase s where s.id.userDeckId = :userDeckId")
  List<UUID> findGrammarIdsByUserDeckId(UUID userDeckId);

  @Modifying
  @Query("delete from UserDeckSyncBase s where s.id.userDeckId = :userDeckId")
  void deleteByUserDeckId(UUID userDeckId);
}