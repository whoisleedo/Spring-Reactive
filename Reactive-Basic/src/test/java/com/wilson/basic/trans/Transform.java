package com.wilson.basic.trans;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Transform {
    
    @Test
    public void skipFew() {
        Flux<String> skipFlux = Flux.just("one" , "two" , "three" , "four" , "five").skip(3);
        
        StepVerifier.create(skipFlux)
                    .expectNext("four" , "five")
                    .verifyComplete();
    }
    
    @Test
    public void skipAFewSeconds() {
        Flux<String> skipFlux = Flux.just("one" , "two" , "three" , "four" , "five")
                                    .delayElements(Duration.ofSeconds(1))
                                    .skip(Duration.ofSeconds(4));
        
        StepVerifier.create(skipFlux)
                    .expectNext("four" , "five")
                    .verifyComplete();
        
    }
    
    @Test
    public void takeNum() {
        Flux<String> takeFlux = Flux.just("one" , "two" , "three" , "four" , "five").take(3);
        
        
        StepVerifier.create(takeFlux)
                    .expectNext("one" , "two" , "three" )
                    .verifyComplete();
    }
    
    @Test
    public void takeTime() {
        Flux<String> takeFlux = Flux.just("one" , "two" , "three" , "four" , "five")
                                    .delayElements(Duration.ofSeconds(1))
                                    .take(Duration.ofMillis(3500));
        
        
        
        StepVerifier.create(takeFlux)
                    .expectNext("one" , "two" , "three" )
                    .verifyComplete();
    }
    
    @Test
    public void filter() {
        Flux<String> filterFlux = Flux.just("one" , "two" , "three" , "four" , "five")
                                    .filter( num -> !num.contains("o"));
        
        StepVerifier.create(filterFlux)
                    .expectNext("three" , "five" )
                    .verifyComplete();
        
    }
    
    @Test
    public void distinct() {
        Flux<String> distinctFlux = Flux.just("one" , "two" , "three" , "one" , "three" )
                                      .distinct();
        
        StepVerifier.create(distinctFlux)
                    .expectNext("one" , "two" , "three")
                    .verifyComplete();
        
    }

}
