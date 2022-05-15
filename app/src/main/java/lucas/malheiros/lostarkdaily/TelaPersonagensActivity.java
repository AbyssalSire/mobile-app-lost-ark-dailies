package lucas.malheiros.lostarkdaily;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public static Comparator<Personagem> PersonagemAlfabetico = new Comparator<Personagem>() {
        @Override
        public int compare(Personagem o1, Personagem o2) {
            return o1.getNome().compareTo(o2.getNome());
        }
    };

    public static Comparator<Personagem> PersonagemLevelPersonagem = new Comparator<Personagem>() {
        @Override
        public int compare(Personagem o1, Personagem o2) {
            return (int) ((o1.getIlvl() * 100) - (o2.getIlvl() * 100));
        }
    };

    public void OrdernaAlfabetica(MenuItem item){
        Collections.sort(lista_personagens, PersonagemAlfabetico);
        mAdapter.notifyDataSetChanged();
        SalvaPrefAlfabetica();
    }

    public void OrdenaNivel(MenuItem item){
        Collections.sort(lista_personagens, PersonagemLevelPersonagem);
        mAdapter.notifyDataSetChanged();
        SalvaPrefNivel();
    }
    public void Ordena(){
        if(lista_personagens.size()<=0){return;}
        SharedPreferences prefs = getSharedPreferences("chave_ordem", MODE_PRIVATE);
        if(prefs.getInt("ordem", 0)==0){
            OrdernaAlfabetica();
        } else {
            OrdenaNivel();
        }
        mAdapter.notifyDataSetChanged();
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
        Collections.sort(lista_personagens, PersonagemAlfabetico);
        mAdapter.notifyDataSetChanged();
        SalvaPrefAlfabetica();
    }
    private void OrdenaNivel() {
        Collections.sort(lista_personagens, PersonagemLevelPersonagem);
        mAdapter.notifyDataSetChanged();
        SalvaPrefNivel();
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
            Bundle bundle = data.getExtras();
            String nome = bundle.getString(CadastroActivity.NOME);
            float ilvl = bundle.getFloat(CadastroActivity.ILVL);
            boolean ehmain = bundle.getBoolean(CadastroActivity.EHMAIN);
            String tier = bundle.getString(CadastroActivity.TIER);
            String classe = bundle.getString(CadastroActivity.CLASSE);


            if (requestCode == 2) {
                Personagem personagem = lista_personagens.get(posicaoSelecionada);
                personagem.setNome(nome);
                personagem.setIlvl(ilvl);
                personagem.setMain(ehmain);
                personagem.setTier(tier);
                personagem.setClasse(classe);
                posicaoSelecionada = -1;

            } else {
                lista_personagens.add(new Personagem(nome, ilvl, ehmain, tier, classe));
            }
            Ordena();
            mAdapter.notifyDataSetChanged();
        }
    }



    public void excluirPersonagem(int indice) {
        lista_personagens.remove(indice);
        mAdapter.notifyDataSetChanged();
    }




}