package hcmute.edu.vn.foody_08;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity {
    String[] provinceNameList;
    public static int imgTICK = R.drawable.bluee_tick;
    ListViewAdapter adapter;
    public ArrayList<String> arrayListPr = new ArrayList<>();
    private ArrayList<Province> arrayListProvince = new ArrayList<Province>();
    private ListView listView1;
    FoodyDatabase db = new FoodyDatabase(this);
    private int province_id;
    private String provine_check, temp;
    private TextView txtCancel, txtAgree;
    private EditText editTextFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        GetDataProvince();
        Intent intent = getIntent();
        province_id = intent.getIntExtra("province_id", 0);
        temp = provine_check = "Hà Nội";

        arrayListProvince = db.getAllPronvinces();

        listView1 = (ListView) findViewById(R.id.listView_city);
        listView1.setAdapter(new ListViewAdapter(CityActivity.this, arrayListPr, imgTICK, provine_check, editTextFind));
        txtCancel = (TextView) findViewById(R.id.txtHuy);
        txtAgree = (TextView) findViewById(R.id.txtXong);
        editTextFind = (EditText) findViewById(R.id.search_city_id);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                temp = arrayListPr.get(position);
                listView1.setAdapter(new ListViewAdapter(CityActivity.this, arrayListPr, imgTICK, temp, editTextFind));
                listView1.smoothScrollToPosition(position);
            }
        });

        txtAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp == provine_check) {
                    ReturnMain(RESULT_OK, provine_check);
                } else if (temp != provine_check) {
                    ReturnMain(RESULT_OK, temp);
                }
            }
        });


        editTextFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final ArrayList<String> listProvince = new ArrayList<>();
                if (!s.equals("")) {
                    Cursor dataProvince = db.GetData("SELECT * FROM Provinces WHERE name LIKE '" + editTextFind.getText() + "%'");
                    while (dataProvince.moveToNext()) {
                        listProvince.add(dataProvince.getString(1));
                    }
                    listView1.setAdapter(new ListViewAdapter(CityActivity.this, listProvince, imgTICK, provine_check, editTextFind));
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            temp = listProvince.get(position);
                            listView1.setAdapter(new ListViewAdapter(CityActivity.this, listProvince, imgTICK, temp, editTextFind));
                            listView1.smoothScrollToPosition(position);
                        }
                    });
                } else {
                    listView1.setAdapter(new ListViewAdapter(CityActivity.this, arrayListPr, imgTICK, provine_check, editTextFind));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityActivity.this, MainActivity.class);
                province_id = GetProvinceId(provine_check);
                intent.putExtra("province_id", province_id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void ReturnMain(int resultcode, String flag) {
        //Intent intent=getIntent();
        Intent intent = new Intent(CityActivity.this, MainActivity.class);
        province_id = GetProvinceId(flag);
        intent.putExtra("id", province_id);
        setResult(resultcode, intent);
        finish();
    }

    public void GetDataProvince() {
        Cursor dataProvince = db.GetData("SELECT * FROM Provinces");
        while (dataProvince.moveToNext()) {
            arrayListPr.add(dataProvince.getString(1));
        }
    }

    private int GetProvinceId(String flag) {
        Cursor dataFlag = db.GetData("SELECT * FROM Provinces WHERE Name = '" + flag + "'");
        dataFlag.moveToFirst();
        return dataFlag.getInt(0);
    }

}
