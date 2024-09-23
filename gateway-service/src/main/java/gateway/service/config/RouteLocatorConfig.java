package gateway.service.config;



import gateway.service.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class RouteLocatorConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;  // Inject the filter

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Route for auth-service
            .route("auth-service", r -> r.path("/auth/**")
                .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                        .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                        .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                        .addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
//                        .circuitBreaker(config -> config.setName("authServiceCircuitBreaker")
//                                .setFallbackUri("forward:/contactSupport"))
                        //.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                         //       .setKeyResolver(userKeyResolver())
                      //  )
                )

                .uri("lb://auth-service"))

            // Route for slack-bot-service
            .route("slack-bot-service", r -> r.path("/slack-bot/**")
                .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                        .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                        .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                        .addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
                        //.circuitBreaker(config -> config.setName("slackBotServiceCircuitBreaker"))
                        .retry(retryConfig -> retryConfig.setRetries(5)
                                .setMethods(HttpMethod.GET)
                                .setBackoff(Duration.ofMillis(1000),Duration.ofMillis(10000),2,true)
                        )

                )
                .uri("lb://slack-bot-service"))

            // Route for slack-subscription-service
            .route("slack-subscription-service", r -> r.path("/subscription/**")
                .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                        .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                        .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                        .addRequestHeader("X-Response-Time", LocalDateTime.now().toString()))
                .uri("lb://slack-subscription-service"))

            .build();
    }

//    @Bean
//    public RedisRateLimiter redisRateLimiter(){
//        return new RedisRateLimiter(100, 100, 1);
//    }

//    @Bean
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
//                .defaultIfEmpty("anonymous");
//    }

}
