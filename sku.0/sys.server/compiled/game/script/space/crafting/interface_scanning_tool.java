package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.temp_schematic;
import script.library.space_crafting;
import script.library.create;
import script.library.static_item;

public class interface_scanning_tool extends script.base_script
{
    public interface_scanning_tool()
    {
    }
    public static final string_id SCAN_CONTENT = new string_id("spam", "scan_di");
    public static final string_id START_SCAN = new string_id("sui", "start_scan");
    public static final string_id SCAN_NOTHING = new string_id("sui", "scan_nothing");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.hasLocalVar(self, "ctsBeingUnpacked"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isGameObjectTypeOf(item, GOT_ship_component_droid_interface))
        {
            string_id errormessage2 = new string_id(space_crafting.STF_COMPONENT_TOOL, "wrong_component_type");
            sendSystemMessage(transferer, errormessage2);
            return SCRIPT_OVERRIDE;
        }
        if (space_crafting.isValidShipComponent(item) == false)
        {
            string_id errormessage2 = new string_id(space_crafting.STF_COMPONENT_TOOL, "wrong_component_type");
            sendSystemMessage(transferer, errormessage2);
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(item, "ship_comp.flags"))
        {
            int flags = getIntObjVar(item, "ship_comp.flags");
            boolean isBitSet = (flags & ship_component_flags.SCF_reverse_engineered) != 0;
            if (isBitSet == true)
            {
                string_id errormessage = new string_id(space_crafting.STF_COMPONENT_TOOL, "already_engineered");
                sendSystemMessage(transferer, errormessage);
                return SCRIPT_OVERRIDE;
            }
        }
        int level = space_crafting.getReverseEngineeringLevel(item);
        if (level <= 0)
        {
            sendSystemMessage(transferer, "This item does not have a valid Reverse Engineering level.", null);
            return SCRIPT_OVERRIDE;
        }
        obj_id[] toolContents = getContents(self);
        if (toolContents.length < 0)
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "reverse_engineering.charges"))
        {
            int value = getIntObjVar(self, "reverse_engineering.charges");
            names[idx] = "charges";
            attribs[idx] = Integer.toString(value);
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "reverse_engineering.charges"))
        {
            return SCRIPT_CONTINUE;
        }
        int charges = getIntObjVar(self, "reverse_engineering.charges");
        if (charges > 0)
        {
            int lootList = mi.addRootMenu(menu_info_types.ITEM_USE, SCAN_CONTENT);
            if (lootList > -1 && ((getContainedBy(self) != getOwner(self)) || isGod(player)))
            {
                String template = utils.getTemplateFilenameNoPath(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        boolean giveFiresprayPiece = false;
        obj_id newComponent = null;
        if (item == menu_info_types.ITEM_USE)
        {
            int charges = getIntObjVar(self, "reverse_engineering.charges");
            if (charges <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id[] toolContents = getContents(self);
            obj_id playerInv = utils.getInventoryContainer(player);
            String temp = "";
            if (toolContents == null || toolContents.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            if (toolContents[0] == null)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < toolContents.length; i++)
            {
                int level = space_crafting.getReverseEngineeringLevel(toolContents[i]);
                String componentType = space_crafting.getShipComponentStringType(toolContents[i]);
                if (!componentType.equals("droid_interface"))
                {
                    return SCRIPT_CONTINUE;
                }
                string_id compName = getNameStringId(toolContents[i]);
                sendSystemMessage(player, "The tool start scanning the " + localize(compName) + "...", null);
                int lootChance = 5 + (level / 2);
                int lootRoll = rand(1, 100);
                destroyObject(toolContents[i]);
                if (lootChance >= lootRoll)
                {
                    int roll = rand(1, 6);
                    if (roll < 6)
                    {
                        sendSystemMessage(player, "You find a detailed flight plan for a transport convoy, stored in the droid interface memory.", null);
                    }
                    if (roll == 1)
                    {
                        static_item.createNewItemFunction("item_interdiction_data_disk_01", playerInv);
                    }
                    if (roll == 2)
                    {
                        static_item.createNewItemFunction("item_interdiction_data_disk_02", playerInv);
                    }
                    if (roll == 3)
                    {
                        static_item.createNewItemFunction("item_interdiction_data_disk_03", playerInv);
                    }
                    if (roll == 4)
                    {
                        static_item.createNewItemFunction("item_interdiction_data_disk_04", playerInv);
                    }
                    if (roll == 5)
                    {
                        static_item.createNewItemFunction("item_interdiction_data_disk_05", playerInv);
                    }
                    if (roll == 6)
                    {
                        sendSystemMessage(player, "You find a rare droid command stored in the memory of the droid interface.", null);
                        int rollChip = rand(1, 3);
                        if (rollChip == 1)
                        {
                            obj_id moduleImpDS = createObject("object/tangible/droid/droid_space_memory_module_1.iff", playerInv, "");
                            setObjVar(moduleImpDS, "programSize", 1);
                            setObjVar(moduleImpDS, "strDroidCommand", "droidcommand_zonetoimperialdeepspace");
                            setName(moduleImpDS, "");
                            setName(moduleImpDS, new string_id("space/droid_commands", "droidcommand_zonetoimperialdeepspace_chipname"));
                        }
                        if (rollChip == 2)
                        {
                            obj_id moduleRebDS = createObject("object/tangible/droid/droid_space_memory_module_1.iff", playerInv, "");
                            setObjVar(moduleRebDS, "programSize", 1);
                            setObjVar(moduleRebDS, "strDroidCommand", "droidcommand_zonetorebeldeepspace");
                            setName(moduleRebDS, "");
                            setName(moduleRebDS, new string_id("space/droid_commands", "droidcommand_zonetorebeldeepspace_chipname"));
                        }
                        if (rollChip == 3)
                        {
                            obj_id moduleKessel = createObject("object/tangible/droid/droid_space_memory_module_1.iff", playerInv, "");
                            setObjVar(moduleKessel, "programSize", 1);
                            setObjVar(moduleKessel, "strDroidCommand", "droidcommand_zonetokessel");
                            setName(moduleKessel, "");
                            setName(moduleKessel, new string_id("space/droid_commands", "droidcommand_zonetokessel_chipname"));
                        }
                    }
                }
                else 
                {
                    sendSystemMessage(player, "You find no useable data traces in the memory of the droid interface.", null);
                }
            }
            if (!hasObjVar(self, "reverse_engineering.charges"))
            {
                return SCRIPT_CONTINUE;
            }
            charges--;
            if (charges <= 0)
            {
                obj_id[] toolContents2 = getContents(self);
                if (toolContents2.length <= 0)
                {
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
            }
            setObjVar(self, "reverse_engineering.charges", charges);
        }
        return SCRIPT_CONTINUE;
    }
}
