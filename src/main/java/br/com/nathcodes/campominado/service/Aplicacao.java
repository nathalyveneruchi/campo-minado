package br.com.nathcodes.campominado.service;

import br.com.nathcodes.campominado.modelos.Campo;
import br.com.nathcodes.campominado.modelos.Tabuleiro;

import java.util.ArrayList;
import java.util.List;

public class Aplicacao {
    private static final CampoService campoService = new CampoService();
    private static final TabuleiroService tabuleiroService = new TabuleiroService(campoService);

    public static void main(String[] args) {
        List<Campo> campos = new ArrayList<>();
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
        tabuleiroService.iniciarJogo(campos, 6, 6, 6);

        tabuleiroService.alternarMarcacao(campos, 4, 4);
        tabuleiroService.alternarMarcacao(campos, 4, 5);
        tabuleiroService.abrir(campos, 3, 3);

    }
}
