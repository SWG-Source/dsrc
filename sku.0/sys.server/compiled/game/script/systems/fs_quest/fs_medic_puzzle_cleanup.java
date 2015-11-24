package script.systems.fs_quest;

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

public class fs_medic_puzzle_cleanup extends script.base_script
{
    public fs_medic_puzzle_cleanup()
    {
    }
    public static final boolean DEBUGGING = false;
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "systems.fs_quest.fs_medic_puzzle_cleanup");
        return SCRIPT_CONTINUE;
    }
    public void cleanUpStuff(obj_id player) throws InterruptedException
    {
        removeObjVar(player, "fs.numberHealed");
        if (!quests.isComplete("fs_medic_puzzle_quest_01", player))
        {
            quests.deactivate("fs_medic_puzzle_quest_01", player);
            clearCompletedQuest(player, quests.getQuestId("fs_medic_puzzle_quest_01"));
            sendSystemMessage(player, new string_id("fs_quest_village", "combat_quest_failed_timeout"));
        }
        if (!quests.isComplete("fs_medic_puzzle_quest_02", player))
        {
            clearCompletedQuest(player, quests.getQuestId("fs_medic_puzzle_quest_02"));
            quests.deactivate("fs_medic_puzzle_quest_02", player);
        }
        if (!quests.isComplete("fs_medic_puzzle_quest_02", player))
        {
            clearCompletedQuest(player, quests.getQuestId("fs_medic_puzzle_quest_03"));
            quests.deactivate("fs_medic_puzzle_quest_03", player);
        }
        if (quests.isComplete("fs_medic_puzzle_quest_01", player))
        {
            quests.complete("fs_medic_puzzle_quest_finish", player, true);
        }
        revokeSchematic(player, "object/draft_schematic/item/quest_item/fs_medic_puzzle_heal_pack.iff");
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        cleanUpStuff(self);
        return SCRIPT_CONTINUE;
    }
}
