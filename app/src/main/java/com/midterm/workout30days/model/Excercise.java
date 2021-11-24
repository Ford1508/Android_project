package com.midterm.workout30days.model;

public class Excercise {
    public int id;
    public String content;
    public String gif;
    public String describe;
    public int timer;
    public int rep;

    public Excercise(int id, String content, String gif, String describe, int timer, int rep) {
        this.id = id;
        this.content = content;
        this.gif = gif;
        this.describe = describe;
        this.timer = timer;
        this.rep = rep;
    }

    public Excercise(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
