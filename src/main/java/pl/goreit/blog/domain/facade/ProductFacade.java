package pl.goreit.blog.domain.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.product_api.ImportedProduct;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.model.Product;
import pl.goreit.blog.domain.model.postgres.BatchProductImport;
import pl.goreit.blog.domain.provider.ProductProvider;
import pl.goreit.blog.infrastructure.mongo.ProductRepo;
import pl.goreit.blog.infrastructure.postgres.BatchProductImportRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Component
public class ProductFacade {

    @Autowired
    private ProductProvider productProvider;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ConversionService goreITConversionService;

    @Autowired
    private BatchProductImportRepo batchProductImportRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(fixedRate = 7000)
    public void importProducts() throws JsonProcessingException, InterruptedException, DomainException {

        ReentrantLock lock = new ReentrantLock();

        boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);

        if (!isLockAcquired) {
            throw new DomainException(ExceptionCode.GOREIT_05);
        }

        List<ImportedProduct> importedProducts = productProvider.provideProducts();

        String productsJson = objectMapper.writeValueAsString(importedProducts);
        batchProductImportRepo.save(new BatchProductImport(productsJson, LocalDateTime.now()));

        List<Product> products = importedProducts.stream()
                .map(importedProduct -> goreITConversionService.convert(importedProduct, Product.class))
                .collect(Collectors.toList());

        productRepo.saveAll(products);

        lock.unlock();

    }

}
