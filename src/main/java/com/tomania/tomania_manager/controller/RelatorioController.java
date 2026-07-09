package com.tomania.tomania_manager.controller;

import com.tomania.tomania_manager.dto.RelatorioProdutoMovimentacaoDTO;
import com.tomania.tomania_manager.service.RelatorioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@AllArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/produtos-movimentacao")
    public List<RelatorioProdutoMovimentacaoDTO> listaProdutosMovimentacao(
            @RequestParam(required = false) Integer produtoId,
            @RequestParam(required = false) Integer dias
    ){
        return relatorioService.relatorioProdutoMovimentacao(produtoId, dias);
    }
}
