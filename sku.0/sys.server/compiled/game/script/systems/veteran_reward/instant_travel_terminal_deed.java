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
            else if (hasObjVar(self, "privateerShip") && (!hasObjVar(player, "itv.owned.privateer")))
            {
                grantCommand(player, "callforprivateerpickup");
                setObjVar(player, "itv.owned.privateer", 1);
            	destroyObject(self);
            }
            else if (hasObjVar(self, "royalShip") && (!hasObjVar(player, "itv.owned.royalship")))
            {
                grantCommand(player, "callforroyalpickup");
                setObjVar(player, "itv.owned.royalship", 1);
            	destroyObject(self);
            }
            else if (hasObjVar(self, "junk") && (!hasObjVar(player, "itv.owned.junk")))
            {
                grantCommand(player, "callforrattletrappickup");
                setObjVar(player, "itv.owned.junk", 1);
            	destroyObject(self);
            }
            else if (hasObjVar(self, "tcg_itv_home") && (!hasObjVar(player, "itv.owned.solar")))
            {
                grantCommand(player, "callforsolarsailerpickup");
                setObjVar(player, "itv.owned.solar", 1);
            	destroyObject(self);
            }
            else if (hasObjVar(self, "tcg_itv_location") && (!hasObjVar(player, "itv.owned.g9rigger")))
            {
                grantCommand(player, "callforg9riggerpickup");
                setObjVar(player, "itv.owned.g9rigger", 1);
            	destroyObject(self);
            }
            else if (hasObjVar(self, "itv_snowspeeder") && (!hasObjVar(player, "itv.owned.snowspeeder")))
            {
                grantCommand(player, "callforsnowspeeder");
                setObjVar(player, "itv.owned.snowspeeder", 1);
                destroyObject(self);
            }
            else if (hasObjVar(self, "itv_slave_1") && (!hasObjVar(player, "itv.owned.slave1")))
            {
                grantCommand(player, "callforslave1pickup");
                setObjVar(player, "itv.owned.slave1", 1);
                destroyObject(self);
            }
            else if (hasObjVar(self, "default_itv") && (!hasObjVar(player, "itv.owned.callforpickup")))
            {
                grantCommand(player, "callforpickup");
                setObjVar(player, "itv.owned.callforpickup", 1);
            	destroyObject(self);
            }
            else
            {
            	sendSystemMessageTestingOnly(player, "You have already used this item.");
            	return SCRIPT_CONTINUE;
            }	
        }
        return SCRIPT_CONTINUE;
    }
}
