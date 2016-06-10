package co.edu.udea.moviemapps.persistence.config;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.util.Scanner;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "movie-mapps.db";

    public static final String FILE_EXTENSION = ".sql";

    public static final int DB_ACTUAL_VERSION = 1;

    public static final int DB_INITIAL_VERSION = 1;

    private Context context;

    public DataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, DB_INITIAL_VERSION - 1, DB_ACTUAL_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion < oldVersion) {
            return;
        }
        for (int v = oldVersion + 1; v <= newVersion; v++) {
            applyPatch(db, v);
        }
    }

    private void applyPatch(SQLiteDatabase db, int v) {
        String filename = DB_NAME + "." + v + FILE_EXTENSION;
        runSqlFile(db, filename);
    }

    private void runSqlFile(SQLiteDatabase db, String filename) {
        try {
            String sqlfileContent = readFileAsset(context, filename);
            String[] sentences = parseSQLSentences(sqlfileContent);
            for (String sql : sentences) {
                db.execSQL(sql);
            }
        } catch (IOException | SQLException e) {
            Log.e("ERROR", "SQL execution failed at runSqlFile: ", e);
        }
    }

    private String[] parseSQLSentences(String sqlfileContent) {
        String otherThanQuote = " [^'] ";
        String quotedString = String.format(" ' %s* ' ", otherThanQuote);
        String regex = String.format("(?x) " + // enable comments, ignore white spaces
                        ";                         " + // match a semi colon
                        "(?=                       " + // start positive look ahead
                        "  (                       " + //   start group 1
                        "    %s*                   " + //     match 'otherThanQuote' zero or more times
                        "    %s                    " + //     match 'quotedString'
                        "  )*                      " + //   end group 1 and repeat it zero or more times
                        "  %s*                     " + //   match 'otherThanQuote'
                        "  $                       " + // match the end of the string
                        ")                         ", // stop positive look ahead
                otherThanQuote, quotedString, otherThanQuote);

        return sqlfileContent.split(regex);
    }

    private String readFileAsset(Context ctx, String filename) throws IOException {
        StringBuilder builder = new StringBuilder();
        Scanner s = new Scanner(ctx.getAssets().open(filename));
        while (s.hasNextLine()) {
            builder.append(s.nextLine());
        }
        return builder.toString();
    }

}
