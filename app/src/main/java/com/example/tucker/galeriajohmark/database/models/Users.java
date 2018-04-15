package com.example.tucker.galeriajohmark.database.models;

import com.example.tucker.galeriajohmark.database.GaleriaDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by John on 4/9/2018.
 */
@Table(database = GaleriaDB.class)
public class Users extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String username;

    @Column
    public String password;

    @Column
    public String nombre;

    @Column
    public String roll;
}
