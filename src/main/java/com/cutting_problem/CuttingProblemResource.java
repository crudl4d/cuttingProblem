package com.cutting_problem;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cutting-problem")
@AllArgsConstructor
public class CuttingProblemResource {

    private CuttingProblemAlgorithm cuttingProblemAlgorithm;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void compute(@RequestBody @Valid BandDto bandDto) {
        cuttingProblemAlgorithm.compute(bandDto);
    }
}
