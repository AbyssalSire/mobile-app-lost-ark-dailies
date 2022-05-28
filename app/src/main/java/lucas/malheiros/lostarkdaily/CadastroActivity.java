package lucas.malheiros.lostarkdaily;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import lucas.malheiros.lostarkdaily.modelo.ClassesDePersonagem;
import lucas.malheiros.lostarkdaily.modelo.Personagem;
import lucas.malheiros.lostarkdaily.persistencia.PersonagensDatabase;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNomePersonagem;
    private EditText editTextIlvlPersonagem;
    private CheckBox checkBoxMain;
    private RadioGroup radioGroupTier;
    private Spinner spinnerClasse;

    public static final String MODO = "MODO";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;
    public static final String NOME = "NOME";
    public static final String ILVL = "ILVL";
    public static final String EHMAIN = "EHMAIN";
    public static final String TIER = "TIER";
    public static final String CLASSE = "CLASSE";
    public static final String ID = "ID";
    private int modo;

    private Personagem personagem;
    private List<ClassesDePersonagem> lista;


    public static void novoPersonagem(AppCompatActivity activity) {
        Intent intent = new Intent(activity, CadastroActivity.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent, 1);
    }

    public static void alterarPersonagem(AppCompatActivity activity, Personagem personagem) {
        Intent intent = new Intent(activity, CadastroActivity.class);
        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(NOME, personagem.getNome());
        intent.putExtra(ILVL, personagem.getIlvl());
        intent.putExtra(EHMAIN, personagem.getMain());

        intent.putExtra(TIER, personagem.getTier());
        intent.putExtra(CLASSE, personagem.getClasse());
        activity.startActivityForResult(intent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> lista = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        editTextNomePersonagem = findViewById(R.id.editTextNomePersonagem);
        editTextIlvlPersonagem = findViewById(R.id.editTextIlvlPersonagem);
        checkBoxMain = findViewById(R.id.checkBoxMain);
        radioGroupTier = findViewById(R.id.radioGroupTier);
        spinnerClasse = findViewById(R.id.spinnerClasse);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        popularSpinner();

        if (bundle != null) {

            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle("Cadastrar Personagem");
                personagem = new Personagem("", -1f, false, "", 1);
            } else if (modo == ALTERAR) {
                setTitle("Alterar Personagem");


                //PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);

                personagem = personagensDatabase.personagemDAO().queryForNome(bundle.getString(NOME));

                editTextNomePersonagem.setText(bundle.getString(NOME));
                editTextIlvlPersonagem.setText(Float.toString(bundle.getFloat(ILVL)));

                checkBoxMain.setChecked(bundle.getBoolean(EHMAIN));

                switch (bundle.getString(TIER)) {
                    case "Tier 1":
                        radioGroupTier.check(R.id.radioButtonT1);
                        break;

                    case "Tier 2":
                        radioGroupTier.check(R.id.radioButtonT2);
                        break;

                    case "Tier 3":
                        radioGroupTier.check(R.id.radioButtonT3);
                        break;
                    default:
                        radioGroupTier.check(R.id.radioButtonT1);
                        break;
                }
                spinnerClasse.setSelection(bundle.getInt(CLASSE));
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSalvar:
                cadastrarPersonagem(item);
                return true;

            case R.id.itemMenuLimpar:
                limparDados(item);
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void limparDados(MenuItem item) {
        editTextNomePersonagem.setText(null);
        editTextIlvlPersonagem.setText(null);
        checkBoxMain.setChecked(false);
        radioGroupTier.clearCheck();
        Toast.makeText(this, R.string.msg_limpar, Toast.LENGTH_SHORT).show();

        editTextNomePersonagem.requestFocus();
    }

    public void cadastrarPersonagem(MenuItem item) {
        String nomePersonagem = editTextNomePersonagem.getText().toString();
        String mensagemMain = "";
        String tier = "";
        int classe;

        if (nomePersonagem == null || nomePersonagem.trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_nome, Toast.LENGTH_LONG).show();
            editTextNomePersonagem.requestFocus();
            return;
        }
        if (editTextIlvlPersonagem.getText().toString() == null || editTextIlvlPersonagem.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_ilvl, Toast.LENGTH_LONG).show();
            editTextIlvlPersonagem.requestFocus();
            return;
        }
        Float ilvlPersonagem = Float.parseFloat((editTextIlvlPersonagem.getText().toString()));


        switch (radioGroupTier.getCheckedRadioButtonId()) {
            case R.id.radioButtonT1:
                tier += getString(R.string.tier_1);
                break;
            case R.id.radioButtonT2:
                tier += getString(R.string.tier_2);
                break;
            case R.id.radioButtonT3:
                tier += getString(R.string.tier_3);
                break;
            default:
                Toast.makeText(this, R.string.erro_tier, Toast.LENGTH_LONG).show();
                return;
        }


        personagem.setNome(nomePersonagem);
        personagem.setIlvl(ilvlPersonagem);

        personagem.setMain(checkBoxMain.isChecked());
        personagem.setTier(tier);
        personagem.setClasse(spinnerClasse.getSelectedItemPosition() + 1);

        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        if (modo == NOVO) {

            personagensDatabase.personagemDAO().insert(personagem);

        } else if (modo == ALTERAR) {

            personagensDatabase.personagemDAO().update(personagem);
        }

        setResult(Activity.RESULT_OK);
        finish();
    }


    public void alterarPersonagem(MenuItem item, Personagem personagemAlterado) {
        String nomePersonagem = personagemAlterado.getNome();
        String ilvlPersonagem = String.valueOf(personagemAlterado.getIlvl());
        String tier = personagemAlterado.getTier();
        int classe = personagemAlterado.getClasse();

        personagem.setNome(nomePersonagem);
        personagem.setIlvl(Float.valueOf(ilvlPersonagem));
        personagem.setMain(checkBoxMain.isChecked());
        personagem.setTier(tier);
        personagem.setClasse(classe);

        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        personagensDatabase.personagemDAO().update(personagem);

        setResult(Activity.RESULT_OK);

        finish();


    }


    private void popularSpinner() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(CadastroActivity.this);

                lista = personagensDatabase.classeDePersonagensDAO().querryAllClasses();

                CadastroActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<ClassesDePersonagem> adapter = new ArrayAdapter<>(CadastroActivity.this, android.R.layout.simple_list_item_1, lista);

                        spinnerClasse.setAdapter(adapter);

                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}