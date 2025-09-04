package co.com.pragma.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRolesTest {

    @Test
    void shouldHaveAllExpectedRoles() {
        // Given & When
        UserRoles[] roles = UserRoles.values();

        // Then
        assertEquals(3, roles.length);
        assertTrue(contains(roles, UserRoles.ADMIN));
        assertTrue(contains(roles, UserRoles.ASESOR));
        assertTrue(contains(roles, UserRoles.CLIENTE));
    }

    @Test
    void shouldConvertToStringCorrectly() {
        // Given & When & Then
        assertEquals("ADMIN", UserRoles.ADMIN.toString());
        assertEquals("ASESOR", UserRoles.ASESOR.toString());
        assertEquals("CLIENTE", UserRoles.CLIENTE.toString());
    }

    @Test
    void shouldParseFromStringCorrectly() {
        // Given & When & Then
        assertEquals(UserRoles.ADMIN, UserRoles.valueOf("ADMIN"));
        assertEquals(UserRoles.ASESOR, UserRoles.valueOf("ASESOR"));
        assertEquals(UserRoles.CLIENTE, UserRoles.valueOf("CLIENTE"));
    }

    @Test
    void shouldThrowExceptionForInvalidRole() {
        // Given
        String invalidRole = "INVALID_ROLE";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            UserRoles.valueOf(invalidRole);
        });
    }

    @Test
    void shouldCompareEnumValues() {
        // Given & When & Then
        assertEquals(UserRoles.ADMIN, UserRoles.ADMIN);
        assertNotEquals(UserRoles.ADMIN, UserRoles.ASESOR);
        assertNotEquals(UserRoles.ASESOR, UserRoles.CLIENTE);
    }

    @Test
    void shouldReturnCorrectOrdinalValues() {
        // Given & When & Then
        assertEquals(0, UserRoles.ADMIN.ordinal());
        assertEquals(1, UserRoles.ASESOR.ordinal());
        assertEquals(2, UserRoles.CLIENTE.ordinal());
    }

    @Test
    void shouldReturnCorrectName() {
        // Given & When & Then
        assertEquals("ADMIN", UserRoles.ADMIN.name());
        assertEquals("ASESOR", UserRoles.ASESOR.name());
        assertEquals("CLIENTE", UserRoles.CLIENTE.name());
    }

    private boolean contains(UserRoles[] roles, UserRoles target) {
        for (UserRoles role : roles) {
            if (role == target) {
                return true;
            }
        }
        return false;
    }
}
