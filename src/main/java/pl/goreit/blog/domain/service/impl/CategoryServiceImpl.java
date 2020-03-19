package pl.goreit.blog.domain.service.impl;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.CategoryResponse;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.model.Category;
import pl.goreit.blog.domain.service.CategoryService;
import pl.goreit.blog.instrastructure.mongo.CategoryRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ConversionService sellConversionService;

    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(ConversionService sellConversionService, CategoryRepo categoryRepo) {
        this.sellConversionService = sellConversionService;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public CategoryResponse findByName(String name) throws DomainException {
        Category category = categoryRepo.findByName(name)
                .orElseThrow(() -> new DomainException(ExceptionCode.FOR_SELL_02));

        return sellConversionService.convert(category, CategoryResponse.class);

    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepo.findAll().stream()
                .map(category -> sellConversionService.convert(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }
}
