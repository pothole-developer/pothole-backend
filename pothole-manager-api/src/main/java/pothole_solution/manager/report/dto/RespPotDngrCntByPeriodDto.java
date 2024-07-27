package pothole_solution.manager.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RespPotDngrCntByPeriodDto {
    String period;
    Long dangerous0to20;
    Long dangerous20to40;
    Long dangerous40to60;
    Long dangerous60to80;
    Long dangerous80to100;
}
