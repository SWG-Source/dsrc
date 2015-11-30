package script.npc;

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

public class legacy_sick extends script.base_script
{
    public legacy_sick()
    {
    }
    public static final String PP_FILE_LOC = "quest/legacy/legacy_sick";
    public static final String RESPONSE_TEXT = "datatables/npc/legacy/legacy_sick.iff";
    public static int INITIAL_DELAY = 10;
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
        messageTo(self, "legSick", null, INITIAL_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int legSick(obj_id self, dictionary params) throws InterruptedException
    {
        int randSick = rand(0, 6);
        chat.publicChat(self, null, null, null, getRandSick(self, randSick));
        ai_lib.doAction(self, "heavy_cough_vomit");
        int randRepeatMsg = rand(90, 300);
        messageTo(self, "legSick", null, randRepeatMsg, false);
        return SCRIPT_CONTINUE;
    }
    public prose_package getRandSick(obj_id target, int sickRow) throws InterruptedException
    {
        string_id response = new string_id(PP_FILE_LOC, utils.dataTableGetString(RESPONSE_TEXT, sickRow, 1));
        prose_package pp = prose.getPackage(response, target);
        return pp;
    }
}
