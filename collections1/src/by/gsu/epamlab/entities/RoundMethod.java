package by.gsu.epamlab.entities;

public enum RoundMethod {

    CEIL {
        double roundFunction(double roundingValue) {
            return Math.ceil(roundingValue);
        }
    },
    FLOOR {
        double roundFunction(double roundingValue) {
            return Math.floor(roundingValue);
        }
    },
    ROUND {
        double roundFunction(double roundingValue) {
            return Math.round(roundingValue);
        }
    };

    abstract double roundFunction(double roundingValue);

    public int rounding(double roundingValue, int roundDigit) {
        int[] tenPowD = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};
        return (int) roundFunction(roundingValue / tenPowD[roundDigit]) * tenPowD[roundDigit];
    }
}