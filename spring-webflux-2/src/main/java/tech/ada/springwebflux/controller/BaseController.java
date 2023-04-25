package tech.ada.springwebflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@RestController
public class BaseController {

    @GetMapping("/flux")
    public Flux<Integer> flux(){
        return Flux.just(1,2,3);
    }

    @GetMapping(value = "/mono")
    public Mono<String> mono(){
        return Mono.just("hello");
    }

    @GetMapping(path = "/hora", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> {
                    return "hora - " + LocalTime.now().toString();
                }).log();
    }

    @GetMapping("/data")
    public Flux<List<Integer>> data(){
        return Flux.interval(Duration.ofSeconds(1))
                .take(2)
                .buffer(4)
                .map(list -> {
                    return List.of(1,2,3,4,5,6,7,8,9,10);
                });
    }

    @GetMapping("/thread-subscribe")
    public Mono<String> thread_subscribe() throws InterruptedException {
        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
                .map(msg -> msg + "thread ")
                .subscribe(v -> System.out.println(v + Thread.currentThread().getName())
                )
        );

        System.out.println("Before-start");
        t.start();
        System.out.println("after-start");
        t.join();
        System.out.println("after-join");
        return mono.doOnNext(msg -> System.out.println("hello received"));
    }

    @GetMapping("/thread-publish")
    public Flux<String> thread_publish() throws InterruptedException{
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1,2)
                .map(i -> 10 + i)
                .publishOn(s)
                .map(i -> "value " + i);

        new Thread(() -> flux.subscribe(System.out::println));
        return flux;
    }

}
