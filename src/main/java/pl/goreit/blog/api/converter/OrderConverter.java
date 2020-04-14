package pl.goreit.blog.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.api.generated.OrderlineView;
import pl.goreit.blog.domain.model.Order;

import java.util.stream.Collectors;

@Component
public class OrderConverter implements Converter<Order, OrderResponse> {

    @Override
    public OrderResponse convert(Order order) {
        return new OrderResponse(order.getId(), order.getCreationTime().toString(),
                order.getOrderLines().stream()
                        .map(orderProduct -> new OrderlineView(orderProduct.getProductTitle(), orderProduct.getAmount(), orderProduct.getPrice()))
                        .collect(Collectors.toList()));
    }
}
