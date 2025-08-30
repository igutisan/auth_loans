package co.com.pragma.api;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/users",
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "listenRegisterUser"
            ),
            @RouterOperation(
                    path = "/api/v1/users/{dni}",
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "listenExistUserByDni"
            ),
            @RouterOperation(
                    path = "/auth/login",
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "listenLogin"
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/users"), handler::listenRegisterUser)
                .andRoute(GET("/api/v1/users/{dni}"), handler::listenExistUserByDni)
                .andRoute(POST("/auth/login"), handler::listenLogin);
    }
}