package hcmute.edu.vn.foody_08;

import java.io.Serializable;

public class Restaurant implements Serializable {


    private int Id;
    private String Name;
    private String Category = "";
    private String Description = "";
    private String Address;
    private String Thumbnail;
    private double Latitude;
    private double Longitude;
    private int CityId;



    private String Wifi;
    private String Password;
    private int MinPrice;
    private int MaxPrice;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String category, String address, String thumbnail, double latitude, double longitude, int cityId, String wifi, String password, int minPrice, int maxPrice) {
        Id = id;
        Name = name;
        Category = category;
        Address = address;
        Thumbnail = thumbnail;
        Latitude = latitude;
        Longitude = longitude;
        CityId = cityId;
        Wifi = wifi;
        Password = password;
        MinPrice = minPrice;
        MaxPrice = maxPrice;
    }

    public int getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(int minPrice) {
        MinPrice = minPrice;
    }

    public int getMaxPrice() {
        return MaxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        MaxPrice = maxPrice;
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

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getWifi() {
        return Wifi;
    }

    public void setWifi(String wifi) {
        Wifi = wifi;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
