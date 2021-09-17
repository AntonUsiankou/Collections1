package by.gsu.epamlab.entities;

import by.gsu.epamlab.enums.Fields;

import static by.gsu.epamlab.Constants.DELEMITER;

public class PurchaseReader {

    private final static int PURCHASE_FIELDS_NUMBER = Purchase.class.getDeclaredFields().length;
    private final static int DISCOUNT_PURCHASE_FIELDS_NUMBER = PURCHASE_FIELDS_NUMBER
            + PricePurchase.class.getDeclaredFields().length;


    public static Purchase getPurchaseFromFactory(String csvLine) {
        Purchase purchase = new Purchase();
        String[] elements = csvLine.split(DELEMITER);
        String name = elements[Fields.NAME.ordinal()];
        Byn price = new Byn(Integer.parseInt(elements[Fields.PRICE.ordinal()]));
        int number = Integer.parseInt(elements[Fields.NUMBER.ordinal()]);

        if (elements.length == PURCHASE_FIELDS_NUMBER) {
            purchase = new Purchase(name, price, number);
        } else {
            if (elements.length == DISCOUNT_PURCHASE_FIELDS_NUMBER) {
                Byn discount = new Byn(Integer.parseInt(elements[Fields.DISCOUNT.ordinal()]));
                purchase = new PricePurchase(name, price, number, discount);
            }
        }
        return purchase;
    }
}
