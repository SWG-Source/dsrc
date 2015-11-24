package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;
import script.library.static_item;
import script.library.storyteller;
import script.library.create;
import script.library.trial;

public class destructible_prop_token extends script.base_script
{
    public destructible_prop_token()
    {
    }
    public static final int MIN_HEALTH = 180000;
    public static final int MAX_HEALTH = 250000;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        string_id eliteConversionPrompt = new string_id("storyteller", "deploy_destuctible_maker");
        mi.addRootMenu(menu_info_types.ITEM_USE, eliteConversionPrompt);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_from_inventory_only"));
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_no_trial_accounts"));
            return SCRIPT_CONTINUE;
        }
        obj_id target = getStorytellerTokenTarget(player);
        if (item == menu_info_types.ITEM_USE)
        {
            if (isIdValid(target))
            {
                if (isTargetMyStorytellerObject(player, target))
                {
                    if (!isInvulnerable(target))
                    {
                        sendSystemMessage(player, new string_id("storyteller", "destrucible_prop_already_destroyable"));
                        return SCRIPT_CONTINUE;
                    }
                    if (!hasObjVar(target, "storyteller.canBeDestroyable"))
                    {
                        sendSystemMessage(player, new string_id("storyteller", "destrucible_prop_cannot_be_destroyable"));
                        return SCRIPT_CONTINUE;
                    }
                    setInvulnerable(target, false);
                    pvpSetAttackableOverride(target, true);
                    string_id message = new string_id("storyteller", "destrucible_prop_made_destructible");
                    prose_package pp = prose.getPackage(message, player, player);
                    prose.setTO(pp, utils.unpackString(getName(target)));
                    sendSystemMessageProse(player, pp);
                    storyteller.handleTokenUsage(self);
                    String logMsg = "(" + player + ")" + getName(player) + " is converting a storyteller propr into a destructible prop: " + getStaticItemName(self) + " on " + target;
                    CustomerServiceLog("storyteller", logMsg);
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("storyteller", "invalid_target"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getStorytellerTokenTarget(obj_id player) throws InterruptedException
    {
        obj_id target = null;
        obj_id intendedTarget = getIntendedTarget(player);
        if (isIdValid(intendedTarget))
        {
            target = intendedTarget;
        }
        else 
        {
            target = getLookAtTarget(player);
        }
        return target;
    }
    public boolean isTargetMyStorytellerObject(obj_id player, obj_id target) throws InterruptedException
    {
        if (storyteller.isAnyStorytellerItem(target))
        {
            if (hasObjVar(target, "storytellerid"))
            {
                obj_id storytellerId = getObjIdObjVar(target, "storytellerid");
                if (storytellerId == player || storytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant"))
                {
                    if (!isMob(target) && !isPlayer(target))
                    {
                        return true;
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("storyteller", "destrucible_prop_on_prop_only"));
                        return false;
                    }
                }
            }
        }
        sendSystemMessage(player, new string_id("storyteller", "converter_cannot_use"));
        return false;
    }
}
