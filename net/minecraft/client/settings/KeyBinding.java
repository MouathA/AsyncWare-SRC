package net.minecraft.client.settings;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.util.*;
import com.google.common.collect.*;

public class KeyBinding implements Comparable
{
    private int keyCode;
    private static final List keybindArray;
    private final String keyCategory;
    public boolean pressed;
    private final int keyCodeDefault;
    private final String keyDescription;
    private static final IntHashMap hash;
    private int pressTime;
    private static final Set keybindSet;
    
    public String getKeyDescription() {
        return this.keyDescription;
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    public int compareTo(final KeyBinding keyBinding) {
        int n = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(keyBinding.keyCategory, new Object[0]));
        if (n == 0) {
            n = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(keyBinding.keyDescription, new Object[0]));
        }
        return n;
    }
    
    public String getKeyCategory() {
        return this.keyCategory;
    }
    
    public static void unPressAllKeys() {
        final Iterator<KeyBinding> iterator = KeyBinding.keybindArray.iterator();
        while (iterator.hasNext()) {
            iterator.next().unpressKey();
        }
    }
    
    public static void resetKeyBindingArrayAndHash() {
        KeyBinding.hash.clearMap();
        for (final KeyBinding keyBinding : KeyBinding.keybindArray) {
            KeyBinding.hash.addKey(keyBinding.keyCode, keyBinding);
        }
    }
    
    public boolean isKeyDown() {
        return this.pressed;
    }
    
    public static void onTick(final int n) {
        if (n != 0) {
            final KeyBinding keyBinding = (KeyBinding)KeyBinding.hash.lookup(n);
            if (keyBinding != null) {
                final KeyBinding keyBinding2 = keyBinding;
                ++keyBinding2.pressTime;
            }
        }
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((KeyBinding)o);
    }
    
    public static void setKeyBindState(final int n, final boolean pressed) {
        if (n != 0) {
            final KeyBinding keyBinding = (KeyBinding)KeyBinding.hash.lookup(n);
            if (keyBinding != null) {
                keyBinding.pressed = pressed;
            }
        }
    }
    
    public KeyBinding(final String keyDescription, final int n, final String keyCategory) {
        this.keyDescription = keyDescription;
        this.keyCode = n;
        this.keyCodeDefault = n;
        this.keyCategory = keyCategory;
        KeyBinding.keybindArray.add(this);
        KeyBinding.hash.addKey(n, this);
        KeyBinding.keybindSet.add(keyCategory);
    }
    
    public boolean isPressed() {
        if (this.pressTime == 0) {
            return false;
        }
        --this.pressTime;
        return true;
    }
    
    static {
        keybindArray = Lists.newArrayList();
        hash = new IntHashMap();
        keybindSet = Sets.newHashSet();
    }
    
    private void unpressKey() {
        this.pressTime = 0;
        this.pressed = false;
    }
    
    public static Set getKeybinds() {
        return KeyBinding.keybindSet;
    }
    
    public void setKeyCode(final int keyCode) {
        this.keyCode = keyCode;
    }
    
    public int getKeyCodeDefault() {
        return this.keyCodeDefault;
    }
}
