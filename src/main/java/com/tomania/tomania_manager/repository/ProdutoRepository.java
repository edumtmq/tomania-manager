package com.tomania.tomania_manager.repository;

import com.tomania.tomania_manager.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {
}
