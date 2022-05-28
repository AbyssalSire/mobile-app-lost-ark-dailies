package lucas.malheiros.lostarkdaily.persistencia;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import lucas.malheiros.lostarkdaily.R;
import lucas.malheiros.lostarkdaily.modelo.ClassesDePersonagem;
import lucas.malheiros.lostarkdaily.modelo.Personagem;

@Database(entities = {Personagem.class, ClassesDePersonagem.class}, version = 1)
public abstract class PersonagensDatabase extends RoomDatabase {
    public abstract PersonagemDAO personagemDAO();

    public abstract ClasseDePersonagensDAO classeDePersonagensDAO();

    public static PersonagensDatabase instance;

    public static PersonagensDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (PersonagensDatabase.class) {
                if (instance == null) {
                    RoomDatabase.Builder builder = Room.databaseBuilder(context, PersonagensDatabase.class, "personagens.db");

                    builder.addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    carregaClassesIniciais(context);
                                }
                            });
                        }
                    });
                    instance = (PersonagensDatabase) builder.allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }

    public static void carregaClassesIniciais(final Context context) {
        String[] classes = context.getResources().getStringArray(R.array.classe_personagens);

        for (String classe : classes) {
            ClassesDePersonagem classesDePersonagem = new ClassesDePersonagem(classe);
            instance.classeDePersonagensDAO().insert(classesDePersonagem);
        }

    }
}

