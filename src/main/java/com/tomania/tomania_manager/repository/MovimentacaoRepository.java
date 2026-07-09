package com.tomania.tomania_manager.repository;

import com.tomania.tomania_manager.entity.Movimentacao;
import com.tomania.tomania_manager.enums.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {

    List<Movimentacao> findByProduto_Id(Integer produtoId);

    List<Movimentacao> findTop5ByOrderByDataMovimentacaoDesc();

    List<Movimentacao> findByTipo(TipoMovimentacao tipo);

    List<Movimentacao> findByDataMovimentacaoBetween(
            LocalDateTime inicio,
            LocalDateTime fim
   );

    List<Movimentacao> findByTipoAndDataMovimentacaoBetween
            (TipoMovimentacao tipo, LocalDateTime inicio, LocalDateTime fim);

    List<Movimentacao> findByProduto_IdAndDataMovimentacaoBetween
            (Integer produtoId, LocalDateTime inicio, LocalDateTime fim);

    List<Movimentacao> findByProduto_IdAndTipoAndDataMovimentacaoBetween
            (Integer produtoId, TipoMovimentacao tipo, LocalDateTime inicio, LocalDateTime fim);
}
