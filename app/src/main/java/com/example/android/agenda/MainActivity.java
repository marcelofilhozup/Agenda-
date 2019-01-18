package com.example.android.agenda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements OnEditListener, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private AgendaListAdapter mAdapter;

    private  LinkedList<Compromisso> listaCompromisso = new LinkedList<>();
    private  LinkedList<Compromisso> aux = new LinkedList<>();

    public static final String EXTRA_MESSAGE_INDEX =
            "INDEX";
    public static final String EXTRA_MESSAGE_OBJECT =
            "OBJECT";
    public static final String EXTRA_MESSAGE_EDIT =
            "TRUE";
    //LinkedList<Compromisso> myList;
    private final int REQUEST_CODE_EDIT = 101;

    public static final int TEXT_REQUEST = 1;
    Compromisso simpleClass;

    TextView mEditText;

    public MainActivity() throws FileNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEditText = findViewById(R.id.TESTE);

        FileInputStream fis = null;
        try {
            fis = this.openFileInput("Agenda.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            listaCompromisso = (LinkedList<Compromisso>) is.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new AgendaListAdapter(this, listaCompromisso,this);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    Compromisso t = new Compromisso("TESTE1","TESTE2","TESTE3");

    public void salvarF(View v) throws IOException {

        FileOutputStream fos = this.openFileOutput("Agenda.txt", Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(listaCompromisso);
        os.close();
        fos.close();

    }


    public void carregar(View v) throws IOException, ClassNotFoundException {
        FileInputStream fis = this.openFileInput("Agenda.txt");
        ObjectInputStream is = new ObjectInputStream(fis);
        aux = (LinkedList<Compromisso>) is.readObject();


        is.close();
        fis.close();
    }



    public void imprimir(View v){



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void launchNovoCompromisso(View view) {

        Intent intent = new Intent(this, NovoCompromisso.class);
        startActivityForResult(intent,TEXT_REQUEST  );
    }

    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FileOutputStream fos = null;
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        try {
            fos = this.openFileOutput("Agenda.txt", Context.MODE_PRIVATE);
            System.out.println("ALOUUUUUAUDSUADUAUSDU");
        } catch (FileNotFoundException e) {
            System.out.println("CCCCAAATIAUSUDA");
            e.printStackTrace();
        }
        ObjectOutputStream os = null;
        try {
            System.out.println("SUCAUSUCAUSUCAUS");
            os = new ObjectOutputStream(fos);
        } catch (IOException e) {
            System.out.println("NAAAAAAAAAAOVALEU");
            e.printStackTrace();
        }


        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Compromisso w;
                w = (Compromisso) data.getParcelableExtra(NovoCompromisso.EXTRA_MESSAGE);
                mAdapter.insertItem(w);

                try {
                    System.out.println("DEUGRAAAAAAAAAAAAA");
                    os.writeObject(listaCompromisso);
                } catch (IOException e) {
                    System.out.println("ITIIITITITITITITI");
                    e.printStackTrace();
                }

            }
        }

        else if(requestCode == REQUEST_CODE_EDIT){
            Compromisso comp = data.getParcelableExtra(EXTRA_MESSAGE_OBJECT);
            int index = data.getIntExtra(EXTRA_MESSAGE_INDEX, -1);
            listaCompromisso.set(index, comp);
            mAdapter.notifyDataSetChanged();

            try {
                os.writeObject(listaCompromisso);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteItem(int index){}

    public void editItem(Compromisso comp, int indice) {

        Intent intent = new Intent(this, EditAgenda.class);
        intent.putExtra(EXTRA_MESSAGE_EDIT, true);
        intent.putExtra(EXTRA_MESSAGE_INDEX, indice);
        intent.putExtra(EXTRA_MESSAGE_OBJECT, (Parcelable) comp);
        startActivityForResult(intent, REQUEST_CODE_EDIT);

    }

    @Override
    public void onClick(View v) {

    }
}
