package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import java.util.*;

public class GuiResourcePackAvailable extends GuiResourcePackList
{
    @Override
    protected String getListHeader() {
        return I18n.format("resourcePack.available.title", new Object[0]);
    }
    
    public GuiResourcePackAvailable(final Minecraft minecraft, final int n, final int n2, final List list) {
        super(minecraft, n, n2, list);
    }
}
