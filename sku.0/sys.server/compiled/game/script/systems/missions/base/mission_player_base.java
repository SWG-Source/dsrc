package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class mission_player_base extends script.systems.missions.base.mission_dynamic_base
{
    public mission_player_base()
    {
    }
    public void playerDestructionIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        debugSpeakMsg(objPlayer, "MISSION IS INCOMPLETE!");
        messageTo(objMission, "destructionIncomplete", dctParams, 0, true);
        return;
    }
    public void playerDestructionFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        debugSpeakMsg(objPlayer, "MISSION IS FAILED!");
        messageTo(objMission, "destructionFail", dctParams, 0, true);
        return;
    }
    public void playerDestructionSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        messageTo(objMission, "destructionSuccess", dctParams, 0, true);
        return;
    }
    public void playerDeliverSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        debugSpeakMsg(objPlayer, "MISSION IS SUCCESFUL!");
        messageTo(objMission, "deliverSuccess", dctParams, 0, true);
        return;
    }
    public void playerDeliverFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        debugSpeakMsg(objPlayer, "MISSION IS FAILED!!");
        messageTo(objMission, "deliverFail", dctParams, 0, true);
        return;
    }
    public void playerDeliverIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        debugSpeakMsg(objPlayer, "MISSION IS Incomplete!");
        messageTo(objMission, "deliverIncomplete", dctParams, 0, true);
        return;
    }
    public void playerFetchSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        debugSpeakMsg(objPlayer, "MISSION IS SUCCESFUL!");
        messageTo(objMission, "fetchSuccess", dctParams, 0, true);
        return;
    }
    public void playerFetchFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        debugSpeakMsg(objPlayer, "MISSION IS FAILED!!");
        messageTo(objMission, "fetchFail", dctParams, 0, true);
        return;
    }
    public void playerFetchIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        obj_id objPlayer = getMissionHolder(objMission);
        debugSpeakMsg(objPlayer, "MISSION IS Incomplete!");
        messageTo(objMission, "fetchIncomplete", dctParams, 0, true);
        return;
    }
    public void returnAllCredits(obj_id objMission) throws InterruptedException
    {
        return;
    }
    public void setupDeliverObjects(obj_id objMission) throws InterruptedException
    {
        return;
    }
    public void cleanupDeliverObjects(obj_id objMission) throws InterruptedException
    {
        return;
    }
}
