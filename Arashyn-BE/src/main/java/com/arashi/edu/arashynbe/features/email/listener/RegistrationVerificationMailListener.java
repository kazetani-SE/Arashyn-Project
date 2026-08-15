package com.arashi.edu.arashynbe.features.email.listener;

import com.arashi.edu.arashynbe.features.email.event.RegistrationVerificationRequestedEvent;
import com.arashi.edu.arashynbe.features.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class RegistrationVerificationMailListener {

  private final EmailService emailService;

  @Async("mailExecutor")
  // Guarantees the mail is only sent AFTER the pending_registrations row
  // has actually been committed to the DB — avoids sending a code that
  // the DB transaction later rolls back.
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onVerificationRequested(RegistrationVerificationRequestedEvent event) {
    emailService.sendVerificationEmail(event.email(), event.otpCode());
  }
}