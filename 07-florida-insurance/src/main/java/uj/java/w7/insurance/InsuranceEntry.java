package uj.java.w7.insurance;

public class InsuranceEntry {

    public String policyID;
    public String county;
    public String tiv_2011;
    public String tiv_2012;
    public float tivDifference;

    public InsuranceEntry(String line){
        String[] entry = line.split(",");

        this.policyID = entry[0];
        this.county = entry[2];
        this.tiv_2011 = entry[7];
        this.tiv_2012 = entry[8];

        this.tivDifference = Float.parseFloat(this.tiv_2012) - Float.parseFloat(this.tiv_2011);
    }

    public void DisplayEntry(){
        System.out.println("PolicyID: " + this.policyID + " county: " + this.county + " tiv_2011 " +  this.tiv_2011 + " tiv_2012: " + this.tiv_2012);
    }

}
