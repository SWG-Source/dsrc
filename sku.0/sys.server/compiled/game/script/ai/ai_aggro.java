package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.colors;
import script.library.pet_lib;
import script.library.stealth;
import script.library.storyteller;
import script.library.utils;

public class ai_aggro extends script.base_script
{
    public ai_aggro()
    {
    }
    public static final int AGGRO_STATUS_NOT_VALID = 0;
    public static final int AGGRO_STATUS_WAITING = 1;
    public static final int AGGRO_STATUS_READY = 2;
    public static final float AGGRO_RADIUS_INTERIOR_VERTICAL = 3.0f;
    public static void requestAggroCheck(obj_id target) throws InterruptedException
    {
        final obj_id self = getSelf();
        if (!isAttemptingAggroCheck(target))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::requestAggroCheck(YES) self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ")");
            final int aggroStatus = ai_aggro.getAggroStatus(target);
            switch (aggroStatus)
            {
                default:
                case ai_aggro.AGGRO_STATUS_NOT_VALID:
                
                {
                    LOGC(aiLoggingEnabled(self), "debug_ai", "creature_combat::handleAggroCheck() self(" + self + ":" + getName(self) + ") target(" + target + ") AGGRO_STATUS_NOT_VALID");
                }
                break;
                case ai_aggro.AGGRO_STATUS_WAITING:
                
                {
                    LOGC(aiLoggingEnabled(self), "debug_ai", "creature_combat::handleAggroCheck() self(" + self + ":" + getName(self) + ") target(" + target + ") AGGRO_STATUS_WAITING");
                    deltadictionary dict = self.getScriptVars();
                    dict.put("ai.aggrocheck." + target, true);
                    dictionary params = new dictionary();
                    params.put("target", target);
                    messageTo(self, "handleAggroCheck", params, rand(3.0f, 5.0f), false);
                }
                break;
                case ai_aggro.AGGRO_STATUS_READY:
                
                {
                    LOGC(aiLoggingEnabled(self), "debug_ai", "creature_combat::handleAggroCheck() self(" + self + ":" + getName(self) + ") target(" + target + ") AGGRO_STATUS_READY");
                    dictionary params = new dictionary();
                    params.put("target", target);
                    messageTo(self, "handleAggroStart", params, rand(2.5f, 4.5f), false);
                }
                break;
            }
        }
        else 
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::requestAggroCheck(NO) self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ")");
        }
    }
    public static void stopAttemptingAggroCheck(obj_id target) throws InterruptedException
    {
        final obj_id self = getSelf();
        LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::stopAttemptingAggroCheck() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ")");
        deltadictionary dict = self.getScriptVars();
        dict.remove("ai.aggrocheck." + target);
    }
    public static boolean isAttemptingAggroCheck(obj_id target) throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        return dict.getBoolean("ai.aggrocheck." + target);
    }
    public static int getAggroStatus(obj_id target) throws InterruptedException
    {
        int result = AGGRO_STATUS_NOT_VALID;
        final obj_id self = getSelf();
        if (!isIdValid(self))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") I AM NO LONGER VALID");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") I AM NO LONGER VALID");
        }
        else if (isIncapacitated(self) || isDead(self))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") I AM DEAD");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") I AM DEAD");
        }
        else if (!isIdValid(target))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET NO LONGER VALID");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET NO LONGER VALID");
        }
        else if (isInvulnerable(target))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET IS INVULNERABLE");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET IS INVULNERABLE");
        }
        else if (ai_lib.isInCombat(self))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") I AM IN COMBAT");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") I AM IN COMBAT");
        }
        else if (!isInTriggerVolume(self, ai_lib.AGGRO_VOLUME_NAME, target))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET OUT OF AGGRO VOLUME");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET OUT OF AGGRO VOLUME");
        }
        else if (!pvpCanAttack(self, target))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") INVALID PVP TARGET");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") INVALID PVP TARGET");
        }
        else if (hasObjVar(target, "gm"))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET IS GM");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET IS GM");
            result = AGGRO_STATUS_WAITING;
        }
        else if (getState(target, STATE_FEIGN_DEATH) == 1)
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET IS FEIGNED");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET IS FEIGNED");
            result = AGGRO_STATUS_WAITING;
        }
        else if (isGameObjectTypeOf(getTopMostContainer(target), GOT_building_player))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET IN PLAYER STRUCTURE");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET IN PLAYER STRUCTURE");
            result = AGGRO_STATUS_WAITING;
        }
        else if (isIncapacitated(target))
        {
            if (isPlayer(target) || pet_lib.isCreaturePet(target))
            {
                LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") REZZURECTABLE TARGET IS INCAPACITATED");
                debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") REZZURECTABLE TARGET IS INCAPACITATED");
                result = AGGRO_STATUS_WAITING;
            }
            else 
            {
                LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET IS INCAPACITATED");
                debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET IS INCAPACITATED");
            }
        }
        else if (isDead(target))
        {
            if (isPlayer(target) || pet_lib.isCreaturePet(target))
            {
                LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") REZZURECTABLE TARGET IS DEAD");
                debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") REZZURECTABLE TARGET IS DEAD");
                result = AGGRO_STATUS_WAITING;
            }
            else 
            {
                LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET IS DEAD");
                debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET IS DEAD");
            }
        }
        else if (isAggroImmune(target))
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET IS AGGRO IMMUNE");
            debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET IS AGGRO IMMUNE");
            result = AGGRO_STATUS_WAITING;
        }
        else 
        {
            boolean doLosCheck = true;
            boolean doDistanceCheck = true;
            if ((isPlayer(target) && !pvpIsEnemy(self, target)) || pet_lib.isCreaturePet(target))
            {
                location here = getLocation(self);
                if (isIdValid(here.cell))
                {
                    location selfLoc = getWorldLocation(self);
                    location targetLoc = getWorldLocation(target);
                    float verticalDelta = Math.abs(targetLoc.y - selfLoc.y);
                    if (verticalDelta > AGGRO_RADIUS_INTERIOR_VERTICAL)
                    {
                        LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") OUT OF VERTICAL RADIUS " + AGGRO_RADIUS_INTERIOR_VERTICAL);
                        debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") OUT OF VERTICAL RADIUS " + AGGRO_RADIUS_INTERIOR_VERTICAL);
                        result = AGGRO_STATUS_WAITING;
                        doDistanceCheck = false;
                        doLosCheck = false;
                    }
                }
                if (doDistanceCheck)
                {
                    final float respectRadius = aiGetRespectRadius(self, target);
                    final float distanceToTarget = getDistance(self, target);
                    if (distanceToTarget > respectRadius)
                    {
                        LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") OUT OF RESPECT RADIUS " + respectRadius);
                        debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") OUT OF RESPECT RADIUS " + respectRadius);
                        result = AGGRO_STATUS_WAITING;
                        doLosCheck = false;
                    }
                }
            }
            if (doLosCheck)
            {
                if (canSee(self, target))
                {
                    if (stealth.hasServerCoverState(target))
                    {
                        LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET HAS COVER STATE (INVISIBLE)");
                        debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET HAS COVER STATE (INVISIBLE)");
                        if (utils.hasScriptVar(target, "sp_smoke_bomb"))
                        {
                            result = AGGRO_STATUS_NOT_VALID;
                        }
                        else 
                        {
                            if (stealth.passiveDetectHiddenTarget(target, self, 100))
                            {
                                stealth.checkForAndMakeVisible(target);
                                result = AGGRO_STATUS_READY;
                            }
                            else 
                            {
                                result = AGGRO_STATUS_NOT_VALID;
                                int roll = rand(1, 5);
                                if (roll == 1)
                                {
                                    faceTo(self, target);
                                }
                            }
                        }
                    }
                    else 
                    {
                        LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") TARGET ACQUIRED");
                        debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") TARGET ACQUIRED");
                        result = AGGRO_STATUS_READY;
                    }
                }
                else 
                {
                    LOGC(aiLoggingEnabled(self), "debug_ai", "ai_aggro::getAggroStatus() self(" + self + ":" + getName(self) + ") target(" + target + ":" + getName(target) + ") CAN'T SEE TARGET");
                    debugSpeakMsgc(aiLoggingEnabled(self), self, "AggroCheck(" + target + ") CAN'T SEE TARGET");
                    result = AGGRO_STATUS_WAITING;
                }
            }
            if (!storyteller.storytellerCombatCheck(self, target))
            {
                result = AGGRO_STATUS_NOT_VALID;
            }
        }
        return result;
    }
}
