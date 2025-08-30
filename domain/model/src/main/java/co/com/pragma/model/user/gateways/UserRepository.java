package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserRepository {
    Mono<User> save(User user);
    Mono<Boolean> existUserByDni(String dni);
   Mono<User> findUserByDni(String dni);
   Mono<Boolean> existUserByEmail(String email);
    Mono<User> findUserById(String id);
    Mono<User> findUserByEmail(String email);

    Mono<User> updateUser(User user);
   Mono<Void> deleteUserById(String id);
   Flux<User> findAll();
}
