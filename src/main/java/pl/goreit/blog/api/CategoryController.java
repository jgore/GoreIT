package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.goreit.api.generated.CategoryResponse;
import pl.goreit.blog.domain.CategoryName;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @ApiOperation(value = "pobiera drzewo categori", notes = "pobiera drzewo categori")
    public CategoryResponse getCategoriesTree(@RequestParam CategoryName categoryName) throws DomainException {
        return categoryService.findByName(categoryName.name());
    }
}
