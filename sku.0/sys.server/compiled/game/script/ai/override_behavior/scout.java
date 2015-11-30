package script.ai.override_behavior;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.gcw;
import script.library.utils;
import script.library.ai_lib;
import script.library.factions;
import script.library.colors;
import script.library.faction_perk;
import script.library.jedi;

public class scout extends script.base_script
{
    public scout()
    {
    }
    public static final String SCRIPTVAR_SCOUT_TARGET = "behavior.scout.target";
    public static final String SCRIPTVAR_SCANNED = "behavior.scout.scanned";
    public static final String SCRIPTVAR_SCAN_STATUS = "behavior.scout.scan_status";
    public static final String SCRIPTVAR_ATTACKED = "behavior.scout.attacked";
    public static final String SCRIPTVAR_CALLED_SUPPORT = "behavior.scout.calledSupport";
    public static final String CONTRABAND_SEARCH_STF = "imperial_presence/contraband_search";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(ai_lib.AGGRO_VOLUME_NAME, 0, false);
        stop(self);
        gcw.assignScanInterests(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "petBeingInitialized"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (breacher == self || ai_lib.isAiDead(breacher) || ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ai_lib.AGGRO_VOLUME_NAME))
        {
            if (!ai_lib.isInCombat(self))
            {
                int reaction = factions.getFactionReaction(self, breacher);
                if (reaction == factions.REACTION_NEGATIVE)
                {
                    return SCRIPT_OVERRIDE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTargetAssignment(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SCRIPTVAR_CALLED_SUPPORT))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("target");
        if (!isIdValid(target))
        {
            acquireNewTarget(self);
            return SCRIPT_CONTINUE;
        }
        obj_id playerCurrentMount = getMountId(target);
        if (isIdValid(playerCurrentMount))
        {
            obj_id mountId = getMountId(target);
            if (isIdValid(mountId))
            {
                sendSystemMessage(target, new string_id(CONTRABAND_SEARCH_STF, "dismount"));
            }
            utils.dismountRiderJetpackCheck(target);
        }
        if (isIdValid(target))
        {
            scoutTarget(self, target);
        }
        return SCRIPT_CONTINUE;
    }
    public void scoutTarget(obj_id npc, obj_id target) throws InterruptedException
    {
        if (!isIdValid(npc) || !isIdValid(target) || npc == target)
        {
            return;
        }
        location here = getLocation(target);
        if (here == null)
        {
            return;
        }
        if (isIdValid(here.cell))
        {
            return;
        }
        utils.setScriptVar(npc, SCRIPTVAR_SCOUT_TARGET, target);
        if (ai_lib.isInCombat(npc))
        {
            return;
        }
        ai_lib.aiFollow(npc, target, 32f, 74f);
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SCRIPTVAR_ATTACKED))
        {
            return SCRIPT_CONTINUE;
        }
        string_id msg = new string_id(CONTRABAND_SEARCH_STF, "probot_distress_fly");
        showFlyText(self, msg, 1.0f, colors.TOMATO);
        int actionCode = gcw.AC_ATTACK;
        dictionary d1 = new dictionary();
        d1.put("actionCode", actionCode);
        d1.put("target", attacker);
        messageTo(self, "callLambdaSupport", d1, 30f, false);
        utils.setScriptVar(self, SCRIPTVAR_ATTACKED, 1);
        acquireNewTarget(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SCRIPTVAR_ATTACKED))
        {
            return SCRIPT_CONTINUE;
        }
        string_id msg = new string_id(CONTRABAND_SEARCH_STF, "probot_distress_fly");
        showFlyText(self, msg, 1.0f, colors.TOMATO);
        int actionCode = gcw.AC_ATTACK;
        dictionary d1 = new dictionary();
        d1.put("actionCode", actionCode);
        d1.put("target", attacker);
        messageTo(self, "callLambdaSupport", d1, 30f, false);
        messageTo(self, "reportActivities", d1, 35, false);
        utils.setScriptVar(self, SCRIPTVAR_ATTACKED, 1);
        acquireNewTarget(self);
        return SCRIPT_CONTINUE;
    }
    public int OnFollowTargetLost(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        acquireNewTarget(self);
        return SCRIPT_CONTINUE;
    }
    public int OnFollowWaiting(obj_id self, obj_id target) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_SCAN_STATUS))
        {
            Vector scanned = utils.getResizeableObjIdArrayScriptVar(self, SCRIPTVAR_SCANNED);
            if (scanned != null && scanned.size() > 0)
            {
                if (utils.getElementPositionInArray(scanned, target) > -1)
                {
                    acquireNewTarget(self);
                    return SCRIPT_CONTINUE;
                }
            }
            sendSystemMessage(target, new string_id(CONTRABAND_SEARCH_STF, "probe_scan"));
            string_id msg = new string_id(CONTRABAND_SEARCH_STF, "probe_scan_fly");
            showFlyText(self, msg, 1.0f, colors.TOMATO);
            utils.setScriptVar(self, SCRIPTVAR_SCAN_STATUS, target);
            dictionary d = new dictionary();
            d.put("target", target);
            messageTo(self, "handleScanComplete", d, 60f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleScanComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPTVAR_SCAN_STATUS);
        if (utils.hasScriptVar(self, SCRIPTVAR_CALLED_SUPPORT) || ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("target");
        obj_id oldTarget = utils.getObjIdScriptVar(self, SCRIPTVAR_SCOUT_TARGET);
        if (!isIdValid(target) || target != oldTarget)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(target, "scan_successful", 1);
        if (ai_lib.checkForSmuggler(target))
        {
            acquireNewTarget(self);
            return SCRIPT_CONTINUE;
        }
        int myFac = pvpGetAlignedFaction(self);
        int tFac = pvpGetAlignedFaction(target);
        if (pvpAreFactionsOpposed(myFac, tFac))
        {
            if (isPlayer(target) && pvpGetType(target) == PVPTYPE_DECLARED)
            {
                string_id msg = new string_id(CONTRABAND_SEARCH_STF, "probot_support_fly");
                showFlyText(self, msg, 1.0f, colors.TOMATO);
                utils.setScriptVar(target, "scan_successful", 1);
                int actionCode = gcw.AC_ATTACK;
                dictionary d1 = new dictionary();
                d1.put("actionCode", actionCode);
                d1.put("target", target);
                messageTo(self, "callLambdaSupport", d1, 10f, false);
                acquireNewTarget(self);
                return SCRIPT_CONTINUE;
            }
        }
        float rating = gcw.scan(self, target);
        Vector scanned = utils.getResizeableObjIdArrayScriptVar(self, SCRIPTVAR_SCANNED);
        scanned = utils.addElement(scanned, target);
        if (scanned != null && scanned.size() > 0)
        {
            utils.setScriptVar(self, SCRIPTVAR_SCANNED, scanned);
        }
        if (rating > 15f)
        {
            sendSystemMessage(target, new string_id(CONTRABAND_SEARCH_STF, "probe_scan_positive"));
            int actionCode = gcw.AC_SCAN;
            dictionary d1 = new dictionary();
            d1.put("actionCode", actionCode);
            d1.put("target", target);
            messageTo(self, "callLambdaSupport", d1, 10f, false);
            acquireNewTarget(self);
        }
        else 
        {
            sendSystemMessage(target, new string_id(CONTRABAND_SEARCH_STF, "probe_scan_negative"));
            acquireNewTarget(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFleeTargetLost(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            obj_id target = utils.getObjIdScriptVar(self, SCRIPTVAR_SCOUT_TARGET);
            if (!isIdValid(target) || !exists(target) || !target.isLoaded())
            {
                acquireNewTarget(self);
            }
            else 
            {
                scoutTarget(self, target);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void acquireNewTarget(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return;
        }
        if (utils.hasScriptVar(self, SCRIPTVAR_CALLED_SUPPORT))
        {
            return;
        }
        utils.removeScriptVar(self, SCRIPTVAR_SCOUT_TARGET);
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "handleNeedTarget", null, 10f, false);
            return;
        }
        obj_id[] players = getPlayerCreaturesInRange(self, 100f);
        if (players == null || players.length == 0)
        {
            destroyObject(self);
            return;
        }
        utils.removeScriptVar(self, SCRIPTVAR_ATTACKED);
        Vector scanned = utils.getResizeableObjIdArrayScriptVar(self, SCRIPTVAR_SCANNED);
        Vector unscanned = utils.removeElements(new Vector(Arrays.asList(players)), scanned);
        if (unscanned != null && unscanned.size() > 0)
        {
            obj_id target = ((obj_id)unscanned.get(rand(0, unscanned.size() - 1)));
            if (isIdValid(target) && (!utils.hasScriptVar(target, "scan_successful")))
            {
                scoutTarget(self, target);
                return;
            }
        }
        ai_lib.wander(self);
        messageTo(self, "handleNeedTarget", null, 15f, false);
    }
    public int handleNeedTarget(obj_id self, dictionary params) throws InterruptedException
    {
        acquireNewTarget(self);
        return SCRIPT_CONTINUE;
    }
    public int callLambdaSupport(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("target");
        int actionCode = params.getInt("actionCode");
        if (!isIdValid(self) || !isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, SCRIPTVAR_CALLED_SUPPORT))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, SCRIPTVAR_CALLED_SUPPORT, getGameTime());
        dictionary d = new dictionary();
        d.put("actionCode", actionCode);
        d.put("actionTarget", target);
        if (actionCode == gcw.AC_SCAN)
        {
            int interests = utils.getIntScriptVar(self, gcw.SCRIPTVAR_SCAN_INTEREST);
            d.put("scanInterests", interests);
        }
        location there = getLocation(target);
        if (there == null)
        {
            utils.removeScriptVar(self, SCRIPTVAR_CALLED_SUPPORT);
            return SCRIPT_CONTINUE;
        }
        location tmp = utils.getRandomLocationInRing(there, 5f, 10f);
        if (tmp != null)
        {
            there = (location)tmp.clone();
        }
        if (there != null)
        {
            gcw.spawnViaLambdaPerGeo(there, d);
        }
        messageTo(self, "handleClearSupportCall", null, 180f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleClearSupportCall(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "lambda_strike"))
        {
            utils.removeScriptVar(self, "lambda_strike");
        }
        utils.removeScriptVar(self, SCRIPTVAR_CALLED_SUPPORT);
        return SCRIPT_CONTINUE;
    }
    public int reportActivities(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("target");
        int actionCode = params.getInt("actionCode");
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "lambda_strike"))
        {
            return SCRIPT_CONTINUE;
        }
        if (target == null)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(target, new string_id(CONTRABAND_SEARCH_STF, "report_activities"));
        utils.setScriptVar(self, "lambda_strike", getGameTime());
        if (!isJedi(target))
        {
            pvpMakeDeclared(target);
            CustomerServiceLog("CONTRABAND_SCANNING: ", "%TU made Overt by Probot Scan", target, null);
        }
        else 
        {
            jedi.doJediTEF(target);
        }
        return SCRIPT_CONTINUE;
    }
}
