package script.ai;

import script.deltadictionary;
import script.library.*;
import script.obj_id;

public class ai_combat_target extends script.base_script
{
    public ai_combat_target()
    {
    }
    public static void clearTargetList() throws InterruptedException
    {
        final obj_id self = getSelf();
        utils.removeScriptVarTree(self, "ai.combat.targetSocialGroup.verified");
    }
    public static boolean isValidTarget(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        boolean result = true;
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        final String targetSocialGroupVerified = "ai.combat.targetSocialGroup.verified." + target;
        if (!pvpCanAttack(self, target))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_combat_target::isValidTarget() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") CAN'T ATTACK THIS TARGET");
            result = false;
        }
        else if (isGameObjectTypeOf(getTopMostContainer(target), GOT_building_player) && !target_dummy.isTargetDummy(target))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_combat_target::isValidTarget() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") NOT ALLOWED IN STRUCTURE");
            result = false;
        }
        else if (dict.getBoolean(targetSocialGroupVerified) == false)
        {
            final String selfSocialGroup = ai_lib.getSocialGroup(self);
            if (selfSocialGroup != null && ai_lib.isMob(target))
            {
                if (!isIdValid(getMaster(self)) && !isIdValid(getMaster(target)) && selfSocialGroup.equals(ai_lib.getSocialGroup(target)))
                {
                    LOGC(aiLoggingEnabled(self), "debug_ai", "ai_combat_target::isValidTarget() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") SAME SOCIAL GROUP");
                    result = false;
                }
                else 
                {
                    dict.put(targetSocialGroupVerified, true);
                }
            }
        }
        String evasionObjVarName = "me_evasion." + target;
        if (utils.hasScriptVar(self, evasionObjVarName) && buff.hasBuff(target, "me_evasion_1"))
        {
            obj_id[] thoseWhoHateMe = getHateList(self);
            if (thoseWhoHateMe.length < 2)
            {
                return true;
            }
            return false;
        }
        if (hasObjVar(target, "ai.combat.limitAttackers") && !isPlayer(target) && !pet_lib.hasMaster(target))
        {
            int attackers = getIntObjVar(target, "ai.combat.limitAttackers");
            obj_id[] haters = getHateList(target);
            if (haters.length >= attackers)
            {
                int npcCount = 0;
                for (obj_id hater : haters) {
                    if (isIdValid(hater) && !isPlayer(hater) && !pet_lib.hasMaster(hater)) {
                        npcCount++;
                    }
                    if (npcCount > attackers) {
                        stopCombat(self);
                        clearHateList(self);
                        return false;
                    }
                    if (isIdValid(hater) && hater == self) {
                        return true;
                    }
                }
            }
        }
        return result;
    }
}
