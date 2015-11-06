package com.wly.filechooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/10/24.
 */
public class FileAdapter extends BaseAdapter{

    private List<Map<String,Object>> list;
    private Context context;
    private String currentDirection;

    public FileAdapter(Context context, List<Map<String, Object>> list, String currentDirection) {
        this.list = list;
        this.context=context;
        this.currentDirection=currentDirection;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view= LayoutInflater.from(context).inflate(R.layout.file_chooser_item,null);
        TextView textView=(TextView)view.findViewById(R.id.item_tv);
        ImageView imageView=(ImageView)view.findViewById(R.id.item_iv);
        /**
         * 此处也需要注意路径要是绝对的路径
         */
        String filename=list.get(position).get("filename").toString();
        File file=new File(currentDirection+"/"+filename);
        if (file.isDirectory()){
            imageView.setBackgroundResource(R.drawable.ic_folder_black_18dp);
        }else if (file.isFile()){
            imageView.setBackgroundResource(R.drawable.ic_insert_drive_file_black_18dp);
        }else if (file.isHidden()){
            imageView.setBackgroundResource(R.mipmap.ic_launcher);
        }
        textView.setText(filename);

        return view;
    }
}
