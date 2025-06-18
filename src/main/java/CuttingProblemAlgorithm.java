import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CuttingProblemAlgorithm {

    private int bandLength;

    public void compute() {
        TreeMap<Integer, Integer> table = readFile();
        System.out.println("Długość pasma: " + bandLength + " mm");
        table.forEach((k, v) -> System.out.println(String.format("%-4s", v) + " elementów po " + k + " mm"));
        int totalWaste = 0;
        Map<String, Integer> sets = new HashMap<>();
        while (!table.isEmpty()) {
            totalWaste += findOptimalSet(table, bandLength, sets);
        }
        writeResults(sets, totalWaste);
    }

    private int findOptimalSet(TreeMap<Integer, Integer> table, int bandLen, Map<String, Integer> sets) {
        int tempLen = 0;
        StringBuilder set = new StringBuilder();
        List<Integer> entriesToRemove = new ArrayList<>();
        for (Map.Entry<Integer, Integer> en : table.descendingMap().entrySet()) {
            if (tempLen + en.getKey() <= bandLen) {
                if (en.getValue() > 1) {
                    tempLen = iterateOverPossibleSets(bandLen, en, tempLen, set);
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
        return bandLen - tempLen;
    }

    private int iterateOverPossibleSets(int bandLen, Map.Entry<Integer, Integer> en, int tempLen, StringBuilder set) {
        for (int i = 0; i < en.getValue(); ++i) {
            if (tempLen + en.getKey() <= bandLen) {
                tempLen += en.getKey();
                set.append(en.getKey()).append(", ");
                en.setValue(en.getValue() - 1);
                i--;
            }
        }
        return tempLen;
    }

    private void putSet(Map<String, Integer> sets, StringBuilder set) {
        if (sets.containsKey(set.toString())) {
            sets.put(set.toString(), sets.get(set.toString()) + 1);
        } else {
            sets.put(set.toString(), 1);
        }
    }

    private void logMap(TreeMap table) {
        System.out.println("******************************");
        table.descendingMap().forEach((k, v) -> System.out.println("LEFT: " + k + " " + v));
        System.out.println("******************************");
    }

    private TreeMap<Integer, Integer> readFile() {
        TreeMap<Integer, Integer> table = new TreeMap<>();
        try {
            List<String> items = Files.readAllLines(Paths.get(".\\src\\main\\resources\\bands.csv"));
            bandLength = Integer.parseInt(items.remove(0));
            items.forEach(item -> {
                String[] arr = item.split(",");
                table.put(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    private void writeResults(Map<String, Integer> sets, int totalWaste) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\main\\resources\\output.csv", false))) {
            String summary = "Suma zmarnowanego materiału: " + totalWaste + ", średnio " + totalWaste / sets.values().stream().reduce(Integer::sum).orElse(-1) + " na cięcie";
            for (Map.Entry<String, Integer> set : sets.entrySet()) {
                writer
                        .append("Wytnij zestaw: [")
                        .append(set.getKey().substring(0, set.getKey().length() - 2)).append("] ")
                        .append(String.valueOf(set.getValue()))
                        .append(" razy\n");
            }
            writer.append(summary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
