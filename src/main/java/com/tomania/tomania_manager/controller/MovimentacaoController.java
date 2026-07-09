package com.tomania.tomania_manager.controller;

import com.tomania.tomania_manager.dto.EntradaLoteRequestDTO;
import com.tomania.tomania_manager.dto.MovimentacaoRequestDTO;
import com.tomania.tomania_manager.dto.MovimentacaoResponseDTO;
import com.tomania.tomania_manager.enums.TipoMovimentacao;
import com.tomania.tomania_manager.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping
    public MovimentacaoResponseDTO registrarMovimentacao(@RequestBody @Valid MovimentacaoRequestDTO movimentacaoRequestDTO){
        return movimentacaoService.registrarMovimentacao(movimentacaoRequestDTO);
    }

    @PostMapping("/entrada-lote")
    public List<MovimentacaoResponseDTO> registrarEntradaEmLote(@RequestBody @Valid List<EntradaLoteRequestDTO> entradas){
        return movimentacaoService.regitrarEntradaEmLote(entradas);
    }

    @GetMapping
    public List<MovimentacaoResponseDTO> listarMovimentacoes(){
        return movimentacaoService.listarMovimentacoes();
    }

    @GetMapping("/{id}")
    public MovimentacaoResponseDTO buscarMovimentacaoPorId(@PathVariable Integer id){
        return movimentacaoService.buscarMovimentacaoPorId(id);
    }

    @GetMapping("/produto/{produtoId}")
    public List<MovimentacaoResponseDTO> listarMovimentacoesPorProduto(@PathVariable Integer produtoId) {
        return movimentacaoService.listarMovimentacoesPorProduto(produtoId);
    }

    @GetMapping("/tipo/{tipo}")
    public List<MovimentacaoResponseDTO> listarMovimentacoesPorTipo(@PathVariable TipoMovimentacao tipo) {
        return movimentacaoService.listarPorTipo(tipo);
    }

    @GetMapping("/periodo")
    public List<MovimentacaoResponseDTO> listarMovimentacoesPorPeriodo(@RequestParam LocalDateTime inicio,
                                                                       @RequestParam LocalDateTime fim){
        return movimentacaoService.listarPorPeriodo(inicio, fim);
    }

    @GetMapping("/filtro")
    public List<MovimentacaoResponseDTO> listarMovimentacoesPorFiltro(
            @RequestParam(required = false) Integer produtoId,
            @RequestParam(required = false) TipoMovimentacao tipo,
            @RequestParam(required = false) @DateTimeFormat LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat LocalDateTime fim
    ){
        return movimentacaoService.filtroCombinado(produtoId, tipo, inicio, fim);
    }
}