package script.item.loot_schematic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.static_item;
import script.library.township;
import script.library.utils;

public class loot_schematic_nonlimited extends script.base_script
{
    public loot_schematic_nonlimited()
    {
    }
    public static final String VAR_SCHEMATIC = "loot_schematic.schematic";
    public static final String VAR_SKILL = "loot_schematic.skill";
    public static final String VAR_SKILL_REQ = "loot_schematic.skill_req";
    public static final String VAR_TYPE = "loot_schematic.type";
    public static final string_id SID_LEARN_SCHEMATIC = new string_id("loot_schematic", "learn_schematic");
    public static final string_id SID_SCHEMATIC = new string_id("loot_schematic", "schematic");
    public static final string_id SID_NOT_ENOUGH_SKILL = new string_id("loot_schematic", "not_enough_skill");
    public static final string_id SID_ALREADY_HAVE_SCHEMATIC = new string_id("loot_schematic", "already_have_schematic");
    public static final string_id SID_MUST_BE_HOLDING = new string_id("loot_schematic", "must_be_holding");
    public static final string_id SID_SCHEMATIC_GRANTED = new string_id("loot_schematic", "schematic_granted");
    public static final string_id SID_SKILL = new string_id("loot_schematic", "skill");
    public static final string_id SID_ABILITY = new string_id("loot_schematic", "ability");
    public static final string_id SID_WAYPOINT = new string_id("loot_schematic", "waypoint");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, VAR_SCHEMATIC) && !static_item.isStaticItem(self))
        {
            String schemGranted = getStringObjVar(self, VAR_SCHEMATIC);
            if (schemGranted.indexOf("weapon") > -1 && schemGranted.indexOf("component") < 0)
            {
                if (schemGranted.indexOf("appearance") < 0)
                {
                    String template = getTemplateName(self);
                    if (template.length() > 0)
                    {
                        utils.replaceSnowflakeItem(self, template);
                    }
                }
            }
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
        int type = getLootItemType(self);
        if (type < 1)
        {
            return SCRIPT_CONTINUE;
        }
        String[] type_str = 
        {
            localize(SID_SCHEMATIC),
            localize(SID_SKILL),
            localize(SID_ABILITY),
            localize(SID_WAYPOINT),
            localize(SID_ABILITY)
        };
        names[idx] = "knowlege_type";
        attribs[idx] = type_str[type - 1];
        idx++;
        if (hasObjVar(self, VAR_SKILL_REQ))
        {
            String skill_req = getStringObjVar(self, VAR_SKILL_REQ);
            string_id skill_id = utils.unpackString("@skl_n:" + skill_req);
            names[idx] = "loot_schematic_skill_required";
            attribs[idx] = localize(skill_id);
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, township.OBJECT_FOR_SALE_ON_VENDOR))
        {
            return SCRIPT_CONTINUE;
        }
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, SID_LEARN_SCHEMATIC);
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, township.OBJECT_FOR_SALE_ON_VENDOR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id container = getContainedBy(self);
        if (!isIdValid(container))
        {
            sendSystemMessage(player, SID_MUST_BE_HOLDING);
            return SCRIPT_CONTINUE;
        }
        else if (container != getObjectInSlot(player, "inventory"))
        {
            sendSystemMessage(player, SID_MUST_BE_HOLDING);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(self, VAR_SKILL_REQ))
            {
                String skill_req = getStringObjVar(self, VAR_SKILL_REQ);
                if (!hasSkill(player, skill_req))
                {
                    string_id skill_id = utils.unpackString("@skl_n:" + skill_req);
                    prose_package pp = prose.getPackage(SID_NOT_ENOUGH_SKILL, skill_id);
                    sendSystemMessageProse(player, pp);
                    return SCRIPT_CONTINUE;
                }
            }
            String schematic = getStringObjVar(self, VAR_SCHEMATIC);
            if (hasSchematic(player, schematic))
            {
                sendSystemMessage(player, SID_ALREADY_HAVE_SCHEMATIC);
                return SCRIPT_CONTINUE;
            }
            grantSchematic(player, schematic);
            sendSystemMessage(player, SID_SCHEMATIC_GRANTED);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int getLootItemType(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return -1;
        }
        if (hasObjVar(item, VAR_TYPE))
        {
            return getIntObjVar(item, VAR_TYPE);
        }
        else 
        {
            return 1;
        }
    }
}
