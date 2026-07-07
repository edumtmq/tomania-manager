package com.tomania.tomania_manager.repository;

import com.tomania.tomania_manager.entity.Movimentacao;
import com.tomania.tomania_manager.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {


    List<Movimentacao> findByProduto_Id(Integer produtoId);



}
