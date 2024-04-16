package pothole_solution.manager.sample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.core.Pothole;
import pothole_solution.core.PotholeRepository;
import pothole_solution.core.Progress;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SampleService {
    private final PotholeRepository potholeRepository;

    public Pothole register(Pothole pothole) {
        return potholeRepository.save(pothole);
    }

    public Pothole getPothole(Long id) {
        return potholeRepository.findById(id).get();
    }

    public List<Pothole> getAllPotholes() {
        return potholeRepository.findAll();
    }

    public Pothole changePotholeProgress(Long id) {
        Pothole pothole = potholeRepository.findById(id).get();
        pothole.changeProgress(Progress.IN_PROGRESS);

        return pothole;
    }

    public void deletePothole(Long id) {
        potholeRepository.deleteById(id);
    }
}
