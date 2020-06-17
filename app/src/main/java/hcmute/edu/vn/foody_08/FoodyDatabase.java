package hcmute.edu.vn.foody_08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class FoodyDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FoodyApplication";

    //Table restaurent
    private static final String TABLE_RESTAURANT = "Restaurants";

    private static final String COLUMN_RESTAURANT_Id ="Id";
    private static final String COLUMN_RESTAURANT_Name ="Name";
    private static final String COLUMN_RESTAURANT_Category ="Category";
    private static final String COLUMN_RESTAURANT_Description = "Description";
    private static final String COLUMN_RESTAURANT_Address = "Address";
    private static final String COLUMN_RESTAURANT_Thumbnail = "Thumbnail";




    public FoodyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String scriptRestaurant = "CREATE TABLE " + TABLE_RESTAURANT + "("
                + COLUMN_RESTAURANT_Id + " INTEGER PRIMARY KEY,"
                + COLUMN_RESTAURANT_Name + " TEXT,"
                + COLUMN_RESTAURANT_Category + " TEXT,"
                + COLUMN_RESTAURANT_Description + " TEXT,"
                + COLUMN_RESTAURANT_Address + " TEXT,"
                + COLUMN_RESTAURANT_Thumbnail+ " INTEGER"
                +")";
        // Execute Script.
        db.execSQL(scriptRestaurant);
    }

    public void initRestaurants(ArrayList<Restaurant> arrayRes)  {
        int count = this.getResCount();
        if(count ==0 ) {
            for (Restaurant restaurant : arrayRes) {
                this.addRestaurant(restaurant);
            }
        }
    }

    public List<Restaurant> getAllRestaurants() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<Restaurant> resList = new ArrayList<Restaurant>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;

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
                restaurant.setThumbnail(cursor.getInt(5));
                // Adding restaurant to list
                resList.add(restaurant);
            } while (cursor.moveToNext());
        }

        // return note list
        return resList;
    }

    public void addRestaurant(Restaurant restaurant) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTAURANT_Id, restaurant.getId());
        values.put(COLUMN_RESTAURANT_Name, restaurant.getName());
        values.put(COLUMN_RESTAURANT_Category,restaurant.getCategory());
        values.put(COLUMN_RESTAURANT_Description,restaurant.getDescription());
        values.put(COLUMN_RESTAURANT_Address,restaurant.getAddress());
        values.put(COLUMN_RESTAURANT_Thumbnail,restaurant.getThumbnail());

        // Inserting Row
        db.insert(TABLE_RESTAURANT, null, values);

        // Closing database connection
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }

    public int getResCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}
