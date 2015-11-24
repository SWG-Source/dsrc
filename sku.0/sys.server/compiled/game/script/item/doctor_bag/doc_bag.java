package script.item.doctor_bag;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.doctor_bag;
import script.library.sui;
import script.library.utils;
import script.library.prose;

public class doc_bag extends script.base_script
{
    public doc_bag()
    {
    }
    public static final string_id SID_EXAMINE_MEDS = new string_id("doctor_bag", "examine_meds");
    public static final string_id SID_REMOVE_MEDS = new string_id("doctor_bag", "remove_meds");
    public static final string_id SID_NOTHING_IN_BAG = new string_id("doctor_bag", "nothing_in_bag");
    public static final string_id SID_MED_ENTRY = new string_id("doctor_bag", "med_entry");
    public static final string_id SID_SET_LABEL = new string_id("doctor_bag", "set_label");
    public static final string_id SID_LABEL_ALREADY = new string_id("doctor_bag", "label_already_assigned");
    public static final string_id SID_NEW_LABEL = new string_id("doctor_bag", "new_label");
    public static final string_id SID_LABEL_NO_SPACES = new string_id("doctor_bag", "label_no_spaces");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        setCount(self, 1);
        mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_REMOVE_MEDS);
        mi.addRootMenu(menu_info_types.SERVER_MENU3, SID_SET_LABEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            displayExamineMeds(player, self);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            displayRemoveMeds(player, self);
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            displayLabelMeds(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void displayRemoveMeds(obj_id player, obj_id self) throws InterruptedException
    {
        int count = 0;
        for (int i = 0; i < doctor_bag.MAX_MEDS; i++)
        {
            if (hasObjVar(self, "meds." + i + ".name"))
            {
                count++;
            }
        }
        int j = 0;
        prose_package[] med_names = new prose_package[count];
        Vector med_index = new Vector();
        med_index.setSize(0);
        for (int i = 0; i < doctor_bag.MAX_MEDS; i++)
        {
            if (hasObjVar(self, "meds." + i + ".name"))
            {
                String med_name = getStringObjVar(self, "meds." + i + ".name");
                String med_label = getStringObjVar(self, "meds." + i + ".label");
                if (med_label == null)
                {
                    med_label = "No Label";
                }
                if (med_name.indexOf(':') > -1)
                {
                    med_name = "@" + med_name;
                }
                prose_package pp = prose.getPackage(SID_MED_ENTRY, med_name, med_label, getIntObjVar(self, "meds." + i + ".count"));
                med_names[j++] = pp;
                med_index = utils.addElement(med_index, i);
            }
        }
        if (med_names.length == 0)
        {
            sendSystemMessage(player, SID_NOTHING_IN_BAG);
            return;
        }
        utils.setScriptVar(self, "med_index", med_index);
        sui.listbox(self, player, "@doctor_bag:remove_med_d", sui.OK_CANCEL, "@doctor_bag:remove_med_t", med_names, "handleRemoveMed", true, false);
    }
    public int handleRemoveMed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int[] med_index = utils.getIntArrayScriptVar(self, "med_index");
        int real_med_index = med_index[idx];
        doctor_bag.removeMedicine(player, self, real_med_index);
        return SCRIPT_CONTINUE;
    }
    public void displayLabelMeds(obj_id player, obj_id self) throws InterruptedException
    {
        int count = 0;
        for (int i = 0; i < doctor_bag.MAX_MEDS; i++)
        {
            if (hasObjVar(self, "meds." + i + ".name"))
            {
                count++;
            }
        }
        int j = 0;
        prose_package[] med_names = new prose_package[count];
        Vector med_index = new Vector();
        med_index.setSize(0);
        for (int i = 0; i < doctor_bag.MAX_MEDS; i++)
        {
            if (hasObjVar(self, "meds." + i + ".name"))
            {
                String med_name = getStringObjVar(self, "meds." + i + ".name");
                String med_label = getStringObjVar(self, "meds." + i + ".label");
                if (med_label == null)
                {
                    med_label = "No Label";
                }
                if (med_name.indexOf(':') > -1)
                {
                    med_name = "@" + med_name;
                }
                prose_package pp = prose.getPackage(SID_MED_ENTRY, med_name, med_label, getIntObjVar(self, "meds." + i + ".count"));
                med_names[j++] = pp;
                med_index = utils.addElement(med_index, i);
            }
        }
        if (med_names.length == 0)
        {
            sendSystemMessage(player, SID_NOTHING_IN_BAG);
            return;
        }
        utils.setScriptVar(self, "med_index", med_index);
        sui.listbox(self, player, "@doctor_bag:label_med_d", sui.OK_CANCEL, "@doctor_bag:label_med_t", med_names, "handleLabelMed", true, false);
    }
    public int handleLabelMed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int[] med_index = utils.getIntArrayScriptVar(self, "med_index");
        int real_med_index = med_index[idx];
        String prefix = "meds." + real_med_index + ".";
        String label = getStringObjVar(self, prefix + "label");
        utils.setScriptVar(self, "med_prefix", prefix);
        sui.inputbox(self, player, "@doctor_bag:label_input_d", "@doctor_bag:label_input_t", "handleSetLabel", label);
        return SCRIPT_CONTINUE;
    }
    public int handleSetLabel(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String new_label = sui.getInputBoxText(params);
        if (new_label == null)
        {
            return SCRIPT_CONTINUE;
        }
        String prefix = utils.getStringScriptVar(self, "med_prefix");
        int whitespace = new_label.indexOf(" ");
        if (whitespace > -1)
        {
            sendSystemMessage(player, SID_LABEL_NO_SPACES);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < doctor_bag.MAX_MEDS; i++)
        {
            if (hasObjVar(self, "meds." + i + ".name"))
            {
                String label = getStringObjVar(self, "meds." + i + ".label");
                if ((label != null) && label.equals(new_label))
                {
                    sendSystemMessage(player, SID_LABEL_ALREADY);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        setObjVar(self, prefix + "label", new_label);
        prose_package pp = prose.getPackage(SID_NEW_LABEL, new_label);
        sendSystemMessageProse(player, pp);
        return SCRIPT_CONTINUE;
    }
    public void displayExamineMeds(obj_id player, obj_id self) throws InterruptedException
    {
        int count = 0;
        for (int i = 0; i < doctor_bag.MAX_MEDS; i++)
        {
            if (hasObjVar(self, "meds." + i + ".name"))
            {
                count++;
            }
        }
        int j = 0;
        prose_package[] med_names = new prose_package[count];
        Vector med_index = new Vector();
        med_index.setSize(0);
        for (int i = 0; i < doctor_bag.MAX_MEDS; i++)
        {
            if (hasObjVar(self, "meds." + i + ".name"))
            {
                String med_name = getStringObjVar(self, "meds." + i + ".name");
                if (med_name.indexOf(':') > -1)
                {
                    med_name = "@" + med_name;
                }
                prose_package pp = prose.getPackage(SID_MED_ENTRY, med_name, getIntObjVar(self, "meds." + i + ".count"));
                med_names[j++] = pp;
                med_index = utils.addElement(med_index, i);
            }
        }
        if (med_names.length == 0)
        {
            sendSystemMessage(player, SID_NOTHING_IN_BAG);
            return;
        }
        utils.setScriptVar(self, "med_index", med_index);
        sui.listbox(self, player, "@doctor_bag:examine_med_d", sui.OK_CANCEL, "@doctor_bag:examine_med_t", med_names, "handleExamineMed", true, false);
    }
    public int handleExamineMed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int[] med_index = utils.getIntArrayScriptVar(self, "med_index");
        int real_med_index = med_index[idx];
        String prefix = "meds." + real_med_index + ".";
        doctor_bag.setSurrogateState(self, prefix);
        doctor_bag.clearSurrogateState(self);
        return SCRIPT_CONTINUE;
    }
}
