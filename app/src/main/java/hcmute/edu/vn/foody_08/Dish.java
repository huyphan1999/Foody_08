package hcmute.edu.vn.foody_08;

public class Dish {
    private String Name;
    private int Price;

    public Dish() {
    }

    public Dish(String name, int price) {
        Name = name;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
