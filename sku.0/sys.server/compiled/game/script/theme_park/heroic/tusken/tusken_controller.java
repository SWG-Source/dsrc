package script.theme_park.heroic.tusken;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;
import script.library.skill;
import script.library.trial;
import script.library.utils;

public class tusken_controller extends script.base_script
{
    public tusken_controller()
    {
    }
    public int kingDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 1000.0f);
        instance.setClock(self, 300);
        dictionary dict = new dictionary();
        dict.put("tokenIndex", 1);
        utils.messageTo(players, "handleAwardtoken", dict, 0, false);
        obj_id group = getGroupObject(players[0]);
        int calendarTime = getCalendarTime();
        String realTime = getCalendarTimeStringLocal(calendarTime);
        CustomerServiceLog("instance-heroic_tusken_army", "TuskenArmy Defeated in instance (" + self + ") by group_id (" + group + ") at " + realTime);
        CustomerServiceLog("instance-heroic_tusken_army", "Group (" + group + ") consists of: ");
        for (int i = 0; i < players.length; ++i)
        {
            String strProfession = skill.getProfessionName(getSkillTemplate(players[i]));
            CustomerServiceLog("instance-heroic_tusken_army", "Group (" + group + ") member " + i + " " + getFirstName(players[i]) + "'s(" + players[i] + ") profession is " + strProfession + ".");
        }
        return SCRIPT_CONTINUE;
    }
    public int instanceFailed(obj_id self, dictionary params) throws InterruptedException
    {
        instance.setClock(self, 10);
        return SCRIPT_CONTINUE;
    }
}
