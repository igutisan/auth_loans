package co.com.pragma.api.Impl;

import co.com.pragma.api.jwt.jwt.JwtProvider;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtProvider jwtProvider;

    @Override
    public String generateToken(User user) {
        return jwtProvider.generateToken(user);
    }
}
