package com.example.amerair;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.List;
import java.util.ArrayList;


public class MyDialog extends DialogFragment {

    private List<String> mSelectedItems;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mSelectedItems = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose");

        builder.setMultiChoiceItems(R.array.Avoid, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                String[] items = getActivity().getResources().getStringArray(R.array.Avoid);

                if(isChecked)
                {
                    mSelectedItems.add(items[which]);
                }
                else if(mSelectedItems.contains(items[which]))
                {
                    mSelectedItems.remove(items[which]);
                }
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String final_selection = "";

                for(String Item : mSelectedItems)
                {
                    final_selection = final_selection+"\n"+Item;
                }
                Toast.makeText(getActivity(), "Selection : "+final_selection,Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

}
