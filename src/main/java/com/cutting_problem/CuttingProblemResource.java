package com.cutting_problem;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cutting-problem")
@AllArgsConstructor
public class CuttingProblemResource {

    private CuttingProblemAlgorithm cuttingProblemAlgorithm;

    @PostMapping
    public void compute() {
        cuttingProblemAlgorithm.compute();
    }
}
