package script.theme_park.dungeon.nova_orion_station;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.township;
import script.library.trial;
import script.library.static_item;
import script.library.chat;

public class nova_orion_vendor extends script.base_script
{
    public nova_orion_vendor()
    {
    }
    public static final String VENDOR_TABLE_DIRECTORY = "datatables/item/vendor/";
    public static final String VENDOR_TABLE_OBJVAR = "item.vendor.vendor_table";
    public static final String VENDOR_CONTAINER_TEMPLATE = "object/tangible/container/vendor/npc_only.iff";
    public static final String OBJECT_FOR_SALE_CASH_COST = "item.object_for_sale.cash_cost";
    public static final String OBJECT_FOR_SALE_RESOURCE_COST = "item.object_for_sale.resource_cost";
    public static final String VENDOR_CONTAINER_LIST_OBJVAR = "item.vendor.container_list";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeVendor", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeVendor", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleInitializeVendor(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, VENDOR_TABLE_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        String vendorTable = VENDOR_TABLE_DIRECTORY + getStringObjVar(self, VENDOR_TABLE_OBJVAR) + ".iff";
        obj_id[] containerList = new obj_id[10];
        obj_id vendorInventory = utils.getInventoryContainer(self);
        int numRowsInTable = dataTableGetNumRows(vendorTable);
        if (numRowsInTable < 1)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < numRowsInTable; i++)
        {
            int reqClass = dataTableGetInt(vendorTable, i, "class");
            if (reqClass > -1 && reqClass < containerList.length && !isIdValid(containerList[reqClass]) && !exists(containerList[reqClass]))
            {
                containerList[reqClass] = createObjectInInventoryAllowOverload(VENDOR_CONTAINER_TEMPLATE, self);
            }
        }
        for (int row = 0; row < numRowsInTable; row++)
        {
            int reqClass = dataTableGetInt(vendorTable, row, "class");
            String item = dataTableGetString(vendorTable, row, "item");
            int creditCost = dataTableGetInt(vendorTable, row, "cash");
            int resourceCost = dataTableGetInt(vendorTable, row, "resourceAmount");
            if (reqClass > -1 && reqClass < containerList.length)
            {
                for (int idx = 0; idx < containerList.length; idx++)
                {
                    if (isIdValid(containerList[idx]) && exists(containerList[idx]) && (reqClass == 0 || reqClass == idx))
                    {
                        obj_id objectForSale = static_item.createNewItemFunction(item, containerList[idx]);
                        setObjVar(objectForSale, OBJECT_FOR_SALE_CASH_COST, creditCost);
                        setObjVar(objectForSale, OBJECT_FOR_SALE_RESOURCE_COST, resourceCost);
                        String datatableObjectScript = dataTableGetString(vendorTable, row, "script");
                        if (datatableObjectScript != null && datatableObjectScript.length() > 0)
                        {
                            attachScript(objectForSale, datatableObjectScript);
                        }
                        else 
                        {
                            attachScript(objectForSale, township.NOVA_ORION_OBJECT_FOR_SALE_SCRIPT);
                        }
                        if (hasObjVar(self, township.OBJVAR_NOVA_ORION_FACTION))
                        {
                            String requiredNovaOrionFaction = getStringObjVar(self, township.OBJVAR_NOVA_ORION_FACTION);
                            setObjVar(objectForSale, township.OBJVAR_NOVA_ORION_FACTION, requiredNovaOrionFaction);
                        }
                    }
                }
            }
        }
        setObjVar(self, VENDOR_CONTAINER_LIST_OBJVAR, containerList);
        chat.chat(self, "I'm open for business");
        return SCRIPT_CONTINUE;
    }
    public int showInventorySUI(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int profession = utils.getPlayerProfession(player);
        if (!hasObjVar(self, VENDOR_CONTAINER_LIST_OBJVAR))
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_not_qualified"));
            return SCRIPT_CONTINUE;
        }
        obj_id[] containerList = getObjIdArrayObjVar(self, VENDOR_CONTAINER_LIST_OBJVAR);
        obj_id container = null;
        if (isIdValid(containerList[profession]) && exists(containerList[profession]))
        {
            container = containerList[profession];
        }
        else 
        {
            container = containerList[0];
        }
        if (!isIdValid(container) || !exists(container))
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_not_qualified"));
            return SCRIPT_CONTINUE;
        }
        queueCommand(player, (1880585606), container, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int showNonClassInventory(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id[] containerList = getObjIdArrayObjVar(self, VENDOR_CONTAINER_LIST_OBJVAR);
        obj_id container = containerList[0];
        if (!isIdValid(container) || !exists(container))
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_not_qualified"));
            return SCRIPT_CONTINUE;
        }
        queueCommand(player, (1880585606), container, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
}
