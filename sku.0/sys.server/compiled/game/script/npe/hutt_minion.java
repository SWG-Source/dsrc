package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class hutt_minion extends script.base_script
{
    public hutt_minion()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterMoving(obj_id self) throws InterruptedException
    {
        stop(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id parent = utils.getObjIdScriptVar(self, "objParent");
        messageTo(parent, "huttMinionDied", null, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "npe.invuln"))
        {
            int currentHealth = getHealth(self);
            int maxHealth = getMaxHealth(self);
            setHealth(self, maxHealth);
        }
        return SCRIPT_CONTINUE;
    }
}
