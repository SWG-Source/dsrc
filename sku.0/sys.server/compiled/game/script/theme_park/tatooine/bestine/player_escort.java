package script.theme_park.tatooine.bestine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.quests;
import script.library.create;

public class player_escort extends script.base_script
{
    public player_escort()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location questLoc = getLocationObjVar(self, "bestine.om.vip_loc");
        quests.addQuestLocationTarget(self, null, "quest", questLoc, 15);
        addLocationTarget("spawnGuy", questLoc, 100);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        int questNum = getIntObjVar(self, "bestine_progress.om_quest");
        String datatable = "datatables/theme_park/bestine_om.iff";
        String npcType = dataTableGetString(datatable, 0, questNum - 1);
        String npcName = dataTableGetString(datatable, 2, questNum - 1);
        if (name.equals("quest"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, "bestine.om.vip_loc");
            obj_id npc = create.object(npcType, spawn);
            setName(npc, npcName);
            String rewardScript = "theme_park.tatooine.bestine.om_vip";
            attachScript(npc, rewardScript);
            setObjVar(npc, "player", self);
            setObjVar(self, "bestine.om.vipObjId", npc);
            setObjVar(npc, "quest", questNum);
        }
        if (name.equals("questGiver"))
        {
            quests.clearQuestLocationTarget(self, null, name);
            obj_id vip = getObjIdObjVar(self, "bestine.om.vipObjId");
            dictionary parms = new dictionary();
            parms.put("player", self);
            messageTo(vip, "stopFollowing", parms, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishQuest(obj_id self, dictionary params) throws InterruptedException
    {
        location questGiver = getLocationObjVar(self, "bestine.om.omLoc");
        quests.addQuestLocationTarget(self, null, "questGiver", questGiver, 10);
        return SCRIPT_CONTINUE;
    }
}
