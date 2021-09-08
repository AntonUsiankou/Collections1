package by.gsu.epamlab.entities;

public class Purchase implements Comparable<Purchase> {

    private String productName;
    private Byn price;
    private int numberUnits;

    public Purchase() {
    }

    public Purchase(String productName, int price, int numberUnits) {
        this.productName = productName;
        this.price = new Byn(price);
        this.numberUnits = numberUnits;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Byn getPrice() {
        return price;
    }

    public void setPrice(Byn price) {
        this.price = price;
    }

    public int getNumberUnits() {
        return numberUnits;
    }

    public void setNumberUnits(int numberUnits) {
        this.numberUnits = numberUnits;
    }

    public Byn getCost() {
        return new Byn(price).multiply(numberUnits);
    }

    protected String fieldsToString() {
        return productName + ";" + price + ";" + numberUnits;
    }

    @Override
    public String toString() {
        return fieldsToString() + ";" + getCost();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;
        Purchase purchase = (Purchase) o;

        return productName.equals(purchase.productName) &&
                price.equals(purchase.price);
    }

    @Override
    public int compareTo(Purchase o) {
        return 0;
    }
}
