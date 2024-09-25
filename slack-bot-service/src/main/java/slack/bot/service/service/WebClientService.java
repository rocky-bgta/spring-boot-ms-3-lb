package slack.bot.service.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class WebClientService {

    private final WebClient webClient;

    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // Generic method to make a blocking GET request
    public <T> T makeBlockingGetRequest(String url, Class<T> responseType, Map<String, String> headers) {
        try {
            WebClient.RequestHeadersSpec<?> request = webClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON); // Expecting JSON response

            // Add custom headers if provided
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.header(header.getKey(), header.getValue());
                }
            }

            // Perform the GET request and block for the result
            return request
                    .retrieve()
                    .bodyToMono(responseType) // Convert response to the expected type
                    .block();  // Make the call blocking

        } catch (RuntimeException e) {
            throw new RuntimeException("Error occurred during GET request to: " + url, e);
        }
    }
}
