package viamcp.platform;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.libs.gson.*;

public class VRInjector implements ViaInjector
{
    public void uninject() {
    }
    
    public String getEncoderName() {
        return "via-encoder";
    }
    
    public void inject() {
    }
    
    public String getDecoderName() {
        return "via-decoder";
    }
    
    public JsonObject getDump() {
        return new JsonObject();
    }
    
    public int getServerProtocolVersion() {
        return 47;
    }
}
