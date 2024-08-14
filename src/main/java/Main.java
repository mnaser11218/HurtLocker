import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    String result;
    ArrayList<String> lists = new ArrayList<>();

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
        this.lists = list;
        return list;
    }

    public HashMap<String, Integer> createItemHashMap(ArrayList<String> lists, String item) {
        HashMap<String, Integer> list = new HashMap<>();
        Pattern pattern = Pattern.compile(item, Pattern.CASE_INSENSITIVE);
        for( int i =0; i< lists.size(); i++){
            Matcher matcher = pattern.matcher(lists.get(i));
            if(matcher.find()){
                System.out.println(lists.get(i));
                String price = getPriceOfItem(lists.get(i));
                System.out.println("price is: " + price);
                // check if key is in the lists, if it is, increase value by one, if not add the key and value to the list
                list.put(price, list.getOrDefault(price, 0)+1);
//                if(list.containsKey(price)){
//                    list.set
//                }
            }

                    for (Map.Entry<String, Integer> entry : list.entrySet()) {
                        String key = entry.getKey();
                        Integer value = entry.getValue();
                        System.out.println("Key=" + key + ", Value=" + value);
                    }


        }
        return list;
    }

    private String getPriceOfItem(String s) {
        String patternString = "\\d\\.\\d\\d";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            return matcher.group();
        }

        return "-1";
    }

}

