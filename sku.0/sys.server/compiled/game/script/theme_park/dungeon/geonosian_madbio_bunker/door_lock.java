package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class door_lock extends script.base_script
{
    public door_lock()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public static final String VAR_ACCESS_LIST = "access";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        convertAccessList(self);
        permissionsMakePrivate(self);
        Vector permList = utils.getResizeableObjIdBatchObjVar(self, VAR_ACCESS_LIST);
        if (permList == null || permList.size() == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int numInList = permList.size();
        for (int i = 0; i < numInList; i++)
        {
            obj_id thisPlayer = ((obj_id)permList.get(i));
            if (isIdValid(thisPlayer))
            {
                String fname = getFirstName(thisPlayer);
                permissionsAddAllowed(self, fname);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        permissionsMakePrivate(self);
        Vector permList = utils.getResizeableObjIdBatchObjVar(self, VAR_ACCESS_LIST);
        if (permList == null || permList.size() == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int numInList = permList.size();
        for (int i = 0; i < numInList; i++)
        {
            obj_id thisPlayer = ((obj_id)permList.get(i));
            if (isIdValid(thisPlayer))
            {
                String fname = getFirstName(thisPlayer);
                permissionsAddAllowed(self, fname);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int addToList(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player) || !isPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        String fname = getFirstName(player);
        obj_id room = params.getObjId("room");
        if (room == null)
        {
            return SCRIPT_CONTINUE;
        }
        permissionsAddAllowed(room, fname);
        Vector permList = utils.getResizeableObjIdBatchObjVar(room, VAR_ACCESS_LIST);
        if (permList == null)
        {
            permList = new Vector();
        }
        if (!utils.isElementInArray(permList, player))
        {
            permList = utils.addElement(permList, player);
            utils.setResizeableBatchObjVar(room, VAR_ACCESS_LIST, permList);
        }
        String roomName = getName(room);
        if (roomName.equals("largeendcave"))
        {
            CustomerServiceLog("DUNGEON_GeonosianBunker", "*Geonosian Finish: " + player + ": " + fname + " has entered the Last Room of the Bunker.");
        }
        return SCRIPT_CONTINUE;
    }
    public int removeFromList(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player) || !isPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        String fname = getFirstName(player);
        obj_id room = params.getObjId("room");
        if (room == null)
        {
            return SCRIPT_CONTINUE;
        }
        permissionsRemoveAllowed(room, fname);
        Vector permList = utils.getResizeableObjIdBatchObjVar(room, VAR_ACCESS_LIST);
        if (permList == null)
        {
            permList = new Vector();
        }
        permList = utils.removeElement(permList, player);
        utils.setResizeableBatchObjVar(room, VAR_ACCESS_LIST, permList);
        return SCRIPT_CONTINUE;
    }
    public boolean convertAccessList(obj_id room) throws InterruptedException
    {
        if (!isIdValid(room))
        {
            return false;
        }
        String[] access_list = getStringArrayObjVar(room, VAR_ACCESS_LIST);
        if (access_list != null)
        {
            obj_id[] new_access_list = nameListToObjId(access_list);
            if ((new_access_list != null) && (new_access_list.length > 0))
            {
                setObjVar(room, VAR_ACCESS_LIST, new_access_list);
            }
        }
        return true;
    }
    public obj_id[] nameListToObjId(String[] name_list) throws InterruptedException
    {
        if (name_list == null || name_list.length < 1)
        {
            return null;
        }
        Vector obj_id_list = new Vector();
        obj_id_list.setSize(0);
        for (int i = 0; i < name_list.length; i++)
        {
            obj_id new_obj_id = getPlayerIdFromFirstName(name_list[i]);
            if (isIdValid(new_obj_id))
            {
                obj_id_list.add(new_obj_id);
            }
            else 
            {
                LOG("door_lock", "door_lock.nameListToObjId -- couldn't find obj_id for " + name_list[i]);
            }
        }
        if (obj_id_list.size() > 0)
        {
            obj_id[] _obj_id_list = new obj_id[0];
            if (obj_id_list != null)
            {
                _obj_id_list = new obj_id[obj_id_list.size()];
                obj_id_list.toArray(_obj_id_list);
            }
            return _obj_id_list;
        }
        else 
        {
            return null;
        }
    }
}
