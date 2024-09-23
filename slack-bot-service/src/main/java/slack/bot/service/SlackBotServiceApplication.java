package slack.bot.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SlackBotServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlackBotServiceApplication.class, args);
	}

}
