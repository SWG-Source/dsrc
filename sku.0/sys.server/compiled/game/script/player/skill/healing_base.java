package script.player.skill;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.colors;
import script.library.combat;
import script.library.groundquests;
import script.library.group;
import script.library.healing;
import script.library.pet_lib;
import script.library.utils;
import script.library.vehicle;
import script.library.stealth;

public class healing_base extends script.base_script
{
    public healing_base()
    {
    }
    public static final int CONE = 0;
    public static final int SINGLE_TARGET = 1;
    public static final int AREA = 2;
    public static final int TARGET_AREA = 3;
    public static final String NONCOMBAT_DATATABLE = "datatables/combat/non_combat_data.iff";
    public static final int MAX_TARGET_ARRAY_SIZE = 10;
    public obj_id validateEnhancer(String parms) throws InterruptedException
    {
        if (parms == null || parms.length() < 1)
        {
            return null;
        }
        obj_id enhancer = utils.stringToObjId(parms);
        if (!isIdValid(enhancer) || !exists(enhancer) || !hasScript(enhancer, "item.medicine.enhancer"))
        {
            return null;
        }
        return enhancer;
    }
    public int[] getActionCost(obj_id self, dictionary actionData) throws InterruptedException
    {
        int[] cost = new int[2];
        float healthCost;
        float actionCost;
        healthCost = actionData.getFloat("healthCost") / 100f;
        actionCost = actionData.getFloat("actionCost") / 100f;
        combat.combatLog(self, null, "getActionCost", "Base Action cost = [" + healthCost + ", " + actionCost + "]");
        int maxHealth = getMaxAttrib(self, HEALTH);
        int maxAction = getMaxAttrib(self, ACTION);
        combat.combatLog(self, null, "getActionCost", "Max Attrib = [" + maxHealth + ", " + maxAction + "]");
        cost[0] = (int)(maxHealth * healthCost);
        cost[1] = (int)(maxAction * actionCost);
        combat.combatLog(self, null, "getActionCost", "Final Action cost = [" + cost[0] + ", " + cost[1] + "]");
        return cost;
    }
    public String makeHealingPlaybackName(obj_id self, obj_id target, dictionary actionData) throws InterruptedException
    {
        String playbackName = actionData.getString("animDefault");
        if (playbackName.endsWith("_*"))
        {
            playbackName = playbackName.substring(0, playbackName.length() - 1);
            if (self == target)
            {
                playbackName += "self";
            }
            else 
            {
                playbackName += "other";
            }
        }
        else if (playbackName.indexOf("&") != -1)
        {
            int index = playbackName.indexOf("&");
            String remain = playbackName.substring(index + 1, playbackName.length());
            playbackName = playbackName.substring(0, index - 1);
            float dist = getDistance(self, target);
            if (dist < 10)
            {
                playbackName += "_near" + remain;
            }
            else if (dist < 20)
            {
                playbackName += "_medium" + remain;
            }
            else 
            {
                playbackName += "_far" + remain;
            }
        }
        combat.combatLog(null, null, "makePlaybackName", "playback name = " + playbackName);
        return playbackName;
    }
    public String[] makeStringArray(int intLength) throws InterruptedException
    {
        int intI = 0;
        String[] strArray = new String[intLength];
        while (intI < intLength)
        {
            strArray[intI] = "";
            intI = intI + 1;
        }
        return strArray;
    }
}
