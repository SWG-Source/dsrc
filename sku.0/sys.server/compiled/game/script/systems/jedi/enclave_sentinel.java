package script.systems.jedi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.utils;
import script.library.chat;
import script.library.factions;

public class enclave_sentinel extends script.base_script
{
    public enclave_sentinel()
    {
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        boolean isLightJedi = hasObjVar(self, "jedi.enclave.light");
        boolean isDarkJedi = hasObjVar(self, "jedi.enclave.dark");
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ai_lib.ALERT_VOLUME_NAME) || volumeName.equals(ai_lib.AGGRO_VOLUME_NAME))
        {
            if (isLightJedi)
            {
                if (!hasSkill(breacher, "force_rank_dark_novice") && !hasSkill(breacher, "combat_bountyhunter_novice") && !(pvpGetAlignedFaction(breacher) == (-615855020) && (pvpGetType(breacher) == PVPTYPE_DECLARED || pvpHasAnyTempEnemyFlags(breacher))))
                {
                    return SCRIPT_OVERRIDE;
                }
            }
            else if (isDarkJedi)
            {
                if (hasSkill(breacher, "force_rank_dark_novice") && !hasSkill(breacher, "combat_bountyhunter_novice"))
                {
                    return SCRIPT_OVERRIDE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawAttack(obj_id self, obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        boolean isLightJedi = hasObjVar(self, "jedi.enclave.light");
        boolean isDarkJedi = hasObjVar(self, "jedi.enclave.dark");
        if (ai_lib.isSameSocialGroup(self, defender))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(defender))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isIdValid(defender) || getTarget(self) == defender)
        {
            return SCRIPT_CONTINUE;
        }
        if (!canSee(self, defender))
        {
            return SCRIPT_CONTINUE;
        }
        boolean isInCombat = ai_lib.isInCombat(self);
        if (!isInCombat)
        {
            if (defender == self)
            {
                return SCRIPT_CONTINUE;
            }
        }
        obj_id myCell = (getLocation(self)).cell;
        if (isIdValid(myCell))
        {
            obj_id defenderCell = (getLocation(defender)).cell;
            if (myCell != defenderCell)
            {
                if (getDistance(self, defender) > 4.0f)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (ai_lib.isAiDead(self) || isInvulnerable(self))
        {
            setWantSawAttackTriggers(self, false);
            return SCRIPT_CONTINUE;
        }
        if (attackers == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (defender == self || ai_lib.isAiDead(defender))
        {
            return SCRIPT_CONTINUE;
        }
        if ((isLightJedi && hasSkill(defender, "force_rank_light_novice")) || (isDarkJedi && hasSkill(defender, "force_rank_dark_novice")))
        {
            if (!isInCombat)
            {
                chat.setAngryMood(self);
                ai_lib.barkString(self, "ally");
            }
            int endLoop = attackers.length;
            if (endLoop > 20)
            {
                endLoop = 20;
            }
            for (int i = 0; i < endLoop; i++)
            {
                if (isIdValid(attackers[i]))
                {
                    if (!isPlayer(attackers[i]))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    addToMentalStateToward(self, attackers[i], FEAR, 10f);
                    if (pvpCanAttack(self, attackers[i]) && !isInCombat && factions.isAggro(self))
                    {
                        startCombat(self, attackers[i]);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleEntertainmentStart(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
}
