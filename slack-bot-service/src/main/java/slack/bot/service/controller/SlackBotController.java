package slack.bot.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SlackBotController {


    @Autowired
    RestTemplate restTemplate;

    @Value("${server.port}")
    private int port;

    @GetMapping("/bot-info")
    public String getBotInfo() {
        String result;
        result = restTemplate.getForObject("http://auth-service/auth/token" , String.class);
        System.out.println("Response from auth service: "+ result);
        System.out.println("request is landed on port : " + port);
        return "bot-info successful executed";
    }

}
