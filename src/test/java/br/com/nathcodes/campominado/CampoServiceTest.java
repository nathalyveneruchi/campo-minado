package br.com.nathcodes.campominado;

import br.com.nathcodes.campominado.exceptions.ExplosaoException;
import br.com.nathcodes.campominado.modelos.Campo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CampoServiceTest {

    @Mock
    private Campo campo;


    @BeforeEach
    void iniciarCamop() {
        campo = new Campo(3, 3);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "3, 2",
            "2, 3",
            "4, 3"
    })
    void testeVizinhoRealDistancia(int linha, int coluna) {
        Campo vizinho = new Campo(linha, coluna);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 2",
            "2, 4",
            "4, 2",
            "4, 4"
    })
    void testeVizinhoRealDistanciaDiagonal(int linha, int coluna) {
        Campo vizinho = new Campo(linha, coluna);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "1, 4",
            "5, 5",
            "5, 1"
    })
    void testeNaoVizinhoRealDistanciaDiagonal(int linha, int coluna) {
        Campo vizinho = new Campo(linha, coluna);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 3",
            "3, 5",
            "3, 1",
            "5, 3"
    })
    void testeNaoVizinhoRealDistancia(int linha, int coluna) {
        Campo vizinho = new Campo(linha, coluna);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);
    }

    @Test
    void testeAlternarMarcacaoComUmaChamada() {
        assertFalse(campo.isMarcado());
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacaoComDuasChamadas() {
        assertFalse(campo.isMarcado());

        campo.alternarMarcacao();
        campo.alternarMarcacao();

        assertFalse(campo.isMarcado());
    }

    @ParameterizedTest
    @CsvSource({
            "false, false, false, true",
            "false, true, true, false",
            "true, true, true, false",
            "true, true, false, false",
            "false, false, true, false",
            "false, true, true, false",
            "true, true, true, false"
    })
    void testeMetodoAbrir(boolean minado, boolean marcado, boolean aberto, boolean resultadoParametrized) {
        campo.setMinado(minado);
        campo.setMarcado(marcado);
        campo.setAberto(aberto);
        boolean resultado = campo.abrir();

        assertEquals(resultado, resultadoParametrized);
    }

    @Test
    void testeAbrirCampoMinadoDeveLancarExplosaoException() {
        campo.minar();

        assertThrows(ExplosaoException.class, () -> campo.abrir());
    }

    @Test
    void testeAbrirComVizinhos() {
        Campo campo11 = new Campo(1, 1);
        Campo campo22 = new Campo(2, 2);

        assertFalse(campo22.isAberto());
        assertFalse(campo11.isAberto());

        campo.adicionarVizinho(campo11);
        campo.adicionarVizinho(campo22);

        campo.abrir();

        assertTrue(campo22.isAberto());
        assertTrue(campo11.isAberto());
    }

    @Test
    void testeAbrirComVizinhosMinados() {
        Campo campo11 = new Campo(1, 1);
        Campo campo12 = new Campo(1, 2);
        campo.minar();

        Campo campo22 = new Campo(2, 2);

        assertFalse(campo22.isAberto());
        assertFalse(campo11.isAberto());
        assertFalse(campo12.isAberto());

        campo.adicionarVizinho(campo11);
        campo.adicionarVizinho(campo12);
        campo.adicionarVizinho(campo22);

        campo.abrir();

        assertTrue(campo22.isAberto());
        assertFalse(campo11.isAberto());
    }
}
