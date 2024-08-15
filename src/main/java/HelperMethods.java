import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethods {

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

    public String toStringHashMap(LinkedHashMap<String, Integer> hash) {
        String results = "";
        int i = 0;
        for(Map.Entry<String, Integer> entry : hash.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(i == 0){
                results += String.format("name: %7s%" + 18 + "s seen:%d times.",key,"",value);
                results += String.format("\n============== %-" + 16 + "s =============\n", "");
//                results += " name: " + key +"        seen: " + value + " times." +" \n ";
//                results += "============       ========" + "\n";
                i++;
            }else{
//                results += " price: " + key +"        seen: " + value + " times." +" \n ";
//                results += "------------       --------" + "\n";
//                i++;
                results+= String.format("price: %7s%" + 18 + "s seen:%d times.",key,"",value);
                results += String.format("\n-------------- %-" + 16 + "s -------------\n", "");


            }
        }
        results += "\n \n";
        return results;
    }

}
