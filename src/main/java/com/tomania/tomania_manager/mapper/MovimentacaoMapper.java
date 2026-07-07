package com.tomania.tomania_manager.mapper;

import com.tomania.tomania_manager.dto.MovimentacaoRequestDTO;
import com.tomania.tomania_manager.dto.MovimentacaoResponseDTO;
import com.tomania.tomania_manager.entity.Movimentacao;
import com.tomania.tomania_manager.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoMapper {

//    -> Transforma a entidade em DTO de resposta
    public MovimentacaoResponseDTO toMovimentacaoResponse(Movimentacao movimentacao){
        return new MovimentacaoResponseDTO(
                movimentacao.getId(),
                movimentacao.getProduto().getId(),
                movimentacao.getProduto().getNome(),
                movimentacao.getTipo(),
                movimentacao.getMotivo(),
                movimentacao.getQuantidade(),
                movimentacao.getDataMovimentacao(),
                movimentacao.getResponsavel()
        );
    }

//    -> Transforma DTO de requisição em entidade para salvar no banco
    public Movimentacao toMovimentacao(MovimentacaoRequestDTO dto, Produto produto){
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(dto.tipo());
        movimentacao.setMotivo(dto.motivo());
        movimentacao.setQuantidade(dto.quantidade());
        movimentacao.setResponsavel(dto.responsavel());
        return movimentacao;
    }
}
