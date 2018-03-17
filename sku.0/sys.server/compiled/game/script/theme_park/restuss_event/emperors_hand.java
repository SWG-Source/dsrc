package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.chat;
import script.library.prose;
import script.library.static_item;
import script.library.xp;
import script.library.trial;

public class emperors_hand extends script.base_script
{
    public emperors_hand()
    {
    }
    public static final String VOLUME_NAME = "aggressive_area";
    public static final string_id FOUND_JEDI = new string_id("restuss_event/object", "jedi_located");
    public static final string_id SID_MNU_LIGHTSABER = new string_id("restuss_event/object", "take_lightsaber");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, 15.0f, true);
        messageTo(self, "setLoiter", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, 15.0f, true);
        messageTo(self, "setLoiter", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "cleanupCorpse", null, 300.0f, false);
        String[] scriptList = getScriptList(self);
        if (scriptList == null || scriptList.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < scriptList.length; i++)
        {
            if (!scriptList[i].endsWith("emperors_hand"))
            {
                String script = scriptList[i].substring(7, scriptList[i].length());
                detachScript(self, script);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        prose_package pp = prose.getPackage(FOUND_JEDI);
        pp.target.set(breacher);
        if (utils.isProfession(breacher, utils.FORCE_SENSITIVE))
        {
            if (!ai_lib.isInCombat(self))
            {
                chat.chat(self, breacher, pp);
                addHate(self, breacher, 1000f);
                startCombat(self, breacher);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                addHate(self, breacher, 500f);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        Vector attackers = new Vector();
        attackers.setSize(0);
        Vector attackerList = utils.getResizeableObjIdArrayScriptVar(self, "attackerList");
        if (utils.hasScriptVar(self, "attackerList"))
        {
            attackers = utils.getResizeableObjIdArrayScriptVar(self, "attackerList");
            if (utils.getElementPositionInArray(attackerList, attacker) > -1)
            {
                return SCRIPT_CONTINUE;
            }
        }
        utils.addElement(attackers, attacker);
        utils.setScriptVar(self, "attackerList", attackers);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isIncapacitated(self))
        {
            utils.removeScriptVar(self, "attackerList");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        Vector attackerList = utils.getResizeableObjIdArrayScriptVar(self, "attackerList");
        if (isIncapacitated(self))
        {
            if (utils.getElementPositionInArray(attackerList, player) > -1)
            {
                if (utils.isProfession(player, utils.FORCE_SENSITIVE))
                {
                    if (!utils.hasScriptVar(player, "lootedSchematic"))
                    {
                        mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_LIGHTSABER);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        Vector attackerList = utils.getResizeableObjIdArrayScriptVar(self, "attackerList");
        if (isIncapacitated(self))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                if (utils.getElementPositionInArray(attackerList, player) > -1)
                {
                    if (utils.isProfession(player, utils.FORCE_SENSITIVE))
                    {
                        if (!utils.hasScriptVar(player, "lootedSchematic"))
                        {
                            obj_id newItem = static_item.createNewItemFunction("item_restuss_schematic_saber_04_01", pInv);
                            CustomerServiceLog("jedi_saber", "%TU - Jinsu Razor Schematic Issued: Schematic OID is " + newItem, player);
                            utils.setScriptVar(player, "lootedSchematic", 1);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int setLoiter(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        ai_lib.setLoiterRanges(self, 0f, 80.0f);
        return SCRIPT_CONTINUE;
    }
    public int cleanupCorpse(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupNpc(self);
        return SCRIPT_CONTINUE;
    }
}
