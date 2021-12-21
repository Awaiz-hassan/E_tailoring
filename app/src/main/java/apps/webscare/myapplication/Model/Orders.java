package apps.webscare.myapplication.Model;

public class Orders {
    String address;
    String placed_on;
    String price_total;
    String title;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaced_on() {
        return placed_on;
    }

    public void setPlaced_on(String placed_on) {
        this.placed_on = placed_on;
    }

    public String getPrice_total() {
        return price_total;
    }

    public void setPrice_total(String price_total) {
        this.price_total = price_total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
