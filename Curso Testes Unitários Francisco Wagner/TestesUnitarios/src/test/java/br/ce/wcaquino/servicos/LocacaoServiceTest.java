package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueEsception;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

public class LocacaoServiceTest {

    private LocacaoService service;

    //Caso não seja static, o Junit não reinicializa.
    private static int contador = 0;


    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    //Executa antes de cada metodo, troque Before por After para executar após cada método
    @Before
    public void setup() {
        service = new LocacaoService();
        contador++;
        System.out.println("Teste número: " + contador);
    }

    @BeforeClass
    public static void setupClass() {
        System.out.println("Executa apenas uma vez");
    }

    @Test
    public void deveAlugarFilme() throws Exception {

        //Cenario
        Usuario usuario = new Usuario("Allan");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

        //Acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //Verificacao
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

    }

    @Test(expected = FilmeSemEstoqueEsception.class)
    public void naoDeveAlugarFilmeSemEstoque() throws Exception {
        //Cenario
        Usuario usuario = new Usuario("Allan");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

        //Acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueEsception {
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            error.checkThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws LocadoraException, FilmeSemEstoqueEsception {
        Usuario usuario = new Usuario("Allan");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        service.alugarFilme(usuario, null);
    }

    @Test
    public void devePagar75PctNoFilme3() throws LocadoraException, FilmeSemEstoqueEsception {
        Usuario usuario = new Usuario("Allan");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0));

        Locacao resultado = service.alugarFilme(usuario, filmes);

        // 4+4+3 = 11
        error.checkThat(resultado.getValor(), is(11.0));
    }

    @Test
    public void devePagar50PctNoFilme4() throws LocadoraException, FilmeSemEstoqueEsception {
        Usuario usuario = new Usuario("Allan");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0));

        Locacao resultado = service.alugarFilme(usuario, filmes);

        // 4+4+3+2 = 13
        error.checkThat(resultado.getValor(), is(13.0));
    }

    @Test
    public void devePagar25PctNoFilme5() throws LocadoraException, FilmeSemEstoqueEsception {
        Usuario usuario = new Usuario("Allan");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0),
                new Filme("Filme 5", 2, 4.0));

        Locacao resultado = service.alugarFilme(usuario, filmes);

        // 4+4+3+2+1 = 14
        error.checkThat(resultado.getValor(), is(14.0));
    }

    @Test
    public void devePagar0PctNoFilme6() throws LocadoraException, FilmeSemEstoqueEsception {
        Usuario usuario = new Usuario("Allan");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0),
                new Filme("Filme 5", 2, 4.0),
                new Filme("Filme 6", 2, 4.0));

        Locacao resultado = service.alugarFilme(usuario, filmes);

        // 4+4+3+2+1 = 14
        error.checkThat(resultado.getValor(), is(14.0));
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws LocadoraException, FilmeSemEstoqueEsception {
        Usuario usuario = new Usuario("Allan");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

        Locacao retorno = service.alugarFilme(usuario, filmes);

        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);

    }
}
