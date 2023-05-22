package com.hoctap.ql_hocphan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HPAdapter extends ArrayAdapter {
    // khai báo các thành phần
    private Context context;
    private int resource;
    private List<HocPhan> listHP;

    // constructor
    public HPAdapter( Context context, int resource, List<HocPhan> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.listHP=objects;
    }
    // hiển thị thông tin lên giao diện
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // khai báo ViewHolder
        ViewHolder viewHolder;
        // kiểm tra xem convertView xem có rỗng không?
        // nếu rỗng thì set hiển thị item_listSV lên
        // nếu không rỗng thì setTag nội dung lên
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_list_sv, parent,false);
            viewHolder=new ViewHolder();
            // ánh xạ
            viewHolder.tvma=(TextView)convertView.findViewById(R.id.tv_ma);
            viewHolder.tvten=(TextView)convertView.findViewById(R.id.tv_ten);
            viewHolder.tvtinchi=(TextView)convertView.findViewById(R.id.tv_tinchi);
            viewHolder.tvhp_tq=(TextView)convertView.findViewById(R.id.tv_hp_tq);
            viewHolder.tvhp_ss=(TextView)convertView.findViewById(R.id.tv_hp_ss);
            viewHolder.tvkql=(TextView)convertView.findViewById(R.id.tv_quanly);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        // thiết lập giá trị
        HocPhan sv=listHP.get(position);
        viewHolder.tvma.setText(sv.getMahp());
        viewHolder.tvten.setText(sv.getTenhp());
        viewHolder.tvtinchi.setText(sv.getStc());
        viewHolder.tvhp_tq.setText(sv.getHp_tq());
        viewHolder.tvhp_ss.setText(sv.getHp_ss());
        viewHolder.tvkql.setText(sv.getKql());

        return convertView;
    }

    class ViewHolder{
        // khai báo các biến tương ứng với các thành phần trong giao diện item_listsv
        TextView tvma, tvten,tvtinchi,tvhp_tq,tvhp_ss,tvkql;

    }
}
