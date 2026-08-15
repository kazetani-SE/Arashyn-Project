package com.arashi.edu.arashynbe.features.email.event;

public record RegistrationVerificationRequestedEvent(String email, String otpCode) {
}