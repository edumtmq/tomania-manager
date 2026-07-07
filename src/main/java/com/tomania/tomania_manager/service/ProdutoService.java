package com.tomania.tomania_manager.service;

import com.tomania.tomania_manager.dto.ProdutoRequestDTO;
import com.tomania.tomania_manager.dto.ProdutoResponseDTO;
import com.tomania.tomania_manager.entity.Produto;
import com.tomania.tomania_manager.mapper.ProdutoMapper;
import com.tomania.tomania_manager.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }
//  -> Cadastro de Produtos
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoDTO){
        Produto produto = produtoMapper.toProduto(produtoDTO);

        Produto produtoSalvo = produtoRepository.save(produto);

        return produtoMapper.toProdutoResponse(produtoSalvo);
    }
//  -> Listar Produtos
    public List<ProdutoResponseDTO> listarProdutos(){
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toProdutoResponse)
                .toList();
    }
//  -> Buscar produto por Id
    public ProdutoResponseDTO buscarProdutoPorId(Integer id){
        Produto produto = produtoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Produto não encontrado"));

        return produtoMapper.toProdutoResponse(produto);
    }



}
