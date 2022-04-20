package net.minecraft.client.main;

import java.io.*;
import net.minecraft.client.*;
import java.net.*;
import com.mojang.authlib.properties.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import joptsimple.*;
import java.util.*;
import com.google.gson.*;

public class Main
{
    public static void main(final String[] array) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        final OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("demo");
        optionParser.accepts("fullscreen");
        optionParser.accepts("checkGlErrors");
        final ArgumentAcceptingOptionSpec withRequiredArg = optionParser.accepts("server").withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo = optionParser.accepts("port").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)25565, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec defaultsTo2 = optionParser.accepts("gameDir").withRequiredArg().ofType((Class)File.class).defaultsTo((Object)new File("."), (Object[])new File[0]);
        final ArgumentAcceptingOptionSpec ofType = optionParser.accepts("assetsDir").withRequiredArg().ofType((Class)File.class);
        final ArgumentAcceptingOptionSpec ofType2 = optionParser.accepts("resourcePackDir").withRequiredArg().ofType((Class)File.class);
        final ArgumentAcceptingOptionSpec withRequiredArg2 = optionParser.accepts("proxyHost").withRequiredArg();
        final ArgumentAcceptingOptionSpec ofType3 = optionParser.accepts("proxyPort").withRequiredArg().defaultsTo((Object)"8080", (Object[])new String[0]).ofType((Class)Integer.class);
        final ArgumentAcceptingOptionSpec withRequiredArg3 = optionParser.accepts("proxyUser").withRequiredArg();
        final ArgumentAcceptingOptionSpec withRequiredArg4 = optionParser.accepts("proxyPass").withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo3 = optionParser.accepts("username").withRequiredArg().defaultsTo((Object)("Player" + Minecraft.getSystemTime() % 1000L), (Object[])new String[0]);
        final ArgumentAcceptingOptionSpec withRequiredArg5 = optionParser.accepts("uuid").withRequiredArg();
        final ArgumentAcceptingOptionSpec required = optionParser.accepts("accessToken").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec required2 = optionParser.accepts("version").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec defaultsTo4 = optionParser.accepts("width").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)854, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec defaultsTo5 = optionParser.accepts("height").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)480, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec defaultsTo6 = optionParser.accepts("userProperties").withRequiredArg().defaultsTo((Object)"{}", (Object[])new String[0]);
        final ArgumentAcceptingOptionSpec defaultsTo7 = optionParser.accepts("profileProperties").withRequiredArg().defaultsTo((Object)"{}", (Object[])new String[0]);
        final ArgumentAcceptingOptionSpec withRequiredArg6 = optionParser.accepts("assetIndex").withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo8 = optionParser.accepts("userType").withRequiredArg().defaultsTo((Object)"legacy", (Object[])new String[0]);
        final NonOptionArgumentSpec nonOptions = optionParser.nonOptions();
        final OptionSet parse = optionParser.parse(array);
        final List values = parse.valuesOf((OptionSpec)nonOptions);
        if (!values.isEmpty()) {
            System.out.println("Completely ignored arguments: " + values);
        }
        final String s = (String)parse.valueOf((OptionSpec)withRequiredArg2);
        Proxy no_PROXY = Proxy.NO_PROXY;
        if (s != null) {
            no_PROXY = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(s, (int)parse.valueOf((OptionSpec)ofType3)));
        }
        final String s2 = (String)parse.valueOf((OptionSpec)withRequiredArg3);
        final String s3 = (String)parse.valueOf((OptionSpec)withRequiredArg4);
        if (!no_PROXY.equals(Proxy.NO_PROXY) && s2 != null && s3 != null) {
            Authenticator.setDefault(new Authenticator(s2, s3) {
                final String val$s1;
                final String val$s2;
                
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(this.val$s1, this.val$s2.toCharArray());
                }
            });
        }
        final int intValue = (int)parse.valueOf((OptionSpec)defaultsTo4);
        final int intValue2 = (int)parse.valueOf((OptionSpec)defaultsTo5);
        final boolean has = parse.has("fullscreen");
        final boolean has2 = parse.has("checkGlErrors");
        final boolean has3 = parse.has("demo");
        final String s4 = (String)parse.valueOf((OptionSpec)required2);
        final Gson create = new GsonBuilder().registerTypeAdapter((Type)PropertyMap.class, (Object)new PropertyMap.Serializer()).create();
        final PropertyMap propertyMap = (PropertyMap)create.fromJson((String)parse.valueOf((OptionSpec)defaultsTo6), (Class)PropertyMap.class);
        final PropertyMap propertyMap2 = (PropertyMap)create.fromJson((String)parse.valueOf((OptionSpec)defaultsTo7), (Class)PropertyMap.class);
        final File file = (File)parse.valueOf((OptionSpec)defaultsTo2);
        final GameConfiguration gameConfiguration = new GameConfiguration(new GameConfiguration.UserInformation(new Session((String)((OptionSpec)defaultsTo3).value(parse), parse.has((OptionSpec)withRequiredArg5) ? ((String)((OptionSpec)withRequiredArg5).value(parse)) : ((String)((OptionSpec)defaultsTo3).value(parse)), (String)((OptionSpec)required).value(parse), (String)((OptionSpec)defaultsTo8).value(parse)), propertyMap, propertyMap2, no_PROXY), new GameConfiguration.DisplayInformation(intValue, intValue2, has, has2), new GameConfiguration.FolderInformation(file, parse.has((OptionSpec)ofType2) ? ((File)parse.valueOf((OptionSpec)ofType2)) : new File(file, "resourcepacks/"), parse.has((OptionSpec)ofType) ? ((File)parse.valueOf((OptionSpec)ofType)) : new File(file, "assets/"), parse.has((OptionSpec)withRequiredArg6) ? ((String)((OptionSpec)withRequiredArg6).value(parse)) : null), new GameConfiguration.GameInformation(has3, s4), new GameConfiguration.ServerInformation((String)parse.valueOf((OptionSpec)withRequiredArg), (int)parse.valueOf((OptionSpec)defaultsTo)));
        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
            @Override
            public void run() {
            }
        });
        Thread.currentThread().setName("Client thread");
        new Minecraft(gameConfiguration).run();
    }
}
