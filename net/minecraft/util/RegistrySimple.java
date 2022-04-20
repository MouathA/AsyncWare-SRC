package net.minecraft.util;

import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class RegistrySimple implements IRegistry
{
    private static final Logger logger;
    protected final Map registryObjects;
    
    @Override
    public void putObject(final Object o, final Object o2) {
        Validate.notNull(o);
        Validate.notNull(o2);
        if (this.registryObjects.containsKey(o)) {
            RegistrySimple.logger.debug("Adding duplicate key '" + o + "' to registry");
        }
        this.registryObjects.put(o, o2);
    }
    
    @Override
    public Object getObject(final Object o) {
        return this.registryObjects.get(o);
    }
    
    protected Map createUnderlyingMap() {
        return Maps.newHashMap();
    }
    
    public boolean containsKey(final Object o) {
        return this.registryObjects.containsKey(o);
    }
    
    public RegistrySimple() {
        this.registryObjects = this.createUnderlyingMap();
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public Set getKeys() {
        return Collections.unmodifiableSet(this.registryObjects.keySet());
    }
    
    @Override
    public Iterator iterator() {
        return this.registryObjects.values().iterator();
    }
}
