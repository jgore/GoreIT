package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.goreit.blog.domain.CategoryName;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.model.Category;
import pl.goreit.blog.domain.model.Product;
import pl.goreit.blog.instrastructure.mongo.CategoryRepo;
import pl.goreit.blog.instrastructure.mongo.ProductRepo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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


    @GetMapping("/addProduct/")
    @ApiOperation(value = "add 100 products")
    public void addProducts(@RequestParam("amount") Integer amount) {

        for (int count = 0; count < amount; count++) {
            Product product = new Product("product " + count, "Example text", BigDecimal.valueOf(count + 100));
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
