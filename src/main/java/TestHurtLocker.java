import org.junit.Test;

public class TestHurtLocker {

    @Test
    public void testSplittingData() throws Exception {
        Main main = new Main();
        String results = main.readRawDataToString();
       // System.out.println(results);
        String[] myArray = results.split("##");
        System.out.println(myArray.length);

       for(String ele : myArray){
           System.out.println(ele);
       }


    }
}
