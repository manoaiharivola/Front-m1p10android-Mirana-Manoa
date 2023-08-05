package emds.example.com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private boolean isConnected = true;

    public NetworkChangeReceiver(boolean isConnected) {
        this.isConnected = isConnected;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            if (activeNetwork != null && activeNetwork.isConnected()) {
                // Connexion Internet rétablie, si isConnected est false
                if (!isConnected) {
                    Toast.makeText(context, "Connexion rétablie", Toast.LENGTH_SHORT).show();
                }
                isConnected = true;
            } else {
                // Pas de connexion Internet
                isConnected = false;
                Toast.makeText(context, "Pas de connexion Internet", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

