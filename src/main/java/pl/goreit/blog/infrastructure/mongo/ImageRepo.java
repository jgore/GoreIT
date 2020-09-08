package pl.goreit.blog.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.Image;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepo extends MongoRepository<Image, String> {
    List<Image> findByTransactionId(UUID transactionId);
    List<Image> findByUserId(String userId);
}
