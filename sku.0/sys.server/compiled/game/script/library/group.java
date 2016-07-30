package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.planetary_map;
import script.library.missions;

public class group extends script.base_script
{
    public group()
    {
    }
    public static final String SCRIPT_GROUP_OBJECT = "grouping.group_object";
    public static final String SCRIPT_GROUP_MEMBER = "grouping.group_member";
    public static final String GROUP_STF = "group";
    public static final string_id PROSE_NOTIFY_UNKNOWN = new string_id("group", "notify_unknown");
    public static final string_id PROSE_NOTIFY_COIN_LOOT_INT = new string_id("group", "notify_coin_loot_int");
    public static final string_id PROSE_NOTIFY_COIN_LOOT_STRING = new string_id("group", "notify_coin_loot_string");
    public static final string_id PROSE_NOTIFY_PARTIAL_COIN_LOOT = new string_id("group", "notify_partial_coin_loot_int");
    public static final string_id PROSE_NOTIFY_ITEM_LOOT = new string_id("group", "notify_item_loot");
    public static final string_id PROSE_NOTIFY_PARTIAL_LOOT = new string_id("group", "notify_partial_loot");
    public static final string_id PROSE_NOTIFY_NO_LOOT = new string_id("group", "notify_no_loot");
    public static final string_id PROSE_NOTIFY_ERROR_LOOT = new string_id("group", "notify_error_loot");
    public static final string_id PROSE_NOTIFY_SINGLE_LOOT = new string_id("group", "notify_single_loot");
    public static final string_id PROSE_NOTIFY_HARVEST_CORPSE = new string_id("group", "notify_harvest_corpse");
    public static final string_id PROSE_NOTIFY_INCAP = new string_id("group", "notify_incap");
    public static final string_id PROSE_NOTIFY_DEATH = new string_id("group", "notify_death");
    public static final string_id PROSE_NOTIFY_CLONED = new string_id("group", "notify_cloned");
    public static final string_id PROSE_NOTIFY_CLONED_CITY = new string_id("group", "notify_cloned_city");
    public static final string_id PROSE_NOTIFY_OPTION_ON = new string_id("group", "notify_option_on");
    public static final string_id PROSE_NOTIFY_OPTION_OFF = new string_id("group", "notify_option_off");
    public static final string_id PROSE_SPLIT_COINS_SELF = new string_id("group", "prose_split_coins_self");
    public static final string_id PROSE_SPLIT_COINS_MISSION = new string_id("group", "prose_split_coins_mission");
    public static final float SPLIT_RANGE = 200f;
    public static final String HANDLER_SPLIT_SUCCESS = "handleSplitSuccess";
    public static final String HANDLER_SPLIT_FAILURE = "handleSplitFailure";
    public static final int FREE_FOR_ALL = 0;
    public static final int MASTER_LOOTER = 1;
    public static final int LOTTERY = 2;
    public static final int RANDOM = 3;
    public static String getGroupChatRoomName(obj_id gid) throws InterruptedException
    {
        if (!isIdValid(gid))
        {
            return null;
        }
        return (getGameChatCode() + "." + getGalaxyName() + ".group." + gid + ".GroupChat");
    }
    public static void sendGroupChatMessage(obj_id gid, prose_package msg) throws InterruptedException
    {
        String oobPP = packOutOfBandProsePackage(null, msg);
        String gRoom = getGroupChatRoomName(gid);
        chatSendToRoom(gRoom, null, oobPP);
    }
    public static boolean isGroupObject(obj_id gid) throws InterruptedException
    {
        if (isIdValid(gid))
        {
            return (getGameObjectType(gid) == GOT_group);
        }
        return false;
    }
    public static boolean isGrouped(obj_id target) throws InterruptedException
    {
        if (isIdValid(target))
        {
            return isIdValid(getGroupObject(target));
        }
        return false;
    }
    public static boolean inSameGroup(obj_id target1, obj_id target2) throws InterruptedException
    {
        if (!isIdValid(target1) || !isIdValid(target2))
        {
            return false;
        }
        if (target1 == target2)
        {
            return true;
        }
        if (beast_lib.isBeastMaster(target1))
        {
            obj_id beast = beast_lib.getBeastOnPlayer(target1);
            if (beast == target2)
            {
                return true;
            }
        }
        if (beast_lib.isBeastMaster(target2))
        {
            obj_id beast = beast_lib.getBeastOnPlayer(target2);
            if (beast == target1)
            {
                return true;
            }
        }
        obj_id gid1 = getGroupObject(target1);
        obj_id gid2 = getGroupObject(target2);
        if (!isIdValid(gid1))
        {
            if (beast_lib.isBeast(target1))
            {
                target1 = getMaster(target1);
                if (target1 == target2)
                {
                    return true;
                }
                gid1 = getGroupObject(target1);
                if (!isIdValid(gid1))
                {
                    return false;
                }
            }
            else 
            {
                return false;
            }
        }
        if (!isIdValid(gid2))
        {
            if (beast_lib.isBeast(target2))
            {
                target2 = getMaster(target2);
                if (target1 == target2)
                {
                    return true;
                }
                gid2 = getGroupObject(target2);
                if (!isIdValid(gid2))
                {
                    return false;
                }
            }
            else 
            {
                return false;
            }
        }
        return (gid1 == gid2);
    }
    public static obj_id getLeader(obj_id target) throws InterruptedException
    {
        if (isIdValid(target))
        {
            obj_id gid = null;
            if (isGroupObject(target))
            {
                gid = target;
            }
            else 
            {
                gid = getGroupObject(target);
            }
            if (isIdValid(gid))
            {
                return getGroupLeaderId(gid);
            }
        }
        return null;
    }
    public static boolean isLeader(obj_id target) throws InterruptedException
    {
        if (isIdValid(target))
        {
            obj_id gid = getGroupObject(target);
            if (isIdValid(gid))
            {
                return (getGroupLeaderId(gid) == target);
            }
        }
        return false;
    }
    public static void changeLeader(obj_id self) throws InterruptedException
    {
        obj_id groupid = getGroupObject(self);
        if (isIdValid(groupid) && isLeader(self))
        {
            obj_id[] members = getGroupMemberIds(groupid);
            if (members != null && members.length > 0)
            {
                boolean found = false;
                for (int i = 0, j = members.length; i < j && !found; i++)
                {
                    if (!isIdValid(members[i]))
                    {
                        continue;
                    }
                    if (members[i] != self)
                    {
                        found = true;
                        queueCommand(self, (-1818569340), members[i], "", COMMAND_PRIORITY_FRONT);
                    }
                }
            }
        }
    }
    public static void leaveGroup(obj_id player) throws InterruptedException
    {
        changeLeader(player);
        queueCommand(player, (1348589140), player, "", COMMAND_PRIORITY_FRONT);
    }
    public static boolean isMixedFactionGroup(obj_id groupId) throws InterruptedException
    {
        if (!isIdValid(groupId))
        {
            return false;
        }
        obj_id[] members = getGroupMemberIds(groupId);
        if (members != null && members.length > 0)
        {
            int faction = factions.FACTION_FLAG_UNKNOWN;
            for (int i = 0, j = members.length; i < j; i++)
            {
                if (!isIdValid(members[i]) || !exists(members[i]))
                {
                    continue;
                }
                if (faction == factions.FACTION_FLAG_UNKNOWN)
                {
                    faction = factions.getFactionFlag(members[i]);
                }
                if (faction != factions.getFactionFlag(members[i]))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static String getCreatureGroupName(obj_id target) throws InterruptedException
    {
        if (isIdValid(target))
        {
            obj_id gid = getGroupObject(target);
            if (isIdValid(gid))
            {
                return getGroupName(gid);
            }
        }
        return null;
    }
    public static Vector getPCMembersInRange(obj_id actor, float range) throws InterruptedException
    {
        if (!isIdValid(actor) || !exists(actor) || !actor.isLoaded())
        {
            return null;
        }
        obj_id gid = getGroupObject(actor);
        if (!isIdValid(gid))
        {
            return null;
        }
        Vector targets = new Vector();
        targets.setSize(0);
        obj_id[] members = getGroupMemberIds(gid);
        if (members != null && members.length > 0)
        {
            location here = getWorldLocation(actor);
            for (int i = 0; i < members.length; i++)
            {
                obj_id member = members[i];
                if (isIdValid(member) && member.isLoaded() && isPlayer(member))
                {
                    location there = getWorldLocation(member);
                    if (getDistance(here, there) <= range)
                    {
                        targets = utils.addElement(targets, member);
                    }
                }
            }
        }
        return targets;
    }
    public static Vector getPCMembersInRange() throws InterruptedException
    {
        return getPCMembersInRange(getSelf());
    }
    public static Vector getPCMembersInRange(float range) throws InterruptedException
    {
        return getPCMembersInRange(getSelf(), range);
    }
    public static Vector getPCMembersInRange(obj_id actor) throws InterruptedException
    {
        return getPCMembersInRange(actor, 200f);
    }
    public static void notifyCoinLoot(obj_id gid, obj_id actor, obj_id target, int amt) throws InterruptedException
    {
        if (!isIdValid(gid) || !isIdValid(actor) || !isIdValid(target))
        {
            return;
        }
        if (amt < 1)
        {
            return;
        }
        prose_package ppCoinLoot = prose.getPackage(PROSE_NOTIFY_COIN_LOOT_INT, actor, getName(actor), null, target, getAssignedName(target), getNameStringId(target), null, null, null, amt, 0.0f);
        sendGroupChatMessage(gid, ppCoinLoot);
    }
    public static void notifyCoinLootFail(obj_id gid, obj_id actor, obj_id target, int amt) throws InterruptedException
    {
        if (!isIdValid(gid) || !isIdValid(actor) || !isIdValid(target))
        {
            return;
        }
        if (amt < 1)
        {
            return;
        }
        int remainder = getCashBalance(target);
        prose_package ppPartialCoinLoot = prose.getPackage(PROSE_NOTIFY_PARTIAL_COIN_LOOT, actor, getName(actor), null, target, getAssignedName(target), getNameStringId(target), null, null, null, amt, 0.0f);
        sendGroupChatMessage(gid, ppPartialCoinLoot);
        sendSystemMessageProse(actor, ppPartialCoinLoot);
    }
    public static void notifyItemLoot(obj_id gid, obj_id actor, obj_id target, obj_id item) throws InterruptedException
    {
        if (!isIdValid(gid))
        {
            return;
        }
        if (!isIdValid(actor))
        {
            return;
        }
        if (!isIdValid(target))
        {
            return;
        }
        if (!isIdValid(item))
        {
            return;
        }
        prose_package ppSingleLoot = prose.getPackage(PROSE_NOTIFY_SINGLE_LOOT, actor, getName(actor), null, target, getAssignedName(target), getNameStringId(target), item, getAssignedName(item), getNameStringId(item), 0, 0.0f);
        obj_id[] objMembersWhoExist = utils.getLocalGroupMemberIds(gid);
        for (int x = 0; x < objMembersWhoExist.length; x++)
        {
            sendSystemMessageProse(objMembersWhoExist[x], ppSingleLoot);
        }
    }
    public static void notifyItemLoot(obj_id gid, obj_id actor, obj_id target, obj_id[] items, int leftCount) throws InterruptedException
    {
        if (!isIdValid(gid) || !isIdValid(actor) || !isIdValid(target))
        {
            return;
        }
        if (items == null || items.length == 0)
        {
            prose_package ppNoLoot = prose.getPackage(PROSE_NOTIFY_NO_LOOT, actor, getName(actor), null, target, getAssignedName(target), getNameStringId(target), null, null, null, leftCount, 0.0f);
            sendSystemMessageProse(gid, ppNoLoot);
        }
        else 
        {
            int cnt = items.length;
            prose_package ppItemLoot = prose.getPackage(PROSE_NOTIFY_ITEM_LOOT, actor, getName(actor), null, target, getAssignedName(target), getNameStringId(target), null, null, null, cnt, 0.0f);
            sendSystemMessageProse(gid, ppItemLoot);
            for (int i = 0; i < items.length; i++)
            {
                notifyItemLoot(gid, actor, target, items[i]);
            }
        }
        if (leftCount > 0)
        {
            prose_package ppNoLoot = prose.getPackage(PROSE_NOTIFY_PARTIAL_LOOT, actor, getName(actor), null, target, getAssignedName(target), getNameStringId(target), null, null, null, leftCount, 0.0f);
            sendSystemMessageProse(gid, ppNoLoot);
        }
    }
    public static void notifyItemLoot(obj_id gid, obj_id actor, obj_id target, obj_id[] items) throws InterruptedException
    {
        notifyItemLoot(gid, actor, target, items, 0);
    }
    public static void notifyHarvest(obj_id gid, obj_id actor, obj_id target, String rType, int amt) throws InterruptedException
    {
        if (!isIdValid(gid) || !isIdValid(actor) || !isIdValid(target))
        {
            return;
        }
        if (rType == null || rType.equals(""))
        {
            return;
        }
        if (amt < 1)
        {
            return;
        }
        prose_package ppHarvest = prose.getPackage(PROSE_NOTIFY_HARVEST_CORPSE, actor, getName(actor), null, target, getAssignedName(target), getNameStringId(target), null, rType, null, amt, 0.0f);
        sendGroupChatMessage(gid, ppHarvest);
    }
    public static void notifyIncapacitation(obj_id gid, obj_id actor) throws InterruptedException
    {
        if (!isIdValid(gid) || !isIdValid(actor))
        {
            return;
        }
        prose_package ppIncap = prose.getPackage(PROSE_NOTIFY_INCAP, actor, getName(actor), null, null, null, null, null, null, null, 0, 0.0f);
        sendGroupChatMessage(gid, ppIncap);
    }
    public static void notifyDeath(obj_id gid, obj_id actor) throws InterruptedException
    {
        if (!isIdValid(gid) || !isIdValid(actor))
        {
            return;
        }
        prose_package ppDeath = prose.getPackage(PROSE_NOTIFY_DEATH, null, getAssignedName(actor), null, null, null, null, null, null, null, 0, 0.0f);
        sendGroupChatMessage(gid, ppDeath);
    }
    public static void notifyCloned(obj_id gid, obj_id actor) throws InterruptedException
    {
        if (!isIdValid(gid) || !isIdValid(actor))
        {
            return;
        }
        location there = getWorldLocation(actor);
        if (there != null)
        {
            String cityName = planetary_map.getCityRegionName(there);
            if (cityName != null && !cityName.equals(""))
            {
                prose_package ppClonedCity = prose.getPackage(PROSE_NOTIFY_CLONED_CITY, null, getAssignedName(actor), null, null, null, null, null, cityName, null, 0, 0.0f);
                sendGroupChatMessage(gid, ppClonedCity);
                return;
            }
        }
        prose_package ppCloned = prose.getPackage(PROSE_NOTIFY_CLONED, actor, getName(actor), null, null, null, null, null, null, null, 0, 0.0f);
        sendGroupChatMessage(gid, ppCloned);
    }
    public static void splitCoins(int amt, dictionary params) throws InterruptedException
    {
        obj_id actor = getSelf();
        if (!isIdValid(actor) || amt < 1)
        {
            return;
        }
        obj_id gid = getGroupObject(actor);
        if (!isIdValid(gid))
        {
            return;
        }
        if (params == null)
        {
            params = new dictionary();
        }
        int totalDividends = 0;
        Vector targets = getPCMembersInRange(SPLIT_RANGE);
        if (targets != null && targets.size() > 0)
        {
            int dividend = (int)(amt / targets.size());
            params.put("target", actor);
            params.put("amt", dividend);
            if (isDepositSafe(targets, dividend))
            {
                for (int i = 0; i < targets.size(); i++)
                {
                    if (((obj_id)targets.get(i)) != actor)
                    {
                        messageTo(((obj_id)targets.get(i)), "handleRequestSplitShare", params, 0, false);
                        totalDividends += dividend;
                    }
                }
            }
            else 
            {
                int placeHolder = amt;
                int toPay = 0;
                obj_id[] safeMoney = getSafeMoney(targets, dividend);
                obj_id[] lowMoney = getUnsafeMoney(targets, dividend);
                if (lowMoney.length > 0 && lowMoney != null)
                {
                    for (int j = 0; j < lowMoney.length; j++)
                    {
                        toPay = getSafeDifference(lowMoney[j], dividend);
                        if (lowMoney[j] != actor)
                        {
                            params.put("amt", toPay);
                            messageTo(lowMoney[j], "handleRequestSplitShare", params, 0, false);
                            totalDividends += toPay;
                            placeHolder -= toPay;
                        }
                    }
                }
                int remainder = 0;
                if (safeMoney.length > 0)
                {
                    remainder = (int)(placeHolder / safeMoney.length);
                }
                else 
                {
                    remainder = placeHolder;
                }
                params.put("amt", remainder);
                if (safeMoney.length > 0 && safeMoney != null)
                {
                    for (int k = 0; k < safeMoney.length; k++)
                    {
                        if (safeMoney[k] != actor)
                        {
                            messageTo(safeMoney[k], "handleRequestSplitShares", params, 0, false);
                            totalDividends += remainder;
                        }
                    }
                }
            }
        }
        prose_package ppSplit = prose.getPackage(PROSE_SPLIT_COINS_SELF, null, Integer.toString(amt), null, null, Integer.toString(amt - totalDividends), null, null, null, null, 0, 0.0f);
        sendSystemMessageProse(actor, ppSplit);
    }
    public static void splitCoins(int amt) throws InterruptedException
    {
        splitCoins(amt, null);
    }
    public static void splitBank(int amt, dictionary params) throws InterruptedException
    {
        obj_id actor = getSelf();
        if (!isIdValid(actor) || amt < 1)
        {
            return;
        }
        obj_id gid = getGroupObject(actor);
        if (!isIdValid(gid))
        {
            return;
        }
        if (params == null)
        {
            params = new dictionary();
        }
        Vector targets = getPCMembersInRange(SPLIT_RANGE);
        if (targets != null && targets.size() > 0)
        {
            int dividend = (int)(amt / targets.size());
            params.put("target", actor);
            params.put("amt", dividend);
            if (isDepositSafe(targets, dividend))
            {
                if (targets != null && targets.size() > 0)
                {
                    for (int i = 0; i < targets.size(); i++)
                    {
                        if (((obj_id)targets.get(i)) != actor)
                        {
                            messageTo(((obj_id)targets.get(i)), "handleRequestPayoutShare", params, 0, false);
                        }
                        else 
                        {
                            messageTo(actor, HANDLER_SPLIT_SUCCESS, params, 0, false);
                        }
                    }
                }
            }
            else 
            {
                int toPay = 0;
                int placeHolder = amt;
                obj_id[] safeMoney = getSafeMoney(targets, dividend);
                obj_id[] lowMoney = getUnsafeMoney(targets, dividend);
                if (lowMoney != null && lowMoney.length > 0)
                {
                    for (int i = 0; i < lowMoney.length; i++)
                    {
                        toPay = getSafeDifference(((obj_id)targets.get(i)), dividend);
                        if (lowMoney[i] != actor)
                        {
                            params.put("amt", toPay);
                            placeHolder -= toPay;
                            messageTo(lowMoney[i], "handleRequestPayoutShare", params, 0, false);
                        }
                    }
                }
                int remainder = 0;
                if (safeMoney.length > 0)
                {
                    remainder = (int)placeHolder / safeMoney.length;
                }
                else 
                {
                    remainder = (int)placeHolder;
                }
                params.put("amt", remainder);
                messageTo(actor, HANDLER_SPLIT_SUCCESS, params, 0, false);
                if (safeMoney != null && safeMoney.length > 0)
                {
                    for (int k = 0; k < safeMoney.length; k++)
                    {
                        if (safeMoney[k] != actor)
                        {
                            messageTo(safeMoney[k], "handleRequestPayoutShare", params, 0, false);
                        }
                    }
                }
            }
        }
    }
    public static void splitBank(int amt) throws InterruptedException
    {
        splitBank(amt, null);
    }
    public static boolean isDepositSafe(Vector members, int money) throws InterruptedException
    {
        for (int i = 0; i < members.size(); i++)
        {
            if (utils.isFreeTrial(((obj_id)members.get(i))))
            {
                int math = getTotalMoney(((obj_id)members.get(i)));
                int quickCheck = math + money;
                if (quickCheck > 50000)
                {
                    return false;
                }
            }
        }
        return true;
    }
    public static obj_id[] getSafeMoney(Vector members, int money) throws InterruptedException
    {
        Vector returnArray = new Vector();
        returnArray.setSize(0);
        for (int i = 0; i < members.size(); i++)
        {
            if (!utils.isFreeTrial(((obj_id)members.get(i))))
            {
                utils.addElement(returnArray, ((obj_id)members.get(i)));
            }
        }
        obj_id[] _returnArray = new obj_id[0];
        if (returnArray != null)
        {
            _returnArray = new obj_id[returnArray.size()];
            returnArray.toArray(_returnArray);
        }
        return _returnArray;
    }
    public static obj_id[] getUnsafeMoney(Vector members, int money) throws InterruptedException
    {
        Vector returnArray = new Vector();
        returnArray.setSize(0);
        for (int i = 0; i < members.size(); i++)
        {
            if (utils.isFreeTrial(((obj_id)members.get(i))))
            {
                utils.addElement(returnArray, ((obj_id)members.get(i)));
            }
        }
        obj_id[] _returnArray = new obj_id[0];
        if (returnArray != null)
        {
            _returnArray = new obj_id[returnArray.size()];
            returnArray.toArray(_returnArray);
        }
        return _returnArray;
    }
    public static int getSafeDifference(obj_id members, int money) throws InterruptedException
    {
        if (utils.isFreeTrial(members))
        {
            int math = getTotalMoney(members);
            int quickCheck = math + money;
            int safe = 50000 - math;
            if (quickCheck > 50000)
            {
                return safe;
            }
            else 
            {
                return money;
            }
        }
        else 
        {
            return money;
        }
    }
    public static boolean systemPayoutToGroup(String acct, obj_id player, int amt, String reason, String returnHandler, dictionary params) throws InterruptedException
    {
        return systemPayoutToGroupInternal(acct, player, amt, reason, null, returnHandler, 0.0f, params);
    }
    public static boolean systemPayoutToGroup(String acct, obj_id player, int amt, string_id reasonId, String returnHandler, dictionary params) throws InterruptedException
    {
        return systemPayoutToGroupInternal(acct, player, amt, null, reasonId, returnHandler, 0.0f, params);
    }
    public static boolean systemPayoutToGroup(String acct, obj_id player, int amt, string_id reasonId, String returnHandler, float divisor, dictionary params) throws InterruptedException
    {
        return systemPayoutToGroupInternal(acct, player, amt, null, reasonId, returnHandler, divisor, params);
    }
    public static boolean systemPayoutToGroupInternal(String acct, obj_id player, int amt, String reason, string_id reasonId, String returnHandler, float divisor, dictionary params) throws InterruptedException
    {
        return systemPayoutToGroupInternal(acct, player, amt, null, reasonId, returnHandler, divisor, params, obj_id.NULL_ID);
    }
    public static boolean systemPayoutToGroupInternal(String acct, obj_id player, int amt, String reason, string_id reasonId, String returnHandler, float divisor, dictionary params, obj_id objMissionData) throws InterruptedException
    {
        if (acct == null || acct.equals(""))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        if (amt < 1)
        {
            return false;
        }
        if (returnHandler == null || returnHandler.equals(""))
        {
            return false;
        }
        if (params == null)
        {
            params = new dictionary();
        }
        obj_id gid = getGroupObject(player);
        if (!isIdValid(gid))
        {
            return false;
        }
        obj_id self = getSelf();
        params.put("msg.id", self);
        params.put("msg.handler", returnHandler);
        if (reason != null && !reason.equals(""))
        {
            params.put("reason", reason);
        }
        if (reasonId != null)
        {
            params.put("reasonId", reasonId);
        }
        params.put(money.DICT_ACCT_NAME, acct);
        params.put(money.DICT_AMOUNT, amt);
        LOG("NewMission", "Payout of " + amt);
        if (acct != null && !acct.equals("") && amt > 0)
        {
            LOG("NewMission", "Distribute " + amt);
            boolean allReceived = distributeMoneyToGroup(player, amt, SPLIT_RANGE, acct, divisor, params, objMissionData);
            string_id partialPayment = new string_id("mission/mission_generic", "group_too_far");
            if (reasonId == null)
            {
                CustomerServiceLog("Mission", "We transferred " + amt + " credits to a group, but there was no message to tell them that", player);
            }
            else 
            {
                obj_id[] gmembers = group.getGroupMemberIds(gid);
                if (gmembers != null && gmembers.length > 0)
                {
                    for (int i = 0; i < gmembers.length; ++i)
                    {
                        sendSystemMessage(gmembers[i], reasonId);
                        if (!allReceived)
                        {
                            sendSystemMessage(gmembers[i], partialPayment);
                        }
                    }
                }
            }
        }
        return true;
    }
    public static boolean distributeMoneyToGroup(obj_id player, int amt, float range, String acct, float divisorInput, dictionary params) throws InterruptedException
    {
        return distributeMoneyToGroup(player, amt, range, acct, divisorInput, params, obj_id.NULL_ID);
    }
    public static boolean distributeMoneyToGroup(obj_id player, int amt, float range, String acct, float divisorInput, dictionary params, obj_id objMissionData) throws InterruptedException
    {
        if (amt < 1)
        {
            return false;
        }
        obj_id groupObject = getGroupObject(player);
        if (!isIdValid(groupObject))
        {
            return false;
        }
        if (params == null)
        {
            params = new dictionary();
        }
        int missionLevel = params.getInt("intPlayerDifficulty");
        Vector targets = getPCMembersInRange(player, range);
        if (targets != null && targets.size() > 0)
        {
            float divisor = divisorInput;
            if (divisor < 1.0f)
            {
                divisor = targets.size();
            }
            string_id ppString = PROSE_SPLIT_COINS_SELF;
            for (int i = 0; i < targets.size(); i++)
            {
                if (missions.isDestroyMission(objMissionData))
                {
                    if (targets.size() > 1)
                    {
                        divisor = missions.alterMissionPayoutDivisor(((obj_id)targets.get(i)), divisor, missionLevel);
                    }
                    missions.incrementDaily(((obj_id)targets.get(i)));
                    ppString = PROSE_SPLIT_COINS_MISSION;
                }
                int payout = amt / ((int)divisor);
                transferBankCreditsFromNamedAccount(acct, ((obj_id)targets.get(i)), payout, "succ", "noHandler", params);
                prose_package ppSplit = prose.getPackage(ppString, null, Integer.toString(amt), null, null, Integer.toString(payout), null, null, null, null, 0, 0.0f);
                sendSystemMessageProse(((obj_id)targets.get(i)), ppSplit);
            }
        }
        return (targets.size() >= getGroupSize(groupObject));
    }
    public static void destroyGroupWaypoint(obj_id player) throws InterruptedException
    {
        obj_id groupWaypoint = getObjIdObjVar(player, "groupWaypoint");
        if (isIdValid(groupWaypoint))
        {
            destroyWaypointInDatapad(groupWaypoint, player);
            removeObjVar(player, "groupWaypoint");
        }
    }
    public static boolean distributeMissionXpToGroup(obj_id player, float range, obj_id objMissionData) throws InterruptedException
    {
        obj_id groupObject = getGroupObject(player);
        if (!isIdValid(groupObject))
        {
            return false;
        }
        if (!isIdValid(objMissionData))
        {
            return false;
        }
        int missionLevel = getIntObjVar(objMissionData, "intPlayerDifficulty");
        Vector targets = getPCMembersInRange(player, range);
        if (targets != null && targets.size() > 0)
        {
            for (int i = 0; i < targets.size(); i++)
            {
                if (missions.isDestroyMission(objMissionData) && missions.canEarnDailyMissionXp(((obj_id)targets.get(i))))
                {
                    xp.grantMissionXp(((obj_id)targets.get(i)), missionLevel);
                }
            }
        }
        return (targets.size() >= getGroupSize(groupObject));
    }
}
