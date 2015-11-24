package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.sui;
import script.library.utils;
import java.util.HashSet;
import script.library.qa;

public class qabadge extends script.base_script
{
    public qabadge()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qabadge");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qabadge");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id player = self;
        if (isGod(player))
        {
            if ((toLower(text)).equals("qabadges"))
            {
                Vector vectorMenuArray = new Vector();
                vectorMenuArray.addElement("*Add All Badges*");
                vectorMenuArray.addElement("*Remove All Badges*");
                String[] badgePages = getAllCollectionPagesInBook("badge_book");
                if ((badgePages != null) && (badgePages.length > 0))
                {
                    for (int i = 0; i < badgePages.length; ++i)
                    {
                        if (!badgePages[i].equals("bdg_accumulation"))
                        {
                            vectorMenuArray.addElement(badgePages[i]);
                        }
                    }
                }
                String[] mainMenuArray = new String[vectorMenuArray.size()];
                vectorMenuArray.toArray(mainMenuArray);
                utils.setScriptVar(self, "qabadge.mainMenu", mainMenuArray);
                if (mainMenuArray.length < 1)
                {
                    sendSystemMessageTestingOnly(player, "Badge UI creation failed.");
                }
                else 
                {
                    utils.setScriptVar(player, "qabadge.mainMenu", mainMenuArray);
                    qa.refreshMenu(player, "Choose the Badge", "Badge Granter", mainMenuArray, "mainMenuOptions", true, "qabadge.pid");
                }
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int mainMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "qabadge.pid"))
            {
                String previousBadgeArray[] = utils.getStringArrayScriptVar(self, "qabadge.mainMenu");
                obj_id player = sui.getPlayerId(params);
                if ((params == null) || (params.isEmpty()))
                {
                    sendSystemMessageTestingOnly(player, "Failing, params empty");
                    utils.removeScriptVarTree(player, "qabadge");
                    utils.removeScriptVarTree(player, "qatool");
                    return SCRIPT_CONTINUE;
                }
                int btn = sui.getIntButtonPressed(params);
                int idx = sui.getListboxSelectedRow(params);
                if (btn == sui.BP_CANCEL)
                {
                    utils.removeScriptVarTree(player, "qabadge");
                    utils.removeScriptVarTree(player, "qatool");
                    return SCRIPT_CONTINUE;
                }
                if (btn == sui.BP_REVERT)
                {
                    String[] options = utils.getStringArrayScriptVar(player, "qatool.toolMainMenu");
                    String mainTitle = utils.getStringScriptVar(player, "qatool.title");
                    String mainPrompt = utils.getStringScriptVar(player, "qatool.prompt");
                    if (options == null)
                    {
                        sendSystemMessageTestingOnly(player, "You didn't start from the main tool menu");
                        String[] mainMenuArray = utils.getStringArrayScriptVar(player, "qabadge.mainMenu");
                        qa.refreshMenu(player, "Choose the Badge", "Badge Granter", mainMenuArray, "mainMenuOptions", true, "qabadge.pid");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        qa.refreshMenu(self, mainPrompt, mainTitle, options, "toolMainMenu", true, "qatool.pid");
                        utils.removeScriptVarTree(player, "qabadge");
                        return SCRIPT_CONTINUE;
                    }
                }
                if (idx < 0)
                {
                    utils.removeScriptVarTree(player, "qabadge");
                    utils.removeScriptVarTree(player, "qatool");
                    sendSystemMessageTestingOnly(player, "You didnt have anything selected");
                    return SCRIPT_CONTINUE;
                }
                String badgeChoice = previousBadgeArray[idx];
                if (badgeChoice.equals("*Add All Badges*"))
                {
                    String[] allBadges = getAllCollectionSlotsInBook("badge_book");
                    if ((allBadges != null) && (allBadges.length > 0))
                    {
                        for (int i = 0; i < allBadges.length; i++)
                        {
                            badge.grantBadge(player, allBadges[i]);
                        }
                    }
                    String[] mainMenuArray = utils.getStringArrayScriptVar(player, "qabadge.mainMenu");
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has added all in game badges from their current character using the QA Badge Tool.");
                    qa.refreshMenu(player, "Choose the Badge", "Badge Granter", mainMenuArray, "mainMenuOptions", "qabadge.pid", sui.OK_CANCEL_REFRESH);
                    return SCRIPT_CONTINUE;
                }
                if (badgeChoice.equals("*Remove All Badges*"))
                {
                    String[] allBadges = getAllCollectionSlotsInBook("badge_book");
                    if ((allBadges != null) && (allBadges.length > 0))
                    {
                        for (int i = 0; i < allBadges.length; i++)
                        {
                            badge.revokeBadge(player, allBadges[i], true);
                        }
                    }
                    String[] mainMenuArray = utils.getStringArrayScriptVar(player, "qabadge.mainMenu");
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has removed all their badges from their current character using the QA Badge Tool.");
                    qa.refreshMenu(player, "Choose the Badge", "Badge Granter", mainMenuArray, "mainMenuOptions", "qabadge.pid", sui.OK_CANCEL_REFRESH);
                    return SCRIPT_CONTINUE;
                }
                String[] menuArray = getAllCollectionSlotsInPage(badgeChoice);
                if ((menuArray == null) || (menuArray.length < 1))
                {
                    sendSystemMessageTestingOnly(player, "Badge UI creation failed.");
                }
                else 
                {
                    qa.refreshMenu(self, "Choose the Badge", "Badge Granter", menuArray, "assignMenuOptions", "qabadge.pid", sui.OK_CANCEL_REFRESH);
                    utils.setScriptVar(self, "qabadge.Menu", menuArray);
                    utils.setScriptVar(self, "qabadge.Main_choice", badgeChoice);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int assignMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "qabadge.pid"))
            {
                String previousBadgeArray[] = utils.getStringArrayScriptVar(self, "qabadge.Menu");
                obj_id player = sui.getPlayerId(params);
                if ((params == null) || (params.isEmpty()))
                {
                    sendSystemMessageTestingOnly(player, "Failing, params empty");
                    utils.removeScriptVarTree(player, "qabadge");
                    utils.removeScriptVarTree(player, "qatool");
                    return SCRIPT_CONTINUE;
                }
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    utils.removeScriptVarTree(player, "qabadge");
                    utils.removeScriptVarTree(player, "qatool");
                    return SCRIPT_CONTINUE;
                }
                if (btn == sui.BP_REVERT)
                {
                    String[] mainMenuArray = utils.getStringArrayScriptVar(player, "qabadge.mainMenu");
                    qa.refreshMenu(player, "Choose the Badge", "Badge Granter", mainMenuArray, "mainMenuOptions", "qabadge.pid", sui.OK_CANCEL_REFRESH);
                    return SCRIPT_CONTINUE;
                }
                int idx = sui.getListboxSelectedRow(params);
                if (idx < 0)
                {
                    utils.removeScriptVarTree(player, "qabadge");
                    utils.removeScriptVarTree(player, "qatool");
                    sendSystemMessageTestingOnly(player, "You didnt have anything selected");
                    return SCRIPT_CONTINUE;
                }
                String badgeChoice = previousBadgeArray[idx];
                badgeAssign(player, badgeChoice);
                String refreshBadge = utils.getStringScriptVar(player, "qabadge.Main_choice");
                String[] menuArray = getAllCollectionSlotsInPage(refreshBadge);
                qa.refreshMenu(player, "Choose the Badge", "Badge Granter", menuArray, "assignMenuOptions", "qabadge.pid", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void badgeAssign(obj_id player, String badgeName) throws InterruptedException
    {
        if (isGod(player))
        {
            boolean hasBadge = badge.hasBadge(player, badgeName);
            if (hasBadge != true)
            {
                badge.grantBadge(player, badgeName);
                sendSystemMessageTestingOnly(player, "Badge granted");
                badge.checkBadgeCount(player);
                CustomerServiceLog("qaTool", "User: (" + player + ") " + getName(player) + " has added a badge to their current character using the QA Badge Tool.");
                explorerBadge(player);
            }
            else 
            {
                badge.revokeBadge(player, badgeName, true);
                sendSystemMessageTestingOnly(player, "Badge revoked");
                badge.checkBadgeCount(player);
                CustomerServiceLog("qaTool", "User: (" + player + ") " + getName(player) + " has removed a badge to their current character using the QA Badge Tool.");
                explorerBadge(player);
            }
        }
    }
    public void explorerBadge(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            int[] intExplorerBadges = dataTableGetIntColumn("datatables/badge/exploration_badges.iff", "intIndex");
            int intExplBadgeCount = 0;
            for (int intI = 0; intI < intExplorerBadges.length; intI++)
            {
                String badgeName = getCollectionSlotName(intExplorerBadges[intI]);
                if ((badgeName != null) && (badgeName.length() > 0) && badge.hasBadge(self, badgeName))
                {
                    intExplBadgeCount = intExplBadgeCount + 1;
                }
            }
            if (intExplBadgeCount >= 10)
            {
                if (!badge.hasBadge(self, "bdg_exp_10_badges"))
                {
                    badge.grantBadge(self, "bdg_exp_10_badges");
                    return;
                }
            }
            if (intExplBadgeCount >= 20)
            {
                if (!badge.hasBadge(self, "bdg_exp_20_badges"))
                {
                    badge.grantBadge(self, "bdg_exp_20_badges");
                    return;
                }
            }
            if (intExplBadgeCount >= 30)
            {
                if (!badge.hasBadge(self, "bdg_exp_30_badges"))
                {
                    badge.grantBadge(self, "bdg_exp_30_badges");
                    return;
                }
            }
            if (intExplBadgeCount >= 40)
            {
                if (!badge.hasBadge(self, "bdg_exp_40_badges"))
                {
                    badge.grantBadge(self, "bdg_exp_40_badges");
                    return;
                }
            }
            if (intExplBadgeCount >= 45)
            {
                if (!badge.hasBadge(self, "bdg_exp_45_badges"))
                {
                    badge.grantBadge(self, "bdg_exp_45_badges");
                    return;
                }
            }
        }
    }
}
