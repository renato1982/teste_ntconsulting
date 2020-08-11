package br.com.nt.teste.teste.renato.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DadosVendedorDTO {

    private Long cpfVendedor;
    private String nomeVendedor;
    private BigDecimal salario;

}
