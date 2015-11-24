package script.theme_park.tatooine.mos_taike;

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

public class player_deliver extends script.base_script
{
    public player_deliver()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location here = getLocationObjVar(self, "mos_taike.cantina.receiverLoc");
        quests.addQuestLocationTarget(self, null, "quest", here, 15);
        addLocationTarget("spawnGuy", here, 100);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        int questNum = getIntObjVar(self, "mos_taike.cantina_quest");
        String datatable = "datatables/theme_park/mos_taike_cantina.iff";
        String npc = dataTableGetString(datatable, 1, questNum - 1);
        String npcName = dataTableGetString(datatable, 3, questNum - 1);
        if (name.equals("quest"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, "mos_taike.cantina.receiverLoc");
            obj_id receiver = create.object(npc, spawn);
            setName(receiver, npcName);
            attachScript(receiver, "theme_park.tatooine.mos_taike.cantina_receiver");
            setObjVar(receiver, "player", self);
            setObjVar(receiver, "quest", questNum);
            setObjVar(self, "mos_taike.cantina.receiver", receiver);
        }
        if (name.equals("questGiver"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishQuest(obj_id self, dictionary params) throws InterruptedException
    {
        location questGiver = getLocationObjVar(self, "mos_taike.cantina.barmaidLoc");
        setObjVar(self, "mos_taike.cantina.questDone", 1);
        quests.addQuestLocationTarget(self, null, "questGiver", questGiver, 10);
        return SCRIPT_CONTINUE;
    }
}
