package com.example.android.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditAgenda extends AppCompatActivity {

    private EditText editTextCompromisso;
    private EditText editTextDescricao;
    private EditText editTextData;

    private int index;

    private Compromisso compromisso;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_agenda);

        setupViews();
        initComp();
    }

    private void setupViews(){
        editTextCompromisso = findViewById(R.id.editTextCompromisso2);
        editTextDescricao = findViewById(R.id.editTextDescricao2);
        editTextData = findViewById(R.id.editTextData2);
    }

    private void initComp() {
        Intent intent = getIntent();
        if(intent != null){

                compromisso = intent.getParcelableExtra(MainActivity.EXTRA_MESSAGE_OBJECT);
                index = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_INDEX, -1);

                editTextCompromisso.setText(compromisso.getTexto1());
                editTextDescricao.setText(compromisso.getTexto2());
                editTextData.setText(compromisso.getDate());

        }
    }

    public void editar(View v){
        String compromisso = editTextCompromisso.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String data = editTextData.getText().toString().trim();

        Compromisso comp = new Compromisso(compromisso,descricao,data);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.EXTRA_MESSAGE_OBJECT, comp);

        bundle.putInt(MainActivity.EXTRA_MESSAGE_INDEX, index);
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();


    }
}
