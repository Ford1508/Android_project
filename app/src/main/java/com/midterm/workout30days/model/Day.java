package com.midterm.workout30days.model;

import java.util.HashMap;
import java.util.Map;

public class Day {
    public int id;
    public String content;
    public String exercise;
    public boolean complete;

    public Day(int id, String content, String exercise, boolean complete) {
        this.id = id;
        this.content = content;
        this.exercise = exercise;
        this.complete = complete;
    }

    public Day(){}

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getExercises() {
        return exercise;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("complete", complete);
        return result;
    }

}

