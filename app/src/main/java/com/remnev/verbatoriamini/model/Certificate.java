package com.remnev.verbatoriamini.model;

import android.util.Log;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;

import java.util.Random;

/**
 * Created by nikitaremnev on 27.05.16.
 */
@JsonObject
public class Certificate {

    @JsonField(name = "q")
    private String specialistName;

    @JsonField(name = "w")
    private String specialistID;

    @JsonField(name = "e")
    private int specialistProfile;

    @JsonField(name = "r")
    private String creationDate;

    @JsonField(name = "t")
    private String email;

    @JsonField(name = "y")
    private String phone;

    @JsonField(name = "u")
    private String city;

    @JsonField(name = "x")
    private String expiry;

    private String cvc;

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSpecialistProfile() {
        return specialistProfile;
    }

    public void setSpecialistProfile(int specialistProfile) {
        this.specialistProfile = specialistProfile;
    }

    public String getSpecialistName() {
        if (specialistName == null) {
            return "";
        }
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getSpecialistID() {
        return specialistID;
    }

    public void setSpecialistID(String specialistID) {
        this.specialistID = specialistID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getConstructedID() {
        if (specialistName != null && !specialistName.equals("")) {
            this.specialistID = specialistName.substring(0, 1) + (int) (100000 + new Random().nextFloat() * 900000);
            return this.specialistID;
        }
        return null;
    }

    public String certificateToString() {
        String result = specialistName + ";" + specialistID + ";" + specialistProfile + ";" + creationDate + ";" + email + ";"
                + phone + ";" + city + ";" + expiry;
        return SpecialistSharedPrefs.encodeString(result);
    }

    public boolean parseCertificate(String readed) {
        String[] array = null;
        if (readed.contains(";")) {
            array = readed.split(";");
        } else {
            readed = SpecialistSharedPrefs.decodeString(readed);
            array = readed.split(";");
        }
        if (array.length == 8) {
            setSpecialistName(array[0]);
            setSpecialistID(array[1]);
            setSpecialistProfile(Integer.parseInt(array[2]));
            setCreationDate(array[3]);
            setEmail(array[4]);
            setPhone(array[5]);
            setCity(array[6]);
            setExpiry(array[7]);
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public boolean equals(Object o) {
//        Specialist specialist = (Specialist) o;
//        try {
//            return specialistName.equals(specialist.getSpecialistName()) &&
//                    creationDate.equals(specialist.getCreationDate()) &&
//                    specialistProfile == specialist.getSpecialistProfile() &&
//                    email.equals(specialist.getEmail()) &&
//                    phone.equals(specialist.getPhone()) &&
//                    city.equals(specialist.getCity());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }

}
