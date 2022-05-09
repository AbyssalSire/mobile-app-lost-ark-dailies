package lucas.malheiros.lostarkdaily;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        intent.putExtra(EHMAIN, personagem.isMain());

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

        popularSpinner(lista);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            int modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle("Cadastrar Personagem");
            } else if (modo == ALTERAR) {
                setTitle("Alterar Personagem");
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

                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).toString().equals(bundle.getString(CLASSE))) {
                        spinnerClasse.setSelection(i);
                    }
                }
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
        String classe;

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

        Intent intent = new Intent();

        intent.putExtra(NOME, nomePersonagem);
        intent.putExtra(ILVL, ilvlPersonagem);
        intent.putExtra(EHMAIN, checkBoxMain.isChecked());
        intent.putExtra(TIER, tier);
        intent.putExtra(CLASSE, (String) spinnerClasse.getSelectedItem());

        setResult(Activity.RESULT_OK, intent);

        finish();

    }

    public void alterarPersonagem(MenuItem item, Personagem personagem) {
        String nomePersonagem = personagem.getNome();
        String ilvlPersonagem = String.valueOf(personagem.getIlvl());
        String tier = personagem.getTier();
        String classe = personagem.getClasse();

        Intent intent = new Intent();

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(NOME, nomePersonagem);
        intent.putExtra(ILVL, ilvlPersonagem);
        intent.putExtra(EHMAIN, personagem.isMain());
        intent.putExtra(TIER, tier);
        intent.putExtra(CLASSE, classe);

        setResult(Activity.RESULT_OK, intent);

        finish();


    }


    private void popularSpinner(ArrayList<String> lista) {

        lista.add(getString(R.string.berserker));
        lista.add(getString(R.string.paladin));
        lista.add(getString(R.string.gunlancer));
        lista.add(getString(R.string.glaivier));
        lista.add(getString(R.string.striker));
        lista.add(getString(R.string.wardancer));
        lista.add(getString(R.string.scrapper));
        lista.add(getString(R.string.soulfist));
        lista.add(getString(R.string.gunslinger));
        lista.add(getString(R.string.artillerist));
        lista.add(getString(R.string.deadeye));
        lista.add(getString(R.string.sharpshooter));
        lista.add(getString(R.string.bard));
        lista.add(getString(R.string.sorceress));
        lista.add(getString(R.string.shadowhunter));
        lista.add(getString(R.string.deathblade));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        spinnerClasse.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}