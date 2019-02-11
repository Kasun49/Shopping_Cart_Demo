package com.example.acer.slt_lite;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.slt_lite.common.common;


public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    private Button btnTEST;
    TextView name,add,email,pass;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3,container,false);

        name= (TextView) view.findViewById(R.id.editText);
        add= (TextView) view.findViewById(R.id.editText2);
        email= (TextView) view.findViewById(R.id.editText3);
        pass= (TextView) view.findViewById(R.id.editText4);


        name.setText(common.fname);
        add.setText(common.address);
        email.setText(common.email);
        pass.setText(common.sub);


        return view;


    }
}