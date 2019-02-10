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
import com.mahmoud_galal.mobilabtask.model.Image;

import java.util.List;

/**
 * Gallery Images Adapter for the Gallery Recycler
 */
public class GalleryImagesAdapter extends RecyclerView.Adapter<GalleryImagesAdapter.ViewHolder> {


    private List<Image> items;
    private OnItemClickedListener onItemClickedListener;

    public GalleryImagesAdapter(List<Image> items){
        this.items = items ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.gallery_image_item,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Image item = items.get(position);
        holder.descriptionTxt.setText("Description:"+(item.description==null?"":item.description));
        holder.titleTxt.setText("Title:"+(item.title==null?"":item.title));
        holder.downsCountTxt.setText(item.downs+"");
        holder.upsCountTxt.setText(item.ups+"");
        holder.scoreTxt.setText(item.score+"");
        boolean isGif = item.isGif() ;
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
                    load(item.link).asBitmap().crossFade().fitCenter().
                    placeholder(R.drawable.gif_loading_background);

        else
            genericRequestBuilder =  Glide.with(holder.srcImage.getContext()).
                    load(item.link).crossFade().fitCenter().
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

    public interface OnItemClickedListener{
        void onItemClicked(View clickedView,Image item,int position);
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView srcImage;
        TextView descriptionTxt,titleTxt,upsCountTxt,downsCountTxt,scoreTxt;
        public ViewHolder(View itemView) {
            super(itemView);
            srcImage = itemView.findViewById(R.id.src_img);
            descriptionTxt = itemView.findViewById(R.id.desc_txt);
            titleTxt = itemView.findViewById(R.id.title_txt);
            upsCountTxt = itemView.findViewById(R.id.img_ups_txt);
            downsCountTxt = itemView.findViewById(R.id.img_downs_txt);
            scoreTxt = itemView.findViewById(R.id.score_txt);

            descriptionTxt.setSelected(true);
            descriptionTxt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            titleTxt.setSelected(true);
            titleTxt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        }
    }
    public List<Image> getItems() {
        return items;
    }

    public void setItems(List<Image> items) {
        this.items = items;
    }

}
