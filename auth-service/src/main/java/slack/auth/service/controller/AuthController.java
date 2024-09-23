package slack.auth.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/token")
    public String getToken() {
        System.out.println("request is landed on port : " + port);
        return "token successful executed";
    }

    @PostMapping("/get-roles")
    public String getRoles() {
        System.out.println("request is landed on port : " + port);
        return "Get Roles successful executed";
    }


}
