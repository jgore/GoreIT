package pl.goreit.blog.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.Photo;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepo extends MongoRepository<Photo, String> {
    List<Photo> findByTransactionId(UUID transactionId);
    List<Photo> findByUserId(String userId);
}
