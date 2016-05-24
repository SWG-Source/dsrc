package script.space.quest_logic;

import script.dictionary;
import script.library.prose;
import script.library.space_quest;
import script.library.space_utils;
import script.obj_id;
import script.prose_package;
import script.string_id;

public class destroyduty_ship extends script.base_script
{
    public destroyduty_ship()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "randomTaunt", null, rand(10, 20), false);
        return SCRIPT_CONTINUE;
    }
    public int randomTaunt(obj_id self, dictionary params) throws InterruptedException
    {
        float r = rand();
        if (r < .15f)
        {
            if(!isIdValid(self) || !exists(self)){
                return SCRIPT_CONTINUE;
            }
            obj_id quest = getObjIdObjVar(self, "quest");
            if (!exists(quest))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id player = getObjIdObjVar(quest, space_quest.QUEST_OWNER);
            if (!exists(player))
            {
                return SCRIPT_CONTINUE;
            }
            String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
            String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
            int numTaunts = getIntObjVar(quest, "numResponses");
            if (numTaunts > 0)
            {
                string_id taunt = new string_id("spacequest/" + questType + "/" + questName, "taunt_" + rand(1, numTaunts));
                String staunt = localize(taunt);
                if ((staunt != null) && (!staunt.equals("")))
                {
                    prose_package pp = prose.getPackage(taunt, 0);
                    space_quest.groupTaunt(self, player, pp);
                }
            }
        }
        messageTo(self, "randomTaunt", null, rand(30, 40), false);
        return SCRIPT_CONTINUE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        dictionary outparams = new dictionary();
        outparams.put("ship", self);
        space_utils.notifyObject(quest, "targetDestroyed", outparams);
        return SCRIPT_CONTINUE;
    }
    public int missionAbort(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (isIdValid(quest) && exists(quest) && hasObjVar(quest, "critical_warped"))
        {
            dictionary outparams = new dictionary();
            outparams.put("ship", self);
            messageTo(quest, "warpoutFailure", null, 2.f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
