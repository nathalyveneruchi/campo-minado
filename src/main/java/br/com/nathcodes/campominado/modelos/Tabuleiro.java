package br.com.nathcodes.campominado.modelos;

import br.com.nathcodes.campominado.exceptions.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(final int linhas, final int colunas, final int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void abrir(final int linha, final int coluna) {
        try {
            campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(Campo::abrir);
        } catch (ExplosaoException e) {
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }

    }

    public void alternarMarcacao(final int linha, final int coluna) {
        campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(Campo::alternarMarcacao);
    }


    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                campos.add(new Campo(linha, coluna));
            }
        }

    }

    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c2.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas;

        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(Campo::isMinado).count();
        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }

    public void reiniciarJogo() {
        campos.forEach(Campo::reiniciar);
        sortearMinas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
