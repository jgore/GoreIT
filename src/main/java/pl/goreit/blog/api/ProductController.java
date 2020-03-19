package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.ProductResponse;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ApiOperation(value = "pobiera wszystkie produkty", notes = "pobiera wszystkie produkty ")
    public List<ProductResponse> getProducts() {
        return productService.getAll();
    }

    @PostMapping("/comment")
    @ApiOperation(value = "Dodaje komentarz do produktu", notes = "dodaje komentarz")
    public ProductResponse addComment(@RequestParam("userId") String userId,
                              @RequestParam("title") String productTitle,
                              @RequestParam("text") String text) throws DomainException {
        return productService.addComment(userId, productTitle, text);
    }

}
