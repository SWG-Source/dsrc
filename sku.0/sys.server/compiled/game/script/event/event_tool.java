package script.event;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

import java.util.StringTokenizer;

public class event_tool extends script.base_script
{
    public event_tool()
    {
    }
    private static final String[] BADBUFFS =
    {
        "movement",
        "delay_attack",
        "private_defense_penalty",
        "private_armor_break",
        "private_posture_override",
        "private_state_resist_debuff",
        "suppression",
        "combat_slow"
    };
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        StringTokenizer st = new java.util.StringTokenizer(text);
        int tokens = st.countTokens();
        String command = null;
        if (st.hasMoreTokens()) {
            command = st.nextToken();
            if (command.equals("grantCommunityBadge")) {
                dictionary dict = new dictionary();
                dict.put("communityRep", self);
                String[] allCommunityBadges = getAllCollectionSlotsInCollection("col_badge_accolades");
                if (st.hasMoreTokens()) {
                    String playerName = "";
                    String badgeName = "";
                    if (st.hasMoreTokens()) {
                        playerName = st.nextToken();
                    }
                    if (st.hasMoreTokens()) {
                        badgeName = st.nextToken();
                    }
                    if (playerName == null || playerName.length() <= 0) {
                        sendSystemMessage(self, "You must supply a player name.", "");
                        return SCRIPT_CONTINUE;
                    }
                    if (badgeName == null || badgeName.length() <= 0) {
                        sendSystemMessage(self, "You must supply a badge name.", "");
                        return SCRIPT_CONTINUE;
                    }
                    String[] info = getCollectionSlotInfo(badgeName);
                    if ((info == null) || (info.length != COLLECTION_INFO_ARRAY_SIZE) || (info[COLLECTION_INFO_INDEX_BOOK] == null) || (!info[COLLECTION_INFO_INDEX_BOOK].equals(badge.BADGE_BOOK))) {
                        sendSystemMessage(self, "This is an invalid badge name.", "");
                        return SCRIPT_CONTINUE;
                    }
                    String collectionName = info[COLLECTION_INFO_INDEX_COLLECTION];
                    if (collectionName == null || collectionName.length() <= 0) {
                        sendSystemMessage(self, "Somehow this badge is missing a collection. Contact Development", "");
                        return SCRIPT_CONTINUE;
                    }
                    if (!collectionName.equals("col_badge_accolades")) {
                        sendSystemMessage(self, "This badge is not a community badge.", "");
                        sendSystemMessage(self, "Valid Community Badges are:", "");
                        for (String allCommunityBadge : allCommunityBadges) {
                            sendSystemMessage(self, allCommunityBadge, "");
                        }
                        return SCRIPT_CONTINUE;
                    }
                    obj_id player = getPlayerIdFromFirstName(playerName);
                    if (!isIdValid(player)) {
                        sendSystemMessage(self, "The name you supplied could not be found. Please check spelling.", "");
                        return SCRIPT_CONTINUE;
                    }
                    dict.put("badgeName", badgeName);
                    messageTo(player, "handlerCommunityBadgeGrant", dict, 0, true);
                    CustomerServiceLog("EventPerk", "[EventTool] 'grantCommunityBadge' used by " + getPlayerName(self) + ":" + self + ". Awarding badge " + badgeName + " to " + getPlayerName(player) + ":" + player + " .", null);
                    sendSystemMessage(self, "Message sent to grant badge. Please wait for response. If you dont get one, the player is not online.", "");
                    return SCRIPT_CONTINUE;
                } else {
                    sendSystemMessage(self, "You need to specify a player name and what badge you want to grant.", "");
                    sendSystemMessage(self, "Correct syntax is grantCommunityBadge <playerName> <badgeName>.", "");
                    sendSystemMessage(self, "Valid Community Badges are:", "");
                    for (String allCommunityBadge : allCommunityBadges) {
                        sendSystemMessage(self, allCommunityBadge, "");
                    }
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("grantAllCommunityBadges")) {
                dictionary dict = new dictionary();
                dict.put("communityRep", self);
                String playerName = "";
                if (st.hasMoreTokens()) {
                    playerName = st.nextToken();
                } else {
                    sendSystemMessage(self, "You need to specify a player name.", "");
                    sendSystemMessage(self, "Correct syntax is grantAllCommunityBadges <playerName>.", "");
                    return SCRIPT_CONTINUE;
                }
                if (playerName == null || playerName.length() <= 0) {
                    sendSystemMessage(self, "You must supply a player name.", "");
                    return SCRIPT_CONTINUE;
                }
                obj_id player = getPlayerIdFromFirstName(playerName);
                if (!isIdValid(player)) {
                    sendSystemMessage(self, "The name you supplied could not be found. Please check spelling.", "");
                    return SCRIPT_CONTINUE;
                }
                String[] allCommunityBadges = getAllCollectionSlotsInCollection("col_badge_accolades");
                for (String allCommunityBadge : allCommunityBadges) {
                    dict.put("badgeName", allCommunityBadge);
                    messageTo(player, "handlerCommunityBadgeGrant", dict, 0, true);
                }
                CustomerServiceLog("EventPerk", "[EventTool] 'grantAllCommunityBadges' used by " + getPlayerName(self) + ":" + self + ". Awarding all community badges to " + getPlayerName(player) + ":" + player + " .", null);
                sendSystemMessage(self, "Messages sent to grant badges. Please wait for responses. If you dont get any, the player is not online.", "");
                return SCRIPT_CONTINUE;
            }
            if (command.equals("grantCommunityBadgeArea")) {
                dictionary dict = new dictionary();
                dict.put("communityRep", self);
                if (st.hasMoreTokens()) {
                    String badgeName = "";
                    int range = 0;
                    if (st.hasMoreTokens()) {
                        badgeName = st.nextToken();
                    }
                    if (st.hasMoreTokens()) {
                        range = utils.stringToInt(st.nextToken());
                    }
                    if (badgeName == null || badgeName.length() <= 0) {
                        sendSystemMessage(self, "You must supply a badge name.", "");
                        return SCRIPT_CONTINUE;
                    }
                    if (range <= 0 || range > 156) {
                        sendSystemMessage(self, "You supplied an invalid range.", "");
                        sendSystemMessage(self, "Valid range is 1 to 156.", "");
                        return SCRIPT_CONTINUE;
                    }
                    String[] info = getCollectionSlotInfo(badgeName);
                    if ((info == null) || (info.length != COLLECTION_INFO_ARRAY_SIZE) || (info[COLLECTION_INFO_INDEX_BOOK] == null) || (!info[COLLECTION_INFO_INDEX_BOOK].equals(badge.BADGE_BOOK))) {
                        sendSystemMessage(self, "This is an invalid badge name.", "");
                        return SCRIPT_CONTINUE;
                    }
                    String collectionName = info[COLLECTION_INFO_INDEX_COLLECTION];
                    if (collectionName == null || collectionName.length() <= 0) {
                        sendSystemMessage(self, "Somehow this badge is missing a collection. Contact Development", "");
                        return SCRIPT_CONTINUE;
                    }
                    if (!collectionName.equals("col_badge_accolades")) {
                        sendSystemMessage(self, "This badge is not a community badge.", "");
                        sendSystemMessage(self, "Valid Community Badges are:", "");
                        String[] allCommunityBadges = getAllCollectionSlotsInCollection("col_badge_accolades");
                        for (String allCommunityBadge : allCommunityBadges) {
                            sendSystemMessage(self, allCommunityBadge, "");
                        }
                        return SCRIPT_CONTINUE;
                    }
                    int badgeCount = 0;
                    obj_id[] objPlayers = getPlayerCreaturesInRange(self, range);
                    if (objPlayers != null && objPlayers.length > 0) {
                        for (obj_id objPlayer : objPlayers) {
                            if (objPlayer != self) {
                                dict.put("badgeName", badgeName);
                                messageTo(objPlayer, "handlerCommunityBadgeGrant", dict, 0, true);
                                sendSystemMessage(self, "Message sent to grant badge. Please wait for response. If you dont get one, the player is not online.", "");
                                badgeCount++;
                            }
                        }
                    }
                    sendSystemMessage(self, "A total of " + badgeCount + " badges were awarded.", null);
                    CustomerServiceLog("EventPerk", "[EventTool] 'grantCommunityBadgeArea' used by " + getPlayerName(self) + ":" + self + ". Awarding badge " + badgeName + " to " + badgeCount + " people.", null);
                } else {
                    sendSystemMessage(self, "You need to specify a badge name and range of area you want to give the badge.", "");
                    sendSystemMessage(self, "Correct syntax is grantCommunityBadge <badgeName> <range>.", "");
                    sendSystemMessage(self, "Valid Range is 1 to 156.", "");
                    sendSystemMessage(self, "Valid Community Badges are:", "");
                    String[] allCommunityBadges = getAllCollectionSlotsInCollection("col_badge_accolades");
                    for (String allCommunityBadge : allCommunityBadges) {
                        sendSystemMessage(self, allCommunityBadge, "");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerCommunityBadgeGrantedToPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        String badgeName = params.getString("badgeName");
        sendSystemMessage(self, getPlayerName(params.getObjId("player")) + " has received the badge " + badgeName + " that you sent them.", "");
        return SCRIPT_CONTINUE;
    }
    public int eventVaderChoke(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id myTarget = getLookAtTarget(self);
        if (!isIdValid(myTarget) || myTarget == self || !isPlayer(myTarget) || myTarget == null)
        {
            sendSystemMessage(self, "Your target for vaderChoke is invalid.", null);
            return SCRIPT_CONTINUE;
        }
        doAnimationAction(self, "force_choke");
        doAnimationAction(myTarget, "heavy_cough_vomit");
        dictionary eDict = new dictionary();
        eDict.put("myTarget", myTarget);
        messageTo(self, "killTargetPlayer", eDict, 6, false);
        CustomerServiceLog("EventPerk", "[EventTool] /eventVaderChoke used by [" + getName(self) + ":" + self + "] on [" + getName(myTarget) + ":" + myTarget, null);
        return SCRIPT_CONTINUE;
    }
    public int eventClearStates(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        dot.removeAllDots(self);
        if (params.equals("all"))
        {
            buff.removeAllBuffs(self);
            return SCRIPT_CONTINUE;
        }
        int[] allBuffs = buff.getAllBuffs(self);
        String buffParam;
        for (int thisBuff : allBuffs) {
            buffParam = buff.getEffectParam(thisBuff, 1);
            for (String badBuff : BADBUFFS) {
                if (buffParam.equals(badBuff)) {
                    buff.removeBuff(self, thisBuff);
                    sendSystemMessage(self, thisBuff + " is EEeevil so it was removed.", null);
                }
            }
        }
        String selfName = getName(self);
        CustomerServiceLog("EventPerk", "[EventTool] /eventClearStates used by " + selfName + ":" + self, null);
        return SCRIPT_CONTINUE;
    }
    public int eventBuff(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null || params.equals(""))
        {
            sendSystemMessage(self, "[Syntax] /eventBuff <buff name> <duration> <value>. Refer /sys.shared/compiled/game/datatables/buff/buff.tab for proper buff names.", null);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        int numArgs = st.countTokens();
        if (numArgs > 3)
        {
            sendSystemMessage(self, "Incorrect number of arguments. [Syntax] /eventBuff <buff name> <optional duration> <optional value>. Refer /sys.shared/compiled/game/datatables/buff/buff.tab for proper buff names.", null);
            return SCRIPT_CONTINUE;
        }
        String buffName = st.nextToken();
        float duration = 0.0f;
        float value = 0.0f;
        if (numArgs > 1)
        {
            String durationStr = st.nextToken();
            duration = utils.stringToInt(durationStr);
        }
        if (numArgs > 2)
        {
            String valueStr = st.nextToken();
            value = utils.stringToInt(valueStr);
        }
        if (numArgs == 1)
        {
            buff.applyBuff(self, buffName);
        }
        if (numArgs == 2)
        {
            buff.applyBuff(self, buffName, duration);
        }
        if (numArgs == 3)
        {
            buff.applyBuff(self, buffName, duration, value);
        }
        String selfName = getName(self);
        CustomerServiceLog("EventPerk", "[EventTool] /eventBuff used by " + selfName + ":" + self + ". Params: " + params, null);
        return SCRIPT_CONTINUE;
    }
    public int eventBuffTarget(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id myTarget = getLookAtTarget(self);
        if (!isIdValid(myTarget) || myTarget == null)
        {
            sendSystemMessage(self, "Invalid target for /eventBuffTarget. Your target must be a player.", null);
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equals(""))
        {
            sendSystemMessage(self, "[Syntax] /eventBuffTarget <buff name> <optional duration> <optional value>. Refer to /sys.shared/compiled/game/datatables/buff/buff.tab for proper buff names.", null);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        int numArgs = st.countTokens();
        if (numArgs > 3)
        {
            sendSystemMessage(self, "Incorrect number of arguments. [Syntax] /eventBuffTarget <buff name> <optional duration> <optional value>. Refer /sys.shared/compiled/game/datatables/buff/buff.tab for proper buff names.", null);
            return SCRIPT_CONTINUE;
        }
        String buffName = st.nextToken();
        float duration = 0.0f;
        float value = 0.0f;
        if (numArgs > 1)
        {
            String durationStr = st.nextToken();
            duration = utils.stringToInt(durationStr);
        }
        if (numArgs > 2)
        {
            String valueStr = st.nextToken();
            value = utils.stringToInt(valueStr);
        }
        if (numArgs == 1)
        {
            buff.applyBuff(myTarget, buffName);
        }
        if (numArgs == 2)
        {
            buff.applyBuff(myTarget, buffName, duration);
        }
        if (numArgs == 3)
        {
            buff.applyBuff(myTarget, buffName, duration, value);
        }
        String selfName = getName(self);
        String targetName = getName(myTarget);
        CustomerServiceLog("EventPerk", "[EventTool] /eventBuffTarget used by " + selfName + ":" + self + " on target " + targetName + ":" + myTarget + ". Params: " + params, null);
        return SCRIPT_CONTINUE;
    }
    public int eventBuffArea(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null || params.equals(""))
        {
            sendSystemMessage(self, "[Syntax] /eventBuffArea <buff name> <player/npc/all> <range> <optional duration> <optioanal value>. Refer to /sys.shared/compiled/game/datatables/buff/buff.tab for proper buff names.", null);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        int numArgs = st.countTokens();
        if (numArgs > 5 || numArgs < 3)
        {
            sendSystemMessage(self, "Incorrect number of arguments. [Syntax] /eventBuffArea <buff name> <player/npc/all> <range> <optional duration> <optional value>. Refer /sys.shared/compiled/game/datatables/buff/buff.tab for proper buff names.", null);
            return SCRIPT_CONTINUE;
        }
        String buffName = st.nextToken();
        String commandType = st.nextToken();
        String rangeStr = st.nextToken();
        float range = utils.stringToInt(rangeStr);
        if (!commandType.equals("player") && !commandType.equals("npc") && !commandType.equals("all"))
        {
            sendSystemMessage(self, "Target for eventBuffArea must be player, npc or all.[Syntax] /eventBuffArea <buff name> <player/npc/all> <range> <optional duration> <optional value>.", null);
            return SCRIPT_CONTINUE;
        }
        if (range < 1 || range > 256)
        {
            sendSystemMessage(self, "Invalid range for eventBuffArea. Range must be between 1 and 256.", null);
            return SCRIPT_CONTINUE;
        }
        float duration = 0.0f;
        float value = 0.0f;
        if (numArgs > 3)
        {
            String durationStr = st.nextToken();
            duration = utils.stringToInt(durationStr);
        }
        if (numArgs > 4)
        {
            String valueStr = st.nextToken();
            value = utils.stringToInt(valueStr);
        }
        if (commandType.equals("player"))
        {
            obj_id[] thingsInRange = getPlayerCreaturesInRange(self, range);
            if (thingsInRange != null && thingsInRange.length > 0)
            {
                for (obj_id aThingsInRange : thingsInRange) {
                    if (aThingsInRange != self) {
                        if (numArgs == 2) {
                            buff.applyBuff(aThingsInRange, buffName);
                        }
                        else if (numArgs == 3) {
                            buff.applyBuff(aThingsInRange, buffName, duration);
                        }
                        else if (numArgs == 4) {
                            buff.applyBuff(aThingsInRange, buffName, duration, value);
                        }
                    }
                }
            }
        }
        if (commandType.equals("npc"))
        {
            obj_id[] thingsInRange = getNPCsInRange(self, range);
            if (thingsInRange != null && thingsInRange.length > 0)
            {
                for (obj_id aThingsInRange : thingsInRange) {
                    if (aThingsInRange != self) {
                        if (numArgs == 2) {
                            buff.applyBuff(aThingsInRange, buffName);
                        }
                        if (numArgs == 3) {
                            buff.applyBuff(aThingsInRange, buffName, duration);
                        }
                        if (numArgs == 4) {
                            buff.applyBuff(aThingsInRange, buffName, duration, value);
                        }
                    }
                }
            }
        }
        if (commandType.equals("all"))
        {
            obj_id[] thingsInRange = getCreaturesInRange(self, range);
            if (thingsInRange != null && thingsInRange.length > 0)
            {
                for (obj_id aThingsInRange : thingsInRange) {
                    if (aThingsInRange != self) {
                        if (numArgs == 2) {
                            buff.applyBuff(aThingsInRange, buffName);
                        }
                        if (numArgs == 3) {
                            buff.applyBuff(aThingsInRange, buffName, duration);
                        }
                        if (numArgs == 4) {
                            buff.applyBuff(aThingsInRange, buffName, duration, value);
                        }
                    }
                }
            }
        }
        String selfName = getName(self);
        location here = getLocation(self);
        CustomerServiceLog("EventPerk", "[EventTool] /eventBuffArea used by " + selfName + ":" + self + " at location " + here + ". Params: " + params, null);
        return SCRIPT_CONTINUE;
    }
    public int eventTefArea(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null || params.equals(""))
        {
            sendSystemMessage(self, "[Syntax] /eventTefArea <range>. Valid range is 1 to 256.", null);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        int numArgs = st.countTokens();
        if (numArgs > 1)
        {
            sendSystemMessage(self, "[Syntax] /eventTefArea <range>. Valid range is 1 to 256.", null);
            return SCRIPT_CONTINUE;
        }
        String rangeStr = st.nextToken();
        float range = utils.stringToInt(rangeStr);
        if (range < 1 || range > 256)
        {
            sendSystemMessage(self, "Invalid range. Range must be between 1 and 256.", null);
            return SCRIPT_CONTINUE;
        }
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, range);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                if (objPlayer != self) {
                    pvpSetPersonalEnemyFlag(self, objPlayer);
                    sendSystemMessage(self, getName(objPlayer) + " can now attack you!", null);
                }
            }
        }
        String selfName = getName(self);
        location here = getLocation(self);
        CustomerServiceLog("EventPerk", "[EventTool] /eventTefArea used by " + selfName + ":" + self + " at location " + here, null);
        return SCRIPT_CONTINUE;
    }
    public int eventTefTarget(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id myTarget = getLookAtTarget(self);
        if (!isIdValid(myTarget) || myTarget == self || !isPlayer(myTarget) || myTarget == null)
        {
            sendSystemMessage(self, "Your target for eventTefTarget is invalid.", null);
            return SCRIPT_CONTINUE;
        }
        pvpSetPersonalEnemyFlag(self, myTarget);
        String playerName = getName(myTarget);
        sendSystemMessage(self, playerName + " can now attack you!", null);
        String selfName = getName(self);
        String targetName = getName(myTarget);
        CustomerServiceLog("EventPerk", "[EventTool] /eventTefTarget used by " + selfName + ":" + self + " on target " + targetName + ":" + myTarget, null);
        return SCRIPT_CONTINUE;
    }
    public int eventTefClear(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        pvpRemoveAllTempEnemyFlags(self);
        String selfName = getName(self);
        CustomerServiceLog("EventPerk", "[EventTool] /eventTefClear used by " + selfName + ":" + self, null);
        return SCRIPT_CONTINUE;
    }
    public int grantBadgeArea(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null || params.equals(""))
        {
            sendSystemMessage(self, "[Syntax] /grantBadgeArea <range> <badge number>. Valid range is 1 to 256.", null);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        int numArgs = st.countTokens();
        if (numArgs != 2)
        {
            sendSystemMessage(self, "[Syntax] /grantBadgeArea <range> <badge number>. Valid range is 1 to 256.", null);
            return SCRIPT_CONTINUE;
        }
        String rangeStr = st.nextToken();
        String badgeNumStr = st.nextToken();
        float range = utils.stringToInt(rangeStr);
        int badgeNumber = utils.stringToInt(badgeNumStr);
        if (range < 1 || range > 256)
        {
            sendSystemMessage(self, "Invalid range. Range must be between 1 and 256.", null);
            return SCRIPT_CONTINUE;
        }
        if (badgeNumber < 0)
        {
            sendSystemMessage(self, "Badge number was invalid and what not.", null);
            return SCRIPT_CONTINUE;
        }
        String badgeName = getCollectionSlotName(badgeNumber);
        if ((badgeName == null) || (badgeName.length() == 0))
        {
            sendSystemMessage(self, "Badge number " + badgeNumber + " is not a valid badge.", null);
            return SCRIPT_CONTINUE;
        }
        int badgeCount = 0;
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, range);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                if (objPlayer != self) {
                    badge.grantBadge(objPlayer, badgeName);
                    sendSystemMessage(self, "Granting badge " + badgeNumber + " to player " + getName(objPlayer), null);
                    badgeCount++;
                }
            }
        }
        sendSystemMessage(self, "A total of " + badgeCount + " badges were awarded.", null);
        CustomerServiceLog("EventPerk", "[EventTool] /grantBadgeArea used by " + getName(self) + ":" + self + ". Awarding badge #" + badgeNumber + " to " + badgeCount + " people.", null);
        return SCRIPT_CONTINUE;
    }
    public int maxForcePower(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        int maxForce = getMaxForcePower(self);
        if (maxForce < 1)
        {
            sendSystemMessage(self, "MaxForcePower failed because you ain't no jedi son!", null);
            return SCRIPT_CONTINUE;
        }
        setForcePower(self, maxForce);
        CustomerServiceLog("EventPerk", "[EventTool] /maxForcePower used by " + getName(self) + ":" + self, null);
        return SCRIPT_CONTINUE;
    }
    public int eventDamage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id myTarget = getLookAtTarget(self);
        if (!isIdValid(myTarget) || (!isPlayer(myTarget) && !isMob(myTarget) && !isNpcCreature(myTarget)))
        {
            sendSystemMessage(self, "Your target for /eventDamage is invalid ya nut!", null);
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equals(""))
        {
            sendSystemMessage(self, "[Syntax] /eventDamageTarget <amount>.", null);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        int numArgs = st.countTokens();
        if (numArgs != 1)
        {
            sendSystemMessage(self, "[Syntax] /eventDamageTarget <amount>.", null);
            return SCRIPT_CONTINUE;
        }
        String damageStr = st.nextToken();
        int damage = utils.stringToInt(damageStr) * -1;
        healing.healDamage(self, myTarget, HEALTH, damage);
        CustomerServiceLog("EventPerk", "[EventTool] /eventDamage used by " + getName(self) + ":" + self + " on target " + getName(myTarget) + ":" + myTarget + " for " + damage + " damage.", null);
        return SCRIPT_CONTINUE;
    }
    public int eventMoveToMe(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id myTarget = getLookAtTarget(self);
        if (!isIdValid(myTarget))
        {
            sendSystemMessage(self, "Your target for eventMoveToMe is invalid. And by that I mean you don't have anything targetted.", null);
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(myTarget);
        long myTargetsValue = myTarget.getValue();
        if (myTargetsValue < 1000000)
        {
            sendSystemMessage(self, "WARNING!!! This object has a very small OID and is likely a build object. You'll need to move it via the console.", null);
            return SCRIPT_CONTINUE;
        }
        setLocation(myTarget, self);
        utils.setScriptVar(self, "eventTool.lastMoveLocation", here);
        utils.setScriptVar(self, "eventTool.lastMovedObject", myTarget);
        sendSystemMessage(self, "Moved object " + myTarget + " to your location. ", null);
        CustomerServiceLog("EventPerk", "[EventTool] /eventMoveToMe used by " + getName(self) + ":" + self + " on target " + getName(myTarget) + ":" + target + " from location " + here + " to location " + getLocation(self), null);
        return SCRIPT_CONTINUE;
    }
    public int eventUndoMoveToMe(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        location lastLocation = utils.getLocationScriptVar(self, "eventTool.lastMoveLocation");
        location here = getLocation(self);
        obj_id lastTarget = utils.getObjIdScriptVar(self, "eventTool.lastMovedObject");
        if (lastLocation == null)
        {
            sendSystemMessage(self, "The event tool was unable to get your last location. Move cannot be undone.", null);
            return SCRIPT_CONTINUE;
        }
        if (!here.area.equals(lastLocation.area))
        {
            sendSystemMessage(self, "You must be in the same area as your last moved object to undo the move. Move to that area and try again.", null);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(lastTarget))
        {
            sendSystemMessage(self, "Event tool was unable to identify your last moved object. Make sure it still exists and that you are on the same server as that object.", null);
            return SCRIPT_CONTINUE;
        }
        location currentTargetLocation = getLocation(lastTarget);
        setLocation(lastTarget, lastLocation);
        utils.setScriptVar(self, "eventTool.lastMoveLocation", currentTargetLocation);
        utils.setScriptVar(self, "eventTool.lastMovedObject", lastTarget);
        sendSystemMessage(self, "Moved object " + lastTarget + " to " + lastLocation + ".", null);
        CustomerServiceLog("EventPerk", "[EventTool] /eventUndoMoveToMe used by " + getName(self) + ":" + self + " on target " + getName(lastTarget) + ":" + lastTarget + " from location " + currentTargetLocation + " to location " + lastLocation, null);
        return SCRIPT_CONTINUE;
    }
    public int eventWeapon(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null || params.equals(""))
        {
            sendSystemMessage(self, "[Syntax] /eventWeapon <weapon template> <power multiplier %>.", null);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        int numArgs = st.countTokens();
        if (numArgs != 2)
        {
            sendSystemMessage(self, "[Syntax] /eventWeapon <weapon template> <power multiplier %>.", null);
            return SCRIPT_CONTINUE;
        }
        String weaponTemplate = st.nextToken();
        float powerMult = (float)utils.stringToInt(st.nextToken()) / 100;
        obj_id myInventory = utils.getInventoryContainer(self);
        obj_id eventWeapon = weapons.createWeapon(weaponTemplate, myInventory, powerMult);
        if (!isIdValid(eventWeapon))
        {
            sendSystemMessage(self, "WARNING: That is not a recognized weapon template.", null);
            return SCRIPT_CONTINUE;
        }
        setName(eventWeapon, "***INTERNAL USE ONLY WEAPON");
        attachScript(eventWeapon, "event.no_rent");
        setObjVar(eventWeapon, "event.creationTime", getGameTime());
        CustomerServiceLog("EventPerk", "[EventTool] /eventWeapon used by " + getName(self) + ":" + self + " to create " + weaponTemplate + " at " + powerMult + "% of maximum strength.", null);
        return SCRIPT_CONTINUE;
    }
    public int eventStorePet(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id myTarget = getLookAtTarget(self);
        if (!isIdValid(myTarget) || !pet_lib.isPet(target))
        {
            sendSystemMessage(self, "Your target for /eventStorePet is invalid.", null);
            return SCRIPT_CONTINUE;
        }
        pet_lib.storePet(myTarget);
        CustomerServiceLog("EventPerk", "[EventTool] /eventStorePet used by " + getName(self) + ":" + self + " to store pet " + getName(myTarget) + ":" + target, null);
        return SCRIPT_CONTINUE;
    }
    public int killTargetPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id myTarget = params.getObjId("myTarget");
        setPosture(myTarget, POSTURE_INCAPACITATED);
        pclib.coupDeGrace(myTarget, myTarget, true, true);
        return SCRIPT_CONTINUE;
    }
}
