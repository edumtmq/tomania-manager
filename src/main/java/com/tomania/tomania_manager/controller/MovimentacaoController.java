package com.tomania.tomania_manager.controller;

import com.tomania.tomania_manager.dto.MovimentacaoRequestDTO;
import com.tomania.tomania_manager.dto.MovimentacaoResponseDTO;
import com.tomania.tomania_manager.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping
    public MovimentacaoResponseDTO registrarMovimentacao(@RequestBody @Valid MovimentacaoRequestDTO movimentacaoRequestDTO){
        return movimentacaoService.registrarMovimentacao(movimentacaoRequestDTO);
    }
}
