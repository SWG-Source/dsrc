package script.item.loot_cube;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.prose;
import script.library.space_crafting;
import script.library.static_item;
import script.library.sui;
import script.library.temp_schematic;
import script.library.utils;
import script.library.xp;

public class republic_assembly_tool extends script.base_script
{
    public republic_assembly_tool()
    {
    }
    public static final int USAGE_DELAY = 15;
    public static final String ITEM_COMBINATION_TABLE = "datatables/item/loot_cube/republic_assembly_tool.iff";
    public static final String ITEM_STF = "item_n";
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public static final String[] BROKEN_COMPONENTS = 
    {
        "object/tangible/component/weapon/mustafar/tulrus_lance_core.iff",
        "object/tangible/component/weapon/mustafar/rifle_coil_dp23.iff",
        "object/tangible/component/weapon/mustafar/rifle_coil_dist.iff",
        "object/tangible/component/weapon/mustafar/pistol_coil_ion.iff",
        "object/tangible/component/weapon/mustafar/pistol_coil_dist.iff",
        "object/tangible/component/weapon/mustafar/obsid_lance_core.iff",
        "object/tangible/component/weapon/mustafar/lava_cannon_coil.iff",
        "object/tangible/component/weapon/mustafar/flamer_coil.iff",
        "object/tangible/component/weapon/mustafar/carbine_coil_sfor.iff",
        "object/tangible/component/weapon/mustafar/2h_tulrus_sword_core.iff",
        "object/tangible/component/weapon/mustafar/2h_obsid_sword_core.iff",
        "object/tangible/component/weapon/mustafar/1h_obsid_sword_core.iff",
        "object/tangible/component/weapon/mustafar/1h_bandit_sword_core.iff",
        "object/tangible/component/structure/mustafar/must_transthermal_padding.iff",
        "object/tangible/component/structure/mustafar/must_synth_creature_hide.iff",
        "object/tangible/component/structure/mustafar/must_soft_light_source.iff",
        "object/tangible/component/structure/mustafar/must_reinforced_support_beam.iff",
        "object/tangible/component/structure/mustafar/must_power_converter.iff",
        "object/tangible/component/structure/mustafar/must_micro_fiber_weave.iff",
        "object/tangible/component/structure/mustafar/must_adhesive_friction_surface.iff"
    };
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        debugLogging("OnReceivedItem", "entered");
        fixBrokenComponents(item);
        double ingredientCRC = 0;
        if (utils.hasScriptVar(self, "ingredientCRC"))
        {
            
        }
        
        {
            ingredientCRC = utils.getDoubleScriptVar(self, "ingredientCRC");
            debugLogging("OnReceivedItem", "found a pre-existing CRC. It was: " + ingredientCRC);
        }
        ingredientCRC = ingredientCRC + getStringCrc(getTemplateName(item));
        debugLogging("OnReceivedItem", "calculating a new CRC. New item was: " + getStringCrc(getTemplateName(item)) + " and new total is: " + ingredientCRC);
        utils.setScriptVar(self, "ingredientCRC", ingredientCRC);
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        debugLogging("OnLostItem", "entered");
        if (isValidId(item) && exists(item))
        {
            String template = getTemplateName(item);
            if (template.startsWith("object/tangible/loot/mustafar/cube_loot/cube_loot_"))
            {
                if (!hasScript(item, "object.autostack"))
                {
                    attachScript(item, "object.autostack");
                }
            }
        }
        double ingredientCRC = utils.getDoubleScriptVar(self, "ingredientCRC");
        ingredientCRC = ingredientCRC - getStringCrc(getTemplateName(item));
        if (ingredientCRC < 0)
        {
            utils.setScriptVar(self, "ingredientCRC", 0);
        }
        else 
        {
            utils.setScriptVar(self, "ingredientCRC", ingredientCRC);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        string_id strSpam = new string_id("item_n", "republic_assembly_tool_menu");
        mi.addRootMenu(menu_info_types.SERVER_MENU1, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        debugLogging("OnObjectMenuSelect", "entered");
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "useTimeStamp"))
        {
            int currentTime = getGameTime();
            int oldTime = utils.getIntScriptVar(self, "useTimeStamp");
            int timeRemaining = currentTime - oldTime;
            if (timeRemaining < USAGE_DELAY)
            {
                string_id strSpam = new string_id("item_n", "republic_assembly_tool_recharging");
                prose_package pp = new prose_package();
                prose.setStringId(pp, strSpam);
                prose.setDI(pp, timeRemaining);
                sendSystemMessageProse(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id clients[] = new obj_id[1];
            clients[0] = player;
            debugLogging("OnObjectMenuSelect", "chose menu option 1");
            utils.setScriptVar(self, "useTimeStamp", getGameTime());
            debugLogging("OnObjectMenuSelect", "activating the assembly tool");
            obj_id newItem = activateAssemblyTool(self, player);
            if (isIdValid(newItem))
            {
                string_id strSuccess = new string_id("item_n", "republic_assembly_tool_successful");
                playClientEffectObj(clients, "sound/item_som_jenha_tar_cube_combine.snd", player, "");
                sendSystemMessage(player, strSuccess);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                debugLogging("OnObjectMenuSelect", "obj_id we got back was invalid: " + newItem);
                string_id strFailure = new string_id("item_n", "republic_assembly_tool_failure");
                playClientEffectObj(clients, "sound/item_electronics_break.snd", player, "");
                sendSystemMessage(player, strFailure);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/republic_assembly_tool/" + section, message);
            debugServerConsoleMsg(null, "@@@@@@@@@@@@@@@@@@@@ " + message);
        }
    }
    public obj_id activateAssemblyTool(obj_id assemblyTool, obj_id objPlayer) throws InterruptedException
    {
        debugLogging("activateAssemblyTool", "entered");
        obj_id generatedItem = null;
        double ingredientCRC = calcCurrentContentsCRC(assemblyTool);
        if (ingredientCRC == 0)
        {
            debugLogging("activateAssemblyTool", "no ingredientCRC. Returning null");
            return null;
        }
        if (!utils.hasScriptVar(assemblyTool, "itemCRCs"))
        {
            debugLogging("activateAssemblyTool", "made the crc table and stored it");
            utils.setScriptVar(assemblyTool, "itemCRCs", makeItemCRCArray());
        }
        double[] itemCRCs = utils.getDoubleArrayScriptVar(assemblyTool, "itemCRCs");
        for (int i = 0; i < itemCRCs.length; i++)
        {
            debugLogging("activateAssemblyTool", "looping through stored CRC array. This is loop number: " + i + " and there will be a total of around: " + itemCRCs.length);
            if (itemCRCs[i] == ingredientCRC)
            {
                debugLogging("activateAssemblyTool", "Found a match ");
                dictionary correctRow = dataTableGetRow(ITEM_COMBINATION_TABLE, i);
                String finalObject = correctRow.getString("finalTemplate");
                String finalScript = correctRow.getString("finalScript");
                String finalName = correctRow.getString("finalName");
                if (finalObject.endsWith(".iff"))
                {
                    generatedItem = createObjectInInventoryAllowOverload(finalObject, objPlayer);
                }
                else 
                {
                    generatedItem = static_item.createNewItemFunction(finalObject, objPlayer);
                }
                debugLogging("activateAssemblyTool", "made a new item. It was a: " + finalObject + " and had obj_id: " + generatedItem);
                obj_id contents[] = getContents(assemblyTool);
                debugLogging("activateAssemblyTool", "Got contents of the assembly tool. There are this many things: ->: " + contents.length);
                if (isIdValid(generatedItem))
                {
                    CustomerServiceLog("mustafar_cube", "%TU has created a '" + finalObject + "' (id: " + generatedItem + ")", objPlayer);
                    for (int j = 0; j < contents.length; j++)
                    {
                        debugLogging("activateAssemblyTool", "Going through each thing in the assembly tool. this is loop #: ->: " + j);
                        if (contents[j] != generatedItem)
                        {
                            debugLogging("activateAssemblyTool", "found something that doesn't match the obj_id of the new item. Destroying it");
                            decrementCount(contents[j]);
                        }
                        if (!finalScript.equals("none"))
                        {
                            attachScript(generatedItem, finalScript);
                        }
                        if (!finalName.equals("none"))
                        {
                            setName(generatedItem, "");
                            int stringCheck = finalName.indexOf("_");
                            if (stringCheck > -1)
                            {
                                setName(generatedItem, new string_id(ITEM_STF, finalName));
                            }
                            else 
                            {
                                setName(generatedItem, finalName);
                            }
                        }
                    }
                    obj_id contentsAgain[] = getContents(assemblyTool);
                    if (contentsAgain.length < 3)
                    {
                        putInOverloaded(generatedItem, assemblyTool);
                    }
                    else 
                    {
                        obj_id pInv = utils.getInventoryContainer(objPlayer);
                        putInOverloaded(generatedItem, pInv);
                    }
                }
            }
        }
        return generatedItem;
    }
    public double[] makeItemCRCArray() throws InterruptedException
    {
        debugLogging("makeItemCRCArray", "entered");
        int numRows = dataTableGetNumRows(ITEM_COMBINATION_TABLE);
        debugLogging("makeItemCRCArray", "there are this many rows in the table -> " + numRows);
        double[] crcs = new double[numRows];
        for (int i = 0; i < numRows; i++)
        {
            debugLogging("makeItemCRCArray", "reading in row number:  " + i);
            dictionary currentRow = dataTableGetRow(ITEM_COMBINATION_TABLE, i);
            for (int j = 1; j <= 3; j++)
            {
                String currentObject = currentRow.getString("item" + j);
                debugLogging("makeItemCRCArray", "currentObject is: " + currentObject + " it is item# " + j);
                int currentItemCRC = getStringCrc(currentObject);
                crcs[i] += currentItemCRC;
            }
        }
        return crcs;
    }
    public double calcCurrentContentsCRC(obj_id assemblyTool) throws InterruptedException
    {
        debugLogging("calcCurrentContentsCRC", "entered");
        double ingredientCRC = 0;
        obj_id contents[] = getContents(assemblyTool);
        debugLogging("calcCurrentContentsCRC", "Got contents of the assembly tool. There are this many things: ->: " + contents.length);
        for (int i = 0; i < contents.length; i++)
        {
            ingredientCRC += getStringCrc(getTemplateName(contents[i]));
            debugLogging("calcCurrentContentsCRC", "Loop Number:  " + i + " and current ingredient CRC total is: " + ingredientCRC);
        }
        return ingredientCRC;
    }
    public void fixBrokenComponents(obj_id item) throws InterruptedException
    {
        String template = getTemplateName(item);
        if (template.startsWith("object/tangible/component/weapon/mustafar/") || template.startsWith("object/tangible/component/structure/mustafar/"))
        {
            for (int i = 0; i < BROKEN_COMPONENTS.length; i++)
            {
                if (template.equals(BROKEN_COMPONENTS[i]))
                {
                    if (!isCrafted(item))
                    {
                        attachScript(item, "item.component.serialize");
                    }
                    break;
                }
            }
        }
    }
}
