package com.oceanmtech.shagun.LoginModule;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.oceanmtech.shagun.R;

public class GenericTextWatcher implements TextWatcher {
    private final EditText[] editText;
    private View view;

    public GenericTextWatcher(View view, EditText editText[]) {
        this.editText = editText;
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String text = s.toString();
        switch (view.getId()) {

            case R.id.etOtp1:
                if (text.length() == 1)
                    editText[1].requestFocus();
                break;

            case R.id.etOtp2:
                if (text.length() == 1)
                    editText[2].requestFocus();
                else if (text.length() == 0)
                    editText[0].requestFocus();
                break;

            case R.id.etOtp3:
                if (text.length() == 1)
                    editText[3].requestFocus();
                else if (text.length() == 0)
                    editText[1].requestFocus();
                break;

            case R.id.etOtp4:
                if (text.length() == 1)
                    editText[4].requestFocus();
                else if (text.length() == 0)
                    editText[2].requestFocus();
                break;

            case R.id.etOtp5:
                if (text.length() == 1)
                    editText[5].requestFocus();
                else if (text.length() == 0)
                    editText[3].requestFocus();
                break;

            case R.id.etOtp6:
                if (text.length() == 0)
                    editText[4].requestFocus();
                break;
        }
    }
}
