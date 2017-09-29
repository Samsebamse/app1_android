package com.example.sami.hangman_v10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Button;


/**
 * Created by Sami on 29-Sep-17.
 */

public class TopFragment extends Fragment {

    private Button backButton;
    private Activity activity;

    public interface TopSection{

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment, container, false);

        this.activity = getActivity();

        backButton = (Button) view.findViewById(R.id.backToMenuButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                backButtonClicked(view);
            }
        });

        return view;
    }

    public void backButtonClicked(View view){
        Intent mainMenu = new Intent(activity, MainActivity.class);
        startActivity(mainMenu);
        activity.finish();
    }
}
