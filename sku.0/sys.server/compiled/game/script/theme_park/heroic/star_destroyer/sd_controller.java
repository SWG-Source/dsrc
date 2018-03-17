package script.theme_park.heroic.star_destroyer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.skill;
import script.library.trial;
import script.library.utils;

public class sd_controller extends script.base_script
{
    public sd_controller()
    {
    }
    public int saitDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(self);
        dictionary dict = new dictionary();
        dict.put("tokenIndex", 3);
        utils.messageTo(players, "handleAwardtoken", dict, 0, false);
        obj_id group = getGroupObject(players[0]);
        int calendarTime = getCalendarTime();
        String realTime = getCalendarTimeStringLocal(calendarTime);
        CustomerServiceLog("instance-heroic_star_destroyer", "Star_Destroyer Defeated in instance (" + self + ") by group_id (" + group + ") at " + realTime);
        CustomerServiceLog("instance-heroic_star_destroyer", "Group (" + group + ") consists of: ");
        for (int i = 0; i < players.length; ++i)
        {
            String strProfession = skill.getProfessionName(getSkillTemplate(players[i]));
            CustomerServiceLog("instance-heroic_star_destroyer", "Group (" + group + ") member " + i + " " + getFirstName(players[i]) + "'s(" + players[i] + ") profession is " + strProfession + ".");
        }
        return SCRIPT_CONTINUE;
    }
}
