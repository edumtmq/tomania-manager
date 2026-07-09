package com.tomania.tomania_manager.dto;

import java.time.LocalDateTime;

public record RelatorioProdutoMovimentacaoDTO(
        Integer produtoId,
        String produtoNome,
        Integer estoqueAtual,
        Integer periodoDias,

        Integer entradaCompra,
        Integer entradaBonificacao,
        Integer entradaAjuste,

        Integer saidaProducao,
        Integer saidaPerda,
        Integer saidaValidade,
        Integer saidaAjuste,

        Integer totalEntradas,
        Integer totalSaidas,
        Double consumoMedioDiario
) {
}
