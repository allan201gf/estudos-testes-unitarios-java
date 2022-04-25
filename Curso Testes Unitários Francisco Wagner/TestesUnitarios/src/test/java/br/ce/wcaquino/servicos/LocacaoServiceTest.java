package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueEsception;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

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

    @Test(expected = FilmeSemEstoqueEsception.class)
    public void testeLocacaoFilmeSemEstoque() throws Exception {
        //Cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Allan");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        //Acao
        service.alugarFilme(usuario, filme);
    }

    @Test
    public void testeLocacaoUsuarioVazio() throws FilmeSemEstoqueEsception {
        LocacaoService service = new LocacaoService();
        Filme filme = new Filme("Filme 1", 1, 5.0);

        try {
            service.alugarFilme(null, filme);
            Assert.fail();
        } catch (LocadoraException e) {
            error.checkThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    @Test
    public void testeLocacaoFilmeVazio() throws LocadoraException, FilmeSemEstoqueEsception {
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Allan");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        service.alugarFilme(usuario, null);
    }
}
