package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.EmailAlreadyExistException;
import co.com.pragma.model.user.gateways.LoggerGateway;
import co.com.pragma.model.user.gateways.SyncGateway;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;


import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository userRepository;
    private final LoggerGateway logs;
    private final SyncGateway syncGateway;



   public Mono<User> registerUser(User user) {
        logs.logInfo("Registering user");
       return emailIsUnique(user.getEmail())
               .then(userRepository.save(user))
               .flatMap(savedUser -> {
                   if ("CLIENTE".equalsIgnoreCase(String.valueOf(savedUser.getRole()))) {
                       return syncGateway.sendUser(savedUser)
                               .thenReturn(savedUser);
                   }
                   return Mono.just(savedUser);
               })
               .doOnSuccess(savedUser -> logs.logInfo("User registered: " + savedUser.getEmail()));

   }

    public Mono<User> getUserByDni(String dni) {
        logs.logInfo("Getting user by dni");
        return  userRepository.findUserByDni(dni);
    }
   public Mono<Boolean> existUserByDni( String dni) {
        logs.logInfo("Checking if user exist");
        return userRepository.existUserByDni(dni);
   }

    public Mono<User> findUserByEmail(String email) {
        logs.logInfo("Getting user by email");
        return userRepository.findUserByEmail(email);
    }

    public Mono<User> findUserById(String id) {
        logs.logInfo("Getting user by id");
        return userRepository.findUserById(id);
    }

    private Mono<Void> emailIsUnique(String email) {
       logs.logInfo("Checking if email is unique");
       return userRepository.existUserByEmail(email)
               .filter(Boolean::booleanValue)
               .flatMap(exist -> Mono.error(new EmailAlreadyExistException(
                       "El correo electrónico ya se encuentra registrado en el sistema")))
               .then();
    }

}
