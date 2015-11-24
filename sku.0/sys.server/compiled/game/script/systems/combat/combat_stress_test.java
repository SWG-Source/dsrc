package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.weapons;

public class combat_stress_test extends script.base_script
{
    public combat_stress_test()
    {
    }
    public static final int START_COMBAT_DELAY = 300;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int intRoll = rand(1, 2);
        if (intRoll == 1)
        {
            pvpSetAlignedFaction(self, (370444368));
        }
        if (intRoll == 2)
        {
            pvpSetAlignedFaction(self, (-615855020));
        }
        obj_id weapon = weapons.createWeapon("object/weapon/ranged/pistol/pistol_dl44.iff", self, 0.85f);
        pvpSetAlignedFaction(self, (-615855020));
        pvpMakeDeclared(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(self, "start_combat", dctParams, START_COMBAT_DELAY, true);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("startCombat"))
        {
            obj_id[] objTargets = getPlayerCreaturesInRange(getLocation(self), 200f);
            int intI = 0;
            while (intI < objTargets.length)
            {
                if (pvpCanAttack(self, objTargets[intI]))
                {
                    queueCommand(self, (-390609650), objTargets[intI], "", COMMAND_PRIORITY_DEFAULT);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (strText.equals("stopCombat"))
        {
            queueCommand(self, (1098448234), null, "", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
}
