package co.com.pragma.model.user.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailAlreadyExistExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Given
        String expectedMessage = "Email already exists in the system";

        // When
        EmailAlreadyExistException exception = new EmailAlreadyExistException(expectedMessage);

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
        EmailAlreadyExistException exception = new EmailAlreadyExistException(nullMessage);

        // Then
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        EmailAlreadyExistException exception = new EmailAlreadyExistException(emptyMessage);

        // Then
        assertNotNull(exception);
        assertEquals(emptyMessage, exception.getMessage());
    }

    @Test
    void shouldBeThrowable() {
        // Given
        String message = "Test email already exists";
        EmailAlreadyExistException exception = new EmailAlreadyExistException(message);

        // When & Then
        assertThrows(EmailAlreadyExistException.class, () -> {
            throw exception;
        });
    }

    @Test
    void shouldExtendRuntimeException() {
        // Given
        EmailAlreadyExistException exception = new EmailAlreadyExistException("Test");

        // When & Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    void shouldCreateWithTypicalMessage() {
        // Given
        String typicalMessage = "El correo electrónico ya se encuentra registrado en el sistema";

        // When
        EmailAlreadyExistException exception = new EmailAlreadyExistException(typicalMessage);

        // Then
        assertEquals(typicalMessage, exception.getMessage());
    }
}
