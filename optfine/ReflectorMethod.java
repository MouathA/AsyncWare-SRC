package optfine;

import java.lang.reflect.*;

public class ReflectorMethod
{
    private Method targetMethod;
    private ReflectorClass reflectorClass;
    private String targetMethodName;
    private Class[] targetMethodParameterTypes;
    private boolean checked;
    
    public boolean exists() {
        return this.checked ? (this.targetMethod != null) : (this.getTargetMethod() != null);
    }
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String targetMethodName, final Class[] targetMethodParameterTypes) {
        this.reflectorClass = null;
        this.targetMethodName = null;
        this.targetMethodParameterTypes = null;
        this.checked = false;
        this.targetMethod = null;
        this.reflectorClass = reflectorClass;
        this.targetMethodName = targetMethodName;
        this.targetMethodParameterTypes = targetMethodParameterTypes;
        this.getTargetMethod();
    }
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String s) {
        this(reflectorClass, s, null);
    }
    
    public Method getTargetMethod() {
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = true;
        final Class targetClass = this.reflectorClass.getTargetClass();
        if (targetClass == null) {
            return null;
        }
        final Method[] declaredMethods = targetClass.getDeclaredMethods();
        while (0 < declaredMethods.length) {
            final Method targetMethod = declaredMethods[0];
            Label_0133: {
                if (targetMethod.getName().equals(this.targetMethodName)) {
                    if (this.targetMethodParameterTypes != null) {
                        if (!Reflector.matchesTypes(this.targetMethodParameterTypes, targetMethod.getParameterTypes())) {
                            break Label_0133;
                        }
                    }
                    this.targetMethod = targetMethod;
                    if (!this.targetMethod.isAccessible()) {
                        this.targetMethod.setAccessible(true);
                    }
                    return this.targetMethod;
                }
            }
            int n = 0;
            ++n;
        }
        Config.log("(Reflector) Method not present: " + targetClass.getName() + "." + this.targetMethodName);
        return null;
    }
    
    public Class getReturnType() {
        final Method targetMethod = this.getTargetMethod();
        return (targetMethod == null) ? null : targetMethod.getReturnType();
    }
    
    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }
}
