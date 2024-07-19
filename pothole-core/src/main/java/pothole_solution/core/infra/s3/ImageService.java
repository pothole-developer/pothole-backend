package pothole_solution.core.infra.s3;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String uploadImage(MultipartFile image, String dirName);

    List<String> uploadImages(List<MultipartFile> images, String dirName);

    void deleteImage(String imageName);

    String updateImage(String imageName, MultipartFile image, String dirName);
}
