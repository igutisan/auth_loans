package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, String>, ReactiveQueryByExampleExecutor<UserEntity> {


    Mono<UserEntity> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

    Mono<UserEntity> findUserByDni(String dni);

    Mono<Boolean> existsByDni(String dni);
}
