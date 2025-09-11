package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;

public interface TokenService {
    String generateToken(User user);
    String getSubject(String token);
    boolean validate(String token);
}

// manejar mejor los mensajes cuando son problemas de roles
// Manejar una estructrua de devolucion ene la paginacion