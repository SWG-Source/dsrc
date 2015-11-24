package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.Arrays;
import java.util.HashSet;
import script.library.buff;
import script.library.dump;
import script.library.features;
import script.library.jedi;
import script.library.skill;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class qa extends script.base_script
{
    public qa()
    {
    }
    public static final String[] QATOOL_MAIN_MENU = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
    public static final String QATOOL_TITLE = "QA Tools";
    public static final String QATOOL_PROMPT = "Choose the tool you want to use";
    public static final String[] SPACE_QUEST_TYPES = 
    {
        "assassinate",
        "delivery",
        "delivery_no_pickup",
        "destroy",
        "destroy_duty",
        "destroy_surpriseattack",
        "escort",
        "escort_duty",
        "inspect",
        "patrol",
        "recovery",
        "recovery_duty",
        "rescue",
        "rescue_duty",
        "space_battle",
        "survival"
    };
    public static final String[] GRANT_SPACE_SKILLS = 
    {
        "_novice",
        "_droid_01",
        "_procedures_01",
        "_weapons_01",
        "_starships_01",
        "_droid_02",
        "_procedures_02",
        "_weapons_02",
        "_starships_02",
        "_droid_03",
        "_procedures_03",
        "_weapons_03",
        "_starships_03",
        "_droid_04",
        "_procedures_04",
        "_weapons_04",
        "_starships_04",
        "_master"
    };
    public static final String[] REVOKE_SPACE_SKILLS = 
    {
        "_master",
        "_starships_04",
        "_weapons_04",
        "_procedures_04",
        "_droid_04",
        "_starships_03",
        "_weapons_03",
        "_procedures_03",
        "_droid_03",
        "_starships_02",
        "_weapons_02",
        "_procedures_02",
        "_droid_02",
        "_starships_01",
        "_weapons_01",
        "_procedures_01",
        "_droid_01",
        "_novice"
    };
    public static final String[][] KNOWN_CHARACTER_SLOTS = 
    {
        
        {
            "hat",
            "earring_l",
            "earring_r",
            "eyes",
            "neck",
            "chest1",
            "chest2",
            "chest3_l",
            "chest3_r",
            "back",
            "cloak",
            "bicep_l",
            "bicep_r",
            "bracer_upper_r",
            "bracer_lower_r",
            "bracer_upper_l",
            "bracer_lower_l",
            "wrist_l",
            "wrist_r",
            "gloves",
            "hold_r",
            "hold_l",
            "ring_l",
            "ring_r",
            "utility_belt",
            "pants1",
            "pants2",
            "shoes"
        },
        
        {
            "Hat: ",
            "Left ear ring: ",
            "Right ear ring: ",
            "Eyes: ",
            "Neck: ",
            "Primary chest: ",
            "Secondary chest: ",
            "Trinary chest left",
            "Trinary chest right",
            "Back:",
            "Cloak: ",
            "Left bicep: ",
            "Right bicep: ",
            "Upper right bracer: ",
            "Lower right bracer: ",
            "Upper left bracer: ",
            "Left lower bracer: ",
            "Left wrist: ",
            "Right wrist: ",
            "Gloves: ",
            "Holding right hand: ",
            "Holding left hand: ",
            "Left ring: ",
            "Right ring: ",
            "Belt: ",
            "Primary pants: ",
            "Secondary pants: ",
            "Shoes: "
        }
    };
    public static final String DAMAGE_PID_SCRIPTVAR = "doDamage.pid";
    public static final String DAMAGE_SCRIPTVAR = "doDamageVar";
    public static final String HEAL_PID_SCRIPTVAR = "healDamage.pid";
    public static final String HEAL_SCRIPTVAR = "healDamageVar";
    public static final String HEAL_TOOL_PROMPT = "Give the amount you want to heal the target for.  This tool will heal in the amount you specify as long as it doesn't exceed the target's maximum health.";
    public static final String HEAL_TOOL_TITLE = "HEAL AMOUNT";
    public static final String DAMAGE_TOOL_PROMPT = "Give the amount you want to damage the target for.  This tool will cause damage in the amount you specify.  ARMOR AND OTHER MITIGATION WILL NOT BE CONSIDERED.  Use the Mitigation Tool to test Mitigation.";
    public static final String DAMAGE_TOOL_TITLE = "DAMAGE AMOUNT";
    public static final String FROG_STRING = "object/tangible/terminal/terminal_character_builder.iff";
    public static final String KASHYYYK_FROG_STRING = "object/tangible/terminal/terminal_kashyyyk_content.iff";
    public static void qaToolMainMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, QATOOL_PROMPT, QATOOL_TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, "qatool.pid");
    }
    public static void refreshMenu(obj_id player, String prompt, String title, String[] options, String myHandler, boolean cancel, String PIDVar) throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindow(player, PIDVar);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, options, myHandler, false, false);
        if (cancel == false)
        {
            setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "Back");
        }
        sui.showSUIPage(pid);
        setWindowPid(player, pid, PIDVar);
    }
    public static void refreshMenu(obj_id player, String prompt, String title, String[] options, String myHandler, boolean cancel, String PIDVar, String scriptVar) throws InterruptedException
    {
        closeOldWindow(player, scriptVar);
        int pid = sui.listbox(player, player, prompt, sui.OK_CANCEL, title, options, myHandler, false, false);
        if (cancel == false)
        {
            setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "Back");
        }
        sui.showSUIPage(pid);
        setWindowPid(player, pid, PIDVar);
        utils.setScriptVar(player, scriptVar, options);
    }
    public static void refreshMenu(obj_id player, String prompt, String title, String[] options, String myHandler, String scriptVar, int btnConfig) throws InterruptedException
    {
        closeOldWindow(player, scriptVar);
        int pid = sui.listbox(player, player, prompt, btnConfig, title, options, myHandler, false, false);
        sui.listboxUseOtherButton(pid, "Back");
        sui.showSUIPage(pid);
        setWindowPid(player, pid, scriptVar);
    }
    public static void refreshMenu(obj_id player, String prompt, String title, String[] options, String myHandler, String PIDVar, String scriptVar, int btnConfig) throws InterruptedException
    {
        closeOldWindow(player, scriptVar);
        int pid = sui.listbox(player, player, prompt, btnConfig, title, options, myHandler, false, false);
        sui.listboxUseOtherButton(pid, "Back");
        sui.showSUIPage(pid);
        setWindowPid(player, pid, PIDVar);
        utils.setScriptVar(player, scriptVar, options);
    }
    public static void refreshMenu(obj_id player, String prompt, String title, String[][] options, String myHandler, boolean cancel, String PIDVar, String scriptVar) throws InterruptedException
    {
        closeOldWindow(player, scriptVar);
        String[] firstDimension = new String[options[0].length];
        for (int i = 0; i < options[0].length; i++)
        {
            firstDimension[i] = options[0][i];
        }
        if (firstDimension.length > 0)
        {
            int pid = sui.listbox(player, player, prompt, sui.OK_CANCEL, title, firstDimension, myHandler, false, false);
            if (cancel == false)
            {
                setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "Back");
            }
            sui.showSUIPage(pid);
            setWindowPid(player, pid, PIDVar);
            utils.setScriptVar(player, scriptVar, options);
        }
    }
    public static void showConfirmationSui(obj_id self, String titleText, String confirmationMessage, String handler) throws InterruptedException
    {
        int pid = sui.msgbox(self, self, confirmationMessage, sui.YES_NO, titleText, handler);
    }
    public static void checkParams(dictionary params, String scriptVar) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if ((params == null) || (params.isEmpty()))
        {
            sendSystemMessageTestingOnly(player, "Failing, params were empty!");
            removeScriptVars(player, scriptVar);
            return;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            removeScriptVars(player, scriptVar);
            return;
        }
    }
    public static void checkParams(dictionary params, String scriptVar, boolean chkIndex) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if ((params == null) || (params.isEmpty()))
        {
            sendSystemMessageTestingOnly(player, "Failing, params were empty!");
            utils.removeScriptVarTree(player, scriptVar);
            return;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (chkIndex == true)
        {
            if (idx == -1)
            {
                utils.removeScriptVarTree(player, scriptVar);
                return;
            }
        }
    }
    public static void closeOldWindow(obj_id player, String scriptVar) throws InterruptedException
    {
        if (utils.hasScriptVar(player, scriptVar))
        {
            int oldpid = utils.getIntScriptVar(player, scriptVar);
            forceCloseSUIPage(oldpid);
            utils.removeScriptVarTree(player, scriptVar);
        }
    }
    public static void setWindowPid(obj_id player, int pid, String scriptVar) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, scriptVar, pid);
        }
    }
    public static String[] populateArray(obj_id player, String datatableName, String choice, String column1, String column2) throws InterruptedException
    {
        int firstColumn = dataTableFindColumnNumber(datatableName, column1);
        int secondColumn = dataTableFindColumnNumber(datatableName, column2);
        String[] firstColumnArray = dataTableGetStringColumn(datatableName, firstColumn);
        String[] secondColumnArray = dataTableGetStringColumn(datatableName, secondColumn);
        HashSet theSet = new HashSet();
        if (firstColumnArray == null || secondColumnArray == null)
        {
            return null;
        }
        for (int y = 0; y < firstColumnArray.length; y++)
        {
            if (choice.equals(firstColumnArray[y]))
            {
                if (!secondColumnArray[y].equals(""))
                {
                    theSet.add(secondColumnArray[y]);
                }
            }
        }
        String[] menuArray = new String[theSet.size()];
        theSet.toArray(menuArray);
        Arrays.sort(menuArray);
        return menuArray;
    }
    public static String[] populateArray(obj_id player, String col, String datatableName) throws InterruptedException
    {
        String[] errorZeroLengthArray = 
        {
            "The Array was empty, could be that you passed the wrong type"
        };
        String[] arrayList = dataTableGetStringColumn(datatableName, col);
        if (arrayList.length == 0)
        {
            sendSystemMessageTestingOnly(player, "Tool Not Functioning because the Datatable Rows equal ZERO!");
            return errorZeroLengthArray;
        }
        int listingLength = arrayList.length;
        HashSet theSet = new HashSet();
        for (int y = 0; y < listingLength; y++)
        {
            theSet.add(arrayList[y]);
        }
        String[] menuArray = new String[theSet.size()];
        theSet.toArray(menuArray);
        Arrays.sort(menuArray);
        return menuArray;
    }
    public static String[] populateArray(obj_id player, String col, String datatableName, String noNeed) throws InterruptedException
    {
        String[] errorZeroLengthArray = 
        {
            "The Array was empty, could be that you passed the wrong type"
        };
        String[] arrayList = dataTableGetStringColumn(datatableName, col);
        if (arrayList.length == 0)
        {
            sendSystemMessageTestingOnly(player, "Tool Not Functioning because the Datatable Rows equal ZERO!");
            return errorZeroLengthArray;
        }
        int listingLength = arrayList.length;
        HashSet theSet = new HashSet();
        for (int y = 0; y < listingLength; y++)
        {
            if (!arrayList[y].equals(noNeed))
            {
                theSet.add(arrayList[y]);
            }
        }
        String[] menuArray = new String[theSet.size()];
        theSet.toArray(menuArray);
        Arrays.sort(menuArray);
        return menuArray;
    }
    public static String[] populateArrayDoNotSort(obj_id player, String col, String datatableName) throws InterruptedException
    {
        String[] menu = dataTableGetStringColumn(datatableName, col);
        Vector theVectorData = new Vector();
        for (int i = 0; i < menu.length; i++)
        {
            if (!menu[i].equals(""))
            {
                theVectorData.addElement(menu[i]);
            }
        }
        String[] menuArray = new String[theVectorData.size()];
        theVectorData.toArray(menuArray);
        return menuArray;
    }
    public static void removeScriptVars(obj_id player, String scriptVar) throws InterruptedException
    {
        utils.removeScriptVarTree(player, "qatool");
        utils.removeScriptVarTree(player, scriptVar);
    }
    public static void removePlayer(obj_id player, String SCRIPTVAR, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(player, err);
        qa.removeScriptVars(player, SCRIPTVAR);
        utils.removeScriptVarTree(player, SCRIPTVAR);
    }
    public static void createInputBox(obj_id player, String prompt, String title, String myHandler, String scriptVar) throws InterruptedException
    {
        qa.closeOldWindow(player, scriptVar);
        int pid = sui.inputbox(player, player, prompt, sui.OK_CANCEL, title, sui.INPUT_NORMAL, null, myHandler, null);
        sui.showSUIPage(pid);
        setWindowPid(player, pid, scriptVar);
        utils.setScriptVar(player, scriptVar, player);
    }
    public static void createMsgBox(obj_id player, String prompt, String title, String myHandler, String scriptVar) throws InterruptedException
    {
        qa.closeOldWindow(player, scriptVar);
        int pid = sui.msgbox(player, player, prompt, sui.YES_NO, title, myHandler);
        sui.showSUIPage(pid);
        setWindowPid(player, pid, scriptVar);
        utils.setScriptVar(player, scriptVar, player);
    }
    public static void spawnStaticItemInInventory(obj_id self, String staticItemString, String altMessage) throws InterruptedException
    {
        String messageString = "";
        if (!altMessage.equals("none"))
        {
            messageString = altMessage;
        }
        else 
        {
            messageString = staticItemString;
        }
        obj_id inv = utils.getInventoryContainer(self);
        if (isIdValid(inv))
        {
            obj_id[] objContents = utils.getContents(inv, true);
            if (objContents.length >= 80)
            {
                sendSystemMessageTestingOnly(self, "Empty your inventory before trying to spawn more items");
            }
            else 
            {
                static_item.createNewItemFunction(staticItemString, inv);
                sendSystemMessageTestingOnly(self, messageString + " was placed in your inventory");
                CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned (" + staticItemString + ") using a QA Tool or command.");
            }
        }
    }
    public static obj_id spawnStaticItemInInventory(obj_id self, String staticItemString, String altMessage, boolean silent) throws InterruptedException
    {
        String messageString = "";
        if (!altMessage.equals("none"))
        {
            messageString = altMessage;
        }
        else 
        {
            messageString = staticItemString;
        }
        obj_id inv = utils.getInventoryContainer(self);
        if (isIdValid(inv))
        {
            obj_id[] objContents = utils.getContents(inv, true);
            if (objContents.length >= 80)
            {
                sendSystemMessageTestingOnly(self, "Empty your inventory before trying to spawn more items");
            }
            else 
            {
                obj_id staticItemId = static_item.createNewItemFunction(staticItemString, inv);
                if (!silent)
                {
                    sendSystemMessageTestingOnly(self, messageString + " was placed in your inventory");
                }
                CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned (" + staticItemString + ") using a QA Tool or command.");
                return staticItemId;
            }
        }
        return null;
    }
    public static obj_id findTarget(obj_id self) throws InterruptedException
    {
        obj_id intendedTarget = getIntendedTarget(self);
        obj_id lookAtTarget = getLookAtTarget(self);
        obj_id finalTarget = null;
        if (isIdValid(intendedTarget))
        {
            finalTarget = intendedTarget;
        }
        else if (isIdValid(lookAtTarget))
        {
            finalTarget = lookAtTarget;
        }
        else 
        {
            finalTarget = self;
        }
        if (!isIdNull(finalTarget))
        {
            return finalTarget;
        }
        return self;
    }
    public static String getObjectVariables(obj_id self, obj_id item) throws InterruptedException
    {
        String objvariable = "";
        String strObjVars = getPackedObjvars(item);
        String[] strSplit = split(strObjVars, '|');
        if (strSplit.length > 2)
        {
            for (int intI = 0; intI < strSplit.length - 2; intI++)
            {
                objvariable += "\t";
                objvariable += strSplit[intI];
                objvariable += "=";
                objvariable += strSplit[intI + 2];
                objvariable += "\n\r";
                intI = intI + 2;
            }
        }
        return objvariable;
    }
    public static String qaTargetDump(obj_id self, obj_id objTarget, boolean colorFlag) throws InterruptedException
    {
        String strTest = "";
        strTest += "OBJECT ID: \t\t\t";
        strTest += objTarget + "\r\n";
        strTest += "TEMPLATE NAME: \t\t";
        strTest += getTemplateName(objTarget) + "\r\n";
        if (!isMob(objTarget) && !isPlayer(objTarget))
        {
            if (getStaticItemName(objTarget) != null)
            {
                strTest += "STATIC ITEM NAME: \t";
                strTest += getStaticItemName(objTarget) + "\r\n";
            }
        }
        strTest += "SERVER CLUSTER: \t\t";
        strTest += getClusterName() + "\r\n";
        strTest += "LOCATION: \t\t\t";
        strTest += getLocation(objTarget) + "\r\n";
        if (isMob(objTarget) && !isPlayer(objTarget))
        {
            String codeString = getCreatureName(objTarget);
            String localizedString = localize(new string_id("mob/creature_names", codeString));
            if (localizedString == null)
            {
                localizedString = getEncodedName(objTarget);
            }
            strTest += "CREATURE NAME: \t\t";
            strTest += localizedString + "\r\n";
            strTest += "CREATURE CODE: \t\t";
            strTest += codeString + "\r\n";
        }
        if (isPlayer(objTarget))
        {
            String playerFullName = getName(objTarget);
            String playerCodeString = getPlayerName(objTarget);
            strTest += "PLAYER FULL NAME: \t";
            strTest += playerFullName + "\r\n";
            strTest += "INTERNAL NAME: \t\t";
            strTest += playerCodeString + "\r\n";
            strTest += "LOTS USED: \t\t\t" + getAccountNumLots(objTarget) + "\r\n\r\n";
        }
        if (isMob(objTarget) || isPlayer(objTarget))
        {
            int healthMax = getMaxAttrib(objTarget, HEALTH);
            int healthCurrent = getAttrib(objTarget, HEALTH);
            int actionMax = getMaxAttrib(objTarget, ACTION);
            int actionCurrent = getAttrib(objTarget, ACTION);
            int mindMax = getMaxAttrib(objTarget, MIND);
            int mindCurrent = getAttrib(objTarget, MIND);
            strTest += "STATS: \r\n\t\tHEALTH - \tMAX: \t";
            strTest += healthMax + "\t";
            strTest += "CURRENT: \t";
            strTest += healthCurrent + "\r\n";
            strTest += "\t\tACTION - \tMAX: \t";
            strTest += actionMax + " \t";
            strTest += "CURRENT: \t";
            strTest += actionCurrent + "\r\n";
            strTest += "\t\tMIND - \t\tMAX: \t";
            strTest += mindMax + "\t\t";
            strTest += "CURRENT: \t";
            strTest += mindCurrent + "\r\n";
            if (isPlayer(objTarget))
            {
                strTest += "\r\nGUILD:\r\n";
                int guildId = getGuildId(objTarget);
                if (guildId != 0)
                {
                    strTest += "Guild ID: " + guildId + "\r\n";
                    strTest += "Guild Name: " + guildGetName(guildId) + "\r\n";
                    strTest += "Guild Abbrev.: " + guildGetAbbrev(guildId) + "\r\n";
                    strTest += "Guild Leader Name & OID: " + getName(guildGetLeader(guildId)) + " " + guildGetLeader(guildId) + "\r\n";
                }
                else 
                {
                    strTest += "No guild\r\n";
                }
                strTest += "\r\nPROFESSION AND WORKING SKILL:\r\n";
                strTest += getSkillTemplate(objTarget) + "\r\n";
                strTest += getWorkingSkill(objTarget) + "\r\n";
                strTest += "\r\nBASE SKILL STATS:\r\n";
                strTest += "(Review expertise and skill modifier sections below to see skill stat modifier data)\r\n";
                String[] allSkillModsList = dataTableGetStringColumn("datatables/expertise/skill_mod_listing.iff", "skill_mod");
                int arrayLength = allSkillModsList.length;
                if (allSkillModsList != null)
                {
                    if (arrayLength > 0)
                    {
                        for (int x = 0; x < arrayLength; x++)
                        {
                            if (allSkillModsList[x].indexOf("_modified") > 0)
                            {
                                int underscoreIdx = allSkillModsList[x].indexOf("_");
                                String nonModifiedSkillname = allSkillModsList[x].substring(0, underscoreIdx);
                                strTest += localize(new string_id("stat_n", allSkillModsList[x])) + ": " + getSkillStatisticModifier(objTarget, nonModifiedSkillname) + "\r\n";
                            }
                        }
                    }
                }
                strTest += "\r\nEXPERTISE:\r\n";
                if (allSkillModsList != null)
                {
                    if (arrayLength > 0)
                    {
                        for (int x = 0; x < arrayLength; x++)
                        {
                            if (allSkillModsList[x].indexOf("expertise_") == 0)
                            {
                                if (getSkillStatisticModifier(objTarget, allSkillModsList[x]) > 0)
                                {
                                    strTest += localize(new string_id("stat_n", allSkillModsList[x])) + ": " + getSkillStatisticModifier(objTarget, allSkillModsList[x]) + "\r\n";
                                }
                            }
                        }
                    }
                }
                strTest += "\r\nSKILL MODIFIERS:\r\n";
                if (allSkillModsList != null)
                {
                    if (arrayLength > 0)
                    {
                        for (int x = 0; x < arrayLength; x++)
                        {
                            if (getEnhancedSkillStatisticModifierUncapped(objTarget, allSkillModsList[x]) > 0)
                            {
                                strTest += localize(new string_id("stat_n", allSkillModsList[x])) + " ( " + allSkillModsList[x] + " ): " + getEnhancedSkillStatisticModifierUncapped(objTarget, allSkillModsList[x]) + "\r\n";
                            }
                        }
                    }
                }
                strTest += "\r\nGCW INFO:\r\n";
                int intPlayerFaction = pvpGetAlignedFaction(objTarget);
                if (intPlayerFaction == (-615855020))
                {
                    strTest += "current faction: Imperial \r\n";
                }
                else if (intPlayerFaction == (370444368))
                {
                    strTest += "current faction: Rebel \r\n";
                }
                else 
                {
                    strTest += "current faction: Neutral \r\n";
                }
                strTest += "current GCW points: " + pvpGetCurrentGcwPoints(objTarget) + "\r\n";
                strTest += "current GCW rating: " + pvpGetCurrentGcwRating(objTarget) + "\r\n";
                strTest += "current GCW rank: " + pvpGetCurrentGcwRank(objTarget) + "\r\n";
                strTest += "current PvP kills: " + pvpGetCurrentPvpKills(objTarget) + "\r\n";
                strTest += "lifetime GCW points: " + pvpGetLifetimeGcwPoints(objTarget) + "\r\n";
                strTest += "max GCW imperial rating: " + pvpGetMaxGcwImperialRating(objTarget) + "\r\n";
                strTest += "max GCW imperial rank: " + pvpGetMaxGcwImperialRank(objTarget) + "\r\n";
                strTest += "max GCW rebel rating: " + pvpGetMaxGcwRebelRating(objTarget) + "\r\n";
                strTest += "max GCW rebel rank: " + pvpGetMaxGcwRebelRank(objTarget) + "\r\n";
                strTest += "lifetime PvP kills: " + pvpGetLifetimePvpKills(objTarget) + "\r\n";
                strTest += "next GCW rating calculation time: " + pvpGetNextGcwRatingCalcTime(objTarget) + "\r\n";
                strTest += "\r\nBUFFS/STATES/DoTs, etc.:\r\n";
                int[] buffs = _getAllBuffs(objTarget);
                if (buffs != null && buffs.length != 0)
                {
                    for (int i = 0; i < buffs.length; i++)
                    {
                        obj_id buffOwner = null;
                        String buffName = buff.getBuffNameFromCrc(buffs[i]);
                        float duration = buff.getDuration(buffName);
                        boolean debuff = buff.isDebuff(buffName);
                        boolean groupBuff = buff.isGroupBuff(buffName);
                        boolean ownedBuff = buff.isOwnedBuff(buffName);
                        if (ownedBuff)
                        {
                            buffOwner = buff.getBuffOwner(objTarget, buffName);
                        }
                        strTest += buffName + "\r\nDuration: " + duration + "\r\n";
                        if (debuff)
                        {
                            strTest += "Debuff: " + debuff + "\r\n";
                        }
                        if (groupBuff)
                        {
                            strTest += "Group buff: " + debuff + "\r\n";
                        }
                        if (ownedBuff)
                        {
                            strTest += "Owned buff: " + ownedBuff + "\r\n";
                            if (isIdValid(buffOwner))
                            {
                                strTest += "Buff Owner: " + getName(buffOwner) + "\r\n";
                            }
                            strTest += "Owner OID: " + buffOwner + "\r\n";
                        }
                    }
                }
                else 
                {
                    strTest += "No buffs" + "\r\n";
                }
            }
        }
        String readableScripts = dump.getReadableScripts(objTarget);
        if (!readableScripts.equals("<>") && !readableScripts.equals("") && readableScripts.length() > 5)
        {
            strTest += "\n\rATTACHED SCRIPTS:\r\n";
            strTest += readableScripts;
            strTest += "\n\r";
        }
        String objVars = dump.getReadableObjVars(objTarget);
        if (!objVars.equals("<>") && !objVars.equals("") && objVars.length() > 5)
        {
            strTest += "OBJVARS:\r\n";
            strTest += objVars;
            strTest += "\n\r";
        }
        String scriptVars = dump.getReadableScriptVars(objTarget);
        if (!scriptVars.equals("<>") && !scriptVars.equals("") && scriptVars.length() > 5)
        {
            strTest += "SCRIPTVARS:\r\n";
            strTest += scriptVars;
            strTest += "\n\r";
        }
        String localVars = dump.getReadableLocalVars(objTarget);
        if (!localVars.equals("<>") && !localVars.equals("") && localVars.length() > 5)
        {
            strTest += "LOCALVARS:\r\n";
            strTest += localVars;
            strTest += "\n\r";
            sendSystemMessageTestingOnly(self, "LOCALVARS:" + localVars);
        }
        if (isPlayer(objTarget))
        {
            String[] skillList = getSkillListingForPlayer(objTarget);
            if (skillList != null && skillList.length > 0)
            {
                strTest += "\r\nSKILLS:\r\n";
                for (int i = 0; i < skillList.length; i++)
                {
                    strTest += skillList[i] + "\r\n";
                }
            }
            else 
            {
                strTest += "\r\nSKILLS:\r\n";
                strTest += "none\r\n";
            }
            String[] allQuests = getAllQuests(self);
            if (allQuests != null && allQuests.length > 0)
            {
                strTest += "\r\nGROUND AND SPACE QUESTS:\r\n";
                strTest += "A = active, C = completed\r\n";
                for (int i = 0; i < allQuests.length; i++)
                {
                    strTest += allQuests[i] + "\r\n";
                }
            }
            else 
            {
                strTest += "\r\nGROUND AND SPACE QUESTS:\r\n";
                strTest += "none\r\n";
            }
            strTest += "\r\nWEARABLES:\r\n";
            if (KNOWN_CHARACTER_SLOTS != null)
            {
                for (int i = 0; i < KNOWN_CHARACTER_SLOTS[0].length; i++)
                {
                    obj_id item = getObjectInSlot(objTarget, KNOWN_CHARACTER_SLOTS[0][i]);
                    if (isIdValid(item))
                    {
                        strTest += KNOWN_CHARACTER_SLOTS[1][i] + "\r\n";
                        if (getStaticItemName(item) != null)
                        {
                            strTest += "\tStatic Item Name: " + getStaticItemName(item) + "\r\n";
                        }
                        if (getTemplateName(item) != null)
                        {
                            strTest += "\tTemplate: " + getTemplateName(item) + "\r\n";
                            strTest += "\tOID: " + item + "\r\n";
                            strTest += getObjectVariables(self, item);
                            if (jedi.isLightsaber(item))
                            {
                                obj_id saberInv = getObjectInSlot(item, "saber_inv");
                                obj_id[] saberContents = utils.getContents(saberInv, true);
                                if (saberContents != null && saberContents.length > 0)
                                {
                                    for (int s = 0; s < saberContents.length; s++)
                                    {
                                        if (isIdValid(saberContents[s]))
                                        {
                                            if (getTemplateName(saberContents[s]) != null)
                                            {
                                                strTest += "SABER SLOT " + (s + 1) + "\r\n";
                                                strTest += "\tTemplate: " + getTemplateName(saberContents[s]) + "\r\n";
                                                strTest += "\tOID: " + saberContents[s] + "\r\n";
                                                strTest += getObjectVariables(self, saberContents[s]);
                                                strTest += "\r\n";
                                            }
                                        }
                                    }
                                }
                                else 
                                {
                                    strTest += "nothing in saber.\r\n";
                                }
                            }
                        }
                    }
                }
            }
            strTest += "\r\nPLAYER DATAPAD:\r\n";
            obj_id targetDatapad = utils.getPlayerDatapad(objTarget);
            if (isIdValid(targetDatapad))
            {
                obj_id[] objContents = utils.getContents(targetDatapad, true);
                if (objContents != null && objContents.length > 0)
                {
                    for (int i = 0; i < objContents.length; i++)
                    {
                        if (isIdValid(objContents[i]))
                        {
                            if (getTemplateName(objContents[i]) != null)
                            {
                                strTest += "\tTemplate: " + getTemplateName(objContents[i]) + "\r\n";
                                strTest += "\tOID: " + objContents[i] + "\r\n";
                                strTest += getObjectVariables(self, objContents[i]);
                                strTest += "\r\n";
                            }
                        }
                    }
                }
                else 
                {
                    strTest += "no datpad items.\r\n";
                }
            }
            strTest += "\r\nENEMY FLAGS:\r\n";
            String enemyFlags = dump.getReadableEnemyFlags(objTarget);
            if (!enemyFlags.equals("<>") && !enemyFlags.equals("") && enemyFlags.length() > 5)
            {
                strTest += enemyFlags;
                strTest += "\n\r";
            }
            else 
            {
                strTest += "none\n\r";
            }
            strTest += "\r\nBOUNTY MISSIONS:\r\n";
            String playerBountyMissions = dump.getReadableBountyMissions(objTarget);
            if (!playerBountyMissions.equals("<>") && !playerBountyMissions.equals("") && playerBountyMissions.length() > 5)
            {
                strTest += playerBountyMissions;
                strTest += "\n\r";
            }
            else 
            {
                strTest += "none\n\r";
            }
            strTest += "\r\nBOUNTIES:\r\n";
            String playerBounties = dump.getReadableBounties(objTarget);
            if (!playerBounties.equals("<>") && !playerBounties.equals("") && playerBounties.length() > 5)
            {
                strTest += playerBounties;
                strTest += "\n\r";
            }
            else 
            {
                strTest += "none\n\r";
            }
            strTest += "\r\nBOUNTY HUNTERS WITH BOUNTIES ON ME:\r\n";
            String huntersOfPlayer = dump.getReadableBountyHunters(objTarget);
            if (!huntersOfPlayer.equals("<>") && !huntersOfPlayer.equals("") && huntersOfPlayer.length() > 5)
            {
                strTest += huntersOfPlayer;
                strTest += "\n\r";
            }
            else 
            {
                strTest += "none\n\r";
            }
            strTest += "\r\nFS QUESTS:\r\n";
            String fsQuestData = dump.getReadableFsQuests(objTarget);
            if (!fsQuestData.equals("<>") && !fsQuestData.equals("") && fsQuestData.length() > 5)
            {
                strTest += fsQuestData;
                strTest += "\n\r";
            }
            else 
            {
                strTest += "none\n\r";
            }
            strTest += "\r\nFS BRANCHES UNLOCKED:\r\n";
            String fsBranchesUnlocked = dump.getReadableFsBranchesUnlocked(objTarget);
            if (!fsBranchesUnlocked.equals("<>") && !fsBranchesUnlocked.equals("") && fsBranchesUnlocked.length() > 5)
            {
                strTest += fsBranchesUnlocked;
                strTest += "\n\r";
            }
            else 
            {
                strTest += "none\n\r";
            }
            strTest += "\r\nGAME SUBSCRIPTION FEATURES:\r\n";
            boolean boolHasCollectorEdition = features.hasCollectorEdition(objTarget);
            boolean boolhasSpaceExpansion = features.hasSpaceExpansion(objTarget);
            boolean boolhasSpaceExpansionPromotion = features.hasSpaceExpansionPromotion(objTarget);
            boolean boolhasJapaneseCollectorEdition = features.hasJapaneseCollectorEdition(objTarget);
            boolean boolhasEpisode3Expansion = features.hasEpisode3Expansion(objTarget);
            boolean boolhasEpisode3PreOrderDigitalDownload = features.hasEpisode3PreOrderDigitalDownload(objTarget);
            boolean boolhasEpisode3ExpansionRetail = features.hasEpisode3ExpansionRetail(objTarget);
            boolean boolhasTrialsOfObiwanExpansionRetail = features.hasTrialsOfObiwanExpansionRetail(objTarget);
            boolean boolhasTrialsOfObiwanExpansionPreorder = features.hasTrialsOfObiwanExpansionPreorder(objTarget);
            boolean boolhasTrialsOfObiwanExpansion = features.hasTrialsOfObiwanExpansion(objTarget);
            boolean boolhasMustafarExpansionRetail = features.hasMustafarExpansionRetail(objTarget);
            boolean boolhasFreeTrial = features.hasFreeTrial(objTarget);
            if (boolHasCollectorEdition)
            {
                strTest += "\t\t\tCollector's Edition\r\n";
            }
            if (boolhasSpaceExpansion)
            {
                strTest += "\t\t\tJTL SpaceExpansion\r\n";
            }
            if (boolhasSpaceExpansionPromotion)
            {
                strTest += "\t\t\tJTL Space Expansion Promotion\r\n";
            }
            if (boolhasJapaneseCollectorEdition)
            {
                strTest += "\t\t\tJapanese Collector's Edition:\r\n";
            }
            if (boolhasEpisode3Expansion)
            {
                strTest += "\t\t\tEpisode 3 Expansion\r\n";
            }
            if (boolhasEpisode3PreOrderDigitalDownload)
            {
                strTest += "\t\t\tEpisode 3 Expansion Preorder\r\n";
            }
            if (boolhasEpisode3ExpansionRetail)
            {
                strTest += "\t\t\tEpisode 3 Expansion Retail\r\n";
            }
            if (boolhasTrialsOfObiwanExpansionRetail)
            {
                strTest += "\t\t\tTrials of Obiwan Expansion Retail\r\n";
            }
            if (boolhasTrialsOfObiwanExpansionPreorder)
            {
                strTest += "\t\t\tTrials of Obiwan Expansion Preorder\r\n";
            }
            if (boolhasTrialsOfObiwanExpansion)
            {
                strTest += "\t\t\tTrials of Obiwan Expansion\r\n";
            }
            if (boolhasMustafarExpansionRetail)
            {
                strTest += "\t\t\tMustafar Expansion Retail\r\n";
            }
            if (boolhasFreeTrial)
            {
                strTest += "\t\t\tFree Trial\r\n";
            }
        }
        if (space_utils.isShip(objTarget))
        {
            if (space_utils.isPlayerControlledShip(objTarget))
            {
                obj_id objPilot = getPilotId(objTarget);
                if (isIdValid(objPilot))
                {
                    strTest += "PILOT BELOW HERE\r\n\r\n";
                    strTest += "OBJECT IS: ";
                    strTest += objPilot;
                    strTest += "\n\r";
                    strTest += "Template of object is ";
                    strTest += getTemplateName(objPilot);
                    strTest += "\n\r";
                    strTest += "Location of object is ";
                    strTest += getClusterName() + " " + getLocation(objPilot);
                    strTest += "\n\r";
                    strTest += "SCRIPTS\r\n";
                    strTest += dump.getReadableScripts(objPilot);
                    strTest += "\n\r";
                    strTest += "OBJVARS\r\n";
                    strTest += dump.getReadableObjVars(objPilot);
                    strTest += "\n\r";
                    strTest += "SCRIPTVARS\r\n";
                    strTest += dump.getReadableScriptVars(objPilot);
                    strTest += "\n\r";
                    strTest += "LOCALVARS\r\n";
                    strTest += dump.getReadableLocalVars(objPilot);
                    strTest += "\n\r";
                }
            }
            strTest += "SHIP STATS BELOW\r\n";
            int[] intSlots = space_crafting.getShipInstalledSlots(objTarget);
            for (int intI = 0; intI < intSlots.length; intI++)
            {
                strTest += dump.getShipComponentDebugString(objTarget, intSlots[intI]);
                strTest += "\n\r";
            }
        }
        String containmentTree = dump.getReadableContainmentTree(objTarget);
        if (!containmentTree.equals("<>") && !containmentTree.equals("") && containmentTree.length() > 5)
        {
            strTest += "\n\r";
            strTest += "TARGET CONTAINMENT:\r\n";
            strTest += containmentTree;
            strTest += "\n\r";
        }
        if (utils.hasScriptVar(objTarget, "spawnedBy"))
        {
            obj_id spawner = utils.getObjIdScriptVar(objTarget, "spawnedBy");
            if (isIdValid(spawner) && exists(spawner) && spawner.isLoaded() && spawner != objTarget)
            {
                strTest += "SPAWNER BELOW HERE\r\n";
                strTest += dump.getTargetInfoString(spawner);
            }
        }
        return strTest;
    }
    public static void createCustomUI(obj_id self, String combinedString) throws InterruptedException
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
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedOk, "%button0%", "exportFile");
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedCancel, "%button0%", "exportFile");
        showSUIPage(page);
        flushSUIPage(page);
    }
    public static void findOrCreateAndEquipQABag(obj_id self, obj_id testerInventoryId, boolean moveContents) throws InterruptedException
    {
        obj_id[] invAndEquip = getInventoryAndEquipment(self);
        if (invAndEquip != null && invAndEquip.length > 0)
        {
            obj_id firstBag = getObjectInSlot(self, "back");
            boolean hasBag = false;
            obj_id myBag = null;
            String firstBagTemp = getTemplateName(firstBag);
            if (firstBagTemp != null && !firstBagTemp.equals("object/tangible/test/qabag.iff"))
            {
                putInOverloaded(firstBag, testerInventoryId);
            }
            for (int i = 0; i < invAndEquip.length; i++)
            {
                String templateName = getTemplateName(invAndEquip[i]);
                if (templateName.equals("object/tangible/test/qabag.iff"))
                {
                    hasBag = true;
                    myBag = invAndEquip[i];
                }
            }
            if (hasBag == false)
            {
                myBag = createObjectInInventoryAllowOverload("object/tangible/test/qabag.iff", self);
            }
            equip(myBag, self, "back");
            obj_id[] invItems = getContents(testerInventoryId);
            Vector itemsVector = new Vector();
            if (moveContents == true && invItems.length >= 1)
            {
                for (int k = 0; k < invItems.length; k++)
                {
                    String templateName = getTemplateName(invItems[k]);
                    if (!(templateName.equals(FROG_STRING)) && !(templateName.equals(KASHYYYK_FROG_STRING)))
                    {
                        itemsVector.add(invItems[k]);
                    }
                }
                obj_id[] tempArray = new obj_id[itemsVector.size()];
                itemsVector.toArray(tempArray);
                moveObjects(tempArray, myBag);
            }
        }
        else 
        {
            obj_id myBag = createObjectInInventoryAllowOverload("object/tangible/test/qabag.iff", self);
            equip(myBag, self, "back");
        }
    }
    public static obj_id[] getAllValidWaypoints(obj_id tester) throws InterruptedException
    {
        HashSet waypointSet = new HashSet();
        obj_id[] datapadWaypoints = getWaypointsInDatapad(tester);
        if (datapadWaypoints != null)
        {
            for (int i = 0; i < datapadWaypoints.length; i++)
            {
                if (isIdValid(datapadWaypoints[i]))
                {
                    waypointSet.add(datapadWaypoints[i]);
                }
            }
        }
        obj_id[] waypointArray = new obj_id[waypointSet.size()];
        waypointSet.toArray(waypointArray);
        return waypointArray;
    }
    public static String[] getMenuList(obj_id tester, obj_id objList[], String objectType) throws InterruptedException
    {
        if (objList.length > -1)
        {
            String[] returnedList = new String[objList.length];
            for (int x = 0; x < objList.length; x++)
            {
                String stringData = "";
                if (objectType.equals("waypoint menu"))
                {
                    location reusedLocation = getWaypointLocation(objList[x]);
                    if (reusedLocation != null)
                    {
                        stringData = getWaypointName(objList[x]) + " " + reusedLocation.area + " " + reusedLocation.x + " " + reusedLocation.y + " " + reusedLocation.z;
                    }
                }
                if (!stringData.equals(""))
                {
                    returnedList[x] = stringData;
                }
                else 
                {
                    returnedList[x] = "There was an error in the object type condition";
                }
            }
            return returnedList;
        }
        sendSystemMessageTestingOnly(tester, "No menu options received.  Tool Failed. ");
        return null;
    }
    public static location[] getLocationList(obj_id tester, obj_id waypointList[]) throws InterruptedException
    {
        if (waypointList.length > -1)
        {
            location[] waypointLocations = new location[waypointList.length];
            for (int x = 0; x < waypointList.length; x++)
            {
                location reusedLocation = getWaypointLocation(waypointList[x]);
                if (reusedLocation != null)
                {
                    waypointLocations[x] = reusedLocation;
                }
            }
            return waypointLocations;
        }
        sendSystemMessageTestingOnly(tester, "No locations received.  Tool Failed. ");
        return null;
    }
    public static void templateObjectSpawner(obj_id player, String templateData) throws InterruptedException
    {
        obj_id inventoryContainer = utils.getInventoryContainer(player);
        if (getVolumeFree(inventoryContainer) <= 0)
        {
            sendSystemMessageTestingOnly(player, "Your Inventory is Full, please make room and try again.");
        }
        else 
        {
            createObject(templateData, inventoryContainer, "");
            sendSystemMessageTestingOnly(player, "Item Issued.");
            CustomerServiceLog("qaTool", "User: (" + player + ") " + getName(player) + " has spawned (" + templateData + ") using a QA Tool or command.");
        }
    }
    public static obj_id templateObjectSpawner(obj_id player, String templateData, boolean silent) throws InterruptedException
    {
        obj_id inventoryContainer = utils.getInventoryContainer(player);
        if (getVolumeFree(inventoryContainer) <= 0)
        {
            sendSystemMessageTestingOnly(player, "Your Inventory is Full, please make room and try again.");
        }
        else 
        {
            obj_id returnedId = createObject(templateData, inventoryContainer, "");
            if (!silent)
            {
                sendSystemMessageTestingOnly(player, "Item Issued.");
            }
            CustomerServiceLog("qaTool", "User: (" + player + ") " + getName(player) + " has spawned (" + templateData + ") using a QA Tool or command.");
            return returnedId;
        }
        return null;
    }
    public static void revokeAllSkills(obj_id player) throws InterruptedException
    {
        String[] skillList = getSkillListingForPlayer(player);
        int attempts = skillList.length;
        if ((skillList != null) && (skillList.length != 0))
        {
            while (skillList.length > 0 && attempts > 0)
            {
                for (int i = 0; i < skillList.length; i++)
                {
                    String skillName = skillList[i];
                    if (!skillName.startsWith("species_") && !skillName.startsWith("social_language_") && !skillName.startsWith("utility_") && !skillName.startsWith("common_") && !skillName.startsWith("demo_") && !skillName.startsWith("force_title_") && !skillName.startsWith("force_sensitive_") && !skillName.startsWith("combat_melee_basic") && !skillName.startsWith("pilot_") && !skillName.startsWith("combat_ranged_weapon_basic"))
                    {
                        skill.revokeSkillSilent(player, skillName);
                    }
                }
                skillList = getSkillListingForPlayer(player);
                --attempts;
            }
        }
        int currentCombatXp = getExperiencePoints(player, "combat_general");
        grantExperiencePoints(player, "combat_general", -currentCombatXp);
        skill.recalcPlayerPools(player, true);
        setSkillTemplate(player, "");
        setWorkingSkill(player, "");
        CustomerServiceLog("qaTool", "User: (" + player + ") " + getName(player) + " has had their entire profession removed/revoked by using a QA Tool or command.");
        sendSystemMessageTestingOnly(player, "All Professions removed from character.");
    }
    public static void revokePilotingSkills(obj_id player) throws InterruptedException
    {
        if (hasSkill(player, "pilot_rebel_navy_novice") || hasSkill(player, "pilot_imperial_navy_novice") || hasSkill(player, "pilot_neutral_novice"))
        {
            String pilotFaction = "";
            if (!utils.hasScriptVar(player, "revokePilotSkill"))
            {
                utils.setScriptVar(player, "revokePilotSkill", 1);
            }
            if (hasSkill(player, "pilot_rebel_navy_novice"))
            {
                pilotFaction = "rebel_navy";
            }
            else if (hasSkill(player, "pilot_imperial_navy_novice"))
            {
                pilotFaction = "imperial_navy";
            }
            else if (hasSkill(player, "pilot_neutral_novice"))
            {
                pilotFaction = "neutral";
            }
            else 
            {
                pilotFaction = "";
            }
            if (!pilotFaction.equals(""))
            {
                for (int i = 0; i < REVOKE_SPACE_SKILLS.length; i++)
                {
                    skill.revokeSkill(player, "pilot_" + pilotFaction + REVOKE_SPACE_SKILLS[i]);
                }
                utils.removeScriptVar(player, "revokePilotSkill");
            }
        }
    }
    public static boolean revokeAndGrantPilot(obj_id self, String factionType) throws InterruptedException
    {
        if (toLower(factionType) == "rebel" || factionType.equals("Rebel Ships"))
        {
            revokePilotingSkills(self);
            grantPilotingSkills(self, "rebel_navy");
            sendSystemMessageTestingOnly(self, "Rebel Pilot Granted.");
            CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has attained Master Rebel Pilot by using a QA Tool or command.");
            return true;
        }
        else if (factionType.equals("Imperial Ships") || toLower(factionType) == "imperial")
        {
            revokePilotingSkills(self);
            grantPilotingSkills(self, "imperial_navy");
            sendSystemMessageTestingOnly(self, "Imperial Pilot Granted.");
            CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has attained Master Imperial Pilot by using a QA Tool or command.");
            return true;
        }
        else if (factionType.equals("Neutral/Freelancer Ships") || toLower(factionType) == "neutral")
        {
            revokePilotingSkills(self);
            grantPilotingSkills(self, "neutral");
            sendSystemMessageTestingOnly(self, "Neutral Pilot Granted.");
            CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has attained Master Neutral Pilot by using a QA Tool or command.");
            return true;
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "The revokeAndGrantPilot function was not used properly. Exiting.");
        }
        return false;
    }
    public static void grantPilotingSkills(obj_id self, String factionToggle) throws InterruptedException
    {
        for (int i = 0; i < GRANT_SPACE_SKILLS.length; i++)
        {
            grantSkill(self, "pilot_" + factionToggle + GRANT_SPACE_SKILLS[i]);
        }
    }
    public static String getClientBuffName(obj_id self, String buffCommand) throws InterruptedException
    {
        if (!buffCommand.equals(""))
        {
            String buffString = "null";
            buffString = utils.packStringId(new string_id("ui_buff", buffCommand));
            if (!buffCommand.equals(""))
            {
                return buffString;
            }
            else 
            {
                return buffCommand;
            }
        }
        return "null";
    }
    public static void applyBuffOption(obj_id self, String buffArg, String buffName) throws InterruptedException
    {
        if (!buffName.equals(""))
        {
            buff.applyBuff(self, self, buffArg);
            sendSystemMessageTestingOnly(self, "Applying Buff");
            sendSystemMessageTestingOnly(self, buffName);
            CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has used the buff (" + buffName + ") attained from a QA Tool or command.");
        }
    }
    public static void stopCreatureCombat(obj_id self, obj_id tester) throws InterruptedException
    {
        stopCombat(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        removeTriggerVolume(ai_lib.ALERT_VOLUME_NAME);
        removeTriggerVolume(ai_lib.AGGRO_VOLUME_NAME);
        utils.removeScriptVarTree(self, "ai.combat");
        detachScript(self, "ai.creature_combat");
    }
    public static void followTester(obj_id self, obj_id tester) throws InterruptedException
    {
        float testerSpeed = getRunSpeed(tester);
        ai_lib.aiFollow(self, tester);
        setMovementRun(self);
        setBaseRunSpeed(self, (testerSpeed * 2));
        sendSystemMessageTestingOnly(tester, "Follow command given to mobile.");
    }
    public static void damageMobTool(obj_id self) throws InterruptedException
    {
        obj_id finalTarget = findTarget(self);
        if (!isIdNull(finalTarget))
        {
            if (finalTarget == self)
            {
                sendSystemMessageTestingOnly(self, "Damaging self.");
            }
            int targetCurrentHealth = getAttrib(finalTarget, HEALTH);
            utils.setScriptVar(self, DAMAGE_SCRIPTVAR + ".lookAtTarget", "" + finalTarget);
            int pid = sui.transfer(self, self, DAMAGE_TOOL_PROMPT, DAMAGE_TOOL_TITLE, "Available", targetCurrentHealth, "Amount", 0, "doTheDamage");
            sui.showSUIPage(pid);
            utils.setScriptVar(self, DAMAGE_PID_SCRIPTVAR, pid);
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "You must have a valid mob or player targeted.  If the mob is moving, you may want to type /qatool damage while targeting the mob/player.");
        }
    }
    public static void healMobTool(obj_id self) throws InterruptedException
    {
        obj_id finalTarget = findTarget(self);
        if (!isIdNull(finalTarget))
        {
            if (finalTarget == self)
            {
                sendSystemMessageTestingOnly(self, "Healing self.");
            }
            int targetCurrentHealth = getAttrib(finalTarget, HEALTH);
            int targetMaxHealth = getMaxHealth(finalTarget);
            int healthDifference = targetMaxHealth - targetCurrentHealth;
            if (healthDifference > 0)
            {
                utils.setScriptVar(self, HEAL_SCRIPTVAR + ".lookAtTarget", "" + finalTarget);
                int pid = sui.transfer(self, self, HEAL_TOOL_PROMPT, HEAL_TOOL_TITLE, "Available", healthDifference, "Amount", 0, "healDamage");
                sui.showSUIPage(pid);
                utils.setScriptVar(self, HEAL_PID_SCRIPTVAR, pid);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "The target health pool is maximum and cannot be healed.");
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "You must have a valid mob or player targeted.  If the mob is moving, you may want to type /qatool heal while targeting the mob/player.");
        }
    }
    public static boolean checkGodLevel(obj_id tester) throws InterruptedException
    {
        if (getGodLevel(tester) < 10)
        {
            return false;
        }
        return true;
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
                for (int i = 0; i < allActive.length; i++)
                {
                    allQuestStringsCombined.add(allActive[i]);
                }
            }
            if (allComplete != null)
            {
                for (int i = 0; i < allComplete.length; i++)
                {
                    allQuestStringsCombined.add(allComplete[i]);
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
                        allQuestStringsFound.add("(A) " + groundCodeAndTitle);
                    }
                    else if (activeQuestString.indexOf("spacequest/") == 0)
                    {
                        String spaceCodeAndTitle = getSpaceQuestStringAndTitle(player, activeQuestString);
                        allQuestStringsFound.add("(A) " + spaceCodeAndTitle);
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
                        allQuestStringsFound.add("(C) " + groundCodeAndTitle);
                    }
                    else if (completedQuestString.indexOf("spacequest/") == 0)
                    {
                        String spaceCodeAndTitle = getSpaceQuestStringAndTitle(player, completedQuestString);
                        allQuestStringsFound.add("(C) " + spaceCodeAndTitle);
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
    public static String getGroundQuestStringAndTitle(obj_id player, String questString) throws InterruptedException
    {
        String localizeThis = questString.substring(6);
        String questStringLoc = "quest/ground/" + localizeThis;
        String questTitle = localize(new string_id(questStringLoc, "journal_entry_title"));
        return questString + " - " + questTitle;
    }
    public static String getSpaceQuestStringAndTitle(obj_id self, String questString) throws InterruptedException
    {
        String questTitle = localize(new string_id(questString, "title"));
        return questString + " - " + questTitle;
    }
    public static void grantGroundQuest(obj_id self, String questString) throws InterruptedException
    {
        groundquests.requestGrantQuest(self, questString, true);
    }
    public static boolean grantSpaceQuest(obj_id self, String questType, String questName) throws InterruptedException
    {
        boolean questSuccess = space_quest.grantQuest(self, questType, questName);
        return questSuccess;
    }
    public static String getSpaceQuestType(obj_id self, String questString) throws InterruptedException
    {
        if (questString.indexOf("spacequest/") == 0)
        {
            String questType = questString.substring(11);
            int slashIdx = questType.indexOf("/");
            questType = questType.substring(0, slashIdx);
            boolean checkType = checkQuestType(self, questType);
            if (checkType)
            {
                return questType;
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "The quest type was typed incorrectly");
            }
        }
        return "Error";
    }
    public static String getSpaceQuestName(obj_id self, String questString) throws InterruptedException
    {
        if (questString.indexOf("spacequest/") == 0)
        {
            String questName = questString.substring(11);
            int slashIdx = questName.indexOf("/");
            questName = questName.substring(slashIdx + 1);
            return questName;
        }
        return "Error";
    }
    public static boolean checkQuestType(obj_id self, String questType) throws InterruptedException
    {
        if (!questType.equals(""))
        {
            for (int i = 0; i < SPACE_QUEST_TYPES.length; i++)
            {
                if (questType.equals(SPACE_QUEST_TYPES[i]))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean completeActiveQuest(obj_id self, String questString) throws InterruptedException
    {
        if (questString.indexOf("spacequest/") == 0)
        {
            if (space_quest.hasQuest(self))
            {
                obj_id datapad = utils.getPlayerDatapad(self);
                if (isIdValid(datapad))
                {
                    obj_id[] dpobjs = getContents(datapad);
                    for (int i = 0; i < dpobjs.length; i++)
                    {
                        if (hasObjVar(dpobjs[i], space_quest.QUEST_TYPE) && hasObjVar(dpobjs[i], space_quest.QUEST_NAME))
                        {
                            space_quest.setQuestWon(self, dpobjs[i]);
                        }
                    }
                    return true;
                }
            }
        }
        else 
        {
            int questid = questGetQuestId(questString);
            if ((questid != 0) && questIsQuestActive(questid, self))
            {
                int questCompleted = questCompleteQuest(questid, self);
                sendSystemMessageTestingOnly(self, "Quest completion function completed.");
                return true;
            }
        }
        return false;
    }
    public static boolean clearQuest(obj_id self, String questString) throws InterruptedException
    {
        if (questString.indexOf("spacequest/") == 0)
        {
            if (space_quest.hasQuest(self))
            {
                obj_id datapad = utils.getPlayerDatapad(self);
                if (isIdValid(datapad))
                {
                    obj_id[] dpobjs = getContents(datapad);
                    for (int i = 0; i < dpobjs.length; i++)
                    {
                        if (hasObjVar(dpobjs[i], space_quest.QUEST_TYPE) && hasObjVar(dpobjs[i], space_quest.QUEST_NAME))
                        {
                            space_quest.setQuestAborted(self, dpobjs[i]);
                            String questType = "";
                            String questName = "";
                            questType = getSpaceQuestType(self, questString);
                            if (!questType.equals("Error") && !questType.equals(""))
                            {
                                questName = getSpaceQuestName(self, questString);
                            }
                            if (!questName.equals("Error") && !questName.equals(""))
                            {
                                String spaceObjVar = "space_quest." + questType + "." + questName;
                                blowOutObjVars(self, spaceObjVar);
                                sendSystemMessageTestingOnly(self, "Space quest ObjVar removed.");
                            }
                        }
                    }
                }
            }
        }
        int questid = questGetQuestId(questString);
        if (questid != 0)
        {
            questClearQuest(questid, self);
            return true;
        }
        return false;
    }
    public static boolean evalSpaceQuestThenGrant(obj_id self, String spaceString) throws InterruptedException
    {
        String questType = "";
        String questName = "";
        if (!spaceString.equals(""))
        {
            questType = getSpaceQuestType(self, spaceString);
            if (!questType.equals("Error") && !questType.equals(""))
            {
                questName = getSpaceQuestName(self, spaceString);
                if (!questType.equals("Error") && !questType.equals(""))
                {
                    boolean grantComplete = grantSpaceQuest(self, questType, questName);
                    if (grantComplete)
                    {
                        return true;
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "The was a problem granting: " + spaceString);
                        return false;
                    }
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Unknown quest string: " + spaceString);
            }
        }
        return false;
    }
    public static boolean blowOutObjVars(obj_id self, String objVar) throws InterruptedException
    {
        if (objVar.equals("space"))
        {
            removeObjVar(self, "space_quest");
        }
        if (objVar.equals("all"))
        {
            removeObjVar(self, "space_quest");
            removeObjVar(self, "quest");
        }
        else 
        {
            removeObjVar(self, objVar);
        }
        return true;
    }
    public static boolean grantPilotSkill(obj_id self, String skillStringData) throws InterruptedException
    {
        String[] allSkillStrings = split(skillStringData, ';');
        for (int i = 0; i < allSkillStrings.length; i++)
        {
            skill.noisyGrantSkill(self, allSkillStrings[i]);
            messageTo(self, "delay", null, 1, false);
        }
        return true;
    }
    public static boolean grantOrClearSpaceQuest(obj_id self, String questData, String fateSwitch) throws InterruptedException
    {
        String[] allData = split(questData, ';');
        for (int i = 0; i < allData.length; i++)
        {
            if (fateSwitch.equals("clear"))
            {
                clearQuest(self, allData[i]);
            }
            else if (fateSwitch.equals("grant"))
            {
                evalSpaceQuestThenGrant(self, allData[i]);
                messageTo(self, "delay", null, 4, false);
                qa.completeActiveQuest(self, allData[i]);
                String questType = getSpaceQuestType(self, questData);
                if (!questType.equals("Error") && !questType.equals(""))
                {
                    String questName = getSpaceQuestName(self, questData);
                    if (!questType.equals("Error") && !questType.equals(""))
                    {
                        if (!space_quest.hasWonQuest(self, questType, questName))
                        {
                            evalSpaceQuestThenGrant(self, allData[i]);
                            messageTo(self, "delay", null, 4, false);
                            qa.completeActiveQuest(self, allData[i]);
                        }
                    }
                }
            }
        }
        return true;
    }
    public static boolean createAQaWaypointInDataPad(obj_id self, location waypointData, String waypointName) throws InterruptedException
    {
        obj_id wayp = createWaypointInDatapad(self, waypointData);
        String playerArea = getLocation(self).area;
        if (playerArea != null && playerArea.equals(waypointData.area))
        {
            setWaypointActive(wayp, true);
        }
        setWaypointName(wayp, waypointName);
        return true;
    }
    public static void forceEggSpawn(obj_id self) throws InterruptedException
    {
        obj_id lookAtTarget = qa.findTarget(self);
        if (hasScript(lookAtTarget, "systems.spawning.spawner_area"))
        {
            int intGoodLocationSpawner = getIntObjVar(lookAtTarget, "intGoodLocationSpawner");
            String strSpawnType = getStringObjVar(lookAtTarget, "strSpawns");
            float fltSize = 8.0f;
            float fltRadius = getFloatObjVar(lookAtTarget, "fltRadius");
            location locTest = spawning.getRandomLocationInCircle(getLocation(lookAtTarget), fltRadius);
            String strSpawn = strSpawnType;
            String strFileName = "datatables/spawning/ground_spawning/types/" + strSpawnType + ".iff";
            if (dataTableOpen(strFileName))
            {
                String[] strSpawns = dataTableGetStringColumnNoDefaults(strFileName, "strItem");
                float[] fltSizes = dataTableGetFloatColumn(strFileName, "fltSize");
                if (strSpawns == null || strSpawns.length == 0)
                {
                    setName(self, "Mangled spawner. strFileName is " + strFileName + " I couldnt find any spawns in that file.");
                }
                int intRoll = rand(0, strSpawns.length - 1);
                fltSize = fltSizes[intRoll];
                strSpawn = strSpawns[intRoll];
            }
            fltSize = getClosestSize(fltSize);
            if (intGoodLocationSpawner > 0)
            {
                requestLocation(lookAtTarget, strSpawn, locTest, rand(100, 200), fltSize, true, true);
            }
            else 
            {
                createSpawnerMobNow(self, strSpawn, null, locTest, fltRadius, lookAtTarget);
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Not recognized as a spawner.  Tool failed.");
        }
    }
    public static void createSpawnerMobNow(obj_id self, String strId, obj_id objLocationObject, location locLocation, float fltRadius, obj_id lookAtTarget) throws InterruptedException
    {
        float spawnerYaw = getYaw(lookAtTarget);
        int intIndex = strId.indexOf(".iff");
        float fltRespawnTime = 0.0f;
        if (intIndex > -1)
        {
            obj_id objTemplate = createObject(strId, locLocation);
            if (!isIdValid(objTemplate))
            {
                return;
            }
            spawning.incrementSpawnCount(lookAtTarget);
            spawning.addToSpawnDebugList(lookAtTarget, objTemplate);
            setObjVar(objTemplate, "objParent", lookAtTarget);
            setObjVar(objTemplate, "fltRespawnTime", fltRespawnTime);
            attachScript(objTemplate, "systems.spawning.spawned_tracker");
            setYaw(objTemplate, spawnerYaw);
        }
        else 
        {
            obj_id objMob = create.object(strId, locLocation);
            if (!isIdValid(objMob))
            {
                setName(lookAtTarget, "BAD MOB OF TYPE " + strId);
                return;
            }
            int intBehavior = getIntObjVar(lookAtTarget, "intDefaultBehavior");
            ai_lib.setDefaultCalmBehavior(objMob, intBehavior);
            spawning.incrementSpawnCount(lookAtTarget);
            spawning.addToSpawnDebugList(lookAtTarget, objMob);
            setObjVar(objMob, "objParent", lookAtTarget);
            setObjVar(objMob, "fltRespawnTime", fltRespawnTime);
            attachScript(objMob, "systems.spawning.spawned_tracker");
            setYaw(objMob, spawnerYaw);
        }
        if (!spawning.checkSpawnCount(lookAtTarget))
        {
            return;
        }
        messageTo(lookAtTarget, "doSpawnEvent", null, fltRespawnTime, false);
        return;
    }
    public static float getClosestSize(float fltOriginalSize) throws InterruptedException
    {
        if (fltOriginalSize <= 4.0f)
        {
            return 4.0f;
        }
        if (fltOriginalSize <= 8.0f)
        {
            return 8.0f;
        }
        if (fltOriginalSize <= 12.0f)
        {
            return 12.0f;
        }
        if (fltOriginalSize <= 16.0f)
        {
            return 16.0f;
        }
        if (fltOriginalSize <= 32.0f)
        {
            return 32.0f;
        }
        else if (fltOriginalSize <= 48.0f)
        {
            return 48.0f;
        }
        else if (fltOriginalSize <= 64.0f)
        {
            return 64.0f;
        }
        else if (fltOriginalSize <= 80.0f)
        {
            return 80f;
        }
        else if (fltOriginalSize <= 96.0f)
        {
            return 96f;
        }
        return 32f;
    }
}
