package com.arashi.edu.arashynbe.features.auth.client;

import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.SupabaseRegisterResponse;

public interface SupabaseAuthClient {

  SupabaseRegisterResponse register(RegisterRequest request);

}