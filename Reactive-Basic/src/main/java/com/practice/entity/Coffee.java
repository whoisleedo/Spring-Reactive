package com.practice.entity;

public class Coffee {
    
    private String id;
    private String name;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Coffee(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public Coffee() {
        super();
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append("}");
        return builder.toString();
    }
    
    
    
    
    

}
