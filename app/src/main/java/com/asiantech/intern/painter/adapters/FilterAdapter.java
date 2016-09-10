package com.asiantech.intern.painter.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asiantech.intern.painter.R;
import com.asiantech.intern.painter.beans.FilterImage;
import com.asiantech.intern.painter.commons.Constant;
import com.asiantech.intern.painter.utils.ImageFilterUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Copyright © 2016 YNC.
 * Created by Congybk on 8/31/2016.
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private final Context mContext;
    private final Bitmap mBitmap;
    private List<FilterImage> mFilterImages;

    public FilterAdapter(Context context, Bitmap bitmap) {
        this.mContext = context;
        mBitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
        initListFilters();
    }

    public static Bitmap resizeBitmap(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());
        System.gc();
        return Bitmap.createScaledBitmap(realImage, width, height, filter);
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_image, parent, false);
        return new FilterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FilterViewHolder holder, int position) {
        holder.tvNameFilter.setText(mFilterImages.get(position).getNameFilter());
        new AsyncTask<Integer, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Integer... integers) {
                return getFilterBitmap(integers[0], false);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                holder.imageViewFilter.setImageBitmap(bitmap);
            }
        }.execute(mFilterImages.get(position).getTypeFilter());
    }

    @Override
    public int getItemCount() {
        return mFilterImages.size();
    }

    private void initListFilters() {
        mFilterImages = new ArrayList<>();
        mFilterImages.add(new FilterImage(Constant.CONTRAST, mContext.getString(R.string.filter_name_contrast)));
        mFilterImages.add(new FilterImage(Constant.INVERT, mContext.getString(R.string.filter_name_invert)));
        mFilterImages.add(new FilterImage(Constant.HUE, mContext.getString(R.string.filter_name_hue)));
        mFilterImages.add(new FilterImage(Constant.SEPIA, mContext.getString(R.string.filter_name_sepia)));
        mFilterImages.add(new FilterImage(Constant.GRAYSCALE, mContext.getString(R.string.filter_name_gray_scale)));
        mFilterImages.add(new FilterImage(Constant.VIGNETTE, mContext.getString(R.string.filter_name_vignette)));
        mFilterImages.add(new FilterImage(Constant.SKETCH, mContext.getString(R.string.filter_name_sketch)));
        mFilterImages.add(new FilterImage(Constant.BRIGHTNESS, mContext.getString(R.string.filter_name_brightness)));
    }

    public FilterImage getPositionItem(int position) {
        return mFilterImages.get(position);
    }

    public Bitmap getFilterBitmap(int type, boolean resize) {
        final int DEFAULT_SIZE = 50;
        if (resize) {
            switch (type) {
                case Constant.CONTRAST:
                    final int DEFAULT_CONTRAST = 1;
                    return resizeBitmap(ImageFilterUtils.doContrast(mBitmap, DEFAULT_CONTRAST), DEFAULT_SIZE, false);
                case Constant.INVERT:
                    return resizeBitmap(ImageFilterUtils.doInvertImage(mBitmap), DEFAULT_SIZE, false);
                case Constant.HUE:
                    final int DEFAULT_HUE = 50;
                    return resizeBitmap(ImageFilterUtils.doHue(mBitmap, DEFAULT_HUE), DEFAULT_SIZE, false);
                case Constant.SEPIA:
                    final float DEFAULT_SEPIA = 0.45f;
                    return resizeBitmap(ImageFilterUtils.doSepia(mBitmap, DEFAULT_SEPIA), DEFAULT_SIZE, false);
                case Constant.GRAYSCALE:
                    return resizeBitmap(ImageFilterUtils.doGrayScale(mBitmap), DEFAULT_SIZE, false);
                case Constant.VIGNETTE:
                    final float DEFAULT_VIGNETTE = 0.8f;
                    return resizeBitmap(ImageFilterUtils.doVignette(mBitmap, DEFAULT_VIGNETTE), DEFAULT_SIZE, false);
                case Constant.SKETCH:
                    return resizeBitmap(ImageFilterUtils.doSketch(mBitmap, mContext), DEFAULT_SIZE, false);
                case Constant.BRIGHTNESS:
                    final int DEFAULT_BRIGHTNESS = 0;
                    return resizeBitmap(ImageFilterUtils.doBrightness(mBitmap, DEFAULT_BRIGHTNESS), DEFAULT_SIZE, false);
            }
        } else {
            switch (type) {
                case Constant.CONTRAST:
                    final int DEFAULT_CONTRAST = 1;
                    return ImageFilterUtils.doContrast(mBitmap, DEFAULT_CONTRAST);
                case Constant.INVERT:
                    return ImageFilterUtils.doInvertImage(mBitmap);
                case Constant.HUE:
                    final int DEFAULT_HUE = 50;
                    return ImageFilterUtils.doHue(mBitmap, DEFAULT_HUE);
                case Constant.SEPIA:
                    final float DEFAULT_SEPIA = 0.45f;
                    return ImageFilterUtils.doSepia(mBitmap, DEFAULT_SEPIA);
                case Constant.GRAYSCALE:
                    return ImageFilterUtils.doGrayScale(mBitmap);
                case Constant.VIGNETTE:
                    final float DEFAULT_VIGNETTE = 0.8f;
                    return ImageFilterUtils.doVignette(mBitmap, DEFAULT_VIGNETTE);
                case Constant.SKETCH:
                    return ImageFilterUtils.doSketch(mBitmap, mContext);
                case Constant.BRIGHTNESS:
                    final int DEFAULT_BRIGHTNESS = 0;
                    return ImageFilterUtils.doBrightness(mBitmap, DEFAULT_BRIGHTNESS);
            }
        }
        return null;
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imageViewFilter;
        private final TextView tvNameFilter;

        public FilterViewHolder(View itemView) {
            super(itemView);
            imageViewFilter = (CircleImageView) itemView.findViewById(R.id.circleImagViewFilter);
            tvNameFilter = (TextView) itemView.findViewById(R.id.tvNameFilter);
        }
    }
}
