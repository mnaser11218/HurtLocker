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
    public HelperMethods hM = new HelperMethods();


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
        lists.forEach(item-> {
            Matcher matcher = pattern.matcher(item);
            if(matcher.find()){
                addItemAndPriceToHashMap(matcher.group(), item);
            }
        });
        return list;
    }

    private void addItemAndPriceToHashMap(String item, String itemString ) {
        String itemType = hM.capitalizeFirstLetter(item);
        String price = hM.getPriceOfItem(itemString);
        if(price != null){
            list.put(itemType,list.getOrDefault(itemType, 0)+1);
            list.put(price, list.getOrDefault(price, 0)+1);
        }
    }

    public void checkIfWordIsMisSpelled(String regexItem) {
        list.clear();
        if(regexItem.equals("\\bcookies\\b")) {
            Pattern pattern = Pattern.compile("Co0kies", Pattern.CASE_INSENSITIVE);
            arraylists.forEach(ele-> {
                Matcher matcher = pattern.matcher(ele);
                if(matcher.find()){
                    list.put("Cookies", 1);
                    list.put(hM.getPriceOfItem(ele), 1);
                }
            });
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
        results+= hM.toStringHashMap(createHashMapForItem(lists, "\\bmilk\\b"));
        results+= hM.toStringHashMap(createHashMapForItem(lists, "\\bBread\\b"));
        results+= hM.toStringHashMap(createHashMapForItem(lists, "\\bcookies\\b"));   ;
        results+= hM.toStringHashMap(createHashMapForItem(lists, "\\bapples\\b"));
        results+= findErrors(":;", string);
        hM.writeFile(results);
        return results;
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.printOutFinalResults();
    }

}

