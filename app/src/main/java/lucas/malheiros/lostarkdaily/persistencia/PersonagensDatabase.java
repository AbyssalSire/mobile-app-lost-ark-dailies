package lucas.malheiros.lostarkdaily.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import lucas.malheiros.lostarkdaily.modelo.Personagem;

@Database(entities = {Personagem.class}, version = 1, exportSchema = false)
public abstract class PersonagensDatabase extends RoomDatabase {
    public abstract PersonagemDAO personagemDAO();

    public static PersonagensDatabase instance;

    public static PersonagensDatabase getDatabase(final Context context){
        if(instance==null){
            synchronized (PersonagensDatabase.class){
                if(instance==null){
                    instance = Room.databaseBuilder(context, PersonagensDatabase.class, "personagens.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
