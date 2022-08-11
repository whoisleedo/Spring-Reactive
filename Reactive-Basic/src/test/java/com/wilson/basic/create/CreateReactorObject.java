package com.wilson.basic.create;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CreateReactorObject {

    @Test
    public void createAFlux_just() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orage", "Grape", "Banana", "Strawberry");
        
        fruitFlux.subscribe(fruit -> System.out.println("Here's some fruit:" + fruit));
        
        
        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orage")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
    }
    @Test
    public void createAFlux_fromArray() {
        String[] fruits = new String[] { "Apple", "Orage", "Grape", "Banana", "Strawberry" };
        Flux<String> fruitFlux = Flux.fromArray(fruits);
       
        
        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orage")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();

    }
    
    @Test
    public void createAFlux_fromIterable() {
        List<String> fruitList = new ArrayList<>();
        fruitList.add("Apple");
        fruitList.add("Orage");
        fruitList.add("Grape");
        fruitList.add("Banana");
        fruitList.add("Strawberry");
        
        Flux<String> fruitFlux = Flux.fromIterable(fruitList);
        
        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orage")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
    }
    
    @Test
    public void createAFlux_fromStream() {
        Stream<String> fruitStream = Stream.of("Apple", "Orage", "Grape", "Banana", "Strawberry");
        
        Flux<String> fruitFlux = Flux.fromStream(fruitStream);
        
        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orage")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
    }
    
    @Test
    public void createAFlux_rang() {
        Flux<Integer> intervalFlux = Flux.range(1, 5);
        
        //intervalFlux.subscribe(System.out::println);
        
        StepVerifier.create(intervalFlux)
                    .expectNext(1)
                    .expectNext(2)
                    .expectNext(3)
                    .expectNext(4)
                    .expectNext(5)
                    .verifyComplete();
    }
    
    @Test
    public void createAFlux_interval() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);
        
        intervalFlux.subscribe(System.out::println);
        
        StepVerifier.create(intervalFlux)
                    .expectNext(0L)
                    .expectNext(1L)
                    .expectNext(2L)
                    .expectNext(3L)
                    .expectNext(4L)
                    .verifyComplete();
    }

}
