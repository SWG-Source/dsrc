package script.item.conversion;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class bounty_hunter extends script.base_script
{
    public bounty_hunter()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("craft_armor_ingredients_d", "dismantle_bounty_hunter"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                showConfirmationWindow(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showConfirmationWindow(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Are you sure you wish to DESTROY this piece of wearable armor and create a piece used for the construction of Mandalorian armor?";
        String title = "Dismantle Bounty Hunter Armor Piece";
        closeOldWindow(player);
        int pid = sui.msgbox(self, player, prompt, sui.OK_CANCEL, title, 0, "handleConfirmationSelect");
        setWindowPid(player, pid);
    }
    public void dismantleBountyHunter(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String templatename = getTemplateName(self);
        String schematicTemplate = "";
        obj_id pInv = utils.getInventoryContainer(player);
        if (getVolumeFree(pInv) <= 1)
        {
            sendSystemMessage(player, new string_id("quest_armorsmith", "inventory_full"));
            return;
        }
        else 
        {
            if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_helmet.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_helmet.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_chest_plate.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_chest_plate.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_leggings.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_leggings.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_bicep_l.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_bicep_l.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_bicep_r.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_bicep_r.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_bracer_l.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_bracer_l.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_bracer_r.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_bracer_r.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_gloves.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_gloves.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_boots.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_boots.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_crafted_belt.iff"))
            {
                schematicTemplate = "object/tangible/wearables/armor/bounty_hunter/armor_bounty_hunter_belt.iff";
            }
            if (schematicTemplate.length() < 1)
            {
                CustomerServiceLog("armor_converion", "%TU attempted to dismantle Bounty Hunter " + templatename + " however was not found in schematic listing", player);
                return;
            }
            obj_id newSchematic = createObject(schematicTemplate, pInv, "");
            if (isIdValid(newSchematic))
            {
                destroyObject(self);
                setCraftedId(newSchematic, newSchematic);
                setCrafter(newSchematic, player);
                sendSystemMessage(player, new string_id("quest_armorsmith", "bounty_hunter_converted"));
            }
            else 
            {
                CustomerServiceLog("armor_converion", "Server attempted to create Bounty Hunter " + newSchematic + " for %TU but failed", player);
                return;
            }
        }
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "bounty_hunter_armor.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "bounty_hunter_armor.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "bounty_hunter_armor.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "bounty_hunter_armor.pid", pid);
        }
    }
    public int handleConfirmationSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, "bounty_hunter_armor.pid");
            return SCRIPT_CONTINUE;
        }
        dismantleBountyHunter(player);
        return SCRIPT_CONTINUE;
    }
}
