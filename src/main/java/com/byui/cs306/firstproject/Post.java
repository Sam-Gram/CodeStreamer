/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.byui.cs306.firstproject;




/**
 *
 * @author Sam
 */
public class Post {
    private String author;
    private String comment;

    public Post(String pAuthor, String pComment)
    {
        author   = pAuthor;
        comment  = pComment;
    }

    
    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    
    @Override
    public String toString()
    {
        return author + " " + 
               comment;
    }
    
}
