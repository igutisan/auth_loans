package co.com.pragma.api.jwt.jwt;


import co.com.pragma.api.config.UserDetailsServiceImpl;
import co.com.pragma.api.exceptions.TokenException;
import co.com.pragma.model.user.gateways.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenService jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtProvider.getSubject(auth.getCredentials().toString()))
                .onErrorResume(e -> Mono.error(new TokenException("Invalid token")))
                .flatMap(id -> userDetailsService.findById(id)
                        .map(userDetails -> new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        )));
    }
}

