package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.mustafar;
import script.library.prose;

public class obiwan_recall_object extends script.base_script
{
    public obiwan_recall_object()
    {
    }
    public static final String STF_OBI_CONVO = "conversation/som_kenobi_obi_wan";
    public static final string_id SID_CALL_OBI = new string_id(mustafar.STF_OBI_MSGS, "recall_obiwan");
    public static final string_id SID_CALLED_OBI = new string_id(mustafar.STF_OBI_MSGS, "recalling_obiwan");
    public static final string_id SID_RECALL_HELLO = new string_id(mustafar.STF_OBI_MSGS, "obi_recall_greeting_01");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (mustafar.canCallObiwan(player, self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_CALL_OBI);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("space_dungeon", "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_recall_object.OnObjectMenuSelect()");
        if (item == menu_info_types.ITEM_USE)
        {
            if (mustafar.canCallObiwan(player, self, true))
            {
                activateObiwanRecaller(player, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void activateObiwanRecaller(obj_id player, obj_id landmark) throws InterruptedException
    {
        obj_id obiwan = mustafar.callObiwan(player, landmark, true);
        int greetingNum = rand(1, 10);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_greet_" + greetingNum);
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        return;
    }
}
