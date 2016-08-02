/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.openercuss.jtelegrambot.ChatFacade;
import org.openercuss.jtelegrambot.telegram.Update;

/**
 *
 * @author JDevys
 */
public class MessageProcessor {
    private static MessageProcessor instance = null;
    private static final Logger logger = Logger.getLogger(MessageProcessor.class.getName());
    private ChatFacade facade = ChatFacade.GetInstance();
    private Map<String, String> commads = new HashMap<String, String>();
    
    private MessageProcessor() {
        commads.put("/new", "What would be the name of the poll?");
    };
    
    public static MessageProcessor getInstance() {
        if (instance == null) {
            instance = new MessageProcessor();
        }
        
        return instance;
    }
    
    public void process(Update update) throws IOException {
        String command = facade.getCommand(update);
        if (command != null) {
            String commandReply = commads.get(command);
            String commandResponse = facade.sendReply(update, commandReply, true);
            logger.severe(commandResponse);
        } else {
            String response = facade.sendReply(update, "Sorry, There is no command in progress");
            logger.severe(response);
        }
    }
}
