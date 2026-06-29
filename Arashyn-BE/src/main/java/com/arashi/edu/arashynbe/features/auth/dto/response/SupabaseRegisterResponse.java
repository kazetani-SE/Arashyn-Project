package com.arashi.edu.arashynbe.features.auth.dto.response;

import java.util.UUID;

public record SupabaseRegisterResponse(

        SupabaseUser user

) {
  public record SupabaseUser(

          UUID id,

          String email

  ) {
  }

}