package script.player.skill;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.performance;
import script.library.utils;
import script.library.prose;
import script.library.sui;

public class performcommands extends script.base_script
{
    public performcommands()
    {
    }
    public static final String MIN_PERFORM_DELAY = "performance.performance_delay";
    public static final String STF = "performance";
    public static final String SCRIPT_BUFF_BUILDER_RESPONSE = "systems.buff_builder.buff_builder_response";
    public static final String SCRIPT_BUFF_BUILDER_CANCEL = "systems.buff_builder.buff_builder_cancel";
    public static final string_id SID_PERFORMANCE_DELAY = new string_id(STF, "performance_delay");
    public static final int PERFORM_DELAY = 15;
    public int cmdStartMusic(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.startMusic(self, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdStartBand(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.startBand(self, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdStopMusic(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.stopMusic(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdStopBand(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.stopBand(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdPauseMusic(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.specialFlourish(self, performance.SPECIAL_FLOURISH_PAUSE_MUSIC);
        return SCRIPT_CONTINUE;
    }
    public int cmdChangeMusic(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.changeMusic(self, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdChangeBandMusic(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.changeBandMusic(self, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdCoverCharge(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int val = utils.stringToInt(params);
        if (val > 0)
        {
            performance.covercharge(self, val);
        }
        else 
        {
            if (utils.hasScriptVar(self, performance.VAR_PERFORM_COVERCHARGE))
            {
                utils.removeScriptVar(self, performance.VAR_PERFORM_COVERCHARGE);
                sendSystemMessage(self, performance.SID_CC_STOP_CHARGE);
            }
            else 
            {
                String title = "@performance:cc_set_title";
                String prompt = "@performance:cc_set_prompt";
                sui.inputbox(self, self, prompt, title, "HandleSetCoverCharge", "");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdStartDance(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id[] members = performance.getDanceMembers(self);
        if (members == null || members.length <= 0 || hasObjVar(self, performance.VAR_PERFORM_NO_GROUP_DANCE))
        {
            performance.startDance(self, params, true);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]) || !exists(members[i]) || hasObjVar(members[i], performance.VAR_PERFORM_NO_GROUP_DANCE))
            {
                continue;
            }
            if (self == members[i])
            {
                if (params != null && params.length() > 0)
                {
                    performance.performanceMessageToSelf(self, self, new string_id("performance", "start_dance"));
                }
                performance.startDance(self, params, true);
            }
            else 
            {
                if (params != null && params.length() > 0)
                {
                    performance.performanceMessageToSelf(members[i], self, new string_id("performance", "start_dance"));
                }
                performance.startDance(members[i], params, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdStopDance(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id[] members = performance.getDanceMembers(self);
        if (members == null || members.length <= 0 || hasObjVar(self, performance.VAR_PERFORM_NO_GROUP_DANCE))
        {
            performance.stopDance(self);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]) || !exists(members[i]) || hasObjVar(members[i], performance.VAR_PERFORM_NO_GROUP_DANCE))
            {
                continue;
            }
            performance.performanceMessageToSelf(members[i], self, new string_id("performance", "stop_dance"));
            performance.stopDance(members[i]);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdChangeDance(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id[] members = performance.getDanceMembers(self);
        if (members == null || members.length <= 0 || hasObjVar(self, performance.VAR_PERFORM_NO_GROUP_DANCE))
        {
            performance.changeDance(self, params, true);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]) || !exists(members[i]) || hasObjVar(members[i], performance.VAR_PERFORM_NO_GROUP_DANCE))
            {
                continue;
            }
            if (self == members[i])
            {
                if (params != null && params.length() > 0)
                {
                    performance.performanceMessageToSelf(self, self, new string_id("performance", "change_dance"));
                }
                performance.changeDance(self, params, true);
            }
            else 
            {
                if (params != null && params.length() > 0)
                {
                    performance.performanceMessageToSelf(members[i], self, new string_id("performance", "change_dance"));
                }
                performance.changeDance(members[i], params, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdPauseDance(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id[] members = performance.getDanceMembers(self);
        if (members == null || members.length <= 0 || hasObjVar(self, performance.VAR_PERFORM_NO_GROUP_DANCE))
        {
            performance.specialFlourish(self, performance.SPECIAL_FLOURISH_PAUSE_MUSIC);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0, j = members.length; i < j; i++)
        {
            if (!isIdValid(members[i]) || !exists(members[i]) || hasObjVar(members[i], performance.VAR_PERFORM_NO_GROUP_DANCE))
            {
                continue;
            }
            performance.specialFlourish(members[i], performance.SPECIAL_FLOURISH_PAUSE_MUSIC);
        }
        return SCRIPT_CONTINUE;
    }
    public int failDance(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.performanceMessageToSelf(self, null, performance.SID_DANCE_FAIL);
        return SCRIPT_CONTINUE;
    }
    public int cmdGroupDance(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (hasObjVar(self, performance.VAR_PERFORM_NO_GROUP_DANCE))
        {
            sendSystemMessage(self, performance.SID_GROUP_DANCE_ON);
            removeObjVar(self, performance.VAR_PERFORM_NO_GROUP_DANCE);
        }
        else 
        {
            sendSystemMessage(self, performance.SID_GROUP_DANCE_OFF);
            setObjVar(self, performance.VAR_PERFORM_NO_GROUP_DANCE, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int failMusic(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.performanceMessageToSelf(self, null, performance.SID_MUSIC_FAIL);
        return SCRIPT_CONTINUE;
    }
    public int cmdFlourish(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int val = utils.stringToInt(params);
        if (val != -1)
        {
            performance.flourish(self, val);
        }
        else 
        {
            performance.performanceMessageToSelf(self, null, performance.SID_FLOURISH_FORMAT);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdBandFlourish(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() < 1)
        {
            performance.performanceMessageToSelf(self, null, performance.SID_BAND_FLOURISH_FORMAT);
            return SCRIPT_CONTINUE;
        }
        String tmp = st.nextToken();
        int val = utils.stringToInt(tmp);
        if (val != -1)
        {
            String instrument = null;
            if (st.hasMoreTokens())
            {
                instrument = toLower(st.nextToken());
            }
            performance.bandFlourish(self, val, instrument);
        }
        else 
        {
            tmp = toLower(tmp);
            if (tmp.equals("on"))
            {
                if (hasObjVar(self, performance.VAR_PERFORM_NO_BAND_FLOURISH))
                {
                    removeObjVar(self, performance.VAR_PERFORM_NO_BAND_FLOURISH);
                }
                performance.performanceMessageToSelf(self, null, performance.SID_BAND_FLOURISH_ON);
            }
            else if (tmp.equals("off"))
            {
                setObjVar(self, performance.VAR_PERFORM_NO_BAND_FLOURISH, true);
                performance.performanceMessageToSelf(self, null, performance.SID_BAND_FLOURISH_OFF);
            }
            else 
            {
                if (hasObjVar(self, performance.VAR_PERFORM_NO_BAND_FLOURISH))
                {
                    performance.performanceMessageToSelf(self, null, performance.SID_BAND_FLOURISH_STATUS_OFF);
                }
                else 
                {
                    performance.performanceMessageToSelf(self, null, performance.SID_BAND_FLOURISH_STATUS_ON);
                }
                if (!tmp.equals("status"))
                {
                    performance.performanceMessageToSelf(self, null, performance.SID_BAND_FLOURISH_FORMAT);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdBandPause(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() < 1)
        {
            performance.performanceMessageToSelf(self, null, performance.SID_BAND_PAUSE_FORMAT);
            return SCRIPT_CONTINUE;
        }
        String instrument = st.nextToken();
        if ((toLower(instrument)).equals("all"))
        {
            obj_id[] members = performance.getBandMembers(self);
            if (members == null || members.length < 1)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < members.length; ++i)
            {
                performance.specialFlourish(members[i], performance.SPECIAL_FLOURISH_PAUSE_MUSIC);
            }
            return SCRIPT_CONTINUE;
        }
        performance.bandFlourish(self, performance.SPECIAL_FLOURISH_PAUSE_MUSIC, instrument);
        return SCRIPT_CONTINUE;
    }
    public int cmdBandSolo(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() < 1)
        {
            performance.performanceMessageToSelf(self, null, performance.SID_BAND_SOLO_FORMAT);
            return SCRIPT_CONTINUE;
        }
        String instrument = st.nextToken();
        obj_id[] members = performance.getBandMembers(self);
        if (members == null || members.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < members.length; ++i)
        {
            if (instrument != null)
            {
                int instrumentNumMember = getInstrumentAudioId(members[i]);
                int instrumentNumSupplied = performance.lookupInstrumentNumber(members[i], instrument);
                boolean hasRightInstrument = true;
                if (instrumentNumMember == instrumentNumSupplied)
                {
                    hasRightInstrument = false;
                }
                else 
                {
                    String instrumentWithHue = instrument + "_hue";
                    instrumentNumSupplied = performance.lookupInstrumentNumber(members[i], instrumentWithHue);
                    if ((instrumentNumMember == instrumentNumSupplied))
                    {
                        hasRightInstrument = false;
                    }
                }
                if (hasRightInstrument)
                {
                    performance.specialFlourish(members[i], performance.SPECIAL_FLOURISH_PAUSE_MUSIC);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSpotLight(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int level = utils.stringToInt(params);
        performance.effect(self, performance.PERFORMANCE_EFFECT_SPOT_LIGHT, level);
        return SCRIPT_CONTINUE;
    }
    public int cmdColorLight(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int level = utils.stringToInt(params);
        performance.effect(self, performance.PERFORMANCE_EFFECT_COLOR_LIGHTS, level);
        return SCRIPT_CONTINUE;
    }
    public int cmdDazzle(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int level = utils.stringToInt(params);
        performance.effect(self, performance.PERFORMANCE_EFFECT_DAZZLE, level);
        return SCRIPT_CONTINUE;
    }
    public int cmdDistract(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int level = utils.stringToInt(params);
        performance.effect(self, performance.PERFORMANCE_EFFECT_DISTRACT, level);
        return SCRIPT_CONTINUE;
    }
    public int cmdSmokeBomb(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int level = utils.stringToInt(params);
        performance.effect(self, performance.PERFORMANCE_EFFECT_SMOKE_BOMB, level);
        return SCRIPT_CONTINUE;
    }
    public int cmdFireJet(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int level = utils.stringToInt(params);
        performance.effect(self, performance.PERFORMANCE_EFFECT_FIRE_JETS, level);
        return SCRIPT_CONTINUE;
    }
    public int cmdVentriloquism(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int level = utils.stringToInt(params);
        performance.effect(self, performance.PERFORMANCE_EFFECT_VENTRILOQUISM, level);
        return SCRIPT_CONTINUE;
    }
    public int cmdCenterStage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.effect(self, performance.PERFORMANCE_EFFECT_CENTER_STAGE, 1);
        return SCRIPT_CONTINUE;
    }
    public int cmdColorSwirl(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.effect(self, performance.PERFORMANCE_EFFECT_COLOR_SWIRL, 1);
        return SCRIPT_CONTINUE;
    }
    public int cmdDanceFloor(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.effect(self, performance.PERFORMANCE_EFFECT_DANCE_FLOOR, 1);
        return SCRIPT_CONTINUE;
    }
    public int cmdFeaturedSolo(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.effect(self, performance.PERFORMANCE_EFFECT_FEATURED_SOLO, 1);
        return SCRIPT_CONTINUE;
    }
    public int cmdFireJet2(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.effect(self, performance.PERFORMANCE_EFFECT_FIRE_JETS2, 1);
        return SCRIPT_CONTINUE;
    }
    public int cmdLaserShow(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.effect(self, performance.PERFORMANCE_EFFECT_LASER_SHOW, 1);
        return SCRIPT_CONTINUE;
    }
    public int cmdListen(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.listen(self, target);
        return SCRIPT_CONTINUE;
    }
    public int cmdStopListening(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.listen(self, null);
        return SCRIPT_CONTINUE;
    }
    public int cmdWatch(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.watch(self, target);
        return SCRIPT_CONTINUE;
    }
    public int cmdStopWatching(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.watch(self, null);
        return SCRIPT_CONTINUE;
    }
    public int cmdDenyService(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.denyService(self, target);
        return SCRIPT_CONTINUE;
    }
    public int cmdSetPerformanceBuffTarget(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.performanceSetPerformanceBuffTarget(self, target);
        return SCRIPT_CONTINUE;
    }
    public int cmdStartJuggling(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.startJuggling(self, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdStopJuggling(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.stopJuggling(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdChangeJuggle(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.changeJuggle(self, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdPauseJuggling(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.specialFlourish(self, performance.SPECIAL_FLOURISH_PAUSE_MUSIC);
        return SCRIPT_CONTINUE;
    }
    public int failJuggling(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        performance.performanceMessageToSelf(self, null, performance.SID_JUGGLE_FAIL);
        return SCRIPT_CONTINUE;
    }
    public int cmdInspire(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id inspireTarget = getIntendedTarget(self);
        if (!isIdValid(inspireTarget) || !isPlayer(inspireTarget))
        {
            inspireTarget = target;
            if (!isIdValid(inspireTarget))
            {
                inspireTarget = self;
            }
            else if (!isPlayer(inspireTarget))
            {
                sendSystemMessage(self, new string_id("performance", "inspire_invalid_target"));
                return SCRIPT_CONTINUE;
            }
        }
        boolean inspiringSelf = (inspireTarget == self);
        if (hasScript(inspireTarget, SCRIPT_BUFF_BUILDER_CANCEL))
        {
            sendSystemMessage(self, new string_id("spam", "already_being_inspired"));
            return SCRIPT_CONTINUE;
        }
        if (!inspiringSelf)
        {
            if (hasScript(self, performance.DANCE_HEARTBEAT_SCRIPT))
            {
                obj_id watched = getPerformanceWatchTarget(target);
                if (watched != self)
                {
                    sendSystemMessage(self, new string_id("performance", "insp_buff_must_watch"));
                    return SCRIPT_CONTINUE;
                }
            }
            else if (hasScript(self, performance.MUSIC_HEARTBEAT_SCRIPT))
            {
                obj_id listened = getPerformanceListenTarget(target);
                if (listened != self)
                {
                    sendSystemMessage(self, new string_id("performance", "insp_buff_must_listen"));
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                sendSystemMessage(self, new string_id("performance", "insp_buff_must_perform"));
                return SCRIPT_CONTINUE;
            }
        }
        attachScript(self, SCRIPT_BUFF_BUILDER_RESPONSE);
        attachScript(inspireTarget, SCRIPT_BUFF_BUILDER_CANCEL);
        buffBuilderStart(self, inspireTarget);
        return SCRIPT_CONTINUE;
    }
}
