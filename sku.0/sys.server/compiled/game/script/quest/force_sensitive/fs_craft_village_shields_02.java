package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.quests;
import script.library.utils;

public class fs_craft_village_shields_02 extends script.base_script
{
    public fs_craft_village_shields_02()
    {
    }
    public static final String QUEST_STEP_02 = "fs_phase_3_craft_shields_02";
    public static final String SKILL_BRANCH_UNLOCKED = "force_sensitive_crafting_mastery_experimentation";
    public int handleCommunityCraftingMinimumQuantityMet(obj_id self, dictionary params) throws InterruptedException
    {
        if (!fs_quests.hasUnlockedBranch(self, SKILL_BRANCH_UNLOCKED))
        {
            fs_quests.unlockBranch(self, SKILL_BRANCH_UNLOCKED);
        }
        quests.complete(QUEST_STEP_02, self, true);
        return SCRIPT_CONTINUE;
    }
}
