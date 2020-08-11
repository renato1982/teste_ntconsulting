package br.com.nt.teste.teste.renato.service;

import br.com.nt.teste.teste.renato.AnaliseDados;
import br.com.nt.teste.teste.renato.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnaliseDadosImpl implements AnaliseDados {

    @Override
    public RetornoDTO processaAnalise() {
        RetornoDTO retornoDTO = new RetornoDTO();
        List<DadosVendedorDTO> listaVendedor = new ArrayList<>();
        List<DadosClienteDTO> listaCliente = new ArrayList<>();
        List<DadosVendaDTO> listaVendas = new ArrayList<>();

        InputStream inputstream;
        try {
            String linha = null;
            FileReader reader = new FileReader("D://HOMEPATH//data//in//testeNT.txt");
            BufferedReader leitor = new BufferedReader(reader);


            while ((linha = leitor.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linha, "" );
                String dados = st.nextToken();

                if (dados.contains("001")){
                    DadosVendedorDTO vendedorDTO = processarDadosVendedor(dados);
                    listaVendedor.add(vendedorDTO);

                }else if (dados.contains("002")){
                    DadosClienteDTO clienteDTO = processarDadosCliente(dados);
                    listaCliente.add(clienteDTO);

                }else if (dados.contains("003")){
                    DadosVendaDTO vendaDTO = procesarDadosVenda(dados);
                    listaVendas.add(vendaDTO);
                }

            }

            retornoDTO = gerarArquivo(listaCliente, listaVendedor, listaVendas);
            leitor.close();
            reader.close();
        } catch (FileNotFoundException e1) {
            System.out.print(e1.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

        return retornoDTO;
    }

    private RetornoDTO gerarArquivo(List<DadosClienteDTO> listaCliente, List<DadosVendedorDTO> listaVendedor, List<DadosVendaDTO> listaVendas) {

        RetornoDTO retornoDTO = new RetornoDTO();

        try {
            FileWriter arquivoSaida = new FileWriter("D://HOMEPATH//data//out//testeNT.txt");
            PrintWriter gravarArquivo = new PrintWriter(arquivoSaida);

            gravarArquivo.println("Total de clientes:... " + listaCliente.size() + ";");
            gravarArquivo.println("Total de vendedores:... " + listaVendedor.size() + ";");
            gravarArquivo.println("Id venda mais cara:... " + vendamaiscara(listaVendas) + ";");

            gravarArquivo.close();
            retornoDTO.setMensagem("Arquivo gerado com sucesso!");
        } catch (IOException e) {
            System.out.print(e.getMessage());
            retornoDTO.setMensagem("Erro ao gerar Arquivo!");
        }

        return retornoDTO;

    }

    private String vendamaiscara(List<DadosVendaDTO> listaVendas) {
        String venda = null;
        BigDecimal valorMaior = new BigDecimal(0);

        for (DadosVendaDTO dados: listaVendas) {
            for (VendaDTO vendaDTO : dados.getVenda()) {
                if(vendaDTO.getValorVenda().compareTo(valorMaior) == 1){
                    valorMaior = vendaDTO.getValorVenda();
                    venda = dados.getIdVenda().toString();

                }
            }
        }

        return venda;
    }

    private DadosVendaDTO procesarDadosVenda(String dados) {
        DadosVendaDTO vendaDTO = new DadosVendaDTO();
        String [] dadosSeparados = dados.split("ç");
        String [] dadosVenda = dadosSeparados[2].replace("[", "")
                .replace("]", "").split(",");
        List<VendaDTO> listaItensVenda = processarItensVenda(dadosVenda);

        vendaDTO.setVenda(listaItensVenda);

        vendaDTO.setIdVenda(1);

        return vendaDTO;
    }

    private List<VendaDTO> processarItensVenda(String[] dadosVenda) {
        List<VendaDTO> lista = new ArrayList<>();
        for (int i = 0; i < dadosVenda.length; i++){
            String [] venda = dadosVenda[i].split("-");
            VendaDTO vendaDTO = new VendaDTO();

            vendaDTO.setIdItem(Integer.valueOf(venda[0]));
            vendaDTO.setQtdItem(Integer.valueOf(venda[1]));
            vendaDTO.setValorVenda(new BigDecimal(venda[2]));

            lista.add(vendaDTO);

        }

        return lista;
    }

    private DadosClienteDTO processarDadosCliente(String dados) {

        DadosClienteDTO dadosClienteDTO = new DadosClienteDTO();
        String [] dadosSeparados = dados.split("ç");

        dadosClienteDTO.setCnpj(Long.valueOf(dadosSeparados[1]));
        dadosClienteDTO.setNomeEmpresa(dadosSeparados[2]);
        dadosClienteDTO.setRamoAtuacao(dadosSeparados[3]);

        return dadosClienteDTO;
    }

    private DadosVendedorDTO processarDadosVendedor(String dados) {

        DadosVendedorDTO vendedorDTO = new DadosVendedorDTO();
        String [] dadosSeparados = dados.split("ç");
        vendedorDTO.setCpfVendedor(Long.valueOf(dadosSeparados[1]));
        vendedorDTO.setNomeVendedor(dadosSeparados[2]);
        vendedorDTO.setSalario(new BigDecimal(dadosSeparados[3]));

        return vendedorDTO;
    }

    public static void main(String[] args) {
        AnaliseDadosImpl dados = new AnaliseDadosImpl();

        dados.processaAnalise();
    }
}
