package script.theme_park.dungeon.nova_orion_station;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class retrieve_item_gravestone_inventory extends script.base_script
{
    public retrieve_item_gravestone_inventory()
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
                    location here = getLocation(player);
                    if (here.area.equals("dungeon1") && (getCellName(here.cell)).equals("greenhouse"))
                    {
                        obj_id graveNode = getFirstObjectWithScript(here, 5.0f, "theme_park.dungeon.nova_orion_station.nova_orion_gravestone_node");
                        if (isIdValid(graveNode))
                        {
                            if (utils.hasScriptVar(graveNode, "graveEventInProgress"))
                            {
                                sendSystemMessage(player, new string_id("nexus", "no_finale_event_already_underway"));
                                return SCRIPT_CONTINUE;
                            }
                            else 
                            {
                                dictionary params = new dictionary();
                                params.put("source", self);
                                params.put("player", player);
                                messageTo(player, "questRetrieveItemObjectFound", params, 0, false);
                                messageTo(graveNode, "startNoFinaleEvent", params, 0, false);
                                if (!hasObjVar(self, "doNotDestroyMe"))
                                {
                                    messageTo(self, "destroyMe", null, 1, false);
                                }
                            }
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id("nexus", "no_finale_event_too_far"));
                            return SCRIPT_CONTINUE;
                        }
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("nexus", "no_finale_event_incorrect_location"));
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyMe(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "doNotDestroyMe"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
