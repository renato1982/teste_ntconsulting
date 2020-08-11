package br.com.nt.teste.teste.renato.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DadosVendaDTO {

    private Integer idVenda;
    private String nomeVendedor;
    private List<VendaDTO> venda = new ArrayList<>();
}
