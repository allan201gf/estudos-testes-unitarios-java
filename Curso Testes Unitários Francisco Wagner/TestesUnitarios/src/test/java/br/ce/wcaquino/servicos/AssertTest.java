package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {

    @Test
    public void test() {
        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals(1, 1);

        Assert.assertEquals("Erro de comparacao",1, 1);

        //Delta é a margem de erro para comparar
        Assert.assertEquals(0.51, 0.51, 0.01);
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        //não é possível comparar tipo primitivo com objeto (int vs Integer)
        int i1 = 5;
        Integer i2 = 5;
        Assert.assertEquals(Integer.valueOf(i1), i2);

        Assert.assertEquals("bola", "bola");
        Assert.assertNotEquals("bola", "bola2");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertTrue("bola".startsWith("bo"));

        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = u2;
        Usuario u4 = null;

        // è necessário adicionar o método equals na classe
        Assert.assertEquals(u1, u2);

        // Compara se aponta para a mesma instancia
        Assert.assertSame(u2, u3);

        Assert.assertNull(u4);
        Assert.assertNotNull(u1);
    }

}
