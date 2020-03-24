package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.product_api.ImportedProduct;
import pl.goreit.blog.domain.model.Product;

@Component
public class ImportedProductToProductConveter implements Converter<ImportedProduct, Product> {

    @Override
    public Product convert(ImportedProduct importedProduct) {
        return new Product(importedProduct.getTitle(), importedProduct.getText(), importedProduct.getPrice());
    }
}
