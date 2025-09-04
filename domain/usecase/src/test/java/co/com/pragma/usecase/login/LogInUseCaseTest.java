package co.com.pragma.usecase.login;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.UserRoles;
import co.com.pragma.model.user.exceptions.BadCredentials;
import co.com.pragma.model.user.gateways.PasswordService;
import co.com.pragma.model.user.gateways.TokenService;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogInUseCaseTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordService passwordService;
    
    @Mock
    private TokenService tokenService;

    private LogInUseCase logInUseCase;
    
    private User testUser;
    private String rawPassword = "plainPassword123";
    private String encodedPassword = "encodedPassword123";
    private String email = "test@example.com";
    private String expectedToken = "jwt.token.here";

    @BeforeEach
    void setUp() {
        logInUseCase = new LogInUseCase(userRepository, passwordService, tokenService);
        
        testUser = User.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .names("John")
                .lastNames("Doe")
                .email(email)
                .dni("12345678")
                .password(encodedPassword)
                .dob(LocalDate.of(1990, 1, 1))
                .phone("1234567890")
                .address("123 Main St")
                .salary(BigDecimal.valueOf(5000))
                .role(UserRoles.CLIENTE)
                .build();
    }

    @Test
    void shouldLoginSuccessfully() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(testUser));
        when(passwordService.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(tokenService.generateToken(testUser)).thenReturn(expectedToken);

        // When & Then
        StepVerifier.create(logInUseCase.login(email, rawPassword))
                .expectNext(expectedToken)
                .verifyComplete();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService).matches(rawPassword, encodedPassword);
        verify(tokenService).generateToken(testUser);
    }

    @Test
    void shouldThrowBadCredentialsWhenUserNotFound() {
        // Given
        String nonExistentEmail = "notfound@example.com";
        when(userRepository.findUserByEmail(nonExistentEmail)).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(logInUseCase.login(nonExistentEmail, rawPassword))
                .expectErrorMatches(throwable -> throwable instanceof BadCredentials &&
                        throwable.getMessage().equals("Invalid Credentials"))
                .verify();

        verify(userRepository).findUserByEmail(nonExistentEmail);
        verify(passwordService, never()).matches(anyString(), anyString());
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    void shouldThrowBadCredentialsWhenPasswordDoesNotMatch() {
        // Given
        String wrongPassword = "wrongPassword";
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(testUser));
        when(passwordService.matches(wrongPassword, encodedPassword)).thenReturn(false);

        // When & Then
        StepVerifier.create(logInUseCase.login(email, wrongPassword))
                .expectErrorMatches(throwable -> throwable instanceof BadCredentials &&
                        throwable.getMessage().equals("Invalid Credentials"))
                .verify();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService).matches(wrongPassword, encodedPassword);
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    void shouldLoginSuccessfullyWithAdminUser() {
        // Given
        User adminUser = testUser.toBuilder().role(UserRoles.ADMIN).build();
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(adminUser));
        when(passwordService.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(tokenService.generateToken(adminUser)).thenReturn(expectedToken);

        // When & Then
        StepVerifier.create(logInUseCase.login(email, rawPassword))
                .expectNext(expectedToken)
                .verifyComplete();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService).matches(rawPassword, encodedPassword);
        verify(tokenService).generateToken(adminUser);
    }

    @Test
    void shouldLoginSuccessfullyWithAsesorUser() {
        // Given
        User asesorUser = testUser.toBuilder().role(UserRoles.ASESOR).build();
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(asesorUser));
        when(passwordService.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(tokenService.generateToken(asesorUser)).thenReturn(expectedToken);

        // When & Then
        StepVerifier.create(logInUseCase.login(email, rawPassword))
                .expectNext(expectedToken)
                .verifyComplete();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService).matches(rawPassword, encodedPassword);
        verify(tokenService).generateToken(asesorUser);
    }

    @Test
    void shouldHandleRepositoryError() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // When & Then
        StepVerifier.create(logInUseCase.login(email, rawPassword))
                .expectError(RuntimeException.class)
                .verify();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService, never()).matches(anyString(), anyString());
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    void shouldHandlePasswordServiceError() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(testUser));
        when(passwordService.matches(rawPassword, encodedPassword))
                .thenThrow(new RuntimeException("Password service error"));

        // When & Then
        StepVerifier.create(logInUseCase.login(email, rawPassword))
                .expectError(RuntimeException.class)
                .verify();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService).matches(rawPassword, encodedPassword);
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    void shouldHandleTokenServiceError() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(testUser));
        when(passwordService.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(tokenService.generateToken(testUser)).thenThrow(new RuntimeException("Token generation error"));

        // When & Then
        StepVerifier.create(logInUseCase.login(email, rawPassword))
                .expectError(RuntimeException.class)
                .verify();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService).matches(rawPassword, encodedPassword);
        verify(tokenService).generateToken(testUser);
    }

    @Test
    void shouldHandleNullEmail() {
        // Given
        String nullEmail = null;
        when(userRepository.findUserByEmail(nullEmail)).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(logInUseCase.login(nullEmail, rawPassword))
                .expectError(BadCredentials.class)
                .verify();

        verify(userRepository).findUserByEmail(nullEmail);
    }

    @Test
    void shouldHandleEmptyEmail() {
        // Given
        String emptyEmail = "";
        when(userRepository.findUserByEmail(emptyEmail)).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(logInUseCase.login(emptyEmail, rawPassword))
                .expectError(BadCredentials.class)
                .verify();

        verify(userRepository).findUserByEmail(emptyEmail);
    }

    @Test
    void shouldHandleNullPassword() {
        // Given
        String nullPassword = null;
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(testUser));
        when(passwordService.matches(nullPassword, encodedPassword)).thenReturn(false);

        // When & Then
        StepVerifier.create(logInUseCase.login(email, nullPassword))
                .expectError(BadCredentials.class)
                .verify();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService).matches(nullPassword, encodedPassword);
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    void shouldHandleEmptyPassword() {
        // Given
        String emptyPassword = "";
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(testUser));
        when(passwordService.matches(emptyPassword, encodedPassword)).thenReturn(false);

        // When & Then
        StepVerifier.create(logInUseCase.login(email, emptyPassword))
                .expectError(BadCredentials.class)
                .verify();

        verify(userRepository).findUserByEmail(email);
        verify(passwordService).matches(emptyPassword, encodedPassword);
        verify(tokenService, never()).generateToken(any(User.class));
    }
}
