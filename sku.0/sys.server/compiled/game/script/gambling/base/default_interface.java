package script.gambling.base;

import script.*;
import script.library.gambling;
import script.library.prose;
import script.library.utils;

public class default_interface extends script.terminal.base.base_terminal
{
    public default_interface()
    {
    }
    private static final float BET_RANGE = 15f;
    private static final string_id MNU_JOIN = new string_id(gambling.STF_INTERFACE, "mnu_join");
    private static final string_id MNU_LEAVE = new string_id(gambling.STF_INTERFACE, "mnu_leave");
    private static final string_id STOP_GAMBLING = new string_id(gambling.STF_INTERFACE, "stop_gambling");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!hasObjVar(self, gambling.VAR_GAMBLE_BASE))
        {
            return SCRIPT_CONTINUE;
        }
        int max = getIntObjVar(self, gambling.VAR_TABLE_PLAYER_LIMIT_MAX);
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
        if (players == null || players.length == 0)
        {
            if (utils.hasScriptVar(player, gambling.SCRIPTVAR_GAMBLING))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU2, MNU_LEAVE);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            if (utils.getElementPositionInArray(players, player) > -1)
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU2, MNU_LEAVE);
                return SCRIPT_CONTINUE;
            }
            if (max > 0)
            {
                if (players.length >= max)
                {
                    sendSystemMessageProse(player, prose.getPackage(gambling.PROSE_STATION_FULL, self));
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (!utils.hasScriptVar(player, gambling.SCRIPTVAR_GAMBLING))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_JOIN);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!hasObjVar(self, gambling.VAR_GAMBLE_BASE))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!utils.hasScriptVar(player, gambling.SCRIPTVAR_GAMBLING))
            {
                item = menu_info_types.SERVER_MENU1;
            }
            else 
            {
                item = menu_info_types.SERVER_MENU2;
            }
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (getTotalMoney(player) < 1)
            {
                sendSystemMessage(player, gambling.SID_PLAYER_BROKE);
                return SCRIPT_CONTINUE;
            }
            queueCommand(player, (-214075413), self, "", COMMAND_PRIORITY_DEFAULT);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            queueCommand(player, (947362646), self, "", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdJoinGame(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        gambling.addTablePlayer(self, target, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdLeaveGame(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(target, "isGambling"))
        {
            obj_id table = utils.getObjIdScriptVar(target, "isGambling");
            if (isIdValid(table))
            {
                if (table != self)
                {
                    if (table.isLoaded())
                    {
                        gambling.removeTablePlayer(table, target, params);
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    gambling.removeTablePlayer(self, target, params);
                    return SCRIPT_CONTINUE;
                }
            }
            sendSystemMessage(target, STOP_GAMBLING);
            utils.removeScriptVar(target, "isGambling");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdPlaceBet(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (getDistance(getLocation(self), getLocation(target)) > BET_RANGE)
        {
            sendSystemMessage(target, new string_id(gambling.STF_INTERFACE, "bet_failed_distance"));
            return SCRIPT_CONTINUE;
        }
        gambling.placeBet(self, target, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdBetFailed(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (getDistance(getLocation(self), getLocation(target)) > BET_RANGE)
        {
            sendSystemMessage(target, new string_id(gambling.STF_INTERFACE, "bet_failed_distance"));
        }
        dictionary d = new dictionary();
        d.put("player", target);
        messageTo(self, "handleBetFailed", d, 0f, false);
        return SCRIPT_CONTINUE;
    }
}
