package com.kpi.slava.wishlistapp.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.database.DBHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControlNoteFragment extends DialogFragment{

    public static final String TAG = "ControlNoteFragment";

    private final int LAYOUT = R.layout.fragment_note_control;

    private View view;

    private TextInputLayout tilTitle, tilText;
    private EditText edtTitle, edtText;

    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("New note");

        view = inflater.inflate(LAYOUT, container, false);

        tilTitle = (TextInputLayout) view.findViewById(R.id.til_enter_note_title);
        edtTitle = (EditText) tilTitle.findViewById(R.id.edt_enter_note_title);
        tilTitle.setHint("Enter title*");

        tilText = (TextInputLayout) view.findViewById(R.id.til_enter_note_text);
        edtText = (EditText) tilText.findViewById(R.id.edt_enter_note_text);
        tilText.setHint("Enter text*");

        dbHelper = new DBHelper(getContext());

        view.findViewById(R.id.btn_enter_note_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btn_enter_note_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String text = edtText.getText().toString();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
                String date = dateFormat.format(new Date());

                if(!title.equals("") && (!title.equals(" ")) && (!text.equals(""))){

                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    database.insert(DBHelper.TABLE_NOTES, null, setContentValues(title, text));

                    dbHelper.close();
                    dismiss();

                }
                else Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private ContentValues setContentValues(String title, String text) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, title);
        contentValues.put(DBHelper.KEY_TEXT, text);

        return contentValues;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        edtTitle.setText("");
        edtText.setText("");
    }
}
