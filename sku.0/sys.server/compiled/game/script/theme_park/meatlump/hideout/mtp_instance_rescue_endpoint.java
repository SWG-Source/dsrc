package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.trial;
import script.library.utils;

public class mtp_instance_rescue_endpoint extends script.base_script
{
    public mtp_instance_rescue_endpoint()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("mtp_trappedMeatlump", 5.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isIdValid(breacher) || isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("mtp_trappedMeatlump"))
        {
            if (utils.hasScriptVar(breacher, "mtp_rescuingPlayer") && !utils.hasScriptVar(breacher, "mtp_isSafe"))
            {
                dictionary webster = new dictionary();
                webster.put("meatlump", breacher);
                messageTo(self, "handleMeatlumpRescued", webster, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMeatlumpRescued(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id meatlump = params.getObjId("meatlump");
        obj_id player = utils.getObjIdScriptVar(meatlump, "mtp_rescuingPlayer");
        if (isIdValid(player))
        {
            groundquests.sendSignal(player, "escort_trapped_meatlump");
            ai_lib.setMood(meatlump, chat.MOOD_RELIEVED);
            chat.setChatMood(meatlump, chat.MOOD_RELIEVED);
            chat.setChatType(meatlump, chat.CHAT_EXCLAIM);
            chat.chat(meatlump, "The exit! I can make it out from here. Thank you!!");
            stop(meatlump);
            removeObjVar(meatlump, "ai.persistantFollowing");
            utils.setScriptVar(meatlump, "mtp_isSafe", true);
            obj_id building = trial.getTop(self);
            if (isIdValid(building))
            {
                obj_id entryb = getCellId(building, "entryb");
                pathTo(meatlump, new location(3.4f, -12.0f, 26.2f, "dungeon1", entryb));
                dictionary webster = new dictionary();
                webster.put("meatlump", meatlump);
                messageTo(self, "handleTrappedMeatlumpCleanup", webster, 4, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTrappedMeatlumpCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id meatlump = params.getObjId("meatlump");
        trial.cleanupObject(meatlump);
        return SCRIPT_CONTINUE;
    }
}
