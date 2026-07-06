package com.tomania.tomania_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String nome;
    @PositiveOrZero
    @Builder.Default
    private Integer estoqueAtual = 0;
    @Builder.Default
    private boolean ativo = true;
    @Positive
    @Column(nullable=false)
    private Integer estoqueMinimo;

}
