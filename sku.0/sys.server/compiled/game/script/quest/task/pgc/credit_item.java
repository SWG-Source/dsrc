package script.quest.task.pgc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.corpse;
import script.library.money;
import script.library.prose;
import script.library.utils;

public class credit_item extends script.base_script
{
    public credit_item()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int credits = getIntObjVar(self, "loot.cashAmount");
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "amount";
        attribs[idx] = Integer.toString(credits);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id objPlayer, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("space/space_loot", "use_credit_chip"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id containingPlayer = utils.getContainingPlayer(self);
        if (!isIdValid(containingPlayer) || containingPlayer != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            int cash = getIntObjVar(self, "loot.cashAmount");
            money.bankTo("pgc_player_donated_credits", player, cash);
            string_id creditsMsg = new string_id("saga_system", "holocron_reward_credits_given");
            prose_package pp = prose.getPackage(creditsMsg, player, player);
            prose.setDI(pp, cash);
            sendSystemMessageProse(player, pp);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
