package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.model.Photo;
import pl.goreit.blog.domain.service.PhotoService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "add new photo")
    public Photo AddPhoto(@RequestParam UUID transactionId,
                          @RequestParam String name,
                          @RequestParam("photo") MultipartFile photo) throws DomainException, IOException {

        return photoService.create(transactionId, name, photo.getBytes());
    }

    @GetMapping()
    @ApiOperation(value = "find all by userId")
    public List<Photo> getAllByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return photoService.findByUser(userId);
    }

}
