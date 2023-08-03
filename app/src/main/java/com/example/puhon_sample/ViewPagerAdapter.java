package com.example.puhon_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int images[] = {
            R.drawable.undraw_mornings_re_cofi,
            R.drawable.undraw_personal_goals_re_iow7,
            R.drawable.undraw_walking_outside_re_56xo
    };

    int title[] = {
            R.string.daily_logging_title,
            R.string.daily_goals_title,
            R.string.stress_act_title
    };

    int description[] = {
            R.string.daily_logging_text,
            R.string.daily_goals_text,
            R.string.stress_act_text
    };

    public ViewPagerAdapter (Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView sliderLayoutImage = (ImageView) view.findViewById(R.id.sliderImage);
        TextView sliderLayoutTitle = (TextView) view.findViewById(R.id.sliderTitle);
        TextView sliderLayoutText = (TextView) view.findViewById(R.id.sliderText);

        sliderLayoutImage.setImageResource(images[position]);
        sliderLayoutTitle.setText(title[position]);
        sliderLayoutText.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
