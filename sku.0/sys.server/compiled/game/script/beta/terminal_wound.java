package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class terminal_wound extends script.base_script
{
    public terminal_wound()
    {
    }
    public static final string_id SID_INFLICT_DAMAGE = new string_id("wound_terminal", "inflict_damage");
    public static final string_id SID_INFLICT_WOUND = new string_id("wound_terminal", "inflict_wound");
    public static final string_id SID_INFLICT_WOUND_HEALTH = new string_id("wound_terminal", "heal_wound_health");
    public static final string_id SID_HEAL_BATTLE = new string_id("wound_terminal", "heal_battle_fatigue");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_INFLICT_DAMAGE);
        mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_INFLICT_WOUND);
        mi.addRootMenu(menu_info_types.SERVER_MENU3, SID_INFLICT_WOUND_HEALTH);
        mi.addRootMenu(menu_info_types.SERVER_MENU4, SID_HEAL_BATTLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isGod(player) || hasObjVar(player, "beta.terminal_ok"))
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                addAttribModifier(player, HEALTH, -500, 0.0f, 0.0f, MOD_POOL);
            }
            if (item == menu_info_types.SERVER_MENU4)
            {
                healShockWound(player, 1000);
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Only authorized users may access this terminal.");
            return SCRIPT_CONTINUE;
        }
    }
}
