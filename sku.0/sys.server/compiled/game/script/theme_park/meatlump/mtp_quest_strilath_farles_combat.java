package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.utils;

public class mtp_quest_strilath_farles_combat extends script.base_script
{
    public mtp_quest_strilath_farles_combat()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleFarlesFaceTo", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleFarlesFaceTo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (isIdValid(player))
        {
            faceTo(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        float damageThreshold = 0.1f;
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        int currentHealth = getAttrib(self, HEALTH);
        if (currentHealth <= (maxHealth * damageThreshold))
        {
            setAttrib(self, HEALTH, maxHealth);
            setInvulnerable(self, true);
            detachScript(self, "ai.creature_combat");
            attachScript(self, "conversation.mtp_hideout_access_strilath_farles_01");
            messageTo(self, "makeNpcStopCombat", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int makeNpcStopCombat(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        stopCombat(self);
        clearHateList(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        buff.removeAllBuffs(self);
        return SCRIPT_CONTINUE;
    }
    public int makeNpcDisappear(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        playClientEffectLoc(getPlayerCreaturesInRange(here, 100.0f), "appearance/pt_smoke_puff_noloop.prt", here, 0.0f);
        messageTo(self, "handleDestroySelf", null, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        stopCombat(self);
        clearHateList(self);
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 20f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                npcEndConversation(players[i]);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
