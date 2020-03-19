package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.CategoryResponse;
import pl.goreit.blog.domain.DomainException;

import java.util.List;

public interface CategoryService {
    CategoryResponse findByName(String name) throws DomainException;

    List<CategoryResponse> findAll();
}
