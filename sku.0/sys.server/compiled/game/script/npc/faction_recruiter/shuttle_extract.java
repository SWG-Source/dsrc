package script.npc.faction_recruiter;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.prose;
import script.library.utils;

public class shuttle_extract extends script.base_script
{
    public shuttle_extract()
    {
    }
    public static final String SCRIPT_PLAYER_SHUTTLE_EXTRACT = "npc.faction_recruiter.player_shuttle_extract";
    public static final String VAR_FACTION = "faction_recruiter.faction";
    public static final String VAR_EXTRACTION_LOCATION = "faction_recruiter.extraction_location";
    public static final string_id SID_SHUTTLE_EXTRACT = new string_id("faction_recruiter", "order_shuttle_extract");
    public static final string_id SID_SHUTTLE_INITIATED = new string_id("faction_recruiter", "shuttle_initiated");
    public static final string_id SID_SHUTTLE_INITIATED_GROUP = new string_id("faction_recruiter", "shuttle_initiated_group");
    public static final string_id SID_NOT_OUTSIDE = new string_id("faction_recruiter", "not_outside");
    public static final string_id SID_EXTRACTION_POINT = new string_id("faction_recruiter", "shuttle_extraction_point");
    public static final string_id SID_ALREADY_REQUEST_SHUTTLE = new string_id("faction_recruiter", "already_request_shuttle");
    public static final int ARRIVAL_TIME = 60;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_SHUTTLE_EXTRACT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            int faction_id = pvpGetAlignedFaction(player);
            String faction = factions.getFactionNameByHashCode(faction_id);
            if (faction == null || pvpGetType(player) == PVPTYPE_NEUTRAL)
            {
                return SCRIPT_CONTINUE;
            }
            String order_faction = getStringObjVar(self, VAR_FACTION);
            if (order_faction == null)
            {
                LOG("LOG_CHANNEL", "shuttle_extract::OnObjectMenuSelect(USE) -- " + self + "'s faction is null.");
                return SCRIPT_CONTINUE;
            }
            if (order_faction.equals(faction))
            {
                if (hasObjVar(player, VAR_EXTRACTION_LOCATION))
                {
                    sendSystemMessage(self, SID_ALREADY_REQUEST_SHUTTLE);
                    return SCRIPT_CONTINUE;
                }
                location loc = getLocation(player);
                obj_id structure = getTopMostContainer(loc.cell);
                if (structure == null || structure == obj_id.NULL_ID)
                {
                    destroyObject(self);
                    if (!hasScript(player, SCRIPT_PLAYER_SHUTTLE_EXTRACT))
                    {
                        attachScript(player, SCRIPT_PLAYER_SHUTTLE_EXTRACT);
                    }
                    setObjVar(player, VAR_EXTRACTION_LOCATION, loc);
                    createExtractionWaypoint(player, loc);
                    prose_package pp = prose.getPackage(SID_SHUTTLE_INITIATED, ARRIVAL_TIME);
                    sendSystemMessageProse(player, pp);
                    obj_id group = getGroupObject(player);
                    if (group != null && group != obj_id.NULL_ID)
                    {
                        if (player == getGroupLeaderId(group))
                        {
                            obj_id[] group_members = getGroupMemberIds(group);
                            if (group_members == null)
                            {
                                LOG("LOG_CHANNEL", "shuttle_extract::OnObjectMenuSelect(Use) -- group_members is null for group " + group);
                                return SCRIPT_CONTINUE;
                            }
                            for (int i = 0; i < group_members.length; i++)
                            {
                                if (group_members[i] != player)
                                {
                                    createExtractionWaypoint(group_members[i], loc);
                                    pp = prose.getPackage(SID_SHUTTLE_INITIATED_GROUP, player, null, ARRIVAL_TIME);
                                    sendSystemMessageProse(group_members[i], pp);
                                }
                            }
                        }
                    }
                    messageTo(player, "msgExtractionShuttleArrival", null, ARRIVAL_TIME, false);
                }
                else 
                {
                    sendSystemMessage(player, SID_NOT_OUTSIDE);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean createExtractionWaypoint(obj_id player, location loc) throws InterruptedException
    {
        obj_id waypoint = createWaypointInDatapad(player, loc);
        if (!isIdValid(waypoint))
        {
            LOG("LOG_CHANNEL", "shuttle_extract::createExtractionWaypoint -- unable to create a waypoint for " + player);
            return false;
        }
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        setName(waypoint, SID_EXTRACTION_POINT);
        return true;
    }
}
