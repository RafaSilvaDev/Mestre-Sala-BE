package com.project.mestresala.mestresalabe.model.user;

public record RegisterDTO(String fullName, String email, String password, UserRole role) {
}
