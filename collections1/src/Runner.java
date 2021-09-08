import by.gsu.epamlab.Constants;
import by.gsu.epamlab.entities.Byn;
import by.gsu.epamlab.entities.PricePurchase;
import by.gsu.epamlab.entities.Purchase;
import by.gsu.epamlab.entities.PurchaseReader;
import by.gsu.epamlab.enums.Week;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static by.gsu.epamlab.Constants.*;

public class Runner {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new FileReader(FILE_PATH))) {
            HashMap<Purchase, Week> purchaseWeekdaysFirstMap = new HashMap<>();
            HashMap<Purchase, Week> purchaseWeekdaysLastMap = new HashMap<>();
            Map<Week, List<Purchase>> weekdaysPurchasesMap = new EnumMap<>(Week.class);
            List<PricePurchase> pricePurchasesList = new ArrayList<>();

            while (sc.hasNext()) {
                Purchase purchase = PurchaseReader.getPurchaseFromFactory(sc.nextLine());
                Week week = Week.valueOf(sc.nextLine());

                purchaseWeekdaysLastMap.put(purchase, week);

                if (!purchaseWeekdaysFirstMap.containsKey(purchase)) {
                    purchaseWeekdaysFirstMap.put(purchase, week);
                }

                List<Purchase> l = weekdaysPurchasesMap.get(week);
                if (l == null) {
                    weekdaysPurchasesMap.put(week, l = new ArrayList<>());
                }
                l.add(purchase);

                if (purchase.getClass() == PricePurchase.class) {
                    pricePurchasesList.add((PricePurchase) purchase);
                }
            }

            print(PURCHASE_FIRST_MAP_MESSAGE, purchaseWeekdaysFirstMap);
            print(PURCHASE_LAST_MAP_MESSAGE, purchaseWeekdaysLastMap);

            Purchase searchPurchaseBread = new Purchase("bread", 155, 1);
            search(purchaseWeekdaysFirstMap, searchPurchaseBread);
            search(purchaseWeekdaysLastMap, searchPurchaseBread);
            Purchase searchPurchaseBreadNewPrice = new Purchase("bread", 170,1);
            search(purchaseWeekdaysFirstMap, searchPurchaseBreadNewPrice);

            print(PURCHASE_FIRST_MAP_MESSAGE, purchaseWeekdaysFirstMap);
            print(PURCHASE_LAST_MAP_MESSAGE, purchaseWeekdaysLastMap);

            System.out.println(TOTAL_COST + PRICE_PURCHASE_IS + calculateTotalCost(pricePurchasesList));

            
        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND_MESSAGE);
        }
    }

    public static <K, V> void print(String header, Map<K, V> map) {
        System.out.println();
        System.out.println(PRINT_THE_MAP + header);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + Constants.DASH + entry.getValue());
        }
        System.out.println();
    }

    private static Byn calculateTotalCost(List<? extends Purchase> list) {
        Byn totalCost = new Byn(NUMBER_NULL);

        for (Purchase purchase : list) {
            totalCost.addition(purchase.getCost());
        }
        return totalCost;
    }

    private static void remove() {

    }

    public static <V, K> void search(Map<K, V> map, K key) {
        V value = map.get(key);
        System.out.println("Value " + key + " is " + (value != null ? value : "not found"));
    }
}

