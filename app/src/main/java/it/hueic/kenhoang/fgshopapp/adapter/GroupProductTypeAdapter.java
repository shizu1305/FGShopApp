package it.hueic.kenhoang.fgshopapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import it.hueic.kenhoang.fgshopapp.adapter.viewholder.GroupProductTypeHolder;
import it.hueic.kenhoang.fgshopapp.common.Common;
import it.hueic.kenhoang.fgshopapp.object.GroupProductType;

public class GroupProductTypeAdapter extends RecyclerView.Adapter<GroupProductTypeHolder> {
    Context context;
    List<GroupProductType> list;
    int resource;

    public GroupProductTypeAdapter(Context context, List<GroupProductType> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public GroupProductTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        return new GroupProductTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(final GroupProductTypeHolder holder, int position) {
        GroupProductType object = list.get(position);
        holder.name.setText(object.getName_group());
        Picasso.with(context)
                .load(Common.URL + object.getImage())
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
