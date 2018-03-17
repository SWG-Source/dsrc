package script.holocron;

import script.dictionary;
import script.library.ai_lib;
import script.library.factions;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;

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
            dictionary params = new dictionary();
            params.put("type", getStringObjVar(self, "newbie_handoff.mission.type"));
            LOG("newbie_handoff_heal", "Target NPC has been healed.");
            messageTo(player, "missionHealComplete", params, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
}
