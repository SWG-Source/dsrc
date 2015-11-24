package script.theme_park.dathomir.aurilia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.instance;
import script.library.static_item;
import script.library.utils;

public class axkva_nightsister_shards extends script.base_script
{
    public axkva_nightsister_shards()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "getQuestPlayerName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int getQuestPlayerName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id questPlayer = utils.getContainingPlayer(self);
        if (isIdValid(questPlayer))
        {
            setObjVar(self, "questPlayer", questPlayer);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkForActiveShardTasks", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int checkForActiveShardTasks(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "questPlayer"))
        {
            obj_id player = getObjIdObjVar(self, "questPlayer");
            if (!isIdValid(player))
            {
                messageTo(self, "shardCleanup", null, 1, false);
            }
            if (!groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_06") && !groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_07") && !groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_08") && !groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_09"))
            {
                dictionary webster = new dictionary();
                webster.put("player", player);
                messageTo(self, "shardCleanup", webster, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int shardCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id player = params.getObjId("player");
            if (isIdValid(player))
            {
                if (isGod(player))
                {
                    sendSystemMessage(player, "Godmode Message Only: Cleaning up invalid quest item - not on expected quest...", "");
                }
                if (groundquests.hasCompletedTask(player, "axkva_min_intro", "axkva_min_intro_09"))
                {
                    if (!instance.isFlaggedForInstance(player, "heroic_axkva_min"))
                    {
                        instance.flagPlayerForInstance(player, "heroic_axkva_min");
                    }
                }
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (hasScript(player, "quest.task.ground.retrieve_item"))
        {
            if (groundquests.playerNeedsToRetrieveThisItem(player, self))
            {
                String menuText = groundquests.getRetrieveMenuText(player, self);
                string_id menuStringId = utils.unpackString(menuText);
                int menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, menuStringId);
                menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
                menuInfoData.setServerNotify(true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (hasScript(player, "quest.task.ground.retrieve_item"))
        {
            if (groundquests.playerNeedsToRetrieveThisItem(player, self))
            {
                if (item == menu_info_types.ITEM_USE)
                {
                    location here = getWorldLocation(player);
                    String planet = here.area;
                    float distanceCheck = -1;
                    if (groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_07"))
                    {
                        location mtChaoltLoc = new location(2983f, 290f, -4665f, "lok", obj_id.NULL_ID);
                        distanceCheck = getDistance(here, mtChaoltLoc);
                        if (!planet.equals("lok") || distanceCheck > 50.0f || distanceCheck < 0)
                        {
                            sendSystemMessage(player, new string_id("quest/pirates", "axkva_min_intro_07_too_far"));
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_09"))
                    {
                        location blueleafLoc = new location(-829.6f, 48.7f, -2055.5f, "yavin4", obj_id.NULL_ID);
                        distanceCheck = getDistance(here, blueleafLoc);
                        if (!planet.equals("yavin4") || distanceCheck > 12.0f || distanceCheck < 0)
                        {
                            sendSystemMessage(player, new string_id("quest/pirates", "axkva_min_intro_09_too_far"));
                            return SCRIPT_CONTINUE;
                        }
                    }
                    dictionary webster = new dictionary();
                    webster.put("source", self);
                    webster.put("player", player);
                    messageTo(player, "questRetrieveItemObjectFound", webster, 0, false);
                    messageTo(self, "handleEndShardQuest", webster, 1, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleEndShardQuest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            if (groundquests.hasCompletedTask(player, "axkva_min_intro", "axkva_min_intro_09"))
            {
                instance.flagPlayerForInstance(player, "heroic_axkva_min");
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
