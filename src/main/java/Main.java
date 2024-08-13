import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public String splitStringintoAnArrayOfElements(String line){
        String[] myArray = new String[28];
       String element = "";
       return element;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        System.out.println("output: " + output);
            String text = output;
            String patternString = "##(.*?)##";

            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(text);
        ArrayList<String> list = new ArrayList<>();
            while (matcher.find()) {
                System.out.println("inside");
                String match = matcher.group();
                list.add(match);
            }
    }
}
