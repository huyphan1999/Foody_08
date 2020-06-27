package hcmute.edu.vn.foody_08;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.text.DecimalFormat;

public class RestaurantActivity extends FragmentActivity {
    TextView txtAddWifi;
    TextView txtTenQuan;
    TextView txtTinh;
    TextView txtTrangThai;
    TextView txtDiaChi;
    TextView txtGia;
    TextView txtLoaiHinh;
    TextView txtWifi;
    TextView txtDistance;
    LinearLayout layoutMenu;
    Restaurant restaurant;
    FoodyDatabase db = new FoodyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent);

        addControl();

        Intent intent=getIntent();
        restaurant = (Restaurant) intent.getSerializableExtra("Restaurant");

        setTextData(restaurant);

        layoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, Menu_Restaurant.class);
                //passing data to the book activity
                intent.putExtra("Restaurant",restaurant);
                //start the activity
                startActivity(intent);
            }
        });
        txtAddWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLogin();
            }
        });
    }

    protected void addControl() {
        txtAddWifi = findViewById(R.id.txtAddWifi);
        txtTenQuan= findViewById(R.id.txtTenQuan);
        txtTinh = findViewById(R.id.txtTinh);
        txtDiaChi=findViewById(R.id.txtDiaChi);
        txtLoaiHinh=findViewById(R.id.txtLoaiHinh);
        txtWifi=findViewById(R.id.txtWifi);
        layoutMenu=findViewById(R.id.layoutMenu);
        txtGia=findViewById(R.id.txtGia);
        txtDistance=findViewById(R.id.txtKhoangCach);
    }

    protected void setTextData(Restaurant res) {
        DecimalFormat df = new DecimalFormat("#,###,###");
        txtTenQuan.setText(res.getName());
        txtTinh.setText(db.getCityName(res.getCityId()));
        txtDiaChi.setText(res.getAddress());
        txtLoaiHinh.setText(res.getCategory());
        String priceRange = df.format(res.getMinPrice()) + "đ - " + df.format(res.getMaxPrice()) + "đ";
        txtGia.setText(priceRange);
        String distance = Distance.getDistance(res.getLatitude(), res.getLongitude());
        if (distance == null) {
            distance = "Không thể xác định";
            Toast.makeText(this, "Không thể lấy được vị trí của bạn vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
        txtDistance.setText(distance);
        if (res.getWifi() != "") {
            txtWifi.setText(res.getWifi());
        }
    }



    private void DialogLogin() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_DayNight_DarkActionBar);
        dialog.setContentView(R.layout.activity_login_wifi);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        int dialogWindowHeight = (int) (displayHeight * 0.35f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);
    }
}
