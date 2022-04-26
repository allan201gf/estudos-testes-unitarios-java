package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueEsception;
import br.ce.wcaquino.exceptions.LocadoraException;

import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

    //Como a classe de teste está na mesma estrutura de pastas, é possível acessar "protected"
    public String vPublica;
    protected String vProtegida;
    String vDefault;

    //Privado apenas dentro da classe
    private String vPrivada;

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueEsception, LocadoraException {
        if(usuario == null) {
            throw new LocadoraException("Usuario vazio");
        }

        if(filmes == null || filmes.isEmpty()) {
            throw new LocadoraException("Filme vazio");
        }

        for(Filme filme : filmes) {
            if(filme.getEstoque() == 0) {
                throw new FilmeSemEstoqueEsception();
            }
        }

        Locacao locacao = new Locacao();
        locacao.setFilme(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());

        Double valorTotal = 0d;
        for (int i = 0; i < filmes.size(); i++ ) {
            Filme filme = filmes.get(i);
            Double valorFilme = filme.getPrecoLocacao();

            if (i == 2) {
                valorFilme = valorFilme * 0.75;
            }
            if (i == 3) {
                valorFilme = valorFilme * 0.5;
            }
            if (i == 4) {
                valorFilme = valorFilme * 0.25;
            }
            if (i == 5) {
                valorFilme = 0.0;
            }

            valorTotal += valorFilme;
        }
        locacao.setValor(valorTotal);

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        //TODO adicionar método para salvar

        return locacao;
    }
}