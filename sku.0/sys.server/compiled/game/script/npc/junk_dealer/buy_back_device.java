package script.npc.junk_dealer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.smuggler;
import script.library.utils;

public class buy_back_device extends script.base_script
{
    public buy_back_device()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String myTemplate = getTemplateName(self);
        obj_id datapad = getContainedBy(self);
        if (!isIdValid(datapad))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getContainedBy(datapad);
        if (myTemplate.equals(smuggler.BUY_BACK_CONTROL_DEVICE_TEMPLATE))
        {
            if (isIdValid(player) && isPlayer(player))
            {
                obj_id playerDatapad = utils.getPlayerDatapad(player);
                if (isIdValid(playerDatapad) && datapad == playerDatapad)
                {
                    if (utils.playerHasHowManyItemByTemplateInDataPad(player, smuggler.BUY_BACK_CONTROL_DEVICE_TEMPLATE, false) > 1)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            sendSystemMessage(player, new string_id("loot_dealer", "no_destroy_buy_back_device"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
