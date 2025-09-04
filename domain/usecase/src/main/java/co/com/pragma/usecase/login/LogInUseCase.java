package co.com.pragma.usecase.login;

import co.com.pragma.model.user.exceptions.BadCredentials;
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
                .switchIfEmpty(Mono.error(new BadCredentials("Invalid Credentials")))
                .flatMap(user -> {
                    if (!passwordService.matches(password, user.getPassword())){
                        return Mono.error(new BadCredentials("Invalid Credentials"));
                    }
                        return Mono.just(tokenService.generateToken(user));
                });
    }
}
