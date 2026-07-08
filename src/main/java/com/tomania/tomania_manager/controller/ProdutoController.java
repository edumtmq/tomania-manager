package com.tomania.tomania_manager.controller;

import com.tomania.tomania_manager.dto.ProdutoRequestDTO;
import com.tomania.tomania_manager.dto.ProdutoResponseDTO;
import com.tomania.tomania_manager.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ProdutoResponseDTO cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO produtoDTO){
        return produtoService.cadastrarProduto(produtoDTO);
    }

    @GetMapping
    public List<ProdutoResponseDTO> listarProdutos(){
        return produtoService.listarProdutos();
    }

    @GetMapping("/inativos")
    public List<ProdutoResponseDTO> listarProdutosInativos(){
        return produtoService.listarProdutosInativos();
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarProdutoPorId(@PathVariable Integer id){
        return produtoService.buscarProdutoPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizarProduto(@PathVariable Integer id,
                                               @RequestBody @Valid ProdutoRequestDTO produtoDTO){
        return produtoService.atualizarProduto(id, produtoDTO);
    }

    @PatchMapping("/{id}/ativar")
    public ProdutoResponseDTO ativarProduto(@PathVariable Integer id){
        return produtoService.ativarProduto(id);
    }

    @DeleteMapping("/{id}")
    public ProdutoResponseDTO inativarProduto(@PathVariable Integer id){
        return produtoService.inativarProduto(id);
    }




}
