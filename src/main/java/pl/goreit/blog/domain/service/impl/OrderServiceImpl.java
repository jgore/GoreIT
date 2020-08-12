package pl.goreit.blog.domain.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderLineRequest;
import pl.goreit.api.generated.OrderResponse;
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
    private ConversionService sellConversionService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private MqSenderService mqSenderService;

    @Override
    public OrderResponse findById(String id) throws DomainException {
        Order order = orderRepo.findById(id).orElseThrow(() -> new DomainException(ExceptionCode.GOREIT_04));
        return sellConversionService.convert(order, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> findByUserId(String userId) {
        List<Order> ordersByUser = orderRepo.findByUserId(userId);
        return ordersByUser.stream()
                .map(order -> sellConversionService.convert(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse create(CreateOrderRequest orderRequest) throws DomainException {
        ObjectId orderId = ObjectId.get();

        List<OrderLineRequest> orderLineRequests = orderRequest.getOrderlines();

        if (orderLineRequests == null || orderLineRequests.isEmpty()) {
            throw new DomainException(ExceptionCode.GOREIT_06);
        }

        List<OrderLine> orderlines = orderLineRequests.stream()
                .map(orderLineView -> {

                    //@FIXME get all upper
                    Product product = productRepo.findByTitle(orderLineView.getProductTitle()).get();
                    return new OrderLine(orderId.toString(), product.getTitle(), orderLineView.getAmount(), product.getPrice());
                })
                .collect(Collectors.toList());

        Order order = new Order(orderId.toString(), orderRequest.getUserId(), orderlines, LocalDateTime.now());

        try {
            mqSenderService.sendOrder(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return sellConversionService.convert(orderRepo.save(order), OrderResponse.class);
    }
}
