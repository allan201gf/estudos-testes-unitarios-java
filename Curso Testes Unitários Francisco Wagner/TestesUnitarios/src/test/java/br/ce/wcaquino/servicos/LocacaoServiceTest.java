package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Test
    public void testeLocacao() throws Exception {

        //Cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Allan");
        Filme filme = new Filme("Filme 1", 1, 5.0);

        //Acao
        Locacao locacao = service.alugarFilme(usuario, filme);

        //Verificacao
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

    }

    @Test(expected = Exception.class)
    public void testeLocacaoFilmeSemEstoque() throws Exception {
        //Cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Allan");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        //Acao
        service.alugarFilme(usuario, filme);
    }

    @Test
    public void testeLocacaoFilmeSemEstoque2() {
        //Cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Allan");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        //Acao
        try {
            service.alugarFilme(usuario, filme);
            Assert.fail("Deveria ter lancado um escecao");
        } catch (Exception e) {
            error.checkThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

    @Test
    public void testeLocacaoFilmeSemEstoque3() {
        //Cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Allan");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        //Acao
        Locacao locacao;
        Exception exception = Assert.assertThrows(Exception.class,
                () -> service.alugarFilme(usuario, filme));
        MatcherAssert.assertThat(exception.getMessage(),is("Filme sem estoque"));
    }

}
