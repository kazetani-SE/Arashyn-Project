package com.arashi.edu.arashynbe.features.learning.util;

import com.arashi.edu.arashynbe.config.properties.LearningCheckerProperties;
import com.arashi.edu.arashynbe.features.learning.util.dto.AnswerPayload;
import com.arashi.edu.arashynbe.features.learning.util.dto.EncryptedAnswer;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnswerCryptoUtil {

  private static final String AES_ALGO = "AES/GCM/NoPadding";
  private static final String HASH_ALGO = "SHA-256";
  private static final int IV_LENGTH_BYTES = 12;
  private static final int GCM_TAG_LENGTH_BITS = 128;
  private static final String DELIMITER = "|";
  private static final String DELIMITER_REGEX = "\\|";

  private final LearningCheckerProperties learningCheckerProperties;
  private final SecureRandom secureRandom = new SecureRandom();

  public record VerifyResult(
          UUID userGrammarId,
          boolean correct,
          String correctAnswer
  ) {}

  public EncryptedAnswer encrypt(AnswerPayload payload) {
    try {
      byte[] iv = new byte[IV_LENGTH_BYTES];
      secureRandom.nextBytes(iv);

      Cipher cipher = Cipher.getInstance(AES_ALGO);
      cipher.init(
              Cipher.ENCRYPT_MODE,
              getSecretKey(),
              new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv)
      );

      byte[] plainBytes = serialize(payload).getBytes(StandardCharsets.UTF_8);
      byte[] cipherBytes = cipher.doFinal(plainBytes);

      return new EncryptedAnswer(
              Base64.getEncoder().encodeToString(iv),
              Base64.getEncoder().encodeToString(cipherBytes)
      );
    } catch (Exception e) {
      throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  public AnswerPayload decrypt(String ivBase64, String cipherTextBase64) {
    try {
      byte[] iv = Base64.getDecoder().decode(ivBase64);
      byte[] cipherText = Base64.getDecoder().decode(cipherTextBase64);

      Cipher cipher = Cipher.getInstance(AES_ALGO);
      cipher.init(
              Cipher.DECRYPT_MODE,
              getSecretKey(),
              new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv)
      );

      byte[] plainBytes = cipher.doFinal(cipherText);
      return deserialize(new String(plainBytes, StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new ApiException(ErrorCode.INVALID_ANSWER_TOKEN);
    }
  }

  public VerifyResult verifyAndReveal(
          String ivBase64,
          String cipherTextBase64,
          String userAnswer
  ) {
    AnswerPayload payload = decrypt(ivBase64, cipherTextBase64);
    boolean isCorrect = normalize(payload.answer()).equals(normalize(userAnswer));

    return new VerifyResult(
            payload.userGrammarId(),
            isCorrect,
            payload.answer()
    );
  }

  private String serialize(AnswerPayload payload) {
    return payload.userGrammarId() + DELIMITER + payload.answer();
  }

  private AnswerPayload deserialize(String value) {
    String[] parts = value.split(DELIMITER_REGEX, 2);

    if (parts.length != 2) {
      throw new ApiException(ErrorCode.INVALID_ANSWER_TOKEN);
    }

    try {
      return new AnswerPayload(
              UUID.fromString(parts[0]),
              parts[1]
      );
    } catch (IllegalArgumentException e) {
      throw new ApiException(ErrorCode.INVALID_ANSWER_TOKEN);
    }
  }

  private SecretKeySpec getSecretKey() throws NoSuchAlgorithmException {
    MessageDigest sha256 = MessageDigest.getInstance(HASH_ALGO);
    byte[] keyBytes = sha256.digest(
            learningCheckerProperties
                    .getLearningSecret()
                    .getBytes(StandardCharsets.UTF_8)
    );
    return new SecretKeySpec(keyBytes, "AES");
  }

  private String normalize(String answer) {
    return Objects.requireNonNullElse(answer, "")
            .trim()
            .toLowerCase(Locale.ROOT);
  }
}