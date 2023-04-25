package tech.ada.springwebflux.repository;

import reactor.core.publisher.Flux;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tech.ada.springwebflux.model.User;

import java.util.List;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Flux<User> findByUsernameIn(List<String> usernames);
}
