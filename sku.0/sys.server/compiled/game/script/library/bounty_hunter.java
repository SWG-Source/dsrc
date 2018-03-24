package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.anims;
import script.library.chat;
import script.library.money;
import script.library.prose;
import script.library.utils;

public class bounty_hunter extends script.base_script
{
    public bounty_hunter()
    {
    }
    public static final String[] LOW_PAYOUT_COMMS = 
    {
        "pp_small_1",
        "pp_small_2",
        "pp_small_3",
        "pp_small_4",
        "pp_small_5"
    };
    public static final String[] MEDIUM_PAYOUT_COMMS = 
    {
        "pp_normal_1",
        "pp_normal_2",
        "pp_normal_3",
        "pp_normal_4",
        "pp_normal_5"
    };
    public static final String[] HIGH_PAYOUT_COMMS = 
    {
        "pp_big_1",
        "pp_big_2",
        "pp_big_3",
        "pp_big_4",
        "pp_big_5"
    };
    public static final String[] TALKING_COMM_CHARACTER = 
    {
        "object/mobile/dressed_tatooine_jabba_thug.iff",
        "object/mobile/dressed_tatooine_jabba_thief.iff",
        "object/mobile/dressed_tatooine_jabba_henchman.iff",
        "object/mobile/dressed_tatooine_jabba_enforcer.iff",
        "object/mobile/ephant_mon.iff",
        "object/mobile/dressed_tatooine_jabba_assassin.iff"
    };
    public static final int BOUNTY_PAYOUT_AMOUNT_MINIMUM = 50;
    public static final int BOUNTY_PAYOUT_AMOUNT_MAXIMUM = 2000;
    public static final boolean BOUNTY_DO_FREQUENCY_ADJUSTER = false;
    public static final int BOUNTY_FREQUENCY = 60;
    public static final float BOUNTY_PAYOUT_ADJUSTER = 0f;
    public static final float BOUNTY_COLLECT_TIME_LIMIT = 600.0f;
    public static final boolean BOUNTY_DO_LEVEL_ADJUSTER = true;
    public static final int BOUNTY_FLOOD_CONTROL_DELAY = 60;
    public static final int BOUNTY_MISSION_TIME_LIMIT = 259200;
    public static final int MAX_BOUNTY = 2000000000;
    public static final int MAX_BOUNTY_SET = 1000000;
    public static final int MIN_BOUNTY_SET = 20000;
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String BOUNTY_CHECK_TABLE = "datatables/missions/bounty/bounty_check.iff";
    public static final String STF = "bounty_hunter";
    public static final string_id PROSE_NO_BOUNTY_MINUTE = new string_id(STF, "no_bounties_while");
    public static final string_id PROSE_NO_BOUNTY_SECONDS = new string_id(STF, "no_bounties_soon");
    public static final string_id PROSE_NO_BOUNTY = new string_id(STF, "prose_no_bounty");
    public static final string_id NO_BOUNTY_TARGET = new string_id(STF, "no_bounty_target");
    public static final string_id NO_BOUNTY_TARGET_ALREADY = new string_id(STF, "no_bounty_target_already");
    public static final string_id NO_BOUNTY_PLAYER = new string_id(STF, "no_bounty_player");
    public static final string_id BOUNTY_TOO_LOW_LEVEL = new string_id(STF, "no_bounty_too_low");
    public static final string_id FUGITIVE = new string_id(STF, "fugitive");
    public static final string_id BOUNTY_ALREADY = new string_id(STF, "bounty_already_issued");
    public static final string_id BOUNTY_CHECK_PAYMENT = new string_id(STF, "bounty_check_payment");
    public static final string_id STF_NO_BOUNTIES = new string_id(STF, "flood_control");
    public static final string_id ALREADY_HAVE_TARGET = new string_id(STF, "already_have_target");
    public static final string_id ALREADY_BEING_HUNTED = new string_id(STF, "already_being_hunted");
    public static final string_id TARGET_COLLECTING_BOUNTY = new string_id(STF, "target_collecting_bounty");
    public static final string_id BOUNTY_FAILED_HUNTER = new string_id(STF, "bounty_failed_hunter");
    public static final string_id BOUNTY_FAILED_TARGET = new string_id(STF, "bounty_failed_target");
    public static final string_id BOUNTY_SUCCESS_HUNTER = new string_id(STF, "bounty_success_hunter");
    public static final string_id BOUNTY_SUCCESS_TARGET = new string_id(STF, "bounty_success_target");
    public static final boolean CONST_FLAG_DO_LOGGING = true;
    public static final int DROID_PROBOT = 1;
    public static final int DROID_SEEKER = 2;
    public static final int DROID_TRACK_TARGET = 1;
    public static final int DROID_FIND_TARGET = 2;
    public static void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/bounty_hunter.scriptlib/" + section, message);
        }
    }
    public static boolean isSpammingBountyCheck(obj_id player, boolean sayProse) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (utils.hasScriptVar(player, "bountyCheckFloodControl"))
        {
            int storedTime = utils.getIntScriptVar(player, "bountyCheckFloodControl");
            int currentTime = getGameTime();
            if (storedTime > currentTime)
            {
                int timeDelta = storedTime - currentTime;
                String timeScale = "seconds";
                if (sayProse)
                {
                    if (timeDelta / 60 > 1)
                    {
                        timeDelta = (int)timeDelta / 60;
                        timeScale = "minutes";
                        String talkingCharacter = TALKING_COMM_CHARACTER[(rand(0, TALKING_COMM_CHARACTER.length - 1))];
                        prose_package pp = new prose_package();
                        pp.stringId = PROSE_NO_BOUNTY_MINUTE;
                        pp.digitInteger = timeDelta;
                        commPlayer(player, player, pp, talkingCharacter);
                    }
                    else 
                    {
                        sendSystemMessage(player, PROSE_NO_BOUNTY_SECONDS);
                    }
                }
                return true;
            }
        }
        return false;
    }
    public static boolean canCheckForBounty(obj_id player, obj_id target) throws InterruptedException
    {
        if (isSpammingBountyCheck(player, true))
        {
            return false;
        }
        if (!isIdValid(target))
        {
            debugLogging("//// canCheckForBounty: ", "////>>>> target invalid");
            return false;
        }
        if (target == player)
        {
            debugLogging("//// canCheckForBounty: ", "////>>>> target is yourself");
            return false;
        }
        if (isDead(target) || isIncapacitated(target))
        {
            debugLogging("//// canCheckForBounty: ", "////>>>> target is is dead, or incapped, or not an npc");
            return false;
        }
        obj_id container = getContainedBy(target);
        if (isPlayer(container))
        {
            debugLogging("//// canCheckForBounty: ", "////>>>> target is is in a container");
            return false;
        }
        if (utils.hasScriptVar(target, "noBountyCheck"))
        {
            sendSystemMessage(player, NO_BOUNTY_TARGET);
            debugLogging("//// canCheckForBounty: ", "////>>>> target has already been checked for bounty");
            return false;
        }
        if (isPlayer(target))
        {
            sendSystemMessage(player, NO_BOUNTY_PLAYER);
            debugLogging("//// canCheckForBounty: ", "////>>>> not an npc... !isNpc");
            return false;
        }
        else 
        {
            if (!ai_lib.isNpc(target))
            {
                sendSystemMessage(player, NO_BOUNTY_TARGET);
                debugLogging("//// canCheckForBounty: ", "////>>>> not an npc... !isNpc");
                return false;
            }
            String targetName = getCreatureName(target);
            if (utils.dataTableGetInt(CREATURE_TABLE, targetName, "difficultyClass") != 0)
            {
                sendSystemMessage(player, NO_BOUNTY_TARGET);
                debugLogging("//// canCheckForBounty: ", "////>>>> ou can't attack that... elite or boss");
                return false;
            }
            if (!pvpCanAttack(player, target))
            {
                sendSystemMessage(player, NO_BOUNTY_TARGET);
                debugLogging("//// canCheckForBounty: ", "////>>>> ou can't attack that... !pvpCanAttack");
                return false;
            }
            if (utils.hasScriptVar(target, "bountyCheck"))
            {
                obj_id sameBountyHunter = utils.getObjIdScriptVar(target, "bountyCheck");
                if (sameBountyHunter == player)
                {
                    sendSystemMessage(player, BOUNTY_ALREADY);
                    debugLogging("//// canCheckForBounty: ", "////>>>> target already has a bounty");
                    return false;
                }
            }
            if (hasObjVar(target, "noBountyCheck"))
            {
                sendSystemMessage(player, NO_BOUNTY_TARGET);
                debugLogging("//// canCheckForBounty: ", "////>>>> target already has a bounty");
                return false;
            }
            int targetLevel = getLevel(target);
            if (targetLevel < 70)
            {
                sendSystemMessage(player, BOUNTY_TOO_LOW_LEVEL);
                debugLogging("//// canCheckForBounty: ", "////>>>> target is too easy to give you a bounty on");
                return false;
            }
        }
        return true;
    }
    public static boolean checkForPresenceOfBounty(obj_id player) throws InterruptedException
    {
        return checkForPresenceOfBounty(player, obj_id.NULL_ID);
    }
    public static boolean checkForPresenceOfBounty(obj_id player, obj_id target) throws InterruptedException
    {
        debugLogging("//// checkForPresenceOfBounty: ", "////>>>> entered");
        if (isIdValid(target) && isPlayer(target))
        {
            sendSystemMessage(player, NO_BOUNTY_TARGET);
        }
        else 
        {
            if (!isGod(player))
            {
                if (11 > rand(1, 100))
                {
                    sendSystemMessage(player, FUGITIVE);
                    return true;
                }
                sendSystemMessage(player, NO_BOUNTY_TARGET);
                return false;
            }
            debugSpeakMsg(player, "Bypassing random check due to God mode");
            sendSystemMessage(player, FUGITIVE);
            return true;
        }
        return false;
    }
    public static void spawnFugitive(obj_id player, location spawnLoc) throws InterruptedException
    {
        String[] fugitive = dataTableGetStringColumnNoDefaults(BOUNTY_CHECK_TABLE, "fugitive");
        String mob = new String();
        if (dataTableHasColumn(BOUNTY_CHECK_TABLE, "chance"))
        {
            int[] weights = dataTableGetIntColumnNoDefaults(BOUNTY_CHECK_TABLE, "chance");
            if (weights != null && weights.length > 0)
            {
                int total = 0;
                for (int i = 0; i < weights.length; i++)
                {
                    total += weights[i];
                }
                if (total < 1)
                {
                    total = 1;
                }
                int roll = rand(1, total);
                int idx = -1;
                int low_range = 0;
                for (int j = 0; j < weights.length; j++)
                {
                    int high_range = low_range + weights[j];
                    if (roll > low_range && roll <= high_range)
                    {
                        idx = j;
                        break;
                    }
                    low_range = high_range;
                }
                if (idx >= 0)
                {
                    if (idx >= fugitive.length)
                    {
                        idx = fugitive.length - 1;
                    }
                    mob = fugitive[idx];
                    obj_id spawnedFugitive = create.object(mob, spawnLoc);
                    createFugitive(player, spawnedFugitive);
                }
                else 
                {
                    return;
                }
            }
            else 
            {
                int pick = rand(0, fugitive.length - 1);
                mob = fugitive[pick];
                obj_id spawnedFugitive = create.object(mob, spawnLoc);
                createFugitive(player, spawnedFugitive);
            }
        }
        else 
        {
            int pick = rand(0, fugitive.length - 1);
            mob = fugitive[pick];
            obj_id spawnedFugitive = create.object(mob, spawnLoc);
            createFugitive(player, spawnedFugitive);
        }
        return;
    }
    public static void createFugitive(obj_id player, obj_id spawnedFugitive) throws InterruptedException
    {
        playMusic(player, spawnedFugitive, "sound/mus_duel_of_the_fates_lcv.snd", 0, false);
        pvpSetAlignedFaction(spawnedFugitive, (84709322));
        pvpSetPermanentPersonalEnemyFlag(spawnedFugitive, player);
        pvpSetPermanentPersonalEnemyFlag(player, spawnedFugitive);
        startCombat(spawnedFugitive, player);
        addHate(spawnedFugitive, player, 1000.0f);
        setObjVar(spawnedFugitive, "soloCollection", player);
        return;
    }
    public static void payBountyCheckReward(obj_id killer, obj_id target) throws InterruptedException
    {
        int payBracket = getIntObjVar(target, "bountyCheckPayBracket");
        sendSystemMessage(killer, BOUNTY_CHECK_PAYMENT);
        if (payBracket == 0)
        {
            CustomerServiceLog("bounty", "Something is wrong. " + killer + " killed an npc that should have been a Bounty Check target but the NPC ( " + target + " ) did not have the obj var for payout set up correctly");
        }
        if (payBracket == 1)
        {
            money.bankTo(money.ACCT_BOUNTY, killer, 15000);
        }
        if (payBracket == 2)
        {
            money.bankTo(money.ACCT_BOUNTY, killer, 25000);
        }
        return;
    }
    public static boolean initiatePlayerBountyCollection(obj_id player, obj_id target, int amount) throws InterruptedException
    {
        return true;
    }
    public static boolean offerCommandCheckBounty(obj_id player, obj_id target, int amount) throws InterruptedException
    {
        return true;
    }
    public static void awardBounty(obj_id player, String creatureName, int amount) throws InterruptedException
    {
        obj_id storedTarget = utils.getObjIdScriptVar(player, "currentBounty");
        dictionary params = new dictionary();
        params.put("amount", amount);
        params.put("creatureName", getEncodedName(storedTarget));
        money.systemPayout(money.ACCT_BOUNTY_CHECK, player, amount, "handleAwardedBountyCheck", params);
    }
    public static void showSetBountySUI(obj_id player, obj_id killer) throws InterruptedException
    {
        String prompt = "@bounty_hunter:setbounty_prompt1 ";
        prompt += getName(killer) + "? ";
        prompt += "@bounty_hunter:setbounty_prompt2";
        prompt += " " + getTotalMoney(player);
        String title = "@bounty_hunter:setbounty_title";
        int pid = createSUIPage(sui.SUI_INPUTBOX, player, player, "handleSetBounty");
        sui.setAutosaveProperty(pid, false);
        sui.setSizeProperty(pid, 300, 325);
        sui.setLocationProperty(pid, 400, 200);
        setSUIProperty(pid, sui.INPUTBOX_PROMPT, sui.PROP_TEXT, prompt);
        setSUIProperty(pid, sui.INPUTBOX_TITLE, sui.PROP_TEXT, title);
        sui.inputboxButtonSetup(pid, sui.OK_CANCEL);
        sui.inputboxStyleSetup(pid, sui.INPUT_NORMAL);
        setSUIProperty(pid, sui.INPUTBOX_INPUT, "MaxLength", "20");
        setSUIProperty(pid, sui.INPUTBOX_COMBO, "MaxLength", "20");
        subscribeToSUIProperty(pid, sui.INPUTBOX_INPUT, sui.PROP_LOCALTEXT);
        subscribeToSUIProperty(pid, sui.INPUTBOX_COMBO, sui.PROP_SELECTEDTEXT);
        showSUIPage(pid);
        utils.setScriptVar(player, "setbounty.killer", killer);
    }
    public static void endBountySession(obj_id hunter, obj_id target, boolean hunterWon) throws InterruptedException
    {
    }
    public static void winBountyMission(obj_id hunter, obj_id target) throws InterruptedException
    {
        int bountyValue = 0;
        if (hasObjVar(target, "bounty.amount"))
        {
            bountyValue = getIntObjVar(target, "bounty.amount");
        }
        dictionary d = new dictionary();
        d.put("target", target);
        d.put("bounty", bountyValue);
        money.systemPayout(money.ACCT_BOUNTY, hunter, bountyValue, "handleAwardedPlayerBounty", d);
        float factionAdj = getBountyFactionPointAdjustment(hunter, target);
        if (factionAdj != 0.0f)
        {
            factions.addFactionStanding(hunter, factions.getFactionNameByHashCode(pvpGetAlignedFaction(hunter)), factionAdj);
        }
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("bounty_hunter", "bounty_success_hunter"));
        pp = prose.setTT(pp, target);
        pp = prose.setDI(pp, bountyValue);
        sendSystemMessageProse(hunter, pp);
        pp = prose.setStringId(pp, new string_id("bounty_hunter", "bounty_success_target"));
        pp = prose.setTT(pp, hunter);
        sendSystemMessageProse(target, pp);
        obj_id[] hunters = getJediBounties(target);
        if (hunters != null && hunters.length > 0)
        {
            for (int i = 0; i < hunters.length; i++)
            {
                if (hunters[i] != hunter)
                {
                    messageTo(hunters[i], "handleBountyMissionIncomplete", d, 0.0f, true);
                }
            }
        }
        obj_id mission = getBountyMission(hunter);
        if (isIdValid(mission))
        {
            endMission(mission);
        }
        removeObjVar(target, "bounty");
        setJediBountyValue(target, 0);
        removeAllJediBounties(target);
    }
    public static void loseBountyMission(obj_id hunter, obj_id target) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("bounty_hunter", "bounty_failed_hunter"));
        pp = prose.setTT(pp, target);
        sendSystemMessageProse(hunter, pp);
        pp = prose.setStringId(pp, new string_id("bounty_hunter", "bounty_failed_target"));
        pp = prose.setTT(pp, hunter);
        sendSystemMessageProse(target, pp);
        obj_id mission = getBountyMission(hunter);
        if (isIdValid(mission))
        {
            endMission(mission);
        }
        removeJediBounty(target, hunter);
        // remove TEFs that were set when players engaged in battle
        if(isPlayer(hunter) && isPlayer(target)) {
            if (pvpHasPersonalEnemyFlag(target, hunter)) pvpRemovePersonalEnemyFlags(target, hunter);
            if (pvpHasPersonalEnemyFlag(hunter, target)) pvpRemovePersonalEnemyFlags(hunter, target);
        }
        CustomerServiceLog("bounty", "%TU was defeated by %TT and failed to collect the bounty on %PT head", hunter, target);
    }
    public static obj_id getBountyMission(obj_id player) throws InterruptedException
    {
        return getBountyMission(player, obj_id.NULL_ID);
    }
    public static obj_id getBountyMission(obj_id player, obj_id target) throws InterruptedException
    {
        obj_id lastMissionId = null;
        if (isIdValid(player))
        {
            obj_id[] missionList = getMissionObjects(player);
            if (missionList != null)
            {
                for (int i = 0; i < missionList.length; i++)
                {
                    String type = getMissionType(missionList[i]);
                    if (type.equals("bounty"))
                    {
                        if (isIdValid(target))
                        {
                            if (hasObjVar(missionList[i], "objTarget"))
                            {
                                obj_id missionTarget = getObjIdObjVar(missionList[i], "objTarget");
                                if (missionTarget == target)
                                {
                                    return missionList[i];
                                }
                            }
                        }
                        else 
                        {
                            lastMissionId = missionList[i];
                        }
                    }
                }
            }
        }
        return lastMissionId;
    }
    public static boolean hasMaxBountyMissionsOnTarget(obj_id target) throws InterruptedException
    {
        obj_id[] hunters = getJediBounties(target);
        if (hunters == null || hunters.length == 0)
        {
            return false;
        }
        int numHunters = hunters.length;
        String Smax = getConfigSetting("GameServer", "maxJediBounties");
        int maxHunters = 3;
        if (Smax != null && !Smax.equals(""))
        {
            Integer Imax = Integer.getInteger(Smax);
            if (Imax != null)
            {
                maxHunters = Imax.intValue();
            }
        }
        if (numHunters >= maxHunters)
        {
            return true;
        }
        return false;
    }
    public static float getBountyFactionPointAdjustment(obj_id hunter, obj_id target) throws InterruptedException
    {
        float pvpRating = (float)pvp.getCurrentPvPRating(target);
        float points = 0.0f;
        if ((pvpGetAlignedFaction(hunter) != 0) && (pvpGetAlignedFaction(hunter) == pvpGetAlignedFaction(target)))
        {
            points = pvpRating / 4.0f;
            points *= -1.0f;
        }
        else if (factions.pvpAreFactionsOpposed(pvpGetAlignedFaction(hunter), pvpGetAlignedFaction(target)))
        {
            points = pvpRating / 10.0f;
        }
        return points;
    }
    public static void probeDroidTrackTarget(obj_id player, obj_id droid) throws InterruptedException
    {
        int intDroidType = getIntObjVar(droid, "intDroidType");
        obj_id objBountyMission = getBountyMission(player);
        obj_id objMission = getBountyMission(player);
        dictionary dctParams = new dictionary();
        dctParams.put("objPlayer", player);
        if (!hasCommand(player, "droid_track"))
        {
            return;
        }
        int intState = getIntObjVar(objMission, "intState");
        if (intState != 1)
        {
            string_id strSpam = new string_id("mission/mission_generic", "bounty_no_signature");
            sendSystemMessage(player, strSpam);
            return;
        }
        if (utils.hasScriptVar(objMission, "intTracking"))
        {
            string_id strSpam = new string_id("mission/mission_generic", "bounty_already_tracking");
            sendSystemMessage(player, strSpam);
            return;
        }
        if (hasObjVar(objMission, "objTarget"))
        {
            obj_id objTarget = getObjIdObjVar(objMission, "objTarget");
            if (isIdValid(objTarget))
            {
                dictionary dctJediInfo = requestJedi(objTarget);
                if (dctJediInfo == null)
                {
                    string_id strSpam = new string_id("mission/mission_generic", "jedi_not_online");
                    sendSystemMessage(player, strSpam);
                    return;
                }
                else 
                {
                    boolean boolOnline = dctJediInfo.getBoolean("online");
                    if (!boolOnline)
                    {
                        string_id strSpam = new string_id("mission/mission_generic", "jedi_not_online");
                        sendSystemMessage(player, strSpam);
                        return;
                    }
                }
            }
        }
        setObjVar(droid, "objPlayer", player);
        if (intDroidType == DROID_PROBOT)
        {
            bounty_hunter.callProbot(player, droid, objMission);
        }
        if (intDroidType == DROID_SEEKER)
        {
            location locSpawnLocation = getLocation(player);
            if (isValidId(locSpawnLocation.cell))
            {
                string_id strResponse = new string_id("mission/mission_generic", "not_in_house");
                sendSystemMessage(player, strResponse);
                return;
            }
            if ((toLower(locSpawnLocation.area)).startsWith("kashyyyk") || (toLower(locSpawnLocation.area)).startsWith("mustafar"))
            {
                sendSystemMessage(player, new string_id("mission/mission_generic", "no_seek"));
                return;
            }
            utils.setScriptVar(objMission, "intTracking", 1);
            debugServerConsoleMsg(droid, "seeker");
            dctParams.put("intDroidType", intDroidType);
            dctParams.put("objDroid", droid);
            dctParams.put("intTrackType", DROID_TRACK_TARGET);
            setObjVar(objMission, "intDroidType", intDroidType);
            setObjVar(objMission, "objDroid", droid);
            setObjVar(objMission, "intTrackType", DROID_TRACK_TARGET);
            if (!hasObjVar(objMission, "intMissionDynamic"))
            {
                dctParams.put("playerBounty", 1);
            }
            location locHeading = getHeading(player);
            locSpawnLocation.x = locSpawnLocation.x + locHeading.x;
            locSpawnLocation.z = locSpawnLocation.z + locHeading.z;
            obj_id objSeeker = createObject("object/creature/npc/droid/bounty_seeker.iff", locSpawnLocation);
            messageTo(objSeeker, "takeOff", null, 5, true);
            string_id strSpam = new string_id("mission/mission_generic", "seeker_droid_launched");
            sendSystemMessage(player, strSpam);
            messageTo(objMission, "halfwayNotification", dctParams, 40, true);
            messageTo(objMission, "halfwayNotification", dctParams, 60, true);
            messageTo(objMission, "findTarget", dctParams, 20, true);
            int intCount = getCount(droid);
            intCount = intCount - 1;
            if (intCount < 0)
            {
                destroyObject(droid);
            }
            else 
            {
                setCount(droid, intCount);
            }
        }
        return;
    }
    public static boolean callProbot(obj_id player, obj_id droid, obj_id objMission) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(droid) || !exists(droid))
        {
            return false;
        }
        location currentPlayerLocation = getLocation(player);
        if (isValidId(currentPlayerLocation.cell))
        {
            string_id strResponse = new string_id("mission/mission_generic", "not_in_house");
            sendSystemMessage(player, strResponse);
            return false;
        }
        dictionary dctParams = new dictionary();
        dctParams.put("objPlayer", player);
        dctParams.put("intDroidType", DROID_PROBOT);
        dctParams.put("objDroid", droid);
        dctParams.put("intTrackType", DROID_TRACK_TARGET);
        if (!hasObjVar(objMission, "intMissionDynamic"))
        {
            dctParams.put("playerBounty", 1);
        }
        messageTo(droid, "droid_Probot_Start", dctParams, 0, true);
        return true;
    }
}
