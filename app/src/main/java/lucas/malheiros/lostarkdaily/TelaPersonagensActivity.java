package lucas.malheiros.lostarkdaily;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lucas.malheiros.lostarkdaily.modelo.Personagem;
import lucas.malheiros.lostarkdaily.persistencia.PersonagensDatabase;

public class TelaPersonagensActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Personagem> lista_personagens = new ArrayList<>();

    private int posicaoSelecionada = -1;
    private View viewSelecionada;
    private ActionMode actionMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_personagens);

        recyclerView = (RecyclerView) findViewById(R.id.lista);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        popularLista();

        Ordena();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int posicao) {
                        posicaoSelecionada = posicao;
                        editarPersonagem((MenuItem) viewSelecionada, posicao);
                    }

                    @Override
                    public void onLongItemClick(View view, int posicao) {

                        posicaoSelecionada = posicao;


                    }
                })
        );

    }


    private void popularLista() {
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        lista_personagens = personagensDatabase.personagemDAO().queryAllName();
        mAdapter = new PersonagemAdapter(getApplicationContext(), lista_personagens);
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_personagens, menu);
        return true;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.menuItemExcluir:
                excluirPersonagem(posicaoSelecionada);
                break;
            case R.id.menuItemEditar:
                editarPersonagem(item, posicaoSelecionada);
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

        return super.onContextItemSelected(item);
    }

    public void sobre(MenuItem item) {
        Intent intent = new Intent(this, SobreActivity.class);

        startActivity(intent);

    }

    public void OrdernaAlfabetica(MenuItem item){
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        lista_personagens = personagensDatabase.personagemDAO().queryAllName();
        SalvaPrefAlfabetica();
    }

    public void OrdenaNivel(MenuItem item){
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        lista_personagens = personagensDatabase.personagemDAO().queryAllItemLevel();
        SalvaPrefAlfabetica();
    }
    public void Ordena(){
        if(lista_personagens.size()<=0){return;}
        SharedPreferences prefs = getSharedPreferences("chave_ordem", MODE_PRIVATE);
        if(prefs.getInt("ordem", 0)==0){
            OrdernaAlfabetica();
        } else {
            OrdenaNivel();
        }
        popularLista();
    }

    public void SalvaPrefAlfabetica(){
        SharedPreferences prefs = getSharedPreferences("chave_ordem", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("ordem", 0);
        editor.commit();
    }

    public void SalvaPrefNivel(){
        SharedPreferences prefs = getSharedPreferences("chave_ordem", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("ordem", 1);
        editor.commit();
    }

    private void OrdernaAlfabetica() {
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        lista_personagens = personagensDatabase.personagemDAO().queryAllName();
        popularLista();
        SalvaPrefAlfabetica();
    }
    private void OrdenaNivel() {
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        lista_personagens = personagensDatabase.personagemDAO().queryAllName();
        popularLista();
        SalvaPrefAlfabetica();
    }

    public void editarPersonagem(MenuItem item, int indice) {
        CadastroActivity.alterarPersonagem(this, lista_personagens.get(indice));
    }

    public void adicionarPersonagem(MenuItem item) {
        CadastroActivity.novoPersonagem(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            popularLista();
        }
    }



    public void excluirPersonagem(int indice) {
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        personagensDatabase.personagemDAO().delete(lista_personagens.get(indice));
        popularLista();
    }




}