package script.systems.event_perk;

import script.*;
import script.library.buff;
import script.library.factions;
import script.library.utils;

public class flag_game_flag extends script.base_script
{
    public flag_game_flag()
    {
    }
    public static final String[] RESTRICTED_BUFFS = 
    {
        "invis_forceCloak",
        "cover",
        "paralyze",
        "paralyze_1",
        "stasis",
        "feign_death"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setStandbyMode", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "event_perk.mom");
        int gameState = utils.getIntScriptVar(mom, "event_perk.game_state");
        int playerFactionId = pvpGetAlignedFaction(player);
        String playerFaction = factions.getFactionNameByHashCode(playerFactionId);
        obj_id ownerId = getObjIdObjVar(self, "event_perk.owner");
        if (player == ownerId && (gameState == 0 || gameState == 4))
        {
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("event_perk", "flag_game_set_time_root"));
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU2, new string_id("event_perk", "flag_game_set_time_10"));
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU3, new string_id("event_perk", "flag_game_set_time_20"));
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, new string_id("event_perk", "flag_game_set_time_30"));
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU6, new string_id("event_perk", "flag_game_set_time_60"));
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU7, new string_id("event_perk", "flag_game_start"));
            int menu2 = mi.addRootMenu(menu_info_types.SERVER_MENU9, new string_id("event_perk", "flag_game_show_scores"));
            int menu10 = mi.addRootMenu(menu_info_types.SERVER_MENU10, new string_id("event_perk", "mnu_redeed"));
            int menu5 = mi.addRootMenu(menu_info_types.SERVER_MENU5, new string_id("event_perk", "mnu_show_exp_time"));
            return SCRIPT_CONTINUE;
        }
        if ((gameState > 0 && gameState < 4) && canUseMe(player, self))
        {
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU8, new string_id("event_perk", "flag_game_capture_flag"));
            return SCRIPT_CONTINUE;
        }
        if (gameState == 4 && canUseMe(player, self))
        {
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU9, new string_id("event_perk", "flag_game_show_scores"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        dictionary params = new dictionary();
        obj_id mom = getObjIdObjVar(self, "event_perk.mom");
        if (item == menu_info_types.SERVER_MENU2)
        {
            params.put("timeLimit", 40);
            messageTo(mom, "setTimeLimit", params, 1, false);
            sendSystemMessage(player, new string_id("event_perk", "flag_game_set_time_10"));
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            params.put("timeLimit", 80);
            messageTo(mom, "setTimeLimit", params, 1, false);
            sendSystemMessage(player, new string_id("event_perk", "flag_game_set_time_20"));
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            params.put("timeLimit", 120);
            messageTo(mom, "setTimeLimit", params, 1, false);
            sendSystemMessage(player, new string_id("event_perk", "flag_game_set_time_30"));
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            params.put("timeLimit", 240);
            messageTo(mom, "setTimeLimit", params, 1, false);
            sendSystemMessage(player, new string_id("event_perk", "flag_game_set_time_60"));
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            messageTo(mom, "startGame", null, 1, false);
        }
        if (item == menu_info_types.SERVER_MENU8 && canUseMe(player, self))
        {
            params.put("player", player);
            messageTo(mom, "tryToSwapFlags", params, 1, false);
        }
        if (item == menu_info_types.SERVER_MENU9)
        {
            params.put("player", player);
            messageTo(mom, "showScores", params, 1, false);
        }
        if (item == menu_info_types.SERVER_MENU10)
        {
            params.put("player", player);
            messageTo(mom, "tryRedeeding", params, 1, false);
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            params.put("player", player);
            messageTo(mom, "showTimeTillExpiration", params, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int setStandbyMode(obj_id self, dictionary params) throws InterruptedException
    {
        int gameState = 0;
        utils.setScriptVar(self, "event_perk.game_state", gameState);
        return SCRIPT_CONTINUE;
    }
    public int goDie(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public boolean canUseMe(obj_id player, obj_id self) throws InterruptedException
    {
        String ownerName = getStringObjVar(self, "event_perk.owner_name");
        float playerTerminalRegistration = getFloatObjVar(player, "event_perk.terminal_registration");
        float myTerminalRegistration = getFloatObjVar(self, "event_perk.terminal_registration");
        int gameState = utils.getIntScriptVar(self, "event_perk.game_state");
        int playerFactionId = pvpGetAlignedFaction(player);
        String playerFaction = factions.getFactionNameByHashCode(playerFactionId);
        int pvpType = pvpGetType(player);
        for (String restrictedBuff : RESTRICTED_BUFFS) {
            if (buff.hasBuff(player, restrictedBuff)) {
                return false;
            }
        }
        if (pvpType == PVPTYPE_DECLARED)
        {
            if ((gameState == 2 && playerFaction.equals("Imperial")) || (gameState == 3 && playerFaction.equals("Rebel")))
            {
                return false;
            }
            else 
            {
                return true;
            }
        }
        return false;
    }
}
