package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;
import script.library.sui;

public class temporary_structure extends script.base_script
{
    public temporary_structure()
    {
    }
    public static final string_id SID_TERMINAL_MANAGEMENT_STATUS = new string_id("player_structure", "management_status");
    public int OnBuildingComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "temporary_structure::msgBuildingComplete");
        String template = params.getString("template");
        dictionary deed_info = params.getDictionary("deed_info");
        obj_id owner = params.getObjId("owner");
        int rot = params.getInt("rotation");
        int build_time = params.getInt("buildTime");
        int time_stamp = params.getInt("timestamp");
        int time = getGameTime();
        location loc = getLocation(self);
        if (player_structure.isBuildingName(template))
        {
            loc.y = params.getFloat("placementHeight");
        }
        destroyObject(self);
        obj_id structure = player_structure.createPlayerStructure(template, owner, loc, rot, deed_info);
        dictionary new_params = new dictionary();
        new_params.put("structure", self);
        new_params.put("template", template);
        messageTo(owner, "OnRemoveStructure", new_params, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int msgDestroyStructure(obj_id self, dictionary params) throws InterruptedException
    {
        player_structure.destroyStructure(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "validateConstruction", null, 180, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT_STATUS, SID_TERMINAL_MANAGEMENT_STATUS);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "temporary_structure::OnObjectMenuSelect");
        if (item == menu_info_types.SERVER_TERMINAL_MANAGEMENT_STATUS)
        {
            LOG("LOG_CHANNEL", "temporary_structure::OnStatus");
            showConstructionStatus(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int statusMenuRefresh(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_OK)
        {
            obj_id player = sui.getPlayerId(params);
            if (isIdValid(player))
            {
                showConstructionStatus(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int showConstructionStatus(obj_id self) throws InterruptedException
    {
        return showConstructionStatus(self, null);
    }
    public int showConstructionStatus(obj_id self, obj_id player) throws InterruptedException
    {
        int build_time = getIntObjVar(self, player_structure.VAR_DEED_BUILDTIME);
        int timestamp = getIntObjVar(self, player_structure.VAR_DEED_TIMESTAMP);
        int current_time = getGameTime();
        int time_passed = current_time - timestamp;
        int percent = time_passed * 100 / build_time;
        int time = build_time - time_passed;
        if (time < 1)
        {
            if (restartConstruction(self))
            {
                sendSystemMessageTestingOnly(player, "Construction process restarted.");
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "Construction restart failed.");
                CustomerServiceLog("structure", "Building Marker " + self + "destroyed");
                destroyObject(self);
            }
        }
        else 
        {
            int[] conv_time = player_structure.convertSecondsTime(time);
            String time_str = player_structure.assembleTimeRemaining(conv_time);
            String[] dsrc = new String[4];
            dsrc[0] = "Owner: " + player_structure.getStructureOwner(self);
            dsrc[1] = "This structure is " + percent + "% complete";
            dsrc[2] = "";
            dsrc[3] = "Time Remaining: " + time_str;
            String title = "Structure Construction Status";
            String prompt = "Structure Construction Status";
            return sui.listbox(self, player, prompt, sui.REFRESH_CANCEL, title, dsrc, "statusMenuRefresh");
        }
        return -1;
    }
    public boolean restartConstruction(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        dictionary params = new dictionary();
        if (hasObjVar(self, player_structure.VAR_TEMP_TEMPLATE))
        {
            params.put("template", getStringObjVar(self, player_structure.VAR_TEMP_TEMPLATE));
        }
        else 
        {
            return false;
        }
        if (hasObjVar(self, player_structure.VAR_TEMP_ROTATION))
        {
            params.put("rotation", getIntObjVar(self, player_structure.VAR_TEMP_ROTATION));
        }
        else 
        {
            return false;
        }
        if (hasObjVar(self, player_structure.VAR_TEMP_ROTATION))
        {
            params.put("owner", getObjIdObjVar(self, player_structure.VAR_TEMP_OWNER));
        }
        else 
        {
            return false;
        }
        if (hasObjVar(self, player_structure.VAR_TEMP_PLACEMENT_HEIGHT))
        {
            params.put("placementHeight", getFloatObjVar(self, player_structure.VAR_TEMP_PLACEMENT_HEIGHT));
        }
        dictionary deed_info = new dictionary();
        deed_info.put("template", player_structure.getDeedTemplate(self));
        if (hasObjVar(self, player_structure.VAR_DEED_SCENE))
        {
            deed_info.put("scene", getStringObjVar(self, player_structure.VAR_DEED_SCENE));
        }
        if (hasObjVar(self, player_structure.VAR_DEED_BUILDTIME))
        {
            deed_info.put("build_time", getIntObjVar(self, player_structure.VAR_DEED_BUILDTIME));
        }
        else 
        {
            return false;
        }
        if (hasObjVar(self, player_structure.VAR_TEMP_OWNER_NAME))
        {
            deed_info.put("owner_name", getStringObjVar(self, player_structure.VAR_TEMP_OWNER_NAME));
        }
        else 
        {
            return false;
        }
        if (hasObjVar(self, "cityName"))
        {
            deed_info.put("cityName", getStringObjVar(self, "cityName"));
        }
        params.put("deed_info", deed_info);
        int buildtime = getIntObjVar(self, player_structure.VAR_DEED_BUILDTIME);
        setObjVar(self, player_structure.VAR_DEED_TIMESTAMP, getGameTime());
        messageTo(self, "OnBuildingComplete", params, buildtime, false);
        return true;
    }
    public int validateConstruction(obj_id self, dictionary params) throws InterruptedException
    {
        int timestamp = getIntObjVar(self, player_structure.VAR_DEED_TIMESTAMP);
        if (timestamp < 0)
        {
            timestamp = 0;
        }
        int current_time = getGameTime();
        int time = current_time - timestamp;
        if (time > 600 || time < 0)
        {
            obj_id owner = getObjIdObjVar(self, player_structure.VAR_TEMP_OWNER);
            String template = getStringObjVar(self, player_structure.VAR_TEMP_TEMPLATE);
            CustomerServiceLog("structure", "Building Marker destroyed" + self + "Owned by " + owner + "House template" + template);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
