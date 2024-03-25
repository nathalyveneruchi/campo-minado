package br.com.nathcodes.campominado.modelos;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int linha;
    private final int coluna;
    private boolean minado;
    private boolean aberto;
    private boolean marcado;

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
}
