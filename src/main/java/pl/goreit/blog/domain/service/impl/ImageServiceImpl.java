package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.goreit.blog.domain.model.Image;
import pl.goreit.blog.domain.service.ImageService;
import pl.goreit.blog.infrastructure.mongo.ImageRepo;

import java.util.List;
import java.util.UUID;

@Service

public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepo imageRepo;

    @Override
    public List<Image> findByTransactionId(UUID transactionId) {
        return imageRepo.findByTransactionId(transactionId);
    }

    @Override
    public List<Image> findByUser(String userId) {
        return imageRepo.findByUserId(userId);
    }

    @Override
    public Image create(UUID transactionId, byte[] imageByte) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        int size = findByTransactionId(transactionId).size()+1;
        Image image = new Image(transactionId, userId, String.valueOf(size), null, imageByte);
        return imageRepo.save(image);
    }
}
