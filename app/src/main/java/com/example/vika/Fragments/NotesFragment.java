package com.example.vika.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vika.Activities.NotesItemActivity;
import com.example.vika.Classes.Constants;
import com.example.vika.Classes.Note;
import com.example.vika.R;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class NotesFragment extends Fragment {
    public static final String TAG = Constants.NOTESFRAGMENTTAG;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final int MENU_EDIT = 1;
    final int MENU_DELETE = 2;
    private String mParam1;
    private String mParam2;
    ArrayList<Note> notes = new ArrayList<>();
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    private OnFragmentInteractionListener mListener;

    public NotesFragment() {}

    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotesItemActivity.class);
                startActivity(intent);
            }
        });
        gridView = getView().findViewById(R.id.mygridview);
        gridViewAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item, notes);
        gridView.setAdapter(gridViewAdapter);
        if(Paper.book().contains("Counter")){
            int counter = Paper.book().read("Counter");
            for (int i = 0; i<=counter; i++){
                String count = String.valueOf(i);
                if (Paper.book().contains(count)){
                    Note note = Paper.book().read(count);
                    notes.add(note);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {super.onAttach(context);}

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class GridViewAdapter extends ArrayAdapter<Note> {

        private List<Note> context_menu;

        public GridViewAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if(null == v){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.grid_item, null);
            }
            Note note = getItem(position);
            TextView title = v.findViewById(R.id.notes_title);
            TextView desc = v.findViewById(R.id.notes_desc);

            title.setText(note.getTitle());
            desc.setText(note.getDesc());
            registerForContextMenu(v);
            return v;
        }
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {}};
}