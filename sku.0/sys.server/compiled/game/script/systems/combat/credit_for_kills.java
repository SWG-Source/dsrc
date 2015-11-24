package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.*;
import script.library.xp;
import script.library.bounty_hunter;

public class credit_for_kills extends script.base_script
{
    public credit_for_kills()
    {
    }
    public static final String VERSION = "v1.00.00";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        xp.setupCreditForKills();
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        xp.cleanupCreditForKills();
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        xp.assessCombatResults(self);
        messageTo(self, xp.HANDLER_XP_DELEGATED, null, 0.1f, false);
        xp.applyHealingCredit(self);
        obj_id attacker = getObjIdObjVar(self, xp.VAR_TOP_GROUP);
        if (hasObjVar(self, "bountyCheckPayBracket"))
        {
            bounty_hunter.payBountyCheckReward(attacker, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        xp.assessCombatResults(self);
        messageTo(self, xp.HANDLER_XP_DELEGATED, null, 0.1f, false);
        xp.applyHealingCredit(self);
        return SCRIPT_CONTINUE;
    }
    public int addEnemyHealer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id healer = params.getObjId("healer");
            if (isIdValid(healer))
            {
                deltadictionary scriptVars = self.getScriptVars();
                Vector healers = scriptVars.getResizeableObjIdArray("healers");
                if (healers == null)
                {
                    healers = new Vector();
                }
                if (!healers.contains(healer))
                {
                    healers.add(healer);
                    scriptVars.put("healers", healers);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
