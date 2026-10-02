package com.arashi.edu.arashynbe.features.system.folder.dto.response;

import java.util.Set;
import java.util.UUID;

public record FolderCheckUpdateResponse(

        boolean hasUpdate,

        Set<UUID> addedChildFolderIds,

        Set<UUID> addedChildDeckIds,

        Set<UUID> removedChildFolderIds,

        Set<UUID> removedChildDeckIds

) {}