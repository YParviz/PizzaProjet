package com.example.demo;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class PizzaDAOTest {

    private static final String DB_URL = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/parviz1u_projet";
    private static final String USERNAME = "parviz1u_appli";
    private static final String PASSWORD = "32216049";

    private PizzaDAO pizzaDAO;

    public PizzaDAOTest() throws SQLException {
        pizzaDAO = new PizzaDAO();
    }

    private void NetoieBaseDonnées() throws SQLException {
        // Permet de nettoyer la base de données avant chaque test
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement st = con.createStatement()) {
            st.executeUpdate("DELETE FROM pizza");
        }
    }

    @Test
    public void testAjouterPizza() throws SQLException {
        NetoieBaseDonnées();

        List<Pizza> pizzasAvant = pizzaDAO.getPizzas();
        int nombreAvant = pizzasAvant.size();

        Pizza nouvellePizza = new Pizza("Indienne", 8.5, 3);
        pizzaDAO.ajouterPizza(nouvellePizza);

        List<Pizza> pizzasApres = pizzaDAO.getPizzas();
        int nombreApres = pizzasApres.size();

        assertEquals(nombreAvant + 1, nombreApres, "Le nombre de pizzas après l'ajout doit être incrémenté de 1");
    }

    @Test
    public void testSupprimerToutesPizzas() throws SQLException {
        NetoieBaseDonnées();

        Pizza pizza1 = new Pizza("Americaine", 15.0, 6);
        Pizza pizza2 = new Pizza("Indienne", 7.0, 4);
        Pizza pizza3 = new Pizza("Italienne", 7.0, 4);
        pizzaDAO.ajouterPizza(pizza1);
        pizzaDAO.ajouterPizza(pizza2);
        pizzaDAO.ajouterPizza(pizza3);

        List<Pizza> pizzasAvant = pizzaDAO.getPizzas();
        assertEquals(3, pizzasAvant.size(), "Il doit y avoir 3 pizzas avant la suppression");

        pizzaDAO.supprimerToutesPizzas();

        List<Pizza> pizzasApres = pizzaDAO.getPizzas();
        assertEquals(0, pizzasApres.size(), "Il ne doit plus y avoir de pizzas après la suppression");
    }
}
