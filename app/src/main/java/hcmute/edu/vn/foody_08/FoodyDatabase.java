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
import java.util.List;

public class FoodyDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private Context context;

    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "FoodyApplication";

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

    //Table Provinces
    private static final String TABLE_PROVINCES = "Provinces";

    private static final String COLUMN_PROVINCES_Id = "Id";
    private static final String COLUMN_PROVINCES_Name = "Name";


    public FoodyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Script tạo bảng Restaurents
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
                + COLUMN_RESTAURANT_Password + " TEXT"
                + ")";

        // Script tạo bảng provinces
        String scriptProvince = "CREATE TABLE " + TABLE_PROVINCES + "("
                + COLUMN_PROVINCES_Id + " INTEGER PRIMARY KEY,"
                + COLUMN_PROVINCES_Name + " TEXT"
                + ")";

        // Execute Script.
        db.execSQL(scriptProvince);
        db.execSQL(scriptRestaurant);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVINCES);
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
                int id = resList.getJSONObject(i).getInt("Id");
                String name = resList.getJSONObject(i).getString("name");
                String address = resList.getJSONObject(i).getString("address");
                String Thumbnail = resList.getJSONObject(i).getString("PhotoUrl");
                double Latitude = resList.getJSONObject(i).getDouble("Latitude");
                double Longitude = resList.getJSONObject(i).getDouble("Longitude");
                int CityId = resList.getJSONObject(i).getInt("CityId");
                String Wifi = resList.getJSONObject(i).getString("Wifi");
                String Password = resList.getJSONObject(i).getString("Password");

                Restaurant restaurant = new Restaurant(id, name, address, Thumbnail, Latitude, Longitude, CityId, Wifi, Password);
                addRestaurant(restaurant);
            }
        }

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
        // Inserting Row
        db.insert(TABLE_RESTAURANT, null, values);

        // Closing database connection
        db.close();
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
