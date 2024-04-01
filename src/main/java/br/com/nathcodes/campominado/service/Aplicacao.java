package br.com.nathcodes.campominado.service;

import br.com.nathcodes.campominado.modelos.Tabuleiro;
import br.com.nathcodes.campominado.modelos.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);
        new TabuleiroConsole(tabuleiro);


    }
}
