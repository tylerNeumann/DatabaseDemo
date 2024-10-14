package fvtc.edu.databasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper helper;
    SQLiteDatabase db;
    public static final String TAG = "MainActivity";
    Button btnInsert;
    Button btnGetItems;
    Button btnUpdate;
    Button btnDelete;
    TextView tvInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this,
                "items.db",
                null,
                helper.DATABASE_VERSION);
        db = helper.getWritableDatabase();

        tvInfo = findViewById(R.id.tvInfo);

        btnGetItems = findViewById(R.id.btnGetItems);
        btnGetItems.setOnClickListener(this);

        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick(View view) {
        int buttonId = view.getId();
        if(buttonId == R.id.btnGetItems){
            GetItems();
        } else if (buttonId == R.id.btnInsert) {
            Insert();
        } else if (buttonId == R.id.btnUpdate) {
            Update();
        }else {
            Delete();
        }
    }
    private void GetItems() {
        if(db != null){
            Cursor cursor = db.rawQuery("select * from tblItems;", null);
            String msg = "";
            while(cursor.moveToNext())
            {
                int id = cursor.getInt(0);
                String FirstName = cursor.getString(1);
                String LastName = cursor.getString(2);
                Log.d(TAG, "GetItems: " + id + ":" + FirstName + ":" + LastName);
                msg += id + ") " + LastName + "\r\n";
            }
            cursor.close();
            tvInfo.setText(msg);
        }
    }

    private void Insert() {
        try{
            if(db != null)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put("FirstName", "Tyler");
                contentValues.put("LastName", "Neumann");
                db.insert("tblItems", null, contentValues);
                GetItems();
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "Insert: " + e.getMessage());
        }
    }


    private void Update() {
        try{
            if(db != null)
            {
                String whereClause = "Id == 2";
                ContentValues contentValues = new ContentValues();
                contentValues.put("LastName", "Simpson");
                db.update("tblItems", contentValues, whereClause, null);
                GetItems();
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "Insert: " + e.getMessage());
        }
    }

    private void Delete() {
        try{
            if(db != null)
            {
                db.execSQL("delete from tblItems");
                // or
                //db.delete("tblItems", null, null);
                GetItems();
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "Insert: " + e.getMessage());
        }
    }
}