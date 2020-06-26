package hcmute.edu.vn.foody_08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodyDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private Context context;

    // Database Version
    private static final int DATABASE_VERSION =1;

    // Database Name
    private static final String DATABASE_NAME = "FoodyApplication";

    //Bảng quán ăn
    //Table Restaurents
    private static final String TABLE_RESTAURANT = "Restaurants";

    private static final String COLUMN_RESTAURANT_Id = "Id";
    private static final String COLUMN_RESTAURANT_Name = "Name";
    private static final String COLUMN_RESTAURANT_Category = "Category";
    private static final String COLUMN_RESTAURANT_Description = "Description";
    private static final String COLUMN_RESTAURANT_Address = "Address";
    private static final String COLUMN_RESTAURANT_Thumbnail = "Thumbnail";
    private static final String COLUMN_RESTAURANT_Latitude = "Latitude";
    private static final String COLUMN_RESTAURANT_Longitude = "Longitude";
    private static final String COLUMN_RESTAURANT_CityId = "CityId";
    private static final String COLUMN_RESTAURANT_Wifi = "Wifi";
    private static final String COLUMN_RESTAURANT_Password = "Password";
    private static final String COLUMN_RESTAURANT_MinPrice = "MinPrice";
    private static final String COLUMN_RESTAURANT_MaxPrice = "MaxPrice";

    //Bảng các tỉnh thành
    //Table Provinces
    private static final String TABLE_PROVINCES = "Provinces";

    private static final String COLUMN_PROVINCES_Id = "Id";
    private static final String COLUMN_PROVINCES_Name = "Name";

    //Bảng danh mục món
    //Table DishType
    private static final String TABLE_DISHTYPE = "DishType";

    private static final String COLUMN_DISHTYPE_Id = "Id";
    private static final String COLUMN_DISHTYPE_Name = "Name";
    private static final String COLUMN_DISHTYPE_ResId = "ResId";

    //Bảng món ăn
    //Table Dishes
    private static final String TABLE_DISH = "Dishes";

    private static final String COLUMN_DISH_Id = "Id";
    private static final String COLUMN_DISH_Name = "Name";
    private static final String COLUMN_DISH_Price = "Price";
    private static final String COLUMN_DISH_DishTypeId = "dishTypeId";

    public FoodyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Script create table Restaurents
        String scriptRestaurant = "CREATE TABLE " + TABLE_RESTAURANT + "("
                + COLUMN_RESTAURANT_Id + " INTEGER PRIMARY KEY,"
                + COLUMN_RESTAURANT_Name + " TEXT,"
                + COLUMN_RESTAURANT_Category + " TEXT,"
                + COLUMN_RESTAURANT_Description + " TEXT,"
                + COLUMN_RESTAURANT_Address + " TEXT,"
                + COLUMN_RESTAURANT_Thumbnail + " TEXT,"
                + COLUMN_RESTAURANT_Latitude + " REAL,"
                + COLUMN_RESTAURANT_Longitude + " REAL,"
                + COLUMN_RESTAURANT_CityId + " INTEGER,"
                + COLUMN_RESTAURANT_Wifi + " TEXT,"
                + COLUMN_RESTAURANT_Password + " TEXT,"
                + COLUMN_RESTAURANT_MinPrice + " INTEGER,"
                + COLUMN_RESTAURANT_MaxPrice + " INTEGER"
                + ")";

        // Script create table Provinces
        String scriptProvince = "CREATE TABLE " + TABLE_PROVINCES + "("
                + COLUMN_PROVINCES_Id + " INTEGER PRIMARY KEY,"
                + COLUMN_PROVINCES_Name + " TEXT"
                + ")";

        // Script create table DishType
        String scriptDishType = "CREATE TABLE " + TABLE_DISHTYPE + "("
                + COLUMN_DISHTYPE_Id + " INTEGER PRIMARY KEY,"
                + COLUMN_DISHTYPE_Name + " TEXT,"
                + COLUMN_DISHTYPE_ResId + " INTEGER"
                + ")";
        // Script create table Dishes
        String scriptDish = "CREATE TABLE " + TABLE_DISH + "("
                + COLUMN_DISH_Id + " INTEGER PRIMARY KEY,"
                + COLUMN_DISH_Name + " TEXT,"
                + COLUMN_DISH_Price + " INTEGER,"
                + COLUMN_DISH_DishTypeId + " INTEGER"
                + ")";

        // Execute Script.
        db.execSQL(scriptProvince);
        db.execSQL(scriptRestaurant);
        db.execSQL(scriptDishType);
        db.execSQL(scriptDish);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVINCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHTYPE);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }


    public void initProvinces(ArrayList<Province> arrayProvince) {
        int count = this.getProvinceCount();
        if (count == 0) {
            for (Province province : arrayProvince) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(COLUMN_PROVINCES_Id, province.getId());
                values.put(COLUMN_PROVINCES_Name, province.getName());
                // Inserting Row
                db.insert(TABLE_PROVINCES, null, values);

                // Closing database connection
                db.close();

            }
        }
    }
    public void addDishType(int dishTypeId,String disTypeName,int resId) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(COLUMN_DISHTYPE_Id, dishTypeId);
                values.put(COLUMN_DISHTYPE_Name, disTypeName);
                values.put(COLUMN_DISHTYPE_ResId,resId);

                // Inserting Row
                db.insert(TABLE_DISHTYPE, null, values);
                // Closing database connection
                db.close();
    }

    public void addDish(int dishId,String Name,int Price,int dishTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DISH_Id, dishId);
        values.put(COLUMN_DISH_Name,Name);
        values.put(COLUMN_DISH_Price,Price);
        values.put(COLUMN_DISH_DishTypeId,dishTypeId);
        // Inserting Row
        db.insert(TABLE_DISH, null, values);
        // Closing database connection
        db.close();
    }


    public String readJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = this.context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void initDataFromJson() throws JSONException {

        //Khởi tạo data từ file json vào database cho Provinces

        if (getProvinceCount() == 0) {
            ArrayList<Province> provinceArrayList = new ArrayList<>();
            JSONArray provinceList = new JSONArray(readJSONFromAsset("provinces.json"));
            for (int i = 0; i < provinceList.length(); ++i) {
                int id = provinceList.getJSONObject(i).getInt("Id");
                String name = provinceList.getJSONObject(i).getString("name");
                Province province = new Province(name, id);
                provinceArrayList.add(province);
            }
            initProvinces(provinceArrayList);
        }
        //Khởi tạo data từ file json vào database cho Restaurants
        if (getResCount() == 0) {
            JSONArray resList = new JSONArray(readJSONFromAsset("restaurants.json"));
            for (int i = 0; i < resList.length(); ++i) {

                //Truyền data chung của nhà hàng vào database
                int id = resList.getJSONObject(i).getInt("Id");
                String name = resList.getJSONObject(i).getString("name");
                String address = resList.getJSONObject(i).getString("address");
                String Thumbnail = resList.getJSONObject(i).getString("PhotoUrl");
                double Latitude = resList.getJSONObject(i).getDouble("Latitude");
                double Longitude = resList.getJSONObject(i).getDouble("Longitude");
                int CityId = resList.getJSONObject(i).getInt("CityId");
                String Wifi = resList.getJSONObject(i).getString("Wifi");
                String Password = resList.getJSONObject(i).getString("Password");
                int MinPrice = resList.getJSONObject(i).getInt("Min_price");
                int MaxPrice = resList.getJSONObject(i).getInt("Max_price");
                String Category = resList.getJSONObject(i).getString("Category");

                Restaurant restaurant = new Restaurant(id,name,Category,address,Thumbnail,Latitude,Longitude,CityId,Wifi,Password,MinPrice,MaxPrice);
                addRestaurant(restaurant);

                //Lấy menu
                JSONArray disTypeList= resList.getJSONObject(i).getJSONArray("Menu");
                int disTypeListLength=disTypeList.length();
                if (disTypeListLength!=0){
                    for (int j = 0; j < disTypeListLength; ++j){

                        //Lấy danh mục món ăn
                        int dishTypeId = disTypeList.getJSONObject(j).getInt("dish_type_id");
                        String disTypeName = disTypeList.getJSONObject(j).getString("dish_type_name");
                        addDishType(dishTypeId,disTypeName,id);

                        //Lấy món
                        JSONArray dishes= disTypeList.getJSONObject(j).getJSONArray("dishes");
                        int dishesLength=dishes.length();
                        if(dishesLength!=0){
                            for (int z = 0; z < dishesLength; ++z){
                                int dishId = dishes.getJSONObject(z).getInt("id");
                                String dishName=dishes.getJSONObject(z).getString("name");
                                int price = dishes.getJSONObject(z).getInt("price");
                                addDish(dishId,dishName,price,dishTypeId);
                            }
                        }

                    }
                }
            }
        }

    }


    public List<Dish> getDishes(int dishTypeId){
        List<Dish> dishList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISH + " WHERE " + COLUMN_DISH_DishTypeId + " = " + dishTypeId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setName(cursor.getString(1));
                dish.setPrice(cursor.getInt(2));
                // Adding restaurant to list
                dishList.add(dish);
            } while (cursor.moveToNext());
        }
        return dishList;
    }


    public HashMap<String, List<Dish>> getMenu(int resId) {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        HashMap<String,List<Dish>> menu = new HashMap<>();
        //Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DISHTYPE + " WHERE " + COLUMN_DISHTYPE_ResId + " = " + resId;

//        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int dishTypeId = cursor.getInt(0);
                String dishTypeName = cursor.getString(1);

                List<Dish> dishes = getDishes(dishTypeId);

                menu.put(dishTypeName,dishes);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return note list
        return menu;
    }

    public List<Restaurant> getAllRestaurants(int cityId) {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<Restaurant> resList = new ArrayList<>();
        //Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE " + COLUMN_RESTAURANT_CityId + " = " + cityId;

//        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(cursor.getInt(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setCategory(cursor.getString(2));
                restaurant.setDescription(cursor.getString(3));
                restaurant.setAddress(cursor.getString(4));
                restaurant.setThumbnail(cursor.getString(5));
                restaurant.setLatitude(cursor.getDouble(6));
                restaurant.setLongitude(cursor.getDouble(7));
                restaurant.setCityId(cursor.getInt(8));
                restaurant.setWifi(cursor.getString(9));
                restaurant.setPassword(cursor.getString(10));
                restaurant.setMinPrice(cursor.getInt(11));
                restaurant.setMaxPrice(cursor.getInt(12));
                // Adding restaurant to list
                resList.add(restaurant);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return note list
        return resList;
    }

    public ArrayList<Province> getAllPronvinces() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        ArrayList<Province> provinceList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROVINCES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province(cursor.getString(1), cursor.getInt(0));
                provinceList.add(province);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return note list
        return provinceList;
    }

    public void addRestaurant(Restaurant restaurant) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTAURANT_Id, restaurant.getId());
        values.put(COLUMN_RESTAURANT_Name, restaurant.getName());
        values.put(COLUMN_RESTAURANT_Category, restaurant.getCategory());
        values.put(COLUMN_RESTAURANT_Description, restaurant.getDescription());
        values.put(COLUMN_RESTAURANT_Address, restaurant.getAddress());
        values.put(COLUMN_RESTAURANT_Thumbnail, restaurant.getThumbnail());
        values.put(COLUMN_RESTAURANT_Latitude, restaurant.getLatitude());
        values.put(COLUMN_RESTAURANT_Longitude, restaurant.getLongitude());
        values.put(COLUMN_RESTAURANT_CityId, restaurant.getCityId());
        values.put(COLUMN_RESTAURANT_Wifi, restaurant.getWifi());
        values.put(COLUMN_RESTAURANT_Password, restaurant.getPassword());
        values.put(COLUMN_RESTAURANT_MinPrice,restaurant.getMinPrice());
        values.put(COLUMN_RESTAURANT_MaxPrice,restaurant.getMaxPrice());
        // Inserting Row
        db.insert(TABLE_RESTAURANT, null, values);

        // Closing database connection
        db.close();
    }

    public String getCityName(int cityId) {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");
        String cityName = "";

        String selectQuery = "SELECT  * FROM " + TABLE_PROVINCES + " WHERE " + COLUMN_PROVINCES_Id + " = " + cityId;
//        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                cityName=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        return cityName;
    }

    public int getResCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getProvinceCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_PROVINCES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
}
