package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai;
import script.library.ai_lib;
import script.library.chat;
import script.library.create;
import script.library.groundquests;
import script.library.prose;
import script.library.space_dungeon;
import script.library.utils;
import script.library.sui;

public class mustafar extends script.base_script
{
    public mustafar()
    {
    }
    public static final String CONST_TBL_EVENT_DATA = "datatables/dungeon/mustafar_trials/obiwan_finale/obiwan_event_data.iff";
    public static final String STF_OBI_MSGS = "mustafar/obiwan_finale";
    public static final string_id SID_OBIWAN_BUSY = new string_id(STF_OBI_MSGS, "obi_busy_elsewhere");
    public static final string_id SID_ALREADY_COMPLETED = new string_id(STF_OBI_MSGS, "obiquest_already_completed");
    public static final string_id SID_NO_OBI_CONVO_YET = new string_id(STF_OBI_MSGS, "obiwan_finale_launch_before_convo");
    public static final string_id SID_NOT_READY = new string_id(STF_OBI_MSGS, "not_prepared");
    public static final string_id SID_MOUNTED = new string_id(STF_OBI_MSGS, "not_while_riding");
    public static final String[] REQUIRED_QUEST_NAMES = 
    {
        "som_kenobi_collectors_business_1",
        "som_kenobi_cursed_shard_2",
        "som_kenobi_hidden_treasure_2",
        "som_kenobi_historian_2",
        "som_kenobi_moral_choice_1",
        "som_kenobi_reunite_shard_3",
        "som_kenobi_samaritan_1",
        "som_kenobi_serpent_shard_1",
        "som_kenobi_symbiosis_1"
    };
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public static final String STF = "som/som_quest";
    public static final String QUEST_ACCEPT_OK = "@" + STF + ":quest_accept_ok";
    public static final String QUEST_ACCEPT_CANCEL = "@" + STF + ":quest_accept_cancel";
    public static final String BEGIN_QUEST_PROMPT = "@" + STF + ":begin_quest_prompt";
    public static final String BEGIN_QUEST_TITLE = "@" + STF + ":begin_quest_title";
    public static void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/mustafar/" + section, message);
        }
    }
    public static boolean canCallObiwan(obj_id player) throws InterruptedException
    {
        return canCallObiwan(player, null, false);
    }
    public static boolean canCallObiwan(obj_id player, obj_id recallObject) throws InterruptedException
    {
        return canCallObiwan(player, recallObject, false);
    }
    public static boolean canCallObiwan(obj_id player, obj_id recallObject, boolean giveFailureFeedback) throws InterruptedException
    {
        debugLogging("canCallObiwan: ", " entered.");
        String planetName = getCurrentSceneName();
        if (!planetName.startsWith("mustafar"))
        {
            return false;
        }
        obj_id topMostContainer = getTopMostContainer(player);
        if ((topMostContainer != player) || (topMostContainer == null))
        {
            return false;
        }
        obj_id dungeon = space_dungeon.getDungeonIdForPlayer(player);
        if (isIdValid(dungeon))
        {
            return false;
        }
        if (isIdValid(getMountId(player)))
        {
            if (giveFailureFeedback)
            {
                sendSystemMessage(player, SID_MOUNTED);
            }
            debugLogging("canCallObiwan: ", " player mounted. No call.");
            return false;
        }
        if (hasObjVar(player, "didMustafarCrystalLair"))
        {
            sendSystemMessage(player, SID_ALREADY_COMPLETED);
            debugLogging("canCallObiwan: ", " player has already completed final quest. No call.");
            removeObjVar(player, "waitingOnObiwan");
            removeObjVar(player, "quest.wait_for_signal.quest/som_kenobi_main_quest_3_b");
            removeObjVar(player, "quest.wait_for_signal.quest/som_kenobi_main_quest_3");
            return false;
        }
        if (!hasCompletedTrials(player) && !isGod(player))
        {
            if (giveFailureFeedback)
            {
                sendSystemMessage(player, SID_NOT_READY);
            }
            return false;
        }
        if (!obiwanAlreadyPresent(player, giveFailureFeedback))
        {
            debugLogging("canCallObiwan: ", " No obiwan in range. ok to call him.");
            return true;
        }
        LOG("space_dungeon", "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_recall_object.canCallObiwan");
        return false;
    }
    public static boolean obiwanAlreadyPresent(obj_id player, boolean giveFailureFeedback) throws InterruptedException
    {
        debugLogging("obiwanAlreadyPresent: ", " entered.");
        location currentLoc = getLocation(player);
        obj_id obiwanObject = getFirstObjectWithObjVar(currentLoc, 100f, "mustafar_obiwan");
        if (isIdValid(obiwanObject))
        {
            if (giveFailureFeedback)
            {
                sendSystemMessage(player, SID_OBIWAN_BUSY);
            }
            debugLogging("obiwanAlreadyPresent: ", "found a pre-existing obiwan object.");
            return true;
        }
        debugLogging("obiwanAlreadyPresent: ", "there is no pre-existing obiwan object");
        return false;
    }
    public static obj_id callObiwan(obj_id player) throws InterruptedException
    {
        return callObiwan(player, null, false, 0);
    }
    public static obj_id callObiwan(obj_id player, boolean timeout) throws InterruptedException
    {
        return callObiwan(player, null, timeout, 0);
    }
    public static obj_id callObiwan(obj_id player, boolean timeout, int appearanceComment) throws InterruptedException
    {
        return callObiwan(player, null, timeout, appearanceComment);
    }
    public static obj_id callObiwan(obj_id player, obj_id landmark) throws InterruptedException
    {
        return callObiwan(player, landmark, false, 0);
    }
    public static obj_id callObiwan(obj_id player, obj_id landmark, boolean timeout) throws InterruptedException
    {
        return callObiwan(player, landmark, timeout, 0);
    }
    public static obj_id callObiwan(obj_id player, obj_id landmark, boolean timeout, int appearanceComment) throws InterruptedException
    {
        debugLogging("//***// callObiwan: ", "////>>>> entered.");
        location currentLoc = getLocation(player);
        float landmarkDistanceOffset = 0f;
        if (isIdValid(landmark))
        {
            location landmarkLoc = getLocation(landmark);
            landmarkDistanceOffset = getDistance(player, landmark);
            debugLogging("callObiwan: ", "landmark location is valid.");
        }
        float distance = landmarkDistanceOffset + 6f;
        if (timeout)
        {
            if (distance < 11f)
            {
                distance = 11f;
            }
        }
        location spawnLoc = utils.findLocInFrontOfTarget(player, distance);
        obj_id obiwan = create.object("som_kenobi_obi_wan_glowie", spawnLoc);
        if (!isIdValid(obiwan))
        {
            debugLogging("callObiwan: ", "obiwan obj_id that we created is not valid");
            return null;
        }
        ai_lib.setDefaultCalmBehavior(obiwan, ai_lib.BEHAVIOR_SENTINEL);
        faceTo(obiwan, player);
        attachScript(obiwan, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_obi_facer");
        setObjVar(obiwan, "mustafar_obiwan", 1);
        if (timeout)
        {
            utils.setScriptVar(obiwan, "player", player);
            attachScript(obiwan, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_dynamic_timeout");
        }
        if (appearanceComment > 0)
        {
            string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_greet_story_" + appearanceComment);
            prose_package pp = prose.getPackage(strSpam);
            prose.setTT(pp, player);
            chat.chat(obiwan, player, pp);
        }
        return obiwan;
    }
    public static void spawnContents(obj_id dungeon, String spawnType, int numberOfCopies) throws InterruptedException
    {
        debugLogging("//***// spawnContents: ", "////>>>> entered. SpawnType requested was: " + spawnType);
        String[] spawnTypeColumnData = dataTableGetStringColumnNoDefaults(CONST_TBL_EVENT_DATA, "dataType");
        if (spawnTypeColumnData.length == 0)
        {
            return;
        }
        for (int i = 0; i < spawnTypeColumnData.length; i++)
        {
            if (spawnTypeColumnData[i].equals(spawnType))
            {
                dictionary dict = dataTableGetRow(CONST_TBL_EVENT_DATA, i);
                debugLogging("spawnContents: ", "Retrieving info for row(" + i + ")");
                int locx = dict.getInt("locx");
                int locy = dict.getInt("locy");
                int locz = dict.getInt("locz");
                location myself = getLocation(dungeon);
                String scene = myself.area;
                obj_id cell = getCellId(dungeon, "mainroom");
                float yaw = dict.getFloat("yaw");
                String script = dict.getString("script");
                location spawnLoc = new location(locx, locy, locz, scene, cell);
                String object = dict.getString("object");
                if ((dict.getString("dataType")).equals("setpiece"))
                {
                    debugLogging("//***// spawnContents: ", "////>>>> Going to try to spawn a 'setpiece'");
                    obj_id item = createObjectInCell(object, dungeon, "mainroom", spawnLoc);
                    if (!isIdValid(item))
                    {
                        debugLogging("spawnContents: ", "Tried to create invalid item(" + object + ")");
                        return;
                    }
                    setYaw(item, yaw);
                    if (!script.equals("none"))
                    {
                        attachScript(item, script);
                    }
                    utils.setScriptVar(item, "dungeon", dungeon);
                    debugLogging("spawnContents: ", "Created object(" + object + "/" + item + ") with script(" + script + ") with yaw (" + yaw);
                }
                else if (!(dict.getString("dataType")).equals("location") && !(dict.getString("dataType")).equals("forcePowerAttack"))
                {
                    debugLogging("//***// spawnContents: ", "////>>>> Going to try to spawn a creature of some kind");
                    if (numberOfCopies < 1)
                    {
                        numberOfCopies = 1;
                    }
                    for (int j = 0; j < numberOfCopies; j++)
                    {
                        obj_id creature = create.object(object, spawnLoc);
                        String creatureName = ai_lib.getCreatureName(creature);
                        obj_id[] players = getPlayerCreaturesInRange(creature, 60.0f);
                        if (!isIdValid(creature))
                        {
                            debugLogging("spawnContents: ", "Tried to create invalid creature(" + object + ")");
                            return;
                        }
                        if (!script.equals("none"))
                        {
                            attachScript(creature, script);
                        }
                        if (creatureName.startsWith("som_kenobi_finale_minion"))
                        {
                            addHate(creature, players[i], 1000f);
                        }
                        utils.setScriptVar(creature, "dungeon", dungeon);
                        debugLogging("spawnContents: ", "Created object(" + object + "/" + creature + ") with script(" + script);
                    }
                }
            }
        }
        return;
    }
    public static boolean moveBossAround(String moveTargetName, obj_id boss, obj_id dungeon) throws InterruptedException
    {
        debugLogging("moveBoss: ", ">>>> entered.");
        if (!isIdValid(dungeon))
        {
            debugLogging("moveBoss: ", ">>>> dungeon objId was invalid.");
            return false;
        }
        int desiredDatatableRow = dataTableSearchColumnForString(moveTargetName, "dataType", mustafar.CONST_TBL_EVENT_DATA);
        if (desiredDatatableRow < 0)
        {
            debugLogging("moveBoss: ", ">>>> desiredDatatableRow was no good");
            return false;
        }
        dictionary dict = dataTableGetRow(mustafar.CONST_TBL_EVENT_DATA, desiredDatatableRow);
        debugLogging("moveBoss: ", "Retrieving info for row(" + desiredDatatableRow + ")");
        int locx = dict.getInt("locx");
        int locy = dict.getInt("locy");
        int locz = dict.getInt("locz");
        String scene = getCurrentSceneName();
        obj_id cell = getCellId(dungeon, "mainroom");
        debugLogging("moveBoss: ", ">>>> locX: " + locx + " locY: " + locy + " locZ: " + locz + " scene: " + scene + " cell: " + cell);
        location gotoLoc = new location(locx, locy, locz, scene, cell);
        debugLogging("moveBoss: ", ">>>> executing pathTo.");
        ai.pathTo(boss, gotoLoc);
        return true;
    }
    public static boolean hasCompletedTrials(obj_id player) throws InterruptedException
    {
        for (int i = 0; i < REQUIRED_QUEST_NAMES.length; i++)
        {
            if (!groundquests.hasCompletedQuest(player, REQUIRED_QUEST_NAMES[i]))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean stillWithinDungeonCheck(obj_id player, obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(dungeon))
        {
            return false;
        }
        obj_id currentTopMost = getTopMostContainer(player);
        if (currentTopMost == dungeon)
        {
            return true;
        }
        return false;
    }
    public static void activateQuestAcceptSUI(obj_id player, obj_id self) throws InterruptedException
    {
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "handleQuestOfferResponse");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, BEGIN_QUEST_TITLE);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, BEGIN_QUEST_PROMPT);
        sui.msgboxButtonSetup(pid, sui.YES_NO);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, QUEST_ACCEPT_OK);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, QUEST_ACCEPT_CANCEL);
        sui.showSUIPage(pid);
    }
}
