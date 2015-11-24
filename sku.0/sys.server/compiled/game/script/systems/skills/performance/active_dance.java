package script.systems.skills.performance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.colors;
import script.library.groundquests;
import script.library.performance;
import script.library.sui;
import script.library.utils;
import script.library.xp;
import script.library.beast_lib;

public class active_dance extends script.base_script
{
    public active_dance()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int healingXP = getExperiencePoints(self, "entertainer_healing");
        if (healingXP > 0)
        {
            grantExperiencePoints(self, "entertainer_healing", (healingXP * -1));
            grantExperiencePoints(self, xp.ENTERTAINER, healingXP);
        }
        groundquests.questStartDance(self);
        performance.checkAndIncrementEntertainerMissions(self, "dancer");
        queueCommand(self, (-1219480501), null, "1", COMMAND_PRIORITY_IMMEDIATE);
        setObjVar(self, performance.VAR_PERFORM_FLOURISH, false);
        setObjVar(self, performance.VAR_PERFORM_SEQUENCE, 0);
        setObjVar(self, performance.VAR_PERFORM_APPLAUSE_COUNT, 0);
        setObjVar(self, performance.VAR_PERFORM_FLOURISH_COUNT, 0);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        groundquests.questStopPerforming(self);
        performance.stopEntertainerMissionTracking(self, "dancer");
        queueCommand(self, (-1219480501), null, "0", COMMAND_PRIORITY_IMMEDIATE);
        setPerformanceType(self, 0);
        removeObjVar(self, performance.VAR_PERFORM);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, performance.DANCE_HEARTBEAT_SCRIPT);
        return SCRIPT_CONTINUE;
    }
    public int OnChangedPosture(obj_id self, int before, int after) throws InterruptedException
    {
        if (after != POSTURE_SKILL_ANIMATING)
        {
            performance.stopDance(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        performance.stopDance(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDuelStart(obj_id self, obj_id actor, obj_id target) throws InterruptedException
    {
        performance.stopDance(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEnterSwimming(obj_id self) throws InterruptedException
    {
        performance.stopDance(self);
        return SCRIPT_CONTINUE;
    }
    public int OnSawEmote(obj_id self, obj_id actor, String emote) throws InterruptedException
    {
        int applause = 0;
        for (int i = 0; i < performance.APPLAUSE_POSTIVE.length; i++)
        {
            if (emote.equals(performance.APPLAUSE_POSTIVE[i]))
            {
                applause = 1;
                break;
            }
        }
        if (applause == 0)
        {
            for (int i = 0; i < performance.APPLAUSE_NEGATIVE.length; i++)
            {
                if (emote.equals(performance.APPLAUSE_NEGATIVE[i]))
                {
                    applause = -1;
                    break;
                }
            }
        }
        if (applause == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int band_size = 1;
        obj_id[] band_members = new obj_id[20];
        band_members[0] = self;
        obj_id group = getGroupObject(self);
        if (isIdValid(group))
        {
            obj_id[] members = getGroupMemberIds(group);
            for (int i = 0; i < members.length; ++i)
            {
                if (members[i] != actor && (hasScript(members[i], performance.DANCE_HEARTBEAT_SCRIPT) || hasScript(members[i], performance.MUSIC_HEARTBEAT_SCRIPT)))
                {
                    band_members[band_size] = members[i];
                    ++band_size;
                }
            }
        }
        boolean audience_member = false;
        obj_id listen_target = getPerformanceListenTarget(actor);
        obj_id watch_target = getPerformanceWatchTarget(actor);
        for (int i = 0; i < band_size; i++)
        {
            if (actor == band_members[i])
            {
                applause = 0;
            }
            if (listen_target == band_members[i])
            {
                audience_member = true;
            }
            if (watch_target == band_members[i])
            {
                audience_member = true;
            }
        }
        if ((audience_member) && (applause != 0))
        {
            int applause_count = getIntObjVar(self, performance.VAR_PERFORM_APPLAUSE_COUNT);
            applause_count += applause;
            if (applause_count > performance.PERFORM_XP_APPLAUSE_MAX)
            {
                applause_count = performance.PERFORM_XP_APPLAUSE_MAX;
            }
            if (applause_count < 0)
            {
                applause_count = 0;
            }
            setObjVar(self, performance.VAR_PERFORM_APPLAUSE_COUNT, applause_count);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPulse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, performance.VAR_PERFORM_SEQUENCE))
        {
            return SCRIPT_CONTINUE;
        }
        int sequence = getIntObjVar(self, performance.VAR_PERFORM_SEQUENCE);
        if (params.getInt("sequence") != sequence)
        {
            return SCRIPT_CONTINUE;
        }
        performance.checkAndIncrementEntertainerMissions(self, "dancer");
        if (performance.applyPerformanceActionCost(self, 1.0f))
        {
            performance.performanceHeal(self, performance.PERFORMANCE_TYPE_DANCE, 1.0f);
            performance.inspire(self, performance.PERFORMANCE_TYPE_DANCE);
            int flourishNum = getIntObjVar(self, performance.VAR_PERFORM_FLOURISH_COUNT);
            int xpAmt = performance.calcPerformanceXp(self);
            int entXpAmt = 0;
            if (flourishNum > 0)
            {
                entXpAmt = (int)((float)xpAmt * 0.5f);
            }
            xp.grantSocialStyleXp(self, xp.DANCE, xpAmt);
            ++sequence;
            setObjVar(self, performance.VAR_PERFORM_SEQUENCE, sequence);
            params.put("sequence", sequence);
            messageTo(self, "OnPulse", params, performance.PERFORMANCE_HEARTBEAT_TIME, false);
        }
        else 
        {
            performance.performanceMessageToSelf(self, null, performance.SID_DANCE_TOO_TIRED);
            performance.stopDance(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFlourish(obj_id self, dictionary params) throws InterruptedException
    {
        int flourishIndex = params.getInt("flourishIndex");
        float mistakeChance = params.getFloat("mistakeChance");
        int check = rand(0, 100);
        LOG("perofrmance_dance", "Mistake Check: " + check + " / " + mistakeChance);
        if (check < mistakeChance)
        {
            doAnimationAction(self, "mistake");
        }
        else 
        {
            if (buff.hasBuff(self, "bm_dancing_pet_entertainer"))
            {
                obj_id beast = beast_lib.getBeastOnPlayer(self);
                if (isIdValid(beast) && exists(beast))
                {
                    messageTo(beast, "doBeastFlourish", null, 0, false);
                }
            }
            doAnimationAction(self, "skill_action_" + flourishIndex);
        }
        performance.performanceTargetedBuffFlourish(self, performance.PERFORMANCE_TYPE_DANCE, 1.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnClearFlourish(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, performance.VAR_PERFORM_FLOURISH, 0);
        return SCRIPT_CONTINUE;
    }
    public int OnEffect(obj_id self, dictionary params) throws InterruptedException
    {
        int effectId = params.getInt("effectId");
        int effectLevel = params.getInt("effectLevel");
        String effectFilename = "";
        string_id effectString = null;
        int action = getAttrib(self, ACTION);
        int cost = performance.getEffectActionCost(effectId, effectLevel);
        if (action > cost)
        {
            drainAttributes(self, cost, 0);
            switch (effectId)
            {
                case performance.PERFORMANCE_EFFECT_SPOT_LIGHT:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_SPOT_LIGHT_1;
                    effectString = performance.SID_EFFECT_PERFORM_SPOT_LIGHT;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_SPOT_LIGHT_2;
                    effectString = performance.SID_EFFECT_PERFORM_SPOT_LIGHT;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_SPOT_LIGHT_3;
                    effectString = performance.SID_EFFECT_PERFORM_SPOT_LIGHT;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_COLOR_LIGHTS:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_COLOR_LIGHTS_1;
                    effectString = performance.SID_EFFECT_PERFORM_COLOR_LIGHTS;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_COLOR_LIGHTS_2;
                    effectString = performance.SID_EFFECT_PERFORM_COLOR_LIGHTS;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_COLOR_LIGHTS_3;
                    effectString = performance.SID_EFFECT_PERFORM_COLOR_LIGHTS;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_DAZZLE:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_DAZZLE_1;
                    effectString = performance.SID_EFFECT_PERFORM_DAZZLE;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_DAZZLE_2;
                    effectString = performance.SID_EFFECT_PERFORM_DAZZLE;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_DAZZLE_3;
                    effectString = performance.SID_EFFECT_PERFORM_DAZZLE;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_DISTRACT:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_DISTRACT_1;
                    effectString = performance.SID_EFFECT_PERFORM_DISTRACT;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_DISTRACT_2;
                    effectString = performance.SID_EFFECT_PERFORM_DISTRACT;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_DISTRACT_3;
                    effectString = performance.SID_EFFECT_PERFORM_DISTRACT;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_SMOKE_BOMB:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_SMOKE_BOMB_1;
                    effectString = performance.SID_EFFECT_PERFORM_SMOKE_BOMB;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_SMOKE_BOMB_2;
                    effectString = performance.SID_EFFECT_PERFORM_SMOKE_BOMB;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_SMOKE_BOMB_3;
                    effectString = performance.SID_EFFECT_PERFORM_SMOKE_BOMB;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_FIRE_JETS:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_FIRE_JETS_1;
                    effectString = performance.SID_EFFECT_PERFORM_FIRE_JETS;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_FIRE_JETS_2;
                    effectString = performance.SID_EFFECT_PERFORM_FIRE_JETS;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_FIRE_JETS_3;
                    effectString = performance.SID_EFFECT_PERFORM_FIRE_JETS;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_VENTRILOQUISM:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_VENTRILOQUISM_1;
                    effectString = performance.SID_EFFECT_PERFORM_VENTRILOQUISM;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_VENTRILOQUISM_2;
                    effectString = performance.SID_EFFECT_PERFORM_VENTRILOQUISM;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_VENTRILOQUISM_3;
                    effectString = performance.SID_EFFECT_PERFORM_VENTRILOQUISM;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_CENTER_STAGE:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_CENTER_STAGE;
                    effectString = performance.SID_EFFECT_PERFORM_CENTER_STAGE;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_CENTER_STAGE;
                    effectString = performance.SID_EFFECT_PERFORM_CENTER_STAGE;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_CENTER_STAGE;
                    effectString = performance.SID_EFFECT_PERFORM_CENTER_STAGE;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_COLOR_SWIRL:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_COLOR_SWIRL;
                    effectString = performance.SID_EFFECT_PERFORM_COLOR_SWIRL;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_COLOR_SWIRL;
                    effectString = performance.SID_EFFECT_PERFORM_COLOR_SWIRL;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_COLOR_SWIRL;
                    effectString = performance.SID_EFFECT_PERFORM_COLOR_SWIRL;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_DANCE_FLOOR:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_DANCE_FLOOR;
                    effectString = performance.SID_EFFECT_PERFORM_DANCE_FLOOR;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_DANCE_FLOOR;
                    effectString = performance.SID_EFFECT_PERFORM_DANCE_FLOOR;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_DANCE_FLOOR;
                    effectString = performance.SID_EFFECT_PERFORM_DANCE_FLOOR;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_FEATURED_SOLO:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_FEATURED_SOLO;
                    effectString = performance.SID_EFFECT_PERFORM_FEATURED_SOLO;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_FEATURED_SOLO;
                    effectString = performance.SID_EFFECT_PERFORM_FEATURED_SOLO;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_FEATURED_SOLO;
                    effectString = performance.SID_EFFECT_PERFORM_FEATURED_SOLO;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_FIRE_JETS2:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_FIRE_JETS2;
                    effectString = performance.SID_EFFECT_PERFORM_FIRE_JETS_2;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_FIRE_JETS2;
                    effectString = performance.SID_EFFECT_PERFORM_FIRE_JETS_2;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_FIRE_JETS2;
                    effectString = performance.SID_EFFECT_PERFORM_FIRE_JETS_2;
                    break;
                }
                break;
                case performance.PERFORMANCE_EFFECT_LASER_SHOW:
                switch (effectLevel)
                {
                    case 1:
                    effectFilename = performance.EFFECT_LASER_SHOW;
                    effectString = performance.SID_EFFECT_PERFORM_LASER_SHOW;
                    break;
                    case 2:
                    effectFilename = performance.EFFECT_LASER_SHOW;
                    effectString = performance.SID_EFFECT_PERFORM_LASER_SHOW;
                    break;
                    case 3:
                    effectFilename = performance.EFFECT_LASER_SHOW;
                    effectString = performance.SID_EFFECT_PERFORM_LASER_SHOW;
                    break;
                }
                break;
            }
            int targetType = performance.getEffectTargetType(effectId, effectLevel);
            switch (targetType)
            {
                case 1:
                performance.playEffectLoc(self, getLocation(self), effectFilename, effectString);
                break;
                case 2:
                obj_id target = getIntendedTarget(self);
                if (isIdValid(target) && isPlayer(target))
                {
                    performance.playEffectTarget(self, target, effectFilename, effectString);
                }
                else 
                {
                    target = getLookAtTarget(self);
                    if (isIdValid(target) && isPlayer(target))
                    {
                        performance.playEffectTarget(self, target, effectFilename, effectString);
                    }
                    else 
                    {
                        performance.performanceMessageToSelf(self, null, performance.SID_EFFECT_NEED_TARGET);
                    }
                }
                break;
                case 3:
                default:
                performance.playEffectTarget(self, self, effectFilename, effectString);
                break;
            }
        }
        else 
        {
            performance.performanceMessageToSelf(self, null, performance.SID_EFFECT_TOO_TIRED);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClearEffect(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, performance.VAR_PERFORM_EFFECT, 0);
        return SCRIPT_CONTINUE;
    }
    public int OnPerformanceEnd(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, performance.DANCE_HEARTBEAT_SCRIPT);
        return SCRIPT_CONTINUE;
    }
    public int handleInspireMenu(obj_id self, dictionary params) throws InterruptedException
    {
        String[] list = utils.getStringArrayScriptVar(self, performance.VAR_BUFF_LIST);
        obj_id target = utils.getObjIdScriptVar(self, performance.VAR_BUFF_TARGET);
        utils.removeScriptVar(self, performance.VAR_BUFF_LIST);
        utils.removeScriptVar(self, performance.VAR_BUFF_TARGET);
        utils.removeScriptVar(self, performance.VAR_BUFF_SUI);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (list == null || list.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        showFlyTextPrivate(target, target, new string_id("performance", list[idx]), 0.66f, colors.LIGHTPINK);
        showFlyTextPrivate(target, self, new string_id("performance", list[idx]), 0.66f, colors.LIGHTPINK);
        utils.setScriptVar(target, performance.VAR_BUFF_TYPE, list[idx]);
        int inspireBuffCrc = buff.getBuffOnTargetFromGroup(target, "inspiration");
        float currentBuffTime = 30f;
        if (utils.hasScriptVar(target, performance.VAR_PERFORM_INSPIRATION))
        {
            currentBuffTime = utils.getFloatScriptVar(target, performance.VAR_PERFORM_INSPIRATION);
        }
        if (inspireBuffCrc != 0)
        {
            buff.removeBuff(target, inspireBuffCrc);
        }
        buff.applyBuff(target, list[idx], currentBuffTime);
        return SCRIPT_CONTINUE;
    }
}
