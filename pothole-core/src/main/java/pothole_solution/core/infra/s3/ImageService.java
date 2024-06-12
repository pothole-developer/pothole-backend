package pothole_solution.core.infra.s3;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(MultipartFile image, String dirName);

    void deleteImage(String imageName);

    String updateImage(String imageName, MultipartFile image, String dirName);
}
