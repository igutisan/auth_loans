package co.com.pragma.api;


import co.com.pragma.api.dto.CreateUserDto;
import co.com.pragma.api.dto.LoginDto;
import co.com.pragma.api.exceptions.ValidationException;
import co.com.pragma.api.mapper.UserDTOMapper;

import co.com.pragma.usecase.login.LogInUseCase;
import co.com.pragma.usecase.user.UserUseCase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final Validator validator;
    private final UserUseCase userUseCase;
    private final LogInUseCase logInUseCase;
    private final UserDTOMapper userMapper;
    private final TransactionalOperator transactionalOperator;
    private final PasswordEncoder passwordEncoder;




    public Mono<ServerResponse> listenExistUserByDni(ServerRequest serverRequest) {
        String dni = serverRequest.pathVariable("dni");
        return userUseCase.existUserByDni(dni)
                .flatMap(exists -> {
                    log.info("Validating user");
                    if(exists){
                        return ServerResponse.ok().bodyValue(Map.of("exists", true));
                    }
                    return ServerResponse.notFound().build();
                });

    }



    public Mono<ServerResponse> listenRegisterUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDto.class)
                .flatMap(dto -> {
                    log.info("Validating dto");
                    Errors errors = new BeanPropertyBindingResult(dto, "dto");
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        log.error("Errors found in mapping dto");

                        Map<String, String> fieldErrors = new HashMap<>();
                        errors.getFieldErrors().forEach(error -> {
                            String fieldName = error.getField();
                            String errorMessage = error.getDefaultMessage();
                            fieldErrors.put(fieldName, errorMessage);
                        });

                        return Mono.<CreateUserDto>error(new ValidationException(
                                "Error en la validación de los datos", fieldErrors));
                    }


                    return Mono.just(dto);
                })
                .map(userMapper::toModel)
                .flatMap(userUseCase::registerUser)
                .map(userMapper::toResponseDto)
                .as(transactionalOperator::transactional)
                .flatMap(userResponse -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse));
    }

    public Mono<ServerResponse> listenGetUserByDni(ServerRequest serverRequest) {
        String dni = serverRequest.pathVariable("dni");
        return userUseCase.getUserByDni(dni)
                .map(userMapper::toResponseDto)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listenLogin(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoginDto.class)
                .flatMap(dto -> logInUseCase.login(dto.email(), dto.password()))
                .flatMap(token -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("token", token)));
    }
}


