package com.nagarro.nagp.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

//        @Autowired
//    private RestTemplate template;
        
    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
//            if (validator.isSecured.test(exchange.getRequest())) {
//                //header contains token or not
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                	return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
//                }
//
//                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//                if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                    authHeader = authHeader.substring(7);
//                }
//                try {
//                    //REST call to AUTH service
////                	String[] credentials = authHeader.split(" ");
////                	UserCredentials uc = new UserCredentials(credentials[0], credentials[1]);
////                	System.out.println(uc.getUsername());
////                    template.postForObject("http://IDENTITY-SERVICE/users/login", uc, String.class);
//                    if(!authHeader.equals("validToken123")) {
//                    	return this.onError(exchange, "Authorization Failed", HttpStatus.UNAUTHORIZED);
//                    }
//                } catch (Exception e) {
//                    System.out.println("invalid access...!");
//                    System.out.println(e.getMessage());
//                    return this.onError(exchange, "Authorization Failed", HttpStatus.UNAUTHORIZED);
//                }
//            }
        	
            return chain.filter(exchange);
        });
    }
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config {

    }
}
