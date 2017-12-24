package com.moictab.yonunca.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moictab.yonunca.R;
import com.moictab.yonunca.model.Statistic;

import java.util.List;

/**
 * Created by moict on 06/12/2017.
 */

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {

    private List<Statistic> statistics;

    public StatisticsAdapter(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistic_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(statistics.get(position).name);
        holder.tvNumber.setText(String.valueOf(statistics.get(position).number));
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvNumber;

        ViewHolder(View view) {
            super(view);

            this.tvName = view.findViewById(R.id.tv_name);
            this.tvNumber = view.findViewById(R.id.tv_number);
        }
    }
}
