package uj.java.w7.insurance;

import java.io.IOException;
import java.io.*;
import java.math.BigDecimal;
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

        List<String> countedCountries = new ArrayList<>();//list with countries already counted
        int count = 0;

        File countFile = new File("count.txt");//creating file

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
            out = new FileOutputStream(countFile);//creating output stream
        }catch (IOException e){
            System.out.println("OutputStream error");
        }

        for (var entry: list) {
            if(countedCountries.isEmpty() || !countedCountries.contains(entry.county)){//counting the countries that have not been counted yet
                count++;
                countedCountries.add(entry.county);
            }
        }

        try{
            System.out.println("Writing to countFile");
            out.write(Integer.toString(count).getBytes(StandardCharsets.UTF_8));//writing the amount of countries to the file
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
            sum += Double.parseDouble(entry.tiv_2012);//counting the sum of tiv_2012
        }

        try{
            System.out.println("Writing to countFile");
            out.write(df.format(sum).getBytes(StandardCharsets.UTF_8));//writing it to file
        }catch (IOException e){
            System.out.println("Writing error");
        }

        return sum;
    }

    //compares entries by tiv2012 and tiv2011 value difference (most valuable)
    public static class InsuranceEntryComparator implements Comparator<InsuranceEntry>{

        public int compare(InsuranceEntry first, InsuranceEntry second){
            return -first.tivDifference.compareTo(second.tivDifference);
        }
    }

    public static void mostValuable(List<InsuranceEntry> list){

        List<InsuranceEntry> sums = new ArrayList<>();
        InsuranceEntryComparator comp = new InsuranceEntryComparator();
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
                if(sums.get(i).county.matches(entry.county)){//first we iterate through top countries, if the country was already added
                    sums.get(i).tivDifference = sums.get(i).tivDifference.add(entry.tivDifference);//we just add the tivDifference value
                    added = true;
                }
            }
            if(added == false){//if it wasn't already added to top countries
                sums.add(new InsuranceEntry(entry.county, new BigDecimal(entry.tivDifference.toString())));//we add it as new InsuranceEntry
            }

            added = false;
        }
        //after all entries have been counted we sort the list
        sums.sort(comp);

        try{
            out.write("country,value\n".getBytes(StandardCharsets.UTF_8));
        }catch (IOException e){}
        for (int i = 0; i < 10; i++) {//and insert the top 10 into the file
            try{
                out.write(sums.get(i).county.getBytes(StandardCharsets.UTF_8));
                out.write(",".getBytes(StandardCharsets.UTF_8));
                out.write(df.format(sums.get(i).tivDifference).getBytes(StandardCharsets.UTF_8));
                out.write("\n".getBytes(StandardCharsets.UTF_8));
            }catch (IOException e){
                System.out.println("Writing error");
            }
        }
    }

    public static void main(String[] args) {

        List<InsuranceEntry> entryList = new ArrayList<>();

        ZipFile zipFile;
        ZipEntry zipEntry;
        InputStream inputStream;
        String text = null;

        try{//opening the zipFile and getting the input stream
            zipFile = new ZipFile("C:\\Users\\Admin\\Desktop\\Studia\\java\\dominikmarchewka\\07-florida-insurance\\FL_insurance.csv.zip");
            zipEntry = zipFile.getEntry("FL_insurance.csv");
            inputStream = zipFile.getInputStream(zipEntry);

            text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);//reading input stream into String

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

        var textArray = text.split("\r", 0);//spliting whole input by end of line char

        entryList.add(new InsuranceEntry(textArray[0].split("\n")[1]));//first end of line char wass different than the others for some reason
        //so I split it again and add first entry manually

        for (int i = 1; i < textArray.length; i++) {//add the rest of entries
            entryList.add(new InsuranceEntry(textArray[i]));
        }

        System.out.println(countCountry(entryList));//method for count.txt
        System.out.println(sumTiv2012(entryList));//method for tiv2012.txt
        mostValuable(entryList);//method for most_valuable.txt
    }

}
