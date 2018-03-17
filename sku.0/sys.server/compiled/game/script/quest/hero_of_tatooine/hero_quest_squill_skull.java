package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class hero_quest_squill_skull extends script.base_script
{
    public hero_quest_squill_skull()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "hero_of_tatooine_skull_to_hermit"))
        {
            obj_id questPlayer = utils.getContainingPlayer(self);
            if (isIdValid(questPlayer))
            {
                if (hasObjVar(questPlayer, "quest.hero_of_tatooine.task"))
                {
                    removeObjVar(questPlayer, "quest.hero_of_tatooine.task");
                }
                if (groundquests.isQuestActive(questPlayer, "quest_hero_of_tatooine_main"))
                {
                    groundquests.clearQuest(questPlayer, "quest_hero_of_tatooine_main");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
