package com.edcan.grooshbene.soundeffector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GrooshBene on 2015. 11. 23..
 */
public class DataAdapter extends ArrayAdapter<CData> {
    // 레이아웃 XML을 읽어들이기 위한 객체
    private LayoutInflater mInflater;
    TextView article;
    TextView writer;
    TextView time;


    public DataAdapter(Context context, ArrayList<CData> object) {
        // 상위 클래스의 초기화 과정
        // context, 0, 자료구조
        super(context, 0, object);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "NotoSansCJKkr-Regular.otf");
//        name.setTypeface(tf);
//        family.setTypeface(tf);
//        phone.setTypeface(tf);
    }

    // 보여지는 스타일을 자신이 만든 xml로 보이기 위한 구문
    @Override
    public View getView(int position, View v, ViewGroup parent) {

        View view = null;
        // 현재 리스트의 하나의 항목에 보일 컨트롤 얻기
        if (v == null) {
            // XML 레이아웃을 직접 읽어서 리스트뷰에 넣음
            view = mInflater.inflate(R.layout.custom_list, null);
        } else {
            view = v;
        }
        // 자료를 받는다.
        final CData data = this.getItem(position);
        if (data != null) {
            //화면 출력
//            phone = (TextView)view.findViewById(R.id.tv_list);
//            phone.setText(data.getPhone());
            article = (TextView)view.findViewById(R.id.textView1);
            writer = (TextView)view.findViewById(R.id.textView2);
            time = (TextView)view.findViewById(R.id.textView3);
            article.setText(data.getArticle());
            writer.setText(data.getWriter());
            time.setText(data.getTime());
        }
        return view;
    }
}