package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class terminal_medicine extends script.base_script
{
    public terminal_medicine()
    {
    }
    public static final string_id SID_ORDER_DAMAGE_MEDICINE = new string_id("sui", "order_damage_medicine");
    public static final string_id SID_ORDER_WOUND_MEDICINE = new string_id("sui", "order_wound_medicine");
    public static final String VAR_CAN_USE_TERMINAL = "terminal.can_use_terminal";
    public static final int VAR_TERMINAL_TIME = 1;
    public static final String[] TEMPLATES = 
    {
        "object/tangible/medicine/medpack_wound_health.iff",
        "object/tangible/medicine/medpack_wound_constitution.iff",
        "object/tangible/medicine/medpack_wound_action.iff",
        "object/tangible/medicine/medpack_wound_stamina.iff"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCanUseTerminal(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canUseTerminal(self))
        {
            mi.addRootMenu(menu_info_types.SERVER_HEAL_DAMAGE, SID_ORDER_DAMAGE_MEDICINE);
            mi.addRootMenu(menu_info_types.SERVER_HEAL_WOUND, SID_ORDER_WOUND_MEDICINE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isGod(player) || hasObjVar(player, "beta.terminal_ok"))
        {
            if (item == menu_info_types.SERVER_HEAL_DAMAGE)
            {
                if (!canUseTerminal(self))
                {
                    sendSystemMessageTestingOnly(player, "You must wait for the terminal to recharge.");
                    LOG("LOG_CHANNEL", "You must wait for the terminal to recharge.");
                    return SCRIPT_CONTINUE;
                }
                obj_id inv = getObjectInSlot(player, "inventory");
                createObject("object/tangible/medicine/medpack_damage.iff", inv, "");
                setCanUseTerminal(self, false);
                dictionary dctMsg = new dictionary();
                messageTo(self, "msgMedicineTerminal", dctMsg, VAR_TERMINAL_TIME, false);
            }
            if (item == menu_info_types.SERVER_HEAL_WOUND)
            {
                if (!canUseTerminal(self))
                {
                    sendSystemMessageTestingOnly(player, "You must wait for the terminal to recharge.");
                    LOG("LOG_CHANNEL", "You must wait for the terminal to recharge.");
                    return SCRIPT_CONTINUE;
                }
                obj_id inv = getObjectInSlot(player, "inventory");
                int idx = rand(0, TEMPLATES.length - 1);
                obj_id medicine = createObject(TEMPLATES[idx], inv, "");
                setCanUseTerminal(self, false);
                dictionary dctMsg = new dictionary();
                messageTo(self, "msgMedicineTerminal", dctMsg, VAR_TERMINAL_TIME, false);
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Only authorized users may access this terminal.");
            return SCRIPT_CONTINUE;
        }
    }
    public boolean setCanUseTerminal(obj_id terminal, boolean toggle) throws InterruptedException
    {
        if (terminal == null)
        {
            return false;
        }
        if (!hasObjVar(terminal, VAR_CAN_USE_TERMINAL))
        {
            setObjVar(terminal, VAR_CAN_USE_TERMINAL, 1);
        }
        if (toggle == true)
        {
            setObjVar(terminal, VAR_CAN_USE_TERMINAL, 1);
        }
        else 
        {
            setObjVar(terminal, VAR_CAN_USE_TERMINAL, 0);
        }
        return true;
    }
    public boolean canUseTerminal(obj_id terminal) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "canUseTerminal called.");
        if (terminal == null)
        {
            return false;
        }
        if (!hasObjVar(terminal, VAR_CAN_USE_TERMINAL))
        {
            setObjVar(terminal, VAR_CAN_USE_TERMINAL, 1);
        }
        if (getIntObjVar(terminal, VAR_CAN_USE_TERMINAL) == 0)
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public int msgMedicineTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        setCanUseTerminal(self, true);
        return SCRIPT_CONTINUE;
    }
}
