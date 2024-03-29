package br.com.nathcodes.campominado.service;

import br.com.nathcodes.campominado.modelos.Campo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabuleiroService {

    private final CampoService campoService;

    public TabuleiroService(final CampoService campoService) {
        this.campoService = campoService;
    }

    public void iniciarJogo(final List<Campo> campos, final int linhas, final int colunas, final int minas) {
        gerarCampos(campos, linhas, colunas);
        associarVizinhos(campos);
        sortearMinas(campos, minas);
    }

    public void abrir(final List<Campo> campos, final int linha, final int coluna) {
        campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(campoService::abrir);
    }

    public void alternarMarcacao(final List<Campo> campos, final int linha, final int coluna) {
        campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(campoService::alternarMarcacao);
    }


    private void gerarCampos(final List<Campo> campos, final int linhas, final int colunas) {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                campos.add(new Campo(linha, coluna));
            }
        }

    }

    private void associarVizinhos(final List<Campo> campos) {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                campoService.adicionarVizinho(c1, c2);
            }
        }
    }

    private void sortearMinas(final List<Campo> campos, final int minas) {
        int minasArmadas;

        do {
            minasArmadas = (int) campos.stream().filter(Campo::isMinado).count();
            int aleatorio = (int) ((Math.random() * campos.size()));
            campoService.minar(campos.get(aleatorio));
        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado(final List<Campo> campos) {
        return campos.stream().allMatch(campoService::objetivoAlcancado);
    }

    public void reiniciarJogo(final List<Campo> campos) {
        campos.forEach(campoService::reiniciar);
        sortearMinas(campos, 50);
    }
}
