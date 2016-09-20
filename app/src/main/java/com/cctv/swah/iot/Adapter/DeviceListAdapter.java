package com.cctv.swah.iot.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.swah.iot.Model.DeviceInfo;
import com.cctv.swah.iot.R;

import java.util.ArrayList;

/**
 * Created by byungwoo on 2016-07-26.
 */
public class DeviceListAdapter extends BaseAdapter {
    Context context;
    private DeviceInfo device;

    private ArrayList<Object> device_list_info = new ArrayList<Object>();


    public DeviceListAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return device_list_info.size();
    }

    @Override
    public Object getItem(int position) {
        return device_list_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Context context = parent.getContext();

        if(device_list_info.get(position) instanceof DeviceInfo) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_device, null);

                holder = new ViewHolder();
                holder.device_img = (ImageView) convertView.findViewById(R.id.device_img);
                holder.model = (TextView) convertView.findViewById(R.id.device_name);
                holder.name = (TextView) convertView.findViewById(R.id.user);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            device = (DeviceInfo) getItem(position);

            try {
                if(device.getDevice() == 0) {
                    holder.device_img.setImageResource(R.drawable.cctv);
                } else if(device.getDevice() == 1) {
                    holder.device_img.setImageResource(R.drawable.temperature);
                }
                Log.e("JSON", "------------"+device.getModel());
                holder.model.setText(device.getModel());
                holder.name.setText(device.getName());
            } catch (Exception e) {
                Log.wtf("DeviceListAdapter", e.toString());
                e.printStackTrace();
            }
        }

        return convertView;
    }

    public void add(DeviceInfo device_info) {
        device_list_info.add(device_info);
    }

    private class ViewHolder {
        public ImageView device_img;
        public TextView model;
        public TextView name;
    }


}
