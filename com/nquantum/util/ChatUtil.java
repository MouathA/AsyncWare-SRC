package com.nquantum.util;

import com.nquantum.util.player.*;

public class ChatUtil
{
    public static String prefix;
    
    public static void sendMessagePrefix(final String s) {
        PlayerUtil.sendMessage(ChatUtil.prefix + s);
    }
    
    public static void sendMessage(final String s) {
        PlayerUtil.sendMessage(s);
    }
    
    static {
        ChatUtil.prefix = "§7[§9Asyncware§7]§f ";
    }
}
