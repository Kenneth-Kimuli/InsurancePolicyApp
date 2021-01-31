package com.ipa.www.insuracepolicyapp;

public class PolicyData {
    private String policyName;
    private String policyProvName;
    private String policyDescription;
    private String policyPrice;

    public PolicyData() {
    }

    public PolicyData(String policyName, String policyProvName, String policyDescription, String policyPrice) {
        this.policyName = policyName;
        this.policyProvName = policyProvName;
        this.policyDescription = policyDescription;
        this.policyPrice = policyPrice;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyProvName() {
        return policyProvName;
    }

    public void setPolicyProvName(String policyProvName) {
        this.policyProvName = policyProvName;
    }

    public String getPolicyDescription() {
        return policyDescription;
    }

    public void setPolicyDescription(String policyDescription) {
        this.policyDescription = policyDescription;
    }

    public String getPolicyPrice() {
        return policyPrice;
    }

    public void setPolicyPrice(String policyPrice) {
        this.policyPrice = policyPrice;
    }
}
