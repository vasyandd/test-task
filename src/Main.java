import info.debatty.java.stringsimilarity.Jaccard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Main {
    private static final Path PATH_TO_INPUT_FILE = Paths.get("input.txt");
    private static final Path PATH_TO_OUTPUT_FILE = Paths.get("output.txt");

    public static void main(String[] args)  {
        try {
            List<String> stringsFromFile = Files.readAllLines(PATH_TO_INPUT_FILE);
            validInputStrings(stringsFromFile);
            int n = Integer.parseInt(stringsFromFile.get(0));
            Set<String> set1 = new LinkedHashSet<>(stringsFromFile.subList(1, n + 1));
            Set<String> set2 = new LinkedHashSet<>(stringsFromFile.subList(n + 2, stringsFromFile.size()));
            Files.write(PATH_TO_OUTPUT_FILE, getSimilarStrings(set1, set2));
        } catch (IOException e) {
            System.out.println("the exception during reading from the input file " +
                    "or writing in the output file" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void validInputStrings(List<String> stringsFromFile) {
        if (stringsFromFile.size() < 2)
            throw new IllegalArgumentException("the input file is too short");
        try {
            int n = Integer.parseInt(stringsFromFile.get(0));
            int m = Integer.parseInt(stringsFromFile.get(n + 1));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("n and m are wrote incorrect in the input file");
        }
    }

    /**
     * The Jaccard method is used to determine similarity of two sublist.
     *
     * @param set1 string set with the first elements
     * @param set2 string set with the second elements
     * @return a string LinkedList that contains the result of concatenation
     * of the most similar strings from set1 and set2 (in order of set1)
     */
    static List<String> getSimilarStrings(Set<String> set1, Set<String> set2) {
        Set<String> minSet, maxSet;
        // this flag is needed to save an order of set1 in result list
        boolean set1IsMax = false;
        if (set1.size() > set2.size()) {
            maxSet = set1;
            minSet = set2;
            set1IsMax = true;
        } else {
            maxSet = set2;
            minSet = set1;
        }
        Map<String, String> similaritySet1AndSet2 = new LinkedHashMap<>();
        Jaccard jaccard = new Jaccard();
        // add pattern string (?) for set1
        set1.forEach(s -> similaritySet1AndSet2.put(s, "?"));
        // start since the minSet, because each string from the minSet
        // will be concat with string of the maxSet (1-1)
        for (String s : minSet) {
            double maxSimilarity = 0;
            for (String s1 : maxSet) {
                double currentSimilarity = jaccard.similarity(s, s1);
                if (Double.compare(currentSimilarity, maxSimilarity) > 0) {
                    maxSimilarity = currentSimilarity;
                    if (set1IsMax) {
                        similaritySet1AndSet2.put(s1, s);
                    } else {
                        similaritySet1AndSet2.put(s, s1);
                    }
                }
            }
            maxSet.remove(similaritySet1AndSet2.get(s));
        }
        LinkedList<String> result = new LinkedList<>();
        similaritySet1AndSet2.forEach((k, v) -> result.add(k + ":" + v));
        // if the maxSet is set2, add remaining strings to the result list
        if (!set1IsMax) {
            maxSet.forEach(s -> result.add(s + ":?"));
        }

        return result;
    }
}
