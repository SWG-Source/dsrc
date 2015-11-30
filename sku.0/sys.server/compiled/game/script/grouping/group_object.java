package script.grouping;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.Hashtable;
import java.util.Set;
import java.util.Iterator;

public class group_object extends script.base_script
{
    public group_object()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int volleyTargetDone(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] members = getGroupMemberIds(self);
        if (members == null || members.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < members.length; i++)
        {
            messageTo(members[i], "volleyTargetDone", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int addGroupMember(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id newGroupMember = params.getObjId("memberObjectId");
            if (isIdValid(newGroupMember))
            {
                deltadictionary scriptVars = self.getScriptVars();
                if (scriptVars != null)
                {
                    selectNearestGroupMission(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int removeGroupMember(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id groupMember = params.getObjId("sender");
            if (isIdValid(groupMember))
            {
                deltadictionary scriptVars = self.getScriptVars();
                if (scriptVars != null)
                {
                    selectNearestGroupMission(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public location calculateNearestGroupMission(obj_id self) throws InterruptedException
    {
        location result = new location();
        deltadictionary scriptVars = self.getScriptVars();
        if (scriptVars != null)
        {
            location[] memberLocations = scriptVars.getLocationArray("memberLocations");
            if ((memberLocations == null) || (memberLocations.length < 1))
            {
                return null;
            }
            location[] missionLocations = scriptVars.getLocationArray("missionLocations");
            if ((missionLocations == null) || (missionLocations.length < 1))
            {
                return null;
            }
            location nearestGroupMission = scriptVars.getLocation("nearestGroupMission");
            if (nearestGroupMission == null)
            {
                if (missionLocations.length > 0)
                {
                    nearestGroupMission = (location)missionLocations[0].clone();
                }
                else 
                {
                    return null;
                }
            }
            Hashtable bestLocations = new Hashtable();
            int memberLocationIndex = 0;
            int preferredIndex = -1;
            for (memberLocationIndex = 0; memberLocationIndex < memberLocations.length; ++memberLocationIndex)
            {
                int missionLocationIndex = 0;
                float bestDistance = -1;
                for (missionLocationIndex = 0; missionLocationIndex < missionLocations.length; ++missionLocationIndex)
                {
                    location currentLocation = (location)missionLocations[missionLocationIndex].clone();
                    if (currentLocation.area.equals(memberLocations[memberLocationIndex].area))
                    {
                        float currentDistance = currentLocation.distance(memberLocations[memberLocationIndex]);
                        if (bestDistance < 0 || currentDistance < bestDistance)
                        {
                            preferredIndex = missionLocationIndex;
                            bestDistance = currentDistance;
                        }
                    }
                }
                if (preferredIndex > -1)
                {
                    Integer p = new Integer(preferredIndex);
                    if (bestLocations.containsKey(new Integer(preferredIndex)))
                    {
                        int votes = (((Integer)bestLocations.get(p))).intValue();
                        votes = votes + 1;
                        bestLocations.put(p, new Integer(votes));
                    }
                    else 
                    {
                        Integer votes = new Integer(1);
                        bestLocations.put(p, votes);
                    }
                }
            }
            Set keySet = bestLocations.keySet();
            Iterator votesIterator = keySet.iterator();
            int mostVotes = 0;
            while (votesIterator.hasNext())
            {
                Integer locationIndex = (Integer)(votesIterator.next());
                int votes = (((Integer)bestLocations.get(locationIndex))).intValue();
                if (votes > mostVotes)
                {
                    nearestGroupMission = (location)missionLocations[locationIndex.intValue()].clone();
                    mostVotes = votes;
                }
            }
            result = (location)nearestGroupMission.clone();
            scriptVars.put("nearestGroupMission", nearestGroupMission);
        }
        else 
        {
            result = null;
        }
        return result;
    }
    public int missionLocationResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params != null) && (params.containsKey("requestMissionLocationsNumber")))
        {
            deltadictionary scriptVars = self.getScriptVars();
            if ((scriptVars != null) && (scriptVars.hasKey("requestMissionLocationsNumber")) && (params.getInt("requestMissionLocationsNumber") == scriptVars.getInt("requestMissionLocationsNumber")))
            {
                obj_id missionHolder = params.getObjId("sender");
                if (isIdValid(missionHolder))
                {
                    location senderLocation = params.getLocation("senderLocation");
                    if (senderLocation != null)
                    {
                        Vector memberLocations = scriptVars.getResizeableLocationArray("memberLocations");
                        if (memberLocations == null)
                        {
                            memberLocations = new Vector();
                        }
                        memberLocations.add(senderLocation);
                        scriptVars.put("memberLocations", memberLocations);
                        location[] missionLocation = params.getLocationArray("missionLocation");
                        if ((missionLocation != null) && (missionLocation.length > 0))
                        {
                            Vector missionLocations = scriptVars.getResizeableLocationArray("missionLocations");
                            if (missionLocations == null)
                            {
                                missionLocations = new Vector();
                            }
                            for (int i = 0; i < missionLocation.length; ++i)
                            {
                                missionLocations.add(missionLocation[i]);
                            }
                            scriptVars.put("missionLocations", missionLocations);
                            location nearestGroupMission = scriptVars.getLocation("nearestGroupMission");
                            if (nearestGroupMission == null)
                            {
                                nearestGroupMission = (location)missionLocation[0].clone();
                                scriptVars.put("nearestGroupMission", nearestGroupMission);
                            }
                        }
                        if (memberLocations.size() >= getPCGroupSize(self))
                        {
                            tellMembersAboutNearestGroupMission(self, calculateNearestGroupMission(self));
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void tellMembersAboutNearestGroupMission(obj_id self, location nearestGroupMission) throws InterruptedException
    {
        if (nearestGroupMission != null)
        {
            deltadictionary scriptVars = self.getScriptVars();
            if (scriptVars != null)
            {
                obj_id[] members = getGroupMemberIds(self);
                int memberIndex = 0;
                dictionary msgData = new dictionary();
                msgData.put("waypointLocation", nearestGroupMission);
                int sequence = scriptVars.getInt("updateSequence");
                sequence++;
                scriptVars.put("updateSequence", sequence);
                msgData.put("updateSequence", sequence);
                for (memberIndex = 0; memberIndex < members.length; ++memberIndex)
                {
                    messageTo(members[memberIndex], "updateGroupWaypoint", msgData, 0, false);
                }
            }
        }
    }
    public void selectNearestGroupMission(obj_id self) throws InterruptedException
    {
        deltadictionary scriptVars = self.getScriptVars();
        if (scriptVars != null)
        {
            scriptVars.remove("memberLocations");
            scriptVars.remove("missionLocations");
            scriptVars.remove("nearestGroupMission");
            obj_id[] members = getGroupMemberIds(self);
            if (members != null)
            {
                int requestMissionLocationsNumber = 1;
                if (scriptVars.hasKey("requestMissionLocationsNumber"))
                {
                    requestMissionLocationsNumber = scriptVars.getInt("requestMissionLocationsNumber");
                    ++requestMissionLocationsNumber;
                }
                scriptVars.put("requestMissionLocationsNumber", requestMissionLocationsNumber);
                dictionary requestData = new dictionary();
                requestData.put("requestMissionLocationsNumber", requestMissionLocationsNumber);
                int memberIndex = 0;
                for (memberIndex = 0; memberIndex < members.length; ++memberIndex)
                {
                    messageTo(members[memberIndex], "requestMissionLocations", requestData, 0, false);
                }
            }
        }
    }
    public int recaclulateNearestGroupWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        selectNearestGroupMission(self);
        return SCRIPT_CONTINUE;
    }
    public int removeMissionLocation(obj_id self, dictionary params) throws InterruptedException
    {
        selectNearestGroupMission(self);
        return SCRIPT_CONTINUE;
    }
}
