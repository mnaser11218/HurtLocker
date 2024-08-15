import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
      HashMap<String, Integer> MilkHash = main.createHashMapForItem(list, milkItem);
        int actual = MilkHash.get("Milk");
        Assert.assertEquals(6, actual);
    }

    @Test
    public void testThereAre6Breads() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
        ArrayList<String> list =  main.splitStringintoAnArrayOfElements(results);
        String milkItem = "\\bBread\\b";
        LinkedHashMap<String, Integer> breadHash = main.createHashMapForItem(list, milkItem);
        int actual = breadHash.get("Bread");
        Assert.assertEquals(6, actual);
    }

    @Test
    public void testThereAre4Apples() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
        ArrayList<String> list =  main.splitStringintoAnArrayOfElements(results);
        String milkItem = "\\bapples\\b";
        HashMap<String, Integer> appleHash = main.createHashMapForItem(list, milkItem);
        int actual = appleHash.get("Apples");
        Assert.assertEquals(4, actual);
    }

    @Test
    public void testThereAre8Cookies() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
        ArrayList<String> list =  main.splitStringintoAnArrayOfElements(results);
        String cookieItem = "\\bcookies\\b";
        HashMap<String, Integer> cookieHash = main.createHashMapForItem(list, cookieItem);
        int actual = cookieHash.get("Cookies");
        Assert.assertEquals(8, actual);
    }



    @Test
    public void testErrors() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
        main.findErrors(":;", results);
        int expected = 4;
        Assert.assertEquals(main.errors, expected);
    }

    @Test
    public void testWriterMethod() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
        boolean actual = main.writeFile(results);
        boolean expected = true;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testPrintFinalResults() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
        String actual = main.printOutFinalResults();
        String expected = " name: Milk          seen: 6 times. \n" +
                " ============       ========\n" +
                " price: 3.23        seen: 5 times. \n" +
                " ------------       --------\n" +
                " price: 1.23        seen: 1 times. \n" +
                " ------------       --------\n" +
                "\n" +
                " \n" +
                " name: Bread          seen: 6 times. \n" +
                " ============       ========\n" +
                " price: 1.23        seen: 6 times. \n" +
                " ------------       --------\n" +
                "\n" +
                " \n" +
                " name: Cookies          seen: 8 times. \n" +
                " ============       ========\n" +
                " price: 2.25        seen: 8 times. \n" +
                " ------------       --------\n" +
                "\n" +
                " \n" +
                " name: Apples          seen: 4 times. \n" +
                " ============       ========\n" +
                " price: 0.25        seen: 2 times. \n" +
                " ------------       --------\n" +
                " price: 0.23        seen: 2 times. \n" +
                " ------------       --------\n" +
                "\n" +
                " \n" +
                "Errors         \t \t seen: 4 times";
        Assert.assertEquals(actual, expected);
    }


    @Test
    public void testToStringHashMap() throws Exception {
        Main main = new Main();
        String string = main.readRawDataToString();

        ArrayList<String> lists= main.splitStringintoAnArrayOfElements(string);
        String actual=  main.toStringHashMap(main.createHashMapForItem(lists, "\\bmilk\\b"));

       // boolean actual = main.toStringHashMap();
       String expected =" name: Milk          seen: 6 times. \n" +
               " ============       ========\n" +
               " price: 3.23        seen: 5 times. \n" +
               " ------------       --------\n" +
               " price: 1.23        seen: 1 times. \n" +
               " ------------       --------\n" +
               "\n" +
               " \n";
        Assert.assertEquals(actual, expected);
    }






}
