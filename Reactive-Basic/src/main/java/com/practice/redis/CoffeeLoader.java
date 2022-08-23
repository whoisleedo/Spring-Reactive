package com.practice.redis;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import com.practice.entity.Coffee;

import reactor.core.publisher.Flux;

@Component
public class CoffeeLoader {
    
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Coffee> coffeeOps;
    private static AtomicInteger id = new AtomicInteger(0);

    public CoffeeLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, Coffee> coffeeOps) {
      this.factory = factory;
      this.coffeeOps = coffeeOps;
    }

    @PostConstruct
    public void loadData() {
      factory.getReactiveConnection().serverCommands().flushAll().thenMany(
          Flux.just("CAFFÈ LATTE", "CAFFÈ MOCHA", "CAPPUCCINO")
              .map(name -> new Coffee(Integer.toString(id.incrementAndGet()), name))
              .flatMap(coffee -> coffeeOps.opsForValue().set(coffee.getId(), coffee)))
          .thenMany(coffeeOps.keys("*")
              .flatMap(coffeeOps.opsForValue()::get))
          .subscribe(System.out::println);
    }

}
