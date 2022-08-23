package com.practice.redis;



import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import com.practice.entity.Coffee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CoffeeController {
    
    private final ReactiveRedisOperations<String, Coffee> coffeeOps;

    CoffeeController(ReactiveRedisOperations<String, Coffee> coffeeOps) {
      this.coffeeOps = coffeeOps;
    }

    @GetMapping("/coffees")
    public Flux<Coffee> all() {
      return coffeeOps.keys("*")
          .flatMap(coffeeOps.opsForValue()::get);
    }
    @GetMapping(path = "/coffee/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Coffee> getById(@PathVariable String id){
        
      return coffeeOps.opsForValue().get(id)
                      .flatMap(coffee -> Mono.just(coffee))
                      .switchIfEmpty(Mono.error(new DataNotFoundException(id)));

    }
    
    @GetMapping(path = "/coffeeTest/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Coffee>> getByIdTest(@PathVariable String id){
        
      return coffeeOps.opsForValue().get(id)
                      .flatMap(coffee -> Mono.just(new ResponseEntity<>(coffee , HttpStatus.OK)))
                      .switchIfEmpty(Mono.just(new ResponseEntity<>(null,HttpStatus.NOT_FOUND)));
                      

    }
    @Component
    @Order(-2)
    class RestWebExceptionHandler implements WebExceptionHandler {

        @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
            if (ex instanceof DataNotFoundException) {
                exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                // marks the response as complete and forbids writing to it
                return exchange.getResponse().setComplete();
            }
            return Mono.error(ex);
        }
    }

}
