package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.ProductResponse;
import pl.goreit.api.generated.product_api.CreateProductRequest;
import pl.goreit.blog.domain.CategoryName;
import pl.goreit.blog.domain.DomainException;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllByCategory(CategoryName categoryName);

    ProductResponse findByTitle(String id) throws DomainException;

    ProductResponse add(CreateProductRequest createProductRequest);

    ProductResponse addComment(String userId, String productTitle, String text) throws DomainException;
}
