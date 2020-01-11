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
    private static final String ITV_COMMAND_TABLE = "datatables/item/itv_command_list.iff";
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
                return SCRIPT_CONTINUE;
            }
            // Make sure the default_itv is always at the bottom of the table else you will get false hits
            int rows = dataTableGetNumRows(ITV_COMMAND_TABLE);
            for (int i=0;i<rows;i++) {
                if (hasObjVar(self, dataTableGetString(ITV_COMMAND_TABLE, i, 0))) {
                    if (hasCommand(player,dataTableGetString(ITV_COMMAND_TABLE, i, 1))) {
                        sendSystemMessageTestingOnly(player, "You have already used this item.");
                        return SCRIPT_CONTINUE;
                    }
                    grantCommand(player, dataTableGetString(ITV_COMMAND_TABLE, i, 1));
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
