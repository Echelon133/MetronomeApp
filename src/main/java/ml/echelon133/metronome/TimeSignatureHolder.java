package ml.echelon133.metronome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TimeSignatureHolder {
    private static Map<String, Integer> timeSignatures;

    static {
        timeSignatures = new HashMap<>();
        timeSignatures.put("2/4", 2);
        timeSignatures.put("3/4", 3);
        timeSignatures.put("4/4", 4);
        timeSignatures.put("5/4", 5);
        timeSignatures.put("6/4", 6);
        timeSignatures.put("7/4", 7);
    }

    public static List<String> getAllSignatureNames() {
        List<String> names = timeSignatures
                .entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
        return names;
    }

    public static Integer getNumberOfBeatsInSignature(String name) {
        return timeSignatures.get(name);
    }
}
