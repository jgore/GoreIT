package pl.goreit.blog.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.CategoryResponse;
import pl.goreit.blog.domain.model.Category;

@Component
public class CategoryConverter implements Converter<Category, CategoryResponse> {

    @Override
    public CategoryResponse convert(Category source) {
        return new CategoryResponse(source.getName());
    }
}
