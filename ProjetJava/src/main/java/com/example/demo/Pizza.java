package com.example.demo;

public class Pizza {
    private String nom;
    private double prix;
    private int nombreIngredients;

    // Constructeur
    public Pizza(String nom, double prix, int nombreIngredients) {
        this.nom = nom;
        this.prix = prix;
        this.nombreIngredients = nombreIngredients;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getNombreIngredients() {
        return nombreIngredients;
    }

    public void setNombreIngredients(int nombreIngredients) {
        this.nombreIngredients = nombreIngredients;
    }

}


