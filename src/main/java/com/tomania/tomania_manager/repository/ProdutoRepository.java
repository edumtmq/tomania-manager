package com.tomania.tomania_manager.repository;

import com.tomania.tomania_manager.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {

    List<Produto> findByAtivoTrue();

    List<Produto> findByAtivoFalse();
}
