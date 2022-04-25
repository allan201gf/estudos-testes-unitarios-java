package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueEsception;
import br.ce.wcaquino.exceptions.LocadoraException;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

    //Como a classe de teste está na mesma estrutura de pastas, é possível acessar "protected"
    public String vPublica;
    protected String vProtegida;
    String vDefault;

    //Privado apenas dentro da classe
    private String vPrivada;

    public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueEsception, LocadoraException {
        if(usuario == null) {
            throw new LocadoraException("Usuario vazio");
        }

        if(filme == null) {
            throw new LocadoraException("Filme vazio");
        }

        if(filme.getEstoque() == 0) {
            throw new FilmeSemEstoqueEsception();
        }

        Locacao locacao = new Locacao();
        locacao.setFilme(filme);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(filme.getPrecoLocacao());

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        //TODO adicionar método para salvar

        return locacao;
    }
}