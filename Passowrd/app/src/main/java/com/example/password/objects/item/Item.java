package com.example.password.objects.item;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.password.objects.category.Category;

@Entity(tableName = "item", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "category_id", onDelete = ForeignKey.CASCADE))
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "item_name")
    private String name;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ColumnInfo(name = "username")
    private String userName;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "info")
    private String info;

    @ColumnInfo(name = "weblink")
    private String webLink;

    public Item(int categoryId, String name, String userName, String password, String info, String webLink) {
        this.categoryId = categoryId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.info = info;
        this.webLink = webLink;
    }

    public Item(String name, String userName, String password, String info, String webLink) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.info = info;
        this.webLink = webLink;
    }

    public Item(int id, int categoryId, String name, String userName, String password, String info, String webLink) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.info = info;
        this.webLink = webLink;
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", info='" + info + '\'' +
                ", webLink='" + webLink + '\'' +
                '}';
    }
}
