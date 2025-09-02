package co.com.pragma.api.config;

import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserUseCase userUseCase;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userUseCase.findUserByEmail(email)
                .map(this::buildUserDetails);
    }

    public Mono<UserDetails> findById(String id) {
        return userUseCase.findUserById(id)
                .map(this::buildUserDetails);
    }

    private UserDetails buildUserDetails(User user) {
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }
}
