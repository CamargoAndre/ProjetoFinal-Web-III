package tech.ada.springwebflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ada.springwebflux.model.User;
import tech.ada.springwebflux.service.UserService;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<User>> salvar(@RequestBody User user){
        return service.salvar(user)
                .map(atual -> ResponseEntity.ok().body(atual));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> atualizar(@RequestBody UserRequest user, @PathVariable String id){
        return service.atualizar(user.create(), id)
                .map(atual -> ResponseEntity.ok().body(atual))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }


    @GetMapping
    public Mono<ResponseEntity<Flux<User>>> listar(){
        return service.listar()
                .collectList()
                .map(users -> ResponseEntity.ok().body(Flux.fromIterable(users)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getById(@PathVariable String id){
        return service.buscarPorId(id)
                .map(atual -> ResponseEntity.ok().body(atual))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PostMapping(value = "/pagamentos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Comprovante> pagamento(@RequestBody Comprovante comprovante){
       return service.atualizar(comprovante);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> remover(@PathVariable String id){
        return service.remover(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .onErrorResume(e -> Mono.just((ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())));
    }

    @GetMapping("/usernames")
    public Mono<ResponseEntity<Flux<User>>> buscarPorUsername(@RequestParam("users") String[] users){
        return service.buscarPorUsernames(users)
                .collectList()
                .map(atual -> ResponseEntity.ok().body(Flux.fromIterable(atual)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }




}
