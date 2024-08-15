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
    public String result;
    public ArrayList<String> arraylists = new ArrayList<>();
    public int errors=0;
    public LinkedHashMap<String, Integer> list = new LinkedHashMap<>();
    public HelperMethods helperMethods = new HelperMethods();


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
                String item = helperMethods.capitalizeFirstLetter(matcher.group());
                String price = helperMethods.getPriceOfItem(lists.get(i));
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
            Pattern pattern = Pattern.compile("Co0kies", Pattern.CASE_INSENSITIVE);
            for (int i = 0; i < this.arraylists.size(); i++) {
                Matcher matcher = pattern.matcher(arraylists.get(i));
                if(matcher.find()){
                    list.put("Cookies", 1);
                    list.put(helperMethods.getPriceOfItem(arraylists.get(i)), 1);
                }
            }
        }
    }

    public String findErrors(String typeOfError, String string){
        Pattern pattern = Pattern.compile(typeOfError, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        while(matcher.find()){
           this.errors++;    // :;
        }
        return "Errors         	 	 seen: " +this.errors +  " times";
    };


    public String printOutFinalResults() throws Exception {
        String results ="";
       String string = readRawDataToString();
        ArrayList<String> lists= splitStringintoAnArrayOfElements(string);
        results+= helperMethods.toStringHashMap(createHashMapForItem(lists, "\\bmilk\\b"));
        results+= helperMethods.toStringHashMap(createHashMapForItem(lists, "\\bBread\\b"));
        results+= helperMethods.toStringHashMap(createHashMapForItem(lists, "\\bcookies\\b"));   ;
        results+= helperMethods.toStringHashMap(createHashMapForItem(lists, "\\bapples\\b"));
        results+= findErrors(":;", string);
        helperMethods.writeFile(results);
        return results;
    }
}

