package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.combat;
import script.library.utils;
import script.library.attrib;

public class combat_delayed_tracker extends script.systems.combat.combat_base
{
    public combat_delayed_tracker()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objOwner = utils.getObjIdScriptVar(self, "objOwner");
        if (!exists(objOwner))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (isDead(objOwner) || utils.hasScriptVar(self, combat.DIED_RECENTLY))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        dictionary actionDataDictionary = utils.getDictionaryScriptVar(self, "combatDataDict");
        combat_data actionData = combat_engine.getCombatDataFromDictionary(actionDataDictionary);
        if (actionData == null)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        obj_id objTarget = params.getObjId("objTarget");
        boolean boolCleanup = params.getBoolean("boolCleanup");
        String strEffect = params.getString("strEffect");
        if (strEffect != null)
        {
            playClientEffectLoc(objOwner, strEffect, getLocation(self), 0);
        }
        combatStandardAction(actionData.actionName, objOwner, objTarget, self, "", actionData, true);
        if (utils.hasScriptVar(self, "intLoopsLeft"))
        {
            int intLoopsLeft = utils.getIntScriptVar(self, "intLoopsLeft");
            intLoopsLeft = intLoopsLeft - 1;
            if (intLoopsLeft > 0)
            {
                utils.setScriptVar(self, "intLoopsLeft", intLoopsLeft);
                actionData.delayAttackLoopsDone = actionData.delayAttackLoops - intLoopsLeft;
                doStandardDelayedAction(self, objTarget, actionData.actionName, actionData, false);
            }
        }
        if (boolCleanup)
        {
            messageTo(objOwner, "eggDetonated", null, 1, false);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedParticle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objOwner = utils.getObjIdScriptVar(self, "objOwner");
        if (!exists(objOwner))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        String strEffect = params.getString("strEffect");
        if (strEffect != null && strEffect.length() > 0)
        {
            playClientEffectLoc(objOwner, strEffect, getLocation(self), 0);
        }
        boolean boolCleanup = params.getBoolean("boolCleanup");
        if (boolCleanup)
        {
            utils.removeObjVar(self, "objOwner");
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
