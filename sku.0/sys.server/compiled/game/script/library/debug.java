package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.healing;
import script.library.pclib;

public class debug extends script.base_script
{
    public debug()
    {
    }
    public static final String VERSION = "v0.01.00";
    public static final String VAR_DEBUG_BASE = "debug";
    public static final String VAR_DICTIONARY_IN = "debug.dict.in";
    public static final String VAR_DICTIONARY_OUT = "debug.dict.out";
    public static void debugAllMsg(String channelName, obj_id self, String message) throws InterruptedException
    {
        debugServerConsoleMsg(self, message);
        LOG(channelName, message);
    }
    public static void print(String msg) throws InterruptedException
    {
        if (msg == null || msg.equals(""))
        {
            msg = "ERROR: invalid (null or empty) parameter specified in debug.print";
        }
        debugServerConsoleMsg(getSelf(), msg);
    }
    public static void barkContents(obj_id self, obj_id[] contents) throws InterruptedException
    {
        String contentsMsg = "Contents: ";
        for (int i = 0; i < contents.length; ++i)
        {
            if (contents[i] != null)
            {
                contentsMsg += " [";
                contentsMsg += contents[i].toString();
                contentsMsg += "]";
            }
        }
        debugServerConsoleMsg(self, contentsMsg);
    }
    public static void barkBug(Object script, obj_id self, String msgText) throws InterruptedException
    {
        String msg = "in file: ";
        msg += (script.getClass()).getName();
        msg += " - ";
        msg += msgText;
        debugServerConsoleMsg(self, msg);
    }
    public static String getDebugName(obj_id id) throws InterruptedException
    {
        if (id == null)
        {
            return (String)("[debug.getDebugName] ERROR: id == null");
        }
        String name = base_class.getName(id);
        if (name == null)
        {
            return (String)("invalid object id");
        }
        return (String)(base_class.getName(id) + " [" + id + "] ");
    }
    public static boolean fullHeal(obj_id target) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        boolean litmus = true;
        int shock = getShockWound(target);
        litmus &= healShockWound(target, shock);
        for (int i = 0; i < 3; i++)
        {
            int attrib = i * 3;
            litmus &= healing.healDamage(target, attrib, 2 * getMaxAttrib(target, attrib));
        }
        return litmus;
    }
    public static boolean damageMob(obj_id target, int attrib, int amt) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        return healing.healDamage(target, attrib, -amt);
    }
    public static boolean heal(obj_id target, int attrib, int amt) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        return healing.healDamage(target, attrib, amt);
    }
    public static boolean woundMob(obj_id target, int attrib, int amt) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        return true;
    }
    public static boolean healWounds(obj_id target) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        return true;
    }
    public static boolean getShock(obj_id target) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        int shock = getShockWound(target);
        debugSpeakMsg(target, "(" + getGameTime() + ") my shock wound value = " + shock);
        return true;
    }
    public static boolean addShock(obj_id target, int amt) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        if (addShockWound(target, amt))
        {
            return getShock(target);
        }
        return false;
    }
    public static boolean healShock(obj_id target, int amt) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        if (healShockWound(target, amt))
        {
            return getShock(target);
        }
        return false;
    }
    public static boolean zeroShock(obj_id target) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        if (setShockWound(target, 0))
        {
            return getShock(target);
        }
        return false;
    }
    public static boolean incapacitateMob(obj_id target) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)))
        {
            return false;
        }
        boolean litmus = true;
        int dam = 0;
        for (int i = 0; i < 3; i++)
        {
            dam += getAttrib(target, i * 3);
        }
        damage(target, 0, 0, dam * 10);
        return litmus;
    }
    public static boolean killCreature(obj_id target) throws InterruptedException
    {
        if ((target == null) || (!isMob(target)) || (isPlayer(target)))
        {
            return false;
        }
        if (incapacitateMob(target))
        {
            return kill(target);
        }
        return false;
    }
    public static void forceSuicide(obj_id target) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return;
        }
        suicide(target);
    }
    public static void suicide(obj_id self) throws InterruptedException
    {
        pclib.killPlayer(self, self);
    }
}
