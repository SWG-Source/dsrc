package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.chat;
import script.library.create;

public class stage_two_crier extends script.base_script
{
    public stage_two_crier()
    {
    }
    public static final string_id WARNING = new string_id("restuss_event/object", "restuss_battle_cryer_warning");
    public static final String FIRST_SIGNAL = "restuss_herald.first_signal";
    public static final String SECOND_SIGNAL = "restuss_herald.second_signal";
    public static final String HERALD_ID = "restuss_herald.herald_id";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCWData(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCWData(self);
        if (canSpawnHerald(self))
        {
            messageTo(self, "beginSpawn", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSpawnHerald(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, FIRST_SIGNAL) && !hasObjVar(self, SECOND_SIGNAL))
        {
            return true;
        }
        return false;
    }
    public void setCWData(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, SECOND_SIGNAL))
        {
            return;
        }
        if (hasObjVar(self, "space_dungeon.lock_key"))
        {
            removeObjVar(self, "space_dungeon.lock_key");
        }
        getClusterWideData("event", "stage_two_crier", true, self);
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String dungeon_name, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        String name = "stage_two_crier" + "-" + self;
        String scene = getCurrentSceneName();
        location loc = getLocation(self);
        dictionary dungeon_info = new dictionary();
        dungeon_info.put("dungeon_id", self);
        dungeon_info.put("scene", scene);
        dungeon_info.put("position_x", loc.x);
        dungeon_info.put("position_y", loc.y);
        dungeon_info.put("position_z", loc.z);
        replaceClusterWideData(manage_name, name, dungeon_info, true, lock_key);
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_CONTINUE;
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        location currentLoc = getLocation(self);
        obj_id herald = create.object("restuss_battle_cryer", currentLoc);
        if (!isIdValid(herald))
        {
            utils.setScriptVar(self, "DESIGNER FATAL", "Failed to create restuss herald");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, HERALD_ID, herald);
        setObjVar(self, FIRST_SIGNAL, true);
        return SCRIPT_CONTINUE;
    }
    public int beginMessage(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id herald = utils.getObjIdScriptVar(self, HERALD_ID);
        messageTo(self, "warningLoop", null, 0.0f, false);
        messageTo(self, "destroySpawn", null, 7200, false);
        setObjVar(self, SECOND_SIGNAL, true);
        return SCRIPT_CONTINUE;
    }
    public int warningLoop(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id herald = utils.getObjIdScriptVar(self, HERALD_ID);
        chat.chat(herald, WARNING);
        messageTo(self, "warningLoop", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySpawn(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id battleCryer = utils.getObjIdScriptVar(self, HERALD_ID);
        destroyObject(battleCryer);
        return SCRIPT_CONTINUE;
    }
}
