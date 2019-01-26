package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.library.instance;
import script.library.mustafar;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class obiwan_exit_object extends script.base_script
{
    public obiwan_exit_object()
    {
    }
    public static final string_id SID_LAST_QUEST = new string_id(mustafar.STF_OBI_MSGS, "obiwan_quest_almost_complete");
    public static final string_id SID_LAUNCH = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_launch");
    public static final string_id SID_EJECT = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_eject");
    public static final string_id SID_LEAVE_YOUR_GROUP = new string_id(mustafar.STF_OBI_MSGS, "obi_leave_your_group");
    public static final String TRIGGER_VOLUME_OBI = "obiwan_interest_volume";
    public static final float OBI_INTEREST_RADIUS = 8.0f;
    public static final string_id SID_SLAVER_CAMP = new string_id("som/som_item", "obiwan_finale_exit_stone");
    public static final string_id SID_LAIR_CRYSTAL = new string_id("travel/zone_transition", "lair_of_the_crystal");
    public static final string_id SID_NO_PERMISSION = new string_id("travel/zone_transition", "default_no_access");
    public static final boolean CONST_FLAG_DO_LOGGING = true;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        item.addRootMenu(menu_info_types.ITEM_USE, SID_SLAVER_CAMP);
        if (getDistance(player, self) > 6.0f)
        {
            return SCRIPT_CONTINUE;
        }
        utils.dismountRiderJetpackCheck(player);
        item.addRootMenu(menu_info_types.ITEM_USE, SID_EJECT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            instance.requestExitPlayer("obiwan_crystal_cave", player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_launch_instance_object/" + section, message);
        }
    }
}
