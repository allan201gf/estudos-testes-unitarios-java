package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

    //Como a classe de teste está na mesma estrutura de pastas, é possível acessar "protected"
    public String vPublica;
    protected String vProtegida;
    String vDefault;

    //Privado apenas dentro da classe
    private String vPrivada;

    public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
        if(filme.getEstoque() == 0) {
            throw new Exception("Filme sem estoque");
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