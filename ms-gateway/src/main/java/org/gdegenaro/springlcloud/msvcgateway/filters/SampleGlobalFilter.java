package org.gdegenaro.springlcloud.msvcgateway.filters;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
    public class SampleGlobalFilter implements WebFilter {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            // Lógica aquí
            return chain.filter(exchange);
        }
    }
