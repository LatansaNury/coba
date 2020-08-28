package com.example.coba.Models;

import android.content.Context;

import com.example.coba.ChatFragment;

import java.util.ArrayList;
import java.util.List;

public class TitleCreator {
    static TitleCreator _titleCreator;
    List<TitleParent> _titleParent;

    public TitleCreator(Context context) {
        _titleParent = new ArrayList<>();
        _titleParent.add(new TitleParent("2 Minggu Terakhir"));
        _titleParent.add(new TitleParent("1 Bulan Terakhir"));
        _titleParent.add(new TitleParent("6 Bulan Terakhir"));
    }

    public static TitleCreator get(Context context) {
        if(_titleCreator == null)
            _titleCreator = new TitleCreator(context);
            return _titleCreator;

    }

    public List<TitleParent> getAll() {
        return _titleParent;
    }
}
