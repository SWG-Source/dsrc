package script.systems.encoded_disk.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.locations;
import script.library.utils;
import script.library.scenario;
import script.library.create;
import script.library.ai_lib;

public class message_fragment extends script.base_script
{
    public message_fragment()
    {
    }
    public static final string_id SID_SYS_MESSAGE_ASSEMBLED = new string_id("encoded_disk/message_fragment", "sys_message_assembled");
    public static final string_id SID_SYS_NOT_ALL_PARTS = new string_id("encoded_disk/message_fragment", "sys_not_all_parts");
    public static final string_id SID_SYS_NOT_IN_INV = new string_id("encoded_disk/message_fragment", "sys_not_in_inv");
    public static final string_id SID_SYS_INVENTROY_FULL = new string_id("treasure_map/treasure_map", "sys_inventory_full");
    public static final string_id SID_USE = new string_id("treasure_map/treasure_map", "use");
    public static final String SID_COMBINE = "@encoded_disk/message_fragment:combine";
    public static final String SID_CLOSE = "@treasure_map/treasure_map:close";
    public static final String MESSAGE_TABLE = "datatables/encoded_disk/message_fragment.iff";
    public static final String VAR_INDEX = "messageTableIndex";
    public static final String VAR_PART_NUMBER = "partNumber";
    public static final String ASSEMBLED_MESSAGE = "object/tangible/encoded_disk/message_assembled_base.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int[] weights = dataTableGetIntColumn(MESSAGE_TABLE, "PARTS");
        int[] enabled = dataTableGetIntColumn(MESSAGE_TABLE, "ENABLED");
        int totalWeight = 0;
        for (int i = 0; i < weights.length; i++)
        {
            if (enabled[i] == 1)
            {
                totalWeight += weights[i];
            }
        }
        int choice = rand(1, totalWeight);
        totalWeight = 0;
        dictionary messageEntry = null;
        for (int i = 0; i < weights.length; i++)
        {
            if (enabled[i] == 0)
            {
                continue;
            }
            totalWeight += weights[i];
            if (choice <= totalWeight)
            {
                messageEntry = dataTableGetRow(MESSAGE_TABLE, i);
                messageEntry.put(scenario.DICT_IDX, i);
                break;
            }
        }
        if ((messageEntry == null) || (messageEntry.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = messageEntry.getInt(scenario.DICT_IDX);
        setObjVar(self, VAR_INDEX, idx);
        int parts = messageEntry.getInt("PARTS");
        int partNumber = rand(1, parts);
        setObjVar(self, VAR_PART_NUMBER, partNumber);
        String scenarioName = messageEntry.getString("SCENARIO_NAME");
        string_id nameID = new string_id("encoded_disk/message_fragment", "name_" + scenarioName);
        String name = localize(nameID) + " [" + partNumber + "/" + parts + "]";
        setName(self, name);
        int factionReward = messageEntry.getInt("FACTION_REWARD");
        if (factionReward > 0)
        {
            setObjVar(self, "factionReward", factionReward);
        }
        int badgeIndex = messageEntry.getInt("BADGE_INDEX");
        if (badgeIndex > 0)
        {
            setObjVar(self, "badgeIndex", badgeIndex);
        }
        int specString = messageEntry.getInt("SPECIAL_STRING");
        if (specString == 1)
        {
            setObjVar(self, "specString", specString);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!dataTableOpen(MESSAGE_TABLE))
        {
            return SCRIPT_CONTINUE;
        }
        int index = getIntObjVar(self, VAR_INDEX);
        dictionary messageEntry = dataTableGetRow(MESSAGE_TABLE, index);
        int parts = messageEntry.getInt("PARTS");
        int partNumber = getIntObjVar(self, VAR_PART_NUMBER);
        String scenarioName = messageEntry.getString("SCENARIO_NAME");
        string_id nameID = new string_id("encoded_disk/message_fragment", "name_" + scenarioName);
        String name = localize(nameID) + " [" + partNumber + "/" + parts + "]";
        setName(self, name);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            displayDialog(self, player);
        }
        return SCRIPT_OVERRIDE;
    }
    public void displayDialog(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (!contains(inventory, self))
        {
            sendSystemMessage(player, SID_SYS_NOT_IN_INV);
            return;
        }
        if (!dataTableOpen(MESSAGE_TABLE))
        {
            return;
        }
        dictionary messageEntry = dataTableGetRow(MESSAGE_TABLE, getIntObjVar(self, VAR_INDEX));
        String entryName = messageEntry.getString("SCENARIO_NAME");
        int parts = messageEntry.getInt("PARTS");
        int partNumber = getIntObjVar(self, VAR_PART_NUMBER);
        string_id textID = new string_id("encoded_disk/message_fragment", "text_" + entryName);
        String text = localize(textID);
        string_id titleID = new string_id("encoded_disk/message_fragment", "name_" + entryName);
        String title = localize(titleID) + " [" + partNumber + "/" + parts + "]";
        createDialog(self, player, text, title);
    }
    public int createDialog(obj_id self, obj_id player, String text, String title) throws InterruptedException
    {
        if (player == null)
        {
            return -1;
        }
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "handleDialogInput");
        sui.setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        sui.msgboxButtonSetup(pid, sui.OK_CANCEL);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, SID_COMBINE);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, SID_CLOSE);
        sui.showSUIPage(pid);
        return pid;
    }
    public int handleDialogInput(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_CANCEL:
            assembleMessage(self, player);
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void assembleMessage(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (!contains(inventory, self))
        {
            sendSystemMessage(player, SID_SYS_NOT_IN_INV);
            return;
        }
        if (!dataTableOpen(MESSAGE_TABLE))
        {
            return;
        }
        int index = getIntObjVar(self, VAR_INDEX);
        dictionary messageEntry = dataTableGetRow(MESSAGE_TABLE, index);
        String entryName = messageEntry.getString("SCENARIO_NAME");
        int parts = messageEntry.getInt("PARTS");
        int partNumber = getIntObjVar(self, VAR_PART_NUMBER);
        obj_id[] contents = utils.getContents(inventory, true);
        if (contents == null)
        {
            return;
        }
        obj_id foundparts[] = 
        {
            null,
            null,
            null,
            null,
            null,
            null
        };
        foundparts[partNumber - 1] = self;
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], VAR_INDEX))
            {
                int idx = getIntObjVar(contents[i], VAR_INDEX);
                if (idx == index)
                {
                    int partNum = getIntObjVar(contents[i], VAR_PART_NUMBER);
                    if (foundparts[partNum - 1] == null)
                    {
                        foundparts[partNum - 1] = contents[i];
                    }
                }
            }
        }
        for (int i = 0; i < parts; i++)
        {
            if (foundparts[i] == null)
            {
                sendSystemMessage(player, SID_SYS_NOT_ALL_PARTS);
                return;
            }
        }
        obj_id assembled = createObject(ASSEMBLED_MESSAGE, inventory, "");
        if (isIdValid(assembled))
        {
            sendSystemMessage(player, SID_SYS_MESSAGE_ASSEMBLED);
        }
        else 
        {
            sendSystemMessage(player, SID_SYS_INVENTROY_FULL);
            return;
        }
        string_id nameId = new string_id("encoded_disk/message_fragment", "name_" + entryName);
        setName(assembled, nameId);
        setObjVar(assembled, "scenarioName", entryName);
        String faction = messageEntry.getString("FACTION");
        setObjVar(assembled, "faction", faction);
        if (hasObjVar(self, "specString"))
        {
            setObjVar(assembled, "specString", rand(1, 20));
        }
        if (hasObjVar(self, "factionReward"))
        {
            setObjVar(assembled, "factionReward", getIntObjVar(self, "factionReward"));
        }
        if (hasObjVar(self, "badgeIndex"))
        {
            setObjVar(assembled, "badgeIndex", getIntObjVar(self, "badgeIndex"));
        }
        for (int i = 0; i < parts; i++)
        {
            if (foundparts[i] != null)
            {
                destroyObject(foundparts[i]);
            }
        }
    }
}
