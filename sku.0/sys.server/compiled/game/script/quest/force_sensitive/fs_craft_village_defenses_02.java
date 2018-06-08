package script.quest.force_sensitive;

import script.dictionary;
import script.library.fs_quests;
import script.library.quests;
import script.obj_id;

public class fs_craft_village_defenses_02 extends script.base_script
{
    public fs_craft_village_defenses_02()
    {
    }
    public static final String QUEST_STEP_02 = "fs_phase_2_craft_defenses_02";
    public static final String SKILL_BRANCH_UNLOCKED = "force_sensitive_crafting_mastery_technique";
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
