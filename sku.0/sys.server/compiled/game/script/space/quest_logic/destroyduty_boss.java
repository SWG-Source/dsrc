package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;
import script.library.prose;

public class destroyduty_boss extends script.space.quest_logic.destroyduty_ship
{
    public destroyduty_boss()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "deliverTaunt", null, 3.f, false);
        return SCRIPT_CONTINUE;
    }
    public int deliverTaunt(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        obj_id quest = getObjIdObjVar(self, "quest");
        int bosslevel = getIntObjVar(quest, "difficulty.basecount");
        String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
        String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
        string_id taunt = new string_id("spacequest/" + questType + "/" + questName, "dd_boss_taunt_" + bosslevel);
        prose_package pp = prose.getPackage(taunt, 0);
        space_quest.groupTaunt(self, player, pp);
        return SCRIPT_CONTINUE;
    }
}
