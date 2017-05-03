package co.edu.udea.moviemapps.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.model.Function;
import co.edu.udea.moviemapps.persistence.config.DataManager;


public class FuncionDataManager extends DataManager {

    public static final String TABLE_NAME = "funciones_table";

    public static final int COL_ID = 0,
            COL_HORA = 1,
            COL_LUGAR = 2,
            COL_SALA = 3,
            COL_PRECIO = 4;

    public static final String[] COLUMNS = {
            "id",
            "hora",
            "lugar",
            "sala",
            "precio"
    };

    public FuncionDataManager(Context context) {
        super(context);
    }

    public static FuncionDataManager getInstance() {
        return new FuncionDataManager(MovieMapps.getContext());
    }

    private synchronized  Function getFuncionFromCursor(Cursor cursor){
        Function funcion = new Function();
        funcion.setId(cursor.getInt(COL_ID));
        funcion.setHora(cursor.getString(COL_HORA));
        funcion.setLugar(cursor.getString(COL_LUGAR));
        funcion.setSala(cursor.getString(COL_SALA));
        funcion.setPrecio(cursor.getString(COL_PRECIO));
        return funcion;
    }

    public ArrayList<Function> getAllFunciones() {
        ArrayList<Function> funciones = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMNS[COL_ID]);

        Function funcion;
        while (cursor.moveToNext()) {
            funcion = getFuncionFromCursor(cursor);
            funciones.add(funcion);
        }

        cursor.close();
        helper.close();
        return funciones;
    }

}