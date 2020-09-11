package pl.goreit.blog.domain.service;

import pl.goreit.blog.domain.model.Photo;
import java.util.List;
import java.util.UUID;

public interface PhotoService {

    List<Photo> findByTransactionId(UUID transactionId);
    List<Photo> findByUser(String userId);

    Photo create(UUID transactionId, String name, byte[] imageByte);
}
