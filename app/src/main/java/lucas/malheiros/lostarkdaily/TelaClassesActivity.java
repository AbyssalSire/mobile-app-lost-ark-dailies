package lucas.malheiros.lostarkdaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lucas.malheiros.lostarkdaily.modelo.ClassesDePersonagem;
import lucas.malheiros.lostarkdaily.modelo.Personagem;
import lucas.malheiros.lostarkdaily.persistencia.PersonagensDatabase;

public class TelaClassesActivity extends AppCompatActivity {
    private List<ClassesDePersonagem> lista_classes = new ArrayList<>();

    private ListView listViewClasses;
    private int  posicaoSelecionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_classes);

        listViewClasses = findViewById(R.id.listViewClasses);

        popularLista();

        registerForContextMenu(listViewClasses);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_classes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_classe_selecionada, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId()) {
            case R.id.menuItemEditarClasse:
                posicaoSelecionada = info.position;
                //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                editarClasse(item, posicaoSelecionada);

                return true;
            case R.id.menuItemExcluirClasse:
                posicaoSelecionada = info.position;
                excluirClasse(posicaoSelecionada);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }



    private void popularLista() {
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        lista_classes = personagensDatabase.classeDePersonagensDAO().querryAllClasses();

        ArrayAdapter<ClassesDePersonagem> adapter = new ArrayAdapter<>(TelaClassesActivity.this, android.R.layout.simple_list_item_1, lista_classes);
        listViewClasses = findViewById(R.id.listViewClasses);
        listViewClasses.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

    }

    public void adicionarClasse(MenuItem menu) {
        CadastroClasseActivity.novaClasse(this);
    }

    private void editarClasse(MenuItem item, int posicaoSelecionada) {

        CadastroClasseActivity.alterarClasse(this, posicaoSelecionada);
    }

    private void excluirClasse(int posicaoSelecionada) {
        PersonagensDatabase personagensDatabase = PersonagensDatabase.getDatabase(this);
        personagensDatabase.classeDePersonagensDAO().delete(lista_classes.get(posicaoSelecionada));
        popularLista();
    }


    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {



            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                popularLista();
            }

    }

}