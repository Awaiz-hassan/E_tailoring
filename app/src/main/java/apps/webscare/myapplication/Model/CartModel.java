package apps.webscare.myapplication.Model;

public class CartModel {
    String title;
    String price;
    Measurement measurement;
    String quantity;
    String image;
    boolean visible;

    public CartModel(String title, String price, Measurement measurement, String quantity, String image) {
        this.title = title;
        this.price = price;
        this.measurement = measurement;
        this.quantity = quantity;
        this.image = image;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

