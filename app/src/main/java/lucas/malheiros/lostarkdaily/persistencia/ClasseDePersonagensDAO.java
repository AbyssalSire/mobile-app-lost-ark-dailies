package lucas.malheiros.lostarkdaily.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lucas.malheiros.lostarkdaily.modelo.ClassesDePersonagem;
import lucas.malheiros.lostarkdaily.modelo.Personagem;

@Dao
public interface ClasseDePersonagensDAO {

    @Insert
    long insert (ClassesDePersonagem classesDePersonagem);

    @Delete
    void delete(ClassesDePersonagem classesDePersonagem);

    @Update
    void update(ClassesDePersonagem classesDePersonagem);

    @Query("Select nomeClasse from classesDePersonagem WHERE id= :id ")
    String queryForId(int id);

    @Query("Select id from classesDePersonagem WHERE nomeClasse= :nomeClasse ")
    Integer queryForNome(String nomeClasse);

    @Query("SELECT * FROM classesDePersonagem WHERE id = :id")
    ClassesDePersonagem queryForCharacterWithId(long id);

    @Query("Select * from classesDePersonagem ORDER BY id ASC")
    List<ClassesDePersonagem> querryAllClasses();

    @Query("SELECT nomeClasse from classesDePersonagem")
    List<String> queryForAllClasses();

    @Query("Select * from classesDePersonagem WHERE nomeClasse= :nomeClasse")
    List<ClassesDePersonagem> queryForExisteClasse(String nomeClasse);
}
