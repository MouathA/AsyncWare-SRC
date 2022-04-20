package net.minecraft.client.resources;

import org.apache.logging.log4j.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;

public class ResourcePackListEntryDefault extends ResourcePackListEntry
{
    private final IResourcePack field_148320_d;
    private static final Logger logger;
    private final ResourceLocation resourcePackIcon;
    
    @Override
    protected int func_183019_a() {
        return 1;
    }
    
    @Override
    protected boolean func_148310_d() {
        return false;
    }
    
    protected boolean func_148307_h() {
        return false;
    }
    
    @Override
    protected void func_148313_c() {
        this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    @Override
    protected String func_148311_a() {
        final PackMetadataSection packMetadataSection = (PackMetadataSection)this.field_148320_d.getPackMetadata(this.mc.getResourcePackRepository().rprMetadataSerializer, "pack");
        if (packMetadataSection != null) {
            return packMetadataSection.getPackDescription().getFormattedText();
        }
        return EnumChatFormatting.RED + "Missing pack.mcmeta :(";
    }
    
    @Override
    protected boolean func_148308_f() {
        return false;
    }
    
    @Override
    protected String func_148312_b() {
        return "Default";
    }
    
    protected boolean func_148314_g() {
        return false;
    }
    
    protected boolean func_148309_e() {
        return false;
    }
    
    public ResourcePackListEntryDefault(final GuiScreenResourcePacks guiScreenResourcePacks) {
        super(guiScreenResourcePacks);
        this.field_148320_d = this.mc.getResourcePackRepository().rprDefaultResourcePack;
        this.resourcePackIcon = this.mc.getTextureManager().getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.field_148320_d.getPackImage()));
    }
}
