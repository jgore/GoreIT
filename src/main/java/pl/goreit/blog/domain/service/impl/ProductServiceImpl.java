package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.api.generated.OrderlineView;
import pl.goreit.api.generated.ProductResponse;
import pl.goreit.api.generated.product_api.CreateProductRequest;
import pl.goreit.blog.domain.CategoryName;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.model.Comment;
import pl.goreit.blog.domain.model.Product;
import pl.goreit.blog.domain.service.ProductService;
import pl.goreit.blog.infrastructure.mongo.ProductRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ConversionService sellConversionService;

    @Autowired
    private ProductRepo productRepo;


    @Override
    public List<ProductResponse> getAllByCategory(CategoryName categoryName) {
        return productRepo.findByCategoryName(categoryName).stream()
                .map(product -> sellConversionService.convert(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findByTitle(String title) throws DomainException {
        Product product = productRepo.findByTitle(title).orElseThrow(() -> new DomainException(ExceptionCode.GOREIT_01));
        return sellConversionService.convert(product, ProductResponse.class);
    }

    @Override
    public ProductResponse add(CreateProductRequest createProductRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sellerId = authentication.getName();

        CreateProductRequest.CategoryName categoryName = createProductRequest.getCategoryName();

        //@FIXME allow add photo album to product
        Product product = new Product(CategoryName.valueOf(categoryName.value()),
                sellerId,
                createProductRequest.getTitle(),
                createProductRequest.getText(), createProductRequest.getPrice(), createProductRequest.getQuantity(),null);
        productRepo.save(product);
        return sellConversionService.convert(product, ProductResponse.class);
    }

    @Override
    public ProductResponse addComment(String userId, String productTitle, String text) throws DomainException {
        Product product = productRepo.findByTitle(productTitle).orElseThrow(() -> new DomainException(ExceptionCode.GOREIT_01));

        Integer sequenceNo = 0;
        if (product.getComments().size() > 0) {
            sequenceNo = product.getComments().get(product.getComments().size() - 1).getSequenceNo();
        }

        product.addComment(new Comment(sequenceNo + 1, userId, text));
        Product saved = productRepo.save(product);
        return sellConversionService.convert(saved, ProductResponse.class);
    }

    @Override
    public void updateAfterOrdered(OrderResponse orderResponse) {
        for (OrderlineView orderlineView : orderResponse.getOrderlineViews()) {
            String productTitle = orderlineView.getProductTitle();
            Integer amount = orderlineView.getAmount();
            Product product = productRepo.findByTitle(productTitle).get();
            product.updateInventoryState(amount);

            product.updateBoughtBy(orderResponse.getUserId());

            productRepo.save(product);
        }

    }
}
