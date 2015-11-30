package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;
import script.library.prose;
import script.library.money;
import script.library.badge;

public class patrol_duty extends script.space.quest_logic.patrol
{
    public patrol_duty()
    {
    }
    public static final string_id SID_LAP_REWARD = new string_id("space/quest", "patrol_lap_reward");
    public static final string_id SID_SPECIAL_REWARD = new string_id("space/quest", "destroy_special_reward");
    public static final string_id SID_BADGE_REWARD = new string_id("space/quest", "destroy_badge_reward");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        if ((questName == null) || (questType == null))
        {
            return SCRIPT_CONTINUE;
        }
        String qTable = "datatables/spacequest/" + questType + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, questName);
        if (questInfo == null)
        {
            sendSystemMessageTestingOnly(player, "Debug: Failed to open or find quest '" + questName + "' in " + questType + ".iff");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "reward", questInfo.getInt("reward"));
        setObjVar(self, "rewardVariance", questInfo.getInt("rewardVariance"));
        setObjVar(self, "specialReward", questInfo.getInt("specialReward"));
        setObjVar(self, "specialRewardIncrement", questInfo.getInt("specialRewardIncrement"));
        setObjVar(self, "badge", questInfo.getInt("badge"));
        setObjVar(self, "badgeLaps", questInfo.getInt("badgeLaps"));
        setObjVar(self, "badgeReward", questInfo.getInt("badgeReward"));
        return SCRIPT_CONTINUE;
    }
    public int arrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        String name = params.getString("name");
        obj_id player = params.getObjId("player");
        String[] navPoints = getStringArrayObjVar(self, "navPoints");
        int currentNav = getIntObjVar(self, "currentNavPoint");
        String destNav = navPoints[currentNav];
        java.util.StringTokenizer st = new java.util.StringTokenizer(destNav, ":");
        String scene = st.nextToken();
        destNav = st.nextToken();
        st = new java.util.StringTokenizer(navPoints[0], ":");
        scene = st.nextToken();
        String navzero = st.nextToken();
        if (destNav.equals(name))
        {
            space_quest.showQuestUpdate(self, SID_WAYPOINT_ARRIVED);
            currentNav++;
            setObjVar(self, "currentNavPoint", currentNav);
            if (currentNav == navPoints.length)
            {
                int curLap = getIntObjVar(self, "currentLap");
                setObjVar(self, "currentNavPoint", 0);
                registerPatrolWaypoint(self, player, navzero);
                int currentLap = curLap + 1;
                setObjVar(self, "currentLap", currentLap);
                int reward = getIntObjVar(self, "reward");
                int rewardVariance = getIntObjVar(self, "rewardVariance");
                if (rewardVariance > 0)
                {
                    if (rand() >= 0.5)
                    {
                        reward += rand(1, rewardVariance);
                    }
                    else if (rand() < 0.5)
                    {
                        reward -= rand(1, rewardVariance);
                    }
                }
                money.bankTo(money.ACCT_SPACE_QUEST_REWARD, player, reward);
                space_quest.showQuestUpdate(self, SID_LAP_REWARD, reward);
                int ibadge = getIntObjVar(self, "badge");
                String badgeName = getCollectionSlotName(ibadge);
                int badgeLaps = getIntObjVar(self, "badgeLaps");
                if ((badgeName != null) && (badgeName.length() > 0) && (badgeLaps > 0) && (currentLap == badgeLaps))
                {
                    if (!badge.hasBadge(player, badgeName))
                    {
                        reward = getIntObjVar(self, "badgeReward");
                        money.bankTo(money.ACCT_SPACE_QUEST_REWARD, player, reward);
                        space_quest.showQuestUpdate(self, SID_BADGE_REWARD, reward);
                        badge.grantBadge(player, badgeName);
                        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
                        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
                        string_id badgeEmailFrom = new string_id("spacequest/" + questType + "_" + questName, "badgefrom");
                        string_id badgeEmailSubject = new string_id("spacequest/" + questType + "_" + questName, "badgesubject");
                        string_id badgeEmailBody = new string_id("spacequest/" + questType + "_" + questName, "badgebody");
                        String player_name = getName(player);
                        String body_oob = chatMakePersistentMessageOutOfBandBody(null, badgeEmailBody);
                        chatSendPersistentMessage("@" + badgeEmailFrom.toString(), player_name, "@" + badgeEmailSubject.toString(), null, body_oob);
                    }
                }
                else 
                {
                    int specialRewardIncrement = getIntObjVar(self, "specialRewardIncrement");
                    if (currentLap % specialRewardIncrement == 0)
                    {
                        reward = getIntObjVar(self, "specialReward");
                        money.bankTo(money.ACCT_SPACE_QUEST_REWARD, player, reward);
                        space_quest.showQuestUpdate(self, SID_SPECIAL_REWARD, reward);
                    }
                }
            }
            else 
            {
                st = new java.util.StringTokenizer(navPoints[currentNav], ":");
                scene = st.nextToken();
                destNav = st.nextToken();
                registerPatrolWaypoint(self, player, destNav);
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
}
