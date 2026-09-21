package com.arashi.edu.arashynbe.features.learning.util;

import com.arashi.edu.arashynbe.config.properties.LearningCheckerProperties;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AnswerCryptoUtil {

  private static final String AES_ALGO = "AES/GCM/NoPadding";
  private static final int IV_LENGTH_BYTES = 12;
  private static final int GCM_TAG_LENGTH_BITS = 128;

  private final LearningCheckerProperties learningCheckerProperties;

  public String[] encrypt(String plainAnswer) {
    try {
      byte[] iv = new byte[IV_LENGTH_BYTES];
      new SecureRandom().nextBytes(iv);

      Cipher cipher = Cipher.getInstance(AES_ALGO);
      cipher.init(Cipher.ENCRYPT_MODE, buildKey(), new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));

      byte[] cipherText = cipher.doFinal(plainAnswer.getBytes(StandardCharsets.UTF_8));

      return new String[]{
              Base64.getEncoder().encodeToString(iv),
              Base64.getEncoder().encodeToString(cipherText)
      };
    } catch (Exception e) {
      throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  public String decrypt(String ivBase64, String cipherTextBase64) {
    try {
      byte[] iv = Base64.getDecoder().decode(ivBase64);
      byte[] cipherText = Base64.getDecoder().decode(cipherTextBase64);

      Cipher cipher = Cipher.getInstance(AES_ALGO);
      cipher.init(Cipher.DECRYPT_MODE, buildKey(), new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));

      byte[] plainBytes = cipher.doFinal(cipherText);
      return new String(plainBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new ApiException(ErrorCode.INVALID_ANSWER_TOKEN);
    }
  }

  public VerifyResult verifyAndReveal(String ivBase64, String cipherTextBase64, String userAnswer) {
    String realAnswer = decrypt(ivBase64, cipherTextBase64);
    boolean isCorrect = normalize(realAnswer).equals(normalize(userAnswer));
    return new VerifyResult(isCorrect, realAnswer);
  }

  public record VerifyResult(boolean correct, String correctAnswer) {}

  private SecretKeySpec buildKey() throws Exception {
    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
    byte[] keyBytes = sha256.digest(learningCheckerProperties.getLearningSecret().getBytes(StandardCharsets.UTF_8));
    return new SecretKeySpec(keyBytes, "AES");
  }

  private String normalize(String answer) {
    return answer == null ? "" : answer.trim().toLowerCase(Locale.ROOT);
  }
}