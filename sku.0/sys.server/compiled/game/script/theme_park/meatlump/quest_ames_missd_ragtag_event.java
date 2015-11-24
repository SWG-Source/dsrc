package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.create;
import script.library.trial;
import script.library.utils;

public class quest_ames_missd_ragtag_event extends script.base_script
{
    public quest_ames_missd_ragtag_event()
    {
    }
    public static final String OWNER_SCRIPTVAR = "waveEventPlayer";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUpGuard", null, 210, true);
        messageTo(self, "attackPlayer", null, 1, false);
        detachScript(self, "npc.converse.npc_convo");
        detachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id victim) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, OWNER_SCRIPTVAR);
        if (!isValidId(player) || !exists(player))
        {
            messageTo(self, "cleanUpGuard", null, 0, true);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "cleanUpGuard", null, 2, true);
        return SCRIPT_CONTINUE;
    }
    public int cleanUpGuard(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary webster = trial.getSessionDict(parent);
        messageTo(parent, "defaultEventReset", webster, 0, false);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int attackPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, OWNER_SCRIPTVAR);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        startCombat(self, player);
        return SCRIPT_CONTINUE;
    }
}
