package pl.goreit.blog.infrastructure.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.OrderResponse;

import static pl.goreit.blog.configuration.config.RabbitConfig.ORDER_Q;

@Component
public class OrderReceiver {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = ORDER_Q)
    public void listen(String in) throws JsonProcessingException {
        System.out.println("Message read from order_q : " + in);
        OrderResponse orderResponse = objectMapper.readValue(in, OrderResponse.class);

        System.out.println(orderResponse);
    }

}