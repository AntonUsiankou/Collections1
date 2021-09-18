package by.gsu.epamlab.entities;


import java.util.Objects;

public class Byn implements Comparable<Byn> {

    private final int value;

    public Byn() {
        this(0);
    }

    public Byn(int value) {
        this.value = value;
    }

    public Byn(int rubs, int coins) {
        this(rubs * 100 + coins);
    }

    public Byn(Byn byn) {
        this(byn.value);
    }

    public int getRubs() {
        return this.value / 100;
    }

    public int getCoins() {
        return this.value % 100;
    }

    public Byn addition(Byn byn) {
        return new Byn(value + byn.value);
    }

    public Byn subtraction(Byn byn) {
        return new Byn(value - byn.value);
    }

    public Byn multiply(int k) {
        return new Byn(value * k);
    }

    public Byn multiply(double k, RoundMethod roundMethod, int digits) {
        return new Byn(roundMethod.rounding(value * k, digits));
    }

    public Byn round(int digits) {
        return new Byn(round(RoundMethod.ROUND, digits));
    }

    public Byn round(RoundMethod roundMethod, int roundDigits) {
        return new Byn(roundMethod.rounding(value, roundDigits));
    }

    @Override
    public String toString() {
        return String.format(getRubs() + "." + value / 10 % 10 + value % 10);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Byn byn = (Byn) o;
        return this.value == byn.value;
    }

    @Override
    public int compareTo(Byn byn) {
        return this.value - byn.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}