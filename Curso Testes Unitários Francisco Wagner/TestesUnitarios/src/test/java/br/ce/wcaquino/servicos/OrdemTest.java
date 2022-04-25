package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//Executa os testes em ordem alfabetica
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {

    public static int contador = 0;

    @Test
    public void t1inicia() {
        contador = 1;
    }

    @Test
    public void t2verifica() {
        Assert.assertEquals(1, contador);
    }

}
