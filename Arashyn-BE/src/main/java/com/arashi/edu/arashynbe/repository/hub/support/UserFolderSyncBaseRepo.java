package com.arashi.edu.arashynbe.repository.hub.support;

import com.arashi.edu.arashynbe.entity.hub.support.UserFolderSyncBase;
import com.arashi.edu.arashynbe.entity.hub.support.UserFolderSyncBaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserFolderSyncBaseRepo extends JpaRepository<UserFolderSyncBase, UserFolderSyncBaseId> {
  @Query("select s from UserFolderSyncBase s where s.id.userFolderId = :userFolderId")
  List<UserFolderSyncBase> findByUserFolderId(UUID userFolderId);

  @Modifying
  @Query("delete from UserFolderSyncBase s where s.id.userFolderId = :userFolderId")
  void deleteByUserFolderId(UUID userFolderId);
}