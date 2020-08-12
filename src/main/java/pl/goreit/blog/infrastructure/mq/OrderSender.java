package pl.goreit.blog.infrastructure.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.goreit.blog.domain.converter.OrderConverter;

import static pl.goreit.blog.configuration.config.RabbitConfig.ORDER_Q;

@Component
public class OrderSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderConverter orderConverter;

    public OrderSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String order) {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(ORDER_Q, order);
    }

}