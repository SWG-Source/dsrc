package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.prose;
import script.library.trial;
import script.library.utils;

public class wave_event_on_creature extends script.base_script
{
    public wave_event_on_creature()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        trial.setInterest(self);
        messageTo(self, "waveEventCheckForUtterance", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int waveEventCheckForUtterance(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "waveEventUtterance"))
        {
            String utterance = utils.getStringScriptVar(self, "waveEventUtterance");
            obj_id player = utils.getObjIdScriptVar(self, "waveEventPlayer");
            if (isIdValid(player))
            {
                String playerName = getName(player);
                if (playerName != null && playerName.length() > 0)
                {
                    prose_package pp = new prose_package();
                    pp.stringId = utils.unpackString(utterance);
                    prose.setTO(pp, playerName);
                    chat.publicChat(self, player, null, null, pp);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary webster = new dictionary();
        webster.put("waveEventChild", self);
        messageTo(parent, "waveEventChildDestroyed", webster, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            obj_id parent = trial.getParent(self);
            dictionary webster = new dictionary();
            webster.put("waveEventChild", self);
            messageTo(parent, "waveEventChildDestroyed", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/ai_controller/" + section, message);
        }
    }
}
