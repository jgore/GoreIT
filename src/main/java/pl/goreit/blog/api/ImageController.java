package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.model.Image;
import pl.goreit.blog.domain.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/photos")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "add new photos")
    public Image addImages(@RequestParam UUID transactionId,
                           @RequestParam("photos[]") MultipartFile photo) throws DomainException, IOException {

        return imageService.create(transactionId, photo.getBytes());
    }

    @GetMapping(value = "")
    @ApiOperation(value = "find all by userId")
    public List<Image> getAllByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return imageService.findByUser(userId);
    }

}
