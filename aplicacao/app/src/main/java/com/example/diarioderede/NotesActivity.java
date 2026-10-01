package com.example.diarioderede;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity {

    private EditText etNote;
    private Button btnAddNote;
    private RecyclerView rvNotes;
    private NoteAdapter adapter;
    private List<Note> noteList;
    private String currentNetworkState;

    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        currentNetworkState = getIntent().getStringExtra("CURRENT_NETWORK");
        if (currentNetworkState == null) currentNetworkState = "Sem ligação";

        etNote = findViewById(R.id.etNote);
        btnAddNote = findViewById(R.id.btnAddNote);
        rvNotes = findViewById(R.id.rvNotes);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        configurarMonitorDeRede();

        noteList = new ArrayList<>();
        adapter = new NoteAdapter(noteList);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(adapter);

        btnAddNote.setOnClickListener(v -> addNote());
    }

    private void addNote() {
        String text = etNote.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Escreva algum texto para guardar", Toast.LENGTH_SHORT).show();
            return;
        }

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        currentNetworkState = obterTipoConexao();

        Note newNote = new Note(text, timeStamp, currentNetworkState);

        noteList.add(0, newNote);
        adapter.notifyItemInserted(0);
        rvNotes.scrollToPosition(0);

        etNote.setText("");
    }

    private void configurarMonitorDeRede() {
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                currentNetworkState = obterTipoConexao();
            }

            @Override
            public void onLost(@NonNull Network network) {
                currentNetworkState = "Sem ligação";
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities capabilities) {
                currentNetworkState = obterTipoConexao();
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
        currentNetworkState = obterTipoConexao();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
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