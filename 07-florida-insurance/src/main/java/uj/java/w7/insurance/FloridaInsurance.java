package uj.java.w7.insurance;

import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;


public class FloridaInsurance {

    public static int countCountry(List<InsuranceEntry> list){

        List<String> countedCountries = new ArrayList<>();
        int count = 0;

        for (var entry: list) {
            if(countedCountries.isEmpty() || !countedCountries.contains(entry.county)){
                //entry.DisplayEntry();
                count++;
                countedCountries.add(entry.county);
            }
        }

        return count;
    }

    public static double sumTiv2012(List<InsuranceEntry> list){

        double sum = 0;

        for (var entry: list) {
            //System.out.println(entry.tiv_2012);
            sum += Double.parseDouble(entry.tiv_2012);
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

        List<InsuranceEntry> top10 = new ArrayList<>();
        InsuranceEntryComparator comp = new InsuranceEntryComparator();

        for (var entry: list ) {

            if(top10.size() < 10) {
                top10.add(entry);
                top10.sort(comp);
                deleteDuplicate(top10);
            }
            else{
                if(entry.tivDifference > top10.get(9).tivDifference) {
                    top10.remove(9);
                    top10.add(entry);
                    top10.sort(comp);
                    deleteDuplicate(top10);
                }
            }
        }

        for (var entry:top10) {
            System.out.println(entry.county + " " + entry.tivDifference);
        }
        System.out.println("size" + top10.size());
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
        Reader inputStreamReader = null;
        int data = 0;
        char c = 'a';
        String line = "";
        int entries = 0;
        int added = 0;

        try{
            zipFile = new ZipFile("C:\\Users\\Admin\\Desktop\\Studia\\java\\dominikmarchewka\\07-florida-insurance\\FL_insurance.csv.zip");
            zipEntry = zipFile.getEntry("FL_insurance.csv");
            inputStream = zipFile.getInputStream(zipEntry);
            inputStreamReader = new InputStreamReader(inputStream);

            data = inputStreamReader.read();
            c = (char) data;

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while(data != -1){
            line += c;

            if(c == '\r'){
                entries++;
                if(line.split(",").length == 18){
                    added++;
                    entryList.add(new InsuranceEntry(line));
                    //entryList.get(added++).DisplayEntry();
                }
                //System.out.println(line);
                line = "";
            }

            try{
                data = inputStream.read();
                c = (char) data;
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println(entries + " " + added);
        //System.out.println(countCountry(entryList));
        //System.out.println(sumTiv2012(entryList));
        mostValuable(entryList);
    }

}
