package br.com.nathcodes.campominado.service;

import br.com.nathcodes.campominado.exceptions.ExplosaoException;
import br.com.nathcodes.campominado.modelos.Campo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampoService {
    public boolean adicionarVizinho(Campo campo, Campo vizinho) {
        List<Campo> vizinhos = new ArrayList<>();
        boolean linhaDiferente = campo.getLinha() != vizinho.getLinha();
        boolean colunaDiferente = campo.getColuna() != vizinho.getColuna();
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(campo.getLinha() - vizinho.getLinha());
        int deltaColuna = Math.abs(campo.getColuna() - vizinho.getColuna());
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            campo.setVizinhos(vizinhos);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            campo.setVizinhos(vizinhos);
            return true;
        } else {
            return false;
        }

    }

    public void alternarMarcacao(Campo campo) {
        if (!campo.isAberto()) {
            campo.setMarcado(!campo.isMarcado());
        }
    }

    public boolean abrir(Campo campo) {
        if (!campo.isMarcado() && !campo.isAberto()) {
            campo.setAberto(true);

            if (campo.isMinado()) {
                throw new ExplosaoException();
            }

            if (vizinhancaSegura(campo)) {
                campo.getVizinhos().forEach(this::abrir);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean vizinhancaSegura(Campo campo) {
        return campo.getVizinhos().stream().noneMatch(Campo::isMinado);
    }

    public void minar(Campo campo) {
        campo.setMinado(true);
    }

    public boolean objetivoAlcancado(Campo campo) {
        boolean desvendado = !campo.isMinado();
        boolean protegido = campo.isMinado() && campo.isMarcado();
        return desvendado || protegido;
    }

    public long minasNaVizinhanca(Campo campo) {
        return campo.getVizinhos().stream().filter(Campo::isMinado).count();
    }

    public void reiniciar(Campo campo) {
        campo.setAberto(false);
        campo.setMinado(false);
        campo.setMarcado(false);
    }

    public String toString(Campo campo) {
        if (campo.isMarcado()) {
            return "x";
        } else if (campo.isAberto() && campo.isMinado()) {
            return "*";
        } else if (campo.isAberto() && this.minasNaVizinhanca(campo) > 0) {
            return Long.toString(minasNaVizinhanca(campo));
        } else if (campo.isAberto()) {
            return " ";
        } else {
            return "?";
        }
    }
}
