package com.allenai.expandablelistview.entiy;

import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEvent;

public class Chapter {
    private int    id;
    private String name;

    public static final String TABLE_NAME = "tb_chapter";
    public static final String COL_ID     = "_id";
    public static final String COL_NAME   = "name";

    private List<ChapterItem> children = new ArrayList<>();

    public Chapter() {
    }

    public Chapter(int id, String name) {
        this.name = name;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterItem> getChildren() {
        return children;
    }

    public void setChildren(List<ChapterItem> children) {
        this.children = children;
    }

    //add children by chateritem
    public void addChild(ChapterItem chapterItem) {

        chapterItem.setPid(getId());
        children.add(chapterItem);
    }

    //add children by id&child name
    public void addChild(int id, String childName) {

        ChapterItem chapterItem = new ChapterItem(id, childName);
        chapterItem.setPid(getId());
        children.add(chapterItem);

    }
}
