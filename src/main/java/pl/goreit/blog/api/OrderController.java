package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.service.OrderService;


@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    @ApiOperation(value = "get order by id")
    public OrderResponse getOrder(@PathVariable("id") String id) throws DomainException {
        return orderService.findById(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "add new order")
    public OrderResponse addOrder(@RequestParam("userId") String userId, @RequestBody CreateOrderRequest orderRequest) {
        return orderService.create(userId, orderRequest);
    }
}
