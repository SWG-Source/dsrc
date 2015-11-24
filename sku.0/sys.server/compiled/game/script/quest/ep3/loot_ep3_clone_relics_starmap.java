package script.quest.ep3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;
import script.library.features;
import script.library.groundquests;

public class loot_ep3_clone_relics_starmap extends script.base_script
{
    public loot_ep3_clone_relics_starmap()
    {
    }
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
        if (item == menu_info_types.SERVER_MENU1 || item == menu_info_types.ITEM_USE)
        {
            grantQuest(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void grantQuest(obj_id player, obj_id self) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || !isIdValid(self) || !exists(self))
        {
            return;
        }
        if (groundquests.isQuestActiveOrComplete(player, "ep3_clone_relics_nym_starmap_1") && !groundquests.hasCompletedQuest(player, "ep3_clone_relics_nym_starmap_4"))
        {
            sendSystemMessage(player, "You must complete the quest series before it can be reset", null);
            return;
        }
        int currentTime = getCalendarTime();
        int starmapTimer = getIntObjVar(player, "ep3.starmapTimer");
        if (starmapTimer > currentTime && !isGod(player))
        {
            sendSystemMessage(player, "You cannot restart this quest for another " + utils.formatTimeVerbose(starmapTimer - currentTime), null);
            return;
        }
        else if (starmapTimer > currentTime && isGod(player))
        {
            sendSystemMessage(player, "Bypassing timer due to god mode. Please say 'Thank you god mode for saving me  " + utils.formatTimeVerbose(starmapTimer - currentTime) + "'", null);
        }
        if (groundquests.isQuestActiveOrComplete(player, "ep3_clone_relics_nym_starmap_1"))
        {
            groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_1");
        }
        if (groundquests.isQuestActiveOrComplete(player, "ep3_clone_relics_nym_starmap_2"))
        {
            groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_2");
        }
        if (groundquests.isQuestActiveOrComplete(player, "ep3_clone_relics_nym_starmap_3"))
        {
            groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_3");
        }
        if (groundquests.isQuestActiveOrComplete(player, "ep3_clone_relics_nym_starmap_4"))
        {
            groundquests.clearQuest(player, "ep3_clone_relics_nym_starmap_4");
        }
        groundquests.grantQuest(player, "ep3_clone_relics_nym_starmap_1");
        int timeUntilReset = secondsUntilNextDailyTime(8, 0, 0);
        int nextReset = timeUntilReset + currentTime;
        setObjVar(player, "ep3.starmapTimer", nextReset);
        destroyObject(self);
        return;
    }
}
