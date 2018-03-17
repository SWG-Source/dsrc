package script.systems.skills.performance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.performance;
import script.library.utils;

public class dance_prop extends script.base_script
{
    public dance_prop()
    {
    }
    public static final string_id SID_NO_PROP_SKILL = new string_id("error_message", "prop_no_prop_skill");
    public static final string_id SID_NO_DUAL_SKILL = new string_id("error_message", "prop_no_dual_skill");
    public static final string_id SID_NOT_IN_COMBAT = new string_id("error_message", "prop_not_in_combat");
    public static final String PROP_DATATABLE = "datatables/tangible/prop_datatable.iff";
    public static final String TEMPLATE_COLUMN = "template";
    public static final String COMMAND_COLUMN = "command";
    public static final String HAND_COLUMN = "hand";
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!isIdValid(destContainer) || !isPlayer(destContainer))
        {
            return SCRIPT_CONTINUE;
        }
        if (getState(destContainer, STATE_COMBAT) == 1)
        {
            sendSystemMessage(destContainer, SID_NOT_IN_COMBAT);
            return SCRIPT_OVERRIDE;
        }
        String template = getTemplateName(self);
        int row = dataTableSearchColumnForString(template, TEMPLATE_COLUMN, PROP_DATATABLE);
        if (row < 0)
        {
            return SCRIPT_OVERRIDE;
        }
        dictionary dict = dataTableGetRow(PROP_DATATABLE, row);
        String command = dict.getString(COMMAND_COLUMN);
        String hand = dict.getString(HAND_COLUMN);
        if (!hasCommand(destContainer, command) && !performance.hasInspirationDancePropUseBuff(destContainer, command))
        {
            if (!utils.isProfession(destContainer, utils.ENTERTAINER) && !hasObjVar(self, "allProfs"))
            {
                sendSystemMessage(destContainer, SID_NO_PROP_SKILL);
                return SCRIPT_OVERRIDE;
            }
            if (!hasObjVar(self, "nocert") && !hasCommand(destContainer, command))
            {
                sendSystemMessage(destContainer, SID_NO_PROP_SKILL);
                return SCRIPT_OVERRIDE;
            }
        }
        String otherHand = "hold_l";
        if (hand.equals("l"))
        {
            otherHand = "hold_r";
        }
        obj_id otherHandObj = getObjectInSlot(destContainer, otherHand);
        if (isIdValid(otherHandObj))
        {
            if (!hasCommand(destContainer, "prop_dual_wield"))
            {
                sendSystemMessage(destContainer, SID_NO_DUAL_SKILL);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
