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

public class fs_village_wounded extends script.base_script
{
    public fs_village_wounded()
    {
    }
    public static final String PP_FILE_LOC = "quest/force_sensitive/fs_lamentations";
    public static final String RESPONSE_TEXT = "datatables/fs_quests/fs_lamentations.iff";
    public static int INITIAL_DELAY = 10;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "ai.ai"))
        {
            detachScript(self, "ai.ai");
        }
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
        setPosture(self, POSTURE_KNOCKED_DOWN);
        messageTo(self, "fsLamentations", null, INITIAL_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int fsLamentations(obj_id self, dictionary params) throws InterruptedException
    {
        int randMoan = rand(0, 9);
        chat.publicChat(self, null, null, null, getRandLamentation(self, randMoan));
        int randMsg = rand(90, 300);
        messageTo(self, "fsLamentations", null, randMsg, false);
        return SCRIPT_CONTINUE;
    }
    public prose_package getRandLamentation(obj_id target, int moanRow) throws InterruptedException
    {
        string_id response = new string_id(PP_FILE_LOC, utils.dataTableGetString(RESPONSE_TEXT, moanRow, 1));
        prose_package pp = prose.getPackage(response, target);
        return pp;
    }
}
