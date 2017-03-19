package com.remnev.verbatoriamini.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by nikitaremnev on 27.05.16.
 */
@JsonObject
public class Child {

    @JsonField(name = "z")
    private String childID;

//    @JsonField(name = "x")
    private String childName;

//    @JsonField(name = "c")
    private String parentName;

//    @JsonField(name = "v")
    private String creationDate;

//    @JsonField(name = "b")
    private String email;

//    @JsonField(name = "n")
    private String phone;

//    @JsonField(name = "m")
    private String city;

//    @JsonField(name = "a")
    private String diagnose;

//    @JsonField(name = "s")
    private String neuroprofile;

//    @JsonField(name = "d")
    private String birthday;

    private long attention;

    private long mediation;

    private long alfa1;

    private long alfa2;

    private long alfa3;

    private long beta1;

    private long beta2;

    private long beta3;

    private long gamma1;

    private long gamma2;

    private long delta;

    private long theta;

    public long getDelta() {
        return delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
    }

    public long getGamma2() {
        return gamma2;
    }

    public void setGamma2(long gamma2) {
        this.gamma2 = gamma2;
    }

    public String getChildID() {
        return childID;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public long getMediation() {
        return mediation;
    }

    public void setMediation(long mediation) {
        this.mediation = mediation;
    }

    public long getAttention() {
        return attention;
    }

    public void setAttention(long attention) {
        this.attention = attention;
    }

    public String getNeuroprofile() {
        return neuroprofile;
    }

    public void setNeuroprofile(String neuroprofile) {
        this.neuroprofile = neuroprofile;
    }

    public long getAlfa1() {
        return alfa1;
    }

    public void setAlfa1(long alfa1) {
        this.alfa1 = alfa1;
    }

    public long getAlfa3() {
        return alfa3;
    }

    public void setAlfa3(long alfa3) {
        this.alfa3 = alfa3;
    }

    public long getBeta1() {
        return beta1;
    }

    public void setBeta1(long beta1) {
        this.beta1 = beta1;
    }

    public long getAlfa2() {
        return alfa2;
    }

    public void setAlfa2(long alfa2) {
        this.alfa2 = alfa2;
    }

    public long getBeta2() {
        return beta2;
    }

    public void setBeta2(long beta2) {
        this.beta2 = beta2;
    }

    public long getGamma1() {
        return gamma1;
    }

    public void setGamma1(long gamma1) {
        this.gamma1 = gamma1;
    }

    public long getBeta3() {
        return beta3;
    }

    public void setBeta3(long beta3) {
        this.beta3 = beta3;
    }

    public long getTheta() {
        return theta;
    }

    public void setTheta(long theta) {
        this.theta = theta;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstuctedNeuroprofile() {
        this.neuroprofile = attention
                + " " + mediation
                + " " + alfa1
                + " " + alfa2
                + " " + alfa3
                + " " + beta1
                + " " + beta2
                + " " + beta3
                + " " + gamma1
                + " " + gamma2
                + " " + delta
                + " " + theta;
        return neuroprofile;
    }

    public String getConstructedID() {
        if (childName != null && !childName.equals("") && birthday != null && !birthday.equals("")) {
            String[] childNames = childName.split(" ");
            if (childNames.length == 1) {
                this.childID = childNames[0].substring(0, 1) + this.birthday.replace("/", "");
            } else if (childNames.length > 1) {
                this.childID = childNames[0].substring(0, 1) + childNames[1].substring(0, 1) +this.birthday.replace("/", "");
            } else {
                this.childID = childName.substring(0, 1) + this.birthday.replace("/", "");
            }
            return this.childID;
        }
        return null;
    }

    public void parseNeuroprofile(String neuroprofile) {
        this.neuroprofile = neuroprofile;
        String[] values = neuroprofile.split(" ");
        for (int i = 0; i < values.length; i ++) {
            switch (i) {
                case 0:
                    this.attention = Long.parseLong(values[i]);
                    break;
                case 1:
                    this.mediation = Long.parseLong(values[i]);
                    break;
                case 2:
                    this.alfa1 = Long.parseLong(values[i]);
                    break;
                case 3:
                    this.alfa2 = Long.parseLong(values[i]);
                    break;
                case 4:
                    this.alfa3 = Long.parseLong(values[i]);
                    break;
                case 5:
                    this.beta1 = Long.parseLong(values[i]);
                    break;
                case 6:
                    this.beta2 = Long.parseLong(values[i]);
                    break;
                case 7:
                    this.beta3 = Long.parseLong(values[i]);
                    break;
                case 8:
                    this.gamma1 = Long.parseLong(values[i]);
                    break;
                case 9:
                    this.gamma2 = Long.parseLong(values[i]);
                    break;
                case 10:
                    this.delta = Long.parseLong(values[i]);
                    break;
                case 11:
                    this.theta = Long.parseLong(values[i]);
                    break;
            }
        }
    }

}
