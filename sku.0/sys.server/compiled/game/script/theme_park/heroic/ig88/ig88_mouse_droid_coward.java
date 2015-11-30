package script.theme_park.heroic.ig88;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.hue;
import script.library.skill;
import script.library.trial;
import script.library.utils;

public class ig88_mouse_droid_coward extends script.base_script
{
    public ig88_mouse_droid_coward()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary sessionDict = new dictionary();
        messageTo(self, "blink", sessionDict, 0.25f, true);
        return SCRIPT_CONTINUE;
    }
    public int blink(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "appearance/pt_ig88_mouse_droid_blink.prt", self, "");
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary sessionDict = new dictionary();
        messageTo(self, "blink", sessionDict, 1, true);
        return SCRIPT_CONTINUE;
    }
    public int shoutDefeat(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, chat.CHAT_SHOUT, new string_id("spam", "ig88_defeat"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "ig88.kicked"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", "kick"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player))
        {
            sendSystemMessage(player, new string_id("spam", "cant_do_it_state"));
            return SCRIPT_CONTINUE;
        }
        obj_id dungeon = getTopMostContainer(self);
        obj_id target = null;
        if (!isIdValid(dungeon) || utils.hasScriptVar(self, "ig88.kicked"))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            playClientEffectObj(self, "sound/dro_mse6_hit_heavy.snd", self, "");
            playClientEffectObj(player, "sound/pl_kick_hv_hit_metal.snd", player, "");
            dictionary params = trial.getSessionDict(dungeon);
            params.put("triggerName", "message_mouse_droid_run_away");
            params.put("target", self);
            params.put("triggerType", "triggerId");
            messageTo(dungeon, "triggerFired", params, 0, false);
            utils.setScriptVar(self, "ig88.kicked", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
