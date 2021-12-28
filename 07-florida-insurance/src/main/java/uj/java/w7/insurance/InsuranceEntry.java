package uj.java.w7.insurance;

import java.math.BigDecimal;

public class InsuranceEntry {

    public String policyID;
    public String county;
    public String tiv_2011;
    public String tiv_2012;
    public BigDecimal tivDifference;

    public InsuranceEntry(String county, BigDecimal tivDifference){
        this.county = county;
        this.tivDifference = new BigDecimal(tivDifference.toString());
        this.policyID = null;
        this.tiv_2011 = null;
        this.tiv_2012 = null;
    }

    public InsuranceEntry(String line){
        String[] entry = line.split(",");

        this.policyID = entry[0];
        this.county = entry[2];
        this.tiv_2011 = entry[7];
        this.tiv_2012 = entry[8];

        BigDecimal bd1 = new BigDecimal(this.tiv_2011);
        BigDecimal bd2 = new BigDecimal(this.tiv_2012);
        //System.out.println(bd1 + " " + this.tiv_2011);
        //System.out.println(bd2 + " " + this.tiv_2012);

        this.tivDifference =  new BigDecimal(bd2.subtract(bd1).toString());
        //System.out.println(this.tivDifference);
    }

    public void DisplayEntry(){
        System.out.println("PolicyID: " + this.policyID + " county: " + this.county + " tiv_2011 " +  this.tiv_2011 + " tiv_2012: " + this.tiv_2012);
    }

}
