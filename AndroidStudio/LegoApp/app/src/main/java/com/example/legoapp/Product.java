package com.example.legoapp;

public class Product
{
    String name;
    int pieces;
    double prize;

    String image;

    public Product(String name, int pieces, double prize, String image) {
        this.name = name;
        this.pieces = pieces;
        this.prize = prize;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
