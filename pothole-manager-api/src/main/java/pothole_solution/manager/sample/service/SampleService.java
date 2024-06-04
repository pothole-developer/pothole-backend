package pothole_solution.manager.sample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pothole_solution.core.pothole.Pothole;
import pothole_solution.core.pothole.PotholeQueryDslRepository;
import pothole_solution.core.pothole.PotholeRepository;
import pothole_solution.core.pothole.dto.PotholeFilterDto;
import pothole_solution.core.pothole.dto.request.PotholeChangeProcessStatusRequestDto;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SampleService {
    private final PotholeRepository potholeRepository;
    private final PotholeQueryDslRepository potholeQueryDslRepository;

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

    public Pothole changePotholeProgress(Long id, PotholeChangeProcessStatusRequestDto changeProcessStatusRequestDto) {
        Pothole pothole = potholeRepository.findById(id).get();
        pothole.changeProgress(changeProcessStatusRequestDto.getProcessStatus());

        return pothole;
    }

    public void deletePothole(Long id) {
        potholeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Pothole> getFilteredPotholes(PotholeFilterDto potholeFilterDto) {
        short availableMinImportance = (potholeFilterDto.getMinImportance() == null) ? 0 : potholeFilterDto.getMinImportance();
        short availableMaxImportance = (potholeFilterDto.getMaxImportance() == null) ? 100 : potholeFilterDto.getMaxImportance();

        potholeFilterDto.changeToAvailableImportance(availableMinImportance, availableMaxImportance);

        return potholeQueryDslRepository.findByFilter(potholeFilterDto);
    }
}
