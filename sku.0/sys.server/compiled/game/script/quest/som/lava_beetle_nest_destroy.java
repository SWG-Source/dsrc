package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.create;
import script.library.trial;
import script.library.utils;
import script.library.group;

public class lava_beetle_nest_destroy extends script.base_script
{
    public lava_beetle_nest_destroy()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id DESTROY = new string_id(STF, "lava_beetle_nest_destroy");
    public static final string_id NO_EXPLOSIVES = new string_id(STF, "lava_beetle_nest_no_explosives");
    public static final string_id ALREADY_DESTROYED = new string_id(STF, "lava_beetle_nest_destroyed");
    public static final string_id EXIT_AREA = new string_id(STF, "lava_beetle_nest_run_away");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if ((groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy", "lava_beetle_nest_tasks") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy_2", "lava_beetle_nest_tasks")))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, DESTROY);
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null)
            {
                return SCRIPT_CONTINUE;
            }
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(self, "beetle_nest_one"))
            {
                if (utils.hasScriptVar(player, "beetle_nest.destroyed_01"))
                {
                    sendSystemMessage(player, ALREADY_DESTROYED);
                    return SCRIPT_CONTINUE;
                }
                if ((groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy", "destroy_lava_beetle_nest_one") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy_2", "destroy_lava_beetle_nest_one")))
                {
                    sendSystemMessage(player, EXIT_AREA);
                    dictionary time = new dictionary();
                    time.put("timer", 10);
                    messageTo(self, "handleFlyTextTimer", time, 0, false);
                    dictionary dict = new dictionary();
                    dict.put("target", player);
                    messageTo(self, "handleDamageTarget", dict, 10, false);
                    if (group.isGrouped(player))
                    {
                        Vector members = group.getPCMembersInRange(player, 80f);
                        if (members != null && members.size() > 0)
                        {
                            int numInGroup = members.size();
                            if (numInGroup < 1)
                            {
                                return SCRIPT_CONTINUE;
                            }
                            for (int i = 0; i < numInGroup; i++)
                            {
                                obj_id thisMember = ((obj_id)members.get(i));
                                utils.setScriptVar(player, "beetle_nestdestroyed_01", 1);
                                dictionary dict2 = new dictionary();
                                dict2.put("target", thisMember);
                                messageTo(self, "handleDestroyNest", dict2, 10, false);
                            }
                        }
                    }
                    else 
                    {
                        utils.setScriptVar(player, "beetle_nest.destroyed_01", 1);
                        messageTo(self, "handleDestroyNest", dict, 10, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    sendSystemMessage(player, ALREADY_DESTROYED);
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, "beetle_nest_two"))
            {
                if (utils.hasScriptVar(player, "beetle_nest.destroyed_02"))
                {
                    sendSystemMessage(player, ALREADY_DESTROYED);
                    return SCRIPT_CONTINUE;
                }
                if ((groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy", "destroy_lava_beetle_nest_two") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy_2", "destroy_lava_beetle_nest_two")))
                {
                    sendSystemMessage(player, EXIT_AREA);
                    dictionary time = new dictionary();
                    time.put("timer", 10);
                    messageTo(self, "handleFlyTextTimer", time, 0, false);
                    dictionary dict = new dictionary();
                    dict.put("target", player);
                    messageTo(self, "handleDamageTarget", dict, 10, false);
                    if (group.isGrouped(player))
                    {
                        Vector members = group.getPCMembersInRange(player, 80f);
                        if (members != null && members.size() > 0)
                        {
                            int numInGroup = members.size();
                            if (numInGroup < 1)
                            {
                                return SCRIPT_CONTINUE;
                            }
                            for (int i = 0; i < numInGroup; i++)
                            {
                                obj_id thisMember = ((obj_id)members.get(i));
                                utils.setScriptVar(player, "beetle_nestdestroyed_02", 1);
                                dictionary dict2 = new dictionary();
                                dict2.put("target", thisMember);
                                messageTo(self, "handleDestroyNest", dict2, 10, false);
                            }
                        }
                    }
                    else 
                    {
                        utils.setScriptVar(player, "beetle_nest.destroyed_02", 1);
                        messageTo(self, "handleDestroyNest", dict, 10, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    sendSystemMessage(player, ALREADY_DESTROYED);
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, "beetle_nest_three"))
            {
                if (utils.hasScriptVar(player, "beetle_nest.destroyed_03"))
                {
                    sendSystemMessage(player, ALREADY_DESTROYED);
                    return SCRIPT_CONTINUE;
                }
                if ((groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy", "destroy_lava_beetle_nest_three") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy_2", "destroy_lava_beetle_nest_three")))
                {
                    sendSystemMessage(player, EXIT_AREA);
                    dictionary time = new dictionary();
                    time.put("timer", 10);
                    messageTo(self, "handleFlyTextTimer", time, 0, false);
                    dictionary dict = new dictionary();
                    dict.put("target", player);
                    messageTo(self, "handleDamageTarget", dict, 10, false);
                    if (group.isGrouped(player))
                    {
                        Vector members = group.getPCMembersInRange(player, 80f);
                        if (members != null && members.size() > 0)
                        {
                            int numInGroup = members.size();
                            if (numInGroup < 1)
                            {
                                return SCRIPT_CONTINUE;
                            }
                            for (int i = 0; i < numInGroup; i++)
                            {
                                obj_id thisMember = ((obj_id)members.get(i));
                                utils.setScriptVar(player, "beetle_nestdestroyed_03", 1);
                                dictionary dict2 = new dictionary();
                                dict2.put("target", thisMember);
                                messageTo(self, "handleDestroyNest", dict2, 10, false);
                            }
                        }
                    }
                    else 
                    {
                        utils.setScriptVar(player, "beetle_nest.destroyed_03", 1);
                        messageTo(self, "handleDestroyNest", dict, 10, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    sendSystemMessage(player, ALREADY_DESTROYED);
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, "beetle_nest_four"))
            {
                if (utils.hasScriptVar(player, "beetle_nest.destroyed_04"))
                {
                    sendSystemMessage(player, ALREADY_DESTROYED);
                    return SCRIPT_CONTINUE;
                }
                if ((groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy", "destroy_lava_beetle_nest_four") || groundquests.isTaskActive(player, "som_lava_beetle_nest_destroy_2", "destroy_lava_beetle_nest_four")))
                {
                    sendSystemMessage(player, EXIT_AREA);
                    dictionary time = new dictionary();
                    time.put("timer", 10);
                    messageTo(self, "handleFlyTextTimer", time, 0, false);
                    dictionary dict = new dictionary();
                    dict.put("target", player);
                    messageTo(self, "handleDamageTarget", dict, 10, false);
                    if (group.isGrouped(player))
                    {
                        Vector members = group.getPCMembersInRange(player, 80f);
                        if (members != null && members.size() > 0)
                        {
                            int numInGroup = members.size();
                            if (numInGroup < 1)
                            {
                                return SCRIPT_CONTINUE;
                            }
                            for (int i = 0; i < numInGroup; i++)
                            {
                                obj_id thisMember = ((obj_id)members.get(i));
                                utils.setScriptVar(player, "beetle_nestdestroyed_04", 1);
                                dictionary dict2 = new dictionary();
                                dict2.put("target", thisMember);
                                messageTo(self, "handleDestroyNest", dict2, 10, false);
                            }
                        }
                    }
                    else 
                    {
                        utils.setScriptVar(player, "beetle_nest.destroyed_04", 1);
                        messageTo(self, "handleDestroyNest", dict, 10, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    sendSystemMessage(player, ALREADY_DESTROYED);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                sendSystemMessage(player, ALREADY_DESTROYED);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFlyTextTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int timer = params.getInt("timer");
        if (timer == 0)
        {
            return SCRIPT_CONTINUE;
        }
        trial.doCountdownTimerFlyText(self, timer);
        return SCRIPT_CONTINUE;
    }
    public int handleDestroyNest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("target");
        if (hasObjVar(self, "beetle_nest_one"))
        {
            groundquests.sendSignal(player, "lava_beetle_nest_one_destroyed");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "beetle_nest_two"))
        {
            groundquests.sendSignal(player, "lava_beetle_nest_two_destroyed");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "beetle_nest_three"))
        {
            groundquests.sendSignal(player, "lava_beetle_nest_three_destroyed");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "beetle_nest_four"))
        {
            groundquests.sendSignal(player, "lava_beetle_nest_four_destroyed");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDamageTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("target");
        playClientEffectLoc(player, "clienteffect/avatar_hallway_explosion.cef", getLocation(self), 0.0f);
        obj_id[] targets = getValidPlayersInRadius(self, 15);
        if (targets == null || targets.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, 750);
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getValidPlayersInRadius(obj_id self, float range) throws InterruptedException
    {
        obj_id[] players = getPlayerCreaturesInRange(self, range);
        Vector targets = new Vector();
        targets.setSize(0);
        if (players != null && players.length != 0)
        {
            for (int k = 0; k < players.length; k++)
            {
                if (isPlayer(players[k]) && isIdValid(players[k]) && exists(players[k]) && !isIncapacitated(players[k]))
                {
                    utils.addElement(targets, players[k]);
                }
            }
        }
        if (targets == null || targets.size() == 0)
        {
            return null;
        }
        obj_id[] validTargets = new obj_id[0];
        if (targets != null)
        {
            validTargets = new obj_id[targets.size()];
            targets.toArray(validTargets);
        }
        return validTargets;
    }
}
