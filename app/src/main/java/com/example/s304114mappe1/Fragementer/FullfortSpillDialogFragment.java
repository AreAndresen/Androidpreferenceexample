package com.example.s304114mappe1.Fragementer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.s304114mappe1.R;

public class FullfortSpillDialogFragment extends DialogFragment {
    private DialogClickListener callback;

    public interface DialogClickListener{
        void fullfortSpillClick();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            callback = (DialogClickListener)getActivity();
        }
        catch(ClassCastException e) {
            throw new ClassCastException("Feil ved kalling av interface!");
        }
    }


    //Egendefinert alertboks kalles her
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.fullfortspilldialog); //setter egen layout her
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);

       //bare en ja knapp når spill er ferdig
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.fullfortSpillClick();
            }
        });
        dialog.show();
        return dialog;
    }

}

