package pothole_solution.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.dto.PotholeFilterDto;
import pothole_solution.core.domain.pothole.dto.request.PotholeChangeProcessStatusRequestDto;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.repository.PotholeQueryDslRepository;
import pothole_solution.core.domain.pothole.repository.PotholeRepository;
import pothole_solution.core.infra.s3.AmazonS3Service;

import java.util.List;

import static pothole_solution.core.global.exception.CustomException.NOT_EXISTED_POTHOLE;

@Service
@Transactional
@RequiredArgsConstructor
public class PotholeManagerService {
    private final PotholeRepository potholeRepository;
    private final PotholeQueryDslRepository potholeQueryDslRepository;
    private final AmazonS3Service amazonS3Service;

    public Pothole register(Pothole pothole, MultipartFile potholeImg) {
        if (potholeImg != null) {
            String thumbnail = amazonS3Service.uploadImage(potholeImg, "thumbnail");

            pothole.createThumbnailURL(thumbnail);
        }

        return potholeRepository.save(pothole);
    }

    @Transactional(readOnly = true)
    public Pothole getPothole(Long id) {
        return findById(id);
    }

    @Transactional(readOnly = true)
    public List<Pothole> getAllPotholes() {
        return potholeRepository.findAll();
    }

    public Pothole changePotholeSimpleInfo(Long id, PotholeChangeProcessStatusRequestDto changeProcessStatusRequestDto, MultipartFile newImage) {
        Pothole pothole = findById(id);

        if (changeProcessStatusRequestDto != null) {
            pothole.changeProgress(changeProcessStatusRequestDto.getProcessStatus());
        }

        if (newImage != null) {
            String newImageUrl = amazonS3Service.updateImage(pothole.getThumbnail(), newImage, "thumbnail");

            pothole.createThumbnailURL(newImageUrl);
        }

        return pothole;
    }

    public void deletePothole(Long id) {
        Pothole pothole = findById(id);

        amazonS3Service.deleteImage(pothole.getThumbnail());

        potholeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Pothole> getFilteredPotholes(PotholeFilterDto potholeFilterDto) {
        Integer availableMinImportance = (potholeFilterDto.getMinImportance() == null) ? 0 : potholeFilterDto.getMinImportance();
        Integer availableMaxImportance = (potholeFilterDto.getMaxImportance() == null) ? 100 : potholeFilterDto.getMaxImportance();

        potholeFilterDto.changeToAvailableImportance(availableMinImportance, availableMaxImportance);

        return potholeQueryDslRepository.findByFilter(potholeFilterDto);
    }

    private Pothole findById(Long id) {
        return potholeRepository.findById(id)
                .orElseThrow(
                        () -> NOT_EXISTED_POTHOLE
                );
    }
}
