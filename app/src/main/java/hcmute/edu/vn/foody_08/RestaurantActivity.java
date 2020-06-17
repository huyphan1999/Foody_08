package hcmute.edu.vn.foody_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {
    TextView txtAddWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent);
        txtAddWifi=findViewById(R.id.txtAddWifi);
        txtAddWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, LoginWifiActivity.class);
                startActivity(intent);
            }
        });
    }
}
