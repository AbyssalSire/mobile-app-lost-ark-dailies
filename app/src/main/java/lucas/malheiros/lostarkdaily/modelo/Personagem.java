package lucas.malheiros.lostarkdaily.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "personagem", foreignKeys = @ForeignKey(entity = ClassesDePersonagem.class, parentColumns = "id", childColumns = "classe"))
public class Personagem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String nome;
    @NonNull
    private Float ilvl;
    @NonNull
    private boolean main;
    @NonNull
    private String tier;

    @NonNull @ColumnInfo(index = true)
    private int classe;

    public Personagem(String nome, Float ilvl, boolean main, String tier, int classe){
        this.nome = nome;
        this.ilvl = ilvl;
        this.main = main;
        this.tier = tier;
        this.classe = classe;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNome(){
        return nome;
    }

    public void setNome (String nome){
        this.nome = nome;
    }

    @NonNull
    public Float getIlvl() {
        return ilvl;
    }

    public void setIlvl(Float ilvl) {
        this.ilvl = ilvl;
    }

    @NonNull
    public boolean getMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    @NonNull
    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    @NonNull
    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }
}
