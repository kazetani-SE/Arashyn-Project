package com.arashi.edu.arashynbe.repository.system;

import com.arashi.edu.arashynbe.entity.system.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FolderRepo extends JpaRepository<Folder, UUID> {
}