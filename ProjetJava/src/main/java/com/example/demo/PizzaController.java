package com.example.demo;

import com.example.demo.Pizza;
import com.example.demo.PizzaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.List;

public class PizzaController {

    @FXML
    private TableView<Pizza> pizzaTable;

    @FXML
    private TableColumn<Pizza, String> nameColumn;

    @FXML
    private TableColumn<Pizza, Double> priceColumn;

    @FXML
    private TableColumn<Pizza, Integer> ingredientsColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField ingredientsTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteButton; // Nouveau bouton pour supprimer une ligne

    private PizzaDAO pizzaDAO;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("nombreIngredients"));

        // Initialize pizzaDAO
        pizzaDAO = new PizzaDAO();

        // Vérifie si la table pizza est vide avant de charger les données du CSV
        if (pizzaDAO.isPizzaTableEmpty()) {
            pizzaDAO.ajouterPizzasInitiales("./pizzas.csv"); // Ajoute les données initiales à partir du fichier CSV
        } else {
            updateTable();
        }
    }

    private void updateTable() {
        pizzaTable.getItems().clear();
        List<Pizza> pizzas = pizzaDAO.getPizzas();
        pizzaTable.getItems().addAll(pizzas);
    }

    @FXML
    private void AjouterButton() {
        String name = nameTextField.getText();
        String priceText = priceTextField.getText();
        double price;
        // Vérifier si le texte contient une virgule décimale
        if (!priceText.contains(".")) {
            // Ajouter ".0" à la fin pour transformer en un nombre à virgule
            priceText += ".0";
        }
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            // Gérer le cas où la conversion échoue
            System.err.println("Erreur: Entrée invalide pour le prix.");
            // Vous pouvez afficher un message d'erreur à l'utilisateur ici si nécessaire
            return; // Sortir de la méthode pour éviter de continuer avec une valeur de prix invalide
        }

        int numberOfIngredients = 0; // Valeur par défaut
        try {
            numberOfIngredients = Integer.parseInt(ingredientsTextField.getText());
        } catch (NumberFormatException e) {
            // Gérer le cas où la conversion échoue
            System.err.println("Erreur: Entrée invalide pour le nombre d'ingrédients.");
            // Vous pouvez afficher un message d'erreur à l'utilisateur ici si nécessaire
            return; // Sortir de la méthode pour éviter de continuer avec une valeur d'ingrédients invalide
        }

        Pizza pizza = new Pizza(name, price, numberOfIngredients);
        pizzaDAO.ajouterPizza(pizza);

        // Ajouter la nouvelle pizza à la liste existante
        pizzaTable.getItems().add(pizza);

        // Mettre à jour la liste après chaque ajout
        updateTable();

        nameTextField.clear();
        priceTextField.clear();
        ingredientsTextField.clear();
    }



    @FXML
    private void ViderButton() {
        pizzaDAO.supprimerToutesPizzas();
        updateTable();
    }

    @FXML
    private void SupprimerLigneSelectionnee() {
        Pizza selectedPizza = pizzaTable.getSelectionModel().getSelectedItem();
        if (selectedPizza != null) {
            pizzaDAO.supprimerPizza(selectedPizza);
            pizzaTable.getItems().remove(selectedPizza);
        } else {
            // Afficher une alerte si aucune ligne n'est sélectionnée
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
            alert.showAndWait();
        }
    }
}
