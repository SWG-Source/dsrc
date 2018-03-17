package script.theme_park.heroic.axkva_min;

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

public class axkva_controller extends script.base_script
{
    public axkva_controller()
    {
    }
    public int axkvaDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(self);
        trial.setDungeonCleanOutTimer(self, 15);
        dictionary dict = new dictionary();
        dict.put("tokenIndex", 0);
        utils.messageTo(players, "handleAwardtoken", dict, 0, false);
        obj_id group = getGroupObject(players[0]);
        int calendarTime = getCalendarTime();
        String realTime = getCalendarTimeStringLocal(calendarTime);
        CustomerServiceLog("instance-heroic_axkva_min", "AxkVa Defeated in instance (" + self + ") by group_id (" + group + ") at " + realTime);
        CustomerServiceLog("instance-heroic_axkva_min", "Group (" + group + ") consists of: ");
        for (int i = 0; i < players.length; ++i)
        {
            String strProfession = skill.getProfessionName(getSkillTemplate(players[i]));
            CustomerServiceLog("instance-heroic_axkva_min", "Group (" + group + ") member " + i + " " + getFirstName(players[i]) + "'s(" + players[i] + ") profession is " + strProfession + ".");
        }
        instance.setClock(self, 300);
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objects = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id shade = null;
        for (int i = 0; i < objects.length; i++)
        {
            if (!hasObjVar(objects[i], "spawn_id"))
            {
                continue;
            }
            if ((getStringObjVar(objects[i], "spawn_id")).equals("shade"))
            {
                shade = objects[i];
            }
        }
        addPassiveReveal(shade, item, 1);
        return SCRIPT_CONTINUE;
    }
}
