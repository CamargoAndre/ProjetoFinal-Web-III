package tech.ada.mercado.service;

import com.mongodb.internal.connection.IndexMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ada.mercado.model.Cotacao;
import tech.ada.mercado.model.Mercado;
import tech.ada.mercado.repository.MercadoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoService {
    private MercadoRepository repository;

    public MercadoService(MercadoRepository repository) {
        this.repository = repository;
    }

    public Mono<Mercado> save(Mercado mercado) {
        return repository.save(mercado);
    }

    public Flux<Mercado> findAll() {
        return repository.findAll();
    }


    public Mono<Mercado> findById(String id) {

        return repository.findById(id);
    }

    public Mono<?> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("user not found id " + id)))
                .flatMap(m -> repository.deleteById(id))
                .then();
    }

    public Mono<Mercado> update(Mercado mercado, String id) {
        return this.findById(id)
                .flatMap( m -> repository.save(m.update(mercado)));
    }

    public Flux<Cotacao> cotacao(String moeda) {

        WebClient webClient = WebClient.create("https://economia.awesomeapi.com.br");
        Flux<Cotacao> cotacao = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + moeda +"/")
                        .build())
                .retrieve().bodyToFlux(Cotacao.class);
        return cotacao;
    }
}
