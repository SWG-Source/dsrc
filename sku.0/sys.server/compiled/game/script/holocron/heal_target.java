package script.holocron;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.holocron.newbie_handoff;
import script.library.factions;
import script.library.ai_lib;

public class heal_target extends script.base_script
{
    public heal_target()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        factions.setFaction(self, "Unattackable");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(player, "newbie_handoff.mission.heal.type"))
        {
            if (getObjIdObjVar(player, "newbie_handoff.mission.heal.npc_id") == self)
            {
                mi.addRootMenu(menu_info_types.SERVER_HEAL_STATE, newbie_handoff.SID_NEWBIE_MISSION_HEAL_MENU);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_HEAL_STATE)
        {
            destroyObject(getObjIdObjVar(player, "newbie_handoff.mission.heal.med_id"));
            String missionType = getStringObjVar(self, "newbie_handoff.mission.type");
            dictionary params = new dictionary();
            params.put("type", missionType);
            LOG("newbie_handoff_heal", "Target NPC has been healed.");
            messageTo(player, "missionHealComplete", params, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
}
