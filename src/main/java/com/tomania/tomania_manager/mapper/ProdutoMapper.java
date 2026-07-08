package com.tomania.tomania_manager.mapper;

import com.tomania.tomania_manager.dto.ProdutoRequestDTO;
import com.tomania.tomania_manager.dto.ProdutoResponseDTO;
import com.tomania.tomania_manager.entity.Produto;
import com.tomania.tomania_manager.enums.StatusProduto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    private StatusProduto calcularStatus(Produto produto){
        if (produto.getEstoqueAtual() < produto.getEstoqueMinimo()){
            return StatusProduto.COMPRAR;
        }

        if (produto.getEstoqueAtual().equals(produto.getEstoqueMinimo())) {
            return StatusProduto.ATENCAO;
        }

        return StatusProduto.OK;
    }

//    -> Transforma a entidade em DTO de resposta
    public ProdutoResponseDTO toProdutoResponse(Produto produto){
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.isAtivo(),
                produto.getEstoqueMinimo(),
                produto.getEstoqueAtual(),
                calcularStatus(produto)
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
