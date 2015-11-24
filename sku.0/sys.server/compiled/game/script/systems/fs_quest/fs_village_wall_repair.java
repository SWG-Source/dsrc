package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.utils;
import script.library.prose;

public class fs_village_wall_repair extends script.base_script
{
    public fs_village_wall_repair()
    {
    }
    public static final String PP_FILE_LOC = "quest/force_sensitive/fs_wall_repair";
    public static final String RESPONSE_TEXT = "datatables/fs_quests/fs_wall_repair.iff";
    public static final int INITIAL_DELAY = 10;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "ai.creature_combat"))
        {
            detachScript(self, "ai.creature_combat");
        }
        if (hasScript(self, "systems.combat.credit_for_kills"))
        {
            detachScript(self, "systems.combat.credit_for_kills");
        }
        if (hasScript(self, "systems.combat.combat_actions"))
        {
            detachScript(self, "systems.combat.combat_actions");
        }
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
        messageTo(self, "fsWallRepair", null, INITIAL_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int fsWallRepair(obj_id self, dictionary params) throws InterruptedException
    {
        int randAnim = rand(0, 4);
        if ((randAnim == 0) || (randAnim == 2) || (randAnim == 4))
        {
            ai_lib.doAction(self, "manipulate_medium");
        }
        else if ((randAnim == 1) || (randAnim == 3))
        {
            ai_lib.doAction(self, "manipulate_high");
        }
        if (randAnim > 2)
        {
            int randRepairRequest = rand(0, 7);
            chat.publicChat(self, null, null, null, getRandRepair(self, randRepairRequest));
        }
        int randRepeatMsg = rand(90, 300);
        messageTo(self, "fsWallRepair", null, randRepeatMsg, false);
        return SCRIPT_CONTINUE;
    }
    public prose_package getRandRepair(obj_id target, int repairRow) throws InterruptedException
    {
        string_id response = new string_id(PP_FILE_LOC, utils.dataTableGetString(RESPONSE_TEXT, repairRow, 1));
        prose_package pp = prose.getPackage(response, target);
        return pp;
    }
}
