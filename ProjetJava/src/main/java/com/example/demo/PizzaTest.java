package com.example.demo;

import com.example.demo.Pizza;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PizzaTest {

    @Test
    public void testConstructeur() {
        Pizza pizza = new Pizza("Margherita", 8.99, 5);
        assertEquals("Margherita", pizza.getNom());
        assertEquals(8.99, pizza.getPrix(), 0.001);
        assertEquals(5, pizza.getNombreIngredients());
    }

    @Test
    public void testGettersEtSetters() {
        Pizza pizza = new Pizza("Margherita", 8.99, 5);

        pizza.setNom("Quattro Stagioni");
        assertEquals("Quattro Stagioni", pizza.getNom());

        pizza.setPrix(10.99);
        assertEquals(10.99, pizza.getPrix(),0.001);

        pizza.setNombreIngredients(7);
        assertEquals(7, pizza.getNombreIngredients());
    }
}