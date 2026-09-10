package com.arashi.edu.arashynbe.repository.hub;

import com.arashi.edu.arashynbe.entity.hub.UserFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserFolderRepo extends JpaRepository<UserFolder, UUID> {

  Optional<UserFolder> findByIdAndUserId(UUID id, UUID userId);

}