package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.money;
import script.library.utils;

public class c_newbie_mentor extends script.base_script
{
    public c_newbie_mentor()
    {
    }
    public static String c_stringFile = "conversation/c_newbie_mentor";
    public boolean c_newbie_mentor_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_newbie_mentor_condition_questActiveOrComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest1");
        return questIsQuestActive(questId1, player) || questIsQuestComplete(questId1, player);
    }
    public boolean c_newbie_mentor_condition_playerOnBankStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest1");
        int bankstep = groundquests.getTaskId(questId1, "bankstep");
        int bankbox1 = groundquests.getTaskId(questId1, "bankbox1");
        int bankbox2 = groundquests.getTaskId(questId1, "bankbox2");
        return questIsTaskActive(questId1, bankstep, player) || questIsTaskActive(questId1, bankbox1, player) || questIsTaskActive(questId1, bankbox2, player);
    }
    public boolean c_newbie_mentor_condition_playerDidNewTutorial(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_hall_jedi");
        return questIsQuestComplete(questId1, player);
    }
    public boolean c_newbie_mentor_condition_playerOnBankReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest1");
        int bankreturn = groundquests.getTaskId(questId1, "bankreturn");
        return questIsTaskActive(questId1, bankreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnBazaarStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest1");
        int bazaarstep = groundquests.getTaskId(questId1, "bazaarstep");
        int bazaarbox1 = groundquests.getTaskId(questId1, "bazaarbox1");
        int bazaarbox2 = groundquests.getTaskId(questId1, "bazaarbox2");
        return questIsTaskActive(questId1, bazaarstep, player) || questIsTaskActive(questId1, bazaarbox1, player) || questIsTaskActive(questId1, bazaarbox2, player);
    }
    public boolean c_newbie_mentor_condition_playerOnBazaarReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest1");
        int bazaarreturn = groundquests.getTaskId(questId1, "bazaarreturn");
        return questIsTaskActive(questId1, bazaarreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnCloneStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest1");
        int clonestep = groundquests.getTaskId(questId1, "clonestep");
        int clonebox1 = groundquests.getTaskId(questId1, "clonebox1");
        return questIsTaskActive(questId1, clonestep, player) || questIsTaskActive(questId1, clonebox1, player);
    }
    public boolean c_newbie_mentor_condition_playerOnCloneReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest1");
        int clonereturn = groundquests.getTaskId(questId1, "clonereturn");
        return questIsTaskActive(questId1, clonereturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnHealingLearned(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest2");
        int healinglearned = groundquests.getTaskId(questId1, "healinglearned");
        return questIsTaskActive(questId1, healinglearned, player);
    }
    public boolean c_newbie_mentor_condition_playerLearnDroid(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest4");
        int groupbox1 = groundquests.getTaskId(questId1, "groupbox1");
        int droidbox1 = groundquests.getTaskId(questId1, "droidbox1");
        return questIsTaskActive(questId1, groupbox1, player) || questIsTaskActive(questId1, droidbox1, player);
    }
    public boolean c_newbie_mentor_condition_playerOnKill(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest2");
        int kill = groundquests.getTaskId(questId1, "kill");
        int killstep = groundquests.getTaskId(questId1, "killstep");
        int combatbox1 = groundquests.getTaskId(questId1, "combatbox1");
        int combatbox2 = groundquests.getTaskId(questId1, "combatbox2");
        int combatbox3 = groundquests.getTaskId(questId1, "combatbox3");
        return questIsTaskActive(questId1, kill, player) || questIsTaskActive(questId1, killstep, player) || questIsTaskActive(questId1, combatbox1, player) || questIsTaskActive(questId1, combatbox2, player) || questIsTaskActive(questId1, combatbox3, player);
    }
    public boolean c_newbie_mentor_condition_playerOnKillReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest2");
        int killreturn = groundquests.getTaskId(questId1, "killreturn");
        return questIsTaskActive(questId1, killreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnHarvestStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest2");
        int harveststep1 = groundquests.getTaskId(questId1, "harveststep1");
        int harveststep2 = groundquests.getTaskId(questId1, "harveststep2");
        int harveststep3 = groundquests.getTaskId(questId1, "harveststep3");
        return questIsTaskActive(questId1, harveststep1, player) || questIsTaskActive(questId1, harveststep2, player) || questIsTaskActive(questId1, harveststep3, player);
    }
    public boolean c_newbie_mentor_condition_playerOnHarvestReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest2");
        int harvestreturn = groundquests.getTaskId(questId1, "harvestreturn");
        return questIsTaskActive(questId1, harvestreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnProfessionStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int professionstep = groundquests.getTaskId(questId1, "professionstep");
        int professionbox1 = groundquests.getTaskId(questId1, "professionbox1");
        int professionbox2 = groundquests.getTaskId(questId1, "professionbox2");
        int professionbox3 = groundquests.getTaskId(questId1, "professionbox3");
        return questIsTaskActive(questId1, professionstep, player) || questIsTaskActive(questId1, professionbox1, player) || questIsTaskActive(questId1, professionbox2, player) || questIsTaskActive(questId1, professionbox3, player);
    }
    public boolean c_newbie_mentor_condition_playerOnProfessionReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int professionreturn = groundquests.getTaskId(questId1, "professionreturn");
        return questIsTaskActive(questId1, professionreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnCantinaStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int cantinastep = groundquests.getTaskId(questId1, "cantinastep");
        int cantinabox1 = groundquests.getTaskId(questId1, "cantinabox1");
        return questIsTaskActive(questId1, cantinastep, player) || questIsTaskActive(questId1, cantinabox1, player);
    }
    public boolean c_newbie_mentor_condition_playerOnVehicleInfo(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest4");
        int vehiclebox1 = groundquests.getTaskId(questId1, "vehiclebox1");
        return questIsTaskActive(questId1, vehiclebox1, player);
    }
    public boolean c_newbie_mentor_condition_playerOnCantinaPerform(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int cantinaperform = groundquests.getTaskId(questId1, "cantinaperform");
        return questIsTaskActive(questId1, cantinaperform, player);
    }
    public boolean c_newbie_mentor_condition_playerOnMedCenterStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int medcenterstep = groundquests.getTaskId(questId1, "medcenterstep");
        int medbox1 = groundquests.getTaskId(questId1, "medbox1");
        return questIsTaskActive(questId1, medcenterstep, player) || questIsTaskActive(questId1, medbox1, player);
    }
    public boolean c_newbie_mentor_condition_playerOnMedCenterReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int medcenterreturn = groundquests.getTaskId(questId1, "medcenterreturn");
        return questIsTaskActive(questId1, medcenterreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnJunkStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int junkstep = groundquests.getTaskId(questId1, "junkstep");
        int junkbox1 = groundquests.getTaskId(questId1, "junkbox1");
        return questIsTaskActive(questId1, junkstep, player) || questIsTaskActive(questId1, junkbox1, player);
    }
    public boolean c_newbie_mentor_condition_playerOnJunkReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int junkreturn = groundquests.getTaskId(questId1, "junkreturn");
        return questIsTaskActive(questId1, junkreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnSurveyStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest4");
        int surveystep = groundquests.getTaskId(questId1, "surveystep");
        int surveybox1 = groundquests.getTaskId(questId1, "surveybox1");
        int surveybox2 = groundquests.getTaskId(questId1, "surveybox2");
        return questIsTaskActive(questId1, surveystep, player) || questIsTaskActive(questId1, surveybox1, player) || questIsTaskActive(questId1, surveybox2, player);
    }
    public boolean c_newbie_mentor_condition_playerOnSurveyReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest4");
        int surveyreturn = groundquests.getTaskId(questId1, "surveyreturn");
        return questIsTaskActive(questId1, surveyreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnCraftStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest4");
        int craftstep = groundquests.getTaskId(questId1, "craftstep");
        int craftreturn = groundquests.getTaskId(questId1, "craftreturn");
        return (questIsTaskActive(questId1, craftstep, player) || questIsTaskActive(questId1, craftreturn, player));
    }
    public boolean c_newbie_mentor_condition_playerOnLearnGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest4");
        int learngroup = groundquests.getTaskId(questId1, "learngroup");
        return questIsTaskActive(questId1, learngroup, player);
    }
    public boolean c_newbie_mentor_condition_playerOnShuttleStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest5");
        int shuttlestep1 = groundquests.getTaskId(questId1, "shuttlestep1");
        int shuttlestep2 = groundquests.getTaskId(questId1, "shuttlestep2");
        int shuttlestep3 = groundquests.getTaskId(questId1, "shuttlestep3");
        int shuttlestep4 = groundquests.getTaskId(questId1, "shuttlestep4");
        return questIsTaskActive(questId1, shuttlestep1, player) || questIsTaskActive(questId1, shuttlestep2, player) || questIsTaskActive(questId1, shuttlestep3, player) || questIsTaskActive(questId1, shuttlestep4, player);
    }
    public boolean c_newbie_mentor_condition_playerOnAssociateStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest5");
        int associatestep = groundquests.getTaskId(questId1, "associatestep");
        return questIsTaskActive(questId1, associatestep, player);
    }
    public boolean c_newbie_mentor_condition_playerOnLastStep(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest5");
        int laststep = groundquests.getTaskId(questId1, "laststep");
        return questIsTaskActive(questId1, laststep, player);
    }
    public boolean c_newbie_mentor_condition_playerOnCantinaReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest3");
        int cantinareturn = groundquests.getTaskId(questId1, "cantinareturn");
        return questIsTaskActive(questId1, cantinareturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnVehicleAcquired(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest4");
        int vehicleacquired = groundquests.getTaskId(questId1, "vehicleacquired");
        return questIsTaskActive(questId1, vehicleacquired, player);
    }
    public boolean c_newbie_mentor_condition_playerOnCraftReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest4");
        int craftreturn = groundquests.getTaskId(questId1, "craftreturn");
        return questIsTaskActive(questId1, craftreturn, player);
    }
    public boolean c_newbie_mentor_condition_playerOnHealingBox1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_newbie_quest2");
        int healingbox1 = groundquests.getTaskId(questId1, "healingbox1");
        return questIsTaskActive(questId1, healingbox1, player);
    }
    public void c_newbie_mentor_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_quest1");
        groundquests.grantQuest(questId, player, npc, true);
        detachScript(player, "theme_park.newbie_tutorial.new_player_ribbon");
        destroyClientPath(player);
    }
    public void c_newbie_mentor_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_newbie_mentor_action_signalBankReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_bankreturn");
    }
    public void c_newbie_mentor_action_signalBazaarReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_bazaarreturn");
    }
    public void c_newbie_mentor_action_signalCloneReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_quest2");
        groundquests.sendSignal(player, "c_newbie_clonereturn");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void c_newbie_mentor_action_signalHealingLearned(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_healinglearned");
    }
    public void c_newbie_mentor_action_signalKillReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_killreturn");
    }
    public void c_newbie_mentor_action_signalProfessionReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_professionreturn");
    }
    public void c_newbie_mentor_action_signalHarvestReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_quest3");
        groundquests.sendSignal(player, "c_newbie_harvestreturn");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void c_newbie_mentor_action_signalCantinaReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_cantinareturn");
        obj_id playerInventory = utils.getInventoryContainer(player);
        String templateName = "object/tangible/loot/simple_kit/viewscreen_reader.iff";
        createObject(templateName, playerInventory, "");
    }
    public void c_newbie_mentor_action_signalJunkReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_quest4");
        groundquests.sendSignal(player, "c_newbie_junkreturn");
        groundquests.grantQuest(questId, player, npc, true);
        obj_id playerInventory = utils.getInventoryContainer(player);
        String templateName = "object/tangible/deed/vehicle_deed/landspeeder_x31_deed.iff";
        createObject(templateName, playerInventory, "");
    }
    public void c_newbie_mentor_action_signalMedCenterReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_medcenterreturn");
        obj_id playerInventory = utils.getInventoryContainer(player);
        String templateName = "object/tangible/loot/tool/datapad_broken.iff";
        createObject(templateName, playerInventory, "");
    }
    public void c_newbie_mentor_action_signalVehicleAcquired(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_vehicleacquired");
    }
    public void c_newbie_mentor_action_signalCraftDone(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_craftdone");
    }
    public void c_newbie_mentor_action_signalLearnGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_quest5");
        groundquests.sendSignal(player, "c_newbie_learngroup");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void c_newbie_mentor_action_signalSurveyReturn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_surveyreturn");
    }
    public void c_newbie_mentor_action_signalLastStep(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_newbie_laststep");
    }
    public void c_newbie_mentor_action_giveWaypointImpTat(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -1119;
        loc.y = 0;
        loc.z = -3526;
        loc.area = "tatooine";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "Lt. Akal Colzet (Imperial Pilot Trainer)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveWaypointImpNab(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 5182;
        loc.y = -192;
        loc.z = 6750;
        loc.area = "naboo";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "Lt. Barn Sinkko (Imperial Pilot Trainer)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveWaypointImpTalus(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -2184;
        loc.y = 0;
        loc.z = 2273;
        loc.area = "talus";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "Hakasha Sireen (Imperial Pilot Trainer)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveWaypointRebTat(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -2991;
        loc.y = 5;
        loc.z = 2123;
        loc.area = "tatooine";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "Commander Da'la Socuna (Rebel Pilot Trainer)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveWaypointRebNab(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 4767;
        loc.y = 4;
        loc.z = -4812;
        loc.area = "naboo";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "V3-FX (Rebel Pilot Training Droid)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveWaypointRebCore(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -5170;
        loc.y = 21;
        loc.z = -2295;
        loc.area = "corellia";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "Captain Kreezo (Rebel Pilot Trainer)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveWaypointNeuTat(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = 3381;
        loc.y = 5;
        loc.z = -4799;
        loc.area = "tatooine";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "Dravis (Privateer Pilot Trainer)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveWaypointNeuNab(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -5495;
        loc.y = 14;
        loc.z = 4476;
        loc.area = "naboo";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "Captain Dinge (RSF Pilot Trainer)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveWaypointNeuCore(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -275;
        loc.y = 28;
        loc.z = -4695;
        loc.area = "corellia";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, "Sergeant Rhea (CorSec Pilot Trainer)");
        setWaypointActive(waypoint, true);
    }
    public void c_newbie_mentor_action_giveMayorQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_quest6");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public int c_newbie_mentor_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_305"))
        {
            doAnimationAction(player, "shrug_hands");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                string_id message = new string_id(c_stringFile, "s_307");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!c_newbie_mentor_condition_playerDidNewTutorial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition_playerDidNewTutorial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_309");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_317");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 2);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_309"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_311");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_313");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_317"))
        {
            doAnimationAction(player, "helpme");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_319");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_321");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_325");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_313"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_315");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_321");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_325");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_321"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_323");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_329");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_325"))
        {
            doAnimationAction(player, "pose_proudly");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_327");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_329");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_321"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_323");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_329");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_325"))
        {
            doAnimationAction(player, "pose_proudly");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_327");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_329");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_329"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_331");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_333");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_345");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_329"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_331");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_333");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_345");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_333"))
        {
            doAnimationAction(player, "nod");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_335");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_337");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_341");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_345"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_347");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_337"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_newbie_mentor_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_339");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_341"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                c_newbie_mentor_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_343");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_353"))
        {
            doAnimationAction(player, "nod");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_355");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_361");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_357"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_359");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_361");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_361"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalBankReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_363");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_365");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_361"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalBankReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_363");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_365");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_365"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_367");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_373"))
        {
            doAnimationAction(player, "shake_head_disgust");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_375");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_385");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_377"))
        {
            doAnimationAction(player, "nod");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_385");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_381"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_383");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_385");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_385"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_387");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_385"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_387");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_385"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_387");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_389"))
        {
            doAnimationAction(player, "taken_aback");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                string_id message = new string_id(c_stringFile, "s_391");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_393");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_393"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_395");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_397");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_397"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_newbie_mentor_action_signalBazaarReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_399");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_405"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_407");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_413");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_409"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_411");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_413");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_413"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_415");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_417");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_413"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_415");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_417");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_417"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_newbie_mentor_action_signalCloneReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_419");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_425"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_427");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_429");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_435"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_437");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_440");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_429"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalHealingLearned(player, npc);
                string_id message = new string_id(c_stringFile, "s_432");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_440"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                c_newbie_mentor_action_signalHealingLearned(player, npc);
                string_id message = new string_id(c_stringFile, "s_443");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_451"))
        {
            doAnimationAction(player, "nod");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalKillReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_453");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_456");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_461"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                c_newbie_mentor_action_signalKillReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_464");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_467");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_456"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_459");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_467"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_469");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_477"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_480");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_483");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_488"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_491");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_493");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_483"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalHarvestReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_485");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_493"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_newbie_mentor_action_signalHarvestReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_496");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_504"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_507");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_510");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_518"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_522");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_526");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_532");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_510"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_newbie_mentor_action_signalProfessionReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_514");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_526"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow2");
                c_newbie_mentor_action_signalProfessionReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_529");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_532"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shoo");
                c_newbie_mentor_action_signalProfessionReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_535");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_545"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_548");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_551");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_561");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_551"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_553");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_556");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_561"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_553");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_556");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_556"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalCantinaReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_559");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_569"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_572");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_575");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_585"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_588");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_591");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_575"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalMedCenterReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_577");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_580");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_580"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                string_id message = new string_id(c_stringFile, "s_583");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_591"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalMedCenterReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_593");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_596");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_596"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_599");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_606"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_608");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_611");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_616"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_619");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_621");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_611"))
        {
            doAnimationAction(player, "taken_aback");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_newbie_mentor_action_signalJunkReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_613");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_621"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_624");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_627");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_627"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                c_newbie_mentor_action_signalJunkReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_629");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_637"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalVehicleAcquired(player, npc);
                string_id message = new string_id(c_stringFile, "s_640");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_643");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_648"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalVehicleAcquired(player, npc);
                string_id message = new string_id(c_stringFile, "s_651");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_653");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_643"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_645");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_653"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_656");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_664"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_667");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_669");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_669"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_672");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_675");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_675"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalSurveyReturn(player, npc);
                string_id message = new string_id(c_stringFile, "s_677");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_683"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_685");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_688");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_699");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 92);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_709"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_712");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch92(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_688"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalCraftDone(player, npc);
                string_id message = new string_id(c_stringFile, "s_691");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_693");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 93);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_699"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalCraftDone(player, npc);
                string_id message = new string_id(c_stringFile, "s_701");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_704");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch93(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_693"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_696");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch95(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_704"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_707");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch99(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_721"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_724");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_727");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 100);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch100(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_727"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_signalLearnGroup(player, npc);
                string_id message = new string_id(c_stringFile, "s_731");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_734");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 101);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch101(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_734"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_737");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch105(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_749"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_751");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch106(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch107(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_759"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_762");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_764");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_787");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_819");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_851"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_855");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_859");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_891");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_923");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 121);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_955"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_959");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_963");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_995");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1027");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 134);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch108(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_764"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_766");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_768");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_772");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_779");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 109);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_787"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_791");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_795");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_803");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_811");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_819"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_823");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_835");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_843");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 117);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch109(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_768"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointImpTat(player, npc);
                string_id message = new string_id(c_stringFile, "s_770");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_772"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_775");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_764");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_787");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_819");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_779"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_783");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch110(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch111(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_764"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_766");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_768");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_772");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_779");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 109);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_787"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_791");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_795");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_803");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_811");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_819"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_823");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_835");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_843");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 117);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch112(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch113(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_795"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointImpNab(player, npc);
                string_id message = new string_id(c_stringFile, "s_799");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_803"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_807");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_764");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_787");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_819");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_811"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_815");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch114(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch115(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_764"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_766");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_768");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_772");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_779");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 109);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_787"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_791");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_795");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_803");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_811");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_819"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_823");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_835");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_843");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 117);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch116(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch117(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_827"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointImpTalus(player, npc);
                string_id message = new string_id(c_stringFile, "s_831");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_835"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_839");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_764");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_787");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_819");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_843"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_847");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch118(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch119(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_764"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_766");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_768");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_772");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_779");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 109);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_787"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_791");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_795");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_803");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_811");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_819"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_823");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_835");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_843");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 117);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch120(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch121(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_859"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_863");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_867");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_875");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_883");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 122);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_891"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_895");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_899");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_907");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_915");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 126);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_923"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_927");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_931");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_939");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_947");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 130);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch122(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_867"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointRebTat(player, npc);
                string_id message = new string_id(c_stringFile, "s_871");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_875"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_879");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_859");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_891");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_923");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 121);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_883"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_887");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch123(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch124(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_859"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_863");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_867");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_875");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_883");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 122);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_891"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_895");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_899");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_907");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_915");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 126);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_923"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_927");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_931");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_939");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_947");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 130);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch125(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch126(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_899"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointRebNab(player, npc);
                string_id message = new string_id(c_stringFile, "s_903");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_907"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_911");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_859");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_891");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_923");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 121);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_915"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_919");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch127(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch128(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_859"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_863");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_867");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_875");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_883");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 122);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_891"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_895");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_899");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_907");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_915");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 126);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_923"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_927");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_931");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_939");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_947");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 130);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch129(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch130(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_931"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointRebCore(player, npc);
                string_id message = new string_id(c_stringFile, "s_935");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_939"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_943");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_859");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_891");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_923");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 121);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_947"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_951");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch131(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch132(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_859"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_863");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_867");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_875");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_883");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 122);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_891"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_895");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_899");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_907");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_915");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 126);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_923"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_927");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_931");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_939");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_947");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 130);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch133(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch134(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_963"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_967");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_971");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_979");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_987");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 135);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_995"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_999");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1003");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1011");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1019");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 139);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1027"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1031");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1035");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1043");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1051");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 143);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch135(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_971"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointNeuTat(player, npc);
                string_id message = new string_id(c_stringFile, "s_975");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_979"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_983");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_963");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_995");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1027");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 134);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_987"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_991");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch136(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch137(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_963"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_967");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_971");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_979");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_987");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 135);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_995"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_999");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1003");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1011");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1019");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 139);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1027"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1031");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1035");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1043");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1051");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 143);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch138(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch139(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1003"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointNeuNab(player, npc);
                string_id message = new string_id(c_stringFile, "s_1007");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1011"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1015");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_963");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_995");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1027");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 134);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1019"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1023");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch140(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch141(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_963"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_967");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_971");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_979");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_987");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 135);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_995"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_999");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1003");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1011");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1019");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 139);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1027"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1031");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1035");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1043");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1051");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 143);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch142(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch143(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1035"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveWaypointNeuCore(player, npc);
                string_id message = new string_id(c_stringFile, "s_1039");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1043"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1047");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_963");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_995");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1027");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 134);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1051"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1055");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch144(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch145(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_963"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_967");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_971");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_979");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_987");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 135);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_995"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_999");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1003");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1011");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1019");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 139);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1027"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1031");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1035");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1043");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1051");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 143);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch146(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch147(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1067"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1071");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch148(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch149(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1083"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1087");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch150(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch151(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1099"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1103");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_754");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1107");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch152(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_754"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1059"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1107"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                c_newbie_mentor_action_signalLastStep(player, npc);
                string_id message = new string_id(c_stringFile, "s_1111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch153(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1115"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1123");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 154);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch154(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1123"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                c_newbie_mentor_action_giveMayorQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1127");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_mentor_handleBranch156(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1135"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_955");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1139"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1063");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1143"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1083");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1147"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1095");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_mentor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1099");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1150"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                c_newbie_mentor_action_giveMayorQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_1152");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1154"))
        {
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1156");
                utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.c_newbie_mentor");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Trehla Keelo");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Trehla Keelo");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.c_newbie_mentor");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!c_newbie_mentor_condition_questActiveOrComplete(player, npc))
        {
            doAnimationAction(npc, "greet");
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_303");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_305");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 1);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnBankStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_349");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnBankReturn(player, npc))
        {
            doAnimationAction(npc, "greet");
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_351");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_353");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_357");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 14);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnBazaarStep(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_369");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnBazaarReturn(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_371");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_373");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_377");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_381");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 20);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnCloneStep(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_401");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnCloneReturn(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_403");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_405");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_409");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 29);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnHealingBox1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_421");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnHealingLearned(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_423");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_425");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_435");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 35);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnKill(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_445");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnKillReturn(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_448");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_451");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_461");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 41);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnHarvestStep(player, npc))
        {
            doAnimationAction(npc, "point_away");
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_472");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnHarvestReturn(player, npc))
        {
            doAnimationAction(npc, "nod");
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_475");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_477");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_488");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 47);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnProfessionStep(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_499");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnProfessionReturn(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_501");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_504");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_518");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 53);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnCantinaStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_537");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnCantinaPerform(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_540");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnCantinaReturn(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_543");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_545");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 61);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnMedCenterStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_564");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnMedCenterReturn(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_567");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_569");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_585");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 66);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnJunkStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_601");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnJunkReturn(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_604");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_606");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_616");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 74);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnVehicleInfo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_632");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnVehicleAcquired(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_635");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_637");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_648");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 81);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnSurveyStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_659");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnSurveyReturn(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_661");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_664");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 87);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnCraftStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_680");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition_playerOnCraftReturn(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_683");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_709");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 91);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerLearnDroid(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_715");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnLearnGroup(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_718");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_721");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 99);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnShuttleStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_740");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnAssociateStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_743");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition_playerOnLastStep(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_746");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_749");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 105);
                npcStartConversation(player, npc, "c_newbie_mentor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_mentor_condition__defaultCondition(player, npc))
        {
            c_newbie_mentor_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1131");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (c_newbie_mentor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1135");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1139");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1143");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1147");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1150");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1154");
                }
                utils.setScriptVar(player, "conversation.c_newbie_mentor.branchId", 156);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "c_newbie_mentor", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_newbie_mentor"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_newbie_mentor.branchId");
        if (branchId == 1 && c_newbie_mentor_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && c_newbie_mentor_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_newbie_mentor_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_newbie_mentor_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && c_newbie_mentor_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && c_newbie_mentor_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_newbie_mentor_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && c_newbie_mentor_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_newbie_mentor_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && c_newbie_mentor_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && c_newbie_mentor_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && c_newbie_mentor_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && c_newbie_mentor_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && c_newbie_mentor_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && c_newbie_mentor_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && c_newbie_mentor_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && c_newbie_mentor_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && c_newbie_mentor_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && c_newbie_mentor_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && c_newbie_mentor_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && c_newbie_mentor_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && c_newbie_mentor_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && c_newbie_mentor_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && c_newbie_mentor_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && c_newbie_mentor_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && c_newbie_mentor_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && c_newbie_mentor_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && c_newbie_mentor_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && c_newbie_mentor_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && c_newbie_mentor_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && c_newbie_mentor_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && c_newbie_mentor_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && c_newbie_mentor_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && c_newbie_mentor_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && c_newbie_mentor_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && c_newbie_mentor_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && c_newbie_mentor_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && c_newbie_mentor_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && c_newbie_mentor_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && c_newbie_mentor_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && c_newbie_mentor_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && c_newbie_mentor_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && c_newbie_mentor_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && c_newbie_mentor_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && c_newbie_mentor_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && c_newbie_mentor_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && c_newbie_mentor_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && c_newbie_mentor_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && c_newbie_mentor_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && c_newbie_mentor_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && c_newbie_mentor_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && c_newbie_mentor_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && c_newbie_mentor_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && c_newbie_mentor_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && c_newbie_mentor_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 92 && c_newbie_mentor_handleBranch92(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 93 && c_newbie_mentor_handleBranch93(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 95 && c_newbie_mentor_handleBranch95(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 99 && c_newbie_mentor_handleBranch99(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 100 && c_newbie_mentor_handleBranch100(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 101 && c_newbie_mentor_handleBranch101(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 105 && c_newbie_mentor_handleBranch105(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 106 && c_newbie_mentor_handleBranch106(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 107 && c_newbie_mentor_handleBranch107(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 108 && c_newbie_mentor_handleBranch108(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 109 && c_newbie_mentor_handleBranch109(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 110 && c_newbie_mentor_handleBranch110(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 111 && c_newbie_mentor_handleBranch111(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 112 && c_newbie_mentor_handleBranch112(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 113 && c_newbie_mentor_handleBranch113(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 114 && c_newbie_mentor_handleBranch114(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 115 && c_newbie_mentor_handleBranch115(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 116 && c_newbie_mentor_handleBranch116(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 117 && c_newbie_mentor_handleBranch117(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 118 && c_newbie_mentor_handleBranch118(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 119 && c_newbie_mentor_handleBranch119(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 120 && c_newbie_mentor_handleBranch120(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 121 && c_newbie_mentor_handleBranch121(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 122 && c_newbie_mentor_handleBranch122(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 123 && c_newbie_mentor_handleBranch123(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 124 && c_newbie_mentor_handleBranch124(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 125 && c_newbie_mentor_handleBranch125(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 126 && c_newbie_mentor_handleBranch126(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 127 && c_newbie_mentor_handleBranch127(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 128 && c_newbie_mentor_handleBranch128(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 129 && c_newbie_mentor_handleBranch129(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 130 && c_newbie_mentor_handleBranch130(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 131 && c_newbie_mentor_handleBranch131(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 132 && c_newbie_mentor_handleBranch132(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 133 && c_newbie_mentor_handleBranch133(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 134 && c_newbie_mentor_handleBranch134(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 135 && c_newbie_mentor_handleBranch135(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 136 && c_newbie_mentor_handleBranch136(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 137 && c_newbie_mentor_handleBranch137(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 138 && c_newbie_mentor_handleBranch138(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 139 && c_newbie_mentor_handleBranch139(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 140 && c_newbie_mentor_handleBranch140(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 141 && c_newbie_mentor_handleBranch141(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 142 && c_newbie_mentor_handleBranch142(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 143 && c_newbie_mentor_handleBranch143(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 144 && c_newbie_mentor_handleBranch144(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 145 && c_newbie_mentor_handleBranch145(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 146 && c_newbie_mentor_handleBranch146(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 147 && c_newbie_mentor_handleBranch147(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 148 && c_newbie_mentor_handleBranch148(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 149 && c_newbie_mentor_handleBranch149(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 150 && c_newbie_mentor_handleBranch150(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 151 && c_newbie_mentor_handleBranch151(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 152 && c_newbie_mentor_handleBranch152(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 153 && c_newbie_mentor_handleBranch153(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 154 && c_newbie_mentor_handleBranch154(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 156 && c_newbie_mentor_handleBranch156(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_newbie_mentor.branchId");
        return SCRIPT_CONTINUE;
    }
}
