package ravada.vasu.com.vijay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vravada on 11/11/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1; // Database Version
    private static final String DATABASE_NAME = "searchManager";    // Database Name
    private static final String TABLE_SEARCH_QUERY_TABLE = "repo_search";// // Contacts table name

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String HTML_URL = "html_url";
    private static final String DESCRIPTION = "description";

    public DatabaseHandler(Context context  ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SEARCH_QUERY_TABLE + "("
                + ID + " INTEGER PRIMARY KEY   AUTOINCREMENT," + NAME + " TEXT,"+ HTML_URL + " TEXT,"+DESCRIPTION + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_QUERY_TABLE);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    protected void addRepositoryInfo(RepositoryInfo repositoryInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,repositoryInfo.getName());
        values.put(HTML_URL,repositoryInfo.getUrl());
        values.put(DESCRIPTION,repositoryInfo.getDescription());
        db.insert(TABLE_SEARCH_QUERY_TABLE,null,values);
        db.close();
    }

    protected RepositoryInfo getRepositoryInfo(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SEARCH_QUERY_TABLE,new String[]{NAME,HTML_URL,DESCRIPTION}, ID+" = ?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        RepositoryInfo repositoryInfo = new RepositoryInfo();
        repositoryInfo.setId(Integer.parseInt(cursor.getString(0)));
        repositoryInfo.setName(cursor.getString(1));
        repositoryInfo.setUrl(cursor.getString(2));
        repositoryInfo.setDescription(cursor.getString(3));
        return repositoryInfo;
    }

    protected List<RepositoryInfo> getAllRepositoriesInfo(){
       List<RepositoryInfo> repositoryInfoList = new ArrayList<RepositoryInfo>();
        String selectQuery = "SELECT * FROM "+ TABLE_SEARCH_QUERY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                RepositoryInfo repositoryInfo = new RepositoryInfo();
                repositoryInfo.setId(Integer.parseInt(cursor.getString(0)));
                repositoryInfo.setName(cursor.getString(1));
                repositoryInfo.setUrl(cursor.getString(2));
                repositoryInfo.setDescription(cursor.getString(3));
                repositoryInfoList.add(repositoryInfo);
            }while (cursor.moveToNext());
        }
        return repositoryInfoList;
    }

    protected int updateRepositroyInfo(RepositoryInfo repositoryInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,repositoryInfo.getName());
        values.put(DESCRIPTION,repositoryInfo.getDescription());
        return db.update(TABLE_SEARCH_QUERY_TABLE,values,ID+" = ?",
                new String[]{String.valueOf(repositoryInfo.getId())});

    }


    protected void deleteRepositoryInfo(RepositoryInfo repositoryInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SEARCH_QUERY_TABLE,ID+" = ?",
                new String[]{String.valueOf(repositoryInfo.getId())});
        db.close();
    }

    protected int getRepositoriesCount(){
        String countQuery = "SELECT * FROM "+ TABLE_SEARCH_QUERY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();
        return cursor.getCount();
    }

}
