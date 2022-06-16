package apps.webscare.myapplication.Model;

public class Measurement {
    String Chest;
    String Bicep;
    String CollarSize;
    String CuffCircumference;
    String Seat;
    String ShirtLength;
    String SleeveLength;
    String Waist;
    String ShirtWidth;

    public Measurement(String chest, String bicep, String collarSize, String cuffCircumference, String seat, String shirtLength, String sleeveLength, String waist, String shirtWidth) {
        Chest = chest;
        Bicep = bicep;
        CollarSize = collarSize;
        CuffCircumference = cuffCircumference;
        Seat = seat;
        ShirtLength = shirtLength;
        SleeveLength = sleeveLength;
        Waist = waist;
        ShirtWidth = shirtWidth;
    }

    public String getChest() {
        return Chest;
    }

    public void setChest(String chest) {
        Chest = chest;
    }

    public String getBicep() {
        return Bicep;
    }

    public void setBicep(String bicep) {
        Bicep = bicep;
    }

    public String getCollarSize() {
        return CollarSize;
    }

    public void setCollarSize(String collarSize) {
        CollarSize = collarSize;
    }

    public String getCuffCircumference() {
        return CuffCircumference;
    }

    public void setCuffCircumference(String cuffCircumference) {
        CuffCircumference = cuffCircumference;
    }

    public String getSeat() {
        return Seat;
    }

    public void setSeat(String seat) {
        Seat = seat;
    }

    public String getShirtLength() {
        return ShirtLength;
    }

    public void setShirtLength(String shirtLength) {
        ShirtLength = shirtLength;
    }

    public String getSleeveLength() {
        return SleeveLength;
    }

    public void setSleeveLength(String sleeveLength) {
        SleeveLength = sleeveLength;
    }

    public String getWaist() {
        return Waist;
    }

    public void setWaist(String waist) {
        Waist = waist;
    }

    public String getShirtWidth() {
        return ShirtWidth;
    }

    public void setShirtWidth(String shirtWidth) {
        ShirtWidth = shirtWidth;
    }
}
