package script.event.emp_day;

import script.dictionary;
import script.library.create;
import script.library.firework;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class firework_show extends script.base_script
{
    public firework_show()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String setting = getConfigSetting("EventTeam", "empireDay");
        if (setting == null || setting.equals("") || !setting.equals("true"))
        {
            return SCRIPT_CONTINUE;
        }
        float rightNow = getGameTime();
        float nextShowTime = rightNow + 3600;
        setObjVar(self, "event.next_show_time", nextShowTime);
        messageTo(self, "fireworksTimerPing", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int fireworksTimerReset(obj_id self, dictionary params) throws InterruptedException
    {
        float rightNow = getGameTime();
        float nextShowTime = rightNow + 3600;
        setObjVar(self, "event.next_show_time", nextShowTime);
        return SCRIPT_CONTINUE;
    }
    public int fireworksTimerPing(obj_id self, dictionary params) throws InterruptedException
    {
        float nextShowTime = getFloatObjVar(self, "event.next_show_time");
        float rightNow = getGameTime();
        if (rightNow > nextShowTime)
        {
            messageTo(self, "broadcastFireworkAnnouncement", null, 1, false);
        }
        else 
        {
            messageTo(self, "fireworksTimerPing", null, 3600, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int broadcastFireworkAnnouncement(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 256.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                sendSystemMessage(objPlayer, new string_id("event/empire_day", "fireworks_broadcast"));
            }
        }
        messageTo(self, "startHugeFireworkDisplay", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int startHugeFireworkDisplay(obj_id self, dictionary params) throws InterruptedException
    {
        for (int i = 0; i < 150; i++)
        {
            messageTo(self, "launchRandomFirework", null, i * 2, false);
        }
        messageTo(self, "fireworksTimerReset", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int launchRandomFirework(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id effect = create.object(dataTableGetString(firework.TBL_FX, rand(1, dataTableGetNumRows(firework.TBL_FX)), "template"), utils.getRandomLocationInRing(getLocation(self), 0, 64));
        if (isIdValid(effect))
        {
            attachScript(effect, firework.SCRIPT_FIREWORK_CLEANUP);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (isGod(objSpeaker))
        {
            if ((toLower(strText)).startsWith("startfireworks"))
            {
                messageTo(self, "broadcastFireworkAnnouncement", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
