import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CuttingProblem {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> table = readFile();
        int bandLen = 99000; //mm
        System.out.println("Długość pasma: " + bandLen + " mm");
        table.forEach((k, v) -> System.out.println(String.format("%-4s",v) + " elementów po " + k + " mm"));
//        logMap(table);
        int totalWaste = 0;
        Map<String, Integer> sets = new HashMap<>();
        while (!table.isEmpty()) {
            totalWaste += findOptimalSet(table, bandLen, sets);
        }
        sets.forEach((set, noOfRepetitions) -> System.out.println("Wytnij zestaw: [" + set.substring(0, set.length() - 2) + "] " + noOfRepetitions + " razy"));
        System.out.println("Suma zmarnowanego materiału: " + totalWaste + ", średnio " + totalWaste / sets.values().stream().reduce(Integer::sum).orElse(-1) + " na cięcie");
    }

    private static int findOptimalSet(TreeMap<Integer, Integer> table, int bandLen, Map<String, Integer> sets) {
        int tempLen = 0;
        StringBuilder set = new StringBuilder();
        List<Integer> entriesToRemove = new ArrayList<>();
        for (Map.Entry<Integer, Integer> en : table.descendingMap().entrySet()) {
            if (tempLen + en.getKey() <= bandLen) {
                if (en.getValue() > 1) {
                    for (int i = 0; i < en.getValue(); ++i) {
                        if (tempLen + en.getKey() <= bandLen) {
                            tempLen += en.getKey();
                            set.append(en.getKey()).append(", ");
                            en.setValue(en.getValue() - 1);
                            i--;//todo to jest chyba strasznie chujowa praktyka xd wymysl cos lepszego debilu
                        }
                    }
                } else {
                    tempLen += en.getKey();
                    set.append(en.getKey()).append(", ");
                    en.setValue(en.getValue() - 1);
                }
            }
            if (en.getValue() == 0) {
                entriesToRemove.add(en.getKey());
            }
        }
        putSet(sets, set);
        entriesToRemove.forEach(table::remove);
//        logMap(table);

//        System.out.println("SET: " + set + "waste: " + (bandLen - tempLen));
        return bandLen - tempLen;
    }

    private static void putSet(Map<String, Integer> sets, StringBuilder set) {
        if (sets.containsKey(set.toString())) {
            sets.put(set.toString(), sets.get(set.toString()) + 1);
        }
        else {
            sets.put(set.toString(), 1);
        }
    }

    private static void logMap(TreeMap table) {
        System.out.println("******************************");
        table.descendingMap().forEach((k, v) -> System.out.println("LEFT: " + k + " " + v));
        System.out.println("******************************");
    }

    private static TreeMap<Integer, Integer> readFile() {
        TreeMap<Integer, Integer> table = new TreeMap<>();
        try {
            List<String> items = Files.readAllLines(Paths.get(".\\src\\main\\resources\\bands.csv"));
            items.forEach(item -> {
                String[] arr = item.split(",");
                table.put(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    private void writeResults(String output) {

    }
}