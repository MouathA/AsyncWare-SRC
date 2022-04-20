package net.minecraft.util;

public class TupleIntJsonSerializable
{
    private int integerValue;
    private IJsonSerializable jsonSerializableValue;
    
    public void setIntegerValue(final int integerValue) {
        this.integerValue = integerValue;
    }
    
    public IJsonSerializable getJsonSerializableValue() {
        return this.jsonSerializableValue;
    }
    
    public void setJsonSerializableValue(final IJsonSerializable jsonSerializableValue) {
        this.jsonSerializableValue = jsonSerializableValue;
    }
    
    public int getIntegerValue() {
        return this.integerValue;
    }
}
