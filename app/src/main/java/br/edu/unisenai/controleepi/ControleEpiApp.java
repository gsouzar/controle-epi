package br.edu.unisenai.controleepi;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;

public class ControleEpiApp extends Application {
  public static boolean configurado() {
    return FirebaseApp.getApps(instancia).size() > 0;
  }

  private static ControleEpiApp instancia;

  @Override
  public void onCreate() {
    super.onCreate();
    instancia = this;
    FirebaseApp.initializeApp(this);
    if (configurado()) {
      FirebaseFirestore.getInstance()
          .setFirestoreSettings(
              new FirebaseFirestoreSettings.Builder()
                  .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                  .build());
    }
  }
}
