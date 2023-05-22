package com.hoctap.ql_hocphan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // 1. khai báo các thành phần
    private EditText edtMahp,edtTenhp,edtStc,edtHp_tq,edtHp_ss,edtKql,edtTim;
    private Button btnThem,btnSua,btnXoa,btnThoat,btnTim;
    private ListView lvsv;
    // CSDL
    private HPDatabase database;
    private HPAdapter adapter;
    private List<HocPhan> listSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        // khởi tạo
        database=new HPDatabase(this);
        listSV=database.InformationHP();
        adapter=new HPAdapter(this,R.layout.item_list_sv,listSV);
        lvsv.setAdapter(adapter);
//        setAdapter();
        // bắt sự kiện cho các button
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận thoát chương trình!");
                builder.setMessage("Bạn có muốn thoát khỏi chương trình không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                // hiển thị
                Dialog dialog=builder.create();
                dialog.show();
            }
        });

        // bắt sự kiện cho btnThem
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMahp.getText().toString().equals("")||edtTenhp.getText().toString().equals("") ||edtStc.getText().toString().equals("")||edtKql.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Thông tin HP không được để trống",Toast.LENGTH_LONG).show();
                }else {
                    HocPhan sv=CreateHP();
                    if(sv!=null){
                        database.AddHP(sv);
                        listSV.clear();
                        listSV.addAll(database.InformationHP());
//                        setAdapter();
                        adapter.notifyDataSetChanged();
                        //Xóa DL nhập vào
                        edtMahp.setText("");
                        edtTenhp.setText("");
                        edtHp_tq.setText("");
                        edtHp_ss.setText("");
                        edtStc.setText("");
                        edtKql.setText("");
                    }
                }
            }
        });
        // bắt sự kiện cho từng Item trong ListView
        lvsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HocPhan hp=listSV.get(position);
                edtMahp.setText(hp.getMahp());
                edtTenhp.setText(hp.getTenhp());
                edtHp_tq.setText(hp.getHp_tq());
                edtHp_ss.setText(hp.getHp_ss());
                edtStc.setText(hp.getStc());
                edtKql.setText(hp.getKql());
            }
        });
        // bắt sự kiện cho btnSua
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                edtmasv.setEnabled(false);
                HocPhan hp=new HocPhan();
                hp.setMahp(String.valueOf(edtMahp.getText()));
                hp.setTenhp(edtTenhp.getText()+" ");
                hp.setStc(edtStc.getText()+"");
                hp.setHp_tq(edtHp_tq.getText()+"");
                hp.setHp_ss(edtHp_ss.getText()+"");
                hp.setKql(edtKql.getText()+"");
                int kq=database.UpdateHP(hp);
                if(kq>0){
                    listSV.clear();
                    listSV.addAll(database.InformationHP());
                    if(adapter!=null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                //Xóa DL nhập vào
                edtMahp.setText("");
                edtTenhp.setText("");
                edtStc.setText("");
                edtHp_tq.setText("");
                edtHp_ss.setText("");
                edtKql.setText("");
            }
        });
        // bắt sự kiện cho button xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HocPhan hp=new HocPhan();
                hp.setMahp(String.valueOf(edtMahp.getText()));
                int kq=database.DeleteHP(hp.getMahp());
                if(kq>0){
                    Toast.makeText(MainActivity.this,"Xóa SV thành công", Toast.LENGTH_LONG).show();
                    listSV.clear();
                    listSV.addAll(database.InformationHP());
                    if(adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Không xóa được SV ", Toast.LENGTH_LONG).show();
                }

                edtMahp.setText("");
                edtTenhp.setText("");
                edtStc.setText("");
                edtHp_tq.setText("");
                edtHp_ss.setText("");
                edtKql.setText("");

            }
        });
        //tim kiem theo mahp
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(edtTim.getText().toString().equals("")){
                        listSV.clear();
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        Toast.makeText(MainActivity.this," chua nhap ma can tim: "+edtTim.getText().toString(),Toast.LENGTH_LONG).show();
                    }else{
                        HocPhan b=database.timLiem(edtTim.getText().toString());
                        if(b!=null){
                            listSV.clear();
                            listSV.add(b);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(MainActivity.this," khong co ma can tim "+edtTim.getText().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }

        });
    }


    private void setAdapter() {
        if(adapter==null){
            // tạo mới
            adapter=new HPAdapter(this,R.layout.item_list_sv,listSV);
            lvsv.setAdapter(adapter);
        }else {
            // cập nhật lại DL
            adapter.notifyDataSetChanged();
        }
    }

    private HocPhan CreateHP() {
        String mahp=edtMahp.getText().toString();
        String tenhp=edtTenhp.getText().toString();
        String stc=edtStc.getText().toString();
        String hptq=edtHp_tq.getText().toString();
        String hpss=edtHp_ss.getText().toString();
        String qlk=edtKql.getText().toString();
        HocPhan sv=new HocPhan(mahp,tenhp,stc,hptq,hpss,qlk);
        return sv;
    }
    private void anhxa() {
        edtMahp=findViewById(R.id.edt_mahp);
        edtTenhp=findViewById(R.id.edt_tenhp);
        edtStc=findViewById(R.id.edt_stc);
        edtHp_tq=findViewById(R.id.edt_hp_tq);
        edtHp_ss=findViewById(R.id.edt_hp_ss);
        edtKql=findViewById(R.id.edt_kql);
        btnThem=findViewById(R.id.btnThem);
        btnSua=findViewById(R.id.btnSua);
        btnXoa=findViewById(R.id.btnXoa);
        btnTim=findViewById(R.id.btnTim);
        btnThoat=findViewById(R.id.btnThoat);
        lvsv=findViewById(R.id.lvsv);
        edtTim=findViewById(R.id.edtTim);
    }
    //khoi tao menu_hp
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hp,menu);
        return true;
    }
    // xu ly cac lua chon menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0://them
                if(edtMahp.getText().toString().equals("")||edtTenhp.getText().toString().equals("") ||edtStc.getText().toString().equals("")||edtKql.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Thông tin HP không được để trống",Toast.LENGTH_LONG).show();
                }else {
                    HocPhan sv=CreateHP();
                    if(sv!=null){
                        database.AddHP(sv);
                        listSV.clear();
                        listSV.addAll(database.InformationHP());
//                        setAdapter();
                        adapter.notifyDataSetChanged();
                        //Xóa DL nhập vào
                        edtMahp.setText("");
                        edtTenhp.setText("");
                        edtHp_tq.setText("");
                        edtHp_ss.setText("");
                        edtStc.setText("");
                        edtKql.setText("");
                    }
                }
                break;
            case 1://sua
                HocPhan hp=new HocPhan();
                hp.setMahp(String.valueOf(edtMahp.getText()));
                hp.setTenhp(edtTenhp.getText()+" ");
                hp.setStc(edtStc.getText()+"");
                hp.setHp_tq(edtHp_tq.getText()+"");
                hp.setHp_ss(edtHp_ss.getText()+"");
                hp.setKql(edtKql.getText()+"");
                int kq=database.UpdateHP(hp);
                if(kq>0){
                    listSV.clear();
                    listSV.addAll(database.InformationHP());
                    if(adapter!=null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                //Xóa DL nhập vào
                edtMahp.setText("");
                edtTenhp.setText("");
                edtStc.setText("");
                edtHp_tq.setText("");
                edtHp_ss.setText("");
                edtKql.setText("");
                break;
            case 2:    //xoa
                HocPhan hp2=new HocPhan();
                hp2.setMahp(String.valueOf(edtMahp.getText()));
                int kq2=database.DeleteHP(hp2.getMahp());
                if(kq2>0){
                    Toast.makeText(MainActivity.this,"Xóa SV thành công", Toast.LENGTH_LONG).show();
                    listSV.clear();
                    listSV.addAll(database.InformationHP());
                    if(adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Không xóa được SV ", Toast.LENGTH_LONG).show();
                }

                edtMahp.setText("");
                edtTenhp.setText("");
                edtStc.setText("");
                edtHp_tq.setText("");
                edtHp_ss.setText("");
                edtKql.setText("");
                break;
            case 3://thoat
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận thoát chương trình!");
                builder.setMessage("Bạn có muốn thoát khỏi chương trình không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                // hiển thị
                Dialog dialog=builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}