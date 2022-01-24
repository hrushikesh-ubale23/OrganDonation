package com.example.organdonation;public class Patient {

    String PatientName,Phone,BloodGroup,Organs;

    public Patient(){}

    public Patient(String patientName, String phone, String bloodGroup, String organs) {
        PatientName = patientName;
        Phone = phone;
        BloodGroup = bloodGroup;
        Organs = organs;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getOrgans() {
        return Organs;
    }

    public void setOrgans(String organs) {
        Organs = organs;
    }
}
