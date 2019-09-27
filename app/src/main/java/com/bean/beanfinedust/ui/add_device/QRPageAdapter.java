package com.bean.beanfinedust.ui.add_device;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.beanfinedust.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class QRPageAdapter extends PagerAdapter {
    private Context mContext = null ;

    // Context를 전달받아 mContext에 저장하는 생성자 추가.
    public QRPageAdapter(Context context) {
        mContext = context ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null ;

        if (mContext != null) {
            // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.first_page, container, false);

            if(position == 1){
                ImageView imageView = view.findViewById(R.id.qr_image);
                imageView.setImageResource(R.drawable.example_qr);
                TextView textView = view.findViewById(R.id.qr_text);
                textView.setText("장치 후면의\nQR코드를 인식해 주세요.");
            }
        }

        // 뷰페이저에 추가.
        container.addView(view);

        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 전체 페이지 수는 10개로 고정.
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }


}
