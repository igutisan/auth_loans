package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;

public interface TokenService {
    String generateToken(User user);
    String getSubject(String token);
    boolean validate(String token);
}
