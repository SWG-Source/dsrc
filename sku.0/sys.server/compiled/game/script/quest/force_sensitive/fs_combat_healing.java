package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.utils;
import script.library.fs_quests;
import script.library.fs_dyn_village;

public class fs_combat_healing extends script.base_script
{
    public fs_combat_healing()
    {
    }
    public static final boolean DEBUGGING = false;
    public int receiveCreditForCombatHealing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        if (quests.isActive("fs_combat_healing_1", self) || quests.isActive("fs_combat_healing_2", self))
        {
            if (hasScript(target, "systems.fs_quest.fs_village_enemy_ai"))
            {
                if (hasObjVar(self, "quest.fs_combat_healing.totalhealed"))
                {
                    int healTotal = getIntObjVar(self, "quest.fs_combat_healing.totalhealed");
                    healTotal++;
                    setObjVar(self, "quest.fs_combat_healing.totalhealed", healTotal);
                }
                else 
                {
                    setObjVar(self, "quest.fs_combat_healing.totalhealed", 1);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "quest.force_sensitive.fs_combat_healing");
        return SCRIPT_CONTINUE;
    }
    public void cleanUpStuff(obj_id player) throws InterruptedException
    {
        removeObjVar(player, "quest.fs_combat_healing.totalhealed");
        if (!quests.isComplete("fs_combat_healing_1", player))
        {
            sendSystemMessage(player, new string_id("fs_quest_village", "combat_quest_failed_timeout"));
            quests.deactivate("fs_combat_healing_1", player);
            clearCompletedQuest(player, quests.getQuestId("fs_combat_healing_1"));
        }
        if (!quests.isComplete("fs_combat_healing_2", player))
        {
            quests.deactivate("fs_combat_healing_2", player);
            clearCompletedQuest(player, quests.getQuestId("fs_combat_healing_2"));
        }
        if (quests.isComplete("fs_combat_healing_1", player))
        {
            quests.complete("fs_combat_healing_finish", player, true);
        }
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        cleanUpStuff(self);
        return SCRIPT_CONTINUE;
    }
}
