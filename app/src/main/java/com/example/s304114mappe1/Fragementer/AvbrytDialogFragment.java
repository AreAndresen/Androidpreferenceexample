package com.example.s304114mappe1.Fragementer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.example.s304114mappe1.R;

public class AvbrytDialogFragment extends DialogFragment {
    private DialogClickListener callback;

    public interface DialogClickListener{
        void jaClick();
        void neiClick();
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

    //kode som lager selve dialogboksen
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.avbryt).setMessage(R.string.avbrytMsg).
                setPositiveButton(R.string.ja, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton){
                        callback.jaClick();
                    }
                })
                .setNegativeButton(R.string.nei,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton){
                        callback.neiClick();
                    }
                })
                .create();
    }

}