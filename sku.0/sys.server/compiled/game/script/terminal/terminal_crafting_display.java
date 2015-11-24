package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.player_structure;
import script.library.skill;
import script.library.space_utils;
import script.library.space_transition;
import script.library.sui;
import script.library.utils;

public class terminal_crafting_display extends script.base_script
{
    public terminal_crafting_display()
    {
    }
    public static final string_id SID_GRANT_BUFF = new string_id("spam", "assembly_terminal_grant_buff");
    public static final string_id SID_NOT_IN_HOUSE = new string_id("spam", "assembly_terminal_not_in_huse");
    public static final string_id SID_WRONG_CLASS = new string_id("spam", "assembly_terminal_wrong_class");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (checkLocation(self))
        {
            int startDisplay = mi.addRootMenu(menu_info_types.ITEM_USE, SID_GRANT_BUFF);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE && isIdValid(player))
        {
            if (checkLocation(self))
            {
                grantBuff(player);
            }
            else 
            {
                sendSystemMessage(player, SID_NOT_IN_HOUSE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkLocation(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (!utils.isInHouseCellSpace(item))
        {
            return false;
        }
        if (!utils.isNestedWithinAPlayer(item))
        {
            obj_id house = getTopMostContainer(item);
            obj_id ship = space_transition.getContainingShip(item);
            String templateName = "";
            if (isIdValid(ship))
            {
                templateName = getTemplateName(ship);
            }
            if (isIdValid(house) && (player_structure.isBuilding(house) || space_utils.isPobType(templateName)))
            {
                return true;
            }
        }
        return false;
    }
    public void grantBuff(obj_id player) throws InterruptedException
    {
        if (hasSkill(player, "class_domestics_phase1_novice"))
        {
            buff.applyBuff(player, "crafting_display_domestic_buff");
        }
        else if (hasSkill(player, "class_engineering_phase1_novice"))
        {
            buff.applyBuff(player, "crafting_display_engineering_buff");
        }
        else if (hasSkill(player, "class_munitions_phase1_novice"))
        {
            buff.applyBuff(player, "crafting_display_munitions_buff");
        }
        else if (hasSkill(player, "class_structures_phase1_novice"))
        {
            buff.applyBuff(player, "crafting_display_structure_buff");
            buff.applyBuff(player, "crafting_display_shipwright_buff");
        }
        else if (hasSkill(player, "class_forcesensitive_phase1_novice"))
        {
            buff.applyBuff(player, "crafting_display_jedi_buff");
        }
        else 
        {
            sendSystemMessage(player, SID_WRONG_CLASS);
        }
    }
}
