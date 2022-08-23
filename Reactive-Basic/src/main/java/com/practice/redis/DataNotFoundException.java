package com.practice.redis;

public class DataNotFoundException extends RuntimeException  {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String id) {
        super("Post:" + id + " is not found.");
    }

}
