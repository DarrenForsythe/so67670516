package com.darrenforsythe.so67670516;

import java.util.function.Function;

import io.netty.handler.logging.LogLevel;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class So67670516Application {

  public static void main(String[] args) {
    SpringApplication.run(So67670516Application.class, args);
  }

  @Bean
  ApplicationRunner runner(WebClient.Builder wcb) {

    return args -> {
      wcb.clientConnector(
              new ReactorClientHttpConnector(
                  HttpClient.create()
                      .wiretap(
                          "reactor.netty.http.client.HttpClient",
                          LogLevel.INFO,
                          AdvancedByteBufFormat.HEX_DUMP)))
          .defaultHeader(
              HttpHeaders.CONTENT_TYPE,
              MediaType.APPLICATION_JSON_VALUE,
              HttpHeaders.ACCEPT,
              MediaType.APPLICATION_JSON_VALUE)
          .build()
          .get()
          .uri("http://localhost:8080")
          .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
          .subscribe(System.out::println);
    };
  }
}
