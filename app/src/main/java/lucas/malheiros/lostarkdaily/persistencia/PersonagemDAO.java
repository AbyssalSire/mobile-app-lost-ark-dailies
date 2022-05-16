package lucas.malheiros.lostarkdaily.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lucas.malheiros.lostarkdaily.modelo.Personagem;

@Dao
public interface PersonagemDAO {

    @Insert
    long insert(Personagem personagem);

    @Delete
    void delete(Personagem personagem);

    @Update
    void update(Personagem personagem);

    @Query("SELECT * FROM personagem WHERE nome = :nome")
    Personagem queryForNome(String nome);

    @Query("SELECT * FROM personagem WHERE id = :id")
    Personagem queryForId(long id);



    @Query("SELECT * FROM personagem ORDER BY nome ASC")
    List<Personagem> queryAllName();

    @Query("SELECT * FROM personagem ORDER BY ilvl ASC")
    List<Personagem> queryAllItemLevel();

}
