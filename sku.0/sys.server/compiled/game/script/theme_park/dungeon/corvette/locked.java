package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class locked extends script.base_script
{
    public locked()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        permissionsMakePrivate(self);
        Vector permList = utils.getResizeableObjIdBatchObjVar(self, "access");
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
        Vector permList = utils.getResizeableObjIdBatchObjVar(self, "access");
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
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        String objvarKey = getStringObjVar(self, "objvar_key");
        if (objvarKey == null || objvarKey.equals(""))
        {
            objvarKey = "nil";
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
        Vector permList = utils.getResizeableObjIdBatchObjVar(room, "access");
        if (permList == null)
        {
            permList = new Vector();
        }
        if (!utils.isElementInArray(permList, player))
        {
            permList = utils.addElement(permList, player);
            utils.setResizeableBatchObjVar(room, "access", permList);
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
        Vector permList = utils.getResizeableObjIdBatchObjVar(room, "access");
        if (permList == null)
        {
            permList = new Vector();
        }
        permList = utils.removeElement(permList, player);
        utils.setResizeableBatchObjVar(room, "access", permList);
        return SCRIPT_CONTINUE;
    }
    public int unlock(obj_id self, dictionary params) throws InterruptedException
    {
        permissionsMakePublic(self);
        return SCRIPT_CONTINUE;
    }
}
