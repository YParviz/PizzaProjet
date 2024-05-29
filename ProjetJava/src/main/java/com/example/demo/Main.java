package com.example.demo;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PizzaDAO pizzaDAO = new PizzaDAO();


        Pizza newPizza = new Pizza("Non", 8.99, 3);
        pizzaDAO.ajouterPizza(newPizza);


        List<Pizza> pizzas = pizzaDAO.getPizzas();
        System.out.println("Liste des pizzas :");
        for (Pizza pizza : pizzas) {
            System.out.println(pizza.getNom() + " - " + pizza.getPrix() + " - " + pizza.getNombreIngredients());
        }


    }
}