package lucas.malheiros.lostarkdaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import lucas.malheiros.lostarkdaily.modelo.ClassesDePersonagem;
import lucas.malheiros.lostarkdaily.modelo.Personagem;
import lucas.malheiros.lostarkdaily.persistencia.PersonagensDatabase;

public class CadastroClasseActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;
    public static final String NOMECLASSE = "NOMECLASSE";
    public static final String IDCLASSE = "IDCLASSE";
    private int modo;


    private ClassesDePersonagem classe;
    private List<ClassesDePersonagem> lista;
    private EditText editTextNomeClasse;
    private String nomeOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_classe);
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        editTextNomeClasse = findViewById(R.id.editTextNomeClasse);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(MODO, NOVO);
        }

        if (modo == NOVO) {
            setTitle(getString(R.string.novaClasse));
        } else {
            classe = personagensDatabase.classeDePersonagensDAO().queryForCharacterWithId(bundle.getInt(IDCLASSE));
            editTextNomeClasse.setText(classe.getNomeClasse());
            setTitle(getString(R.string.editClass));
        }
        editTextNomeClasse.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_classe, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSalvarClasse:
                Salvar(item);
                return true;

            case R.id.itemMenuLimparClasse:
                LimparDadosClasse(item);
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void novaClasse(AppCompatActivity activity) {
        Intent intent = new Intent(activity, CadastroClasseActivity.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent, NOVO);

    }

    public static void alterarClasse(AppCompatActivity activity, int posicaoSelecionada) {
        Intent intent = new Intent(activity, CadastroClasseActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(IDCLASSE, posicaoSelecionada);
        activity.startActivityForResult(intent, ALTERAR);

    }

    public void Salvar(MenuItem item) {

        String nomeClasse = editTextNomeClasse.getText().toString();


        if (nomeClasse == null || nomeClasse.trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_nome, Toast.LENGTH_LONG).show();
            editTextNomeClasse.requestFocus();
            return;
        }
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        if (modo == NOVO) {
        classe = new ClassesDePersonagem(nomeClasse);

            personagensDatabase.classeDePersonagensDAO().insert(classe);

        } else if (modo == ALTERAR) {
            classe.setNomeClasse(nomeClasse);
            personagensDatabase.classeDePersonagensDAO().update(classe);

            //personagensDatabase.personagemDAO().update(personagem);
        }

        setResult(Activity.RESULT_OK);
        finish();
    }

    public void cancelar(View view) {
        onBackPressed();
    }

    public void LimparDadosClasse(MenuItem item) {

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}