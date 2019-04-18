package script.quest.som;

import script.*;
import script.library.groundquests;

public class miner_tracking_computer extends script.base_script
{
    public miner_tracking_computer()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id ACTIVATE = new string_id(STF, "tracking_computer_activate");
    public static final string_id WAIT = new string_id(STF, "tracking_computer_working");
    public static final string_id FINISHED = new string_id(STF, "tracking_computer_done");
    public static final string_id SUCCESS = new string_id(STF, "tracking_computer_success");
    public static final string_id SEARCHING = new string_id(STF, "tracking_computer_searching");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "som_poison_miners"))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ACTIVATE);
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null)
            {
                return SCRIPT_CONTINUE;
            }
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "som_poison_miners", "mustafar_poison_miners_two"))
            {
                doAnimationAction(player, "std_manipulate_medium");
                sendSystemMessage(player, SEARCHING);
                groundquests.sendSignal(player, "poison_miners_signal_one");
                playClientEffectObj(self, "clienteffect/survey_effect.cef", self, "");
                dictionary dict = new dictionary();
                dict.put("target", player);
                messageTo(self, "handleMinerTrack", dict, 10.0f, false);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "som_poison_miners", "mustafar_poison_miners_two_b"))
            {
                sendSystemMessage(player, WAIT);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, FINISHED);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMinerTrack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("target");
        sendSystemMessage(player, SUCCESS);
        groundquests.sendSignal(player, "poison_miners_signal_two");
        return SCRIPT_CONTINUE;
    }
}
