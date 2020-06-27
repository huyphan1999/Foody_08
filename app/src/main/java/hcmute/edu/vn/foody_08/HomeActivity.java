package hcmute.edu.vn.foody_08;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class HomeActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FoodyDatabase db = new FoodyDatabase(this);



        try {
            db.initDataFromJson();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        }, 2000);


    }


}