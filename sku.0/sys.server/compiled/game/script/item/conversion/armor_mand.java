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

public class armor_mand extends script.base_script
{
    public armor_mand()
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
            if (hasObjVar(self, "armor.mandDeconstruct"))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("craft_armor_ingredients_d", "dismantle_mand"));
            }
            else 
            {
                detachScript(self, "item.conversion.armor_mand");
            }
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
        String prompt = "Are you sure you wish to DESTROY this piece of armor and create a Mandalorian armor schematic used in the constrution of Mandalorian armor?";
        String title = "Dismantle Mandalorian Armor Piece";
        closeOldWindow(player);
        int pid = sui.msgbox(self, player, prompt, sui.OK_CANCEL, title, 0, "handleConfirmationSelect");
        setWindowPid(player, pid);
    }
    public void dismantleMand(obj_id player) throws InterruptedException
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
            if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_helmet.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_helmet_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_chest_plate.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_chest_plate_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_leggings.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_leggings_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_bicep_l.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_bicep_l_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_bicep_r.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_bicep_r_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_bracer_l.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_bracer_l_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_bracer_r.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_bracer_r_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_gloves.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_gloves_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_shoes.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_boots_schematic.iff";
            }
            else if (templatename.equals("object/tangible/wearables/armor/mandalorian/armor_mandalorian_belt.iff"))
            {
                schematicTemplate = "object/tangible/loot/loot_schematic/death_watch_mandalorian_belt_schematic.iff";
            }
            if (schematicTemplate.length() < 1)
            {
                CustomerServiceLog("armor_converion", "%TU attempted to dismantle Mandalorian " + templatename + " however was not found in schematic listing", player);
                return;
            }
            obj_id newSchematic = createObject(schematicTemplate, pInv, "");
            if (isIdValid(newSchematic))
            {
                destroyObject(self);
                setCraftedId(newSchematic, newSchematic);
                setCrafter(newSchematic, player);
                sendSystemMessage(player, new string_id("quest_armorsmith", "schematic_issued"));
            }
            else 
            {
                CustomerServiceLog("armor_converion", "Server attempted to create Mandalorian " + newSchematic + " for %TU but failed", player);
                return;
            }
        }
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "mand_armor.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "mand_armor.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "mand_armor.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "mand_armor.pid", pid);
        }
    }
    public int handleConfirmationSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, "mand_armor.pid");
            return SCRIPT_CONTINUE;
        }
        dismantleMand(player);
        return SCRIPT_CONTINUE;
    }
}
