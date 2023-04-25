package tech.ada.mercado.cotroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ada.mercado.exceptions.NotFoundException;
import tech.ada.mercado.model.Cotacao;
import tech.ada.mercado.model.Mercado;
import tech.ada.mercado.service.MercadoService;

@RestController
@Slf4j
@RequestMapping("/mercados")
public class MercadoController {

    private MercadoService service;

    public MercadoController(MercadoService service){
        this.service = service;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Mercado>> save(@RequestBody Mercado mercado){
        return service.save(mercado).map(m -> ResponseEntity.ok().body(m));
    }

    @PutMapping("/id")
    public Mono<ResponseEntity<Mercado>> update(@RequestBody Mercado mercado, @RequestParam String id){
        return service.update(mercado, id)
                .map(m -> ResponseEntity.ok().body(m))
                .switchIfEmpty(Mono.error(NotFoundException::new));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public Mono<ResponseEntity<Flux<Mercado>>> findAll(){
        return service.findAll()
                .collectList()
                .map(m -> ResponseEntity.ok().body(Flux.fromIterable(m)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mercado>> findById(@PathVariable String id){
        return service.findById(id)
                .map(m -> ResponseEntity.ok().body(m))
                .switchIfEmpty(Mono.error(NotFoundException::new));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return service.delete(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .onErrorResume(e -> Mono.error(InternalError::new));
    }

    @GetMapping("/moeda")
    public Mono<ResponseEntity<Flux<Cotacao>>> consultaAPI(@RequestParam("moeda") String moeda){
        return service.cotacao(moeda).collectList()
                .map(m -> ResponseEntity.ok().body(Flux.fromIterable(m)));
    }
}
