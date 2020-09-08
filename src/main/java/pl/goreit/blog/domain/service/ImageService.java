package pl.goreit.blog.domain.service;

import pl.goreit.blog.domain.model.Image;
import java.util.List;
import java.util.UUID;

public interface ImageService {

    List<Image> findByTransactionId(UUID transactionId);
    List<Image> findByUser(String userId);

    Image create(UUID transactionId, byte[] imageByte);
}
