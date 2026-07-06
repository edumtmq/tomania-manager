package com.tomania.tomania_manager.mapper;

import com.tomania.tomania_manager.dto.ProdutoRequestDTO;
import com.tomania.tomania_manager.dto.ProdutoResponseDTO;
import com.tomania.tomania_manager.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

//    -> Transforma a entidade em DTO de resposta
    public ProdutoResponseDTO toProdutoResponse(Produto produto){
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.isAtivo(),
                produto.getEstoqueMinimo(),
                produto.getEstoqueAtual()
        );
    }
//   -> Trasnforma DTO de Requisição em entidade p salvar no banco

    public Produto toProduto(ProdutoRequestDTO dto){
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setEstoqueMinimo(dto.estoqueMinimo());
        return produto;
    }
}
