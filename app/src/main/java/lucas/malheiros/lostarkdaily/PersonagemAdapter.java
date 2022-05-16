package lucas.malheiros.lostarkdaily;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lucas.malheiros.lostarkdaily.modelo.Personagem;

public class PersonagemAdapter extends RecyclerView.Adapter<PersonagemAdapter.MyViewHolder> {

    List<Personagem> personagems;
    RecyclerView.ViewHolder viewHolder;
    Context context;
    private int posicao;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView textViewNome, textViewIlvl, textViewTier, textViewClasse;
        CheckBox checkBoxEhMain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewIlvl = itemView.findViewById(R.id.textViewIlvl);
            textViewTier = itemView.findViewById(R.id.textViewTier);
            textViewClasse = itemView.findViewById(R.id.textViewClasse);
            checkBoxEhMain = itemView.findViewById(R.id.checkBoxEhMain);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.menuItemEditar, Menu.NONE, R.string.editar);
            menu.add(Menu.NONE, R.id.menuItemExcluir, Menu.NONE, R.string.excluir);
        }
    }

    public PersonagemAdapter(Context contextRecebido, List<Personagem> personagens){
        context = contextRecebido;
        this.personagems = personagens;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View personagemLista = LayoutInflater.from(context).inflate(R.layout.linha_lista_personagens, viewGroup, false);
        viewHolder = new MyViewHolder(personagemLista);
        return (MyViewHolder) viewHolder;
    }

    @Override
    public int getItemCount() {
        return personagems.size();
    }

    public int getPosition(){
        return posicao;
    }

    public void setPosition(int posicao){
        this.posicao = posicao;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Personagem personagem = personagems.get(i);
        myViewHolder.textViewNome.setText(personagem.getNome());
        myViewHolder.textViewClasse.setText(personagem.getClasse());
        myViewHolder.textViewIlvl.setText(String.valueOf(personagem.getIlvl()));
        myViewHolder.textViewTier.setText(personagem.getTier());
        myViewHolder.checkBoxEhMain.setChecked(personagem.getMain());


    }


    public interface OnPersonagemListener {
        void onPersonagemClick(int indice);

        void onPersonagemLongClick(int indice);

        void openContextMenu(TelaPersonagensActivity telaPersonagensActivity, int menu_personagem_selecionado);
    }

}
