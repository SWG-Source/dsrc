package script.cureward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class cureward extends script.base_script
{
    public cureward()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (getConfigSetting("GameServer", "combatUpgradeReward") == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!createRewards(self))
        {
            messageTo(self, "handleRetryRewardNextLogin", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (getConfigSetting("GameServer", "combatUpgradeReward") == null)
        {
            detachScript(self, "cureward.cureward");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean createRewards(obj_id self) throws InterruptedException
    {
        int bornOnDate = getPlayerBirthDate(self);
        if (bornOnDate > 1579)
        {
            return true;
        }
        String cuRewardOption = getConfigSetting("GameServer", "combatUpgradeReward");
        obj_id reward = createObjectInInventoryAllowOverload("object/tangible/event_perk/frn_loyalty_award_plaque_silver.iff", self);
        if (!isIdValid(reward))
        {
            return false;
        }
        if (cuRewardOption.equals("2"))
        {
            reward = createObjectInInventoryAllowOverload("object/tangible/event_perk/frn_loyalty_award_plaque_gold.iff", self);
            if (!isIdValid(reward))
            {
                return false;
            }
        }
        return true;
    }
    public int handleRetryRewardNextLogin(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "cureward.cureward");
        return SCRIPT_CONTINUE;
    }
}
