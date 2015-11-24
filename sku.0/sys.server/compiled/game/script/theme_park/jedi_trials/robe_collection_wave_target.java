package script.theme_park.jedi_trials;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.prose;
import script.library.trial;
import script.library.utils;

public class robe_collection_wave_target extends script.base_script
{
    public robe_collection_wave_target()
    {
    }
    public static final String DATATABLE = "datatables/quest/jedi_collection/jedi_robe_collection.iff";
    public static final String DATA_UTTERANCE = "utterance_msg";
    public static final String DATA_SLOT_AWARDED = "slot_awarded";
    public static final String DATA_SUCCESS_MSG = "success_msg";
    public static final String DATA_FAILURE_MSG = "failure_msg";
    public static final String DATA_FAILURE_DEBUFF = "failure_debuff";
    public static final String LIGHT_JEDI_VOLUME = "jedi_event_light_jedi";
    public static final float LIGHT_JEDI_MAX_DISTANCE = 7.0f;
    public static final int LIGHT_JEDI_DISTANCE_INTERVAL = 3;
    public static final int LIGHT_JEDI_EVENT_DURATION = 180;
    public static final String OBJVAR_EVENT_NAME = "jediRobeEventName";
    public static final String SCRIPTVAR_EVENT_PLAYER = "jediRobeEventPlayer";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        trial.setInterest(self);
        messageTo(self, "jediEventInitializeNpc", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int jediEventInitializeNpc(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_EVENT_PLAYER);
        if (isIdValid(player))
        {
            String eventName = getStringObjVar(self, OBJVAR_EVENT_NAME);
            dictionary eventData = dataTableGetRow(DATATABLE, eventName);
            if (eventData == null || eventData.isEmpty())
            {
                messageTo(self, "handleJediEventDestroySelf", null, 2, false);
                return SCRIPT_CONTINUE;
            }
            obj_id parent = trial.getParent(self);
            if (!isIdValid(parent))
            {
                messageTo(self, "handleJediEventDestroySelf", null, 2, false);
                return SCRIPT_CONTINUE;
            }
            String utterance = eventData.getString(DATA_UTTERANCE);
            if (utterance != null && utterance.length() > 0)
            {
                prose_package pp = new prose_package();
                pp.stringId = new string_id("quest2", utterance);
                String playerName = getName(player);
                if (playerName != null && playerName.length() > 0)
                {
                    prose.setTO(pp, playerName);
                    chat.publicChat(self, player, null, null, pp);
                }
            }
            dictionary webster = new dictionary();
            webster.put("player", player);
            webster.put("count", 1);
            messageTo(self, "jediEventCombatCheck", webster, 2, false);
            if (eventName.equals("light_jedi"))
            {
                messageTo(self, "jediEventDistanceCheck", webster, LIGHT_JEDI_DISTANCE_INTERVAL, false);
                dictionary resetDict = trial.getSessionDict(parent);
                messageTo(self, "jediEventLightJediHandler", resetDict, LIGHT_JEDI_EVENT_DURATION, false);
            }
            else 
            {
                buff.applyBuff(self, "jedi_statue_self_dps_debuff");
            }
            if (!ai_lib.isInCombat(self))
            {
                startCombat(self, player);
                addHate(self, player, 1000.0f);
            }
        }
        else 
        {
            messageTo(self, "handleJediEventDestroySelf", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int jediEventLightJediHandler(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        int passed = params.getInt(trial.MESSAGE_SESSION);
        int current = trial.getSession(parent);
        if (!isInvulnerable(self) && trial.verifySession(parent, params))
        {
            jediEventEnded(self, utils.getObjIdScriptVar(self, SCRIPTVAR_EVENT_PLAYER));
        }
        return SCRIPT_CONTINUE;
    }
    public int jediEventDistanceCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInvulnerable(self) && !hasFailedJediEvent(self))
        {
            obj_id player = params.getObjId("player");
            if (!isIdValid(player) || !exists(player))
            {
                messageTo(self, "handleJediEventDestroySelf", null, 2, false);
                return SCRIPT_CONTINUE;
            }
            location jediLoc = getLocation(self);
            location playerLoc = getLocation(player);
            location creationLoc = aiGetHomeLocation(self);
            float distanceCheck = utils.getDistance2D(jediLoc, creationLoc);
            if (distanceCheck > LIGHT_JEDI_MAX_DISTANCE)
            {
                if (isGod(player))
                {
                    sendSystemMessage(player, "God Mode Failure Message: Player ran away - the coward...", "");
                }
                setJediEventFailed(self, player);
                jediEventEnded(self, player);
            }
            else 
            {
                dictionary webster = new dictionary();
                webster.put("player", player);
                messageTo(self, "jediEventDistanceCheck", webster, 3, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int jediEventCombatCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInvulnerable(self))
        {
            obj_id player = params.getObjId("player");
            int count = params.getInt("count");
            if (!isIdValid(player) || !exists(player))
            {
                messageTo(self, "handleJediEventDestroySelf", null, 2, false);
                return SCRIPT_CONTINUE;
            }
            obj_id[] playerHateList = getHateList(player);
            if (playerHateList != null)
            {
                if (playerHateList.length > 1 || (playerHateList.length > 0 && playerHateList[0] != self))
                {
                    if (isGod(player))
                    {
                        sendSystemMessage(player, "God Mode Message: Player has someone other than the NPC Statue Jedi on their hate list. Event failed.", "");
                    }
                    setJediEventFailed(self, player);
                    jediEventEnded(self, player);
                    return SCRIPT_CONTINUE;
                }
            }
            if (!ai_lib.isInCombat(self))
            {
                if (count > 3)
                {
                    if (isGod(player))
                    {
                        sendSystemMessage(player, "God Mode Message: NPC Jedi is still not in combat after multiple attempts to force it. Event failed.", "");
                    }
                    setJediEventFailed(self, player);
                    jediEventEnded(self, player);
                    return SCRIPT_CONTINUE;
                }
                startCombat(self, player);
                addHate(self, player, 1000.0f);
            }
            dictionary webster = new dictionary();
            webster.put("player", player);
            webster.put("count", ++count);
            messageTo(self, "jediEventCombatCheck", webster, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (hasFailedJediEvent(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, OBJVAR_EVENT_NAME))
        {
            messageTo(self, "handleJediEventDestroySelf", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        String eventName = getStringObjVar(self, OBJVAR_EVENT_NAME);
        if (eventName == null || eventName.length() < 1)
        {
            messageTo(self, "handleJediEventDestroySelf", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        float damageThreshold = 0.1f;
        if (eventName.equals("light_jedi"))
        {
            damageThreshold = 0.6f;
        }
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        int currentHealth = getAttrib(self, HEALTH);
        if (currentHealth <= (maxHealth * damageThreshold))
        {
            if (eventName.equals("light_jedi"))
            {
                if (isGod(attacker))
                {
                    sendSystemMessage(attacker, "God Mode Failure Message: : Damage threshold broken against light jedi.", "");
                }
                setJediEventFailed(self, attacker);
            }
            jediEventEnded(self, attacker);
        }
        else 
        {
            int maxAction = getUnmodifiedMaxAttrib(self, ACTION);
            int currentAction = getAttrib(self, ACTION);
            int actionMod = (int)(maxAction * 0.1);
            int newAction = currentAction + actionMod;
            if (newAction > maxAction)
            {
                newAction = maxAction;
            }
            setAttrib(self, ACTION, newAction);
        }
        return SCRIPT_CONTINUE;
    }
    public int makeJediEventStopCombat(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        buff.removeAllBuffs(self);
        if ((params == null) || (params.isEmpty()))
        {
            messageTo(self, "handleJediEventDestroySelf", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        obj_id attacker = params.getObjId("attacker");
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_EVENT_PLAYER);
        if (!isIdValid(player) || !isIdValid(attacker) || attacker != player)
        {
            messageTo(self, "handleJediEventDestroySelf", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        String eventName = getStringObjVar(self, OBJVAR_EVENT_NAME);
        if (eventName == null || eventName.length() < 1)
        {
            messageTo(self, "handleJediEventDestroySelf", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        dictionary eventData = dataTableGetRow(DATATABLE, eventName);
        if (eventData == null || eventData.isEmpty())
        {
            messageTo(self, "handleJediEventDestroySelf", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        String endMsg = eventData.getString(DATA_SUCCESS_MSG);
        String slotAwarded = eventData.getString(DATA_SLOT_AWARDED);
        if (hasFailedJediEvent(self))
        {
            endMsg = eventData.getString(DATA_FAILURE_MSG);
            CustomerServiceLog("jedi_cloak_statue_event", "(" + player + ") " + getName(player) + " has FAILED against the " + eventName + " statue event with buff list: (" + buffListForCSLog(player) + ")");
        }
        else 
        {
            modifyCollectionSlotValue(player, slotAwarded, 1);
            CustomerServiceLog("jedi_cloak_statue_event", "(" + player + ") " + getName(player) + " has BEATEN the " + eventName + " statue event with buff list: (" + buffListForCSLog(player) + ")");
        }
        chat.publicChat(self, player, new string_id("quest2", endMsg));
        messageTo(self, "handleJediEventExit", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public String buffListForCSLog(obj_id player) throws InterruptedException
    {
        String buffsString = "none";
        int[] buffs = buff.getAllBuffs(player);
        if (buffs != null || buffs.length > 0)
        {
            buffsString = "";
            for (int i = 0; i < buffs.length; i++)
            {
                String buffName = buff.getBuffNameFromCrc(buffs[i]);
                String ending = ", ";
                if (i == (buffs.length - 1))
                {
                    ending = "";
                }
                else if (i == (buffs.length - 2))
                {
                    ending = ", and ";
                }
                buffsString = buffsString + buffName + ending;
            }
        }
        return buffsString;
    }
    public int OnHateTargetAdded(obj_id self, obj_id target) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_EVENT_PLAYER);
        if (isIdValid(player))
        {
            if (target != player)
            {
                if (isGod(player))
                {
                    sendSystemMessage(player, "God Mode Failure Message: Someone else (" + target + ") added to hate list.", "");
                }
                setJediEventFailed(self, player);
                jediEventEnded(self, player);
            }
            obj_id[] hateList = getHateList(self);
            if (hateList != null)
            {
                if (hateList.length > 1)
                {
                    if (isGod(player))
                    {
                        sendSystemMessage(player, "God Mode Failure Message: More than one entry on the jedi's hate list.", "");
                    }
                    setJediEventFailed(self, player);
                    jediEventEnded(self, player);
                }
                else 
                {
                    if (hateList[0] != player)
                    {
                        if (isGod(player))
                        {
                            sendSystemMessage(player, "God Mode Failure Message: Someone other than the player is the top person on the hate list.", "");
                        }
                        setJediEventFailed(self, player);
                        jediEventEnded(self, player);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostTarget(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        if (hasFailedJediEvent(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_EVENT_PLAYER);
        if (!isIdValid(player))
        {
            messageTo(self, "handleJediEventDestroySelf", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        if (oldTarget != player)
        {
            if (isGod(player))
            {
                sendSystemMessage(player, "God Mode Failure Message: Jedi lost his target (" + oldTarget + ") and that target was not the player who started the event.", "");
            }
            setJediEventFailed(self, player);
            jediEventEnded(self, player);
        }
        else if (isDead(oldTarget) || isIncapacitated(oldTarget))
        {
            if (isGod(player))
            {
                sendSystemMessage(player, "God Mode Failure Message: Player is now either dead or incapped.", "");
            }
            setJediEventFailed(self, oldTarget);
            jediEventEnded(self, oldTarget);
        }
        else 
        {
            if (!isInvulnerable(self))
            {
                startCombat(self, oldTarget);
                addHate(self, oldTarget, 1000.0f);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id victim) throws InterruptedException
    {
        if (isGod(victim))
        {
            sendSystemMessage(victim, "God Mode Failure Message: Player Incapacitated.", "");
        }
        setJediEventFailed(self, victim);
        jediEventEnded(self, victim);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_EVENT_PLAYER);
        if (isIdValid(player) && pvpHasPersonalEnemyFlag(player, self))
        {
            pvpRemovePersonalEnemyFlags(self, player);
        }
        obj_id parent = trial.getParent(self);
        dictionary webster = new dictionary();
        webster.put("jediEventChild", self);
        messageTo(parent, "jediEventChildDestroyed", webster, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleJediEventExit(obj_id self, dictionary params) throws InterruptedException
    {
        setState(self, STATE_GLOWING_JEDI, true);
        messageTo(self, "handleJediEventDestroySelf", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleJediEventDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, SCRIPTVAR_EVENT_PLAYER);
        obj_id parent = trial.getParent(self);
        dictionary webster = new dictionary();
        webster.put("jediEventChild", self);
        messageTo(parent, "jediEventChildDestroyed", webster, 1, false);
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public void setJediEventFailed(obj_id self, obj_id player) throws InterruptedException
    {
        utils.setScriptVar(self, "playerFailedjediEvent", true);
        String eventName = getStringObjVar(self, OBJVAR_EVENT_NAME);
        dictionary eventData = dataTableGetRow(DATATABLE, eventName);
        if (eventData != null && !eventData.isEmpty())
        {
            String failureBuff = eventData.getString(DATA_FAILURE_DEBUFF);
            buff.applyBuff(player, failureBuff);
        }
        return;
    }
    public boolean hasFailedJediEvent(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "playerFailedjediEvent"))
        {
            return true;
        }
        return false;
    }
    public void jediEventEnded(obj_id self, obj_id player) throws InterruptedException
    {
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        setAttrib(self, HEALTH, maxHealth);
        setInvulnerable(self, true);
        pvpRemovePersonalEnemyFlags(self, player);
        pvpRemovePersonalEnemyFlags(player, self);
        detachScript(self, "ai.creature_combat");
        dictionary webster = new dictionary();
        webster.put("attacker", player);
        messageTo(self, "makeJediEventStopCombat", webster, 1, false);
        return;
    }
}
