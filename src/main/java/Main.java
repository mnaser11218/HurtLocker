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
       checkIfWordIsMisSpelled(regexItem);
        Pattern pattern = Pattern.compile(regexItem, Pattern.CASE_INSENSITIVE);
        for( int i =0; i< lists.size(); i++){
            Matcher matcher = pattern.matcher(lists.get(i));
            if(matcher.find()){
                String item = capitalizeFirstLetter(matcher.group());
                String price = getPriceOfItem(lists.get(i));
                if(price != null){
                    list.put(item,list.getOrDefault(item, 0)+1);
                    list.put(price, list.getOrDefault(price, 0)+1);
                }
            }
        }
        return list;
    }

    public void checkIfWordIsMisSpelled(String regexItem) {
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

    public String getPriceOfItem(String s) {
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


    public boolean writeFile(String results) {
        boolean writenFile = false;
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/outPut2.txt", true);
            fileWriter.write(results);
           fileWriter.close();
           writenFile = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writenFile;
    }

    public String printOutFinalResults() throws Exception {
        String results ="";
       String string = readRawDataToString();
        ArrayList<String> lists= splitStringintoAnArrayOfElements(string);
        results+= toStringHashMap(createHashMapForItem(lists, "\\bmilk\\b"));
        results+= toStringHashMap(createHashMapForItem(lists, "\\bBread\\b"));
        results+= toStringHashMap(createHashMapForItem(lists, "\\bcookies\\b"))   ;
        results+= toStringHashMap(createHashMapForItem(lists, "\\bapples\\b"));
        results+= findErrors(":;", string);
        writeFile(results);
        return results;
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        String string = main.readRawDataToString();

        ArrayList<String> lists= main.splitStringintoAnArrayOfElements(string);
        System.out.println(main.toStringHashMap(main.createHashMapForItem(lists, "\\bmilk\\b")));

    }

    public String toStringHashMap(LinkedHashMap<String, Integer> hash) {
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

