package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.prose;
import script.library.storyteller;
import script.library.sui;
import script.library.utils;

public class storyteller_vendor extends script.base_script
{
    public storyteller_vendor()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachStorytellerVendorConvo(self);
        messageTo(self, "handlSpecialName", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachStorytellerVendorConvo(self);
        messageTo(self, "handlSpecialName", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handlSpecialName(obj_id self, dictionary params) throws InterruptedException
    {
        if (rand(0, 5) == 1)
        {
            setName(self, "Pex (an elite storyteller vendor)");
        }
        return SCRIPT_CONTINUE;
    }
    public void attachStorytellerVendorConvo(obj_id vendor) throws InterruptedException
    {
        if (!hasScript(vendor, "conversation.storyteller_vendor"))
        {
            attachScript(vendor, "conversation.storyteller_vendor");
        }
        return;
    }
    public int[] convertSecondsTime(int time) throws InterruptedException
    {
        if (time < 1)
        {
            return null;
        }
        int mod_day = time % 86400;
        int mod_hour = mod_day % 3600;
        int days = time / 86400;
        int hours = mod_day / 3600;
        int minutes = mod_hour / 60;
        int seconds = mod_hour % 60;
        int[] converted_time = 
        {
            days,
            hours,
            minutes,
            seconds
        };
        return converted_time;
    }
    public int msgStorytellerTokenTypeSelected(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String scriptvar_path = "storytellerVendor." + player;
        String oldPidVar = scriptvar_path + ".pid";
        if (utils.hasScriptVar(self, oldPidVar))
        {
            utils.removeScriptVar(self, oldPidVar);
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int tokenType = idx;
        storyteller.displayStorytellerTokenPurchaseSUI(player, tokenType, self);
        return SCRIPT_CONTINUE;
    }
    public int msgStorytellerTokenPurchaseSelected(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.storytellerTokenPurchased(params, self);
        return SCRIPT_CONTINUE;
    }
    public int msgStorytellerChargesSelected(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.storytellerSellTokenWithCharges(params, self);
        return SCRIPT_CONTINUE;
    }
}
