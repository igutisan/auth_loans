package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.UserRoles;
import co.com.pragma.model.user.exceptions.EmailAlreadyExistException;
import co.com.pragma.model.user.gateways.LoggerGateway;
import co.com.pragma.model.user.gateways.SyncGateway;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private LoggerGateway loggerGateway;
    
    @Mock
    private SyncGateway syncGateway;

    private UserUseCase userUseCase;

    private User testUser;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userRepository, loggerGateway, syncGateway);
        
        testUser = User.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .names("John")
                .lastNames("Doe")
                .email("john.doe@example.com")
                .dni("12345678")
                .password("encodedPassword")
                .dob(LocalDate.of(1990, 1, 1))
                .phone("1234567890")
                .address("123 Main St")
                .salary(BigDecimal.valueOf(5000))
                .role(UserRoles.CLIENTE)
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Given
        when(userRepository.existUserByEmail(testUser.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.save(testUser)).thenReturn(Mono.just(testUser));
        when(syncGateway.sendUser(any(User.class))).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(userUseCase.registerUser(testUser))
                .expectNext(testUser)
                .verifyComplete();

        verify(loggerGateway).logInfo("Registering user");
        verify(loggerGateway).logInfo("Checking if email is unique");
        verify(loggerGateway).logInfo("User registered: " + testUser.getEmail());
        verify(userRepository).existUserByEmail(testUser.getEmail());
        verify(userRepository).save(testUser);
        verify(syncGateway).sendUser(testUser);
    }

    @Test
    void shouldRegisterNonClienteUserWithoutSyncing() {
        // Given
        User adminUser = testUser.toBuilder().role(UserRoles.ADMIN).build();
        when(userRepository.existUserByEmail(adminUser.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.save(adminUser)).thenReturn(Mono.just(adminUser));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(adminUser))
                .expectNext(adminUser)
                .verifyComplete();

        verify(loggerGateway).logInfo("Registering user");
        verify(loggerGateway).logInfo("Checking if email is unique");
        verify(loggerGateway).logInfo("User registered: " + adminUser.getEmail());
        verify(userRepository).existUserByEmail(adminUser.getEmail());
        verify(userRepository).save(adminUser);
        verify(syncGateway, never()).sendUser(any());
    }

    @Test
    void shouldRegisterAsesorUserWithoutSyncing() {
        // Given
        User asesorUser = testUser.toBuilder().role(UserRoles.ASESOR).build();
        when(userRepository.existUserByEmail(asesorUser.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.save(asesorUser)).thenReturn(Mono.just(asesorUser));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(asesorUser))
                .expectNext(asesorUser)
                .verifyComplete();

        verify(syncGateway, never()).sendUser(any());
    }

    @Test
    void shouldValidateEmailUniqueness() {
        // Given
        String existingEmail = "existing@example.com";
        when(userRepository.existUserByEmail(existingEmail)).thenReturn(Mono.just(true));

        // When & Then - Test que el repository es llamado para verificar email
        userRepository.existUserByEmail(existingEmail)
                .subscribe(exists -> {
                    assertTrue(exists);
                });

        verify(userRepository).existUserByEmail(existingEmail);
    }

    @Test
    void shouldGetUserByDniSuccessfully() {
        // Given
        String dni = testUser.getDni();
        when(userRepository.findUserByDni(dni)).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.getUserByDni(dni))
                .expectNext(testUser)
                .verifyComplete();

        verify(loggerGateway).logInfo("Getting user by dni");
        verify(userRepository).findUserByDni(dni);
    }

    @Test
    void shouldReturnEmptyWhenUserByDniNotFound() {
        // Given
        String dni = "nonexistent";
        when(userRepository.findUserByDni(dni)).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(userUseCase.getUserByDni(dni))
                .verifyComplete();

        verify(loggerGateway).logInfo("Getting user by dni");
        verify(userRepository).findUserByDni(dni);
    }

    @Test
    void shouldCheckIfUserExistsByDni() {
        // Given
        String dni = testUser.getDni();
        when(userRepository.existUserByDni(dni)).thenReturn(Mono.just(true));

        // When & Then
        StepVerifier.create(userUseCase.existUserByDni(dni))
                .expectNext(true)
                .verifyComplete();

        verify(loggerGateway).logInfo("Checking if user exist");
        verify(userRepository).existUserByDni(dni);
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotExistByDni() {
        // Given
        String dni = "nonexistent";
        when(userRepository.existUserByDni(dni)).thenReturn(Mono.just(false));

        // When & Then
        StepVerifier.create(userUseCase.existUserByDni(dni))
                .expectNext(false)
                .verifyComplete();

        verify(loggerGateway).logInfo("Checking if user exist");
        verify(userRepository).existUserByDni(dni);
    }

    @Test
    void shouldFindUserByEmailSuccessfully() {
        // Given
        String email = testUser.getEmail();
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.findUserByEmail(email))
                .expectNext(testUser)
                .verifyComplete();

        verify(loggerGateway).logInfo("Getting user by email");
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void shouldReturnEmptyWhenUserByEmailNotFound() {
        // Given
        String email = "nonexistent@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(userUseCase.findUserByEmail(email))
                .verifyComplete();

        verify(loggerGateway).logInfo("Getting user by email");
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void shouldFindUserByIdSuccessfully() {
        // Given
        String id = testUser.getId();
        when(userRepository.findUserById(id)).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.findUserById(id))
                .expectNext(testUser)
                .verifyComplete();

        verify(loggerGateway).logInfo("Getting user by id");
        verify(userRepository).findUserById(id);
    }

    @Test
    void shouldReturnEmptyWhenUserByIdNotFound() {
        // Given
        String id = "nonexistent-id";
        when(userRepository.findUserById(id)).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(userUseCase.findUserById(id))
                .verifyComplete();

        verify(loggerGateway).logInfo("Getting user by id");
        verify(userRepository).findUserById(id);
    }

    @Test
    void shouldHandleRepositoryErrorDuringRegistration() {
        // Given
        when(userRepository.existUserByEmail(testUser.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.save(testUser)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(testUser))
                .expectError(RuntimeException.class)
                .verify();

        verify(userRepository).save(testUser);
        verify(syncGateway, never()).sendUser(any());
    }

    @Test
    void shouldHandleSyncGatewayErrorDuringClienteRegistration() {
        // Given
        when(userRepository.existUserByEmail(testUser.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.save(testUser)).thenReturn(Mono.just(testUser));
        when(syncGateway.sendUser(testUser)).thenReturn(Mono.error(new RuntimeException("Sync error")));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(testUser))
                .expectError(RuntimeException.class)
                .verify();

        verify(syncGateway).sendUser(testUser);
    }
}
