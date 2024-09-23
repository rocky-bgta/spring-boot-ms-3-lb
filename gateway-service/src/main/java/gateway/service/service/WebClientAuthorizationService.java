package gateway.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WebClientAuthorizationService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<String> validateToken(String token) {
        WebClient webClient = webClientBuilder.build();
        String url = "http://auth-service/auth/get-roles";

        // Send the POST request and return the Mono<AuthResponse> directly
        return webClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Auth Response: {}", response))
                .doOnError(error -> log.error("Error during token validation: ", error));
    }
}
