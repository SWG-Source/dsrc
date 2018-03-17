package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.metrics;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class mine_disarm_kit extends script.base_script
{
    public mine_disarm_kit()
    {
    }
    public static final string_id SID_CANNOT_USE_YET = new string_id("faction/faction_hq/faction_hq_response", "mine_disarm_cannot_use_yet");
    public static final string_id SID_FOUND_NO_MINES = new string_id("faction/faction_hq/faction_hq_response", "mine_disarm_found_no_mines");
    public static final string_id SID_FRIENDLY_MINEFIELD = new string_id("faction/faction_hq/faction_hq_response", "mine_disarm_friendly_minefield");
    public static final string_id SID_ACCIDENTAL_DETONATE = new string_id("faction/faction_hq/faction_hq_response", "mine_disarm_accidental_detonate");
    public static final string_id SID_COULD_NOT_DISARM = new string_id("faction/faction_hq/faction_hq_response", "mine_disarm_could_not_disarm");
    public static final string_id SID_SUCCESSFUL_DISARM = new string_id("faction/faction_hq/faction_hq_response", "mine_disarm_successful_disarm");
    public static final String[] DISARM_EFFECT = 
    {
        "appearance/pt_disarm_mine_trigger.prt",
        "appearance/pt_disarm_mine_fail.prt",
        "appearance/pt_disarm_mine_success.prt"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            findMine(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void findMine(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "clickItem.mine_disarm"))
        {
            int time = getIntObjVar(player, "clickItem.mine_disarm");
            if (getGameTime() < time)
            {
                sendSystemMessage(player, SID_CANNOT_USE_YET);
                return;
            }
        }
        playClientEffectObj(player, "appearance/pt_mine_disarm.prt", player, "");
        setObjVar(player, "clickItem.mine_disarm", (getGameTime() + 60));
        sendCooldownGroupTimingOnly(player, getStringCrc("mine_disarm"), 60);
        obj_id[] objects = getNonCreaturesInRange(player, 40f);
        if (objects == null || objects.length == 0)
        {
            sendSystemMessage(player, SID_FOUND_NO_MINES);
            return;
        }
        obj_id minefield = obj_id.NULL_ID;
        for (int i = 0; i < objects.length; i++)
        {
            if (isIdValid(objects[i]) && hasScript(objects[i], "faction_perk.minefield.advanced_minefield"))
            {
                minefield = objects[i];
                break;
            }
        }
        if (!isIdValid(minefield) || !exists(minefield))
        {
            sendSystemMessage(player, SID_FOUND_NO_MINES);
            return;
        }
        if (!pvpAreFactionsOpposed(pvpGetAlignedFaction(player), pvpGetAlignedFaction(minefield)))
        {
            sendSystemMessage(player, SID_FRIENDLY_MINEFIELD);
            return;
        }
        obj_id structure = getObjIdObjVar(minefield, hq.VAR_DEFENSE_PARENT);
        if (!isIdValid(structure) || !exists(structure))
        {
            sendSystemMessage(player, SID_FOUND_NO_MINES);
            return;
        }
        float radius = hq.getMinefieldRadius(structure);
        radius += 5f;
        if (getDistance(player, minefield) > radius)
        {
            sendSystemMessage(player, SID_FOUND_NO_MINES);
            return;
        }
        int currentMines = hq.getTotalMines(structure);
        if (currentMines <= 0)
        {
            sendSystemMessage(player, SID_FOUND_NO_MINES);
            return;
        }
        int maxMines = hq.getMaxMines(structure);
        if (maxMines <= 0)
        {
            sendSystemMessage(player, SID_FOUND_NO_MINES);
            return;
        }
        int chance = (int)((((float)currentMines / (float)maxMines) * 100f) * 0.75);
        int roll = rand(1, 100);
        if (utils.hasScriptVar(player, "mine_found"))
        {
            int time = utils.getIntScriptVar(player, "mine_found");
            if ((getGameTime() - time) < 120)
            {
                roll -= 50;
                utils.removeScriptVar(player, "mine_found");
            }
        }
        if (roll < chance)
        {
            int disarm_roll = rand(1, 100);
            int result = -1;
            if (roll < 15)
            {
                result = 0;
            }
            else if (roll < 50)
            {
                result = 1;
            }
            else 
            {
                result = 2;
            }
            dictionary d = new dictionary();
            d.put("player", player);
            d.put("minefield", minefield);
            d.put("hq", structure);
            d.put("result", result);
            if (result > -1)
            {
                playClientEffectLoc(player, DISARM_EFFECT[result], getLocation(player), 0f);
            }
            messageTo(self, "handleDisarmMine", d, 2f, false);
        }
        else 
        {
            sendSystemMessage(player, SID_FOUND_NO_MINES);
        }
    }
    public int handleDisarmMine(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id minefield = params.getObjId("minefield");
        obj_id structure = params.getObjId("hq");
        int result = params.getInt("result");
        if (!isIdValid(player) || !isIdValid(minefield) || !isIdValid(structure))
        {
            sendSystemMessage(player, SID_FOUND_NO_MINES);
            return SCRIPT_CONTINUE;
        }
        if (result == 0)
        {
            sendSystemMessage(player, SID_ACCIDENTAL_DETONATE);
            dictionary d = new dictionary();
            d.put("player", player);
            d.put("hq", structure);
            messageTo(minefield, "handleAccidentalMineDetonation", d, 0f, false);
            utils.removeScriptVar(player, "mine_found");
        }
        else if (result == 1)
        {
            sendSystemMessage(player, SID_COULD_NOT_DISARM);
            utils.setScriptVar(player, "mine_found", getGameTime());
        }
        else if (result == 2)
        {
            sendSystemMessage(player, SID_SUCCESSFUL_DISARM);
            hq.getRandomMineType(structure);
            utils.removeScriptVar(player, "mine_found");
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        int count = getCount(self);
        count--;
        if (count <= 0)
        {
            destroyObject(self);
        }
        else 
        {
            setCount(self, count);
        }
        return SCRIPT_CONTINUE;
    }
}
