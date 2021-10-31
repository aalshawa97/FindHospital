package com.example.findhospital.activity

import java.util.*


/**
 * Created by abdul on 2/25/2017.
 */
class ChatMessage {
    //Declare variables
    private var messageText = " "
    private var messageUser = " "
    private var messageTime: Long = 0

    //Class constructor
    constructor(messageText: String, messageUser: String) {
        this.messageText = messageText
        this.messageUser = messageUser
        messageTime = Date().time
    }

    constructor() {}

    //Getters and setters
    fun getMessageText(): String {
        return messageText
    }

    fun getMessageUser(): String {
        return messageUser
    }

    fun getMessageTime(): Long {
        return messageTime
    }

    fun setMessageText(messageText: String) {
        this.messageText = messageText
    }

    fun setMessageUser(messageUser: String) {
        this.messageUser = messageUser
    }

    fun setMessageTime(messageTime: Long) {
        this.messageTime = messageTime
    }
}
