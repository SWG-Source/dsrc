package script.theme_park.poi.general.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.theme_park.tatooine.gating;
import script.ai.ai_combat;

public class spawn_squill extends script.base_script
{
    public spawn_squill()
    {
    }
    public static final String QUEST_ID = gating.BIB_QUEST;
    public static final String QUEST_SCRIPT_PATH = "theme_park.tatooine.bib_quest.";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location hero = getLocation(self);
        hero.x = hero.x + 140;
        location squill = hero;
        setObjVar(self, "squillLoc", squill);
        quests.addQuestLocationTarget(self, "squill", "squill", squill, 30);
        return SCRIPT_CONTINUE;
    }
    public int IDied(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "squillKilled", 1);
        detachScript(self, "theme_park.poi.general.special.spawn_squill");
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("squill"))
        {
            location squill = getLocationObjVar(self, "squillLoc");
            quests.clearQuestLocationTarget(self, "squill", "squill");
            obj_id creature = createObject("object/creature/monster/squill/squill.iff", squill);
            attachScript(creature, "theme_park.poi.general.behavior.dangerous_animal");
            setObjVar(creature, "quester", self);
            removeObjVar(self, "squillLoc");
            startCombat(creature, self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
