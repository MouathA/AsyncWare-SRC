package net.minecraft.client.renderer.tileentity;

import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class TileEntityItemStackRenderer
{
    private TileEntityBanner banner;
    private TileEntitySkull skull;
    private TileEntityEnderChest enderChest;
    private TileEntityChest field_147718_c;
    public static TileEntityItemStackRenderer instance;
    private TileEntityChest field_147717_b;
    
    public void renderByItem(final ItemStack itemValues) {
        if (itemValues.getItem() == Items.banner) {
            this.banner.setItemValues(itemValues);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.banner, 0.0, 0.0, 0.0, 0.0f);
        }
        else if (itemValues.getItem() == Items.skull) {
            GameProfile gameProfile = null;
            if (itemValues.hasTagCompound()) {
                final NBTTagCompound tagCompound = itemValues.getTagCompound();
                if (tagCompound.hasKey("SkullOwner", 10)) {
                    gameProfile = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag("SkullOwner"));
                }
                else if (tagCompound.hasKey("SkullOwner", 8) && tagCompound.getString("SkullOwner").length() > 0) {
                    gameProfile = TileEntitySkull.updateGameprofile(new GameProfile((UUID)null, tagCompound.getString("SkullOwner")));
                    tagCompound.removeTag("SkullOwner");
                    tagCompound.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), gameProfile));
                }
            }
            if (TileEntitySkullRenderer.instance != null) {
                GlStateManager.translate(-0.5f, 0.0f, -0.5f);
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                TileEntitySkullRenderer.instance.renderSkull(0.0f, 0.0f, 0.0f, EnumFacing.UP, 0.0f, itemValues.getMetadata(), gameProfile, -1);
            }
        }
        else {
            final Block blockFromItem = Block.getBlockFromItem(itemValues.getItem());
            if (blockFromItem == Blocks.ender_chest) {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.enderChest, 0.0, 0.0, 0.0, 0.0f);
            }
            else if (blockFromItem == Blocks.trapped_chest) {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147718_c, 0.0, 0.0, 0.0, 0.0f);
            }
            else {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147717_b, 0.0, 0.0, 0.0, 0.0f);
            }
        }
    }
    
    public TileEntityItemStackRenderer() {
        this.field_147717_b = new TileEntityChest(0);
        this.field_147718_c = new TileEntityChest(1);
        this.enderChest = new TileEntityEnderChest();
        this.banner = new TileEntityBanner();
        this.skull = new TileEntitySkull();
    }
    
    static {
        TileEntityItemStackRenderer.instance = new TileEntityItemStackRenderer();
    }
}
