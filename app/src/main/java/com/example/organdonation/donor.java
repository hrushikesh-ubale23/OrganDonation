package com.example.organdonation;

public class donor {

    String DonorName,Phone,Organs,BloodGroup;

    public donor(){}

    public donor(String donorName, String phone, String organs, String bloodGroup) {
        DonorName = donorName;
        Phone = phone;
        Organs = organs;
        BloodGroup = bloodGroup;
    }

    public String getDonorName() {
        return DonorName;
    }

    public void setDonorName(String donorName) {
        DonorName = donorName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getOrgans() {
        return Organs;
    }

    public void setOrgans(String organs) {
        Organs = organs;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }
}
