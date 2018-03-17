package script.theme_park.jedi_trials;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.jedi_trials;
import script.library.utils;

public class padawan_trials_search extends script.base_script
{
    public padawan_trials_search()
    {
    }
    public static final String DATATABLE_NAME = "datatables/dungeon/corellian_corvette_quest.iff";
    public static final string_id SEARCH_ITEM = new string_id("bestine", "search_item");
    public static final string_id ALREADY_SEARCHED_MSG = new string_id("jedi_trials", "already_searched");
    public static final string_id SUCCESSFUL_SEARCH_MSG = new string_id("jedi_trials", "successful_search_msg");
    public static final string_id INVENTORY_FULL_MSG = new string_id("bestine", "inventory_full");
    public static final String ALREADY_SEARCHED_OBJVAR = "alreadySearched";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("DestroyNpcVolume", 32.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (isPlayer(breacher))
        {
            if (volumeName.equals("DestroyNpcVolume"))
            {
                obj_id trialPlayer = getObjIdObjVar(self, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
                if (trialPlayer == breacher)
                {
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canSearch(self, player))
        {
            int menuOption = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SEARCH_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            if (canSearch(self, player))
            {
                if (hasObjVar(self, ALREADY_SEARCHED_OBJVAR))
                {
                    sendSystemMessage(player, ALREADY_SEARCHED_MSG);
                }
                else 
                {
                    sendSystemMessage(player, SUCCESSFUL_SEARCH_MSG);
                    setObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01", true);
                    setObjVar(self, ALREADY_SEARCHED_OBJVAR, true);
                    messageTo(player, "handleSetBeginLoc", null, 1, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSearch(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(self, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.acceptedTask");
        }
        return false;
    }
}
