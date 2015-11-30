package script.npc.random_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;

public class quest_setup extends script.base_script
{
    public quest_setup()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void createStaticMissionNpc(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, "type");
        if (type == null || type.equals(""))
        {
            type = getRandomType();
        }
        type = determineSpawnType(type, self);
        setObjVar(self, "type", type);
        debugSpeakMsg(self, "Type is " + type);
        location here = getLocation(self);
        obj_id npc = create.object(type, here);
        String questScript = "npc.random_quest.quest_convo";
        attachScript(npc, questScript);
        setObjVar(npc, "quest_table", type);
        return;
    }
    public String getRandomType() throws InterruptedException
    {
        String type = "businessman";
        int randType = rand(1, 4);
        switch (randType)
        {
            case 1:
            type = "businessman";
            break;
            case 2:
            type = "criminal";
            break;
            case 3:
            type = "noble";
            break;
            case 4:
            type = "scientist";
            break;
        }
        return type;
    }
    public String determineSpawnType(String type, obj_id self) throws InterruptedException
    {
        String text = getTemplateName(self);
        int businessCheck = text.indexOf("businessman");
        int crimeCheck = text.indexOf("businessman");
        int nobleCheck = text.indexOf("businessman");
        int scienceCheck = text.indexOf("businessman");
        if (businessCheck > -1)
        {
            type = "businessman";
            return type;
        }
        if (crimeCheck > -1)
        {
            type = "criminal";
            return type;
        }
        if (nobleCheck > -1)
        {
            type = "noble";
            return type;
        }
        if (scienceCheck > -1)
        {
            type = "scientist";
            return type;
        }
        return type;
    }
}
