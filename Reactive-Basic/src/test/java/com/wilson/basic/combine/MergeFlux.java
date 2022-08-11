package com.wilson.basic.combine;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

public class MergeFlux {
    
    @Test
    public void mergerFlux() {
        Flux<String> personFlux = Flux.just("Wilson","Jack","Mike")
                                      .delayElements(Duration.ofMillis(500));
        
        Flux<String> foodFlux = Flux.just("Apples", "Banana" , "Mongo")
                                    .delaySequence(Duration.ofMillis(250))
                                    .delayElements(Duration.ofMillis(500));
        
        Flux<String> mergeFlux = personFlux.mergeWith(foodFlux);
        
        StepVerifier.create(mergeFlux)
                    .expectNext("Wilson")
                    .expectNext("Apples")
                    .expectNext("Jack")
                    .expectNext("Banana")
                    .expectNext("Mike")
                    .expectNext("Mongo")
                    .verifyComplete();
    }
    
    
    @Test
    public void zipFluxes() {
        Flux<String> personFlux = Flux.just("Wilson","Jack","Mike");
        
        Flux<String> foodFlux = Flux.just("Apples", "Banana" , "Mongo");
        
        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(personFlux, foodFlux);
        
        StepVerifier.create(zippedFlux)
                    .expectNextMatches( p -> 
                            p.getT1().equals("Wilson") && p.getT2().equals("Apples"))
                    .expectNextMatches( p -> 
                            p.getT1().equals("Jack") && p.getT2().equals("Banana"))
                     .expectNextMatches( p -> 
                            p.getT1().equals("Mike") && p.getT2().equals("Mongo"))
                    .verifyComplete();
        
    }
    
    @Test
    public void zipFluxesToObject() {
        Flux<String> personFlux = Flux.just("Wilson","Jack","Mike");
        
        Flux<String> foodFlux = Flux.just("Apples", "Banana" , "Mongo");
        
        Flux<String> zippedFlux = Flux.zip(personFlux, foodFlux , (p , f) -> p + " eats " + f);
        
        StepVerifier.create(zippedFlux)
                    .expectNext("Wilson eats Apples")
                    .expectNext("Jack eats Banana")
                    .expectNext("Mike eats Mongo")
                    .verifyComplete();
        
    }
    
    @Test
    public void firstFlux() {
        Flux<String> slowFlux = Flux.just("Wilson","Jack","Mike")
                                    .delaySubscription(Duration.ofMinutes(100));
        
        Flux<String> fastFlux = Flux.just("Apples", "Banana" , "Mongo");
        
        Flux<String> firstFlux = Flux.firstWithSignal(slowFlux,fastFlux);
        
        StepVerifier.create(firstFlux)
                    .expectNext("Apples")
                    .expectNext("Banana")
                    .expectNext("Mongo")
                    .verifyComplete();
        
    }

}
