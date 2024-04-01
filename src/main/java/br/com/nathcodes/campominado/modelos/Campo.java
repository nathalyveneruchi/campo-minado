package br.com.nathcodes.campominado.modelos;

import br.com.nathcodes.campominado.exceptions.ExplosaoException;

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
        List<Campo> vizinhos = new ArrayList<>();
        boolean linhaDiferente = getLinha() != vizinho.getLinha();
        boolean colunaDiferente = getColuna() != vizinho.getColuna();
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(getLinha() - vizinho.getLinha());
        int deltaColuna = Math.abs(getColuna() - vizinho.getColuna());
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            setVizinhos(vizinhos);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            setVizinhos(vizinhos);
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
        if (!isMarcado() && !isAberto()) {
            setAberto(true);

            if (isMinado()) {
                throw new ExplosaoException();
            }

            if (vizinhancaSegura()) {
                getVizinhos().forEach(Campo::abrir);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean vizinhancaSegura() {
        return getVizinhos().stream().noneMatch(Campo::isMinado);
    }

    public void minar() {
        setMinado(true);
    }

    public boolean objetivoAlcancado() {
        boolean desvendado = !isMinado();
        boolean protegido = isMinado() && isMarcado();
        return desvendado || protegido;
    }

    public long minasNaVizinhanca() {
        return getVizinhos().stream().filter(Campo::isMinado).count();
    }

    public void reiniciar() {
        setAberto(false);
        setMinado(false);
        setMarcado(false);
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
