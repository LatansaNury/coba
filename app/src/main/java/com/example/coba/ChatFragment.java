package com.example.coba;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.coba.Adapter.MyAdapter;
import com.example.coba.Models.HistoryFall;
import com.example.coba.Models.TitleCreator;
import com.example.coba.Models.TitleParent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends androidx.fragment.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    MyAdapter adapter;
    List<Object> childList = new ArrayList<>();
    ArrayList<HistoryFall> histories = new ArrayList<>();
    ArrayList<HistoryFall> historiesTwoWeek  = new ArrayList<>();
    ArrayList<HistoryFall> historiesTwoMonth  = new ArrayList<>();
    ArrayList<HistoryFall> historiesSixMonth = new ArrayList<>();
    List<ParentObject> parentObject = new ArrayList<>();


//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        ((MyAdapter)recyclerView.getAdapter()).onSaveInstanceState(outState);
//    }

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);

        historiesTwoWeek = new ArrayList<>();
        historiesTwoMonth = new ArrayList<>();
        historiesSixMonth = new ArrayList<>();

        parentObject = initData();
        adapter = new MyAdapter(getContext(),parentObject );
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
        //      return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myRef = FirebaseDatabase.getInstance().getReference("history");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                histories = new ArrayList<>();
                HistoryFall item = snapshot.getValue(HistoryFall.class);
                histories.add(item);
                groupItemsByDate();
                parentObject = initData();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void groupItemsByDate() {
        for (HistoryFall historyFall : histories) {
            long diffInMillisec = getCurrentDate().getTime() - historyFall.getDate().getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);;
            if (diffInDays < 14) {
                historiesTwoWeek.add(historyFall);
            } else if (diffInDays < 60) {
                historiesTwoMonth.add(historyFall);
            } else {
                historiesSixMonth.add(historyFall);
            }
        }
    }


    private List<ParentObject> initData() {
        TitleCreator titleCreator = TitleCreator.get(getActivity());
        List<TitleParent> titles = titleCreator.getAll();
        List<ParentObject> parentObject = new ArrayList<>();
        for (int i = 0; i< titles.size();i++) {
            List<Object> childList = new ArrayList<>();
            if (i==0) {
                childList.addAll(historiesTwoWeek);
                titles.get(i).setChildObjectList(childList);
            }else if (i==1) {
                childList.addAll(historiesTwoMonth);
            }else{
                childList.addAll(historiesSixMonth);
            }
            titles.get(i).setChildObjectList(childList);
            parentObject.add(titles.get(i));
        }
        return parentObject;
    }

    private Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }


}

