package script.quest.crafting_contract;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.utils;

public class crate extends script.base_script
{
    public crate()
    {
    }
    public static final String ITEM_OBJVAR = "contract.item";
    public static final String MAX_NUM_OBJVAR = "contract.max";
    public static final String CURRENT_NUM_OBJVAR = "contract.current";
    public static final String XP_OBJVAR = "contract.xp";
    public static final String COMPLETE_OBJVAR = "contract.complete";
    public static final string_id WRONG_ITEM = new string_id("quest/crafting_contract/system_messages", "wrong_item");
    public static final string_id NOT_CRAFTED = new string_id("quest/crafting_contract/system_messages", "not_crafted");
    public static final string_id NOT_CRAFTER = new string_id("quest/crafting_contract/system_messages", "not_crafter");
    public static final string_id ALREADY_COMPLETE = new string_id("quest/crafting_contract/system_messages", "already_complete");
    public static final string_id CANNOT_REMOVE = new string_id("quest/crafting_contract/system_messages", "cannot_remove");
    public static final string_id JOB_COMPLETE = new string_id("quest/crafting_contract/system_messages", "job_complete");
    public static final string_id ITEMS_REMAINING = new string_id("quest/crafting_contract/system_messages", "items_remaining");
    public static final string_id ATTR_OF = new string_id("quest/crafting_contract/system_messages", "of");
    public static final string_id ATTR_COMPLETE = new string_id("quest/crafting_contract/system_messages", "complete");
    public static final string_id ATTR_REQUIRED = new string_id("quest/crafting_contract/system_messages", "required");
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, ITEM_OBJVAR))
        {
            names[idx] = "item_required";
            String template = getStringObjVar(self, ITEM_OBJVAR);
            String item_name = localize(getNameFromTemplate(template));
            attribs[idx] = " " + item_name;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, MAX_NUM_OBJVAR))
        {
            names[idx] = "job_status";
            if (hasObjVar(self, COMPLETE_OBJVAR))
            {
                attribs[idx] = " " + localize(ATTR_COMPLETE);
            }
            else 
            {
                int max = getIntObjVar(self, MAX_NUM_OBJVAR);
                int current = 0;
                if (hasObjVar(self, CURRENT_NUM_OBJVAR))
                {
                    current = getIntObjVar(self, CURRENT_NUM_OBJVAR);
                }
                attribs[idx] = " " + current + " " + localize(ATTR_OF) + " " + max + " " + localize(ATTR_REQUIRED);
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        String template = getStringObjVar(self, ITEM_OBJVAR);
        if (template == null || template.equals(""))
        {
            return SCRIPT_OVERRIDE;
        }
        String item_template = getTemplateName(item);
        if (!template.equals(item_template))
        {
            sendSystemMessage(transferer, WRONG_ITEM);
            return SCRIPT_OVERRIDE;
        }
        if (!isCrafted(item))
        {
            sendSystemMessage(transferer, NOT_CRAFTED);
            return SCRIPT_OVERRIDE;
        }
        if (transferer != getCrafter(item))
        {
            sendSystemMessage(transferer, NOT_CRAFTER);
            return SCRIPT_OVERRIDE;
        }
        int max_number = getIntObjVar(self, MAX_NUM_OBJVAR);
        int current_number = 0;
        if (hasObjVar(self, CURRENT_NUM_OBJVAR))
        {
            current_number = getIntObjVar(self, CURRENT_NUM_OBJVAR);
        }
        if (current_number >= max_number)
        {
            sendSystemMessage(transferer, ALREADY_COMPLETE);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        int max_number = getIntObjVar(self, MAX_NUM_OBJVAR);
        int current_number = 0;
        if (hasObjVar(self, CURRENT_NUM_OBJVAR))
        {
            current_number = getIntObjVar(self, CURRENT_NUM_OBJVAR);
        }
        current_number++;
        setObjVar(self, CURRENT_NUM_OBJVAR, current_number);
        if (!hasObjVar(self, XP_OBJVAR))
        {
            int xp = getIntObjVar(item, "crafting.creator.xp");
            setObjVar(self, XP_OBJVAR, xp);
        }
        int remaining = max_number - current_number;
        if (remaining <= 0)
        {
            sendSystemMessage(transferer, JOB_COMPLETE);
            setObjVar(self, COMPLETE_OBJVAR, 1);
        }
        else 
        {
            prose_package pp = prose.getPackage(ITEMS_REMAINING, remaining);
            sendSystemMessageProse(transferer, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isGod(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(transferer, CANNOT_REMOVE);
        return SCRIPT_OVERRIDE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        int max_number = getIntObjVar(self, MAX_NUM_OBJVAR);
        int current_number = 0;
        if (hasObjVar(self, CURRENT_NUM_OBJVAR))
        {
            current_number = getIntObjVar(self, CURRENT_NUM_OBJVAR);
        }
        current_number--;
        if (current_number <= 0)
        {
            current_number = 0;
            if (hasObjVar(self, XP_OBJVAR))
            {
                removeObjVar(self, XP_OBJVAR);
            }
        }
        int remaining = max_number - current_number;
        if (remaining > 0)
        {
            if (hasObjVar(self, COMPLETE_OBJVAR))
            {
                removeObjVar(self, COMPLETE_OBJVAR);
            }
        }
        setObjVar(self, CURRENT_NUM_OBJVAR, current_number);
        return SCRIPT_CONTINUE;
    }
}
