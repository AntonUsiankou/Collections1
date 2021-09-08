package by.gsu.epamlab.entities;

import java.util.Scanner;

import static by.gsu.epamlab.Constants.*;

public class PricePurchase extends Purchase {

    private Byn discount;


    public PricePurchase(String productName, int price, int numberUnits, int discount) {
        super(productName, price, numberUnits);
        this.discount = new Byn(discount);
    }

    @Override
    protected String fieldsToString() {
        return super.fieldsToString() + DELEMITER + discount;
    }

    @Override
    public Byn getCost() {
        return new Byn(getPrice()).subtraction(discount).multiply(getNumberUnits());
    }
}