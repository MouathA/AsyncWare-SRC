package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings;

import com.nquantum.module.*;
import java.util.*;

public class Setting
{
    private boolean onlyint;
    private String mode;
    private Module parent;
    private String name;
    private boolean bval;
    private double min;
    private String sval;
    private double dval;
    private ArrayList options;
    private double max;
    
    public Setting(final String name, final Module parent, final String sval, final ArrayList options) {
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = "Combo";
    }
    
    public void setValBoolean(final boolean bval) {
        this.bval = bval;
    }
    
    public Module getParentMod() {
        return this.parent;
    }
    
    public boolean getValBoolean() {
        return this.bval;
    }
    
    public double getValDouble() {
        if (this.onlyint) {
            this.dval = (int)this.dval;
        }
        return this.dval;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public void setValDouble(final double dval) {
        this.dval = dval;
    }
    
    public boolean onlyInt() {
        return this.onlyint;
    }
    
    public String getValString() {
        return this.sval;
    }
    
    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }
    
    public double getMax() {
        return this.max;
    }
    
    public Setting(final String name, final Module parent, final boolean bval) {
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = "Check";
    }
    
    public String getName() {
        return this.name;
    }
    
    public Setting(final String name, final Module parent, final double dval, final double min, final double max, final boolean onlyint) {
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";
    }
    
    public void setValString(final String sval) {
        this.sval = sval;
    }
    
    public boolean isCombo() {
        return this.mode.equalsIgnoreCase("Combo");
    }
    
    public ArrayList getOptions() {
        return this.options;
    }
    
    public boolean isCheck() {
        return this.mode.equalsIgnoreCase("Check");
    }
}
