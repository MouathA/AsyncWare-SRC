package optfine;

public class ReflectorClass
{
    private Class targetClass;
    private String targetClassName;
    private boolean checked;
    
    public ReflectorClass(final String targetClassName) {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClassName = targetClassName;
        this.getTargetClass();
    }
    
    public Class getTargetClass() {
        if (this.checked) {
            return this.targetClass;
        }
        this.checked = true;
        return this.targetClass = Class.forName(this.targetClassName);
    }
    
    public String getTargetClassName() {
        return this.targetClassName;
    }
    
    public boolean exists() {
        return this.getTargetClass() != null;
    }
    
    public ReflectorClass(final Class targetClass) {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClass = targetClass;
        this.targetClassName = targetClass.getName();
        this.checked = true;
    }
}
