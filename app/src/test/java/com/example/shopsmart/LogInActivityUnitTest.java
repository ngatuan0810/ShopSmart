package com.example.shopsmart;

import junit.framework.TestCase;

import org.junit.Test;

public class LogInActivityUnitTest extends TestCase {

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
        assertFalse(isValid);
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
        assertFalse(isValid);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }



    public void testHandleAppLogin_InvalidEmail() {
        // Arrange
        String invalidEmail = "invalid-email";
        String validPassword = "validPassword";

        // Act
        boolean isValidEmail = isValidEmail(invalidEmail);
        boolean isValidPassword = isValidPassword(validPassword);

        // Assert
        assertFalse(isValidEmail);
        assertTrue(isValidPassword);
    }

    public void testHandleAppLogin_InvalidPassword() {
        // Arrange
        String validEmail = "example@example.com";
        String invalidPassword = "short";

        // Act
        boolean isValidEmail = isValidEmail(validEmail);
        boolean isValidPassword = isValidPassword(invalidPassword);

        // Assert
        assertTrue(isValidEmail);
        assertFalse(isValidPassword);
    }


    public void testHandleAppLogin_ValidInputs() {
        // Arrange
        String validEmail = "example@example.com";
        String validPassword = "validPassword";

        // Act
        boolean isValidEmail = isValidEmail(validEmail);
        boolean isValidPassword = isValidPassword(validPassword);

        // Assert
        assertTrue(isValidEmail);
        assertTrue(isValidPassword);
    }

}