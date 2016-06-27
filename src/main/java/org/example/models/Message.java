/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.models;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonAutoDetect;
import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
/**
 *
 * @author JDevys
 */
public class Message {
    @JsonProperty
    public int message_id;
    
    @JsonProperty
    public User from;
    
    @JsonProperty
    public int date;
    
    @JsonProperty
    public Chat chat;
    
    @JsonProperty
    public String text;
}
