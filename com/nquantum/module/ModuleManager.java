package com.nquantum.module;

import java.util.function.*;
import java.util.*;
import com.nquantum.module.movement.*;
import com.nquantum.module.combat.*;
import com.nquantum.module.exploit.*;
import com.nquantum.module.render.*;
import com.nquantum.module.player.*;
import com.nquantum.module.misc.*;
import com.nquantum.module.customize.*;

public class ModuleManager
{
    private ArrayList modules;
    
    public Module getModuleByName(final String s) {
        return (Module)this.modules.stream().filter(ModuleManager::lambda$getModuleByName$0).findFirst().orElse(null);
    }
    
    private static boolean lambda$getModuleByName$0(final String s, final Module module) {
        return module.getName().equalsIgnoreCase(s);
    }
    
    public List getModules(final Category category) {
        final ArrayList<Module> list = new ArrayList<Module>();
        while (0 < this.modules.size()) {
            final Module module = this.modules.get(0);
            if (module.getCategory().equals(category)) {
                list.add(module);
            }
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public ModuleManager() {
        (this.modules = new ArrayList()).add(new AutoGap());
        this.modules.add(new AntiBot());
        this.modules.add(new BowAimbot());
        this.modules.add(new InfAura());
        this.modules.add(new KillAura());
        this.modules.add(new FollowAura());
        this.modules.add(new AutoArmor());
        this.modules.add(new Criticals());
        this.modules.add(new Regen());
        this.modules.add(new TPAura());
        this.modules.add(new NoSlowdown());
        this.modules.add(new AntiVoid());
        this.modules.add(new Sprint());
        this.modules.add(new Fly());
        this.modules.add(new Step());
        this.modules.add(new LongJump());
        this.modules.add(new Jesus());
        this.modules.add(new Speed());
        this.modules.add(new Phase());
        this.modules.add(new SlimeJump());
        this.modules.add(new SwingAnimation());
        this.modules.add(new WorldColor());
        this.modules.add(new Wallhack());
        this.modules.add(new Tracers());
        this.modules.add(new BlockOverlay());
        this.modules.add(new WorldTime());
        this.modules.add(new Fullbright());
        this.modules.add(new ClickGUI());
        this.modules.add(new ESP());
        this.modules.add(new ESP2D());
        this.modules.add(new ItemPhysics());
        this.modules.add(new NoHurtCam());
        this.modules.add(new SpeedGraph());
        this.modules.add(new NameTags());
        this.modules.add(new Zoom());
        this.modules.add(new Xray());
        this.modules.add(new Trajectories());
        this.modules.add(new TargetStrafe());
        this.modules.add(new EntityDesync());
        this.modules.add(new AutoTool());
        this.modules.add(new NoFall());
        this.modules.add(new AntiObsidian());
        this.modules.add(new InvWalk());
        this.modules.add(new InventoryManager());
        this.modules.add(new BedBreaker());
        this.modules.add(new AutoSoup());
        this.modules.add(new ChestStealer());
        this.modules.add(new Scaffold());
        this.modules.add(new AntiKB());
        this.modules.add(new AutoPot());
        this.modules.add(new FreeCam());
        this.modules.add(new FriendProtect());
        this.modules.add(new AntiDesync());
        this.modules.add(new PacketFly8000());
        this.modules.add(new SendCustomPacket());
        this.modules.add(new Disabler());
        this.modules.add(new ThunderDetector());
        this.modules.add(new AbortBreaking());
        this.modules.add(new BoatFly());
        this.modules.add(new CraftingCarry());
        this.modules.add(new Crasher());
        this.modules.add(new GodMode());
        this.modules.add(new PackSpoofer());
        this.modules.add(new ForceOp());
        this.modules.add(new PlayerList());
        this.modules.add(new SpinBot());
        this.modules.add(new Resolver());
        this.modules.add(new AntiTabComplete());
        this.modules.add(new Panic());
        this.modules.add(new NoMouseInteract());
        this.modules.add(new AntiVanish());
        this.modules.add(new NoSignRender());
        this.modules.add(new PacketFixer());
        this.modules.add(new MultiAction());
        this.modules.add(new HUD());
    }
    
    public ArrayList getModules() {
        return this.modules;
    }
}
