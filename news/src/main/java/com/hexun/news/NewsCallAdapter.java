package com.hexun.news;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hexun.base.R;

import java.util.HashMap;
import java.util.List;

public class NewsCallAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, String>> list;

    public NewsCallAdapter(Context context, List<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
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

    private ImpowerCustomHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.slv_impower_item_news,null);
            holder = new ImpowerCustomHolder();
            holder.ivImg = (ImageView) convertView.findViewById(R.id.iv_impower_item_img);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_impower_item_name);
            holder.tvDuration = (TextView) convertView.findViewById(R.id.tv_impower_item_duration);
            holder.tvTimeLead = (TextView) convertView.findViewById(R.id.tv_impower_item_time_lead);
            convertView.setTag(holder);
        } else {
            holder = (ImpowerCustomHolder) convertView.getTag();
        }
        /**
         * 通话类型
         */
        if (TextUtils.equals((list.get(position).get("type") + ""), "打入")) { //"打入"
            holder.ivImg.setImageResource(R.drawable.phone_receive);
            holder.tvDuration.setTextColor(context.getResources().getColor(R.color.primary_font_title));
            holder.tvDuration.setText(list.get(position).get("duration") + "");// 通话时长
        } else if (TextUtils.equals((list.get(position).get("type") + ""), "打出")) {  //"打出"
            holder.ivImg.setImageResource(R.drawable.phone_call);
            holder.tvDuration.setTextColor(context.getResources().getColor(R.color.primary_font_title));
            holder.tvDuration.setText(list.get(position).get("duration") + "");// 通话时长
        } else if (TextUtils.equals((list.get(position).get("type") + ""), "未接")) { //"未接"
            holder.ivImg.setImageResource(R.drawable.phone_no);
            holder.tvDuration.setTextColor(context.getResources().getColor(R.color.primary_red,null));
            holder.tvDuration.setText("未接通");// 通话时长
        } else {
            holder.ivImg.setImageResource(R.drawable.phone_no);
            holder.tvDuration.setTextColor(context.getResources().getColor(R.color.primary_red));
            holder.tvDuration.setText("未接通");// 通话时长
        }
        /**
         * 通话记录的联系人
         */
        if (TextUtils.equals((list.get(position).get("peopleName") + ""), "未备注联系人")) {// 通话记录的联系人
            holder.tvName.setText(list.get(position).get("phoneNum"));// 通话记录的联系人
        } else {
            holder.tvName.setText(list.get(position).get("peopleName") + "(" + list.get(position).get("phoneNum") + ")");// 通话记录的联系人
        }
        holder.tvTimeLead.setText(list.get(position).get("phoneNum"));// 通话距离
        return convertView;
    }

    class ImpowerCustomHolder {
        private ImageView ivImg;//图标
        private TextView tvName,//名字
                tvDuration,//时长
                tvTimeLead;//时间差
    }
}

