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
public class Chat {
    @JsonProperty
    public int id;
    
    @JsonProperty
    public String type;
    
    @JsonProperty
    public String title;
    
    @JsonProperty
    public String username;
    
    @JsonProperty
    public String first_name;
    
    @JsonProperty
    public String last_name;
}
