package com.wilson.basic.trans;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;

import com.practice.Player;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class MapReactive {
    
    @Test
    public void map() {
        Flux<Player> playerFlux = 
                Flux.just("Michael Jordan" , "Scottie Pippen" , "Steve Kerr")
                    .map(name -> {
                        String[] split = name.split("\\s");
                        return new Player(split[0] , split[1]);
                    });
        
        StepVerifier.create(playerFlux)
                    .expectNext(new Player("Michael", "Jordan"))
                    .expectNext(new Player("Scottie", "Pippen"))
                    .expectNext(new Player("Steve", "Kerr"))
                    .verifyComplete();
                    
        
    }
    
    @Test
    public void flatMap() {
        Flux<Player> playerFlux = Flux.just("Michael Jordan" , "Scottie Pippen" , "Steve Kerr")
                                      .flatMap( n -> Mono.just(n)
                                            .map( p ->{
                                                String[] split = p.split("\\s");
                                                return new Player(split[0] , split[1]);
                                                })
                                            .subscribeOn(Schedulers.parallel())
                                            );
        
        List<Player> playerList = Arrays.asList(
                                          new Player("Michael", "Jordan"),
                                          new Player("Scottie", "Pippen"),
                                          new Player("Steve", "Kerr"));
        
        StepVerifier.create(playerFlux)
                    .expectNextMatches( p -> playerList.contains(p))
                    .expectNextMatches( p -> playerList.contains(p))
                    .expectNextMatches( p -> playerList.contains(p))
                    .verifyComplete();
    }

}
