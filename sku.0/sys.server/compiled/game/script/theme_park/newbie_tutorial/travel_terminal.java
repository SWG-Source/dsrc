package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;
import script.library.features;
import script.library.weapons;
import java.util.Vector;
import java.util.Arrays;

public class travel_terminal extends script.theme_park.newbie_tutorial.tutorial_base
{
    public travel_terminal()
    {
    }
    public static final string_id SID_USE_MENU = new string_id("sui", "use");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menuOption = mi.addRootMenu(menu_info_types.ITEM_USE, SID_USE_MENU);
        removeObjVar(player, "banking_bankid");
        removeStaticWaypoint(self);
        if (hasObjVar(self, "newbie.skipped"))
        {
            if (!hasScript(player, NEWBIE_SCRIPT_SKIPPED))
            {
                attachScript(player, NEWBIE_SCRIPT_SKIPPED);
            }
        }
        setLookAtTarget(player, null);
        transferBankToInventory(player);
        obj_id currentWeapon = getCurrentWeapon(player);
        if (hasObjVar(currentWeapon, "newbie.item"))
        {
            destroyObject(currentWeapon);
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id[] contents = getContents(playerInv);
        for (int i = 0; i < contents.length; i++)
        {
            if (isWeapon(contents[i]))
            {
                String templateName = getTemplateName(contents[i]);
                obj_id weapon = weapons.createWeapon(templateName, playerInv, 0.75f);
                equip(weapon, player);
                destroyObject(contents[i]);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasScript(player, NEWBIE_SCRIPT_SKIPPED))
            {
                if (!hasObjVar(player, "newbie.talkedtojedi"))
                {
                    string_id TALK_TO_JEDI = new string_id(NEWBIE_STRING_FILE, "talk_to_jedi");
                    sendSystemMessage(player, TALK_TO_JEDI);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    removeObjVar(player, "newbie.talkedtojedi");
                }
            }
            leaveTutorial(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void leaveTutorial(obj_id self, obj_id player) throws InterruptedException
    {
        removeObjVar(player, "banking_bankid");
        removeStaticWaypoint(self);
        if (hasObjVar(self, "newbie.skipped"))
        {
            if (!hasScript(player, NEWBIE_SCRIPT_SKIPPED))
            {
                attachScript(player, NEWBIE_SCRIPT_SKIPPED);
            }
        }
        setLookAtTarget(player, null);
        transferBankToInventory(player);
        obj_id currentWeapon = getCurrentWeapon(player);
        if (hasObjVar(currentWeapon, "newbie.item"))
        {
            destroyObject(currentWeapon);
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id[] contents = getContents(playerInv);
        for (int i = 0; i < contents.length; i++)
        {
            if (isWeapon(contents[i]))
            {
                String templateName = getTemplateName(contents[i]);
                obj_id weapon = weapons.createWeapon(templateName, playerInv, 0.75f);
                equip(weapon, player);
                destroyObject(contents[i]);
                break;
            }
        }
        sendToMosEisley(player);
    }
    public void sendToMosEisley(obj_id player) throws InterruptedException
    {
        String planet = "tatooine";
        float locX = 3528.0f + rand(-2.5f, 2.5f);
        float locY = 5.0f;
        float locZ = -4804.0f + rand(-2.5f, 2.5f);
        setYaw(player, 180.0f);
        groundquests.completeQuest(player, "quest/c_newbie_hall_01");
        groundquests.completeQuest(player, "quest/c_newbie_hall_02");
        groundquests.completeQuest(player, "quest/c_newbie_hall_range01");
        groundquests.completeQuest(player, "quest/c_newbie_hall_range02");
        groundquests.completeQuest(player, "quest/c_newbie_hall_03");
        groundquests.completeQuest(player, "quest/c_newbie_hall_jedi");
        newbieTutorialRequest(player, "clientReady");
        attachScript(player, "theme_park.newbie_tutorial.new_player_ribbon");
        warpPlayer(player, planet, locX, locY, locZ, null, 0.0f, 0.0f, 0.0f);
        return;
    }
    public int warp_player_now(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        leaveTutorial(self, player);
        return SCRIPT_CONTINUE;
    }
}
