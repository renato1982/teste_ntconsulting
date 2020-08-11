package br.com.nt.teste.teste.renato.schedule;

import br.com.nt.teste.teste.renato.AnaliseDados;
import br.com.nt.teste.teste.renato.dto.RetornoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/teste")
@RequiredArgsConstructor
public class ProcessarDados {

    @Autowired
    private final AnaliseDados analiseDados;

    @GetMapping("/processar")
    public ResponseEntity<?> execute(){

        RetornoDTO retornoDTO = analiseDados.processaAnalise();

        return ResponseEntity.ok(retornoDTO);
    }
}
