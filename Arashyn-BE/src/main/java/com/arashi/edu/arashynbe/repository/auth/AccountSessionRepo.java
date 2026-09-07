package com.arashi.edu.arashynbe.repository.auth;

import com.arashi.edu.arashynbe.entity.auth.AccountSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountSessionRepo extends JpaRepository<AccountSession, UUID> {

  Optional<AccountSession> findByRefreshToken(String refreshToken);


}