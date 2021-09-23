package by.gsu.epamlab.entities;


import java.util.Objects;

import static by.gsu.epamlab.Constants.NUMBER_HUNDRED;
import static by.gsu.epamlab.Constants.NUMBER_TEN;

public class Byn implements Comparable<Byn> {

    private final int value;

    public Byn() {
        this(0);
    }

    public Byn(int value) {
        this.value = value;
    }

    public Byn(int rubs, int coins) {
        this(rubs * NUMBER_HUNDRED + coins);
    }

    public Byn(Byn byn) {
        this(byn.value);
    }

    public int getRubs() {
        return this.value / NUMBER_HUNDRED;
    }

    public int getCoins() {
        return this.value % NUMBER_HUNDRED;
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
        return String.format(getRubs() + "." + value / NUMBER_TEN % NUMBER_TEN + value % NUMBER_TEN);
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
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }
}