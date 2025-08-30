package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
>implements UserRepository {



    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, entity -> mapper.map(entity, User.class));

    }

    @Override
    public Mono<User> save(User user) {
        return super.save(user);
    }


    @Override
    public Mono<Boolean> existUserByDni(String dni) {
        return repository.existsByDni(dni);
    }

    @Override
    public Mono<User> findUserById(String id) {
        return super.findById(id);
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toEntity);
    }

    @Override
    public Mono<User> findUserByDni(String dni) {
        return repository.findUserByDni(dni)
                .map(this::toEntity);
    }


    @Override
    public Mono<Boolean> existUserByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<User> updateUser(User user) {
        return null;
    }

    @Override
    public Mono<Void> deleteUserById(String id) {
        return null;
    }
}
