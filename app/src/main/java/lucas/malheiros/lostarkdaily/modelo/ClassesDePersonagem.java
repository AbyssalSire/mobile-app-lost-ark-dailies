package lucas.malheiros.lostarkdaily.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "classesDePersonagem", indices = @Index(value = {"nomeClasse"}, unique = true))
public class ClassesDePersonagem {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nomeClasse;

    public ClassesDePersonagem(String nomeClasse){
        setNomeClasse(nomeClasse);
    }

    @NonNull
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @NonNull
    public String getNomeClasse(){
        return nomeClasse;
    }

    public void setNomeClasse(@NonNull String nomeClasse){
        this.nomeClasse = nomeClasse;
    }

    @Override
    public String toString(){
        return getNomeClasse();
    }

}
