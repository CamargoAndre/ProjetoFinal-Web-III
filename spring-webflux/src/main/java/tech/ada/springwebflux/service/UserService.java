package tech.ada.springwebflux.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ada.springwebflux.controller.Comprovante;
import tech.ada.springwebflux.model.User;
import tech.ada.springwebflux.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<User> salvar(User user){
        return repository.save(user);
    }

    public Flux<User> listar(){
        return repository.findAll();
    }

    public Mono<User> atualizar(User user, String id){
        return repository.findById(id)
                .flatMap(atual -> repository.save(atual.update(user)));
    }

    public Mono<?> remover(String id){
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("user not found id " + id)))
                .flatMap(u -> repository.deleteById(id))
                .then();
    }

    public Mono<User> buscarPorId(String id){
        return repository.findById(id);
    }

    public Flux<User> buscarPorUsernames(String ... users){
        return repository.findByUsernameIn(Arrays.asList(users));
    }

    public Mono<Comprovante> atualizar(Comprovante comp) {
        Flux<User> users = this.buscarPorUsernames(comp.getParamsUsers());
        return users.zipWith(users.skip(1))
                .map(tupla -> {
                    User pagador;
                    User recebedor;
                    if(tupla.getT1().getUsername().equals(comp.getPagador())){
                        pagador = tupla.getT1();
                        recebedor = tupla.getT2();
                    }else{
                        pagador = tupla.getT2();
                        recebedor = tupla.getT1();
                    }
                    pagador.pay(recebedor, comp);
                    return List.of(pagador, recebedor);
                })
                .flatMap(lista -> {
                    return repository.saveAll(lista);
                })
                .then(Mono.just(comp));
    }


}
