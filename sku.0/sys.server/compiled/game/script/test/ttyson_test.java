package script.test;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

public class ttyson_test extends script.base.remote_object_requester
{
    public ttyson_test()
    {
    }
    public static final String STARTING_EQUIPMENT_FILE = "datatables/equipment/newbie_equipment.iff";
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (true)
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String command = tok.nextToken();
                if (command.equalsIgnoreCase("tt_giveQuest"))
                {
                    debugConsoleMsg(self, "hit tt_giveQuest");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        int result = questActivateQuest(questId, self, self);
                        if (result == 1)
                        {
                            String s = "Error: Quest already active.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 2)
                        {
                            String s = "Error: No such quest.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 3)
                        {
                            String s = "Error: No such task.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 4)
                        {
                            String s = "Error: Quest already completed, and not repeatable.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 5)
                        {
                            String s = "Error: Failed prerequisites.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 6)
                        {
                            String s = "Error: Failed exclusions.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 7)
                        {
                            String s = "Error: Quest Not active.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 8)
                        {
                            String s = "Error: Task not active.";
                            debugSpeakMsg(self, s);
                        }
                        else if (result == 9)
                        {
                            String s = "Error: No such player.";
                            debugSpeakMsg(self, s);
                        }
                    }
                    else 
                    {
                        debugConsoleMsg(self, "Usage: tt_giveQuest <questname>. i.e. \"tt_giveQuest quest/loot_5_widgets\"");
                        debugSpeakMsg(self, "Usage: tt_giveQuest <questname>. i.e. \"tt_giveQuest quest/loot_5_widgets\"");
                    }
                }
                else if (command.equalsIgnoreCase("tt_grantQuest"))
                {
                    debugConsoleMsg(self, "hit tt_grantQuest");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        groundquests.grantQuest(self, questName);
                    }
                }
                else if (command.equalsIgnoreCase("tt_clearQuest"))
                {
                    debugConsoleMsg(self, "hit tt_clearQuest");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        questClearQuest(questId, self);
                    }
                    else 
                    {
                        debugConsoleMsg(self, "Usage: tt_clearQuest <questname>. i.e. \"tt_giveQuest quest/loot_5_widgets\"");
                        debugSpeakMsg(self, "Usage: tt_clearQuest <questname>. i.e. \"tt_giveQuest quest/loot_5_widgets\"");
                    }
                }
                else if (command.equalsIgnoreCase("tt_completeTask"))
                {
                    debugConsoleMsg(self, "hit tt_completeTask");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        String taskName = tok.nextToken();
                        int taskId = utils.stringToInt(taskName);
                        if (taskId == -1)
                        {
                            taskId = groundquests.getTaskId(questId, taskName);
                        }
                        questCompleteTask(questId, taskId, self);
                    }
                    else 
                    {
                        debugConsoleMsg(self, "Usage: tt_completeTask <questname> <taskid>. i.e. \"tt_completeTask quest/loot_5_widgets 0\"");
                        debugSpeakMsg(self, "Usage: tt_completeTask <questname> <taskid>. i.e. \"tt_completeTask quest/loot_5_widgets 0\"");
                    }
                }
                else if (command.equalsIgnoreCase("tt_completeAllTasks"))
                {
                    debugConsoleMsg(self, "hit tt_completeAllTasks");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        for (int taskId = 0; taskId < 16; ++taskId)
                        {
                            questCompleteTask(questId, taskId, self);
                            if (questIsQuestComplete(questId, self))
                            {
                                break;
                            }
                        }
                    }
                    else 
                    {
                        final String usageText = "Usage: tt_completeAllTasks <questname> i.e. \"tt_completeAllTasks quest/loot_5_widgets\"";
                        debugConsoleMsg(self, usageText);
                        debugSpeakMsg(self, usageText);
                    }
                }
                else if (command.equalsIgnoreCase("tt_failTask"))
                {
                    debugConsoleMsg(self, "hit tt_failTask");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        String taskName = tok.nextToken();
                        int taskId = utils.stringToInt(taskName);
                        if (taskId == -1)
                        {
                            taskId = groundquests.getTaskId(questId, taskName);
                        }
                        questFailTask(questId, taskId, self);
                    }
                    else 
                    {
                        final String usage = "Usage: tt_failTask <questname> <taskid>. i.e. \"tt_failTask quest/loot_5_widgets 0\"";
                        debugConsoleMsg(self, usage);
                        debugSpeakMsg(self, usage);
                    }
                }
                else if (command.equalsIgnoreCase("tt_completeQuest"))
                {
                    debugConsoleMsg(self, "hit tt_completeQuest");
                    if (tok.hasMoreTokens())
                    {
                        String questName = tok.nextToken();
                        int questId = questGetQuestId(questName);
                        questCompleteQuest(questId, self);
                    }
                    else 
                    {
                        debugConsoleMsg(self, "Usage: tt_completeQuest <questname>. i.e. \"tt_giveQuest quest/loot_5_widgets\"");
                        debugSpeakMsg(self, "Usage: tt_completeQuest <questname>. i.e. \"tt_giveQuest quest/loot_5_widgets\"");
                    }
                }
                else if (command.equalsIgnoreCase("tt_sendSignal"))
                {
                    debugConsoleMsg(self, "hit tt_sendSignal");
                    if (tok.hasMoreTokens())
                    {
                        String signalName = tok.nextToken();
                        groundquests.sendSignal(self, signalName);
                    }
                    else 
                    {
                        final String usage = "Usage: tt_sendSignal <signalName>. i.e. \"tt_sendSignal killed_the_rabbit\"";
                        debugConsoleMsg(self, usage);
                        debugSpeakMsg(self, usage);
                    }
                }
                else if (command.equalsIgnoreCase("tt_testTaskNames"))
                {
                    debugConsoleMsg(self, "hit tt_testTaskNames");
                    String questName = "quest/test_encounter";
                    if (groundquests.isValidQuestName(questName))
                    {
                        debugConsoleMsg(self, "valid quest name");
                    }
                    int questCrc = questGetQuestId(questName);
                    int taskId = groundquests.getTaskId(questCrc, "killed_them_rats");
                    debugConsoleMsg(self, "questCrc=[" + questCrc + "] taskId=[" + taskId + "]");
                    if (questIsTaskComplete(questCrc, taskId, self))
                    {
                        groundquests.sendSignal(self, "testSignal");
                        debugConsoleMsg(self, "sending signal");
                    }
                    else 
                    {
                        debugConsoleMsg(self, "not sending signal");
                    }
                }
                else if (command.equalsIgnoreCase("tt_testRandomLocation"))
                {
                    for (int i = 0; i < 50; ++i)
                    {
                        location l = groundquests.getRandom2DLocationAroundPlayer(self, 3, 5);
                        obj_id object = create.object("object/tangible/gravestone/gravestone01.iff", l);
                    }
                }
                else if (command.equalsIgnoreCase("tt_setPendingEscortTarget"))
                {
                    if (tok.hasMoreTokens())
                    {
                        String targetString = tok.nextToken();
                        obj_id target = obj_id.getObjId(java.lang.Long.parseLong(targetString));
                        if (!groundquests.isEscortTargetReadyForStaticEscortTask(target))
                        {
                            debugConsoleMsg(self, "target busy");
                        }
                        else 
                        {
                            groundquests.setPendingStaticEscortTarget(self, target);
                            debugConsoleMsg(self, "target target set");
                        }
                    }
                    else 
                    {
                        debugSpeakMsg(self, "Usage: tt_setPendingEscortTarget <targetid>");
                    }
                }
                else if (command.equalsIgnoreCase("tt_testSui"))
                {
                    int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, self, "testMessage");
                    sui.setAutosaveProperty(pid, false);
                    sui.setSizeProperty(pid, 350, 275);
                    sui.setLocationProperty(pid, 100, 100);
                    sui.setSoundProperty(pid, "sound/tut_00_congratulations.snd");
                    setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, "Hello");
                    String prompt = "";
                    for (int i = 0; i < 100; ++i)
                    {
                        prompt += "This is a long string. ";
                    }
                    setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, prompt);
                    sui.msgboxButtonSetup(pid, sui.OK_ONLY);
                    sui.showSUIPage(pid);
                }
                else if (command.equalsIgnoreCase("tt_time"))
                {
                    debugSpeakMsg(self, "getGameTime() = " + getGameTime());
                    debugSpeakMsg(self, "getPlayerPlayedTime() = " + getPlayerPlayedTime(self));
                }
                else if (command.equalsIgnoreCase("tt_go"))
                {
                    location loc = new location(-1259, 0, -3552, "tatooine", null);
                    addLocationTarget("hello", loc, 5);
                    debugSpeakMsg(self, "Added a location target");
                }
                else if (command.equalsIgnoreCase("tt_newbie"))
                {
                    giveNewbieEquipment(self);
                    giveNewbieSkills(self);
                    debugSpeakMsg(self, "Newbie setup complete.");
                }
                else if (command.equalsIgnoreCase("tt_yaw"))
                {
                    float yaw = utils.stringToFloat(tok.nextToken());
                    setYaw(self, yaw);
                    debugSpeakMsg(self, "Set yaw to " + yaw);
                }
                else if (command.equalsIgnoreCase("tt_testcwd"))
                {
                    dictionary dict = new dictionary();
                    dict.put("Me", self);
                    replaceClusterWideData("tony_manager", "test", dict, true, 0);
                    debugSpeakMsg(self, "Setting data in CWD object");
                }
                else if (command.equalsIgnoreCase("tt_remoteCreate"))
                {
                    remoteCreate("space_tatooine", "syren5_cal_handro", 0, 0, 0);
                    debugSpeakMsg(self, "Requesting remote object creation data from the CWD object");
                }
                else if (command.equalsIgnoreCase("tt_heading"))
                {
                    enableLogLocationHeading(self);
                }
                else if (command.equalsIgnoreCase("tt_worrt"))
                {
                    float playerRadius = getObjectCollisionRadius(self);
                    float objectRadius = 3;
                    float offset = playerRadius;
                    location heading = getHeading(self);
                    for (int i = 0; i < 4; ++i)
                    {
                        offset += objectRadius;
                        location spawnLocation = getLocation(self);
                        spawnLocation.x += heading.x * offset;
                        spawnLocation.z += heading.z * offset;
                        create.object("worrt", spawnLocation);
                    }
                }
                else if (command.equalsIgnoreCase("tt_ai_stop"))
                {
                    final obj_id target = getLookAtTarget(self);
                    if (isValidId(target) && target != self)
                    {
                        ai_lib.setDefaultCalmBehavior(target, ai_lib.BEHAVIOR_SENTINEL);
                    }
                }
                else if (command.equalsIgnoreCase("tt_ai_go"))
                {
                    final obj_id target = getLookAtTarget(self);
                    if (isValidId(target) && target != self)
                    {
                        ai_lib.setDefaultCalmBehavior(target, ai_lib.BEHAVIOR_LOITER);
                    }
                }
                else if (command.equalsIgnoreCase("tt_giveSpace"))
                {
                    debugConsoleMsg(self, "hit tt_giveSpace");
                    if (tok.hasMoreTokens())
                    {
                        String missionType = tok.nextToken();
                        String missionName = tok.nextToken();
                        space_quest.grantQuest(self, missionType, missionName);
                    }
                    else 
                    {
                        String usage = "Usage: tt_giveSpace <missionType> <missionName>. i.e. \"tt_giveSpace assassinate npe_hard_main_3a\"";
                        debugConsoleMsg(self, usage);
                        debugSpeakMsg(self, usage);
                    }
                }
                else if (command.equalsIgnoreCase("tt_winSpace"))
                {
                    debugConsoleMsg(self, "hit tt_winSpace");
                    if (tok.hasMoreTokens())
                    {
                        String missionType = tok.nextToken();
                        String missionName = tok.nextToken();
                        obj_id quest = space_quest._getQuest(self, missionType, missionName);
                        space_quest.setQuestWon(self, quest);
                    }
                    else 
                    {
                        String usage = "Usage: tt_winSpace <missionType> <missionName>. i.e. \"tt_winSpace assassinate npe_hard_main_3a\"";
                        debugConsoleMsg(self, usage);
                        debugSpeakMsg(self, usage);
                    }
                }
                else if (command.equalsIgnoreCase("tt_failSpace"))
                {
                    debugConsoleMsg(self, "hit tt_failSpace");
                    if (tok.hasMoreTokens())
                    {
                        String missionType = tok.nextToken();
                        String missionName = tok.nextToken();
                        obj_id quest = space_quest._getQuest(self, missionType, missionName);
                        space_quest.setQuestFailed(self, quest);
                    }
                    else 
                    {
                        String usage = "Usage: tt_failSpace <missionType> <missionName>. i.e. \"tt_failSpace assassinate npe_hard_main_3a\"";
                        debugConsoleMsg(self, usage);
                        debugSpeakMsg(self, usage);
                    }
                }
                else if (command.equalsIgnoreCase("tt_abortSpace"))
                {
                    debugConsoleMsg(self, "hit tt_abortSpace");
                    if (tok.hasMoreTokens())
                    {
                        String missionType = tok.nextToken();
                        String missionName = tok.nextToken();
                        obj_id quest = space_quest._getQuest(self, missionType, missionName);
                        space_quest.setQuestAborted(self, quest);
                    }
                    else 
                    {
                        String usage = "Usage: tt_abortSpace <missionType> <missionName>. i.e. \"tt_abortSpace assassinate npe_hard_main_3a\"";
                        debugConsoleMsg(self, usage);
                        debugSpeakMsg(self, usage);
                    }
                }
                else if (command.equalsIgnoreCase("tt_instantTravel"))
                {
                    String planet = getCurrentSceneName();
                    boolean result = enterClientTicketPurchaseMode(self, planet, "point1", true);
                    if (result)
                    {
                        debugSpeakMsg(self, "enterClientTicketPurchaseMode 1 succeeded");
                    }
                    else 
                    {
                        debugSpeakMsg(self, "enterClientTicketPurchaseMode 1 failed");
                    }
                }
                else if (command.equalsIgnoreCase("tt_purchaseTicket"))
                {
                    String planet = getCurrentSceneName();
                    boolean result = enterClientTicketPurchaseMode(self, planet, "point2", false);
                    if (result)
                    {
                        debugSpeakMsg(self, "enterClientTicketPurchaseMode 2 succeeded");
                    }
                    else 
                    {
                        debugSpeakMsg(self, "enterClientTicketPurchaseMode 2 failed");
                    }
                }
                else if (command.equalsIgnoreCase("tt_testpob"))
                {
                    LOG("tt_testpob", "getLocation() = " + getLocation(self));
                    LOG("tt_testpob", "getTopMostContainer() = " + getTopMostContainer(self));
                    LOG("tt_testpob", "getContainedBy() = " + getContainedBy(self));
                }
                else if (command.equalsIgnoreCase("tt_hyperspaceBlock"))
                {
                    int duration = -1;
                    if (tok.hasMoreTokens())
                    {
                        duration = utils.stringToInt(tok.nextToken());
                    }
                    space_utils.setHyperspaceBlock(self, duration, true);
                }
                else if (command.equalsIgnoreCase("tt_travelBlock"))
                {
                    if (utils.hasScriptVar(self, "travelBlock"))
                    {
                        LOG("TTYSON_LOG", "removing travelBlock");
                        utils.removeScriptVar(self, "travelBlock");
                    }
                    else 
                    {
                        int value = 0;
                        if (tok.hasMoreTokens())
                        {
                            value = Math.max(0, utils.stringToInt(tok.nextToken()));
                        }
                        LOG("TTYSON_LOG", "setting travelBlock to " + value);
                        utils.setScriptVar(self, "travelBlock", value);
                    }
                }
                else if (command.equalsIgnoreCase("tt_highlight"))
                {
                    newbieTutorialHighlightUIElement(self, "/GroundHUD.Toolbar.Toolbar.volume.1", 7.0f);
                }
                else if (command.equalsIgnoreCase("tt_openInventory"))
                {
                    final obj_id target = getIntendedTarget(self);
                    if (isValidId(target))
                    {
                        obj_id inv = utils.getInventoryContainer(target);
                        queueCommand(self, (1880585606), inv, "", COMMAND_PRIORITY_DEFAULT);
                    }
                }
                else if (command.equalsIgnoreCase("tt_openContainer"))
                {
                    obj_id container = obj_id.NULL_ID;
                    if (tok.hasMoreTokens())
                    {
                        container = utils.stringToObjId(tok.nextToken());
                    }
                    if (!isValidId(container))
                    {
                        container = getIntendedTarget(self);
                    }
                    if (!isValidId(container))
                    {
                        container = getLookAtTarget(self);
                    }
                    if (isValidId(container))
                    {
                        queueCommand(self, (1880585606), container, "", COMMAND_PRIORITY_DEFAULT);
                    }
                }
                else if (command.equalsIgnoreCase("tt_createStaticIn"))
                {
                    if (tok.hasMoreTokens())
                    {
                        obj_id container = obj_id.NULL_ID;
                        String staticItemName = tok.nextToken();
                        if (tok.hasMoreTokens())
                        {
                            container = utils.stringToObjId(tok.nextToken());
                        }
                        if (!isValidId(container))
                        {
                            container = getIntendedTarget(self);
                        }
                        if (!isValidId(container))
                        {
                            container = getLookAtTarget(self);
                        }
                        if (isValidId(container))
                        {
                            static_item.createNewItemFunction(staticItemName, container);
                        }
                    }
                    else 
                    {
                        String usage = "Usage: tt_createStaticIn <staticItemName> [container]";
                        debugConsoleMsg(self, usage);
                        debugSpeakMsg(self, usage);
                    }
                }
                else if (command.equalsIgnoreCase("tt_testDebugSpeak"))
                {
                    for (int i = 0; i < 10; ++i)
                    {
                        debugSpeakMsg(self, "debugSpeakMsg test " + i);
                    }
                }
                else if (command.equalsIgnoreCase("tt_makeVendor"))
                {
                    obj_id vendor = create.object("vendor", getLocation(self));
                    debugConsoleMsg(self, "vendor = " + vendor);
                    obj_id buyBox = create.object("object/tangible/test/buy_box.iff", utils.getInventoryContainer(vendor), false, false);
                    debugConsoleMsg(self, "buyBox = " + buyBox);
                    for (int i = 0; i < 10; ++i)
                    {
                        obj_id token = static_item.createNewItemFunction("item_heroic_token_axkva_01_01", buyBox);
                        attachScript(token, "test.buy_box");
                    }
                }
                else if (command.equalsIgnoreCase("tt_makeVendor2"))
                {
                    obj_id vendor = create.object("vendor", getLocation(self));
                    debugConsoleMsg(self, "vendor = " + vendor);
                    obj_id backpack = create.object("object/tangible/wearables/backpack/backpack_s01.iff", vendor, false, false);
                    debugConsoleMsg(self, "backpack = " + backpack);
                    obj_id buyBox = create.object("object/tangible/test/buy_box.iff", backpack, false, false);
                    debugConsoleMsg(self, "buyBox = " + buyBox);
                    for (int i = 0; i < 10; ++i)
                    {
                        obj_id token = static_item.createNewItemFunction("item_heroic_token_axkva_01_01", buyBox);
                        attachScript(token, "test.buy_box");
                    }
                }
                else if (command.equalsIgnoreCase("tt_turret"))
                {
                    obj_id turret1 = create.object("object/installation/turret/generic_turret_block_sm.iff", getLocation(self));
                    if (isIdValid(turret1))
                    {
                        setInvulnerable(turret1, false);
                        setObjVar(turret1, turret.OBJVAR_CAN_ATTACK, "allPlayers");
                    }
                }
                else if (command.equalsIgnoreCase("tt_clientEffectLoc"))
                {
                    final obj_id target = getIntendedTarget(self);
                    if (isValidId(target))
                    {
                        playClientEffectLoc(self, "clienteffect/bacta_bomb.cef", getLocation(target), 0);
                    }
                }
                else if (command.equalsIgnoreCase("tt_clientProjectile"))
                {
                    final obj_id target = getIntendedTarget(self);
                    if (isValidId(target))
                    {
                        createClientProjectile(self, "object/weapon/ranged/pistol/shared_pistol_dl44.iff", getLocation(self), getLocation(target), 30, 4, true, 255, 0, 0, 255);
                    }
                }
                else if (command.equalsIgnoreCase("tt_checkHouse"))
                {
                    final obj_id target = getIntendedTarget(self);
                    if (isValidId(target))
                    {
                        debugConsoleMsg(self, "canShowPackOption = " + player_structure.canShowPackOption(self, target));
                        if (!utils.checkConfigFlag("GameServer", "allowPlayersToPackAbandonedStructures"))
                        {
                            debugConsoleMsg(self, "allowPlayersToPackAbandonedStructures = false");
                        }
                        if (!player_structure.isAbandoned(target))
                        {
                            debugConsoleMsg(self, "isAbandoned = false");
                        }
                        if (utils.isFreeTrial(self))
                        {
                            debugConsoleMsg(self, "isFreeTrial = true");
                        }
                        if (!isCommoditiesServerAvailable())
                        {
                            debugConsoleMsg(self, "isCommoditiesServerAvailable = false");
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPurchaseTicketInstantTravel(obj_id self, obj_id player, String departPlanetName, String departTravelPointName, String arrivePlanetName, String arriveTravelPointName, boolean roundTrip) throws InterruptedException
    {
        debugSpeakMsg(self, "OnPurchaseTicketInstantTravel: entered");
        debugSpeakMsg(self, departPlanetName);
        debugSpeakMsg(self, departTravelPointName);
        debugSpeakMsg(self, arrivePlanetName);
        debugSpeakMsg(self, arriveTravelPointName);
        debugSpeakMsg(self, roundTrip ? "roundtrip" : "not roundtrip");
        return SCRIPT_CONTINUE;
    }
    public int OnPurchaseTicket(obj_id self, obj_id player, String departPlanetName, String departTravelPointName, String arrivePlanetName, String arriveTravelPointName, boolean roundTrip) throws InterruptedException
    {
        debugSpeakMsg(self, "OnPurchaseTicket: entered");
        debugSpeakMsg(self, departPlanetName);
        debugSpeakMsg(self, departTravelPointName);
        debugSpeakMsg(self, arrivePlanetName);
        debugSpeakMsg(self, arriveTravelPointName);
        debugSpeakMsg(self, roundTrip ? "roundtrip" : "not roundtrip");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.ttyson_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFailedStructurePackup(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "handleFailedStructurePackup()");
        debugConsoleMsg(self, "handleFailedStructurePackup()");
        return SCRIPT_CONTINUE;
    }
    public int testMessage(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "TestMessage messageHandler hit");
        java.util.Enumeration keys = params.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            String value = params.getString(key);
            debugConsoleMsg(self, key + " = " + value);
        }
        int pageId = params.getInt("pageId");
        if (pageId >= 0)
        {
            String questCrcVar = "ttyson_test.questCrc." + pageId;
            String taskIdVar = "ttyson_test.taskId." + pageId;
            String questCrc = utils.getStringScriptVar(self, questCrcVar);
            String taskId = utils.getStringScriptVar(self, taskIdVar);
            debugConsoleMsg(self, "questCrc = " + questCrc);
            debugConsoleMsg(self, "taskId = " + taskId);
            utils.removeScriptVar(self, questCrcVar);
            utils.removeScriptVar(self, taskId);
        }
        return SCRIPT_CONTINUE;
    }
    public int startPerform(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int stopPerform(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int logLocationHeading(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("ttyson_test", "Location = " + (getLocation(self)).toString());
        LOG("ttyson_test", "Heading = " + (getHeading(self)).toString());
        enableLogLocationHeading(self);
        return SCRIPT_CONTINUE;
    }
    public void enableLogLocationHeading(obj_id target) throws InterruptedException
    {
        messageTo(target, "logLocationHeading", null, 5, false);
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        groundquests.questOutputDebugLog("ttyson_test", "OnArrivedAtLocation", "Arrived at " + locationName);
        return SCRIPT_CONTINUE;
    }
    public void giveNewbieEquipment(obj_id player) throws InterruptedException
    {
        String profession = "all_professions";
        obj_id bank = utils.getInventoryContainer(player);
        if (!isIdValid(bank))
        {
            LOG("newbie", "BAD: cannot locate new player's bank");
            return;
        }
        if (!dataTableHasColumn(STARTING_EQUIPMENT_FILE, profession))
        {
            LOG("newbie", "BAD: cannot locate starting equip for profession: " + profession);
            return;
        }
        String[] items = dataTableGetStringColumnNoDefaults(STARTING_EQUIPMENT_FILE, profession);
        if (items == null)
        {
            LOG("newbie", "no items");
        }
        else if (items.length < 1)
        {
            LOG("newbie", "no items");
        }
        for (String item : items) {
            LOG("newbie", "NEWBIE STARTING EQUIP MAKING: " + item);
            obj_id newItem = null;
            if (item.startsWith("object/weapon/")) {
                newItem = weapons.createWeapon(item, bank, 0.75f);
            } else if (item.startsWith("object/tangible/medicine")) {
                newItem = createObject("object/tangible/medicine/instant_stimpack/stimpack_noob.iff", bank, "");
                if (isIdValid(newItem)) {
                    setCount(newItem, 5);
                    setObjVar(newItem, "healing.power", 250);
                }
            } else {
                newItem = createObject(item, bank, "");
            }
            if (!isIdValid(newItem)) {
                LOG("newbie", "BAD: could not create newbie equipment item " + item);
            } else {
                pclib.autoInsureItem(newItem);
            }
        }
    }
    public void giveNewbieSkills(obj_id player) throws InterruptedException
    {
        skill.grantSkillToPlayer(player, "combat_marksman_novice");
        skill.grantSkillToPlayer(player, "combat_brawler_novice");
        skill.grantSkillToPlayer(player, "science_medic_novice");
        skill.grantSkillToPlayer(player, "outdoors_scout_novice");
        skill.grantSkillToPlayer(player, "crafting_artisan_novice");
        skill.grantSkillToPlayer(player, "social_entertainer_novice");
    }
}
