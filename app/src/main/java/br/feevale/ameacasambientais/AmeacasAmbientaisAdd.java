package br.feevale.ameacasambientais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AmeacasAmbientaisAdd extends AppCompatActivity {

    BancoDados db;
    EditText edEndereco, edDescricao;
    Button btData;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ameacas_ambientais_add);

        edEndereco = findViewById(R.id.edEnderecoNovo);
        edDescricao = findViewById(R.id.edDescricaoNova);
        btData = findViewById(R.id.btData);
        db = new BancoDados(getBaseContext());
        initDatePicker();

        btData.setText(pegarData());
    }

    private String pegarData() {
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = mes + 1;
        return dia + "/" + formataMes(String.valueOf(mes)) + "/" + ano;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes = mes + 1;
                String data = criarStringData(dia, mes, ano);
                btData.setText(data);
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

    public void addAmeaca(View v) {
        AmeacasAmbientais ameaca = new AmeacasAmbientais();
        ameaca.setEndereco(edEndereco.getText().toString());
        ameaca.setData(btData.getText().toString());
        ameaca.setDescricao(edDescricao.getText().toString());

        if (ameaca.getDescricao().isEmpty()) {
            Toast.makeText(getBaseContext(), "Necessário informar pelo menos uma descrição!", Toast.LENGTH_LONG).show();
        }else{
            db.adicionarAmeacaAmbiental(ameaca);
            Toast.makeText(getBaseContext(), "Ameaça ambiental adicionada com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void abrirSeletorData(View view) {

        datePickerDialog.show();
    }

    public String formataMes(String mes){
        if (mes.length() == 2){
            return mes;
        }else{
            return "0" + mes;
        }
    }
}