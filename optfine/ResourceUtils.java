package optfine;

import net.minecraft.client.resources.*;
import java.io.*;

public class ResourceUtils
{
    private static boolean directAccessValid;
    private static ReflectorClass ForgeAbstractResourcePack;
    private static ReflectorField ForgeAbstractResourcePack_resourcePackFile;
    
    static {
        ResourceUtils.ForgeAbstractResourcePack = new ReflectorClass(AbstractResourcePack.class);
        ResourceUtils.ForgeAbstractResourcePack_resourcePackFile = new ReflectorField(ResourceUtils.ForgeAbstractResourcePack, "resourcePackFile");
        ResourceUtils.directAccessValid = true;
    }
    
    public static File getResourcePackFile(final AbstractResourcePack abstractResourcePack) {
        if (ResourceUtils.directAccessValid) {
            return abstractResourcePack.resourcePackFile;
        }
        return (File)Reflector.getFieldValue(abstractResourcePack, ResourceUtils.ForgeAbstractResourcePack_resourcePackFile);
    }
}
