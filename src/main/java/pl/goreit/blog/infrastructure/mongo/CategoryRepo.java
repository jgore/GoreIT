package pl.goreit.blog.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepo extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);

}
