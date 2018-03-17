package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.HashSet;
import script.library.features;
import script.library.fs_quests;
import script.library.gm;
import script.library.jedi;
import script.library.quests;
import script.library.space_quest;
import script.library.static_item;

public class dump extends script.base_script
{
    public dump()
    {
    }
    public static final String DATATABLE_FS_QUESTS = "datatables/player/quests.iff";
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
    public static String getTargetInfoStringByLong(long lTarget) throws InterruptedException
    {
        obj_id target = obj_id.getObjId(lTarget);
        if (!isIdValid(target))
        {
            return "";
        }
        return getTargetInfoString(target);
    }
    public static String getTargetInfoString(obj_id objTarget) throws InterruptedException
    {
        String strTest = "Object: " + objTarget + "\r\n";
        if (isMob(objTarget) && !isPlayer(objTarget))
        {
            String creatureName = getCreatureName(objTarget);
            if (creatureName != null && creatureName.length() > 0)
            {
                strTest += "Creature Name: " + creatureName + "\r\n";
            }
        }
        if (static_item.isStaticItem(objTarget))
        {
            strTest += "Static Item: " + getStaticItemName(objTarget) + " (ver " + getStaticItemVersion(objTarget) + ")\r\n";
        }
        strTest += "Template: " + getTemplateName(objTarget) + "\r\n";
        strTest += "Location: " + getClusterName() + " " + getLocation(objTarget) + "\r\n";
        strTest += "---------------------CONTAINMENT-------------------------\r\n";
        strTest += getReadableContainmentTree(objTarget);
        strTest += "---------------------SCRIPTS-----------------------------\r\n";
        strTest += getReadableScripts(objTarget);
        strTest += "---------------------OBJVARS-----------------------------\r\n";
        strTest += getReadableObjVars(objTarget);
        strTest += "---------------------SCRIPTVARS--------------------------\r\n";
        strTest += getReadableScriptVars(objTarget);
        strTest += "\r\n";
        strTest += "---------------------LOCALVARS---------------------------\r\n";
        strTest += getReadableLocalVars(objTarget);
        strTest += "\r\n";
        strTest += "---------------------ENEMY FLAGS-------------------------\r\n";
        strTest += getReadableEnemyFlags(objTarget);
        if (isPlayer(objTarget))
        {
            strTest += "---------------------BOUNTY MISSIONS---------------------\r\n";
            strTest += getReadableBountyMissions(objTarget);
            strTest += "---------------------BOUNTIES----------------------------\r\n";
            strTest += getReadableBounties(objTarget);
            strTest += "---------------------BOUNTY HUNTERS WITH BOUNTIES ON ME--\r\n";
            strTest += getReadableBountyHunters(objTarget);
            strTest += "---------------------FS QUESTS---------------------------\r\n";
            strTest += getReadableFsQuests(objTarget);
            strTest += "---------------------FS BRANCHES UNLOCKED----------------\r\n";
            strTest += getReadableFsBranchesUnlocked(objTarget);
            strTest += "---------------------SPACE QUESTS------------------------\r\n";
            strTest += getReadableSpaceQuests(objTarget);
        }
        if (space_utils.isShip(objTarget))
        {
            if (space_utils.isPlayerControlledShip(objTarget))
            {
                obj_id objPilot = getPilotId(objTarget);
                if (isIdValid(objPilot))
                {
                    strTest += "=====================PILOT BELOW HERE====================\r\n\r\n";
                    strTest += "Object is " + objPilot + "\r\n";
                    strTest += "Template of object is " + getTemplateName(objPilot) + "\r\n";
                    strTest += "Location of object is " + getClusterName() + " " + getLocation(objPilot) + "\r\n";
                    strTest += "---------------------SCRIPTS-----------------------------\r\n";
                    strTest += getReadableScripts(objPilot);
                    strTest += "---------------------OBJVARS-----------------------------\r\n";
                    strTest += getReadableObjVars(objPilot);
                    strTest += "---------------------SCRIPTVARS--------------------------\r\n";
                    strTest += getReadableScriptVars(objPilot);
                    strTest += "\r\n";
                    strTest += "---------------------LOCALVARS---------------------------\r\n";
                    strTest += getReadableLocalVars(objPilot);
                    strTest += "\r\n";
                }
            }
            strTest += "=====================SHIP STATS BELOW====================\r\n";
            int[] intSlots = space_crafting.getShipInstalledSlots(objTarget);
            for (int intI = 0; intI < intSlots.length; intI++)
            {
                strTest += getShipComponentDebugString(objTarget, intSlots[intI]) + "\r\n";
            }
        }
        if (utils.hasScriptVar(objTarget, "spawnedBy"))
        {
            obj_id spawner = utils.getObjIdScriptVar(objTarget, "spawnedBy");
            if (isIdValid(spawner) && exists(spawner) && spawner.isLoaded() && spawner != objTarget)
            {
                strTest += "=====================SPAWNER BELOW HERE==================\r\n";
                strTest += getTargetInfoString(spawner);
            }
        }
        return strTest;
    }
    public static String getReadableObjVars(obj_id objTarget) throws InterruptedException
    {
        String strObjVars = getPackedObjvars(objTarget);
        String[] strSplit = split(strObjVars, '|');
        String strTest = "";
        if (strSplit.length > 2)
        {
            for (int intI = 0; intI < strSplit.length - 2; intI++)
            {
                strTest = strTest + strSplit[intI];
                strTest = strTest + "=";
                strTest = strTest + strSplit[intI + 2];
                strTest = strTest + "\r\n";
                intI = intI + 2;
            }
        }
        return strTest;
    }
    public static String getReadableScripts(obj_id objTarget) throws InterruptedException
    {
        String[] strScriptArray = getScriptList(objTarget);
        String strScripts = "";
        for (int intJ = 0; intJ < strScriptArray.length; intJ++)
        {
            String script = strScriptArray[intJ];
            if (script.indexOf("script.") > -1)
            {
                script = script.substring(7);
                strScripts += script + "\r\n";
            }
        }
        return strScripts;
    }
    public static String getReadableScriptVars(obj_id objTarget) throws InterruptedException
    {
        deltadictionary dctScriptVars = objTarget.getScriptVars();
        return dctScriptVars.toString();
    }
    public static String getReadableLocalVars(obj_id objTarget) throws InterruptedException
    {
        dictionary dd = objTarget.getScriptDictionary();
        return dd.toString();
    }
    public static String getReadableContainmentTree(obj_id objTarget) throws InterruptedException
    {
        final int MAX_DEPTH = 32;
        String readableContainmentTree = "";
        obj_id prevObject = objTarget;
        obj_id container = getContainedBy(objTarget);
        int depth = 0;
        while (isIdValid(container) && container != prevObject && depth < MAX_DEPTH)
        {
            readableContainmentTree += getReadableContainer(container);
            prevObject = container;
            container = getContainedBy(container);
            ++depth;
        }
        obj_id topmost = getTopMostContainer(objTarget);
        if (isIdValid(topmost) && topmost != container && topmost != prevObject)
        {
            readableContainmentTree += getReadableContainer(topmost);
        }
        readableContainmentTree += "<world>\n";
        return readableContainmentTree;
    }
    public static String getReadableContainer(obj_id container) throws InterruptedException
    {
        int type = getContainerType(container);
        String template = getTemplateName(container);
        location loc = getLocation(container);
        if (template.equals("object/cell/cell.iff"))
        {
            String cellName = getCellName(container);
            if (cellName != null)
            {
                template += " name=" + cellName;
            }
        }
        return container + " type=" + type + " " + template + "\n\t" + loc + "\n";
    }
    public static String getReadableEnemyFlags(obj_id objTarget) throws InterruptedException
    {
        String readableEnemyFlags = "";
        String[] enemies = pvpGetEnemyFlags(objTarget);
        if (enemies != null)
        {
            for (int i = 0; i < enemies.length; ++i)
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(enemies[i]);
                String sTarget = st.nextToken();
                String sTargetName = getPlayerName(utils.stringToObjId(sTarget));
                String sTefFac = st.nextToken();
                int iTefFac = utils.stringToInt(sTefFac);
                String sTefFacName = "<unknown>";
                if (iTefFac == (-526735576))
                {
                    sTefFacName = "battlefield";
                }
                else if (iTefFac == (1183528962))
                {
                    sTefFacName = "duel";
                }
                else if (iTefFac == (-429740311))
                {
                    sTefFacName = "bountyduel";
                }
                else if (iTefFac == (221551254))
                {
                    sTefFacName = "nonaggressive";
                }
                else if (iTefFac == (-160237431))
                {
                    sTefFacName = "unattackable";
                }
                else if (iTefFac == (84709322))
                {
                    sTefFacName = "bountytarget";
                }
                else if (iTefFac == (-1526926610))
                {
                    sTefFacName = "guildwarcooldownperiod";
                }
                else if (iTefFac == (-615855020))
                {
                    sTefFacName = "imperial";
                }
                else if (iTefFac == (370444368))
                {
                    sTefFacName = "rebel";
                }
                else if (iTefFac == (-377582139))
                {
                    sTefFacName = "bubblecombat";
                }
                String sExpiration = st.nextToken();
                readableEnemyFlags += sTarget;
                readableEnemyFlags += " (";
                readableEnemyFlags += sTargetName;
                readableEnemyFlags += "), ";
                readableEnemyFlags += sTefFac;
                readableEnemyFlags += " (";
                readableEnemyFlags += sTefFacName;
                readableEnemyFlags += "), ";
                readableEnemyFlags += sExpiration;
                readableEnemyFlags += "ms";
                readableEnemyFlags += "\r\n";
            }
        }
        if (readableEnemyFlags.equals(""))
        {
            readableEnemyFlags = "<>\r\n";
        }
        return readableEnemyFlags;
    }
    public static String getReadableBountyMissions(obj_id objTarget) throws InterruptedException
    {
        String readableBountyMissions = "";
        obj_id[] objMissions = getMissionObjects(objTarget);
        if (objMissions != null)
        {
            for (int i = 0; i < objMissions.length; ++i)
            {
                String missionType = getMissionType(objMissions[i]);
                if (missionType.equals("bounty"))
                {
                    readableBountyMissions += "mission id: ";
                    readableBountyMissions += objMissions[i];
                    readableBountyMissions += ", target id: ";
                    if (hasObjVar(objMissions[i], "objTarget"))
                    {
                        readableBountyMissions += getObjIdObjVar(objMissions[i], "objTarget");
                    }
                    else 
                    {
                        readableBountyMissions += "NPC";
                    }
                    readableBountyMissions += ", target name: ";
                    if (hasObjVar(objMissions[i], "strTargetName"))
                    {
                        readableBountyMissions += getStringObjVar(objMissions[i], "strTargetName");
                    }
                    else 
                    {
                        readableBountyMissions += "<unset>";
                    }
                    readableBountyMissions += "\r\n";
                }
            }
        }
        if (readableBountyMissions.equals(""))
        {
            readableBountyMissions = "<>\r\n";
        }
        return readableBountyMissions;
    }
    public static String getReadableBounties(obj_id objTarget) throws InterruptedException
    {
        String readableBounties = "";
        obj_id[] bounties = getBountyHunterBounties(objTarget);
        if (bounties != null)
        {
            for (int i = 0; i < bounties.length; ++i)
            {
                readableBounties += bounties[i];
                readableBounties += " (";
                readableBounties += getPlayerName(bounties[i]);
                readableBounties += ")";
                readableBounties += "\r\n";
            }
        }
        if (readableBounties.equals(""))
        {
            readableBounties = "<>\r\n";
        }
        return readableBounties;
    }
    public static String getReadableBountyHunters(obj_id objTarget) throws InterruptedException
    {
        String readableBountyHunters = "";
        obj_id[] hunters = getJediBounties(objTarget);
        if (hunters != null)
        {
            for (int i = 0; i < hunters.length; ++i)
            {
                readableBountyHunters += hunters[i];
                readableBountyHunters += " (";
                readableBountyHunters += getPlayerName(hunters[i]);
                readableBountyHunters += ")";
                readableBountyHunters += "\r\n";
            }
        }
        if (readableBountyHunters.equals(""))
        {
            readableBountyHunters = "<>\r\n";
        }
        return readableBountyHunters;
    }
    public static String getReadableFsQuests(obj_id objTarget) throws InterruptedException
    {
        String readableFsQuests = "";
        dictionary questMap = new dictionary();
        int rows = dataTableGetNumRows(DATATABLE_FS_QUESTS);
        for (int i = 0; i < rows; i++)
        {
            String questName = (quests.getDataEntry(i, "NAME")).trim();
            String attachScript = (quests.getDataEntry(i, "ATTACH_SCRIPT")).trim();
            String taskTarget = (quests.getDataEntry(i, "TARGET")).trim();
            String taskParam = (quests.getDataEntry(i, "PARAMETER")).trim();
            String parentQuest = (quests.getParentQuestName(questName)).trim();
            dictionary d = new dictionary();
            if (attachScript != null && !attachScript.equals(""))
            {
                d.put("attachScript", attachScript);
            }
            if (taskTarget != null && !taskTarget.equals(""))
            {
                d.put("taskTarget", taskTarget);
            }
            if (taskParam != null && !taskParam.equals(""))
            {
                d.put("taskParam", taskParam);
            }
            if (parentQuest != null && !parentQuest.equals(""))
            {
                d.put("parentQuest", parentQuest);
            }
            if (isQuestActive(objTarget, i))
            {
                d.put("isComplete", false);
                questMap.put(questName, d);
            }
            else if (isQuestComplete(objTarget, i))
            {
                d.put("isComplete", true);
                questMap.put(questName, d);
            }
        }
        java.util.Enumeration enum_ = questMap.keys();
        while (enum_.hasMoreElements())
        {
            String questName = (String)enum_.nextElement();
            dictionary childQuestDict = questMap.getDictionary(questName);
            String parentQuestName = childQuestDict.getString("parentQuest");
            if (parentQuestName != null)
            {
                dictionary parentQuestDict = questMap.getDictionary(parentQuestName);
                if (parentQuestDict != null)
                {
                    Vector childArray = parentQuestDict.getResizeableStringArray("childQuests");
                    if (childArray == null)
                    {
                        childArray = new Vector();
                    }
                    childArray.add(questName);
                    parentQuestDict.put("childQuests", childArray);
                }
            }
        }
        enum_ = questMap.keys();
        while (enum_.hasMoreElements())
        {
            String questName = (String)enum_.nextElement();
            dictionary d = questMap.getDictionary(questName);
            if (d.containsKey("parentQuest"))
            {
                continue;
            }
            readableFsQuests += getReadableFsQuest(questMap, questName, 0);
            readableFsQuests += getReadableFsQuestChildren(questMap, questName, 1);
        }
        return readableFsQuests;
    }
    public static String getReadableFsQuestChildren(dictionary questMap, String questName, int indentLevel) throws InterruptedException
    {
        String readableFsQuestChildren = "";
        dictionary d = questMap.getDictionary(questName);
        String[] childQuests = d.getStringArray("childQuests");
        if (childQuests != null)
        {
            for (int i = 0; i < childQuests.length; i++)
            {
                readableFsQuestChildren += getReadableFsQuest(questMap, childQuests[i], indentLevel);
                readableFsQuestChildren += getReadableFsQuestChildren(questMap, childQuests[i], indentLevel + 1);
            }
        }
        return readableFsQuestChildren;
    }
    public static String getReadableFsQuest(dictionary questMap, String questName, int indentLevel) throws InterruptedException
    {
        String readableFsQuest = "";
        dictionary d = questMap.getDictionary(questName);
        for (int i = 0; i < indentLevel; i++)
        {
            readableFsQuest += "\t";
        }
        if (d.getBoolean("isComplete"))
        {
            readableFsQuest += "[x] ";
        }
        else 
        {
            readableFsQuest += "[ ] ";
        }
        readableFsQuest += questName;
        if (d.containsKey("attachScript"))
        {
            readableFsQuest += " (" + d.getString("attachScript") + ")";
        }
        if (d.containsKey("taskTarget"))
        {
            readableFsQuest += " (" + d.getString("taskTarget") + ")";
        }
        if (d.containsKey("taskParam"))
        {
            readableFsQuest += " (" + d.getString("taskParam") + ")";
        }
        readableFsQuest += "\n";
        return readableFsQuest;
    }
    public static String getReadableFsBranchesUnlocked(obj_id objTarget) throws InterruptedException
    {
        String branch_list = "";
        for (int i = 0; i < 16; i++)
        {
            if (fs_quests.hasUnlockedBranch(objTarget, i))
            {
                String branch = fs_quests.getBranchFromId(i);
                String branch_name = localize(new string_id("quest/force_sensitive/utils", branch));
                if (branch != null)
                {
                    branch_list += branch_name + "\n";
                }
            }
        }
        return branch_list;
    }
    public static String getReadableSpaceQuests(obj_id objTarget) throws InterruptedException
    {
        String readableSpaceQuests = "";
        obj_var_list typeList = getObjVarList(objTarget, space_quest.QUEST_STATUS);
        if (typeList != null)
        {
            java.util.Enumeration typeEnum = typeList.keys();
            while (typeEnum.hasMoreElements())
            {
                String questType = (String)typeEnum.nextElement();
                obj_var_list questList = typeList.getObjVarList(questType);
                java.util.Enumeration questEnum = questList.keys();
                while (questEnum.hasMoreElements())
                {
                    String questName = (String)questEnum.nextElement();
                    readableSpaceQuests += getReadableSpaceQuest(objTarget, questType, questName);
                }
            }
        }
        return readableSpaceQuests;
    }
    public static String getReadableSpaceQuest(obj_id player, String questType, String questName) throws InterruptedException
    {
        String readableSpaceQuest = "";
        obj_id questObject = space_quest._getQuest(player, questType, questName);
        int questId = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questIsQuestComplete(questId, player))
        {
            readableSpaceQuest += "[x]";
        }
        else if (questIsQuestActive(questId, player))
        {
            readableSpaceQuest += "[ ]";
        }
        else 
        {
            readableSpaceQuest += "???";
        }
        readableSpaceQuest += " " + questName;
        readableSpaceQuest += " type=" + questType;
        if (!isIdNull(questObject))
        {
            readableSpaceQuest += " zone=" + utils.getStringObjVar(questObject, space_quest.QUEST_ZONE, "?");
            readableSpaceQuest += " oid=" + questObject;
            if (utils.getBooleanObjVar(questObject, space_quest.QUEST_DUTY, false))
            {
                readableSpaceQuest += " isDuty";
            }
            if (space_quest.isQuestInProgress(questObject))
            {
                readableSpaceQuest += " isInProgress";
            }
        }
        if (space_quest.hasFailedQuest(player, questType, questName))
        {
            readableSpaceQuest += " hasFailed";
        }
        if (space_quest.hasWonQuest(player, questType, questName))
        {
            readableSpaceQuest += " hasWon";
        }
        if (space_quest.hasAbortedQuest(player, questType, questName))
        {
            readableSpaceQuest += " hasAborted";
        }
        if (space_quest.hasReceivedReward(player, questType, questName))
        {
            readableSpaceQuest += " hasReceivedReward";
        }
        string_id title = new string_id("spacequest/" + questType + "/" + questName, "title");
        readableSpaceQuest += " title=<" + getString(title) + ">";
        readableSpaceQuest += "\n";
        return readableSpaceQuest;
    }
    public static String csTargetDump(obj_id self, obj_id objTarget, boolean colorFlag) throws InterruptedException
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
                String[] collectionBooks = getAllCollectionBooks();
                if (collectionBooks != null && collectionBooks.length > 0)
                {
                    String allCompCollections = getAllCompletedCollections(objTarget, collectionBooks);
                    if (allCompCollections != null && !allCompCollections.equals(""))
                    {
                        strTest += "\r\nCOMPLETED COLLECTIONS:\r\n";
                        strTest += allCompCollections + "\r\n";
                    }
                    String allIncompCollections = getAllIncompleteCollections(objTarget, collectionBooks);
                    if (allIncompCollections != null && !allIncompCollections.equals(""))
                    {
                        strTest += "\r\nCOLLECTIONS STARTED BUT INCOMPLETE:\r\n";
                        strTest += allIncompCollections + "\r\n";
                    }
                }
            }
        }
        String readableScripts = getReadableScripts(objTarget);
        if (!readableScripts.equals("<>") && !readableScripts.equals("") && readableScripts.length() > 5)
        {
            strTest += "\n\rATTACHED SCRIPTS:\r\n";
            strTest += readableScripts;
            strTest += "\n\r";
        }
        String objVars = getReadableObjVars(objTarget);
        if (!objVars.equals("<>") && !objVars.equals("") && objVars.length() > 5)
        {
            strTest += "OBJVARS:\r\n";
            strTest += objVars;
            strTest += "\n\r";
        }
        String scriptVars = getReadableScriptVars(objTarget);
        if (!scriptVars.equals("<>") && !scriptVars.equals("") && scriptVars.length() > 5)
        {
            strTest += "SCRIPTVARS:\r\n";
            strTest += scriptVars;
            strTest += "\n\r";
        }
        String localVars = getReadableLocalVars(objTarget);
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
            String[] allQuests = gm.getAllQuests(self);
            if (allQuests != null && allQuests.length > 0)
            {
                strTest += "\r\nGROUND AND SPACE QUESTS:\r\n";
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
            String enemyFlags = getReadableEnemyFlags(objTarget);
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
            String playerBountyMissions = getReadableBountyMissions(objTarget);
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
            String playerBounties = getReadableBounties(objTarget);
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
            String huntersOfPlayer = getReadableBountyHunters(objTarget);
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
            String fsQuestData = getReadableFsQuests(objTarget);
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
            String fsBranchesUnlocked = getReadableFsBranchesUnlocked(objTarget);
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
                    strTest += getReadableScripts(objPilot);
                    strTest += "\n\r";
                    strTest += "OBJVARS\r\n";
                    strTest += getReadableObjVars(objPilot);
                    strTest += "\n\r";
                    strTest += "SCRIPTVARS\r\n";
                    strTest += getReadableScriptVars(objPilot);
                    strTest += "\n\r";
                    strTest += "LOCALVARS\r\n";
                    strTest += getReadableLocalVars(objPilot);
                    strTest += "\n\r";
                }
            }
            strTest += "SHIP STATS BELOW\r\n";
            int[] intSlots = space_crafting.getShipInstalledSlots(objTarget);
            for (int intI = 0; intI < intSlots.length; intI++)
            {
                strTest += getShipComponentDebugString(objTarget, intSlots[intI]);
                strTest += "\n\r";
            }
        }
        String containmentTree = getReadableContainmentTree(objTarget);
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
                strTest += getTargetInfoString(spawner);
            }
        }
        return strTest;
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
    public static obj_id findDumpTarget(obj_id self) throws InterruptedException
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
        return finalTarget;
    }
    public static String getAllCompletedCollections(obj_id player, String[] collectionBooks) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return null;
        }
        if (collectionBooks == null || collectionBooks.length <= 0)
        {
            return null;
        }
        String retrunString = "";
        for (int i = 0; i < collectionBooks.length; i++)
        {
            String[] completedCollections = getCompletedCollectionsInBook(player, collectionBooks[i]);
            if (completedCollections == null || completedCollections.length <= 0)
            {
                continue;
            }
            else 
            {
                retrunString += "\t" + collectionBooks[i] + "\r\n";
                for (int j = 0; j < completedCollections.length; j++)
                {
                    int sizeOf = completedCollections[j].length();
                    retrunString += "\t\t" + completedCollections[j] + "\r\n";
                }
            }
        }
        return retrunString;
    }
    public static String getAllIncompleteCollections(obj_id player, String[] collectionBooks) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return null;
        }
        if (collectionBooks == null || collectionBooks.length <= 0)
        {
            return null;
        }
        String returnString = "";
        for (int i = 0; i < collectionBooks.length; i++)
        {
            returnString += "\t" + collectionBooks[i] + "\r\n";
            String[] allCollections = getAllCollectionsInBook(collectionBooks[i]);
            if (allCollections == null || allCollections.length <= 0)
            {
                continue;
            }
            boolean hasCollectionDataForBook = false;
            for (int j = 0; j < allCollections.length; j++)
            {
                if (hasCompletedCollection(player, allCollections[j]))
                {
                    continue;
                }
                String[] allSlots = getAllCollectionSlotsInCollection(allCollections[j]);
                if (allSlots == null || allSlots.length <= 0)
                {
                    continue;
                }
                boolean startedNotFinished = false;
                for (int l = 0; l < allSlots.length; l++)
                {
                    if (getCollectionSlotValue(player, allSlots[l]) > 0)
                    {
                        startedNotFinished = true;
                        break;
                    }
                }
                if (startedNotFinished)
                {
                    returnString += "\t\t" + allCollections[j] + "\r\n";
                    hasCollectionDataForBook = true;
                    for (int k = 0; k < allSlots.length; k++)
                    {
                        returnString += "\t\t\t" + allSlots[k] + ": " + getCollectionSlotValue(player, allSlots[k]) + "\r\n";
                    }
                }
            }
            if (!hasCollectionDataForBook)
            {
                returnString += "\t\tNone\r\n";
            }
        }
        return returnString;
    }
}
