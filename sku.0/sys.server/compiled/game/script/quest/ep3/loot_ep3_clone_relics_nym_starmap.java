package script.quest.ep3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.groundquests;
import script.library.utils;
import script.library.permissions;
import script.library.features;

public class loot_ep3_clone_relics_nym_starmap extends script.base_script
{
    public loot_ep3_clone_relics_nym_starmap()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "makeMoreLoot", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id who) throws InterruptedException
    {
        if (!features.hasEpisode3Expansion(who))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_OPEN)
        {
            utils.requestContainerOpen(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isIdValid(destContainer) || !exists(destContainer) || !isIdValid(transferer) || !exists(transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        int currentTime = getCalendarTime();
        int starmapTimer = getIntObjVar(transferer, "ep3.starmapTimer");
        int starmapPickupTimer = getIntObjVar(transferer, "ep3.starmapPickupTimer");
        if (starmapTimer > currentTime && !isGod(transferer))
        {
            sendSystemMessage(transferer, "You cannot pick up this item for another " + utils.formatTimeVerbose(starmapTimer - currentTime), null);
            return SCRIPT_OVERRIDE;
        }
        else if (starmapTimer > currentTime && isGod(transferer))
        {
            sendSystemMessage(transferer, "Bypassing timer due to god mode. Please say 'Thank you god mode for saving me  " + utils.formatTimeVerbose(starmapTimer - currentTime) + "'", null);
        }
        if (groundquests.isQuestActiveOrComplete(transferer, "ep3_clone_relics_nym_starmap_1") && !groundquests.hasCompletedQuest(transferer, "ep3_clone_relics_nym_starmap_4"))
        {
            sendSystemMessage(transferer, "You must complete the quest series before you can pick this item up again.", null);
            return SCRIPT_OVERRIDE;
        }
        if (starmapPickupTimer > currentTime && !isGod(transferer))
        {
            sendSystemMessage(transferer, "You cannot pick up this item for another " + utils.formatTimeVerbose(starmapPickupTimer - currentTime), null);
            return SCRIPT_OVERRIDE;
        }
        int timeUntilReset = secondsUntilNextDailyTime(8, 0, 0);
        int nextReset = timeUntilReset + currentTime;
        setObjVar(transferer, "ep3.starmapPickupTimer", nextReset);
        messageTo(self, "makeMoreLoot", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int makeMoreLoot(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] boxContent = getContents(self);
        if (boxContent == null || boxContent.length == 0)
        {
            createObject("object/tangible/quest/quest_start/ep3_clone_relics_nym_starmap.iff", self, "");
        }
        return SCRIPT_CONTINUE;
    }
}
