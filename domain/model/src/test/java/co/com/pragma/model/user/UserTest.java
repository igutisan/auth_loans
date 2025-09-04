package co.com.pragma.model.user;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithBuilder() {
        // Given
        String expectedId = "123e4567-e89b-12d3-a456-426614174000";
        String expectedNames = "John";
        String expectedLastNames = "Doe";
        String expectedPassword = "encodedPassword123";
        String expectedDni = "12345678";
        LocalDate expectedDob = LocalDate.of(1990, 5, 15);
        String expectedPhone = "1234567890";
        String expectedAddress = "123 Main St";
        String expectedEmail = "john.doe@example.com";
        BigDecimal expectedSalary = BigDecimal.valueOf(5000.00);
        UserRoles expectedRole = UserRoles.CLIENTE;

        // When
        User user = User.builder()
                .id(expectedId)
                .names(expectedNames)
                .lastNames(expectedLastNames)
                .password(expectedPassword)
                .dni(expectedDni)
                .dob(expectedDob)
                .phone(expectedPhone)
                .address(expectedAddress)
                .email(expectedEmail)
                .salary(expectedSalary)
                .role(expectedRole)
                .build();

        // Then
        assertNotNull(user);
        assertEquals(expectedId, user.getId());
        assertEquals(expectedNames, user.getNames());
        assertEquals(expectedLastNames, user.getLastNames());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedDni, user.getDni());
        assertEquals(expectedDob, user.getDob());
        assertEquals(expectedPhone, user.getPhone());
        assertEquals(expectedAddress, user.getAddress());
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedSalary, user.getSalary());
        assertEquals(expectedRole, user.getRole());
    }

    @Test
    void shouldCreateUserWithNoArgsConstructor() {
        // When
        User user = new User();

        // Then
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getNames());
        assertNull(user.getLastNames());
        assertNull(user.getPassword());
        assertNull(user.getDni());
        assertNull(user.getDob());
        assertNull(user.getPhone());
        assertNull(user.getAddress());
        assertNull(user.getEmail());
        assertNull(user.getSalary());
        assertNull(user.getRole());
    }

    @Test
    void shouldCreateUserWithAllArgsConstructor() {
        // Given
        String expectedId = "123e4567-e89b-12d3-a456-426614174000";
        String expectedNames = "Jane";
        String expectedLastNames = "Smith";
        String expectedPassword = "hashedPassword456";
        String expectedDni = "87654321";
        LocalDate expectedDob = LocalDate.of(1985, 8, 22);
        String expectedPhone = "9876543210";
        String expectedAddress = "456 Oak Ave";
        String expectedEmail = "jane.smith@example.com";
        BigDecimal expectedSalary = BigDecimal.valueOf(7500.50);
        UserRoles expectedRole = UserRoles.ASESOR;

        // When
        User user = new User(expectedId, expectedNames, expectedLastNames, expectedPassword,
                expectedDni, expectedDob, expectedPhone, expectedAddress, expectedEmail,
                expectedSalary, expectedRole);

        // Then
        assertNotNull(user);
        assertEquals(expectedId, user.getId());
        assertEquals(expectedNames, user.getNames());
        assertEquals(expectedLastNames, user.getLastNames());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedDni, user.getDni());
        assertEquals(expectedDob, user.getDob());
        assertEquals(expectedPhone, user.getPhone());
        assertEquals(expectedAddress, user.getAddress());
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedSalary, user.getSalary());
        assertEquals(expectedRole, user.getRole());
    }

    @Test
    void shouldModifyUserWithSetters() {
        // Given
        User user = new User();
        String expectedEmail = "modified@example.com";
        UserRoles expectedRole = UserRoles.ADMIN;

        // When
        user.setEmail(expectedEmail);
        user.setRole(expectedRole);

        // Then
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedRole, user.getRole());
    }

    @Test
    void shouldCreateCopyWithToBuilder() {
        // Given
        User originalUser = User.builder()
                .id("original-id")
                .names("Original")
                .lastNames("User")
                .email("original@example.com")
                .role(UserRoles.CLIENTE)
                .salary(BigDecimal.valueOf(3000))
                .build();

        // When
        User modifiedUser = originalUser.toBuilder()
                .email("modified@example.com")
                .role(UserRoles.ADMIN)
                .build();

        // Then
        assertNotEquals(originalUser, modifiedUser);
        assertEquals("original-id", modifiedUser.getId());
        assertEquals("Original", modifiedUser.getNames());
        assertEquals("User", modifiedUser.getLastNames());
        assertEquals("modified@example.com", modifiedUser.getEmail());
        assertEquals(UserRoles.ADMIN, modifiedUser.getRole());
        assertEquals(BigDecimal.valueOf(3000), modifiedUser.getSalary());
        
        // Original should remain unchanged
        assertEquals("original@example.com", originalUser.getEmail());
        assertEquals(UserRoles.CLIENTE, originalUser.getRole());
    }

    @Test
    void shouldHandleNullValues() {
        // When
        User user = User.builder()
                .id(null)
                .names(null)
                .email(null)
                .salary(null)
                .role(null)
                .build();

        // Then
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getNames());
        assertNull(user.getEmail());
        assertNull(user.getSalary());
        assertNull(user.getRole());
    }
}
