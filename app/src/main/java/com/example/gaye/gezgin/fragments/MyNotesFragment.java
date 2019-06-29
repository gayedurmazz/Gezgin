package com.example.gaye.gezgin.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gaye.gezgin.R;
import com.example.gaye.gezgin.activities.AddNoteActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyNotesFragment extends Fragment {

    private ListView lvNotes;
    private ArrayList<String> myNoteList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_notes, container, false);
        lvNotes = (ListView) view.findViewById(R.id.lv_notes);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, myNoteList);
        lvNotes.setAdapter(adapter);

        view.findViewById(R.id.btn_add_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(i);
            }
        });
        getMyNotes();
        return view;
    }

    private void getMyNotes() {
        //servis kullanılan yerlerde bekleme süresi olacağından progress dialog kullanılması gerek
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference().child("GezdiğimYerler");

        //verilerin değişimiyle etkilenir
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                myNoteList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String place = ds.child("sehirAdi").getValue().toString();
                    myNoteList.add(place);
                }
                //adapter'a bağlı listedeğiştiğinde listeyi günceller
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

}