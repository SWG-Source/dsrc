package script.item.comestible;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.consumable;
import script.library.utils;
import script.library.metrics;
import script.library.healing;

public class spice extends script.base_script
{
    public spice()
    {
    }
    public static final string_id SID_SYS_ALREADY_SPICED = new string_id("spice/spice", "sys_already_spiced");
    public static final String EXAM_ATTRIB_MOD = "attribmods";
    public static final String EXAM_NONE = "@consumable:none";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "spice");
        String template = getTemplateName(self);
        int Fidx = (template.lastIndexOf("spice_") + 6);
        int Lidx = (template.lastIndexOf(".iff") - 1);
        String name = template.substring(Fidx, Lidx);
        setObjVar(self, "spice.name", name);
        detachScript(self, "item.comestible.spice");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            eatSpice(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void eatSpice(obj_id self, obj_id player) throws InterruptedException
    {
        int dur = getIntObjVar(self, "spice.duration");
        int[] mods = getIntArrayObjVar(self, "spice.mods");
        if (dur == 0)
        {
            dur = (int)getFloatObjVar(self, "spice.duration");
        }
        if (hasAttribModifier(player, "spice"))
        {
            sendSystemMessage(player, SID_SYS_ALREADY_SPICED);
            return;
        }
        String tname = getTemplateName(self);
        int pos = tname.indexOf('_');
        String name = tname.substring(pos + 1);
        name = name.substring(0, name.indexOf('.'));
        string_id cmsg = new string_id("spice/spice", name + "_consume");
        sendSystemMessage(player, cmsg);
        boolean buffIcon = false;
        for (int i = 0; i < mods.length; i++)
        {
            int buffValue = 0;
            if (mods[i] != 0)
            {
                attrib_mod newMod;
                if (!buffIcon)
                {
                    newMod = new attrib_mod("spice." + name + ".up", i, buffValue, dur, 0, 0, true, true, true);
                    buffIcon = true;
                }
                else 
                {
                    newMod = new attrib_mod(null, i, buffValue, dur, 0, 0, true, false, false);
                }
                addAttribModifier(player, newMod);
                metrics.logBuffStatus(player);
            }
        }
        float downTimeReduction = 0f;
        if (utils.hasScriptVar(player, "food.reduce_spice_downtime.eff"))
        {
            int eff = utils.getIntScriptVar(player, "food.reduce_spice_downtime.eff");
            utils.removeScriptVarTree(player, "food.reduce_spice_downtime");
            clearBuffIcon(player, "food.reduce_spice_downtime");
            downTimeReduction = 1f - (eff / 100f);
            if (downTimeReduction > .7f)
            {
                downTimeReduction = .7f;
            }
        }
        attachScript(player, "player.player_spice");
        utils.setScriptVar(player, "spice.name", name);
        utils.setScriptVar(player, "spice.mods", mods);
        utils.setScriptVar(player, "spice.duration", dur);
        utils.setScriptVar(player, "spice.downTimeReduction", downTimeReduction);
        destroyObject(self);
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int dur = getIntObjVar(self, "spice.duration");
        int[] mods = getIntArrayObjVar(self, "spice.mods");
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = EXAM_ATTRIB_MOD;
        if ((mods == null))
        {
            attribs[idx] = EXAM_NONE;
        }
        else 
        {
            int numMods = 0;
            int modNum = idx + 1;
            if (modNum > names.length)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < mods.length; i++)
            {
                if (mods[i] == 0)
                {
                    continue;
                }
                String sVal = "";
                if (mods[i] > 0)
                {
                    sVal = "+";
                }
                names[modNum] = toLower(consumable.STAT_NAME[i]);
                attribs[modNum] = sVal + "%, " + dur + "s";
                numMods++;
                modNum++;
                if (modNum > names.length)
                {
                    break;
                }
            }
            if (numMods == 0)
            {
                attribs[idx] = EXAM_NONE;
            }
            else 
            {
                attribs[idx] = "" + numMods;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
