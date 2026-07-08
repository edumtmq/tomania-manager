package com.tomania.tomania_manager.controller;

import com.tomania.tomania_manager.dto.DashboardResumoDTO;
import com.tomania.tomania_manager.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/resumo")
    public DashboardResumoDTO gerarResumo() {
        return dashboardService.gerarResumo();
    }
}
