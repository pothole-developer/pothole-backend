package pothole_solution.core.infra.s3;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pothole_solution.core.global.exception.CustomException.*;

@Service
@RequiredArgsConstructor
public class AmazonS3Service implements ImageService {
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadImage(MultipartFile image, String dirName)  {
        if (image == null || image.isEmpty()) {
            throw INVALID_POTHOLE_IMG;
        }

        // 클라이언트가 전송한 파일 이름
        String getOriginalFilename = image.getOriginalFilename();

        if (getOriginalFilename == null || getOriginalFilename.isBlank()) {
            throw INVALID_POTHOLE_IMG_NAME;
        }

        // 파일 이름의 빈 칸 or Tab -> _ 로 변경
        String originalFilename = getOriginalFilename.replaceAll("\\s", "_");

        // 파일 확장자
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);

        // 파일 이름 중복 방지
        // 파일 수정 및 삭제할 때 아래의 값을 통해 해야 함
        // 아래의 값 예시) thumbnail/8826a18a-5359-429c-8870-69db63615ad3_pothole_img_test.jpeg
        String imageName = dirName + "/" + UUID.randomUUID() + "_" + originalFilename;

        ObjectMetadata objectMetadata = ObjectMetadata.builder().contentType(fileExtension).build();

        try {
            S3Resource s3Resource = s3Template.upload(bucket, imageName, image.getInputStream(), objectMetadata);

            return s3Resource.getURL().toString();

        } catch (IOException e) {
            throw FAILED_UPLOAD;
        }
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> images, String dirName)  {
        // 만약 여러 개 중 한 개를 실패했을 때, Exception을 Throw 후 종료되므로 그 뒤에 정상적인 파일이 있다하더라도 S3에 업로드 되지 않고 종료됨.
        if (images == null || images.isEmpty()) {
            throw INVALID_POTHOLE_IMG;
        }

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            String getOriginalFilename = image.getOriginalFilename();

            if (getOriginalFilename == null || getOriginalFilename.isBlank()) {
                throw INVALID_POTHOLE_IMG_NAME;
            }

            String originalFilename = getOriginalFilename.replaceAll("\\s", "_");

            String fileExtension = StringUtils.getFilenameExtension(originalFilename);

            String imageName = dirName + "/" + UUID.randomUUID() + "_" + originalFilename;

            ObjectMetadata objectMetadata = ObjectMetadata.builder().contentType(fileExtension).build();

            try {
                S3Resource s3Resource = s3Template.upload(bucket, imageName, image.getInputStream(), objectMetadata);

                imageUrls.add(s3Resource.getURL().toString());

            } catch (IOException e) {
                throw FAILED_UPLOAD;
            }

        }

        return imageUrls;
    }

    @Override
    public void deleteImage(String imageName) {
        s3Template.deleteObject(bucket, getImageKey(imageName));
    }

    @Override
    public String updateImage(String oldImageName, MultipartFile newImage, String dirName) {
        deleteImage(oldImageName);

        return uploadImage(newImage, dirName);
    }

    private String getImageKey(String imageName) {
        try {
            URI imageUri = new URI(imageName);

            String path = imageUri.getPath();

            return path.substring(1);

        } catch (URISyntaxException e) {
            throw INVALID_POTHOLE_IMG_URL;
        }
    }
}
