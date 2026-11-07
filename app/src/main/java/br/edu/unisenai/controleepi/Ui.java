package br.edu.unisenai.controleepi;

import android.view.View;
import android.widget.*;
import androidx.core.view.*;

public final class Ui {
  private Ui() {}

  public static void inset(View root) {
    int l = root.getPaddingLeft(),
        t = root.getPaddingTop(),
        r = root.getPaddingRight(),
        b = root.getPaddingBottom();
    ViewCompat.setOnApplyWindowInsetsListener(
        root,
        (v, w) -> {
          androidx.core.graphics.Insets i =
              w.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
          v.setPadding(l + i.left, t + i.top, r + i.right, b + i.bottom);
          return w;
        });
  }

  public static String texto(EditText v) {
    return v.getText().toString().trim();
  }
}
