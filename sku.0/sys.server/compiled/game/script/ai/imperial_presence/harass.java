package script.ai.imperial_presence;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.chat;
import script.library.gcw;
import script.library.ai_lib;
import script.ai.ai_combat;
import script.library.attrib;
import script.library.badge;
import script.library.factions;
import script.library.faction_perk;
import script.library.anims;
import script.library.sui;
import script.library.money;
import script.library.prose;
import script.library.group;
import script.library.regions;
import script.library.jedi;
import script.library.create;
import script.library.space_utils;
import script.library.stealth;

public class harass extends script.base_script
{
    public harass()
    {
    }
    public static final String SCRIPTVAR_FINE = "harass.fine";
    public static final String SCRIPTVAR_HARASS_BASE = "harass";
    public static final String SCRIPTVAR_TARGET = "harass.target";
    public static final String SCRIPTVAR_STATUS = "harass.status";
    public static final String SCRIPTVAR_RATING = "harass.rating";
    public static final String VOL_CHECKPOINT = "volCheckPoint";
    public static final float VOL_CHECKPOINT_RANGE = 64f;
    public static final String VOL_CITY_CHECKPOINT = "volCityCheckPoint";
    public static final float VOL_CITY_CHECKPOINT_RANGE = 25f;
    public static final String VOL_DETAIN = "volDetain";
    public static final float VOL_DETAIN_RANGE = 10f;
    public static final String STF = "imperial_presence/contraband_search";
    public static final String TBL_RANK = "datatables/faction/rank.iff";
    public static final string_id IMP_FINE_TITLE = new string_id(STF, "imp_fine_title");
    public static final string_id IMP_FINE_TEXT = new string_id(STF, "imp_fine_text");
    public static final string_id IMP_FINE_TEXT2 = new string_id(STF, "imp_fine_text2_imperial");
    public static final string_id IMP_FINE_TEXT2_REB = new string_id(STF, "imp_fine_text2_rebel");
    public static final string_id SORRY_SIR_NAME = new string_id(STF, "sorry_sir_name");
    public static final string_id SORRY_SIR = new string_id(STF, "sorry_sir");
    public static final string_id REBEL_OFFICER = new string_id(STF, "rebel_discovered_officer");
    public static final string_id IMPERIAL_FINE = new string_id(STF, "imperial_fine");
    public static final String COL_INDEX = "INDEX";
    public static final int MT_TOTAL = 2;
    public String getFactionName(obj_id self) throws InterruptedException
    {
        String tFac = factions.getFaction(self);
        if (tFac.equals(factions.FACTION_IMPERIAL))
        {
            return "imperial";
        }
        else if (tFac.equals(factions.FACTION_REBEL))
        {
            return "rebel";
        }
        else 
        {
            float imp_r = gcw.getImperialRatio(self);
            float reb_r = gcw.getRebelRatio(self);
            if (imp_r >= reb_r)
            {
                return "imperial";
            }
            else 
            {
                return "rebel";
            }
        }
    }
    public boolean isInFriendlyFaction(obj_id self, obj_id who) throws InterruptedException
    {
        String tFac = factions.getFaction(who);
        String sFac = getFactionName(self);
        if (tFac == null)
        {
            return false;
        }
        else if (tFac.equals("Rebel") && sFac.equals("rebel"))
        {
            return true;
        }
        else if (tFac.equals("Imperial") && sFac.equals("imperial"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean isOnLeaveFromFriendlyFaction(obj_id self, obj_id who) throws InterruptedException
    {
        if (factions.isOnLeave(who) && isInFriendlyFaction(self, who))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean isInEnemyFaction(obj_id self, obj_id target) throws InterruptedException
    {
        String tFac = factions.getFaction(target);
        String sFac = getFactionName(self);
        if (tFac == null)
        {
            return false;
        }
        else if (tFac.equals("Imperial") && sFac.equals("rebel"))
        {
            return true;
        }
        else if (tFac.equals("Rebel") && sFac.equals("imperial"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        region[] rgnTest = getRegionsWithBuildableAtPoint(here, regions.BUILD_FALSE);
        if (rgnTest != null)
        {
            detachScript(self, "ai.soldier");
            removeTriggerVolume(ai_lib.ALERT_VOLUME_NAME);
            removeTriggerVolume(ai_lib.AGGRO_VOLUME_NAME);
            createTriggerVolume(VOL_CITY_CHECKPOINT, VOL_CITY_CHECKPOINT_RANGE, true);
            setAttributeInterested(self, attrib.ALL);
        }
        else 
        {
            createTriggerVolume(VOL_CHECKPOINT, VOL_CHECKPOINT_RANGE, true);
            setAttributeInterested(self, attrib.ALL);
        }
        setWantSawAttackTriggers(self, false);
        if (!utils.hasScriptVar(self, gcw.SCRIPTVAR_SCAN_INTEREST))
        {
            gcw.assignScanInterests(self);
        }
        if (!hasScript(self, "systems.gcw.gcw_data_updater"))
        {
            attachScript(self, "systems.gcw.gcw_data_updater");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id who) throws InterruptedException
    {
        if (isDead(self) || isDead(who))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(who))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "Imperial.controlScore"))
        {
            return SCRIPT_CONTINUE;
        }
        if (who == self || !isPlayer(who))
        {
            return SCRIPT_CONTINUE;
        }
        if (stealth.hasServerCoverState(who))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(who, "scan_successful_2"))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(who);
        if (here == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(here.cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (volName.equals(VOL_CHECKPOINT) && (!utils.hasScriptVar(self, SCRIPTVAR_TARGET)))
        {
            if (!utils.hasScriptVar(who, "being_scanned"))
            {
                if (ai_lib.isInCombat(self))
                {
                    messageTo(self, "handleHarassTarget", null, 10f, false);
                    return SCRIPT_CONTINUE;
                }
                utils.setScriptVar(self, SCRIPTVAR_TARGET, who);
                enterHarassMode(self, who);
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        if (volName.equals(VOL_CITY_CHECKPOINT) && (!utils.hasScriptVar(self, SCRIPTVAR_TARGET)))
        {
            if (rand(0, 10) <= 6)
            {
                if (!utils.hasScriptVar(who, "being_scanned"))
                {
                    if (ai_lib.isInCombat(self))
                    {
                        messageTo(self, "handleHarassTarget", null, 10f, false);
                        return SCRIPT_CONTINUE;
                    }
                    utils.setScriptVar(self, SCRIPTVAR_TARGET, who);
                    enterHarassMode(self, who);
                    return SCRIPT_CONTINUE;
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volName, obj_id who) throws InterruptedException
    {
        if (!isIdValid(who))
        {
            return SCRIPT_CONTINUE;
        }
        if (who == self || !isPlayer(who))
        {
            return SCRIPT_CONTINUE;
        }
        int status = utils.getIntScriptVar(self, SCRIPTVAR_STATUS);
        if (volName.equals(VOL_DETAIN))
        {
            switch (status)
            {
                case 1:
                chat.publicChat(self, who, new string_id(STF, "return_request_" + getFactionName(self)));
                utils.setScriptVar(self, SCRIPTVAR_STATUS, 2);
                dictionary d = new dictionary();
                d.put("target", who);
                messageTo(self, "handleReturnRequest", d, 10f, false);
                break;
                case 3:
                chat.publicChat(self, who, new string_id(STF, "return_false_" + getFactionName(self)));
                utils.setScriptVar(self, SCRIPTVAR_STATUS, 2);
                dictionary d3 = new dictionary();
                d3.put("target", who);
                messageTo(self, "handleReturnRequest", d3, 10f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowWaiting(obj_id self, obj_id target) throws InterruptedException
    {
        obj_id harassTarget = utils.getObjIdScriptVar(self, SCRIPTVAR_TARGET);
        if (!isIdValid(harassTarget) || harassTarget != target || !isPlayer(target))
        {
            enterCheckpointMode(self);
            return SCRIPT_CONTINUE;
        }
        createTriggerVolume(VOL_DETAIN, VOL_DETAIN_RANGE, false);
        addTriggerVolumeEventSource(VOL_DETAIN, target);
        obj_id[] contents = getTriggerVolumeContents(self, VOL_DETAIN);
        if (contents != null && contents.length > 0)
        {
            if (utils.getElementPositionInArray(contents, target) > -1)
            {
                volDetainBreach(self, target);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowTargetLost(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        enterCheckpointMode(self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        cleanupHarassment(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        cleanupHarassment(self);
        return SCRIPT_CONTINUE;
    }
    public int handleHarassTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = utils.getObjIdScriptVar(self, SCRIPTVAR_TARGET);
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "handleHarassTarget", null, 10f, false);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(target) && target.isLoaded() && !isIncapacitated(target))
        {
            enterHarassMode(self, params);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            enterCheckpointMode(self);
            return SCRIPT_CONTINUE;
        }
    }
    public int handleNewHarassTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("harassTarget");
        if (ai_lib.isInCombat(self))
        {
            dictionary d6 = new dictionary();
            d6.put("harassTarget", target);
            messageTo(self, "handleNewHarassTarget", d6, 10f, false);
            return SCRIPT_CONTINUE;
        }
        enterHarassMode(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handleCheckpointMode(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id harassTarget = utils.getObjIdScriptVar(self, SCRIPTVAR_TARGET);
        if (harassTarget == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(harassTarget, "breach_protect"))
        {
            utils.removeScriptVar(harassTarget, "breach_protect");
        }
        enterCheckpointMode(self);
        return SCRIPT_CONTINUE;
    }
    public boolean enterHarassMode(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("harassTarget");
        if (target == null)
        {
            enterCheckpointMode(self);
            return false;
        }
        utils.setScriptVar(self, SCRIPTVAR_TARGET, target);
        return enterHarassMode(self, target);
    }
    public boolean enterHarassMode(obj_id self, obj_id target) throws InterruptedException
    {
        if (utils.hasScriptVar(target, "being_scanned"))
        {
            enterCheckpointMode(self);
            return false;
        }
        if (isIdValid(target) && target.isLoaded())
        {
            utils.setScriptVar(target, "being_scanned", 1);
            obj_id playerCurrentMount = getMountId(target);
            if (isIdValid(playerCurrentMount) && playerCurrentMount != null)
            {
                obj_id mountId = getMountId(target);
                if (isIdValid(mountId))
                {
                    string_id msgString = new string_id(STF, "dismount_" + getFactionName(self));
                    sendSystemMessage(target, msgString);
                    space_utils.tauntPlayer(target, self, msgString);
                    sendSystemMessage(target, new string_id(STF, "dismount"));
                    utils.dismountRiderJetpackCheck(target);
                }
            }
            messageTo(self, "followHarass", null, 1f, false);
            return true;
        }
        return false;
    }
    public int followHarass(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = utils.getObjIdScriptVar(self, SCRIPTVAR_TARGET);
        ai_lib.aiFollow(self, target);
        return SCRIPT_CONTINUE;
    }
    public void enterCheckpointMode(obj_id self) throws InterruptedException
    {
        cleanupHarassment(self);
        setMovementWalk(self);
        ai_lib.wander(self);
    }
    public void cleanupHarassment(obj_id self) throws InterruptedException
    {
        obj_id target = utils.getObjIdScriptVar(self, SCRIPTVAR_TARGET);
        if (isIdValid(target))
        {
            if (utils.hasScriptVar(target, "being_scanned"))
            {
                utils.removeScriptVar(target, "being_scanned");
                messageTo(target, "handleCleanupHarassment", null, 1, false);
            }
        }
        removeTriggerVolume(VOL_DETAIN);
        utils.removeScriptVarTree(self, SCRIPTVAR_HARASS_BASE);
    }
    public void volDetainBreach(obj_id self, obj_id who) throws InterruptedException
    {
        if (!isPlayer(who))
        {
            return;
        }
        if (utils.hasScriptVar(who, "breach_protect"))
        {
            messageTo(self, "handleCheckpointMode", null, 3f, false);
            return;
        }
        int rank = pvpGetCurrentGcwRank(who);
        int myFac = pvpGetAlignedFaction(self);
        String faction = factions.getFaction(who);
        int tFac = pvpGetAlignedFaction(who);
        if (pvpGetType(who) == PVPTYPE_NEUTRAL)
        {
            tFac = 0;
        }
        if (isInFriendlyFaction(self, who) && !isOnLeaveFromFriendlyFaction(self, who))
        {
            if (rank > 8)
            {
                doAnimationAction(self, anims.PLAYER_SALUTE2);
                String playerName = getName(who);
                prose_package officer = new prose_package();
                java.util.StringTokenizer st = new java.util.StringTokenizer(playerName, " ");
                if (st.countTokens() == 2)
                {
                    String firstName = st.nextToken();
                    String lastName = st.nextToken();
                    officer = prose.getPackage(SORRY_SIR_NAME, factions.getRankNameStringId(rank, faction), lastName);
                }
                else 
                {
                    officer = prose.getPackage(SORRY_SIR, factions.getRankNameStringId(rank, faction));
                }
                chat.publicChat(self, null, null, null, officer);
                utils.setScriptVar(who, "scan_successful", 1);
                utils.setScriptVar(who, "scan_successful_2", 1);
                utils.setScriptVar(who, "breach_protect", 1);
                messageTo(self, "handleCheckpointMode", null, 3f, false);
                return;
            }
            else if (rand(1, 2) == 2)
            {
                chat.publicChat(self, who, new string_id(STF, "business_" + getFactionName(self)));
                utils.setScriptVar(who, "scan_successful", 1);
                utils.setScriptVar(who, "scan_successful_2", 1);
                utils.setScriptVar(who, "breach_protect", 1);
                messageTo(self, "handleCheckpointMode", null, 3f, false);
                return;
            }
        }
        int status = utils.getIntScriptVar(self, SCRIPTVAR_STATUS);
        switch (status)
        {
            case 0:
            chat.publicChat(self, who, new string_id(STF, "scan_greeting_" + getFactionName(self)));
            utils.setScriptVar(self, SCRIPTVAR_STATUS, 1);
            float rating = gcw.getContrabandRating(who);
            utils.setScriptVar(self, SCRIPTVAR_RATING, rating);
            sendSystemMessage(who, new string_id(STF, "contraband_scan_" + getFactionName(self)));
            dictionary d1 = new dictionary();
            d1.put("status", 1);
            d1.put("target", who);
            messageTo(self, "handleScanComplete", d1, 15f, false);
            break;
            case 2:
            chat.publicChat(self, who, new string_id(STF, "return_thank_" + getFactionName(self)));
            utils.setScriptVar(self, SCRIPTVAR_STATUS, 3);
            sendSystemMessage(who, new string_id(STF, "contraband_scan_" + getFactionName(self)));
            dictionary d2 = new dictionary();
            d2.put("status", 3);
            d2.put("target", who);
            messageTo(self, "handleScanComplete", d2, 15f, false);
            break;
        }
    }
    public int handleScanComplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(target))
        {
            messageTo(self, "handleScanComplete", params, 5f, false);
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(target) || !target.isLoaded())
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(target) || isDead(target))
        {
            messageTo(self, "handleCheckpointMode", null, 3f, false);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(target, "breach_protect"))
        {
            utils.removeScriptVar(target, "breach_protect");
        }
        utils.removeScriptVar(target, "being_scanned");
        utils.setScriptVar(target, "scan_successful", 1);
        utils.setScriptVar(target, "scan_successful_2", 1);
        int oldStatus = params.getInt("status");
        int curStatus = utils.getIntScriptVar(self, SCRIPTVAR_STATUS);
        if (oldStatus != curStatus)
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.checkForJedi(target))
        {
            if (!isIdValid(self))
            {
                return SCRIPT_CONTINUE;
            }
            if (badge.hasBadge(target, "bdg_jedi_elder") || badge.hasBadge(target, "new_prof_jedi_master") || getState(target, STATE_GLOWING_JEDI) != 0)
            {
                chat.publicChat(target, self, new string_id(STF, "jedi_mind_trick"));
                doAnimationAction(target, anims.PLAYER_FORCE_PERSUASION);
                chat.think(self, new string_id(STF, "not_search_you"));
                dictionary jedi = new dictionary();
                jedi.put("target", target);
                messageTo(self, "handleJediMindTrick", jedi, 5f, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                chat.publicChat(target, self, new string_id(STF, "jedi_mind_trick_novice"));
                doAnimationAction(target, anims.PLAYER_FORCE_PERSUASION);
                chat.think(self, new string_id(STF, "not_search_you_novice"));
                doAnimationAction(self, anims.PLAYER_SCRATCH_HEAD);
                dictionary jedi = new dictionary();
                jedi.put("target", target);
                messageTo(self, "handleJediMindTrickNovice", jedi, 5f, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (group.isGrouped(target))
        {
            Vector members = group.getPCMembersInRange(target, 35f);
            if (members != null && members.size() > 0)
            {
                int numInGroup = members.size();
                if (numInGroup < 1)
                {
                    return SCRIPT_CONTINUE;
                }
                for (int i = 0; i < numInGroup; i++)
                {
                    obj_id thisMember = ((obj_id)members.get(i));
                    if (hasSkill(thisMember, "class_smuggler_phase1_novice") && thisMember != (target))
                    {
                        if (ai_lib.checkForSmuggler(thisMember))
                        {
                            chat.publicChat(self, target, new string_id(STF, "clean_target_" + getFactionName(self)));
                            if (getGender(self) == GENDER_MALE)
                            {
                                playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 0f);
                            }
                            removeTriggerVolume(VOL_DETAIN);
                            enterCheckpointMode(self);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        if (ai_lib.checkForSmuggler(target))
        {
            chat.publicChat(self, target, new string_id(STF, "clean_target_" + getFactionName(self)));
            if (getGender(self) == GENDER_MALE)
            {
                playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 0f);
            }
            removeTriggerVolume(VOL_DETAIN);
            enterCheckpointMode(self);
            return SCRIPT_CONTINUE;
        }
        int interests = utils.getIntScriptVar(self, gcw.SCRIPTVAR_SCAN_INTEREST);
        int playerLevel = getLevel(target);
        if (playerLevel >= 75 && isEnemyJedi(self, target) && rand(1, 5) == 1)
        {
            sendSystemMessage(target, new string_id(STF, "discovered_" + getFactionName(self)));
            chat.publicChat(self, target, new string_id(STF, "discovered_jedi_" + getFactionName(self)));
            attackFactionViolator(self, target, true);
        }
        else if (playerLevel >= 25 && utils.checkBit(interests, gcw.INTEREST_FACTION) && isInEnemyFaction(self, target) && rand(1, 2) == 1)
        {
            sendSystemMessage(target, new string_id(STF, "discovered_" + getFactionName(self)));
            chat.publicChat(self, target, new string_id(STF, "discovered_chat_" + getFactionName(self)));
            attackFactionViolator(self, target, false);
        }
        else 
        {
            float rating = utils.getFloatScriptVar(self, SCRIPTVAR_RATING);
            if (rating > rand(10f, 15f))
            {
                invokePenaltyAction(self, target);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                chat.publicChat(self, target, new string_id(STF, "clean_target_" + getFactionName(self)));
                if (getGender(self) == GENDER_MALE)
                {
                    playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 0f);
                }
            }
        }
        removeTriggerVolume(VOL_DETAIN);
        enterCheckpointMode(self);
        return SCRIPT_CONTINUE;
    }
    public boolean isEnemyJedi(obj_id self, obj_id target) throws InterruptedException
    {
        if (!utils.isProfession(target, utils.FORCE_SENSITIVE))
        {
            return false;
        }
        if ("imperial" == getFactionName(self))
        {
            return true;
        }
        else if (utils.isProfession(target, utils.FORCE_SENSITIVE) && isInEnemyFaction(self, target))
        {
            return true;
        }
        return false;
    }
    public int handleReturnRequest(obj_id self, dictionary params) throws InterruptedException
    {
        int status = utils.getIntScriptVar(self, SCRIPTVAR_STATUS);
        obj_id target = params.getObjId("target");
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (status == 2 && isIdValid(target))
        {
            String tFac = factions.getFaction(target);
            if (isInEnemyFaction(self, target))
            {
                penaltyAction(self, target, 50f);
                sendSystemMessage(target, new string_id(STF, "discovered_" + getFactionName(self)));
                attackFactionViolator(self, target, false);
                removeTriggerVolume(VOL_DETAIN);
                enterCheckpointMode(self);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(target, new string_id(STF, "ran_away_" + getFactionName(self)));
                penaltyAction(self, target, 50f);
                CustomerServiceLog("CONTRABAND_SCANNING: ", "(" + target + ")" + getFirstName(target) + " lost 50 faction by running from a scan");
                removeTriggerVolume(VOL_DETAIN);
                enterCheckpointMode(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void attackFactionViolator(obj_id self, obj_id target, boolean antiJedi) throws InterruptedException
    {
        if (isJedi(target))
        {
            jedi.doJediTEF(target);
        }
        else if (factions.isOnLeave(target))
        {
            pvpMakeCovert(target);
            CustomerServiceLog("CONTRABAND_SCANNING: ", "(" + target + ")" + getFirstName(target) + " made GCW PVP by Crackdown Scan");
        }
        String backupClass = null;
        if (getFactionName(self) == "imperial")
        {
            if (antiJedi && isEnemyJedi(self, target))
            {
                backupClass = "crackdown_elite_dark_trooper_hard";
            }
            else 
            {
                backupClass = "crackdown_storm_commando";
            }
        }
        else 
        {
            if (antiJedi && isEnemyJedi(self, target))
            {
                backupClass = "crackdown_rebel_elite_heavy_trooper_hard";
            }
            else 
            {
                backupClass = "crackdown_rebel_commando";
            }
        }
        obj_id spawnSource = getObjIdObjVar(self, "mom");
        if (isIdValid(spawnSource))
        {
            for (int i = 0; i < 3; i++)
            {
                obj_id backup = create.object(backupClass, getLocation(spawnSource));
                LOG("spawn_backup", "Spawning backup: " + backup + " I am: " + self);
                attachScript(backup, "ai.imperial_presence.harass_backup");
                setObjVar(backup, "whereToFight", getLocation(self));
                setObjVar(backup, "whoToFight", target);
                setObjVar(backup, "home", getLocation(spawnSource));
                if (i == 0)
                {
                    setObjVar(backup, "leader", 1);
                    setObjVar(backup, "faction", getFactionName(self));
                    if (antiJedi && isEnemyJedi(self, target))
                    {
                        setObjVar(backup, "jedi", 1);
                    }
                }
            }
        }
        startCombat(self, target);
    }
    public void invokePenaltyAction(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target))
        {
            return;
        }
        obj_id harassTarget = utils.getObjIdScriptVar(self, SCRIPTVAR_TARGET);
        if (!isIdValid(harassTarget) || harassTarget != target)
        {
            return;
        }
        float rating = utils.getFloatScriptVar(self, SCRIPTVAR_RATING);
        invokePenaltyAction(self, target, rating);
    }
    public void invokePenaltyAction(obj_id self, obj_id target, float rating) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target) || !target.isLoaded() || rating <= 0f)
        {
            enterCheckpointMode(self);
            return;
        }
        float amt = rating * 20;
        int FINE = (int)amt;
        String tFac = factions.getFaction(target);
        int rank = pvpGetCurrentGcwRank(target);
        if (isInEnemyFaction(self, target) && (rank > 6))
        {
            String playerName = getName(target);
            prose_package reboff = new prose_package();
            java.util.StringTokenizer st = new java.util.StringTokenizer(playerName, " ");
            reboff = prose.getPackage(new string_id(STF, "discovered_officer_" + getFactionName(self)), factions.getRankNameStringId(rank, tFac), playerName);
            sendSystemMessage(target, new string_id(STF, "discovered_" + getFactionName(self)));
            chat.publicChat(self, null, null, null, reboff);
            attackFactionViolator(self, target, false);
            messageTo(self, "handleCheckpointMode", null, 5f, false);
        }
        else if (isInFriendlyFaction(self, target))
        {
            chat.publicChat(self, target, new string_id(STF, "pay_fine_" + getFactionName(self)));
            dictionary params = new dictionary();
            params.put("target", target);
            params.put("rating", rating);
            messageTo(self, "fineImperial", params, 5f, false);
            return;
        }
        else 
        {
            if (money.hasFunds(target, MT_TOTAL, FINE))
            {
                chat.publicChat(self, target, new string_id(STF, "fined_" + getFactionName(self)));
                money.requestPayment(target, self, FINE, "fine", null);
                CustomerServiceLog("CONTRABAND_SCANNING: ", "(" + target + ")" + getFirstName(target) + " was fined " + FINE + " credits");
                utils.moneyOutMetric(self, money.ACCT_FINES, FINE);
                if (getGender(self) == GENDER_MALE)
                {
                    playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 0f);
                }
                messageTo(self, "handleCheckpointMode", null, 5f, false);
                return;
            }
            else 
            {
                chat.publicChat(self, target, new string_id(STF, "failure_to_pay_" + getFactionName(self)));
                penaltyAction(self, target, rating * 2);
                CustomerServiceLog("CONTRABAND_SCANNING: ", "(" + target + ")" + getFirstName(target) + " was fined " + rating * 2 + " Imperial Faction Points");
                if (getGender(self) == GENDER_MALE)
                {
                    playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 0f);
                }
                messageTo(self, "handleCheckpointMode", null, 5f, false);
                return;
            }
        }
        messageTo(self, "handleCheckpointMode", null, 30f, false);
    }
    public void penaltyAction(obj_id self, obj_id target, float lostFaction) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target) || !target.isLoaded() || lostFaction <= 0f)
        {
            return;
        }
        String testFaction = null;
        if (getFactionName(self) == "imperial")
        {
            testFaction = "Imperial";
        }
        else 
        {
            testFaction = "Rebel";
        }
        float curStanding = factions.getFactionStanding(target, testFaction);
        float newStanding = curStanding - lostFaction;
        String tFac = factions.getFaction(target);
        if (tFac == null)
        {
            factions.addFactionStanding(target, testFaction, -lostFaction);
            return;
        }
        if (isInFriendlyFaction(self, target))
        {
            if (newStanding < factions.FACTION_RATING_DECLARABLE_MIN)
            {
                float delta = curStanding - factions.FACTION_RATING_DECLARABLE_MIN;
                factions.addFactionStanding(target, testFaction, -delta);
            }
        }
        else 
        {
            if (newStanding < factions.FACTION_RATING_MIN)
            {
                float delta = curStanding - factions.FACTION_RATING_MIN;
                factions.addFactionStanding(target, testFaction, -delta);
                chat.publicChat(self, target, new string_id(STF, "neutral_turn_over_" + getFactionName(self)));
                return;
            }
        }
        factions.addFactionStanding(target, testFaction, -lostFaction);
    }
    public int fineImperial(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        float rating = params.getFloat("rating");
        showFineSui(target, self, rating);
        return SCRIPT_CONTINUE;
    }
    public void showFineSui(obj_id self, obj_id target, float rating) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(target))
        {
            return;
        }
        float amt = rating * 20;
        int FINE = (int)amt;
        if (money.hasFunds(self, MT_TOTAL, FINE))
        {
            int pid = sui.createSUIPage(sui.SUI_MSGBOX, target, self, "handleFineSui");
            setSUIProperty(pid, "", "Size", "350,275");
            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, localize(IMP_FINE_TITLE));
            if (getFactionName(self) == "imperial")
            {
                setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, localize(IMP_FINE_TEXT) + FINE + localize(IMP_FINE_TEXT2));
            }
            else 
            {
                setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, localize(IMP_FINE_TEXT) + FINE + localize(IMP_FINE_TEXT2_REB));
            }
            sui.msgboxButtonSetup(pid, sui.YES_NO);
            sui.showSUIPage(pid);
            utils.setScriptVar(target, SCRIPTVAR_FINE, pid);
            return;
        }
        else 
        {
            chat.publicChat(target, self, new string_id(STF, "failure_to_pay_" + getFactionName(self)));
            penaltyAction(target, self, rating * 2);
            messageTo(target, "handleCheckpointMode", null, 5f, false);
            return;
        }
    }
    public int handleFineSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = sui.getPlayerId(params);
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        float rating = utils.getFloatScriptVar(self, SCRIPTVAR_RATING);
        float amt = rating * 20;
        int FINE = (int)amt;
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            chat.publicChat(self, target, new string_id(STF, "warning_" + getFactionName(self)));
            money.requestPayment(target, self, FINE, "fine", null);
            CustomerServiceLog("CONTRABAND_SCANNING: ", "(" + target + ")" + getFirstName(target) + " was fined " + FINE + " credits");
            utils.moneyOutMetric(self, money.ACCT_FINES, FINE);
            if (getGender(self) == GENDER_MALE)
            {
                playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 0f);
            }
            int pidClose = utils.getIntScriptVar(target, SCRIPTVAR_FINE);
            cleanupImperialFine(target);
            sui.closeSUI(target, pidClose);
            messageTo(self, "handleCheckpointMode", null, 5f, false);
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_CANCEL)
        {
            chat.publicChat(self, target, new string_id(STF, "punish_" + getFactionName(self)));
            penaltyAction(self, target, rating * 2);
            CustomerServiceLog("CONTRABAND_SCANNING: ", "(" + target + ")" + getFirstName(target) + " was fined " + rating * 2 + " Imperial Faction Points");
            if (getGender(self) == GENDER_MALE)
            {
                playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 0f);
            }
            int pidClose = utils.getIntScriptVar(target, SCRIPTVAR_FINE);
            cleanupImperialFine(target);
            sui.closeSUI(target, pidClose);
            messageTo(self, "handleCheckpointMode", null, 5f, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            chat.publicChat(self, target, new string_id(STF, "punish_+" + getFactionName(self)));
            penaltyAction(self, target, rating * 2);
            CustomerServiceLog("CONTRABAND_SCANNING: ", "(" + target + ")" + getFirstName(target) + " was fined " + rating * 2 + " Imperial Faction Points");
            if (getGender(self) == GENDER_MALE)
            {
                playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 0f);
            }
            int pidClose = utils.getIntScriptVar(target, SCRIPTVAR_FINE);
            cleanupImperialFine(target);
            sui.closeSUI(target, pidClose);
            messageTo(self, "handleCheckpointMode", null, 5f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleJediMindTrick(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        doAnimationAction(self, anims.PLAYER_WAVE_ON_DIRECTING);
        chat.publicChat(self, target, new string_id(STF, "dont_search"));
        if (getGender(self) == GENDER_MALE)
        {
            playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 2f);
        }
        removeTriggerVolume(VOL_DETAIN);
        enterCheckpointMode(self);
        return SCRIPT_CONTINUE;
    }
    public int handleJediMindTrickDark(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        doAnimationAction(self, anims.PLAYER_STANDING_PLACATE);
        chat.publicChat(self, target, new string_id(STF, "dont_search_dark"));
        if (getGender(self) == GENDER_MALE)
        {
            playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 2f);
        }
        removeTriggerVolume(VOL_DETAIN);
        enterCheckpointMode(self);
        return SCRIPT_CONTINUE;
    }
    public int handleJediMindTrickNovice(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        chat.publicChat(self, target, new string_id(STF, "dont_search_novice"));
        if (getGender(self) == GENDER_MALE)
        {
            playClientEffectLoc(target, "clienteffect/stormtrp_movealng.cef", getLocation(self), 2f);
        }
        removeTriggerVolume(VOL_DETAIN);
        enterCheckpointMode(self);
        return SCRIPT_CONTINUE;
    }
    public void cleanupImperialFine(obj_id target) throws InterruptedException
    {
        utils.removeScriptVar(target, SCRIPTVAR_FINE);
    }
}
