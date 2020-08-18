package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.CategoryResponse;
import pl.goreit.blog.domain.CategoryName;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @ApiOperation(value = "pobiera  categorie")
    public List<CategoryResponse> getCategories() throws DomainException {
        return categoryService.findAll();
    }

    @GetMapping("/byName/{name}")
    @ApiOperation(value = "pobiera drzewo categori", notes = "pobiera drzewo categori")
    public CategoryResponse getCategoriesTree(@RequestParam CategoryName categoryName) throws DomainException {
        return categoryService.findByName(categoryName.name());
    }
}
