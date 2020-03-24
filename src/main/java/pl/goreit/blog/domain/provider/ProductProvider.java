package pl.goreit.blog.domain.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.goreit.api.generated.product_api.ImportedProduct;
import pl.goreit.api.generated.product_api.ImportedProductListResponse;
import pl.goreit.blog.configuration.timing.Timed;

import java.util.List;
import java.util.Objects;

@Component
public class ProductProvider {

    private static final String PRODUCT_API = "http://localhost:5001";

    @Autowired
    private RestTemplate restTemplate;

    @Timed
    public List<ImportedProduct> provideProducts() {
        ImportedProductListResponse importedProductListResponse = restTemplate.getForObject(PRODUCT_API + "/products", ImportedProductListResponse.class);
        return Objects.requireNonNull(importedProductListResponse).getProducts();
    }
}
