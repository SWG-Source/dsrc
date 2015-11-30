package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai_combat;
import script.library.factions;
import script.library.chat;
import script.library.attrib;
import script.library.utils;

public class townperson extends script.base_script
{
    public townperson()
    {
    }
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final String SOCIAL_VOLUME = "npc_socialization";
    public static final float SOCIAL_RANGE = 15f;
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_THREATEN = "threaten";
    public static final int CONVO_LENGTH = 300;
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (getConfigSetting("GameServer", "disableAITriggerVolumes") == null)
        {
            createTriggerVolume(SOCIAL_VOLUME, SOCIAL_RANGE, false);
        }
        String diction = "townperson";
        switch (rand(1, 3))
        {
            case 1:
            diction = "townperson_fancy";
            break;
            case 2:
            diction = "townperson_slang";
            break;
            case 3:
            diction = "townperson";
            break;
        }
        setObjVar(self, "ai.diction", diction);
        factions.setFaction(self, "townperson");
        switch (rand(1, 5))
        {
            case 1:
            factions.setFaction(self, "ImperialCitizen");
            break;
            case 2:
            factions.setFaction(self, "RebelCitizen");
            break;
        }
        attachScript(self, "theme_park.tatooine.city_convo");
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.speaking");
        if (getConfigSetting("GameServer", "disableAITriggerVolumes") == null)
        {
            createTriggerVolume(SOCIAL_VOLUME, SOCIAL_RANGE, false);
        }
        setAttributeAttained(self, attrib.TOWNSPERSON);
        setAttributeInterested(self, attrib.TOWNSPERSON);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (breacher == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ALERT_VOLUME_NAME))
        {
            if (hasScript(breacher, "ai.townperson"))
            {
                addTriggerVolumeEventSource(SOCIAL_VOLUME, breacher);
            }
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(SOCIAL_VOLUME))
        {
            if (!isPlayer(breacher))
            {
                initiateDialog(self, breacher);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(ALERT_VOLUME_NAME))
        {
            if (hasScript(breacher, "ai.townperson"))
            {
                removeTriggerVolumeEventSource(SOCIAL_VOLUME, breacher);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int cancelFacing(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.speaking");
        if (getBehavior(self) == BEHAVIOR_CALM)
        {
            messageTo(self, "resumeDefaultCalmBehavior", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void initiateDialog(obj_id talker, obj_id listener) throws InterruptedException
    {
        if (ai_lib.isInCombat(talker) || ai_lib.isInCombat(listener))
        {
            return;
        }
        if (ai_lib.isFollowing(talker) || ai_lib.isFollowing(listener))
        {
            return;
        }
        aiUnEquipWeapons(talker);
        aiUnEquipWeapons(listener);
        if (utils.hasScriptVar(talker, "ai.speaking") || utils.hasScriptVar(listener, "ai.speaking"))
        {
            return;
        }
        if (getBehavior(talker) >= BEHAVIOR_THREATEN || getBehavior(listener) >= BEHAVIOR_THREATEN)
        {
            return;
        }
        if (getIntObjVar(listener, "ai.defaultCalmBehavior") == ai_lib.BEHAVIOR_SENTINEL || getIntObjVar(talker, "ai.defaultCalmBehavior") == ai_lib.BEHAVIOR_SENTINEL)
        {
            return;
        }
        utils.setScriptVar(talker, "ai.speaking", listener);
        utils.setScriptVar(listener, "ai.speaking", talker);
        stop(talker);
        stop(listener);
        faceTo(listener, talker);
        utils.setScriptVar(talker, "ai.pathingToSocialize", listener);
        utils.setScriptVar(listener, "ai.pathingToSocialize", talker);
        location pathToLoc = new location(getLocation(listener));
        location myLoc = getLocation(talker);
        if (pathToLoc.x < myLoc.x)
        {
            pathToLoc.x += 1.5f;
        }
        else 
        {
            pathToLoc.x -= 1.5f;
        }
        if (pathToLoc.z < myLoc.z)
        {
            pathToLoc.z += 1.5f;
        }
        else 
        {
            pathToLoc.z -= 1.5f;
        }
        pathTo(talker, pathToLoc);
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.pathingToSocialize"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
        if (exists(listener))
        {
            faceTo(self, listener);
            faceTo(listener, self);
            ai_lib.greet(self, listener);
            ai_lib.doAction(self, "conversation_" + getGender(self));
            ai_lib.doAction(listener, "conversation_" + getGender(listener));
        }
        messageTo(self, "handleEndSocializing", null, CONVO_LENGTH, isObjectPersisted(self));
        return SCRIPT_CONTINUE;
    }
    public int handleEndSocializing(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.pathingToSocialize"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
        endSocializing(self, listener);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.pathingToSocialize"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
        endSocializing(self, listener);
        return SCRIPT_CONTINUE;
    }
    public void endSocializing(obj_id talker, obj_id listener) throws InterruptedException
    {
        if (getBehavior(talker) == BEHAVIOR_CALM)
        {
            ai_lib.setMood(talker, ai_lib.MOOD_CALM);
            ai_lib.dismiss(talker, listener);
            messageTo(talker, "resumeDefaultCalmBehavior", null, 5, false);
        }
        if (getBehavior(listener) == BEHAVIOR_CALM)
        {
            ai_lib.setMood(listener, ai_lib.MOOD_CALM);
            ai_lib.doAction(listener, "wave" + rand(1, 2));
            messageTo(listener, "resumeDefaultCalmBehavior", null, 8, false);
        }
        utils.removeScriptVar(talker, "ai.pathingToSocialize");
        utils.removeScriptVar(listener, "ai.pathingToSocialize");
        utils.removeScriptVar(talker, "ai.speaking");
        utils.removeScriptVar(listener, "ai.speaking");
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (newBehavior > oldBehavior)
        {
            if (utils.hasScriptVar(self, "ai.pathingToSocialize"))
            {
                obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
                endSocializing(self, listener);
            }
            if (newBehavior >= BEHAVIOR_ALERT && newBehavior < BEHAVIOR_ATTACK)
            {
                doAgitateBehavior(self, newBehavior);
                return SCRIPT_OVERRIDE;
            }
            else 
            {
            }
            return SCRIPT_CONTINUE;
        }
        else if (newBehavior == BEHAVIOR_CALM)
        {
            if (utils.hasScriptVar(self, "ai.speaking"))
            {
                ai_lib.setMood(self, ai_lib.MOOD_CALM);
                return SCRIPT_OVERRIDE;
            }
            if (oldBehavior != BEHAVIOR_CALM)
            {
                chat.setNeutralMood(self);
                ai_lib.barkString(self, "calm");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void doAgitateBehavior(obj_id npc, int behavior) throws InterruptedException
    {
        if (isInvulnerable(npc))
        {
            return;
        }
        return;
    }
    public int OnTargeted(obj_id self, obj_id attacker) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            return SCRIPT_OVERRIDE;
        }
        addToMentalStateToward(self, attacker, FEAR, 100f);
        return SCRIPT_OVERRIDE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "ai.speaking"))
        {
            obj_id listener = utils.getObjIdScriptVar(self, "ai.speaking");
            if (listener != speaker)
            {
                endSocializing(self, listener);
            }
        }
        utils.setScriptVar(self, "ai.speaking", speaker);
        return SCRIPT_CONTINUE;
    }
    public int OnEndNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.speaking");
        if (getBehavior(self) == BEHAVIOR_CALM)
        {
            messageTo(self, "resumeDefaultCalmBehavior", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int resumeDefaultCalmBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "ai.speaking"))
        {
            return SCRIPT_OVERRIDE;
        }
        if (getBehavior(self) == BEHAVIOR_CALM)
        {
            aiUnEquipWeapons(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawAttack(obj_id self, obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        if (getConfigSetting("GameServer", "disableAICombat") != null)
        {
            setWantSawAttackTriggers(self, false);
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isAiDead(self) || isInvulnerable(self))
        {
            setWantSawAttackTriggers(self, false);
            return SCRIPT_OVERRIDE;
        }
        if (!utils.hasScriptVar(self, "ai.pathingToSocialize"))
        {
            return SCRIPT_CONTINUE;
        }
        setAnimationMood(self, "nervous");
        obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
        endSocializing(self, listener);
        return SCRIPT_CONTINUE;
    }
}
