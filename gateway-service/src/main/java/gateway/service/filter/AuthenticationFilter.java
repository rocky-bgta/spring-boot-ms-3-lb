package gateway.service.filter;


import gateway.service.constant.PublicURL;
import gateway.service.service.WebClientAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private PublicURL publicURL;


    @Autowired
    WebClientAuthorizationService webClientAuthorizationService;


    //@Autowired
    //private AuthService authService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (publicURL.isSecured.test(exchange.getRequest())) {
                // header contains token or not
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    throw new RuntimeException("Missing authorization header");
//                }

               // String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                //String token;
               // if (authHeader != null ) {
               //     token = authHeader;

                    // Call to authorizationService in a reactive way using flatMap
                    return webClientAuthorizationService.validateToken("token")
                            .flatMap(authResponse -> {
                                // Get the request path
                                String requestPath = exchange.getRequest().getURI().getPath();

                                //TODO need to un comment isAuthorized section later ===
                                // Check if the roles for the request URL are present using PrivateURL
//                                boolean isAuthorized = PrivateURL.areAllRolesPresent(requestPath, authResponse.getRoles());
//
//                                if (!isAuthorized) {
//                                    return Mono.error(new RuntimeException("Unauthorized access to path: " + requestPath));
//                                }
                                // =====================================================
                                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                        .header("X-User-Name", "authResponse.getUser().getUsername()")
                                        .build();

                                //log.info("Adding header X-User-Name: {}", authResponse.getUser().getUsername());

                                // Use the mutated request in the exchange
                                ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();


                                // Proceed to the next filter in the chain if authorized
                                return chain.filter(mutatedExchange);
                            })
                            .onErrorResume(e -> {
                                // Handle errors, return appropriate response
                                return Mono.error(new RuntimeException("Unauthorized access to application"));
                            });
                }
           // }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
