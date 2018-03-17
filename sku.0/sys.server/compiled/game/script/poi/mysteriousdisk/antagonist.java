package script.poi.mysteriousdisk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.combat;
import script.library.poi;
import script.library.scenario;
import script.ai.ai_combat;

public class antagonist extends script.poi.base.scenario_actor
{
    public antagonist()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiMysteriousDisk Antagonist";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "armWeapon", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int armWeapon(obj_id self, dictionary params) throws InterruptedException
    {
        aiEquipPrimaryWeapon(self);
        return SCRIPT_CONTINUE;
    }
    public int commentOnKill(obj_id self, dictionary params) throws InterruptedException
    {
        poi.quickSay(self, "a_lookingfordisk");
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "lookingForDisk"))
        {
            removeObjVar(self, "lookingForDisk");
            stop(self);
            messageTo(self, "searchDone", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int searchDone(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id med = poi.findObject(scenario.MEDIATOR + "_0");
        if ((med == null) || (med == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(med, "gaveDisk"))
        {
            poi.quickSay(self, "a_nodisk");
        }
        else 
        {
            poi.quickSay(self, "a_hasdisk");
        }
        return SCRIPT_CONTINUE;
    }
}
