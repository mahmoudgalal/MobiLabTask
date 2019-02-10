/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mahmoud_galal.mobilabtask.R;
import com.mahmoud_galal.mobilabtask.model.Gallery;
import com.mahmoud_galal.mobilabtask.model.Image;
import java.util.List;

/**
 * Adapter for the Galleries adapter
 */
public class GalleriesAdapter extends RecyclerView.Adapter<GalleriesAdapter.ViewHolder> {

    public List<Gallery> getItems() {
        return items;
    }

    public void setItems(List<Gallery> items) {
        this.items = items;
    }
    public GalleriesAdapter(List<Gallery> items){
        this.items = items ;
    }

    private List<Gallery> items;
    private OnItemClickedListener onItemClickedListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.gallery_item,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Gallery item = items.get(position);
        holder.descriptionTxt.setText("Title:"+(item.title == null?"":item.title));
        Image coverImage = item.findCoverImage();
        String imgLink = coverImage != null?coverImage.link:null;
        boolean isGif = coverImage != null && coverImage.isGif() ;
        holder.coverTxt.setVisibility(imgLink == null?View.VISIBLE:View.INVISIBLE);
        String imgCount = item.images != null ?item.images.size()+"":"00";
        holder.imgCountTxt.setText(imgCount);

        holder.downsCountTxt.setText(item.downs+"");
        holder.upsCountTxt.setText(item.ups+"");
        holder.scoreTxt.setText(item.score+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickedListener != null){
                    onItemClickedListener.onItemClicked(view,item,items.indexOf(item));
                }
            }
        });

        GenericRequestBuilder genericRequestBuilder;
        if(isGif)
            genericRequestBuilder = Glide.with(holder.srcImage.getContext().
                    getApplicationContext()).
                    load(imgLink).asBitmap().crossFade().fitCenter().
                    placeholder(R.drawable.gif_loading_background);

        else
            genericRequestBuilder =  Glide.with(holder.srcImage.getContext()).
                    load(imgLink).crossFade().fitCenter().
                    placeholder(R.drawable.ic_launcher_background);
        genericRequestBuilder.
                    error(R.drawable.image_error_background).
                    diskCacheStrategy(DiskCacheStrategy.ALL).
                    into(holder.srcImage);
    }

    @Override
    public int getItemCount() {
        return items != null?items.size(): 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView srcImage;
        TextView descriptionTxt,imgCountTxt,coverTxt,
                upsCountTxt,downsCountTxt,scoreTxt;
        public ViewHolder(View itemView) {
            super(itemView);
            srcImage = itemView.findViewById(R.id.src_img);
            descriptionTxt = itemView.findViewById(R.id.desc_txt);
            imgCountTxt = itemView.findViewById(R.id.img_count_txt);
            coverTxt = itemView.findViewById(R.id.cover_txt);

            upsCountTxt = itemView.findViewById(R.id.img_ups_txt);
            downsCountTxt = itemView.findViewById(R.id.img_downs_txt);
            scoreTxt = itemView.findViewById(R.id.score_txt);

            descriptionTxt.setSelected(true);
            descriptionTxt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        }
    }

    /**
     * Listener for the list item clicked event
     */
    public interface OnItemClickedListener{
        void onItemClicked(View clickedView,Gallery item,int position);
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

}
