package pl.goreit.blog.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import pl.goreit.blog.domain.model.Order;

import java.util.Optional;

@Component
public interface OrderRepo extends MongoRepository<Order, String> {

    Optional<Order> findByUserId(String userId);

}
