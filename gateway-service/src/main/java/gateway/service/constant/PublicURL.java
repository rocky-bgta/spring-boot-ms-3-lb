package gateway.service.constant;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public final class PublicURL {

    // list of non secured api
    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/register-users",
            "/auth/token",
            "/auth/get-roles",
            "/auth/signing-out",
            "/eureka",
            "/slack-bot/slack/login",
            "/slack-bot/slack/oauth/callback",
            "/subscription/api/v1/package",
            "/slack-bot/api/v1/slack-organization/point-transaction",
            "/slack-bot/api/v1/organization-attendance-plugin-activation",
            "/slack-bot/woocommerce-redeem-shop",
            "/bot-info"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
