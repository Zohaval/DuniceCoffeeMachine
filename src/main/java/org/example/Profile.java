package org.example;

public class Profile {

    private String nameProfile = "";
    private int amountEspresso = 0;
    private int amountCappuccino = 0;

    public Profile(String nameProfile, int amountEspresso, int amountCappuccino) {
        this.nameProfile = nameProfile;
        this.amountEspresso = amountEspresso;
        this.amountCappuccino = amountCappuccino;
    }

    public String getNameProfile() {
        return nameProfile;
    }

    public int getAmountEspresso() {
        return amountEspresso;
    }

    public int getAmountCappuccino() {
        return amountCappuccino;
    }

    public void setNameProfile(String nameProfile) {
        this.nameProfile = nameProfile;
    }

    public void setAmountEspresso(int amountEspresso) {
        this.amountEspresso = amountEspresso;
    }

    public void setAmountCappuccino(int amountCappuccino) {
        this.amountCappuccino = amountCappuccino;
    }
}
