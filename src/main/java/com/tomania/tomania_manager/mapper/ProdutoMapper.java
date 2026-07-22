package com.tomania.tomania_manager.mapper;

import com.tomania.tomania_manager.dto.ProdutoRequestDTO;
import com.tomania.tomania_manager.dto.ProdutoResponseDTO;
import com.tomania.tomania_manager.entity.Produto;
import com.tomania.tomania_manager.enums.StatusProduto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    private static final int MARGEM_ATENCAO = 2;

    private StatusProduto calcularStatus(
            Produto produto
    ) {
        int estoqueAtual =
                produto.getEstoqueAtual();

        int estoqueMinimo =
                produto.getEstoqueMinimo();

        if (estoqueAtual <= estoqueMinimo) {
            return StatusProduto.COMPRAR;
        }

        if (
                estoqueAtual
                        <= estoqueMinimo
                        + MARGEM_ATENCAO
        ) {
            return StatusProduto.ATENCAO;
        }

        return StatusProduto.OK;
    }

    // Transforma a entidade em DTO de resposta
    public ProdutoResponseDTO toProdutoResponse(
            Produto produto
    ) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.isAtivo(),
                produto.getEstoqueMinimo(),
                produto.getEstoqueAtual(),
                calcularStatus(produto)
        );
    }

    // Transforma o DTO de requisição em entidade
    public Produto toProduto(
            ProdutoRequestDTO dto
    ) {
        Produto produto = new Produto();

        produto.setNome(dto.nome());

        produto.setEstoqueMinimo(
                dto.estoqueMinimo()
        );

        return produto;
    }
}