package pothole_solution.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pothole_solution.core.domain.pothole.dto.PotholeFilterDto;
import pothole_solution.core.domain.pothole.dto.request.ChangePotholeProgressStatusRequestDto;
import pothole_solution.core.domain.pothole.entity.Pothole;
import pothole_solution.core.domain.pothole.entity.PotholeHistory;
import pothole_solution.core.domain.pothole.entity.PotholeHistoryImage;
import pothole_solution.core.domain.pothole.repository.PotholeHistoryImageRepository;
import pothole_solution.core.domain.pothole.repository.PotholeHistoryRepository;
import pothole_solution.core.domain.pothole.repository.PotholeQueryDslRepository;
import pothole_solution.core.domain.pothole.repository.PotholeRepository;
import pothole_solution.core.infra.s3.ImageService;

import java.util.ArrayList;
import java.util.List;

import static pothole_solution.core.global.exception.CustomException.NONE_POTHOLE;

@Service
@Transactional
@RequiredArgsConstructor
public class PotholeManagerServiceImpl implements PotholeManagerService {
    private final PotholeRepository potholeRepository;
    private final PotholeHistoryRepository potholeHistoryRepository;
    private final PotholeHistoryImageRepository potholeHistoryImageRepository;
    private final PotholeQueryDslRepository potholeQueryDslRepository;
    private final ImageService imageService;

    @Override
    public Pothole registerPothole(Pothole pothole, List<MultipartFile> registerPotholeImages) {
        List<String> imageUrls = imageService.uploadImages(registerPotholeImages, pothole.getProcessStatus().getValue());
        pothole.createThumbnail(imageUrls.get(0));

        // Pothole 저장
        Pothole savedPothole = potholeRepository.save(pothole);

        // PotholeHistory 생성 및 저장
        PotholeHistory potholeHistory = new PotholeHistory(pothole.getProcessStatus(), savedPothole);
        PotholeHistory savedPotholeHistory = potholeHistoryRepository.save(potholeHistory);

        // PotholeHistoryImage 생성 및 저장
        for (String imageUrl : imageUrls) {
            PotholeHistoryImage potholeHistoryImage = new PotholeHistoryImage(savedPotholeHistory, imageUrl);
            potholeHistoryImageRepository.save(potholeHistoryImage);
        }

        return savedPothole;
    }

    @Override
    @Transactional(readOnly = true)
    public Pothole getPotholeByPotholeId(Long potholeId) {
        return findByPotholeId(potholeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pothole> getAllPotholes() {
        return potholeRepository.findAll();
    }

    @Override
    public Pothole changePotholeProgressStatus(Long potholeId, ChangePotholeProgressStatusRequestDto changePotholeProcessStatusRequestDto) {
        Pothole pothole = findByPotholeId(potholeId);

        List<PotholeHistory> potholeHistories = potholeHistoryRepository.findAllByPotholeId(potholeId);

        pothole.changeProgress(changePotholeProcessStatusRequestDto.getProgressStatus());

        // 해당 진행 상태에 대한 PotholeHistory 가 없다면 생성
        createPotholeHistoryToProgressStatus(pothole, potholeHistories);

        return pothole;
    }

    private void createPotholeHistoryToProgressStatus(Pothole pothole, List<PotholeHistory> potholeHistories) {
        boolean isProcessStatusIncluded = potholeHistories.stream()
                                                           .anyMatch(potholeHistory ->
                                                                   potholeHistory.getProcessStatus().equals(pothole.getProcessStatus())
                                                           );

        if (!isProcessStatusIncluded) {
            PotholeHistory potholeHistory = new PotholeHistory(pothole, pothole.getProcessStatus());
            potholeHistoryRepository.save(potholeHistory);
        }
    }

    @Override
    public void deletePothole(Long potholeId) {
        Pothole pothole = findByPotholeId(potholeId);

        String thumbnail = pothole.getThumbnail();

        List<String> potholeHistoryImages = getPotholeHistoryImagesByPotholeId(potholeId);

        // PotholeHistoryImage 삭제
        potholeHistoryImageRepository.deleteAllByPotholeId(potholeId);

        // PotholeHistory 삭제
        potholeHistoryRepository.deleteAllByPotholeId(potholeId);

        // Pothole 삭제
        potholeRepository.deleteById(potholeId);

        // S3 PotholeHistoryImage 삭제
        if (!potholeHistoryImages.isEmpty()) {
            potholeHistoryImages.forEach(imageService::deleteImage);
        }

        // S3 Pothole Thumbnail 삭제
        if (thumbnail != null && !thumbnail.isEmpty()) {
            imageService.deleteImage(thumbnail);
        }
    }

    private List<String> getPotholeHistoryImagesByPotholeId(Long potholeId) {
        List<PotholeHistoryImage> potholeHistoryImages = potholeHistoryImageRepository.findAllByPotholeId(potholeId);

        List<String> potholeHistoryImagesUrl = new ArrayList<>();

        if (potholeHistoryImages != null && !potholeHistoryImages.isEmpty()) {
            potholeHistoryImages.forEach(potholeHistoryImage -> potholeHistoryImagesUrl.add(potholeHistoryImage.getImage()));
        }

        return potholeHistoryImagesUrl;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pothole> getFilteredPotholes(PotholeFilterDto potholeFilterDto) {
        Integer availableMinImportance = (potholeFilterDto.getMinImportance() == null) ? 0 : potholeFilterDto.getMinImportance();
        Integer availableMaxImportance = (potholeFilterDto.getMaxImportance() == null) ? 100 : potholeFilterDto.getMaxImportance();

        potholeFilterDto.changeToAvailableImportance(availableMinImportance, availableMaxImportance);

        return potholeQueryDslRepository.findByFilter(potholeFilterDto);
    }

    private Pothole findByPotholeId(Long potholeId) {
        return potholeRepository.findById(potholeId)
                                .orElseThrow(
                                        () -> NONE_POTHOLE
                                );
    }
}
