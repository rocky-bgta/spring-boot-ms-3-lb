/*
package gateway.service.config;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Configuration
public class EurekaConfigForAwsEC2 {

    private static final String EC2_METADATA_URL = "http://169.254.169.254/latest/meta-data/local-ipv4";

    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        String privateIp = getPrivateIpFromEc2Metadata();

        if (privateIp != null) {
            config.setIpAddress(privateIp);
            config.setPreferIpAddress(true);
            System.out.println("Private IP set for Eureka registration: " + privateIp);
        } else {
            System.err.println("Could not retrieve private IP from EC2 metadata.");
        }

        return config;
    }

    private String getPrivateIpFromEc2Metadata() {
        try {
            URL url = new URL(EC2_METADATA_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String privateIp = in.readLine();
            in.close();

            return privateIp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
*/
