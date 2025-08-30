package co.com.pragma.usecase.login;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.PasswordService;
import co.com.pragma.model.user.gateways.TokenService;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LogInUseCase {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    public Mono<String> login(String email, String password) {
        return userRepository.findUserByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid password")))
                .flatMap(user -> {
                    if (!passwordService.matches(password, user.getPassword())){
                        return Mono.error(new RuntimeException("Invalid password"));
                    }
                    System.out.println(user.getRol());
                        return Mono.just(tokenService.generateToken(user));
                });
    }
}
