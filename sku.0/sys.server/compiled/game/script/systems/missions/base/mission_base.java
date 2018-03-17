package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.force_rank;
import script.library.gcw;
import script.library.group;
import script.library.jedi;
import script.library.missions;
import script.library.money;
import script.library.prose;
import script.library.utils;
import script.library.xp;

public class mission_base extends script.base_script
{
    public mission_base()
    {
    }
    public static final String MISSION_SUCCESS_PERSISTENT_MESSAGE = "success";
    public static final String MISSION_FAILURE_PERSISTENT_MESSAGE = "failure";
    public static final String MISSION_INCOMPLETE_PERSISTENT_MESSAGE = "incomplete";
    public static final String MISSION_TIMED_OUT_PERSISTENT_MESSAGE = "timed_out";
    public static final String GENERIC_MISSION_MESSAGE_STRING_FILE = "mission/mission_generic";
    public static final int MAX_MISSIONS = 2;
    public static final String BOUNTY_MISSION_LISTENERS = "mission.objBountyListeners";
    public static final int BOUNTY_DIFFICULTY_BASIC = 1;
    public static final int BOUNTY_DIFFICULTY_ADVANCED = 2;
    public static final int BOUNTY_DIFFICULTY_EXPERT = 3;
    public static final int BOUNTY_TRACK_SPEED = 120;
    public static final int BOUNTY_FIND_SPEED = 100;
    public static final int DROID_PROBOT = 1;
    public static final int DROID_SEEKER = 2;
    public static final int DROID_TRACK_TARGET = 1;
    public static final int DROID_FIND_TARGET = 2;
    public static final int FACTION_NONE = 0;
    public static final float BOUNTY_REWARD_MODIFIER = 0.75f;
    public static final int MISSION_SPAWN_TRIGGER_RANGE = 256;
    public static final int STATE_BOUNTY_INFORMANT = 0;
    public static final int STATE_BOUNTY_PROBE = 1;
    public static final int INFORMANT_EASY = 1;
    public static final int INFORMANT_MEDIUM = 2;
    public static final int INFORMANT_HARD = 3;
    public static final float SPAWN_OVERLOAD_DIFFICULTY_MODIFIER = .50f;
    public location findRandomLocation(location locCenter, int intVariance) throws InterruptedException
    {
        int intMin;
        int intMax;
        intMin = 0 - intVariance;
        intMax = 0 + intVariance;
        locCenter.x = locCenter.x + rand(intMin, intMax);
        locCenter.z = locCenter.x + rand(intMin, intMax);
        return locCenter;
    }
    public obj_id getMissionData(obj_id objMission) throws InterruptedException
    {
        return objMission;
    }
    public void addListener(obj_id objListener, obj_id objTarget) throws InterruptedException
    {
        return;
    }
    public int getMissionBondAmount(obj_id objMission) throws InterruptedException
    {
        if (hasObjVar(objMission, "intBond"))
        {
            int intBond = getIntObjVar(objMission, "intBond");
            return intBond;
        }
        return 0;
    }
    public void removeListener(obj_id objListener, obj_id objTarget) throws InterruptedException
    {
        return;
    }
    public void messageListeners(obj_id objOwner, String strMessageName, dictionary dctParams) throws InterruptedException
    {
        return;
    }
    public void deliverReward(obj_id objMission) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(objMission);
        obj_id objMissionData = getMissionData(objMission);
        int intPlayerDifficulty = getIntObjVar(objMissionData, "intPlayerDifficulty");
        obj_id objGroup = getGroupObject(objPlayer);
        string_id strMessage = new string_id();
        boolean boolGroup = false;
        if (isIdValid(objGroup))
        {
            boolGroup = true;
            strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "group_success");
        }
        else 
        {
            strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "success");
        }
        dictionary dctParams = new dictionary();
        int intBond = getMissionBondAmount(objMissionData);
        if (intBond > 0)
        {
            transferBankCreditsTo(objMissionData, objPlayer, intBond, "testSuccess", "testFail", dctParams);
        }
        int intReward = getMissionReward(objMissionData);
        int jediBonusReward = 0;
        if (hasObjVar(objMissionData, "jediBonusReward"))
        {
            jediBonusReward = getIntObjVar(objMissionData, "jediBonusReward");
            intReward = intReward + jediBonusReward;
            if (jediBonusReward >= 0)
            {
                String msg = utils.packStringId(new string_id("mission/mission_generic", "bonus_reward"));
                msg += " " + jediBonusReward;
                sendSystemMessage(objPlayer, msg, null);
            }
            else 
            {
                sendSystemMessage(objPlayer, new string_id("mission/mission_generic", "easy_reward"));
            }
        }
        if (hasObjVar(objMissionData, "intIncomplete"))
        {
            intReward = intReward / 2;
        }
        int originalGroupSize = 0;
        if (hasObjVar(objMissionData, "originalGroupSize"))
        {
            originalGroupSize = getIntObjVar(objMissionData, "originalGroupSize");
        }
        String strMissionType = getMissionType(objMissionData);
        if (strMissionType.equals("bounty"))
        {
            missions.increaseBountyJediKillTracking(objPlayer, missions.WINS);
            obj_id objTarget = getObjIdObjVar(objMissionData, "objTarget");
            if (isIdValid(objTarget) && exists(objTarget) && isPlayer(objTarget) && isJedi(objTarget))
            {
                xp.grant(objTarget, xp.JEDI_GENERAL, (intReward * -2));
            }
            if (isPlayer(objTarget) && isJedi(objTarget))
            {
                boolean isForceRanked = force_rank.isForceRanked(objTarget);
                if ((isForceRanked && intReward > jedi.MIN_FR_JEDI_BOUNTY) || (!isForceRanked && intReward > jedi.MIN_NON_FR_JEDI_BOUNTY))
                {
                    messageTo(objTarget, "updateBHKillData", dctParams, 0, true);
                }
            }
            else 
            {
            }
        }
        if (hasObjVar(objMission, "hq"))
        {
            obj_id objHq = getObjIdObjVar(objMission, "hq");
            if (isIdValid(objHq))
            {
                int hqReward = Math.round(intReward / 20f);
                transferBankCreditsFromNamedAccount(money.ACCT_MISSION_DYNAMIC, objHq, hqReward, "noHandler", "noHandler", new dictionary());
            }
        }
        if (!boolGroup)
        {
            intReward = group.getSafeDifference(objPlayer, intReward);
            if (originalGroupSize > 0 && !strMissionType.equals("destroy"))
            {
                intReward = intReward / originalGroupSize;
            }
            float divisor = missions.alterMissionPayoutDivisorDaily(objPlayer);
            intReward = intReward / (int)divisor;
            if (missions.canEarnDailyMissionXp(objPlayer) && missions.isDestroyMission(objMissionData))
            {
                xp.grantMissionXp(objPlayer, intPlayerDifficulty);
            }
            transferBankCreditsFromNamedAccount(money.ACCT_MISSION_DYNAMIC, objPlayer, intReward, "testSuccess", "testFail", dctParams);
            utils.moneyInMetric(objPlayer, money.ACCT_MISSION_DYNAMIC, intReward);
            prose_package successProse = prose.getPackage(new string_id("mission/mission_generic", "success_w_amount"), intReward);
            sendSystemMessageProse(objPlayer, successProse);
            missions.incrementDaily(objPlayer);
        }
        else 
        {
            int currentGroupSize = getPCGroupSize(objGroup);
            if (originalGroupSize < 0)
            {
                originalGroupSize = 0;
            }
            int missionDivisor = originalGroupSize;
            strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "group_success");
            if (!strMissionType.equals("destroy"))
            {
                if (currentGroupSize > originalGroupSize)
                {
                    missionDivisor = currentGroupSize;
                    strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "group_expanded");
                }
            }
            else 
            {
                missionDivisor = 1;
                dctParams.put("intPlayerDifficulty", intPlayerDifficulty);
                group.distributeMissionXpToGroup(objPlayer, group.SPLIT_RANGE, objMissionData);
            }
            group.systemPayoutToGroupInternal(money.ACCT_MISSION_DYNAMIC, objPlayer, intReward, null, strMessage, "test", (float)missionDivisor, dctParams, objMissionData);
        }
        String strTitleString = MISSION_SUCCESS_PERSISTENT_MESSAGE;
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strMessageString = "m" + intStringId + "d";
        if (hasObjVar(objMissionData, "strFaction"))
        {
            String strFaction = getStringObjVar(objMissionData, "strFaction");
            int intFactionReward = getIntObjVar(objMissionData, "intFactionReward");
            int intGCWPoints = getIntObjVar(objMissionData, "intGCWPoints");
            if (hasObjVar(objMission, "hq"))
            {
                intFactionReward = Math.round(intFactionReward * 1.05f);
            }
            if (!boolGroup)
            {
                factions.awardFactionStanding(objPlayer, strFaction, intFactionReward);
                if ((factions.isImperial(objPlayer) || factions.isRebel(objPlayer)) && !factions.isOnLeave(objPlayer) && intGCWPoints > 0)
                {
                    gcw._grantGcwPoints(null, objPlayer, intGCWPoints, false, gcw.GCW_POINT_TYPE_GROUND_PVE, "mission terminal");
                }
            }
            else 
            {
                factions.awardFactionStanding(objPlayer, strFaction, intFactionReward);
                if (!strMissionType.equals("deliver"))
                {
                    obj_id[] objGroupMembers = getGroupMemberIds(objGroup);
                    int intNewReward = 0;
                    if (objGroupMembers.length < 1)
                    {
                        LOG("DESIGNER_FATAL", "Group object " + objGroup + " with player " + objPlayer + " is a zero length group!!!");
                    }
                    else 
                    {
                        intNewReward = intFactionReward / objGroupMembers.length;
                    }
                    if (intNewReward > 0)
                    {
                        location locOwnerLocation = getLocation(objPlayer);
                        if (locOwnerLocation != null)
                        {
                            String strOwnerPlanet = locOwnerLocation.area;
                            float fltDistance = 0;
                            for (int intI = 0; intI < objGroupMembers.length; intI++)
                            {
                                location locMemberLocation = getLocation(objGroupMembers[intI]);
                                if (locMemberLocation == null)
                                {
                                    fltDistance = 100000;
                                }
                                else 
                                {
                                    String strMemberPlanet = locMemberLocation.area;
                                    if (!strMemberPlanet.equals(strOwnerPlanet))
                                    {
                                        fltDistance = 100000;
                                    }
                                    else 
                                    {
                                        fltDistance = getDistance(objPlayer, objGroupMembers[intI]);
                                    }
                                }
                                if (fltDistance < 80 && objGroupMembers[intI] != objPlayer)
                                {
                                    factions.awardFactionStanding(objGroupMembers[intI], strFaction, intNewReward);
                                    if (intGCWPoints > 0)
                                    {
                                        gcw._grantGcwPoints(null, objGroupMembers[intI], intGCWPoints, false, gcw.GCW_POINT_TYPE_GROUND_PVE, "mission terminal");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    public obj_id getValidPayTarget(obj_id player, int money) throws InterruptedException
    {
        Vector targets = group.getPCMembersInRange(player, 200);
        obj_id mostMoney = player;
        if (group.getSafeDifference(player, money) == money)
        {
            return player;
        }
        for (int i = 0; i < targets.size(); i++)
        {
            if (!utils.isFreeTrial(((obj_id)targets.get(i))) && !hasScript(((obj_id)targets.get(i)), "ai.pet"))
            {
                return ((obj_id)targets.get(i));
            }
            if (utils.isFreeTrial(((obj_id)targets.get(i))))
            {
                if (group.getSafeDifference(((obj_id)targets.get(i)), money) == money)
                {
                    return ((obj_id)targets.get(i));
                }
            }
            if (!hasScript(((obj_id)targets.get(i)), "ai.pet"))
            {
                if (group.getSafeDifference(((obj_id)targets.get(i)), money) > group.getSafeDifference(mostMoney, money))
                {
                    mostMoney = ((obj_id)targets.get(i));
                }
            }
        }
        return mostMoney;
    }
    public void setupBountyMissionObject(obj_id objMission) throws InterruptedException
    {
        return;
    }
    public void cleanupBountyMission(obj_id objMission) throws InterruptedException
    {
        return;
    }
    public void returnReward(obj_id objMission) throws InterruptedException
    {
        return;
    }
    public int getBountyDifficulty(obj_id objPlayer) throws InterruptedException
    {
        int intBountyLevel = getSkillStatisticModifier(objPlayer, "bounty_mission_level");
        LOG("missions", "bounty level is " + intBountyLevel);
        if (intBountyLevel < 1)
        {
            LOG("DESIGNER_FATAL", "Bounty hunter id " + objPlayer + " has a bounty difficulty of less than 1");
            return 1;
        }
        return intBountyLevel;
    }
    public obj_id cleanMissionObject(obj_id objMissionObject) throws InterruptedException
    {
        removeAllObjVars(objMissionObject);
        detachAllScripts(objMissionObject);
        attachScript(objMissionObject, "systems.missions.base.mission_object");
        attachScript(objMissionObject, "systems.missions.base.mission_cleanup_tracker");
        return objMissionObject;
    }
}
