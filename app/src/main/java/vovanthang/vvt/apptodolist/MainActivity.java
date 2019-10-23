package vovanthang.vvt.apptodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DelegatedAdminReceiver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView listView;
    ArrayList<CongViec> arrayList;
    CongViecAdapter congViecAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this,"ghichu.sql",null,1);
        listView = (ListView) findViewById(R.id.listviewCongViec);
        arrayList = new ArrayList<>();
        congViecAdapter = new CongViecAdapter(this,R.layout.dong_cong_viec,arrayList);
        listView.setAdapter(congViecAdapter);

        database.Query("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT , TenCongViec VARCHAR(200))");

       // database.Query("INSERT INTO CongViec VALUES(null,'Tên Công Việc')");
        GetData();

    }

    private  void GetData(){
        Cursor cursor  = database.Getdata("SELECT * FROM CongViec");
        arrayList.clear();
        while (cursor.moveToNext()){
            String ten =  cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new CongViec(ten,id));
        }
        congViecAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemAdd){
            DiaLogAdd();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DiaLogAdd(){
        final EditText edtDialogThem;
        Button btndialoGThem, btndialogHuy;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);
        dialog.show();

        edtDialogThem = (EditText) dialog.findViewById(R.id.edtDialogThem);
        btndialoGThem = (Button) dialog.findViewById(R.id.btnDialogThêm);
        btndialogHuy = (Button) dialog.findViewById(R.id.btnDialogHuy);

        btndialogHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btndialoGThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tenCV = edtDialogThem.getText().toString();
                if(tenCV.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập vào tên CV", Toast.LENGTH_SHORT).show();
                }
                else {
                    database.Query("INSERT INTO CongViec VALUES(null,'"+tenCV+"')");
                    dialog.dismiss();
                    GetData();
                }
            }
        });
    }

    public void DiaLogEdit(final String ten , final int id){
        final Dialog dialogedit = new Dialog(this);
        dialogedit.setContentView(R.layout.dialog_cap_nhat);
        dialogedit.show();

        final EditText edtdialogcapnhat = (EditText) dialogedit.findViewById(R.id.edtDialogCapNhatThem);
        Button btndialogThem , btndialogHuy;

        btndialogThem = (Button) dialogedit.findViewById(R.id.btnDialogCapNhatThêm);
        btndialogHuy = (Button) dialogedit.findViewById(R.id.btnDialogCapNhatHuy);
        edtdialogcapnhat.setText(ten);


        btndialogHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogedit.dismiss();
            }
        });

        btndialogThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenmoi  = edtdialogcapnhat.getText().toString();
                database.Query("UPDATE CongViec SET TenCongViec='"+tenmoi+"' WHERE Id ='"+id+"'");
                Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialogedit.dismiss();
                GetData();
            }
        });

    }

    public void AlertDialogDelete(String tencv, final int id){
        AlertDialog.Builder alerdilog  = new AlertDialog.Builder(this);
        alerdilog.setTitle("Bạn có muốn xóa "+ tencv+" không?");
        alerdilog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alerdilog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.Query("DELETE FROM CongViec WHERE Id ='"+id+"'");
                GetData();
            }
        });

        alerdilog.show();
    }
}
