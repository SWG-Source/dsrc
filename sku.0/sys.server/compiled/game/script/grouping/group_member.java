package script.grouping;

import script.*;
import script.library.*;

public class group_member extends script.base_script
{
    public group_member()
    {
    }
    private static final string_id SID_GROUP_CREDIT_SPLIT_FAILED = new string_id("group", "credit_split_failed");
    private static final string_id SID_LOOT_FREE_FOR_ALL = new string_id("group", "loot_free_for_all");
    private static final string_id SID_LOOT_MASTER_LOOTER = new string_id("group", "loot_master_looter");
    private static final string_id SID_LOOT_LOTTERY = new string_id("group", "loot_lottery");
    private static final string_id SID_LOOT_RANDOM = new string_id("group", "loot_random");
    public static final String SCRIPT_ME = group.SCRIPT_GROUP_MEMBER;
    private static final string_id[] LOOT_OPTIONS =
    {
        SID_LOOT_FREE_FOR_ALL,
        SID_LOOT_MASTER_LOOTER,
        SID_LOOT_LOTTERY,
        SID_LOOT_RANDOM
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isIdValid(getGroupObject(self)))
        {
            detachScript(self, SCRIPT_ME);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id groupObject = getGroupObject(self);
        if (isIdValid(groupObject))
        {
            dictionary groupObjectMessageData = new dictionary();
            groupObjectMessageData.put("memberObjectId", self);
            messageTo(groupObject, "addGroupMember", groupObjectMessageData, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id myGroup = getGroupObject(self);
        if (!isIdValid(myGroup))
        {
            detachScript(self, SCRIPT_ME);
            return SCRIPT_CONTINUE;
        }
        group.notifyIncapacitation(myGroup, self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        group.destroyGroupWaypoint(self);
        deltadictionary scriptVars = self.getScriptVars();
        if (scriptVars != null)
        {
            scriptVars.remove("updateSequence");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        detachScript(self, SCRIPT_ME);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        deltadictionary scriptVars = self.getScriptVars();
        if (scriptVars != null)
        {
            group.destroyGroupWaypoint(self);
            scriptVars.remove("updateSequence");
        }
        obj_id groupObject = getGroupObject(self);
        if (isIdValid(groupObject))
        {
            if (utils.hasScriptVar(groupObject, combat.VAR_GROUP_VOLLEY_TARGET))
            {
                obj_id target = utils.getObjIdScriptVar(groupObject, combat.VAR_GROUP_VOLLEY_TARGET);
                stopClientEffectObjByLabel(new obj_id[]
                {
                    self
                }, target, combat.ID_VOLLEY_FIRE_PARTICLE);
            }
            dictionary msgData = new dictionary();
            msgData.put("sender", self);
            messageTo(groupObject, "removeGroupMember", msgData, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int volleyTargetDone(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("objTarget"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("objTarget");
        stopClientEffectObjByLabel(new obj_id[]
        {
            self
        }, target, combat.ID_VOLLEY_FIRE_PARTICLE);
        return SCRIPT_CONTINUE;
    }
    public int handleCloneRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("grouping", "HANDLER:handleCloneRespawn:: (" + self + ") " + getName(self));
        obj_id myGroup = getGroupObject(self);
        if (!isIdValid(myGroup))
        {
            detachScript(self, SCRIPT_ME);
            return SCRIPT_CONTINUE;
        }
        group.notifyCloned(myGroup, self);
        return SCRIPT_CONTINUE;
    }
    public int cmdSplitCreditsWithGroup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params != null && !params.equals(""))
        {
            int amt = utils.stringToInt(params);
            if (amt > 0)
            {
                group.splitCoins(amt);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRequestSplitShare(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.equals(""))
        {
            obj_id target = params.getObjId("target");
            int amt = params.getInt("amt");
            if (isIdValid(target) && amt > 0)
            {
                transferCashTo(target, self, amt, group.HANDLER_SPLIT_SUCCESS, group.HANDLER_SPLIT_FAILURE, params);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRequestPayoutShare(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.equals(""))
        {
            obj_id target = params.getObjId("target");
            int amt = params.getInt("amt");
            if (isIdValid(target) && amt > 0)
            {
                transferBankCreditsTo(target, self, amt, group.HANDLER_SPLIT_SUCCESS, group.HANDLER_SPLIT_FAILURE, params);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePayoutRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        String acct = params.getString(money.DICT_ACCT_NAME);
        int amt = params.getInt(money.DICT_AMOUNT);
        if (acct != null && !acct.equals("") && amt > 0)
        {
            transferBankCreditsFromNamedAccount(acct, self, amt, "handlePayoutComplete", "noHandler", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePayoutComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("msg.id");
        String handler = params.getString("msg.handler");
        if (isIdValid(target) && handler != null && !handler.equals(""))
        {
            messageTo(target, handler, params, 0, false);
        }
        int amt = params.getInt(money.DICT_AMOUNT);
        if (amt > 0)
        {
            group.splitBank(amt, params);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSplitSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int dividend = params.getInt("amt");
        if (dividend == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(self))
        {
            obj_id gid = getGroupObject(self);
            if (isIdValid(gid))
            {
                transferCashTo(self, getGroupLeaderId(gid), dividend, "noHandler", "noHandler", params);
            }
            return SCRIPT_CONTINUE;
        }
        String reason = params.getString("reason");
        string_id reasonId = params.getStringId("reasonId");
        if ((reason == null || reason.equals("")) && reasonId == null)
        {
            prose_package ppSplit = prose.getPackage(new string_id("group", "prose_split"), dividend);
            sendSystemMessageProse(self, ppSplit);
        }
        else 
        {
            if (reasonId != null)
            {
                sendSystemMessage(self, reasonId);
                prose_package ppSplit = prose.getPackage(new string_id("group", "prose_split"), dividend);
                sendSystemMessageProse(self, ppSplit);
            }
            else 
            {
                prose_package ppSplitReason = prose.getPackage(new string_id("group", "prose_split_reason"), reason, dividend);
                sendSystemMessageProse(self, ppSplitReason);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSplitFailure(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("target");
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (target != self)
        {
            messageTo(target, group.HANDLER_SPLIT_SUCCESS, params, 0f, false);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, SID_GROUP_CREDIT_SPLIT_FAILED);
        return SCRIPT_CONTINUE;
    }
    public int handleSquadLeaderXpNotify(obj_id self, dictionary params) throws InterruptedException
    {
        xp.notifySquadLeaderXp(self);
        return SCRIPT_CONTINUE;
    }
    public int updateGroupWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            int sequence = params.getInt("updateSequence");
            deltadictionary scriptVars = self.getScriptVars();
            if (scriptVars != null)
            {
                int lastUpdateMessage = scriptVars.getInt("updateSequence");
                if (lastUpdateMessage < sequence)
                {
                    lastUpdateMessage = sequence;
                    scriptVars.put("updateSequence", lastUpdateMessage);
                    location newWaypointLocation = params.getLocation("waypointLocation");
                    if (newWaypointLocation != null)
                    {
                        obj_id groupWaypoint = getObjIdObjVar(self, "groupWaypoint");
                        if (groupWaypoint != null)
                        {
                            setWaypointLocation(groupWaypoint, newWaypointLocation);
                        }
                        else 
                        {
                            groupWaypoint = createWaypointInDatapad(self, newWaypointLocation);
                            setObjVar(self, "groupWaypoint", groupWaypoint);
                        }
                        setWaypointName(groupWaypoint, "@group:groupwaypoint");
                        setWaypointColor(groupWaypoint, "yellow");
                        setWaypointActive(groupWaypoint, true);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int missionWaypointUpdated(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id missionObject = params.getObjId("missionId");
            if (isIdValid(missionObject))
            {
                obj_id groupObject = getGroupObject(self);
                if (groupObject != null)
                {
                    messageTo(groupObject, "recaclulateNearestGroupWaypoint", null, 0, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int waitForMissionObjectDestruction(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id missionObject = params.getObjId("missionId");
            if (isIdValid(missionObject))
            {
                obj_id player = getMissionHolder(missionObject);
                if (isIdValid(player))
                {
                    messageTo(self, "waitForMissionObjectDestruction", params, 1, false);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        obj_id groupObject = getGroupObject(self);
        if (groupObject != null)
        {
            messageTo(groupObject, "removeMissionLocation", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int missionObjectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            messageTo(self, "waitForMissionObjectDestruction", params, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyGroupWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        group.destroyGroupWaypoint(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdGroupLootSet(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!group.isGrouped(self))
        {
            sendSystemMessage(self, new string_id(group.GROUP_STF, "group_only"));
            return SCRIPT_CONTINUE;
        }
        obj_id leader = group.getLeader(self);
        if (leader == null)
        {
            sendSystemMessage(self, new string_id(group.GROUP_STF, "notFound"));
            return SCRIPT_CONTINUE;
        }
        if (leader != self)
        {
            String leader_only;
            switch(getGroupLootRule(group.getGroupObject(leader))){
                case 0:
                    leader_only = "leader_only_free4all";
                    break;
                case 1:
                    leader_only = "leader_only_master";
                    break;
                case 2:
                    leader_only = "leader_only_lottery";
                    break;
                default:
                    leader_only = "leader_only";
                    break;
            }
            sendSystemMessage(self, new string_id(group.GROUP_STF, leader_only));
        }
        groupLootSui(self);
        return SCRIPT_CONTINUE;
    }
    private int groupLootSui(obj_id self) throws InterruptedException
    {
        int pid = createSUIPage(sui.SUI_LISTBOX, self, self, "handleLootOptionSelected");
        string_id sid_title = new string_id(group.GROUP_STF, "set_loot_type_title");
        string_id sid_text = new string_id(group.GROUP_STF, "set_loot_type_text");
        String title = getString(sid_title);
        String text = getString(sid_text);
        string_id sid_select = new string_id(group.GROUP_STF, "select");
        string_id sid_cancel = new string_id(group.GROUP_STF, "cancel");
        String select = getString(sid_select);
        String cancel = getString(sid_cancel);
        if (text == null || text.equals(""))
        {
            text = "Choose from the following:";
        }
        if (title == null || title.equals(""))
        {
            title = "CHOOSE GROUP LOOT TYPE";
        }
        String loot_options[] = new String[LOOT_OPTIONS.length];
        for (int i = 0; i < LOOT_OPTIONS.length; i++)
        {
            loot_options[i] = utils.packStringId(LOOT_OPTIONS[i]);
        }
        sui.listbox(self, self, text, sui.OK_CANCEL, title, loot_options, "handleLootOptionSelected", true, false);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL_REFRESH);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, select);
        setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, sui.PROP_TEXT, cancel);
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        return pid;
    }
    public int handleLootOptionSelected(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        obj_id groupObject = group.getGroupObject(player);
        obj_id[] objMembersWhoExist = utils.getLocalGroupMemberIds(groupObject);
        if (player == null)
        {
            return SCRIPT_CONTINUE;
        }
        String newTypeString = "free4all";
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
                setGroupLootRule(groupObject, group.FREE_FOR_ALL);
                newTypeString = "free4all";
                break;
            case 1:
                setGroupLootRule(groupObject, group.MASTER_LOOTER);
                newTypeString = "master";
                break;
            case 2:
                setGroupLootRule(groupObject, group.LOTTERY);
                newTypeString = "lotto";
                break;
            case 3:
                setGroupLootRule(groupObject, group.RANDOM);
                newTypeString = "random";
                break;
        }
        dictionary param = new dictionary();
        param.put("selection", "selected_" + newTypeString);
        string_id message = new string_id(group.GROUP_STF, "selected_" + newTypeString);
        for (obj_id objMember : objMembersWhoExist) {
            if (objMember != player) {
                messageTo(objMember, "groupLootChangedSui", param, 1, true);
            }
        }
        sendSystemMessage(self, message);
        return SCRIPT_CONTINUE;
    }
    public int groupLootChangedSui(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String selection = params.getString("selection");
        string_id message = new string_id(group.GROUP_STF, selection);
        int pid = createSUIPage(sui.SUI_MSGBOX, self, self, "lootTypeReset");
        setObjVar(self, "group.lottosui", pid);
        string_id sidlootChanged = new string_id(group.GROUP_STF, "loot_changed");
        String text = getString(message);
        String title = getString(sidlootChanged);
        if (title == null || title.equals(""))
        {
            title = "LOOT CHANGED";
        }
        if (text == null || text.equals(""))
        {
            text = "Choose";
        }
        setSUIProperty(pid, "", "Size", "450,375");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.msgboxButtonSetup(pid, sui.YES_NO);
        string_id sid_leave = new string_id(group.GROUP_STF, "leave_group");
        string_id sid_ok = new string_id(group.GROUP_STF, "ok");
        String str_leave = getString(sid_leave);
        String str_ok = getString(sid_ok);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, str_ok);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, str_leave);
        subscribeToSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT);
        showSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int lootTypeReset(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_OVERRIDE;
        }
        String widgetName = params.getString("eventWidgetName");
        if (widgetName == null)
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_OK)
        {
            queueCommand(player, (1348589140), player, "", COMMAND_PRIORITY_FRONT);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
