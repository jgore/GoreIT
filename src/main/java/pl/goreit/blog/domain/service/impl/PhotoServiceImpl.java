package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.goreit.blog.domain.model.Photo;
import pl.goreit.blog.domain.service.PhotoService;
import pl.goreit.blog.infrastructure.mongo.ImageRepo;

import java.util.List;
import java.util.UUID;

@Service

public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private ImageRepo imageRepo;

    @Override
    public List<Photo> findByTransactionId(UUID transactionId) {
        return imageRepo.findByTransactionId(transactionId);
    }

    @Override
    public List<Photo> findByUser(String userId) {
        return imageRepo.findByUserId(userId);
    }

    @Override
    public Photo create(UUID transactionId, String name, byte[] imageByte) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Photo photo = new Photo(transactionId, userId, name, null, imageByte);
        return imageRepo.save(photo);
    }
}
