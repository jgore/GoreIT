package pl.goreit.blog.domain.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.goreit.blog.domain.converter.OrderConverter;
import pl.goreit.blog.domain.model.Order;

import static pl.goreit.blog.configuration.config.RabbitConfig.ORDER_Q;

@Component
public class MqSenderService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderConverter orderConverter;


    public MqSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendOrder(Order order) throws JsonProcessingException {
        String jsonOrder = objectMapper.writeValueAsString(orderConverter.convert(order));
        System.out.println("Sending -> " + jsonOrder);
        rabbitTemplate.convertAndSend(ORDER_Q, jsonOrder);
    }
}
