package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.utils;

public class doctor_bag extends script.base_script
{
    public doctor_bag()
    {
    }
    public static final int MAX_MEDS = 25;
    public static final int HEALING_RANGE = 0;
    public static final int HEALING_AREA = 1;
    public static final int HEALING_DOT_POWER = 2;
    public static final int HEALING_DOT_POTENCY = 3;
    public static final int HEALING_DOT_ATTRIBUTE = 4;
    public static final int HEALING_DOT_DURATION = 5;
    public static final string_id SID_MEDICINE_STORED = new string_id("doctor_bag", "medicine_stored");
    public static final string_id SID_MEDICINE_REMOVED = new string_id("doctor_bag", "medicine_removed");
    public static boolean isDoctorBag(obj_id object) throws InterruptedException
    {
        return hasObjVar(object, "doctor_bag");
    }
    public static boolean hasDoctorBag(obj_id player) throws InterruptedException
    {
        return (null != getDoctorBag(player));
    }
    public static obj_id getDoctorBag(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (pInv == null)
        {
            return null;
        }
        obj_id[] contents = utils.getContents(pInv, true);
        if (contents == null)
        {
            return null;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (isDoctorBag(contents[i]))
            {
                return contents[i];
            }
        }
        return null;
    }
    public static boolean addMedicine(obj_id player, obj_id bag, obj_id medicine) throws InterruptedException
    {
        if (hasScript(medicine, "systems.combat.combat_delayed_tracker"))
        {
            return false;
        }
        obj_var_list meds = getObjVarList(bag, "meds");
        if (meds == null)
        {
            meds = new obj_var_list("meds");
        }
        else if (meds.getNumItems() >= MAX_MEDS)
        {
            return false;
        }
        int free_index = -1;
        for (int i = 0; i < MAX_MEDS; i++)
        {
            obj_var_list med = meds.getObjVarList(Integer.toString(i));
            if (med == null)
            {
                free_index = i;
                break;
            }
        }
        if (free_index == -1)
        {
            return false;
        }
        String prefix = "meds." + free_index + ".";
        setObjVar(bag, prefix + "object_tangible", getTemplateName(medicine));
        setObjVar(bag, prefix + "name_id", getNameStringId(medicine));
        setObjVar(bag, prefix + "name", getName(medicine));
        setObjVar(bag, prefix + "consumable.medicine", getIntObjVar(medicine, "consumable.medicine"));
        setObjVar(bag, prefix + "consumable.mods", getAttribModArrayObjVar(medicine, "consumable.mods"));
        setObjVar(bag, prefix + "consumable.skillModMin", getIntArrayObjVar(medicine, "consumable.skillModMin"));
        setObjVar(bag, prefix + "consumable.skillModRequired", getStringArrayObjVar(medicine, "consumable.skillModRequired"));
        setObjVar(bag, prefix + "count", getCount(medicine));
        if (hasObjVar(medicine, "healing"))
        {
            if (hasObjVar(medicine, "healing.apply_dot"))
            {
                setObjVar(bag, prefix + "healing.apply_dot", getStringObjVar(medicine, "healing.apply_dot"));
            }
            if (hasObjVar(medicine, "healing.cure_dot"))
            {
                setObjVar(bag, prefix + "healing.cure_dot", getStringObjVar(medicine, "healing.cure_dot"));
            }
            if (hasObjVar(medicine, "healing.dot_id"))
            {
                setObjVar(bag, prefix + "healing.dot_id", getStringObjVar(medicine, "healing.dot_id"));
            }
            if (hasObjVar(medicine, "healing.state"))
            {
                setObjVar(bag, prefix + "healing.state", getStringObjVar(medicine, "healing.state"));
            }
            int dotInfo[] = new int[6];
            dotInfo[0] = getIntObjVar(medicine, "healing.range");
            dotInfo[1] = getIntObjVar(medicine, "healing.area");
            dotInfo[2] = getIntObjVar(medicine, "healing.dot_power");
            dotInfo[3] = getIntObjVar(medicine, "healing.dot_potency");
            dotInfo[4] = getIntObjVar(medicine, "healing.dot_attribute");
            dotInfo[5] = getIntObjVar(medicine, "healing.dot_duration");
            setObjVar(bag, prefix + "healing.dot_info", dotInfo);
        }
        prose_package pp = prose.getPackage(SID_MEDICINE_STORED, medicine);
        sendSystemMessageProse(player, pp);
        destroyObject(medicine);
        return true;
    }
    public static boolean removeMedicine(obj_id player, obj_id bag, int med) throws InterruptedException
    {
        String prefix = "meds." + med + ".";
        obj_id pInv = utils.getInventoryContainer(player);
        if (pInv == null)
        {
            return false;
        }
        String template = getStringObjVar(bag, prefix + "object_tangible");
        obj_id new_med = createObject(template, pInv, "");
        if (new_med == null)
        {
            return false;
        }
        setName(new_med, getStringIdObjVar(bag, prefix + "name_id"));
        setCount(new_med, getIntObjVar(bag, prefix + "count"));
        setObjVar(new_med, "consumable.medicine", getIntObjVar(bag, prefix + "consumable.medicine"));
        setObjVar(new_med, "consumable.mods", getAttribModArrayObjVar(bag, prefix + "consumable.mods"));
        setObjVar(new_med, "consumable.skillModMin", getIntArrayObjVar(bag, prefix + "consumable.skillModMin"));
        setObjVar(new_med, "consumable.skillModRequired", getStringArrayObjVar(bag, prefix + "consumable.skillModRequired"));
        if (hasObjVar(bag, prefix + "healing"))
        {
            if (hasObjVar(bag, prefix + "healing.apply_dot"))
            {
                setObjVar(new_med, "healing.apply_dot", getStringObjVar(bag, prefix + "healing.apply_dot"));
            }
            if (hasObjVar(bag, prefix + "healing.cure_dot"))
            {
                setObjVar(new_med, "healing.cure_dot", getStringObjVar(bag, prefix + "healing.cure_dot"));
            }
            if (hasObjVar(bag, prefix + "healing.dot_id"))
            {
                setObjVar(new_med, "healing.dot_id", getStringObjVar(bag, prefix + "healing.dot_id"));
            }
            if (hasObjVar(bag, prefix + "healing.state"))
            {
                setObjVar(new_med, "healing.state", getStringObjVar(bag, prefix + "healing.state"));
            }
            int dotInfo[] = getIntArrayObjVar(bag, prefix + "healing.dot_info");
            if (dotInfo[0] != 0)
            {
                setObjVar(new_med, "healing.range", dotInfo[0]);
            }
            if (dotInfo[1] != 0)
            {
                setObjVar(new_med, "healing.area", dotInfo[1]);
            }
            if (dotInfo[2] != 0)
            {
                setObjVar(new_med, "healing.dot_power", dotInfo[2]);
            }
            if (dotInfo[3] != 0)
            {
                setObjVar(new_med, "healing.dot_potency", dotInfo[3]);
            }
            if (dotInfo[4] != 0)
            {
                setObjVar(new_med, "healing.dot_attribute", dotInfo[4]);
            }
            if (dotInfo[5] != 0)
            {
                setObjVar(new_med, "healing.dot_duration", dotInfo[5]);
            }
        }
        setCraftedId(new_med, new_med);
        removeObjVar(bag, "meds." + med);
        prose_package pp = prose.getPackage(SID_MEDICINE_REMOVED, new_med);
        sendSystemMessageProse(player, pp);
        return true;
    }
    public static boolean setSurrogateState(obj_id bag, String prefix) throws InterruptedException
    {
        clearSurrogateState(bag);
        setObjVar(bag, "consumable.medicine", getIntObjVar(bag, prefix + "consumable.medicine"));
        setObjVar(bag, "consumable.mods", getAttribModArrayObjVar(bag, prefix + "consumable.mods"));
        setObjVar(bag, "consumable.skillModMin", getIntArrayObjVar(bag, prefix + "consumable.skillModMin"));
        setObjVar(bag, "consumable.skillModRequired", getStringArrayObjVar(bag, prefix + "consumable.skillModRequired"));
        if (hasObjVar(bag, prefix + "healing"))
        {
            if (hasObjVar(bag, prefix + "healing.apply_dot"))
            {
                setObjVar(bag, "healing.apply_dot", getStringObjVar(bag, prefix + "healing.apply_dot"));
            }
            if (hasObjVar(bag, prefix + "healing.cure_dot"))
            {
                setObjVar(bag, "healing.cure_dot", getStringObjVar(bag, prefix + "healing.cure_dot"));
            }
            if (hasObjVar(bag, prefix + "healing.dot_id"))
            {
                setObjVar(bag, "healing.dot_id", getStringObjVar(bag, prefix + "healing.dot_id"));
            }
            if (hasObjVar(bag, prefix + "healing.state"))
            {
                setObjVar(bag, "healing.state", getStringObjVar(bag, prefix + "healing.state"));
            }
            int dotInfo[] = getIntArrayObjVar(bag, prefix + "healing.dot_info");
            if (dotInfo[0] != 0)
            {
                setObjVar(bag, "healing.range", dotInfo[0]);
            }
            if (dotInfo[1] != 0)
            {
                setObjVar(bag, "healing.area", dotInfo[1]);
            }
            if (dotInfo[2] != 0)
            {
                setObjVar(bag, "healing.dot_power", dotInfo[2]);
            }
            if (dotInfo[3] != 0)
            {
                setObjVar(bag, "healing.dot_potency", dotInfo[3]);
            }
            if (dotInfo[4] != 0)
            {
                setObjVar(bag, "healing.dot_attribute", dotInfo[4]);
            }
            if (dotInfo[5] != 0)
            {
                setObjVar(bag, "healing.dot_duration", dotInfo[5]);
            }
        }
        setCount(bag, getIntObjVar(bag, prefix + "count"));
        setObjVar(bag, "surrogate", prefix);
        int[] stomachMods = new int[3];
        setObjVar(bag, "consumable.stomachValues", stomachMods);
        return true;
    }
    public static boolean clearSurrogateState(obj_id bag) throws InterruptedException
    {
        removeObjVar(bag, "surrogate");
        removeObjVar(bag, "consumable.medicine");
        removeObjVar(bag, "consumable.mods");
        removeObjVar(bag, "consumable.skillModMin");
        removeObjVar(bag, "consumable.skillModRequired");
        removeObjVar(bag, "consumable.stomachValues");
        removeObjVar(bag, "healing");
        setCount(bag, 0);
        return true;
    }
    public static void decrementSurrogateCharge(obj_id bag) throws InterruptedException
    {
        String prefix = getStringObjVar(bag, "surrogate");
        int charges = getIntObjVar(bag, prefix + "count");
        charges--;
        if (charges <= 0)
        {
            String index = prefix.substring(0, prefix.length() - 1);
            removeObjVar(bag, index);
        }
        else 
        {
            setObjVar(bag, prefix + "count", charges);
        }
    }
}
