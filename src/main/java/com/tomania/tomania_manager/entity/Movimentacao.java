package com.tomania.tomania_manager.entity;


import com.tomania.tomania_manager.enums.MotivoMovimentacao;
import com.tomania.tomania_manager.enums.TipoMovimentacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    @NotNull
    @Positive
    private Integer quantidade;
    @NotBlank
    private String responsavel;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable=false)
    private MotivoMovimentacao motivo;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable=false)
    private TipoMovimentacao tipo;
    private LocalDateTime dataMovimentacao;
    @PrePersist
    public void prePersist(){
        if (dataMovimentacao == null) {
            dataMovimentacao = LocalDateTime.now();
        }
    }


}
