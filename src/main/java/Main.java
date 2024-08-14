import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    String result;
    ArrayList<String> arraylists = new ArrayList<>();
    int errors=0;
    LinkedHashMap<String, Integer> list = new LinkedHashMap<>();


    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        this.result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public ArrayList<String> splitStringintoAnArrayOfElements(String line){
        String patternString = "(.*?)##";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        ArrayList<String> list = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group();
            list.add(match);
        }
        this.arraylists = list;
        return list;
    }

    public LinkedHashMap<String, Integer> createHashMapForItem(ArrayList<String> lists, String regexItem) {
       // HashMap<String, Integer> list = new HashMap<>();
       checkIfWordIsMisSpelled(regexItem);
        Pattern pattern = Pattern.compile(regexItem, Pattern.CASE_INSENSITIVE);

        for( int i =0; i< lists.size(); i++){

            Matcher matcher = pattern.matcher(lists.get(i));

            if(matcher.find()){
             //   updateItemHashMap(capitalizeFirstLetter(matcher.group()), value);
                String item = capitalizeFirstLetter(matcher.group());
                String price = getPriceOfItem(lists.get(i));
                if(price != null){
                    list.put(item,list.getOrDefault(item, 0)+1);
                    list.put(price, list.getOrDefault(price, 0)+1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : list.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Key=" + key + ", Value=" + value);
        }

        return list;
    }

    private void checkIfWordIsMisSpelled(String regexItem) {
        list.clear();
        if(regexItem.equals("\\bcookies\\b")) {
           // regexItem = "(?i)(C......)";
            Pattern pattern = Pattern.compile("Co0kies", Pattern.CASE_INSENSITIVE);
            for (int i = 0; i < this.arraylists.size(); i++) {
                Matcher matcher = pattern.matcher(arraylists.get(i));
                if(matcher.find()){
                    list.put("Cookies", 1);
                    list.put(getPriceOfItem(arraylists.get(i)), 1);
                }
            }
        }
    }

    public String capitalizeFirstLetter(String string) {
      return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }

    private String getPriceOfItem(String s) {
        String patternString = "\\d\\.\\d\\d";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }

    public String findErrors(String typeOfError, String string){
        Pattern pattern = Pattern.compile(typeOfError, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        while(matcher.find()){
           this.errors++;    // :;
        }
        return "Errors         	 	 seen: " +this.errors +  " times";
    };


    public void writeFile(String results) {
        try {
         //  String results = toStringHashMap(hash);
            FileWriter fileWriter = new FileWriter("src/main/resources/outPut2.txt", true);
            System.out.println("file created");
            fileWriter.write(results);
            System.out.println("succesfully wrote to file");
           fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        String results ="";
        String string = main.readRawDataToString();
        ArrayList<String> lists= main.splitStringintoAnArrayOfElements(string);
        LinkedHashMap<String, Integer> hashMilk = main.createHashMapForItem(lists, "\\bmilk\\b");
        results += main.toStringHashMap(hashMilk);
        LinkedHashMap<String, Integer> hashBread = main.createHashMapForItem(lists, "\\bBread\\b");
        results += main.toStringHashMap(hashBread);
        LinkedHashMap<String, Integer> hashCookies = main.createHashMapForItem(lists, "\\bcookies\\b");
        results += main.toStringHashMap(hashCookies);
        LinkedHashMap<String, Integer> hashApples = main.createHashMapForItem(lists, "\\bapples\\b");
        results += main.toStringHashMap(hashApples);
        results+= main.findErrors(":;", string);
        main.writeFile(results);

    }

    private String toStringHashMap(LinkedHashMap<String, Integer> hash) {
        String results = "";
        int i = 0;
        for(Map.Entry<String, Integer> entry : hash.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(i == 0){
                results += " name: " + key +"          seen: " + value + " times." +" \n ";
                results += "============       ========" + "\n";
                i++;
            }else{
                results += " price: " + key +"        seen: " + value + " times." +" \n ";
                results += "------------       --------" + "\n";
            }
        }
        results += "\n \n";
        return results;
    }
}

