package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAO {

    private static final String DB_URL = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/parviz1u_projet";
    private static final String USERNAME = "parviz1u_appli";
    private static final String PASSWORD = "32216049";

    public PizzaDAO() {
        // Test de connexion à la base de données
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    public boolean isPizzaTableEmpty() {
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) AS total FROM pizza")) {

            if (rs.next()) {
                int count = rs.getInt("total");
                return count == 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification si la table pizza est vide : " + e.getMessage());
        }
        return true;
    }

    public void ajouterPizzasInitiales(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] elements = line.split(",");
                    if (elements.length != 3) {
                        throw new IllegalArgumentException("Format de ligne incorrect");
                    }
                    String name = elements[0];
                    double price = Double.parseDouble(elements[1]);
                    int numberOfIngredients = Integer.parseInt(elements[2]);

                    Pizza pizza = new Pizza(name, price, numberOfIngredients);
                    ajouterPizza(pizza);
                } catch (Exception e) {
                    System.err.println("Erreur de format dans le fichier CSV : " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Pour récupérer la liste des pizzas
    public List<Pizza> getPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM pizza")) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                int nombreIngredients = rs.getInt("nombre_ingredients");
                pizzas.add(new Pizza(nom, prix, nombreIngredients));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des pizzas : " + e.getMessage());
        }
        return pizzas;
    }

    // Pour ajouter une pizza
    public void ajouterPizza(Pizza pizza) {
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement pst = con.prepareStatement("INSERT INTO pizza (nom, prix, nombre_ingredients) VALUES (?, ?, ?)")) {

            pst.setString(1, pizza.getNom());
            pst.setDouble(2, pizza.getPrix());
            pst.setInt(3, pizza.getNombreIngredients());

            pst.executeUpdate();
            System.out.println("Pizza ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la pizza : " + e.getMessage());
        }
    }

    // Pour supprimer une pizza spécifique en fonction de son nom
    public void supprimerPizzaParNom(String nomPizza) {
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement pst = con.prepareStatement("DELETE FROM pizza WHERE nom = ?")) {
            pst.setString(1, nomPizza);
            pst.executeUpdate();
            System.out.println("Pizza supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la pizza : " + e.getMessage());
        }
    }

    // Pour supprimer toutes les pizzas
    public void supprimerToutesPizzas() {
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement st = con.createStatement()) {

            st.executeUpdate("DELETE FROM pizza");
            System.out.println("Toutes les pizzas ont été supprimées avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression des pizzas : " + e.getMessage());
        }
    }
    public void supprimerPizza(Pizza pizza) {
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement pst = con.prepareStatement("DELETE FROM pizza WHERE nom = ?")) {
            pst.setString(1, pizza.getNom());
            pst.executeUpdate();
            System.out.println("Pizza supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la pizza : " + e.getMessage());
        }
    }

}
