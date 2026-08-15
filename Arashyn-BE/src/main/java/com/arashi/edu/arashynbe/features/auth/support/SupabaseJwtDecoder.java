package com.arashi.edu.arashynbe.features.auth.support;

import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class SupabaseJwtDecoder {

  private final JWKSource<SecurityContext> supabaseJwkSource;

  /**
   * Verify the signature (ES256 or HS256 depending on the token) via JWKS.
   * Allows expired tokens (exp) because the objective is to extract accountId for token refresh.
   */
  public JWTClaimsSet decodeAllowExpired(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);

      var processor = new DefaultJWTProcessor<SecurityContext>();

      // Accept both ES256 (new key) and HS256 (legacy key being rotated out)
      var keySelector = new JWSVerificationKeySelector<SecurityContext>(
              java.util.Set.of(JWSAlgorithm.ES256, JWSAlgorithm.HS256),
              supabaseJwkSource
      );
      processor.setJWSKeySelector(keySelector);

      // Bypass exp check (allow expired tokens); Nimbus checks exp by default
      // -> custom claims verifier that skips expiry check
      processor.setJWTClaimsSetVerifier((claims, context) -> {
        // Do not throw even if exp has passed -> bypass expiry check
      });

      return processor.process(signedJWT, null);

    } catch (ParseException | com.nimbusds.jose.JOSEException ex) {
      throw new ApiException(ErrorCode.INVALID_ACCESS_TOKEN);
    } catch (com.nimbusds.jose.proc.BadJOSEException ex) {
      throw new ApiException(ErrorCode.INVALID_ACCESS_TOKEN);
    }
  }
}