package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

public interface SyncGateway {
    Mono<Void> sendUser(User user);
}
