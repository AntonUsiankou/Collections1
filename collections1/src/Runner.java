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
            Map<Purchase, Week> firstPurchaseDayMap = new HashMap<>();
            Map<Purchase, Week> lastPurchaseDayMap = new HashMap<>();
            List<PricePurchase> pricePurchasesList = new ArrayList<>();
            Map<Week, List<Purchase>> enumMap = new EnumMap<>(Week.class);

            while (sc.hasNext()) {
                Purchase purchase = PurchaseReader.getPurchaseFromFactory(sc.next());
                Week week = Week.valueOf(sc.next());
                //1)
                if (!lastPurchaseDayMap.containsKey(purchase)) {
                    firstPurchaseDayMap.put(purchase, week);
                }
                //3)
                lastPurchaseDayMap.put(purchase, week);
                //10)
                if (purchase.getClass() == PricePurchase.class) {
                    pricePurchasesList.add((PricePurchase) purchase);
                }
                //12)
                List<Purchase> purchaseList = enumMap.get(week);
                if (purchaseList == null) {
                    enumMap.put(week, purchaseList = new ArrayList<>());
                }
                purchaseList.add(purchase);

            }
            //2)
            print(PURCHASE_FIRST_MAP_MESSAGE, firstPurchaseDayMap);
            //4)
            print(PURCHASE_LAST_MAP_MESSAGE, lastPurchaseDayMap);
            //5)
            Purchase searchPurchaseBread = new Purchase("bread", new Byn(155), 1);
            search(firstPurchaseDayMap, searchPurchaseBread);
            search(lastPurchaseDayMap, searchPurchaseBread);
            //6)
            Purchase searchPurchaseBreadNewPrice = new Purchase("bread", new Byn(170), 1);
            search(lastPurchaseDayMap, searchPurchaseBreadNewPrice);
            //7)
            removeEntries(firstPurchaseDayMap, MEAT, Week.MONDAY);
            //8)
            removeEntries(lastPurchaseDayMap, EMPTY, Week.FRIDAY);
            //9)
            print(PURCHASE_FIRST_MAP_MESSAGE, firstPurchaseDayMap);
            print(PURCHASE_LAST_MAP_MESSAGE, firstPurchaseDayMap);
            //11)
            System.out.println(TOTAL_COST + PRICE_PURCHASE_IS + calculateTotalCost(pricePurchasesList));
            //13)
            print(WEEKDAY_PURCHASES_ENUM_MAP, enumMap);
            for (Map.Entry<Week, List<Purchase>> entry : enumMap.entrySet()) {
                System.out.println(TOTAL_COST + entry.getKey() + IS + calculateTotalCost(entry.getValue()));
            }
            //14)
            System.out.println(TOTAL_COST + PRICE_PURCHASE_IS + calculateTotalCost(pricePurchasesList));
            //15)
            search(enumMap, Week.MONDAY);

        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND_MESSAGE);
        }
    }

    private static <K, V> void print(String header, Map<K, V> map) {
        System.out.println(PRINT_THE_MAP + header);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + Constants.DASH + entry.getValue());
        }
        System.out.println(SEPARATOR_LINE);
    }

    private static Byn calculateTotalCost(List<? extends Purchase> purchases) {
        Byn totalCost = new Byn();
        for (Purchase purchase : purchases) {
            totalCost = totalCost.addition(purchase.getCost());
        }
        System.out.println(SEPARATOR_LINE);
        return totalCost;
    }

    private static <K extends Purchase, V> void removeEntries(Map<K, V> map, String productName, Week week) {
        for (Iterator<Map.Entry<K, V>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<K, V> pair = it.next();
            K key = pair.getKey();
            V value = pair.getValue();
            if (key.getProductName().equals(productName) || value.equals(week)) {
                it.remove();
            }
        }
    }

    private static <V, K> void search(Map<K, V> map, K key) {
        V temp = map.get(key);
        if(temp != null) {
            System.out.println(temp);
            System.out.println();
        }else {
            System.out.println(NOT_FOUND);
        }
        System.out.println(SEPARATOR_LINE);

    }
}