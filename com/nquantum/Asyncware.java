package com.nquantum;

import com.nquantum.command.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.*;
import com.nquantum.util.font.*;
import com.nquantum.clickgui.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.util.*;
import com.nquantum.module.*;
import com.nquantum.event.*;
import com.nquantum.event.impl.*;
import java.util.function.*;
import java.io.*;
import java.awt.*;
import com.nquantum.notification.*;
import org.lwjgl.opengl.*;

public class Asyncware
{
    public static TTFFontRenderer moon;
    public static TTFFontRenderer roboto;
    public CommandManager commandManager;
    public static TTFFontRenderer sfreg;
    public static TTFFontRenderer sfbold;
    public String name;
    public EventManager eventManager;
    public ClickGui clickGui;
    public static TTFFontRenderer sftitle;
    public String creator;
    public static TTFFontRenderer s;
    public String version;
    public static TTFFontRenderer nig;
    public static GlyphPageFontRenderer sf;
    public static Asyncware instance;
    public ClickguiScreen clickGui2;
    public SettingsManager settingsManager;
    public ModuleManager moduleManager;
    public NotificationManager notificationManager;
    public static TTFFontRenderer sfboldsmall;
    public ConfigSystem configSystem;
    public static TTFFontRenderer astofolo;
    public static TTFFontRenderer comfort;
    
    private static void lambda$onKey$1(final Module module) {
        module.toggle();
    }
    
    public Asyncware() {
        this.name = "Asyncware";
        this.version = "2";
        this.creator = "nquantum";
    }
    
    public void stopClient() {
        this.eventManager.unregister(this);
    }
    
    @Punjabi
    public void onChat(final EventChat eventChat) {
        this.commandManager.handleChat(eventChat);
    }
    
    private static boolean lambda$onKey$0(final EventKey eventKey, final Module module) {
        return module.getKey() == eventKey.getKey();
    }
    
    @Punjabi
    public void onKey(final EventKey eventKey) {
        this.moduleManager.getModules().stream().filter(Asyncware::lambda$onKey$0).forEach(Asyncware::lambda$onKey$1);
    }
    
    static {
        Asyncware.instance = new Asyncware();
    }
    
    public void startClient() {
        final String string = "C:\\Users\\" + "\u1694\u1692\u1684\u1693\u16cf\u168f\u1680\u168c\u1684" + "\\AppData\\Roaming\\.minecraft\\versions\\Asyncware\\assets\\";
        Asyncware.sf = GlyphPageFontRenderer.create(string + "jellolight.ttf", 22, false, false, false);
        Asyncware.sftitle = new TTFFontRenderer(Font.createFont(0, new File(string + "jellolight.ttf")).deriveFont(0, 20.0f));
        Asyncware.sfreg = new TTFFontRenderer(Font.createFont(0, new File(string + "jelloregular.ttf")).deriveFont(0, 20.0f));
        Asyncware.sfbold = new TTFFontRenderer(Font.createFont(0, new File(string + "jellomedium.ttf")).deriveFont(0, 20.0f));
        Asyncware.sfboldsmall = new TTFFontRenderer(Font.createFont(0, new File(string + "jellomedium.ttf")).deriveFont(0, 13.5f));
        Asyncware.astofolo = new TTFFontRenderer(Font.createFont(0, new File(string + "regularpr.ttf")).deriveFont(0, 18.2f));
        Asyncware.roboto = new TTFFontRenderer(Font.createFont(0, new File(string + "roboto.ttf")).deriveFont(0, 20.0f));
        Asyncware.moon = new TTFFontRenderer(Font.createFont(0, new File(string + "moon.ttf")).deriveFont(0, 19.0f));
        Asyncware.comfort = new TTFFontRenderer(Font.createFont(0, new File(string + "comfortaa.ttf")).deriveFont(0, 17.0f));
        Asyncware.s = new TTFFontRenderer(Font.createFont(0, new File(string + "regularpr.ttf")).deriveFont(0, 15.2f));
        Asyncware.nig = new TTFFontRenderer(Font.createFont(0, new File(string + "sfthin.ttf")).deriveFont(0, 60.0f));
        this.settingsManager = new SettingsManager();
        this.eventManager = new EventManager();
        this.moduleManager = new ModuleManager();
        this.notificationManager = new NotificationManager();
        this.commandManager = new CommandManager();
        this.clickGui = new ClickGui();
        this.clickGui2 = new ClickguiScreen();
        if (new File("Asyncware/configs/").exists()) {
            ConfigSystem.load("default");
            NotificationManager.show(new Notification(NotificationType.INFO, "Config", "Config loaded successfully!", 3));
        }
        else {
            NotificationManager.show(new Notification(NotificationType.ERROR, "Config", "Failed to load config!", 3));
        }
        System.out.println("[" + this.name + "] Starting client, b" + this.version + ", created by " + this.creator);
        Display.setTitle(this.name + " b" + this.version);
        this.eventManager.register(this);
    }
}
