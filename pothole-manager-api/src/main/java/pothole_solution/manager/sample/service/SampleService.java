package pothole_solution.manager.sample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.PotholeRepository;
import pothole_solution.core.pothole.dto.request.PotholeChangeProgressRequestDto;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SampleService {
    private final PotholeRepository potholeRepository;

    public Pothole register(Pothole pothole) {
        return potholeRepository.save(pothole);
    }

    @Transactional(readOnly = true)
    public Pothole getPothole(Long id) {
        return potholeRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Pothole> getAllPotholes() {
        return potholeRepository.findAll();
    }

    public Pothole changePotholeProgress(Long id, PotholeChangeProgressRequestDto changeProgressRequestDto) {
        Pothole pothole = potholeRepository.findById(id).get();
        pothole.changeProgress(changeProgressRequestDto.getProgress());

        return pothole;
    }

    public void deletePothole(Long id) {
        potholeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Pothole> getFilteredPotholes(Integer minImportance, Integer maxImportance, String process) {
        List<Pothole> potholes = potholeRepository.findAll();

        int actualMinImportance = (minImportance == null) ? 0 : minImportance;
        int actualMaxImportance = (maxImportance == null) ? 100 : maxImportance;

        return potholes.stream()
                .filter(pothole ->
                        (actualMinImportance <= pothole.getImportance() && pothole.getImportance() <= actualMaxImportance)
                        &&
                        (process == null || pothole.getProgress().getValue().equals(process))
                )
                .toList();
    }
}
