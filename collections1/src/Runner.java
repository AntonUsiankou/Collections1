import by.gsu.epamlab.Constants;
import by.gsu.epamlab.entities.*;
import by.gsu.epamlab.enums.Week;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static by.gsu.epamlab.Constants.*;

public class Runner {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new FileReader(FILE_PATH))) {
            Map<Purchase, Week> firstPurchasesMap = new HashMap<>();
            Map<Purchase, Week> lastPurchasesMap = new HashMap<>();
            Map<Week, List<Purchase>>  dayEnumPurchasesMap = new EnumMap<>(Week.class);
            List<PricePurchase> priceDiscountPurchasesList = new ArrayList<>();

            while (sc.hasNext()) {
                Purchase purchase = PurchaseReader.getPurchaseFromFactory(sc.next());
                Week week = Week.valueOf(sc.next());
                //1)
                if (!lastPurchasesMap.containsKey(purchase)) {
                    firstPurchasesMap.put(purchase, week);
                }
                //3)
                lastPurchasesMap.put(purchase, week);
                //12)
                if (purchase.getClass() == PricePurchase.class) {
                    priceDiscountPurchasesList.add((PricePurchase) purchase);
                }
                //10)
                List<Purchase> purchaseList = dayEnumPurchasesMap.get(week);
                if (purchaseList == null) {
                    dayEnumPurchasesMap.put(week, purchaseList = new ArrayList<>());
                }
                purchaseList.add(purchase);
            }
            //2)
            print(PURCHASE_FIRST_MAP_MESSAGE, firstPurchasesMap);
            //4)
            print(PURCHASE_LAST_MAP_MESSAGE, lastPurchasesMap);
            //5)
            Purchase searchPurchaseBread = new Purchase("bread", new Byn(155), 1);
            search(firstPurchasesMap, searchPurchaseBread);
            search(lastPurchasesMap, searchPurchaseBread);
            //6)
            Purchase searchPurchaseBreadNewPrice = new Purchase("bread", new Byn(170), 1);
            search(lastPurchasesMap, searchPurchaseBreadNewPrice);

            //7)
            remove(firstPurchasesMap, new EntryChecker<Purchase, Week>() {
                @Override
                public boolean check(Map.Entry<Purchase, Week> entry) {
                    Purchase purchaseCheck = new Purchase("meat",new Byn(0), 0);
                    return entry.getKey().getProductName().equals(purchaseCheck.getProductName());
                }
            });
            //8)
            remove(lastPurchasesMap, new EntryChecker() {
                @Override
                public boolean check(Map.Entry entry) {
                    Week weekCheck = Week.FRIDAY;
                    return entry.getKey() == weekCheck;
                }
            });

            //9)
            print(PURCHASE_FIRST_MAP_MESSAGE, firstPurchasesMap);
            print(PURCHASE_LAST_MAP_MESSAGE, firstPurchasesMap);
            //11)
            System.out.println(TOTAL_COST + PRICE_PURCHASE_IS + calculateTotalCost(priceDiscountPurchasesList));
            //13)
            print(WEEKDAY_PURCHASES_ENUM_MAP, dayEnumPurchasesMap);
            for (Map.Entry<Week, List<Purchase>> entry : dayEnumPurchasesMap.entrySet()) {
                System.out.println(TOTAL_COST + entry.getKey() + IS + calculateTotalCost(entry.getValue()));
            }
            //14)
            System.out.println(TOTAL_COST + PRICE_PURCHASE_IS + calculateTotalCost(priceDiscountPurchasesList));
            //15)
            search(dayEnumPurchasesMap, Week.MONDAY);

            //additional task)
            remove(dayEnumPurchasesMap, new EntryChecker<Week, List<Purchase>>() {
                @Override
                public boolean check(Map.Entry<Week, List<Purchase>> entry) {
                    Purchase purchaseCheckMilk = new Purchase("milk", new Byn(0), 0);
                    List<Purchase> purList = entry.getValue();
                    for(Purchase p : purList){
                        if(p.getProductName().equals(purchaseCheckMilk.getProductName())){
                            return true;
                        }
                    }
                    return false;
                }
            });
            print(WEEK_PURCHASES_ENUM_MAP_WITHOUT_MILK, dayEnumPurchasesMap);

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
    public interface EntryChecker<K, V> {
        boolean check(Map.Entry<K, V> entry);
    }
    private static <K, V> void remove(Map<K, V> map, EntryChecker cheker) {
        for (Iterator<Map.Entry<K, V>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<K, V> entry = it.next();
            if(cheker.check(entry)){
                it.remove();
            }
        }
    }
}