package com.example.cerimuseum.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cerimuseum.R;
import com.example.cerimuseum.model.DataManager;

public class ListFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    Spinner spinnerSortBy;


    public ListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.listRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);

        createSortBySpinner();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void createSortBySpinner() {
        spinnerSortBy = view.findViewById(R.id.spinnerSortBy);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.SortByList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortBy.setAdapter(arrayAdapter);

        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                switch (item) {
                    case "Name (A → Z)":
                        DataManager.sortByNameAZ();
                        break;
                    case "Name (Z → A)":
                        DataManager.sortByNameZA();
                        break;
                    case "Time frame (newest)":
                        DataManager.sortByNewest();
                        break;
                    case "Time frame (oldest)":
                        DataManager.sortByOldest();
                        break;
                }

                adapter.notifyDataSetChanged();

                Toast.makeText(parent.getContext(), "Sorted by: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
