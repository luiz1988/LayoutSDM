package com.bingo.app.layoutsdm;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX ";
    private final String NOTIFICACAO_RADIOBUTTON_SELECIONADO = "NOTIFICACAO_RADIOBUTTON_SELECIONADO";

    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;
    private EditText emailEdittext;
    private EditText nomeEditText;
    private EditText telefoneEditText;
    private LinearLayout emailLinearLayout;

    private LinearLayout telefoneLinearLayout;
    private ArrayList<View> telefoneArrayList;
    private ArrayList<View> emailArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        notificacoesCheckBox = findViewById(R.id.notificaçoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        emailEdittext = findViewById(R.id.emailEditText);
       // telefoneEditText = findViewById(R.id.telefoneEditText);
        nomeEditText = findViewById(R.id.nomeEditText);
        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        emailLinearLayout = findViewById(R.id.emailLinearLayout);
        telefoneArrayList = new ArrayList<>();
        emailArrayList = new ArrayList<>();

        // Tratando evento de check no checkbox
        //notificacoesCheckBox.setOnClickListener(this);

        notificacoesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else{
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });

//        notificacoesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    notificacoesRadioGroup.setVisibility(View. VISIBLE);
//                }
//                else{
//                    notificacoesRadioGroup.setVisibility(View.GONE);
//                }
//            }
//        });

    }

    public void adicionarEmail(View view) {
        if (view.getId() == R.id.addEmailButton) {
            LayoutInflater inflater = getLayoutInflater();
            View novoEmailLayout = inflater.inflate(R.layout.novo_email_layout, null);
            emailArrayList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);
        }
    }

    public void adicionarTelefone(View view) {
        if (view.getId() == R.id.adicionarTelefoneButton) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout, null);
            telefoneArrayList.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("ESTADO_NOTIFICACAO_CHECKBOX",notificacoesCheckBox.isChecked());
        outState.putInt("NOTIFICACAO_RADIOBUTTON_SELECIONADO",notificacoesRadioGroup.getCheckedRadioButtonId());

        outState.putInt("emailTamanho", emailArrayList.size());
        for (int i = 0; i < emailArrayList.size(); i++) {
            List<View> listaFilhos = emailArrayList.get(i).getTouchables();
            for (int j = 0; j < listaFilhos.size(); j++) {
                if (listaFilhos.get(j) instanceof EditText) {
                    EditText et = (EditText) listaFilhos.get(j);
                    outState.putString("email" + j, et.getText().toString());
                }
            }
        }

        outState.putInt("foneTamanho", telefoneArrayList.size());
        for (int i = 0; i < telefoneArrayList.size(); i++) {
            List<View> listaFilhos = telefoneArrayList.get(i).getTouchables();
            for (int j = 0; j < listaFilhos.size(); j++) {
                if (listaFilhos.get(j) instanceof EditText) {
                    EditText et = (EditText) listaFilhos.get(j);
                    outState.putString("fone" + i, et.getText().toString());
                } else if (listaFilhos.get(j) instanceof Spinner) {
                    Spinner s = (Spinner) listaFilhos.get(j);
                    outState.putInt("tipoTelefone" + i, s.getSelectedItemPosition());
                }
            }
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        notificacoesCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX,false));
        if(notificacoesCheckBox.isChecked()){
            notificacoesRadioGroup.setVisibility(View.VISIBLE);
        }
        else{
            notificacoesRadioGroup.setVisibility(View.GONE);
        }

        int idRadioButtonSelecionado = savedInstanceState.getInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO,-1);
        if(idRadioButtonSelecionado != -1){

            notificacoesRadioGroup.check(idRadioButtonSelecionado);
        }
        Integer emailSize = savedInstanceState.getInt("emailTamanho", 0);
        for (int i = 0; i < emailSize; i++) {
            LayoutInflater inflater = getLayoutInflater();
            View novo = inflater.inflate(R.layout.novo_email_layout, null);
            List<View> listaFilhos = novo.getTouchables();
            for (int j = 0; j < listaFilhos.size(); j++) {
                if (listaFilhos.get(j) instanceof EditText) {
                    EditText et = (EditText) listaFilhos.get(j);
                    et.setText(savedInstanceState.getString("email" + j));
                }
            }
            emailArrayList.add(novo);
            emailLinearLayout.addView(novo);
        }

        Integer foneSize = savedInstanceState.getInt("foneTamanho", 0);
        for (int i = 0; i < foneSize; i++) {
            LayoutInflater inflater = getLayoutInflater();
            View novo = inflater.inflate(R.layout.novo_telefone_layout, null);
            List<View> listaFilhos = novo.getTouchables();
            for (int j = 0; j < listaFilhos.size(); j++) {
                if (listaFilhos.get(j) instanceof EditText) {
                    EditText et = (EditText) listaFilhos.get(j);
                    et.setText(savedInstanceState.getString("fone" + i));
                } else if (listaFilhos.get(j) instanceof Spinner) {
                    Spinner s = (Spinner) listaFilhos.get(j);
                    s.setSelection(savedInstanceState.getInt("tipoTelefone" + i, -1));
                }
            }
            telefoneArrayList.add(novo);
            telefoneLinearLayout.addView(novo);
        }




    }

    public void LimparFormulario(View view){
        nomeEditText.setText("");
        emailEdittext.setText("");
        //telefoneEditText.setText("");
        notificacoesCheckBox.setChecked(false);
        notificacoesRadioGroup.clearCheck();
        nomeEditText.requestFocus();
        emailLinearLayout.removeAllViews();
        telefoneLinearLayout.removeAllViews();
    }
}
