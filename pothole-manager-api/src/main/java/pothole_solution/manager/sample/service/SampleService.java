package pothole_solution.manager.sample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.PotholeRepository;
import pothole_solution.core.pothole.Progress;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SampleService {
    private final PotholeRepository potholeRepository;

    @Transactional
    public Pothole register(Pothole pothole) {
        return potholeRepository.save(pothole);
    }

    public Pothole getPothole(Long id) {
        return potholeRepository.findById(id).get();
    }

    public List<Pothole> getAllPotholes() {
        return potholeRepository.findAll();
    }

    @Transactional
    public Pothole changePotholeProgress(Long id) {
        Pothole pothole = potholeRepository.findById(id).get();
        pothole.changeProgress(Progress.EMERGENCY_ONGOING);

        return pothole;
    }

    public void deletePothole(Long id) {
        potholeRepository.deleteById(id);
    }

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
