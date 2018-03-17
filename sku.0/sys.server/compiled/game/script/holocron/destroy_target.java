package script.holocron;

import script.dictionary;
import script.library.ai_lib;
import script.obj_id;

public class destroy_target extends script.base_script
{
    public destroy_target()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "newbie_handoff.mission.player");
        String missionType = getStringObjVar(self, "newbie_handoff.mission.type");
        dictionary params = new dictionary();
        params.put("type", missionType);
        if (missionType.equals("brawler") || missionType.equals("marksman"))
        {
            LOG("newbie_handoff_destroy", "Target creature has been killed.");
            messageTo(player, "missionDestroyComplete", params, 0, true);
        }
        else if (missionType.equals("scout"))
        {
            LOG("newbie_handoff_harvest", "Target creature has been killed.");
            messageTo(player, "missionHarvestTargetDead", params, 3, true);
        }
        return SCRIPT_CONTINUE;
    }
}
