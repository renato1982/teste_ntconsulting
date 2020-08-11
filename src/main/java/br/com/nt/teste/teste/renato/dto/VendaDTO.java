package br.com.nt.teste.teste.renato.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendaDTO {

    private Integer idItem;
    private Integer qtdItem;
    private BigDecimal valorVenda;
}
