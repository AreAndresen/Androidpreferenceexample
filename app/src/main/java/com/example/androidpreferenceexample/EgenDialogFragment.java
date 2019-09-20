package com.example.androidpreferenceexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EgenDialogFragment extends DialogFragment {
    private DialogClickListener callback;

    public interface DialogClickListener{
        public void onYesClick();
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

        dialog.setContentView(R.layout.egendialog); //setter egen layout her
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //bare en ja knapp når spill er ferdig
                callback.onYesClick();
            }
        });
        dialog.show();
        return dialog;
    }

}

