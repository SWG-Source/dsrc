package script.systems.veteran_reward;

import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class instant_travel_terminal_deed extends script.base_script
{
    public instant_travel_terminal_deed()
    {
    }
    public static final string_id LEARN_ABILITY = new string_id("item_n", "instant_travel_terminal_learn");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, LEARN_ABILITY);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        // get the setting for minimum ITV level (if its not set, make it the max player level)
        String minLevelSetting = getConfigSetting("GameServer", "itvMinUsageLevel");
        if(minLevelSetting == null) minLevelSetting = "0";

        int minimumLevel = Integer.parseInt(minLevelSetting);
        if (item == menu_info_types.ITEM_USE)
        {
            if(getLevel(player) < minimumLevel){
                sendSystemMessage(player, "Instant Travel vehicles may not be used until you have reached level " + minLevelSetting + ". Please use this deed again when you reach level " + minLevelSetting + " or higher.", null);
            }
            else if (hasObjVar(self, "privateerShip"))
            {
                grantCommand(player, "callforprivateerpickup");
            	destroyObject(self);
            }
            else if (hasObjVar(self, "royalShip"))
            {
                grantCommand(player, "callforroyalpickup");
            	destroyObject(self);
            }
            else if (hasObjVar(self, "junk"))
            {
                grantCommand(player, "callforrattletrappickup");
            	destroyObject(self);
            }
            else if (hasObjVar(self, "tcg_itv_home"))
            {
                grantCommand(player, "callforsolarsailerpickup");
            	destroyObject(self);
            }
            else if (hasObjVar(self, "tcg_itv_location"))
            {
                grantCommand(player, "callforg9riggerpickup");
            	destroyObject(self);
            }
            else if (hasObjVar(self, "itv_snowspeeder"))
            {
                grantCommand(player, "callforsnowspeeder");
                destroyObject(self);
            }
            else if (hasObjVar(self, "itv_slave_1"))
            {
                grantCommand(player, "callforslave1pickup");
                destroyObject(self);
            }
            else
            {
                grantCommand(player, "callforpickup");
            	destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
