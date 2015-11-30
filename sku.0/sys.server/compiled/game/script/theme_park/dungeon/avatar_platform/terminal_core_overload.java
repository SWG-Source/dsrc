package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.player_structure;

public class terminal_core_overload extends script.base_script
{
    public terminal_core_overload()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id INCREASE_POWER = new string_id(STF, "core_overload");
    public static final string_id WARNING_OVERLOAD = new string_id(STF, "warning_overload");
    public static final string_id ALREADY_OVERLOADED = new string_id(STF, "core_overloaded");
    public static final string_id SAFETY_MEASURES = new string_id(STF, "safety_measures");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, INCREASE_POWER);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "ep3_avatar_self_destruct", "overloadCore"))
            {
                startCoreOverload(player, self);
                groundquests.sendSignal(player, "coreOverloaded");
                sendSystemMessage(player, WARNING_OVERLOAD);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.hasCompletedTask(player, "ep3_avatar_self_destruct", "overloadCore"))
            {
                sendSystemMessage(player, ALREADY_OVERLOADED);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, SAFETY_MEASURES);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void startCoreOverload(obj_id player, obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id powercore = getCellId(structure, "powercore");
        location here = getLocation(self);
        String planet = here.area;
        location corePoint = new location(-90.0f, 7.98f, -50.0f, planet, powercore);
        playClientEffectLoc(player, "clienteffect/ep3_avatar_core_overload.cef", corePoint, 7.98f);
        messageTo(self, "handlePowerCoreReplay", null, 60f, false);
        return;
    }
    public int handlePowerCoreReplay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = player_structure.getPlayersInBuilding(getTopMostContainer(self));
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                startCoreOverload(players[i], self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
