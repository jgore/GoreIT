package pl.goreit.blog.configuration.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String ORDER_Q = "orderQ";

    @Bean
    public Queue orderQ() {
        return new Queue(ORDER_Q, false);
    }

}