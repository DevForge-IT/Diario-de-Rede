package com.example.diarioderede;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvNetworkStatus;
    private Button btnOpenNotes;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private String currentNetworkState = "Sem ligação";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNetworkStatus = findViewById(R.id.tvTitle);
        btnOpenNotes = findViewById(R.id.btnOpenNotes);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        configurarMonitorDeRede();

        btnOpenNotes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            intent.putExtra("CURRENT_NETWORK", currentNetworkState);
            startActivity(intent);
        });
    }

    private void configurarMonitorDeRede() {
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                atualizarUI();
            }

            @Override
            public void onLost(@NonNull Network network) {
                atualizarUI();
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities capabilities) {
                atualizarUI();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (connectivityManager != null) {
            NetworkRequest request = new NetworkRequest.Builder().build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
        atualizarUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    private void atualizarUI() {
        runOnUiThread(() -> {
            currentNetworkState = obterTipoConexao();
            tvNetworkStatus.setText("Rede atual: " + currentNetworkState);
        });
    }

    private String obterTipoConexao() {
        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return "Wi-Fi";
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return "Dados móveis";
                    }
                }
            }
        }
        return "Sem ligação";
    }
}