package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.healing;
import script.library.consumable;
import script.library.sui;
import script.library.money;
import script.library.utils;

public class player_dispenser extends script.base_script
{
    public player_dispenser()
    {
    }
    public static final String VAR_DATATABLE = "dispenser.datatable";
    public static final string_id SID_INVALID_SELECTION = new string_id("dispenser", "invalid_selection");
    public static final string_id SID_PURCHASE_COMPLETE = new string_id("dispenser", "purchase_complete");
    public static final string_id SID_INSUFFICIENT_FUNDS = new string_id("dispenser", "insufficient_funds");
    public static final string_id SID_INVENTORY_FULL = new string_id("dispenser", "inventory_full");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "player.player_dispenser");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "dispenser"))
        {
            removeObjVar(self, "dispenser");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgMedicinePurchaseSelected(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseSelected");
        int row_selected = getSelection(self, params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary row = dataTableGetRow("datatables/dispenser/medicine.iff", row_selected);
        if (row == null)
        {
            sendSystemMessage(self, SID_INVALID_SELECTION);
            return SCRIPT_CONTINUE;
        }
        obj_id dispenser = getObjIdObjVar(self, "dispenser.dispenser");
        if (dispenser == null)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseSelected -- dispenser is null for " + self);
            sendSystemMessage(self, SID_INVALID_SELECTION);
            return SCRIPT_CONTINUE;
        }
        obj_id inv = getObjectInSlot(self, "inventory");
        if (inv == null || inv == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseSelected -- can't find inventory for " + self);
            return SCRIPT_CONTINUE;
        }
        int free_space = getVolumeFree(inv);
        if (free_space < 1)
        {
            sendSystemMessage(self, SID_INVENTORY_FULL);
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put("purchase", row);
        money.pay(self, money.ACCT_DISPENSER, 5, "msgMedicinePurchaseConfirmed", d, true);
        return SCRIPT_CONTINUE;
    }
    public int msgMedicinePurchaseConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "player.player_dispenser");
        dictionary d = params.getDictionary("purchase");
        if (d == null)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseConfirmed -- purchase data for " + self + " is null.");
            return SCRIPT_CONTINUE;
        }
        String template = d.getString("template");
        String attribute = d.getString("attribute");
        int value = d.getInt("value");
        int charges = d.getInt("charges");
        obj_id inv = getObjectInSlot(self, "inventory");
        obj_id medicine = createObject(template, inv, "");
        if (medicine == null)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseConfirmed -- unable to create medicine for " + self);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(medicine, consumable.VAR_CONSUMABLE_BASE))
        {
            removeObjVar(medicine, consumable.VAR_CONSUMABLE_BASE);
        }
        Vector am = new Vector();
        am.setSize(0);
        if (attribute.equals("damage"))
        {
            attrib_mod tmp = new attrib_mod(0, 0, 0.0f, 0.0f, 0.0f);
            for (int i = 0; i < 3; i++)
            {
                tmp = utils.createHealDamageAttribMod(i * 3, value);
                am = utils.addElement(am, tmp);
            }
        }
        else 
        {
            int attrib_type = healing.stringToAttribute(attribute.toUpperCase());
            if (attrib_type == -1)
            {
                LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseConfirmed -- illegal attribute type for " + self);
                return SCRIPT_CONTINUE;
            }
            attrib_mod tmp = new attrib_mod(0, 0, 0.0f, 0.0f, 0.0f);
            tmp = utils.createHealWoundAttribMod(attrib_type, value);
            am = utils.addElement(am, tmp);
        }
        setObjVar(medicine, consumable.VAR_CONSUMABLE_MODS, am);
        setObjVar(medicine, consumable.VAR_CONSUMABLE_MEDICINE, true);
        setCount(medicine, charges);
        int[] stomach = 
        {
            0,
            0,
            0
        };
        setObjVar(medicine, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
        String[] skill_mod = 
        {
            "healing_ability"
        };
        setObjVar(medicine, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
        int[] skill_min = 
        {
            5
        };
        setObjVar(medicine, consumable.VAR_SKILL_MOD_MIN, skill_min);
        LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseConfirmed -- " + medicine + " created for " + self);
        sendSystemMessage(self, SID_PURCHASE_COMPLETE);
        return SCRIPT_CONTINUE;
    }
    public int msgDispenserPurchaseSelected(obj_id self, dictionary params) throws InterruptedException
    {
        int row_selected = getSelection(self, params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String datatable = getStringObjVar(self, VAR_DATATABLE);
        if (datatable == null)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgDispenserPurchaseSelected -- datatable is null for " + self);
            return SCRIPT_CONTINUE;
        }
        dictionary row = dataTableGetRow(datatable, row_selected);
        if (row == null)
        {
            sendSystemMessage(self, SID_INVALID_SELECTION);
            return SCRIPT_CONTINUE;
        }
        obj_id dispenser = getObjIdObjVar(self, "dispenser.dispenser");
        if (dispenser == null)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgDispenserPurchaseSelected -- dispenser is null for " + self);
            sendSystemMessage(self, SID_INVALID_SELECTION);
            return SCRIPT_CONTINUE;
        }
        obj_id inv = getObjectInSlot(self, "inventory");
        if (inv == null || inv == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseSelected -- can't find inventory for " + self);
            return SCRIPT_CONTINUE;
        }
        int free_space = getVolumeFree(inv);
        if (free_space < 1)
        {
            sendSystemMessage(self, SID_INVENTORY_FULL);
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put("purchase", row);
        money.pay(self, money.ACCT_DISPENSER, 5, "msgDispenserPurchaseConfirmed", d, true);
        return SCRIPT_CONTINUE;
    }
    public int msgDispenserPurchaseConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_dispenser::msgDispenserPurchaseConfirmed -- params " + params);
        detachScript(self, "player.player_dispenser");
        dictionary d = params.getDictionary("purchase");
        if (d == null)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgDispenserPurchaseConfirmed -- purchase data for " + self + " is null.");
            return SCRIPT_CONTINUE;
        }
        String template = d.getString("template");
        obj_id inv = getObjectInSlot(self, "inventory");
        obj_id item = createObject(template, inv, "");
        if (item == null)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgDispenserPurchaseConfirmed -- unable to create item for " + self);
            return SCRIPT_CONTINUE;
        }
        LOG("LOG_CHANNEL", "player_dispenser::msgDispenserPurchaseConfirmed -- " + item + " created for " + self);
        sendSystemMessage(self, SID_PURCHASE_COMPLETE);
        return SCRIPT_CONTINUE;
    }
    public int msgPurchaseFailed(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, SID_INSUFFICIENT_FUNDS);
        return SCRIPT_CONTINUE;
    }
    public int getSelection(obj_id player, dictionary params) throws InterruptedException
    {
        obj_id dispenser = getObjIdObjVar(player, "dispenser.dispenser");
        if (dispenser == null || dispenser == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "player_dispenser::getSelection -- dispenser is null for " + player);
            detachScript(player, "player.player_dispenser");
            return -1;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            detachScript(player, "player.player_dispenser");
            return -1;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected < 0)
        {
            detachScript(player, "player.player_dispenser");
        }
        if (!hasObjVar(player, VAR_DATATABLE))
        {
            detachScript(player, "player.player_dispenser");
            return -1;
        }
        String datatable = getStringObjVar(player, VAR_DATATABLE);
        int num_items = dataTableGetNumRows(datatable);
        LOG("LOG_CHANNEL", "row_selected ->" + row_selected + " num_items ->" + num_items);
        if (row_selected >= num_items)
        {
            detachScript(player, "player.player_dispenser");
            return -1;
        }
        return row_selected;
    }
}
