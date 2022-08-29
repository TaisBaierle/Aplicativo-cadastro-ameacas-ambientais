package br.feevale.ameacasambientais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AmeacasAmbientaisEdit extends AppCompatActivity {

    BancoDados db;
    EditText edEndereco, edDescricao;
    Button btDataNova;
    AmeacasAmbientais ameacasAmbientalAtual;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ameacas_ambientais_edit);

        edEndereco = findViewById(R.id.edEndereco);
        edDescricao = findViewById(R.id.edDescricao);
        btDataNova = findViewById(R.id.btDataEditada);
        initDatePicker();

        db = new BancoDados(getBaseContext());
        Long id = getIntent().getLongExtra("ID", 0);

        ameacasAmbientalAtual = db.getAmeacaAmbiental(id);

        edEndereco.setText(ameacasAmbientalAtual.getEndereco());
        edDescricao.setText(ameacasAmbientalAtual.getDescricao());
        btDataNova.setText(ameacasAmbientalAtual.getData());
    }

    public void updateAmeacaAmbiental(View v) {
        ameacasAmbientalAtual.setEndereco(edEndereco.getText().toString());
        ameacasAmbientalAtual.setDescricao(edDescricao.getText().toString());
        ameacasAmbientalAtual.setData(btDataNova.getText().toString());

        if (ameacasAmbientalAtual.getDescricao().isEmpty()) {
            Toast.makeText(getBaseContext(), "Necessário informar pelo menos uma descrição!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Ameaça ambiental alterada com sucesso!", Toast.LENGTH_LONG).show();
            db.atualizarAmeacaAmbiental(ameacasAmbientalAtual);
            finish();
        }

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes = mes + 1;
                String data = criarStringData(dia, mes, ano);
                btDataNova.setText(data);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, ano, mes, dia);
    }

    private String criarStringData(int dia, int mes, int ano) {
        return dia + "/" + formataMes(String.valueOf(mes)) + "/" + ano;
    }

    public String formataMes(String mes) {
        if (mes.length() == 2) {
            return mes;
        } else {
            return "0" + mes;
        }
    }

    public void editarSeletorData(View view) {
        datePickerDialog.show();
    }
}