package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;

public class devistator_repair extends script.base_script
{
    public devistator_repair()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!trial.isRruDeactivated(self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, trial.WORKING_RRU_ACTIVE);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, trial.WORKING_RRU_INACTIVE);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!trial.isRruDeactivated(self) && hasCurrentInhibitor(player))
            {
                trial.setRruDeactivated(self, true);
                playPowerDown(self);
                utils.sendSystemMessagePob(trial.getTop(self), trial.WORKING_RRU_FALTER);
                messageTo(self, "resetRru", trial.getSessionDict(trial.getTop(self), "devistator_control"), rand(15, 28), false);
            }
            else if (!trial.isRruDeactivated(self) && !hasCurrentInhibitor(player))
            {
                sendSystemMessage(player, trial.WORKING_RRU_OP_CLUE);
            }
            else if (trial.isRruDeactivated(self))
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void playPowerDown(obj_id self) throws InterruptedException
    {
        String effect = "clienteffect/cyborg_itm_powerdown.cef";
        playClientEffectObj(self, effect, self, "");
    }
    public void playPowerUp(obj_id self) throws InterruptedException
    {
        String effect = "clienteffect/amb_biogenics_reactor_bootup.cef";
        playClientEffectObj(self, effect, self, "");
    }
    public boolean hasCurrentInhibitor(obj_id player) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, trial.WORKING_INHIBITOR_TRACKER))
        {
            return false;
        }
        int tracking = utils.getIntScriptVar(player, trial.WORKING_INHIBITOR_TRACKER);
        utils.removeScriptVar(player, trial.WORKING_INHIBITOR_TRACKER);
        return (trial.getSession(trial.getTop(player), "devistator_control") == tracking);
    }
    public int resetRru(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(trial.getTop(self), params, "devistator_control"))
        {
            return SCRIPT_CONTINUE;
        }
        if (trial.isDevistatorKilled(self))
        {
            return SCRIPT_CONTINUE;
        }
        trial.bumpSession(trial.getTop(self), "devistator_control");
        playPowerUp(self);
        trial.setRruDeactivated(self, false);
        utils.sendSystemMessagePob(trial.getTop(self), trial.WORKING_RRU_RECHARGE);
        return SCRIPT_CONTINUE;
    }
}
