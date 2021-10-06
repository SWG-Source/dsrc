package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.create;
import script.library.ai_lib;
import script.library.utils;

public class first_sister_altar extends script.base_script
{
    public first_sister_altar()
    {
    }

    /**
     * "Place the Offering"
     */
    public static final string_id STF_PLACE_OFFERING = new string_id("quest/ground/wod_sister1", "task02_journal_entry_title");
    /**
     * "This object does not interest you."
     */
    public static final string_id STF_NO_INTEREST = new string_id("quest/groundquests", "retrieve_item_no_interest");
    /**
     * "%TU is not %TT. Please try again later."
     */
    public static final string_id STF_NOT_NOW = new string_id("spam", "not_available_fill_2");
    /**
     * "Naija Kymeri (Voidsister Huntress)"
     */
    public static final string_id STF_MOB = new string_id("mob/creature_names", "wod_first_sister");

    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleFirstSisterSpawn", null, 30f, false);
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if(groundquests.isTaskActive(player, "wod_sister1", "useAltar"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, STF_PLACE_OFFERING);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if(!groundquests.isTaskActive(player, "wod_sister1", "useAltar"))
        {
            sendSystemMessage(player, STF_NO_INTEREST);
            return SCRIPT_CONTINUE;
        }
        final obj_id boss = utils.getObjIdScriptVar(self, "wod_mob");
        if(!isIdValid(boss) || !exists(boss) || isDead(boss) || isIncapacitated(boss))
        {
            prose_package pp = new prose_package();
            pp.stringId = STF_NOT_NOW;
            pp.actor.set(STF_MOB);
            pp.target.set("nearby right now");
            sendSystemMessageProse(player, pp);
            return SCRIPT_CONTINUE;
        }
        else
        {
            if(getDistance(player, boss) > 20f)
            {
                pathTo(boss, utils.getRandomLocationInRing(getLocation(player), 3, 10));
            }
            groundquests.completeTask(player, "wod_sister1", "useAltar");

        }
        return SCRIPT_CONTINUE;
    }

    public int firstSisterDeath(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "wod_mob");
        messageTo(self, "handleFirstSisterSpawn", null, 600f, false);
        return SCRIPT_CONTINUE;
    }

    public int handleFirstSisterSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id boss = create.createCreature("wod_first_sister_aggro",
                new location(-4836f, 135f, -2939f, "dathomir", obj_id.NULL_ID), true);
        utils.setScriptVar(self, "wod_mob", boss);
        utils.setScriptVar(boss, "wod_altar", self);
        return SCRIPT_CONTINUE;
    }

}
