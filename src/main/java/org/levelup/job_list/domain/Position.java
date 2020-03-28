package org.levelup.job_list.domain;

public class Position {

    private int id;
    private String name;

    public Position(int id, String name){

        this.id =id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
