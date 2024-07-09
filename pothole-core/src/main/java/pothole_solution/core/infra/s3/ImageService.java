package pothole_solution.core.infra.s3;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String uploadImage(MultipartFile image, Long id, String progressStatus);

    List<String> uploadImages(List<MultipartFile> images, Long id, String progressStatus);

    void deleteImage(String imageName);

    String updateImage(String imageName, MultipartFile image, Long id, String progressStatus);
}
