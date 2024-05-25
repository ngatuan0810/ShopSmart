package com.example.shopsmart;


import junit.framework.TestCase;

public class LogInActivityTest extends TestCase {

    public void testValidEmail() {
        // Arrange
        String validEmail = "example@example.com";

        // Act
        boolean isValid = isValidEmail(validEmail);

        // Assert
        assertTrue(isValid);
    }


    public void testInvalidEmail() {
        // Arrange
        String invalidEmail = "invalid-email";

        // Act
        boolean isValid = isValidEmail(invalidEmail);

        // Assert
        assertTrue(isValid);
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    }

    public void testValidPassword(){
        String validPassword = "validPassword";
        boolean isValid = isValidPassword(validPassword);
        assertTrue(isValid);
    }

    public void testInValidPassword(){
        String inValidPassword = "short";
        boolean isValid = isValidPassword(inValidPassword);
        assertTrue(isValid);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}