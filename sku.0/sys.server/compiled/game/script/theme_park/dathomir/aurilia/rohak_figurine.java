package script.theme_park.dathomir.aurilia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.sui;
import script.library.stealth;
import script.library.utils;

public class rohak_figurine extends script.base_script
{
    public rohak_figurine()
    {
    }
    public static final String FIGURINE_QUEST_NAME = "rohak_token_box";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isMob(self))
        {
            setCondition(self, CONDITION_CONVERSABLE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (isMob(self))
        {
            setCondition(self, CONDITION_CONVERSABLE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        int menu = 0;
        if (isMob(self))
        {
            menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        }
        else 
        {
            menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
        }
        if (hasScript(player, "quest.task.ground.retrieve_item"))
        {
            if (!hasObjVar(self, "figurineTask"))
            {
                return SCRIPT_CONTINUE;
            }
            String figurineTask = getStringObjVar(self, "figurineTask");
            if (groundquests.isTaskActive(player, FIGURINE_QUEST_NAME, figurineTask))
            {
                String menuText = groundquests.getRetrieveMenuText(player, self);
                string_id menuStringId = utils.unpackString(menuText);
                if (isMob(self))
                {
                    menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, menuStringId);
                }
                else 
                {
                    menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, menuStringId);
                }
            }
        }
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        if (menuInfoData != null)
        {
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE || item == menu_info_types.CONVERSE_START)
        {
            if (hasScript(player, "quest.task.ground.retrieve_item"))
            {
                String figurineTask = getStringObjVar(self, "figurineTask");
                if (groundquests.isTaskActive(player, FIGURINE_QUEST_NAME, figurineTask))
                {
                    sendRetrieveObjectFoundMessage(self, player);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (groundquests.hasCompletedTask(player, FIGURINE_QUEST_NAME, figurineTask))
                    {
                        doAnimationAction(self, "thank");
                        sendSystemMessage(player, new string_id("nexus", "figurine_already_used"));
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        doAnimationAction(self, "pose_proudly");
        string_id msg = new string_id("nexus", "figurine_dont_need");
        sendSystemMessage(player, msg);
        return SCRIPT_CONTINUE;
    }
    public void sendRetrieveObjectFoundMessage(obj_id self, obj_id player) throws InterruptedException
    {
        dictionary webster = new dictionary();
        webster.put("source", self);
        messageTo(player, "questRetrieveItemObjectFound", webster, 0, false);
        doAnimationAction(player, "bow");
        doAnimationAction(self, "celebrate");
        sendSystemMessage(player, new string_id("nexus", "figurine_used"));
        if (hasObjVar(self, "questRetrieveSignal"))
        {
            String questRetrieveSignal = getStringObjVar(self, "questRetrieveSignal");
            if (questRetrieveSignal != null && questRetrieveSignal.length() > 0)
            {
                groundquests.sendSignal(player, questRetrieveSignal);
            }
        }
        if (hasObjVar(self, "questFlavorObject"))
        {
            messageTo(self, "handleQuestFlavorObject", null, 0, false);
        }
        return;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "spawner"))
        {
            obj_id spawner = utils.getObjIdScriptVar(self, "spawner");
            if (isIdValid(spawner))
            {
                messageTo(spawner, "figurineDestroyed", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
