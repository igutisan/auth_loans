package co.com.pragma.consumer;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.SyncGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements SyncGateway {
    private final WebClient client;

    @Override
    @CircuitBreaker(name = "rest-consumer", fallbackMethod = "sendUserFallback")
    public Mono<Void> sendUser(User user) {
        return client
                .post()
                .uri("/api/v1/users")
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> sendUserFallback(User user, Throwable throwable) {
        return Mono.error(throwable);
    }
}
