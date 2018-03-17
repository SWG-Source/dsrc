package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class fs_crafting1_comp extends script.base_script
{
    public fs_crafting1_comp()
    {
    }
    public static final String QUEST_OBJVAR = "quest.fs_crafting1";
    public static final String STATUS_OBJVAR = QUEST_OBJVAR + ".status";
    public static final String ANALYZED_OBJVAR = QUEST_OBJVAR + ".analyzed";
    public static final string_id STATUS_OPERATIONAL = new string_id("quest/force_sensitive/fs_crafting", "phase1_status_operational");
    public static final string_id STATUS_DAMAGED = new string_id("quest/force_sensitive/fs_crafting", "phase1_status_damaged");
    public static final string_id STATUS_NOT_CALIBRATED = new string_id("quest/force_sensitive/fs_crafting", "phase1_status_not_calibrated");
    public static final String[] TEMPLATES = 
    {
        "object/tangible/item/quest/force_sensitive/fs_craft_puzzle_config_processor.iff",
        "object/tangible/item/quest/force_sensitive/fs_craft_puzzle_gyro_receiver.iff",
        "object/tangible/item/quest/force_sensitive/fs_craft_puzzle_signal_amp.iff",
        "object/tangible/item/quest/force_sensitive/fs_craft_puzzle_solid_state_array.iff"
    };
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "status";
        if (hasObjVar(self, STATUS_OBJVAR))
        {
            names[idx] = "status";
            int status = getIntObjVar(self, STATUS_OBJVAR);
            if (status == 1)
            {
                attribs[idx] = " " + localize(STATUS_OPERATIONAL);
            }
            else if (status == -1)
            {
                attribs[idx] = " " + localize(STATUS_DAMAGED);
            }
            else 
            {
                attribs[idx] = " " + localize(STATUS_NOT_CALIBRATED);
            }
        }
        else 
        {
            attribs[idx] = " " + localize(STATUS_NOT_CALIBRATED);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (isCrafted(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isIdValid(owner))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(self);
        int index = -1;
        for (int i = 0; i < TEMPLATES.length; i++)
        {
            if (template.equals(TEMPLATES[i]))
            {
                index = i;
            }
        }
        if (index == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(owner, STATUS_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        int[] status = getIntArrayObjVar(owner, STATUS_OBJVAR);
        if (status == null || status.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(owner, ANALYZED_OBJVAR))
        {
            int[] analyzed = getIntArrayObjVar(owner, ANALYZED_OBJVAR);
            if (analyzed == null || analyzed.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
            if (analyzed[index] == 0)
            {
                status[index] = -1;
                setObjVar(owner, STATUS_OBJVAR, status);
            }
        }
        else 
        {
            status[index] = -1;
            setObjVar(owner, STATUS_OBJVAR, status);
        }
        return SCRIPT_CONTINUE;
    }
}
