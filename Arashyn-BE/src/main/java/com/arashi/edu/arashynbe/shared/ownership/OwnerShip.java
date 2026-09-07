package com.arashi.edu.arashynbe.shared.ownership;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public class OwnerShip {

  public <T extends OwnedEntity> T requireOwnership(
          UUID id,
          JpaRepository<T, UUID> repository,
          ErrorCode notFoundError
  ) {

    T entity = repository.findById(id)
            .orElseThrow(() ->
                    new ApiException(notFoundError)
            );

    if (!entity.getOwner().getId().equals(CurrentUser.getId())) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }

    return entity;
  }

}