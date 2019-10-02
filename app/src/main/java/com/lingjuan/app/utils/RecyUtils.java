package com.lingjuan.app.utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: TaoHui
 * @date: 2019/1/17
 */
public class RecyUtils {

    public static void setLayoutManager(RecyclerView recyclerView, Context context){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }
}
