package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Vector;
import java.util.StringTokenizer;

public class gm extends script.base_script
{
    public gm()
    {
    }
    public static final String SCRIPT_CMD = "gm.cmd";
    public static final String SCRIPT_HANDLER = "gm.handler";
    public static final String KEYWORD_SELF = "-self";
    public static final String KEYWORD_TARGET = "-target";
    public static final String KEYWORD_OVERRIDE = "-override";
    public static final String KEYWORD_ID = "-id:";
    public static final String KEYWORD_ADHOC = "-adhoc";
    public static final String KEYWORD_PARSE = "-parse";
    public static final String SCRIPTVAR_MESSAGE_COUNT = "gm.msgcount";
    public static final String SCRIPTVAR_SHOWFACTION_PID = "gm.showFaction.pid";
    public static final String SCRIPTVAR_SEARCHCORPSE_PID = "gm.searchCorpse.pid";
    public static final String SCRIPTVAR_SEARCHCORPSE_TARGET = "gm.searchCorpse.target";
    public static final String SCRIPTVAR_WIPEITEMS_PID = "gm.wipeItems.pid";
    public static final String SCRIPTVAR_WIPEITEMS_TARGET = "gm.wipeItems.target";
    public static final String SCRIPTVAR_SHOWEXPERIENCE_PID = "gm.showExperience.pid";
    public static final String SCRIPTVAR_SETHUE_PID = "gm.setHue.pid";
    public static final String SCRIPTVAR_SETHUE_TARGET = "gm.setHue.target";
    public static final String SCRIPTVAR_SETHUE_DATA = "gm.setHue.data";
    public static final String SCRIPTVAR_MONEY_PID = "gm.money.pid";
    public static final String INSTANCE_AUTH = "gm.instance.override_authorization";
    public static final String HANDLER_CONFIRM_WIPE = "confirmWipeItems";
    public static final String HANDLER_HUE_VAR_UI = "handleHueVarUI";
    public static final int MA_NONE = 0;
    public static final int MA_WITHDRAW = 1;
    public static final int MA_TO_ACCT = 2;
    public static final String HANDLER_MONEY_PASS = "handleMoneyPass";
    public static final String HANDLER_MONEY_FAIL = "handleMoneyFail";
    public static final String[] PLAYER_STATE_NAMES = 
    {
        "Cover",
        "Combat",
        "Peace",
        "Aiming",
        "Meditate",
        "Berserk",
        "Feign Death",
        "Unsed State",
        "Unsed State",
        "Unsed State",
        "Tumbling",
        "Rallied",
        "Stunned",
        "Blinded",
        "Dizzy",
        "Intimidated",
        "Immobilized",
        "Frozen",
        "Swimming",
        "Sitting",
        "Crafting",
        "Unsed State",
        "Masked Scent",
        "Poisoned",
        "Bleeding",
        "Diseased",
        "On Fire"
    };
    public static final String[] ROADMAP_SKILL_OPTIONS = 
    {
        "Select Roadmap",
        "Earn Current Skill",
        "Set Roadmap Progression"
    };
    public static String removeKeyword(String params, String keyword) throws InterruptedException
    {
        if (params == null || params.equals("") || keyword == null || keyword.equals(""))
        {
            return "";
        }
        if (params.equalsIgnoreCase(keyword))
        {
            return "";
        }
        int idx = (toLower(params)).indexOf(keyword);
        if (idx > -1)
        {
            String ret = "";
            StringTokenizer st = new StringTokenizer(params);
            while (st.hasMoreTokens())
            {
                String tmp = st.nextToken();
                if (!(toLower(tmp)).equals(keyword))
                {
                    ret += " " + tmp;
                }
            }
            if (ret == null || ret.equals(""))
            {
                return "";
            }
            return ret.trim();
        }
        return params;
    }
    public static dictionary parseTarget(String params, obj_id target, String cmd) throws InterruptedException
    {
        obj_id self = getSelf();
        String tmpParams = params;
        obj_id tmpTarget = target;
        if (tmpParams.indexOf(KEYWORD_SELF) > -1)
        {
            tmpParams = removeKeyword(tmpParams, KEYWORD_SELF);
            tmpTarget = self;
        }
        else if (tmpParams.indexOf(KEYWORD_TARGET) > -1)
        {
            tmpParams = removeKeyword(tmpParams, KEYWORD_TARGET);
            if (!isIdValid(tmpTarget))
            {
                tmpTarget = self;
            }
        }
        else 
        {
            if (tmpParams.indexOf(KEYWORD_ID) > -1)
            {
                dictionary d = parseObjId(tmpParams);
                obj_id oid = d.getObjId("oid");
                if (isIdValid(oid))
                {
                    tmpTarget = oid;
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "[" + cmd + "] Unable to parse a valid object id!");
                    return null;
                }
                tmpParams = d.getString("params");
            }
            else 
            {
                tmpTarget = self;
            }
        }
        if (isIdValid(tmpTarget))
        {
            dictionary ret = new dictionary();
            ret.put("oid", tmpTarget);
            ret.put("params", tmpParams);
            return ret;
        }
        return new dictionary();
    }
    public static dictionary parseObjId(String params) throws InterruptedException
    {
        dictionary ret = new dictionary();
        if (params == null || params.equals(""))
        {
            return ret;
        }
        int idx = params.indexOf(gm.KEYWORD_ID);
        if (idx == -1)
        {
            return ret;
        }
        String retString = "";
        obj_id retId = null;
        StringTokenizer st = new StringTokenizer(params);
        while (st.hasMoreTokens())
        {
            String arg = st.nextToken();
            if (arg.startsWith(KEYWORD_ID))
            {
                String sId = arg.substring(KEYWORD_ID.length());
                retId = utils.stringToObjId(sId);
            }
            else 
            {
                retString += arg + " ";
            }
        }
        if (isIdValid(retId))
        {
            ret.put("oid", retId);
            ret.put("params", retString.trim());
        }
        return ret;
    }
    public static void attachHandlerScript(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || !isGod(player))
        {
            return;
        }
        if (!hasScript(player, SCRIPT_HANDLER))
        {
            attachScript(player, SCRIPT_HANDLER);
            utils.setScriptVar(player, SCRIPTVAR_MESSAGE_COUNT, 1);
        }
        else 
        {
            int cnt = utils.getIntScriptVar(player, SCRIPTVAR_MESSAGE_COUNT);
            cnt++;
            utils.setScriptVar(player, SCRIPTVAR_MESSAGE_COUNT, cnt);
        }
    }
    public static void decrementMessageCount(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || !isGod(player))
        {
            return;
        }
        int cnt = utils.getIntScriptVar(player, SCRIPTVAR_MESSAGE_COUNT);
        cnt--;
        if (cnt < 1)
        {
            utils.removeScriptVar(player, SCRIPTVAR_MESSAGE_COUNT);
            detachScript(player, SCRIPT_HANDLER);
        }
        else 
        {
            utils.setScriptVar(player, SCRIPTVAR_MESSAGE_COUNT, cnt);
        }
    }
    public static void showSetHueVarUI(obj_id player, obj_id target, dictionary palColData) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target) || palColData == null || palColData.isEmpty())
        {
            return;
        }
        if (utils.hasScriptVar(player, SCRIPTVAR_SETHUE_PID))
        {
            int oldpid = utils.getIntScriptVar(player, SCRIPTVAR_SETHUE_PID);
            forceCloseSUIPage(oldpid);
            cleanupSetHueScriptVars(player);
        }
        Vector entries = new Vector();
        entries.setSize(0);
        Vector dta = new Vector();
        dta.setSize(0);
        Enumeration keys = palColData.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            int val = palColData.getInt(key);
            entries = utils.addElement(entries, key + " (value: " + val + ")");
            dta = utils.addElement(dta, key);
        }
        if (entries != null && entries.size() > 0)
        {
            String title = "Select Hue Variable";
            String prompt = "Select the hue variable that you would like to alter and press OK.";
            int pid = sui.listbox(player, player, prompt, sui.OK_CANCEL, title, entries, HANDLER_HUE_VAR_UI);
            if (pid > -1)
            {
                utils.setScriptVar(player, SCRIPTVAR_SETHUE_PID, pid);
                utils.setScriptVar(player, SCRIPTVAR_SETHUE_TARGET, target);
                utils.setScriptVar(player, SCRIPTVAR_SETHUE_DATA, dta);
                attachHandlerScript(player);
                return;
            }
        }
        sendSystemMessageTestingOnly(player, "/setHue: unable to create hue variable ui!");
    }
    public static void showSetHueColorUI(obj_id player, obj_id target, String varPath) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target) || varPath == null || varPath.equals(""))
        {
            return;
        }
        if (utils.hasScriptVar(player, SCRIPTVAR_SETHUE_PID))
        {
            int oldpid = utils.getIntScriptVar(player, SCRIPTVAR_SETHUE_PID);
            forceCloseSUIPage(oldpid);
            cleanupSetHueScriptVars(player);
        }
        int pid = sui.colorize(player, player, target, varPath, "handleHueColorUI");
        if (pid > -1)
        {
            utils.setScriptVar(player, SCRIPTVAR_SETHUE_PID, pid);
            utils.setScriptVar(player, SCRIPTVAR_SETHUE_TARGET, target);
            utils.setScriptVar(player, SCRIPTVAR_SETHUE_DATA, varPath);
            attachHandlerScript(player);
            return;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "/setHue: unable to create colorize ui!");
        }
    }
    public static void cleanupSetHueScriptVars(obj_id player) throws InterruptedException
    {
        utils.removeScriptVar(player, SCRIPTVAR_SETHUE_PID);
        utils.removeScriptVar(player, SCRIPTVAR_SETHUE_TARGET);
        utils.removeScriptVar(player, SCRIPTVAR_SETHUE_DATA);
    }
    public static void showMoneyStatus(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return;
        }
        if (utils.hasScriptVar(player, SCRIPTVAR_MONEY_PID))
        {
            int oldpid = utils.getIntScriptVar(player, SCRIPTVAR_MONEY_PID);
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, SCRIPTVAR_MONEY_PID);
        }
        String title = "FINANCIAL REPORT";
        String prompt = "Financial Status Information for player:\n(" + target + ")" + utils.getStringName(target);
        prompt += "\n\nNOTE: THIS INFORMATION IS NOT REAL-TIME UPDATED! TIME: " + getGameTime();
        Vector entries = new Vector();
        entries.setSize(0);
        int cash = getCashBalance(target);
        entries = utils.addElement(entries, "Cash: " + cash);
        int bank = getBankBalance(target);
        entries = utils.addElement(entries, "Bank: " + bank);
        int total = getTotalMoney(target);
        entries = utils.addElement(entries, "Total: " + total);
        if (entries != null && entries.size() > 0)
        {
            int pid = sui.listbox(player, prompt, title, entries, "cleanupMoneyStatus");
            if (pid > -1)
            {
                utils.setScriptVar(player, SCRIPTVAR_MONEY_PID, pid);
                attachHandlerScript(player);
            }
        }
    }
    public static boolean setBalance(obj_id player, int type, String sAmt) throws InterruptedException
    {
        if (!isIdValid(player) || sAmt.equals(""))
        {
            return false;
        }
        String var = "";
        int bal = 0;
        switch (type)
        {
            case money.MT_CASH:
            bal = getCashBalance(player);
            break;
            case money.MT_BANK:
            bal = getBankBalance(player);
            break;
            default:
            return false;
        }
        int setType = -1;
        int amt = -1;
        if (sAmt.startsWith("+"))
        {
            if (sAmt.endsWith("+"))
            {
                return false;
            }
            setType = 1;
            amt = utils.stringToInt(sAmt.substring(1));
        }
        else if (sAmt.startsWith("-"))
        {
            if (sAmt.endsWith("-"))
            {
                return false;
            }
            setType = 2;
            amt = utils.stringToInt(sAmt.substring(1));
        }
        else 
        {
            amt = utils.stringToInt(sAmt);
            int delta = amt - bal;
            if (delta > 0)
            {
                setType = 1;
                amt = delta;
            }
            else if (delta < 0)
            {
                setType = 2;
                amt = -delta;
            }
            else 
            {
                return false;
            }
        }
        if (amt < 0)
        {
            return false;
        }
        dictionary d = new dictionary();
        d.put(money.DICT_PLAYER_ID, player);
        d.put(money.DICT_AMOUNT, amt);
        switch (setType)
        {
            case 1:
            if (type == money.MT_CASH)
            {
                d.put("nextAction", MA_WITHDRAW);
            }
            transferBankCreditsFromNamedAccount(money.ACCT_CUSTOMER_SERVICE, player, amt, HANDLER_MONEY_PASS, HANDLER_MONEY_FAIL, d);
            break;
            case 2:
            if (type == money.MT_CASH)
            {
                d.put("nextAction", MA_TO_ACCT);
                depositCashToBank(player, amt, HANDLER_MONEY_PASS, HANDLER_MONEY_FAIL, d);
            }
            else 
            {
                transferBankCreditsToNamedAccount(player, money.ACCT_CUSTOMER_SERVICE, amt, HANDLER_MONEY_PASS, HANDLER_MONEY_FAIL, d);
            }
            break;
            default:
            return false;
        }
        attachHandlerScript(getSelf());
        return true;
    }
    public static void showFactionInformation(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return;
        }
        if (utils.hasScriptVar(player, gm.SCRIPTVAR_SHOWFACTION_PID))
        {
            int oldPid = utils.getIntScriptVar(player, SCRIPTVAR_SHOWFACTION_PID);
            forceCloseSUIPage(oldPid);
            utils.removeScriptVar(player, SCRIPTVAR_SHOWFACTION_PID);
        }
        String name = utils.getStringName(target);
        if (name == null || name.equals(""))
        {
            name = getName(target);
        }
        String title = "FACTION INFORMATION";
        String prompt = "Faction Status Information for target:\n(" + target + ")" + name;
        prompt += "\n\nNOTE: THIS INFORMATION IS NOT REAL-TIME UPDATED! TIME: " + getGameTime();
        Vector entries = new Vector();
        entries.setSize(0);
        String factionName = factions.getFaction(target);
        if (factionName == null || factionName.equals(""))
        {
            factionName = factions.getFactionNameByHashCode(pvpGetAlignedFaction(target));
        }
        entries = utils.addElement(entries, "FACTION: " + factionName);
        entries = utils.addElement(entries, "DECLARED?: " + factions.isDeclared(target));
        if (isPlayer(target))
        {
            entries = utils.addElement(entries, "RANK: " + pvpGetCurrentGcwRank(target));
        }
        String[] enemyFlags = pvpGetEnemyFlags(target);
        if (enemyFlags == null || enemyFlags.length == 0)
        {
            entries = utils.addElement(entries, "ENEMY FLAGS: none");
        }
        else 
        {
            entries = utils.addElement(entries, "ENEMY FLAGS:");
            for (int i = 0; i < enemyFlags.length; i++)
            {
                String[] efData = split(enemyFlags[i], ' ');
                String efaction = factions.getFactionNameByHashCode(utils.stringToInt(efData[1]));
                entries = utils.addElement(entries, " - id:" + efData[0] + " f:" + efaction + " expires: " + efData[2]);
            }
        }
        boolean hasStandings = false;
        if (hasObjVar(target, factions.FACTION))
        {
            obj_var_list ovl = getObjVarList(target, factions.FACTION);
            if (ovl != null)
            {
                int numItems = ovl.getNumItems();
                if (numItems > 0)
                {
                    entries = utils.addElement(entries, "FACTION STANDINGS:");
                    for (int i = 0; i < numItems; i++)
                    {
                        obj_var ov = ovl.getObjVar(i);
                        String fname = ov.getName();
                        float val = ov.getFloatData();
                        entries = utils.addElement(entries, " - " + fname + ": " + val);
                        hasStandings = true;
                    }
                }
            }
        }
        if (!hasStandings)
        {
            entries = utils.addElement(entries, "FACTION STANDINGS: none");
        }
        int pid = sui.listbox(player, player, prompt, sui.OK_ONLY, title, entries, "cleanupShowFaction");
        if (pid > -1)
        {
            utils.setScriptVar(player, SCRIPTVAR_SHOWFACTION_PID, pid);
            attachHandlerScript(player);
        }
    }
    public static void showSetPlayerStateUI(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        if (utils.hasScriptVar(player, "setPlayerStateGM.pid"))
        {
            int old_pid = utils.getIntScriptVar(player, "setPlayerStateGM.pid");
            sui.closeSUI(player, old_pid);
            utils.removeScriptVarTree(player, "setPlayerStateGM");
        }
        String[] stateList = getPlayerState(player);
        String title = "CS Player State Tool";
        String prompt = "Select a state and choose OK to toggle it.  Choose CANCEL or OK with no selection to quit.\n\n**** WARNING **** \nUse with extreme caution invalid state settings can break the game!!";
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, stateList, "handleSetPlayerStateUI");
        utils.setScriptVar(self, "setPlayerStateGM.pid", pid);
    }
    public static String[] getPlayerState(obj_id player) throws InterruptedException
    {
        Vector stateList = new Vector();
        stateList.setSize(0);
        for (int i = 0; i < STATE_NUMBER_OF_STATES; i++)
        {
            int curState = getState(player, i);
            String stateEntry = PLAYER_STATE_NAMES[i];
            int tabCount = 2 - (stateEntry.length() / 15);
            for (int j = 0; j < tabCount; j++)
            {
                stateEntry += "\t";
            }
            if (curState == 1)
            {
                stateEntry += "* ON  *";
            }
            else 
            {
                stateEntry += "* OFF *";
            }
            stateList = utils.addElement(stateList, stateEntry);
        }
        String[] _stateList = new String[0];
        if (stateList != null)
        {
            _stateList = new String[stateList.size()];
            stateList.toArray(_stateList);
        }
        return _stateList;
    }
    public static void cmdResetJedi(obj_id player) throws InterruptedException
    {
        if (isIdNull(player))
        {
            sendSystemMessage(getSelf(), "This command can only be used on players. Please target a player and try again.", null);
            return;
        }
        if (hasObjVar(player, "jedi.converted"))
        {
            removeObjVar(player, "jedi.converted");
            if (hasObjVar(player, "jedi.reverted"))
            {
                int numReverts = getIntObjVar(player, "jedi.reverted");
                numReverts = numReverts + 1;
                setObjVar(player, "jedi.reverted", numReverts);
            }
            else 
            {
                setObjVar(player, "jedi.reverted", 1);
            }
        }
        setObjVar(player, "jedi.usingSui", 1);
        attachScript(player, "player.player_jedi_conversion");
        return;
    }
    public static String[] getRoadmapList() throws InterruptedException
    {
        String[] roadmapList = 
        {
            "smuggler_1a",
            "bounty_hunter_1a",
            "officer_1a",
            "commando_1a",
            "force_sensitive_1a",
            "medic_1a",
            "spy_1a",
            "entertainer_1a",
            "trader_0a",
            "trader_0b",
            "trader_0c",
            "trader_0d"
        };
        return roadmapList;
    }
    public static String[] convertRoadmapNames(String[] list) throws InterruptedException
    {
        String[] newList = new String[list.length];
        for (int i = 0; i < newList.length; i++)
        {
            char branch = list[i].charAt(list[i].length() - 1);
            branch -= 49;
            String roadmapName = "@ui_roadmap:title_" + list[i].substring(0, list[i].lastIndexOf('_'));
            String branchName = "@ui_roadmap:track_title_" + list[i].substring(0, list[i].lastIndexOf('_')) + "_" + branch;
            newList[i] = roadmapName;
            if (list[i].startsWith("trader"))
            {
                newList[i] += " - " + branchName;
            }
        }
        return newList;
    }
    public static String[] convertSkillListNames(String[] skillList) throws InterruptedException
    {
        for (int i = 0; i < skillList.length; i++)
        {
            skillList[i] = "@skl_n:" + skillList[i];
        }
        return skillList;
    }
    public static String[] getAllQuests(obj_id player) throws InterruptedException
    {
        if (!isIdNull(player))
        {
            String[] allActive = getAllActiveQuests(player);
            String[] allComplete = getAllCompletedQuests(player);
            Vector allQuestStringsCombined = new Vector();
            if (allActive != null)
            {
                allQuestStringsCombined.add("All Active Quests");
                for (int i = 0; i < allActive.length; i++)
                {
                    allQuestStringsCombined.add(allActive[i]);
                }
            }
            if (allComplete != null)
            {
                allQuestStringsCombined.add("\nAll Completed Quests");
                for (int i = 0; i < allComplete.length; i++)
                {
                    allQuestStringsCombined.add("*" + allComplete[i]);
                }
            }
            if (allActive == null && allComplete == null)
            {
                allQuestStringsCombined.add("No Active or Completed Quests to Display.");
            }
            else 
            {
                if (allQuestStringsCombined.size() > 0)
                {
                    String[] allQuests = new String[allQuestStringsCombined.size()];
                    allQuestStringsCombined.toArray(allQuests);
                    return allQuests;
                }
            }
        }
        return null;
    }
    public static String[] getAllActiveQuests(obj_id player) throws InterruptedException
    {
        int[] activeQuestIds = questGetAllActiveQuestIds(player);
        if (activeQuestIds.length > -1)
        {
            HashSet allQuestStringsFound = new HashSet();
            String activeQuestString = "";
            for (int i = 0; i < activeQuestIds.length; i++)
            {
                activeQuestString = questGetQuestName(activeQuestIds[i]);
                if (!activeQuestString.equals(""))
                {
                    if (activeQuestString.indexOf("quest/") == 0)
                    {
                        String groundCodeAndTitle = getGroundQuestStringAndTitle(player, activeQuestString);
                        allQuestStringsFound.add(groundCodeAndTitle);
                    }
                    else if (activeQuestString.indexOf("spacequest/") == 0)
                    {
                        String spaceCodeAndTitle = getSpaceQuestStringAndTitle(player, activeQuestString);
                        allQuestStringsFound.add(spaceCodeAndTitle);
                    }
                }
            }
            if (allQuestStringsFound.size() > 0)
            {
                String[] allActiveQuests = new String[allQuestStringsFound.size()];
                allQuestStringsFound.toArray(allActiveQuests);
                Arrays.sort(allActiveQuests);
                return allActiveQuests;
            }
        }
        return null;
    }
    public static String[] getAllCompletedQuests(obj_id player) throws InterruptedException
    {
        int[] completedQuestIds = questGetAllCompletedQuestIds(player);
        if (completedQuestIds.length > -1)
        {
            HashSet allQuestStringsFound = new HashSet();
            String completedQuestString = "";
            for (int i = 0; i < completedQuestIds.length; i++)
            {
                completedQuestString = questGetQuestName(completedQuestIds[i]);
                if (!completedQuestString.equals(""))
                {
                    if (completedQuestString.indexOf("quest/") == 0)
                    {
                        String groundCodeAndTitle = getGroundQuestStringAndTitle(player, completedQuestString);
                        allQuestStringsFound.add(groundCodeAndTitle);
                    }
                    else if (completedQuestString.indexOf("spacequest/") == 0)
                    {
                        String spaceCodeAndTitle = getSpaceQuestStringAndTitle(player, completedQuestString);
                        allQuestStringsFound.add(spaceCodeAndTitle);
                    }
                }
            }
            if (allQuestStringsFound.size() > 0)
            {
                String[] allCompletedQuests = new String[allQuestStringsFound.size()];
                allQuestStringsFound.toArray(allCompletedQuests);
                Arrays.sort(allCompletedQuests);
                return allCompletedQuests;
            }
        }
        return null;
    }
    public static void createCustomUI(obj_id self, String uiTitle, String combinedString) throws InterruptedException
    {
        int page = createSUIPage("/Script.messageBox", self, self);
        setSUIProperty(page, "Prompt.lblPrompt", "LocalText", combinedString);
        setSUIProperty(page, "bg.caption.lblTitle", "Text", uiTitle);
        setSUIProperty(page, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(page, "Prompt.lblPrompt", "GetsInput", "true");
        setSUIProperty(page, "btnCancel", "Visible", "true");
        setSUIProperty(page, "btnRevert", "Visible", "false");
        setSUIProperty(page, "btnOk", "Visible", "false");
        showSUIPage(page);
        flushSUIPage(page);
    }
    public static void createDumpTargetUI(obj_id self, String combinedString) throws InterruptedException
    {
        String uiTitle = "Target Data";
        int page = createSUIPage("/Script.messageBox", self, self);
        setSUIProperty(page, "Prompt.lblPrompt", "LocalText", combinedString);
        setSUIProperty(page, "bg.caption.lblTitle", "Text", uiTitle);
        setSUIProperty(page, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(page, "Prompt.lblPrompt", "GetsInput", "true");
        setSUIProperty(page, "btnCancel", "Visible", "true");
        setSUIProperty(page, "btnRevert", "Visible", "false");
        setSUIProperty(page, "btnOk", sui.PROP_TEXT, "Create File");
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedOk, "%button0%", "exportCsDumpFile");
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedCancel, "%button0%", "exportCsDumpFile");
        showSUIPage(page);
        flushSUIPage(page);
    }
    public static void forceClearQuest(obj_id player, String questName) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, questName) || groundquests.hasCompletedQuest(player, questName))
        {
            groundquests.clearQuest(player, questName);
            sendSystemMessageTestingOnly(player, "Clear Quest function completed.  The quest removed was " + questName);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Either the player doesn't have this quest or the quest string was incorrect. Space Quests cannot be cleared using this command.");
        }
    }
    public static void forceRegrantQuest(obj_id player, String questName) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, questName) || groundquests.hasCompletedQuest(player, questName))
        {
            groundquests.clearQuest(player, questName);
            groundquests.requestGrantQuest(player, questName, true);
            sendSystemMessageTestingOnly(player, "Regrant Quest function completed.");
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Either the player doesn't have this quest or the quest string was incorrect. Space Quests cannot be cleared and granted using this command.");
        }
    }
    public static String getGroundQuestStringAndTitle(obj_id player, String questString) throws InterruptedException
    {
        String localizeThis = questString.substring(6);
        String questStringLoc = "quest/ground/" + localizeThis;
        String questTitle = localize(new string_id(questStringLoc, "journal_entry_title"));
        return questString + " - " + questTitle;
    }
    public static String getSpaceQuestStringAndTitle(obj_id player, String questString) throws InterruptedException
    {
        int firstSlash = questString.indexOf("/");
        int lastSlash = questString.lastIndexOf("/");
        String questType = questString.substring(firstSlash + 1, lastSlash);
        String spaceQuestString = questString.substring(lastSlash + 1);
        String questStringLoc = "spacequest/" + questType + "/" + spaceQuestString;
        String questTitle = localize(new string_id(questStringLoc, "title"));
        return spaceQuestString + " - " + questTitle;
    }
    public static boolean isInstanceAuthorized(obj_id subject) throws InterruptedException
    {
        return utils.hasScriptVar(subject, INSTANCE_AUTH);
    }
}
