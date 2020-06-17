package hcmute.edu.vn.foody_08;

public class Restaurant {


    //Tesst push

    private int Id;
    private String Name;
    private String Category;
    private String Description;
    private String Address;
    private int Thumbnail;

    public Restaurant(int id, String name, String category, String description, String address, int thumbnail) {
        Id = id;
        Name = name;
        Category = category;
        Description = description;
        Address = address;
        Thumbnail = thumbnail;
    }

    public Restaurant(){

    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
