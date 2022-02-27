package com.litongjava.android.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

/**
 * 自定义EditText
 */
public class CommonEditText extends AppCompatEditText {

  public CommonEditText(Context context) {
    super(context);
  }

  public CommonEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    addTextChangedListener(new MyTextWatcher());
  }

  public class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      if (onTextChaged != null) {
        onTextChaged.setText(s + "");
      }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
  }


  public interface OnTextChaged {
    void setText(String s);
  }

  public OnTextChaged onTextChaged;

  public void setOnTextChaged(OnTextChaged onTextChaged) {
    this.onTextChaged = onTextChaged;
  }

  public void setTextSize(float size) {
    setTextSize(size);
  }

  public void setTextColor(Context context, int colorId) {
    setTextColor(ContextCompat.getColor(context, colorId));
  }

  public void setHintColor(Context context, int colorId) {
    setHintTextColor(ContextCompat.getColor(context, colorId));
  }
}
