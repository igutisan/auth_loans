package co.com.pragma.model.user.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BadCredentialsTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Given
        String expectedMessage = "Invalid credentials provided";

        // When
        BadCredentials exception = new BadCredentials(expectedMessage);

        // Then
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldCreateExceptionWithNullMessage() {
        // Given
        String nullMessage = null;

        // When
        BadCredentials exception = new BadCredentials(nullMessage);

        // Then
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        BadCredentials exception = new BadCredentials(emptyMessage);

        // Then
        assertNotNull(exception);
        assertEquals(emptyMessage, exception.getMessage());
    }

    @Test
    void shouldBeThrowable() {
        // Given
        String message = "Test exception";
        BadCredentials exception = new BadCredentials(message);

        // When & Then
        assertThrows(BadCredentials.class, () -> {
            throw exception;
        });
    }

    @Test
    void shouldExtendRuntimeException() {
        // Given
        BadCredentials exception = new BadCredentials("Test");

        // When & Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }
}
