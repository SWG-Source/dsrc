package script.theme_park.nym;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.quests;

public class player extends script.base_script
{
    public player()
    {
    }
    public static final String msg = "theme_park_nym/messages";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int finishDroidQuest(obj_id self, dictionary params) throws InterruptedException
    {
        string_id droidMessage = new string_id(msg, "acquired_memory");
        sendSystemMessage(self, droidMessage);
        setObjVar(self, "nym.kole.droid", 1);
        return SCRIPT_CONTINUE;
    }
    public int finishGasQuest(obj_id self, dictionary params) throws InterruptedException
    {
        string_id gasMessage = new string_id(msg, "acquired_gas");
        sendSystemMessage(self, gasMessage);
        setObjVar(self, "nym.jinkins.gotgas", 1);
        return SCRIPT_CONTINUE;
    }
    public int gotImggcu(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "nym.nym.imggcu", 1);
        if (hasObjVar(self, "nym.nym.harddrive"))
        {
            string_id haveAll = new string_id(msg, "all_nym_needed");
            sendSystemMessage(self, haveAll);
        }
        else 
        {
            debugSpeakMsg(self, "GOT IMGGCU");
            string_id imggcu = new string_id(msg, "acquired_imggcu");
            sendSystemMessage(self, imggcu);
        }
        return SCRIPT_CONTINUE;
    }
    public int gotHarddrive(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "nym.nym.harddrive", 1);
        if (hasObjVar(self, "nym.nym.imggcu"))
        {
            string_id haveAll = new string_id(msg, "all_nym_needed");
            sendSystemMessage(self, haveAll);
        }
        else 
        {
            string_id hardDrive = new string_id(msg, "acquired_hard_drive");
            sendSystemMessage(self, hardDrive);
        }
        return SCRIPT_CONTINUE;
    }
    public int goSeeGuy(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id waypointToGuy = getObjIdObjVar(self, "nym.jinkins.guypoint");
        if (waypointToGuy == null)
        {
            location guy = new location(540.4f, 12f, 5091.18f, "lok", null);
            String questID = "jinkinsQuest";
            String target = "The Guy";
            String CONVO = "convo";
            String entry = "entry";
            obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", guy, 2, target, CONVO, entry);
            setObjVar(self, "nym.jinkins.guypoint", waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int goToCave(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id waypointToCave = getObjIdObjVar(self, "nym.jinkins.cavepoint");
        if (waypointToCave == null)
        {
            location cave = new location(-3027, 0, -681, "lok", null);
            String questID = "jinkinsQuest";
            String target = "Droid Cave";
            String CONVO = "convo2";
            String entry = "entry2";
            obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", cave, 2, target, CONVO, entry);
            setObjVar(self, "nym.jinkins.cavepoint", waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int goSeeImperial(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id waypointToGuy = getObjIdObjVar(self, "nym.kole.officerPoint");
        if (waypointToGuy == null)
        {
            location guy = new location(469.3f, 12f, 5020.83f, "lok", null);
            String questID = "koleQuest";
            String target = "Target Officer";
            String CONVO = "convoOfficer";
            String entry = "entryOfficer";
            obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", guy, 2, target, CONVO, entry);
            setObjVar(self, "nym.kole.officerPoint", waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int goGetGas(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id waypointToCave = getObjIdObjVar(self, "nym.kole.gasPoint");
        if (waypointToCave == null)
        {
            location cave = new location(6467, 0, 3885, "lok", null);
            String questID = "koleQuest";
            String target = "Target Gas";
            String CONVO = "convo Gas";
            String entry = "entry Gas";
            obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", cave, 2, target, CONVO, entry);
            setObjVar(self, "nym.kole.gasPoint", waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnWaypointDestroyed(obj_id self, obj_id waypoint) throws InterruptedException
    {
        obj_id kolecave = getObjIdObjVar(self, "nym.kole.cavepoint");
        obj_id koleguy = getObjIdObjVar(self, "nym.kole.guypoint");
        obj_id jinkinsgas = getObjIdObjVar(self, "nym.jinkins.gasPoint");
        obj_id jinkinsguy = getObjIdObjVar(self, "nym.jinkins.officerPoint");
        obj_id nymguy = getObjIdObjVar(self, "nym.nym.retiredGuy");
        obj_id nymlab = getObjIdObjVar(self, "nym.nym.researchlab");
        if (waypoint == kolecave)
        {
            removeObjVar(self, "nym.kole.cavepoint");
        }
        if (waypoint == koleguy)
        {
            removeObjVar(self, "nym.kole.guypoint");
        }
        if (waypoint == jinkinsgas)
        {
            removeObjVar(self, "nym.jinkins.gasPoint");
        }
        if (waypoint == jinkinsguy)
        {
            removeObjVar(self, "nym.jinkins.officerPoint");
        }
        if (waypoint == nymlab)
        {
            removeObjVar(self, "nym.nym.researchlab");
        }
        if (waypoint == nymguy)
        {
            removeObjVar(self, "nym.nym.retiredGuy");
        }
        return SCRIPT_CONTINUE;
    }
    public int goSeeRetired(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id waypointToGuy = getObjIdObjVar(self, "nym.nym.retiredGuy");
        if (waypointToGuy == null)
        {
            location guy = new location(540.4f, 12f, 5091.18f, "lok", null);
            String questID = "nymQuest";
            String target = "Retired Imperial Officer";
            String CONVO = "convoOfficer";
            String entry = "entryOfficer";
            obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", guy, 2, target, CONVO, entry);
            setObjVar(self, "nym.nym.retiredGuy", waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int goResearchLab(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id waypointToGuy = getObjIdObjVar(self, "nym.nym.researchlab");
        if (waypointToGuy == null)
        {
            location bunker = new location(902, 0, -4193, "lok", null);
            String questID = "nymQuest";
            String target = "Research Lab";
            String CONVO = "convoOfficer";
            String entry = "entryOfficer";
            obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", bunker, 2, target, CONVO, entry);
            setObjVar(self, "nym.nym.researchlab", waypoint);
        }
        return SCRIPT_CONTINUE;
    }
}
