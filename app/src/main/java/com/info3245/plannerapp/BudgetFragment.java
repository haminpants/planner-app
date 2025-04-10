package com.info3245.plannerapp;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.time.YearMonth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MONTH = "month";
//    private static final String ARG_YEAR = "year";
//    private YearMonth month;
//    private YearMonth year;

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private YearMonth month;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param month Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(YearMonth month) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MONTH, month.toString());
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                month = YearMonth.parse(getArguments().getString(ARG_MONTH));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        TextView txtBudget = view.findViewById(R.id.txtBudget);
        txtBudget.setText(("Budget for: " + month.toString()));
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }
}