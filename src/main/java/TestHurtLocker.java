import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestHurtLocker {


    @Test
    public void testSplittingData() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
       // System.out.println(results);
        Assert.assertEquals(main.splitStringintoAnArrayOfElements(results).size(), 28);
    }

    @Test
    public void testThereAre6Milks() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
       ArrayList<String> list =  main.splitStringintoAnArrayOfElements(results);
       String milkItem = "\\bmilk\\b";
      HashMap<String, Integer> MilkHash = main.createItemHashMap(list, milkItem);
    }
}
