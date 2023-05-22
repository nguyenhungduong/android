package com.hoctap.ql_hocphan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HPDatabase extends SQLiteOpenHelper {
    //1. khai báo CSDL
    private static final String DATABASE_NAME="QL_HP";
    // khai báo tên bảng
    private static final String TABLE_NAME="hocphan";
    // khai báo tên trường
    private static final String MA="mahp";
    private static final String TEN="tenhp";
    private static final String TQ="hptq";
    private static final String SS="hpss";
    private static final String STC="stc";
    private static final String KQL="kql";

    // khai version
    private static int VERSION = 1;
    private Context context;
    // constructor
    public HPDatabase( Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1="CREATE TABLE "+TABLE_NAME+" ( "+ MA+" Text primary key, "+ TEN+" Text, "+ STC+ " int, "+ TQ+" Text, "+ SS+" Text, "+ KQL+" Text )";
        // thực thi tạo bảng
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  "+TABLE_NAME);
        onCreate(db);
    }
    //3. Thêm mới sinh viên vào CSDL
    public void AddHP(HocPhan hp){
        // gọi CSDL
        SQLiteDatabase db=this.getWritableDatabase();
        // tạo đối tượng values chứa các nội dung của sv
        ContentValues values=new ContentValues();
        values.put(MA,hp.getMahp());
        values.put(TEN,hp.getTenhp());
        values.put(STC,hp.getStc());
        values.put(TQ,hp.getHp_tq());
        values.put(SS,hp.getHp_ss());
        values.put(KQL,hp.getKql());
        // chèn values vào CSDL
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    //4. cập nhật CSDL
    public  int UpdateHP(HocPhan hp){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MA,hp.getMahp());
        values.put(TEN,hp.getTenhp());
        values.put(STC,hp.getStc());
        values.put(TQ,hp.getHp_tq());
        values.put(SS,hp.getHp_ss());
        values.put(KQL,hp.getKql());
        int a=db.update(TABLE_NAME,values,MA+"=?",new String[]{hp.getMahp()});
        db.close();
        return a;
    }
    //5. xóa sv thông qua ID
    public int DeleteHP(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        int b=db.delete(TABLE_NAME,MA+"=?",new String[]{id});
        db.close();
        return b;
    }
    //6. lấy toàn bộ thông tin sv trong CSDL
    public List<HocPhan> InformationHP(){
        List<HocPhan> listHP=new ArrayList<HocPhan>();
        SQLiteDatabase db=this.getWritableDatabase();
        String sql3="SELECT * FROM "+TABLE_NAME;
        // thực thi
        Cursor cursor=db.rawQuery(sql3,null);
        if(cursor.moveToFirst()){
            do {
                HocPhan hp=new HocPhan();
                hp.setMahp(cursor.getString(0));
                hp.setTenhp(cursor.getString(1));
                hp.setStc(cursor.getString(2));
                hp.setHp_tq(cursor.getString(3));
                hp.setHp_ss(cursor.getString(4));
                hp.setKql(cursor.getString(5));
                listHP.add(hp);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listHP;
    }
    public HocPhan timLiem(String tim){
        HocPhan hp=new HocPhan();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{MA,TEN,TQ,SS,STC,KQL},MA+" = "+tim,null,null,null,null);
        if(cursor.moveToFirst()){
                hp.setMahp(cursor.getString(0));
                hp.setTenhp(cursor.getString(1));
                hp.setStc(cursor.getString(2));
                hp.setHp_tq(cursor.getString(3));
                hp.setHp_ss(cursor.getString(4));
                hp.setKql(cursor.getString(5));

        }
        cursor.close();
        db.close();
        return hp;
    }
}
