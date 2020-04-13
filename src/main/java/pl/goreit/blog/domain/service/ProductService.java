package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.ProductResponse;
import pl.goreit.blog.domain.DomainException;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();

    ProductResponse findByTitle(String id) throws DomainException;

    ProductResponse addComment(String userId, String productTitle, String text) throws DomainException;
}
