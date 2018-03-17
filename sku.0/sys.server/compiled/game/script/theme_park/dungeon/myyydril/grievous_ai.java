package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.utils;

public class grievous_ai extends script.base_script
{
    public grievous_ai()
    {
    }
    public static final string_id FIVE = new string_id("dungeon/myyydril", "five");
    public static final string_id FOUR = new string_id("dungeon/myyydril", "four");
    public static final string_id THREE = new string_id("dungeon/myyydril", "three");
    public static final string_id TWO = new string_id("dungeon/myyydril", "two");
    public static final string_id ONE = new string_id("dungeon/myyydril", "one");
    public static final color RED = new color(255, 0, 0, 255);
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleGrievousAiLoop(obj_id self, dictionary params) throws InterruptedException
    {
        int level = getIntObjVar(self, "grievous.power_level");
        if (level < 5)
        {
            obj_id powerCell = lookForPowerCells(self);
            if (isIdValid(powerCell))
            {
                dictionary d = new dictionary();
                d.put("powerCell", powerCell);
                if (utils.getBooleanScriptVar(self, "grievous.absorbing") == false)
                {
                    messageTo(self, "handleAbsorbPowerCell", d, 7.0f, false);
                }
            }
        }
        messageTo(self, "handleGrievousAiLoop", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public obj_id lookForPowerCells(obj_id self) throws InterruptedException
    {
        location selfLocation = getLocation(self);
        obj_id[] objectsInRange = getObjectsInRange(selfLocation, 200.0f);
        for (int i = 0; i < objectsInRange.length; i++)
        {
            if (hasObjVar(objectsInRange[i], "grievous_encounter.isPowerCell"))
            {
                obj_id powerCell = objectsInRange[i];
                ai_lib.aiFollow(self, powerCell);
                return powerCell;
            }
        }
        return null;
    }
    public int handleAbsorbPowerCell(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id powerCell = params.getObjId("powerCell");
        float distance = getDistance(self, powerCell);
        if (distance <= 15.0)
        {
            if (utils.getBooleanScriptVar(self, "grievous.absorbing") == false)
            {
                utils.setScriptVar(self, "grievous.absorbing", true);
                dictionary d = new dictionary();
                d.put("powerCell", powerCell);
                messageTo(self, "handleAbsorb5", d, 1.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAbsorb5(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.getBooleanScriptVar(self, "grievous.absorbing") == true)
        {
            showFlyText(self, FIVE, 1.0f, RED);
            messageTo(self, "handleAbsorb4", params, 1.0f, false);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAbsorb4(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.getBooleanScriptVar(self, "grievous.absorbing") == true)
        {
            showFlyText(self, FOUR, 1.0f, RED);
            messageTo(self, "handleAbsorb3", params, 1.0f, false);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAbsorb3(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.getBooleanScriptVar(self, "grievous.absorbing") == true)
        {
            showFlyText(self, THREE, 1.0f, RED);
            messageTo(self, "handleAbsorb2", params, 1.0f, false);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAbsorb2(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.getBooleanScriptVar(self, "grievous.absorbing") == true)
        {
            showFlyText(self, TWO, 1.0f, RED);
            messageTo(self, "handleAbsorb1", params, 1.0f, false);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAbsorb1(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.getBooleanScriptVar(self, "grievous.absorbing") == true)
        {
            showFlyText(self, ONE, 1.0f, RED);
            messageTo(self, "handleAbsorb0", params, 1.0f, false);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAbsorb0(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.getBooleanScriptVar(self, "grievous.absorbing") == true)
        {
            obj_id powerCell = params.getObjId("powerCell");
            destroyObject(powerCell);
            int level = getIntObjVar(self, "grievous.power_level");
            if (level < 5)
            {
                setObjVar(self, "grievous.power_level", level + 1);
                playClientEffectObj(self, "appearance/pt_grievious_powerup.prt", self, "");
                int hp = getMaxAttrib(self, 0);
                debugSpeakMsg(self, "My hitpoints are at: " + hp);
                setMaxAttrib(self, 0, hp + (int)(.10 * hp));
                int newHp = getMaxAttrib(self, 0);
                setAttrib(self, 0, newHp);
            }
            level = getIntObjVar(self, "grievous.power_level");
            utils.setScriptVar(self, "grievous.absorbing", false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        utils.setScriptVar(self, "grievous.absorbing", false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id contentManager = getObjIdObjVar(self, "grievous_encounter.contentManager");
        messageTo(contentManager, "handleNpcDeath", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
