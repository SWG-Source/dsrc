package script.space.quest_logic;

import script.*;
import script.library.*;

public class escort_target extends script.base_script
{
    public escort_target()
    {
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        dictionary outparams = new dictionary();
        outparams.put("ship", self);
        outparams.put("reason", 1);
        space_utils.notifyObject(quest, "escortFailed", outparams);
        return SCRIPT_CONTINUE;
    }
    public int registerDestination(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id quest = params.getObjId("quest");
        location loc = params.getLocation("loc");
        addLocationTarget3d("dest", loc, 300);
        setObjVar(self, "quest", quest);
        int noDistancePolling = getIntObjVar(quest, "noDistancePolling");
        if (noDistancePolling == 1)
        {
            return SCRIPT_CONTINUE;
        }
        int customMsgs = getIntObjVar(quest, "customDistanceMessages");
        if (customMsgs == 1)
        {
            setObjVar(self, "customMsgs", 1);
        }
        obj_id player = params.getObjId("player");
        setObjVar(self, "player", player);
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        messageTo(self, "checkPlayerLocation", outparams, 60.f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (name.equals("dest"))
        {
            dictionary params = new dictionary();
            params.put("ship", self);
            space_utils.notifyObject(quest, "escortComplete", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkPlayerLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id quest = getObjIdObjVar(self, "quest");
        if(!isIdValid(quest) || !exists(quest) || quest == null || quest == obj_id.NULL_ID){
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
        String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
        if (!exists(player))
        {
            dictionary outparams = new dictionary();
            outparams.put("ship", self);
            outparams.put("reason", 0);
            messageTo(quest, "escortFailed", outparams, 0.f, true);
            destroyObjectHyperspace(self);
            return SCRIPT_CONTINUE;
        }
        boolean closeenough = false;
        if (group.isGrouped(player))
        {
            obj_id gid = getGroupObject(player);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null)
            {
                for (obj_id member : members) {
                    float dist = getDistance(space_transition.getContainingShip(member), self);
                    closeenough = Math.abs(dist) <= space_quest.ESCORT_ROAM_TOLERANCE;
                    if (closeenough) {
                        break;
                    }
                }
            }
        }
        else 
        {
            float dist = getDistance(space_transition.getContainingShip(player), self);
            closeenough = Math.abs(dist) <= space_quest.ESCORT_ROAM_TOLERANCE;
        }
        if (!closeenough)
        {
            space_utils.matchEngineSpeed(player, self, .2f, true);
            int too_far = getIntObjVar(self, "too_far");
            switch (too_far)
            {
                case 0:
                string_id warning;
                if (hasObjVar(self, "customMsgs"))
                {
                    warning = new string_id("spacequest/" + questType + "/" + questName, "escort_too_far1");
                }
                else 
                {
                    warning = new string_id("space/quest", "escort_too_far1");
                }
                prose_package pp = prose.getPackage(warning, 0);
                space_quest.groupTaunt(self, player, pp);
                setObjVar(self, "too_far", 1);
                break;
                case 1:
                if (hasObjVar(self, "customMsgs"))
                {
                    warning = new string_id("spacequest/" + questType + "/" + questName, "escort_too_far2");
                }
                else 
                {
                    warning = new string_id("space/quest", "escort_too_far2");
                }
                pp = prose.getPackage(warning, 0);
                space_quest.groupTaunt(self, player, pp);
                setObjVar(self, "too_far", 2);
                break;
                case 2:
                if (hasObjVar(self, "customMsgs"))
                {
                    warning = new string_id("spacequest/" + questType + "/" + questName, "escort_too_far3");
                }
                else 
                {
                    warning = new string_id("space/quest", "escort_too_far3");
                }
                pp = prose.getPackage(warning, 0);
                space_quest.groupTaunt(self, player, pp);
                dictionary outparams = new dictionary();
                outparams.put("ship", self);
                outparams.put("reason", 0);
                space_utils.notifyObject(quest, "escortFailed", outparams);
                destroyObjectHyperspace(self);
                break;
            }
        }
        else 
        {
            space_utils.matchEngineSpeed(player, self, .5f, true);
            removeObjVar(self, "too_far");
        }
        messageTo(self, "checkPlayerLocation", params, 60.f, false);
        return SCRIPT_CONTINUE;
    }
    public int missionAbort(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int OnShieldsDepleted(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "shieldsComplain"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "shieldsComplain", 1);
        obj_id quest = getObjIdObjVar(self, "quest");
        String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
        String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(quest, space_quest.QUEST_OWNER);
        string_id warning = new string_id("spacequest/" + questType + "/" + questName, "shields_depleted");
        prose_package pp = prose.getPackage(warning, 0);
        space_quest.groupTaunt(self, player, pp);
        return SCRIPT_CONTINUE;
    }
    public int OnHullNearlyDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "hullComplain"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "hullComplain", 1);
        obj_id quest = getObjIdObjVar(self, "quest");
        String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
        String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(quest, space_quest.QUEST_OWNER);
        string_id warning = new string_id("spacequest/" + questType + "/" + questName, "hull_half");
        prose_package pp = prose.getPackage(warning, 0);
        space_quest.groupTaunt(self, player, pp);
        return SCRIPT_CONTINUE;
    }
}
