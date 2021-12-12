package uj.java.w7.insurance;

import java.io.IOException;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;


public class FloridaInsurance {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static int countCountry(List<InsuranceEntry> list){

        List<String> countedCountries = new ArrayList<>();
        int count = 0;

        File countFile = new File("count.txt");

        try{
            if(countFile.createNewFile()){
                System.out.println("Created count file");
            }else{
                System.out.println("Count file already exists");
            }
        }catch (IOException e){
            System.out.println("An error occured");
        }

        OutputStream out = null;

        try{
            out = new FileOutputStream(countFile);
        }catch (IOException e){
            System.out.println("OutputStream error");
        }

        for (var entry: list) {
            if(countedCountries.isEmpty() || !countedCountries.contains(entry.county)){
                //entry.DisplayEntry();
                count++;
                countedCountries.add(entry.county);
            }
        }

        try{
            System.out.println("Writing to countFile");
            out.write(Integer.toString(count).getBytes(StandardCharsets.UTF_8));
        }catch (IOException e){
            System.out.println("Writing error");
        }

        return count;
    }

    public static double sumTiv2012(List<InsuranceEntry> list){

        double sum = 0;

        File countFile = new File("tiv2012.txt");

        try{
            if(countFile.createNewFile()){
                System.out.println("Created tiv2012 file");
            }else{
                System.out.println("Tiv2012 file already exists");
            }
        }catch (IOException e){
            System.out.println("An error occured");
        }

        OutputStream out = null;

        try{
            out = new FileOutputStream(countFile);
        }catch (IOException e){
            System.out.println("OutputStream error");
        }

        for (var entry: list) {
            //System.out.println(entry.tiv_2012);
            sum += Double.parseDouble(entry.tiv_2012);
        }

        try{
            System.out.println("Writing to countFile");
            out.write(df.format(sum).getBytes(StandardCharsets.UTF_8));
            //out.write('\n');
            //out.write(Double.toString(sum).getBytes(StandardCharsets.UTF_8));
        }catch (IOException e){
            System.out.println("Writing error");
        }

        return sum;
    }

    //compares entries by tiv2012 and tiv2011 difference value(most valuable)
    public static class InsuranceEntryComparator implements Comparator<InsuranceEntry>{

        public int compare(InsuranceEntry first, InsuranceEntry second){
            return -Float.compare(first.tivDifference, second.tivDifference);
        }
    }

    public static void mostValuable(List<InsuranceEntry> list){

        List<InsuranceEntry> sums = new ArrayList<>();
        List<InsuranceEntry> top10 = new ArrayList<>();
        InsuranceEntryComparator comp = new InsuranceEntryComparator();
        InsuranceEntry minValue = null;

        File countFile = new File("most_valuable.txt");

        try{
            if(countFile.createNewFile()){
                System.out.println("Created most_valuable file");
            }else{
                System.out.println("most_valuable file already exists");
            }
        }catch (IOException e){
            System.out.println("An error occured");
        }

        OutputStream out = null;

        try{
            out = new FileOutputStream(countFile);
        }catch (IOException e){
            System.out.println("OutputStream error");
        }

        boolean added = false;

        for (var entry:list) {

            for(int i = 0; i < sums.size(); i++){
                if(sums.get(i).county.matches(entry.county)){
                    sums.get(i).tivDifference += entry.tivDifference;
                    added = true;
                }
            }
            if(added == false){
                sums.add(entry);
            }

            added = false;
            /*if(top10.size() < 10){
                top10.add(entry);
            }
            else{
                if(entry.tivDifference > minValue.tivDifference){
                    top10.remove(minValue);
                    top10.add(entry);
                }
            }
            deleteDuplicate(top10);
            minValue = findMin(top10);*/
        }

        sums.sort(comp);

        //top10.sort(comp);

        try{
            out.write("country,value\n".getBytes(StandardCharsets.UTF_8));
        }catch (IOException e){}
        for (int i = 0; i < 10; i++) {
            try{
                out.write(sums.get(i).county.getBytes(StandardCharsets.UTF_8));
                out.write(",".getBytes(StandardCharsets.UTF_8));
                out.write(df.format(sums.get(i).tivDifference).getBytes(StandardCharsets.UTF_8));
                out.write("\n".getBytes(StandardCharsets.UTF_8));
            }catch (IOException e){
                System.out.println("Writing error");
            }
        }
        //System.out.println("size" + top10.size());
    }

    public static InsuranceEntry findMin(List<InsuranceEntry> list){
        InsuranceEntry min;

        min = list.get(0);
        for(int i = 1; i < list.size(); i++){
            if(list.get(i).tivDifference < min.tivDifference)
                min = list.get(i);
        }

        return min;
    }

    public static void deleteDuplicate(List<InsuranceEntry> list){

        String tempStr;
        float tempFl;
        int i = 0;
        while(i < list.size()){
            tempStr = list.get(i).county;
            tempFl = list.get(i).tivDifference;

            for(int j = i + 1; j < list.size(); j++){
                if(list.get(j).county.matches(tempStr)){
                    if(list.get(j).tivDifference <= tempFl){
                        list.remove(j);
                    }
                    else{
                        if(i >= 0)
                        list.remove(i);
                        i--;
                    }
                }
            }
            i++;
        }
    }

    public static void main(String[] args) {

        List<InsuranceEntry> entryList = new ArrayList<>();

        ZipFile zipFile = null;
        ZipEntry zipEntry = null;
        InputStream inputStream = null;
        int entries = 0;
        int added = 0;
        String text = null;

        try{
            zipFile = new ZipFile("C:\\Users\\Admin\\Desktop\\Studia\\java\\dominikmarchewka\\07-florida-insurance\\FL_insurance.csv.zip");
            zipEntry = zipFile.getEntry("FL_insurance.csv");
            inputStream = zipFile.getInputStream(zipEntry);

            text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

        var textArray = text.split("\r", 0);

        //System.out.println(textArray[0].split("\n")[0]);
        entryList.add(new InsuranceEntry(textArray[0].split("\n")[1]));

        for (int i = 1; i < textArray.length; i++) {
            entries++;
            added++;
            entryList.add(new InsuranceEntry(textArray[i]));
        }


        System.out.println(entries + " " + added);
        System.out.println(countCountry(entryList));
        System.out.println(sumTiv2012(entryList));
        mostValuable(entryList);
    }

}
