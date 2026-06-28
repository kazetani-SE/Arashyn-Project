package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.shared.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepo extends JpaRepository<Account, UUID> {
  @Query("""
    select a.role
    from Account a
    where a.id = :id
""")
  Optional<Role> findRoleById(UUID id);
}