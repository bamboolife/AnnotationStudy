package com.sundy.simple.annotation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-11 19:08
 * 描述：SqliteHelper 用于创建、更新数据库操作。
 */
public class SqliteHelper extends SQLiteOpenHelper {
    private String createTable;
    private String tableName;

    public SqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String createTable, String tableName) {
        super(context, name, factory, version);
        this.createTable = createTable;
        this.tableName = tableName;
    }

    /**
     * 创建表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
     if (createTable!=""){
         db.execSQL(createTable);
     }
    }
    /**
     * 更新表
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + tableName );
        onCreate(db);
    }
    /**
     * 更新列
     * @param db
     * @param oldColumn
     * @param newColumn
     * @param typeColumn
     */
    public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
        try{
            db.execSQL( "ALTER TABLE " +
                    tableName + " CHANGE " +
                    oldColumn + " "+ newColumn +
                    " " + typeColumn
            );
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
