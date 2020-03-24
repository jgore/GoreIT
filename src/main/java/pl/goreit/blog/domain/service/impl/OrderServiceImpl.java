package pl.goreit.blog.domain.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderLineRequest;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.blog.api.converter.OrderConverter;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.model.Order;
import pl.goreit.blog.domain.model.OrderLine;
import pl.goreit.blog.domain.model.Product;
import pl.goreit.blog.domain.service.OrderService;
import pl.goreit.blog.infrastructure.mongo.OrderRepo;
import pl.goreit.blog.infrastructure.mongo.ProductRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public OrderResponse findById(String id) throws DomainException {
        Order order = orderRepo.findById(id).orElseThrow(() -> new DomainException(ExceptionCode.GOREIT_04));
        return orderConverter.convert(order);
    }

    @Override
    public OrderResponse findByUserId(String userId) throws DomainException {
        Order order = orderRepo.findByUserId(userId).orElseThrow(() -> new DomainException(ExceptionCode.GOREIT_04));
        return orderConverter.convert(order);
    }

    @Override
    public OrderResponse create(String userId, CreateOrderRequest orderRequest) {
        ObjectId orderId = ObjectId.get();

        List<OrderLineRequest> orderLineViews = orderRequest.getOrderLines();

        List<OrderLine> orderLines = orderLineViews.stream()
                .map(orderLineView -> {

                    //@FIXME get all upper
                    Product product = productRepo.findByTitle(orderLineView.getProductTitle()).get();
                    return new OrderLine(orderId.toString(), product.getTitle(), orderLineView.getAmount(), product.getPrice());
                })
                .collect(Collectors.toList());

        Order order = new Order(orderId.toString(), userId, orderLines, LocalDateTime.now());

        return orderConverter.convert(orderRepo.save(order));
    }
}
