package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderLineRequest;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.blog.domain.CategoryName;
import pl.goreit.blog.domain.model.Category;
import pl.goreit.blog.domain.model.Product;
import pl.goreit.blog.domain.service.OrderService;
import pl.goreit.blog.infrastructure.mongo.CategoryRepo;
import pl.goreit.blog.infrastructure.mongo.ProductRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
@Profile("!prod")
public class TestHelperController {

    public static String TEST_PRODUCT = "TEST_PRODUCT";
    public static BigDecimal TEST_PRODUCT_PRICE = BigDecimal.valueOf(100);
    public static final String TEST_SUB_CATEGORY_NAME = "TEST_SUB_CATEGORY_NAME";

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private OrderService orderService;

    @PostMapping("addOrder")
    @ApiOperation(value = "add order")
    public OrderResponse addOrder(@RequestParam("userId") String userId,
                         @RequestParam("orderProductNumber") Integer orderProductNumber,
                         @RequestParam("amount") Integer amount) {

        Product korepetycje0 = productRepo.findByTitle("korepetycje").get();
        List<OrderLineRequest> orderLineRequests = new ArrayList<>();

        for (int i = 0; i < orderProductNumber; i++) {
            Product product = korepetycje0;
            OrderLineRequest orderProductView = new OrderLineRequest(product.getTitle(), amount);
            orderLineRequests.add(orderProductView);
        }

        return orderService.create(userId, new CreateOrderRequest(orderLineRequests));
    }


    @GetMapping("/addProduct/")
    @ApiOperation(value = "add 100 products")
    public void addProducts(@RequestParam("amount") Integer amount) {

        for (int count = 0; count < amount; count++) {
            Product product = new Product("korepetycje" , "Pomoc w programowaniu", BigDecimal.valueOf(150));
            productRepo.save(product);
        }


    }

    @GetMapping("/addCategories/")
    @ApiOperation(value = "add ALL categories")
    public void addCategories() {

        List<CategoryName> categoryNames = Arrays.asList(CategoryName.values());

        categoryNames.forEach(categoryName -> {
            Category category = new Category(categoryName.name());
            categoryRepo.save(category);
        });

    }


}
