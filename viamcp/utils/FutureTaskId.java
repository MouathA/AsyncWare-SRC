package viamcp.utils;

import com.viaversion.viaversion.api.platform.*;
import java.util.concurrent.*;

public class FutureTaskId implements PlatformTask
{
    private final Future object;
    
    public Future getObject() {
        return this.object;
    }
    
    public FutureTaskId(final Future object) {
        this.object = object;
    }
    
    public void cancel() {
        this.object.cancel(false);
    }
    
    public Object getObject() {
        return this.getObject();
    }
}
