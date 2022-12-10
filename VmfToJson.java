import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class VmfToJson {
    private static Map<String, Object> parseEntry(Map<String, Object> parent, List<String> section) {
        Map<String, Object> entries = new HashMap<String, Object>();

        int indent = 0;
        String currentSectionName = null;
        for (int i = 0; i < section.size(); i++) {
            String line = section.get(i);
            if (line.equals("{")) {
                if (indent == 0) {
                    currentSectionName = section.get(i - 1);
                    int start = i + 1;
                }
                indent += 1;
            } else if (line.equals("}")) {
                indent -= 1;
                if (indent == 0) {
                    int stop = i;
                    if (entries.containsKey(currentSectionName)) {
                        List<List<Integer>> existingValue = (List<List<Integer>>) entries.get(currentSectionName);
                        existingValue.add(List.of(start, stop));
                        entries.put(currentSectionName, existingValue);
                    } else {
                        entries.put(currentSectionName, List.of(List.of(start, stop)));
                    }
                }
            } else {
                if (i < section.size() - 1) {
                    if (section.get(i + 1).equals("{")) {
                        // do nothing
                    } else if (indent == 0) {
                        String[] pair = parseKeyValuePair(line);
                        parent.putIfAbsent(pair[0], pair[1]);
                    }
                } else {
                    if (indent == 0) {
                        String[] pair = parseKeyValuePair(line);
                        parent.putIfAbsent(pair[0], pair[1]);
                    }
                }
            }
        }

        for (String entry : entries.keySet()) {
            if (((List<List<Integer>>) entries.get(entry)).size() > 1) {
                parent.putIfAbsent(entry, new ArrayList<Map<String, Object>>());
                for (List<Integer> part : (List<List<Integer>>) entries.get(entry)) {
                    Map<String, Object> subDict = new HashMap<String, Object>();
                    parseEntry(subDict, section.subList(part.get(0), part.get(1)));
                    ((List<Map<String, Object>>) parent.get(entry)).add(subDict);
                }
            } else if (((List<List<Integer>>) entries.get(entry)).size() == 1) {
                parent.putIfAbsent(entry, new HashMap<String, Object>());
                parseEntry((Map<String, Object>) parent.get(entry), section.subList(((List<List<Integer>>) entries.get(entry)).get(0).get(0), ((List<List<Integer>>) entries.get(entry)).get(0)..get(1)));
}
}
    return parent;
}

private static String[] parseKeyValuePair(String string) {
    if (string.startsWith("\"") && string.endsWith("\"")) {
        String[] items = string.substring(1, string.length() - 1).split("\" \"");
        return items;
    }
    return new String[] {};
}

private static Map<String, Object> convertVmfToDict(String filepath) {
    List<String> vmf = new ArrayList<String>();

    // TODO: implement reading a file and adding each line to the list

    Map<String, Object> parent = new HashMap<String, Object>();
    parseEntry(parent, vmf);

    return parent;
}

private static String convertVmfToJson(String filepath, boolean isPretty) {
    Map<String, Object> vmfDict = convertVmfToDict(filepath);

    // TODO: implement converting the dictionary to JSON and return the resulting string
    return "";
}

private static void convertVmfToJsonExport(String filepathIn, String filepathOut, boolean isPretty) {
    String jvmf = convertVmfToJson(filepathIn, isPretty);

    // TODO: implement writing the JSON string to a file at the specified path
}

public static void main(String[] args) {
    if (args.length == 2) {
        System.out.println(convertVmfToJson(args[1], false));
    } else if (args.length > 2) {
        boolean prettyPrint = false;
        if (List.of(args).containsAll(List.of("-p", "-pretty", "--p", "--pretty"))) {
            prettyPrint = true;
        }

        if (args.length == 3) {
            if (prettyPrint) {
                System.out.println(convertVmfToJson(args[1], true));
            } else {
                convertVmfToJsonExport(args[1], args[2], false);
                System.out.println("Converted " + args[1] + " > " + args[2]);
            }
        } else if (args.length == 4) {
            convertVmfToJsonExport(args[1], args[2], prettyPrint);
            System.out.println("Converted " + args[1] + " > " + args[2]);
        }
    }
}
}
