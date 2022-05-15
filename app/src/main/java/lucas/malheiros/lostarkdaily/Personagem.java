package lucas.malheiros.lostarkdaily;

public class Personagem {

    private String nome;
    private Float ilvl;
    private boolean main;
    private String tier;
    private String classe;

    public Personagem(String nome, Float ilvl, boolean main, String tier, String classe){
        this.nome = nome;
        this.ilvl = ilvl;
        this.main = main;
        this.tier = tier;
        this.classe = classe;
    }

    public String getNome(){
        return nome;
    }

    public void setNome (String nome){
        this.nome = nome;
    }

    public Float getIlvl() {
        return ilvl;
    }

    public void setIlvl(Float ilvl) {
        this.ilvl = ilvl;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
