package br.com.nathcodes.campominado.modelos;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int linha;
    private final int coluna;
    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();

    public Campo(final int linha,
                 final int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public boolean isMinado() {
        return minado;
    }

    public void setMinado(final boolean minado) {
        this.minado = minado;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(final boolean aberto) {
        this.aberto = aberto;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(final boolean marcado) {
        this.marcado = marcado;
    }

    public List<Campo> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(final List<Campo> vizinhos) {
        this.vizinhos = vizinhos;
    }

    public boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int detalGeral = deltaColuna + deltaLinha;

        if (detalGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (detalGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    public void alternarMarcacao() {
        if (!isAberto()) {
            setMarcado(!isMarcado());
        }
    }

    public boolean abrir() {

        if (!aberto && !marcado) {
            if (minado) {
                return true;
            }

            setAberto(true);

            if (vizinhancaSegura()) {
                vizinhos.forEach(Campo::abrir);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public void minar() {
        minado = true;
    }

    public boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    public long minasNaVizinhanca() {
        return (int) vizinhos.stream().filter(v -> v.minado).count();
    }

    public void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
    }

    public String toString() {
        if (isMarcado()) {
            return "x";
        } else if (isAberto() && isMinado()) {
            return "*";
        } else if (isAberto() && this.minasNaVizinhanca() > 0) {
            return Long.toString(minasNaVizinhanca());
        } else if (isAberto()) {
            return " ";
        } else {
            return "?";
        }
    }
}
