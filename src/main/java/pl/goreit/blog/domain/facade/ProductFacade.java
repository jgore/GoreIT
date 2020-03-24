package pl.goreit.blog.domain.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.product_api.ImportedProduct;
import pl.goreit.blog.domain.model.Product;
import pl.goreit.blog.domain.provider.ProductProvider;
import pl.goreit.blog.infrastructure.mongo.ProductRepo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductFacade {

    @Autowired
    private ProductProvider productProvider;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ConversionService goreITConversionService;

    @Scheduled(fixedRate = 10000)
    public void importProducts() {
        List<ImportedProduct> importedProducts = productProvider.provideProducts();
        List<Product> products = importedProducts.stream()
                .map(importedProduct -> goreITConversionService.convert(importedProduct, Product.class))
                .collect(Collectors.toList());

        productRepo.saveAll(products);
    }
}
