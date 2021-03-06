package com.sundy.simple.annotation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-11 19:25
 * 描述：AbstractDB 是一个抽象类，用于数据库的操作
 */
public abstract class AbstractDB {
    public static final String TAG = "AbstractDB";

    private SQLiteDatabase db;
    private SqliteHelper dbHelper;
    // 版本
    private int version;
    // 数据库名称
    private String dbName;
    // 表名
    private String tableName;
    // 建表语句
    private StringBuilder createBuilder;

    // 插入的数据
    private ContentValues contentValues = new ContentValues();

    // 是否存在数据
    private boolean isColums;

    private ArrayList<String> clumsList = new ArrayList<String>();


    /**
     * 打开数据库
     *
     * @param _context
     * @param _clazz
     * @param _object
     * @return
     */
    public AbstractDB open(Context _context, Class<?> _clazz, Object _object) {
        if (paramsDB(_clazz, _object)) {
            dbHelper = new SqliteHelper(_context, dbName, null, version, createBuilder.toString(), tableName);
            db = dbHelper.getWritableDatabase();
            Log.e(TAG, "-------------数据库打开成功！----------");

        } else {
            Log.e(TAG, "-------------数据库打开失败！----------");
        }
        return this;
    }
    /**
     * 打开数据库时是否进行插入操作
     *
     * @param isInsert
     * @return
     */
    public AbstractDB isInsert(boolean isInsert) {
        if (isInsert && isColums) {
            insert();
        }
        return this;
    }
    /**
     * 创建并检查数据库
     *
     * @param _object
     * @param _clazz
     * @return false:失败 true:成功
     */
    public boolean paramsDB(Class<?> _clazz, Object _object) {
        isColums = false;
        contentValues.clear();
        clumsList.clear();
        createBuilder = new StringBuilder();
        Class<?> clazz = _clazz;
        Field[] fields = clazz.getDeclaredFields();
        createBuilder.append("CREATE TABLE IF NOT EXISTS ");
        /*
         * 表名
         */
        TableDB tDb = _clazz.getAnnotation(TableDB.class);
        if (tDb != null) {
            dbName = tDb.dbName();
            tableName = tDb.table();
            version = tDb.version();
            createBuilder.append(tableName);
            createBuilder.append("(");
        } else {
            return false;
        }
        /*
         * 列
         */
        for (Field field : fields) {
            ColumDB requestParamsKey = field.getAnnotation(ColumDB.class);
            if (requestParamsKey != null) {
                String key = requestParamsKey.column();
                String value;
                try {
                    value = (String) field.get(_object);

                    clumsList.add(key);
                    if (!value.isEmpty()) {
                        contentValues.put(key, value);
                    }
                    createBuilder.append(key);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                createBuilder.append(" varchar,");
                isColums = true;
            }
        }
        if (!isColums) {
            return false;
        }
        createBuilder.deleteCharAt(createBuilder.length() - 1);
        createBuilder.append(")");
        return true;
    }

    public AbstractDB insert() {
        if (contentValues != null) {
            db.insert(tableName, null, contentValues);
            Log.e(TAG, "-------------数据添加成功！----------");
        } else {
            Log.e(TAG, "-------------数据添加失败！----------");
        }
        return this;
    }

    /**
     * 获取数据
     *
     * @return
     */
    public String getDate() {
        StringBuilder dateBuilder = new StringBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        while (cursor.moveToNext()) {
            if (clumsList != null) {
                for (int i = 0, length = clumsList.size(); i < length; i++) {
                    String name = cursor.getString(cursor.getColumnIndex(clumsList.get(i)));
                    dateBuilder.append(clumsList.get(i));
                    dateBuilder.append("=");
                    dateBuilder.append(name);
                    dateBuilder.append(",");
                }
                dateBuilder.append("\n");
            }
        }
        cursor.close();
        if (dateBuilder.length() > 1) {
            dateBuilder.deleteCharAt(dateBuilder.length() - 1);
            return dateBuilder.toString();
        } else {
            Log.e(TAG, "-------------无数据解析！----------");
            return "";
        }
    }

    public void Close() {
        db.close();
        dbHelper.close();
    }


}
