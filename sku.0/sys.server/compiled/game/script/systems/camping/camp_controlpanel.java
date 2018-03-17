package script.systems.camping;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.camping;
import script.library.factions;
import script.library.faction_perk;
import script.library.prose;
import script.library.utils;
import script.library.sui;
import script.library.player_structure;
import script.library.structure;
import script.library.group;

public class camp_controlpanel extends script.base_script
{
    public camp_controlpanel()
    {
    }
    public static final float FIELD_COST_MULTIPLER = 1.15f;
    public static final string_id SID_MNU_DISBAND = new string_id("camp", "mnu_disband");
    public static final string_id SID_MNU_STATUS = new string_id("camp", "mnu_status");
    public static final string_id SID_MNU_WAYPOINT = new string_id("camp", "mnu_waypoint");
    public static final string_id SID_MNU_ASSUME_OWNERSHIP = new string_id("camp", "mnu_assume_ownership");
    public static final string_id SID_NO_GROUP = new string_id("camp", "waypoint_no_group");
    public static final string_id SID_CAMP_UP = new string_id("camp", "waypoint_camp_up");
    public static final string_id SID_WAYPOINT_SENT = new string_id("camp", "waypoint_sent");
    public static final string_id SID_SELECT_SUB_MENU = new string_id("camp", "select_sub_menu");
    public static final string_id SID_MNU_REQUISITION = new string_id("camp", "mnu_requisition");
    public static final string_id SID_MNU_REQUISITION_WPN = new string_id("camp", "mnu_requisition_wpn");
    public static final string_id SID_MNU_REQUISITION_INSTALLATION = new string_id("camp", "mnu_requisition_installation");
    public static final string_id SID_SUI_FIELD_REQ_TITLE = new string_id("camp", "sui_field_req_title");
    public static final string_id SID_SUI_FIELD_REQ_PROMPT = new string_id("camp", "sui_field_req_prompt");
    public static final string_id SID_SUI_CAMP_STATUS_TITLE = new string_id("camp", "sui_camp_status_title");
    public static final string_id SID_SUI_CAMP_STATUS_PROMPT = new string_id("camp", "sui_camp_status_prompt");
    public static final string_id SID_SUI_CAMP_STATUS_OWNER = new string_id("camp", "sui_camp_status_owner");
    public static final string_id SID_SUI_CAMP_STATUS_UPTIME = new string_id("camp", "sui_camp_status_uptime");
    public static final string_id SID_SUI_CAMP_STATUS_TOT_VIS = new string_id("camp", "sui_camp_status_tot_vis");
    public static final string_id SID_SUI_CAMP_STATUS_CUR_VIS = new string_id("camp", "sui_camp_status_cur_vis");
    public static final string_id SID_SUI_CAMP_STATUS_HEAL = new string_id("camp", "sui_camp_status_heal");
    public int handleCampPrep(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = params.getObjId("master");
        setObjVar(self, "master", master);
        setName(self, getEncodedName(master));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(self, "master");
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getObjIdObjVar(master, camping.VAR_OWNER);
        if (owner == null)
        {
            return SCRIPT_CONTINUE;
        }
        else if (owner == obj_id.NULL_ID)
        {
        }
        else 
        {
            if (owner == player)
            {
                mi.addRootMenu(menu_info_types.SERVER_CAMP_DISBAND, SID_MNU_DISBAND);
            }
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_MNU_STATUS);
            int mFac = pvpGetAlignedFaction(master);
            if (mFac != 0)
            {
                int pFac = pvpGetAlignedFaction(player);
                if (pFac == mFac && factions.isDeclared(player))
                {
                    int mnuReq = mi.addRootMenu(menu_info_types.SERVER_MENU3, SID_MNU_REQUISITION);
                    if (mnuReq > -1)
                    {
                        mi.addSubMenu(mnuReq, menu_info_types.SERVER_MENU4, SID_MNU_REQUISITION_WPN);
                        mi.addSubMenu(mnuReq, menu_info_types.SERVER_MENU5, SID_MNU_REQUISITION_INSTALLATION);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(self, "master");
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getObjIdObjVar(master, camping.VAR_OWNER);
        if (item == menu_info_types.SERVER_CAMP_DISBAND)
        {
            if ((owner == null) || (owner == obj_id.NULL_ID))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (owner == player)
                {
                    camping.nukeCamp(master);
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            showStatus(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
        }
        else if (item == menu_info_types.SERVER_CAMP_ASSUME_OWNERSHIP)
        {
        }
        else 
        {
            int mFac = pvpGetAlignedFaction(master);
            if (mFac != 0)
            {
                int pFac = pvpGetAlignedFaction(player);
                if (pFac == mFac && factions.isDeclared(player))
                {
                    if (item == menu_info_types.SERVER_MENU3)
                    {
                        sendSystemMessage(player, SID_SELECT_SUB_MENU);
                    }
                    else if (item == menu_info_types.SERVER_MENU4)
                    {
                        faction_perk.displayItemPurchaseSUI(player, pvpGetCurrentGcwRank(player), factions.getFaction(player), FIELD_COST_MULTIPLER);
                    }
                    else if (item == menu_info_types.SERVER_MENU5)
                    {
                        faction_perk.displayItemPurchaseSUI(player, pvpGetCurrentGcwRank(player), factions.getFaction(player), FIELD_COST_MULTIPLER);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgFactionItemPurchaseSelected(obj_id self, dictionary params) throws InterruptedException
    {
        faction_perk.factionItemPurchased(params, FIELD_COST_MULTIPLER);
        return SCRIPT_CONTINUE;
    }
    public void showStatus(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(self, "master");
        obj_id owner = getObjIdObjVar(master, camping.VAR_OWNER);
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        prose_package ppCampOwner = prose.getPackage(SID_SUI_CAMP_STATUS_OWNER);
        prose.setTT(ppCampOwner, owner);
        dsrc = utils.addElement(dsrc, " \0" + packOutOfBandProsePackage(null, ppCampOwner));
        int createTime = getIntObjVar(master, "camp.createTime");
        int curTime = getGameTime();
        String timestring = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(curTime - createTime));
        prose_package ppUptime = prose.getPackage(SID_SUI_CAMP_STATUS_UPTIME);
        prose.setTT(ppUptime, timestring);
        dsrc = utils.addElement(dsrc, " \0" + packOutOfBandProsePackage(null, ppUptime));
        int count = getIntObjVar(master, "visitor_count");
        prose_package ppTotVis = prose.getPackage(SID_SUI_CAMP_STATUS_TOT_VIS);
        prose.setDI(ppTotVis, count);
        dsrc = utils.addElement(dsrc, " \0" + packOutOfBandProsePackage(null, ppTotVis));
        count = getIntObjVar(master, "occ_count");
        prose_package ppCurVis = prose.getPackage(SID_SUI_CAMP_STATUS_CUR_VIS);
        prose.setDI(ppCurVis, count);
        dsrc = utils.addElement(dsrc, " \0" + packOutOfBandProsePackage(null, ppCurVis));
        float healrate = camping.getCampHealModifier(master);
        prose_package ppHealMod = prose.getPackage(SID_SUI_CAMP_STATUS_HEAL);
        prose.setDF(ppHealMod, healrate);
        dsrc = utils.addElement(dsrc, " \0" + packOutOfBandProsePackage(null, ppHealMod));
        sui.listbox(player, utils.packStringId(SID_SUI_CAMP_STATUS_PROMPT), utils.packStringId(SID_SUI_CAMP_STATUS_TITLE), sui.OK_CANCEL, dsrc);
    }
    public void sendWaypoint(obj_id self, obj_id player) throws InterruptedException
    {
        if (!group.isGrouped(player))
        {
            sendSystemMessage(player, SID_NO_GROUP);
            return;
        }
        location point = getLocation(self);
        obj_id group = getGroupObject(player);
        obj_id[] members = getGroupMemberIds(group);
        for (int i = 0; i < members.length; i++)
        {
            if (isIdValid(members[i]))
            {
                if (members[i] == player)
                {
                    sendSystemMessage(player, SID_WAYPOINT_SENT);
                    continue;
                }
                obj_id waypoint = getCampWaypoint(members[i]);
                if (!isIdValid(waypoint))
                {
                    waypoint = createWaypointInDatapad(members[i], point);
                }
                if (isIdValid(waypoint))
                {
                    setWaypointActive(waypoint, true);
                    setWaypointLocation(waypoint, point);
                    setName(waypoint, "Camp Location");
                    setWaypointColor(waypoint, "white");
                }
                sendSystemMessage(members[i], SID_CAMP_UP);
            }
        }
    }
    public obj_id getCampWaypoint(obj_id other) throws InterruptedException
    {
        obj_id datapad = getObjectInSlot(other, "datapad");
        obj_id[] data = getContents(datapad);
        if (data != null)
        {
            for (int i = 0; i < data.length; i++)
            {
                String waypointName = getName(data[i]);
                if (waypointName.equals("Camp Location"))
                {
                    return data[i];
                }
            }
        }
        return null;
    }
}
