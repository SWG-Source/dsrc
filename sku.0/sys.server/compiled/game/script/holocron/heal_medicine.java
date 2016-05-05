package script.holocron;

import script.dictionary;
import script.menu_info_types;
import script.obj_id;

public class heal_medicine extends script.base_script
{
    public heal_medicine()
    {
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id target = getLookAtTarget(player);
            if (target == null)
            {
                LOG("newbie_handoff_heal", "Need target to use medicine.");
                sendSystemMessage(self, newbie_handoff.SID_NEWBIE_MISSION_HEAL_NEED_TARGET);
            }
            else if (target == getObjIdObjVar(player, "newbie_handoff.mission.heal.npc_id"))
            {
                destroyObject(self);
                String missionType = getStringObjVar(self, "newbie_handoff.mission.type");
                dictionary params = new dictionary();
                params.put("type", missionType);
                LOG("newbie_handoff_heal", "Target NPC has been healed.");
                messageTo(player, "missionHealComplete", params, 1, true);
            }
            else 
            {
                LOG("newbie_handoff_heal", "Incorrect heal target.");
                sendSystemMessage(self, newbie_handoff.SID_NEWBIE_MISSION_HEAL_WRONG_TARGET);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
