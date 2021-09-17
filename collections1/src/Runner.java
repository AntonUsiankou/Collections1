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
            Map<Purchase, Week> firstMapPurchaseWeekdays = new HashMap<>();
            Map<Purchase, Week> lastMapPurchaseWeekdays = new HashMap<>();
            Map<Week, List<Purchase>> weekdaysPurchasesMap = new EnumMap<>(Week.class);
            List<PricePurchase> pricePurchasesList = new ArrayList<>();

            while (sc.hasNext()) {
                Purchase purchase = PurchaseReader.getPurchaseFromFactory(sc.nextLine());
                Week week = Week.valueOf(sc.nextLine());
                //1)
                if (!lastMapPurchaseWeekdays.containsKey(purchase)) {
                    firstMapPurchaseWeekdays.put(purchase, week);
                }
                //3)
                lastMapPurchaseWeekdays.put(purchase, week);
                //10)
                if (purchase.getClass() == PricePurchase.class) {
                    pricePurchasesList.add((PricePurchase) purchase);
                }
                //12)
                List<Purchase> purchaseList = weekdaysPurchasesMap.get(week);
                if (purchaseList == null) {
                    weekdaysPurchasesMap.put(week, purchaseList = new ArrayList<>());
                }
                purchaseList.add(purchase);

            }
            //2)
            print(PURCHASE_FIRST_MAP_MESSAGE, firstMapPurchaseWeekdays);
            //4)
            print(PURCHASE_LAST_MAP_MESSAGE, lastMapPurchaseWeekdays);
            //5)
            Purchase searchPurchaseBread = new Purchase("bread", new Byn(155), 0);
            search(firstMapPurchaseWeekdays, searchPurchaseBread);
            search(lastMapPurchaseWeekdays, searchPurchaseBread);
            //6)
            Purchase searchPurchaseBreadNewPrice = new Purchase("bread", new Byn(170), 0);
            search(firstMapPurchaseWeekdays, searchPurchaseBreadNewPrice);
            //7)
            removeEntries(firstMapPurchaseWeekdays, MEAT, Week.MONDAY);
            //8)
            removeEntries(lastMapPurchaseWeekdays, EMPTY, Week.FRIDAY);
            //9)
            print(PURCHASE_FIRST_MAP_MESSAGE, firstMapPurchaseWeekdays);
            print(PURCHASE_LAST_MAP_MESSAGE, firstMapPurchaseWeekdays);
            //11)
            System.out.println(TOTAL_COST + PRICE_PURCHASE_IS + calculateTotalCost(pricePurchasesList));
            //13)
            print(WEEKDAY_PURCHASES_ENUM_MAP, weekdaysPurchasesMap);
            for (Map.Entry<Week, List<Purchase>> entry : weekdaysPurchasesMap.entrySet()) {
                System.out.println(TOTAL_COST + entry.getKey() + " is " + calculateTotalCost(entry.getValue()));
            }
            //14)
            System.out.println(TOTAL_COST + PRICE_PURCHASE_IS + calculateTotalCost(pricePurchasesList));
            //15)
            search(weekdaysPurchasesMap, Week.MONDAY);

        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND_MESSAGE);
        }
    }

    private static <K, V> void print(String header, Map<K, V> map) {
        System.out.println(PRINT_THE_MAP + header);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + Constants.DASH + entry.getValue());
        }
        System.out.println("--------------------------------------------");
    }

    private static Byn calculateTotalCost(List<? extends Purchase> purchases) {
        Byn totalCost = new Byn();
        for (Purchase purchase : purchases) {
            totalCost = totalCost.addition(purchase.getCost());
        }
        System.out.println("--------------------------------------------");
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
        V value = map.get(key);
        System.out.println("Value " + key + " is " + (value != null ? value : "not found"));
        System.out.println("--------------------------------------------");
    }


}