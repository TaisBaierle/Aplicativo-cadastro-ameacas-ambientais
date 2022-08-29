package br.feevale.ameacasambientais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ListView listaAmeacas;
    AmeacasAmbientasAdapter adapter;
    BancoDados db;
    Button btNovaAmeaca;
    int idSelecaoExclusao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new BancoDados(getBaseContext());

        btNovaAmeaca = findViewById(R.id.btNovaAmeaca);
        listaAmeacas = findViewById(R.id.listAmeacas);

        adapter = new AmeacasAmbientasAdapter(db, getBaseContext());
        listaAmeacas.setAdapter(adapter);

        listaAmeacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(MainActivity.this, AmeacasAmbientaisEdit.class);
                it.putExtra("ID", l);
                startActivity(it);
            }

        });

        listaAmeacas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecaoExclusao = i;
                confirmaExclusao();
                return true;
            }
        });
    }

    public void incluirAmeaca(View v) {
        Intent it = new Intent(MainActivity.this, AmeacasAmbientaisAdd.class);
        startActivity(it);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void confirmaExclusao(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Aviso de exclusão!!");
        dialog.setIcon(android.R.drawable.ic_menu_delete);
        dialog.setMessage("Deseja excluir o registro?");
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.removerAmeacaAmbiental((AmeacasAmbientais) adapter.getItem(idSelecaoExclusao));
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Registro excluído com sucesso!", Toast.LENGTH_LONG).show();
            }
        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.show();
    }
}