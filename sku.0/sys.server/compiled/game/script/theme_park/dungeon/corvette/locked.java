package script.theme_park.dungeon.corvette;

import script.dictionary;
import script.library.utils;
import script.obj_id;

import java.util.Vector;

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
        for (Object o : permList) {
            obj_id thisPlayer = ((obj_id) o);
            if (isIdValid(thisPlayer)) {
                String fname = getFirstName(thisPlayer);
                permissionsAddAllowed(self, fname);
                sendDirtyCellPermissionsUpdate(self, thisPlayer, true);
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
        for (Object o : permList) {
            obj_id thisPlayer = ((obj_id) o);
            if (isIdValid(thisPlayer)) {
                String fname = getFirstName(thisPlayer);
                permissionsAddAllowed(self, fname);
                sendDirtyCellPermissionsUpdate(self, thisPlayer, true);
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
        sendDirtyCellPermissionsUpdate(room, player, true);
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
        sendDirtyCellPermissionsUpdate(room, player, false);
        return SCRIPT_CONTINUE;
    }
    public int unlock(obj_id self, dictionary params) throws InterruptedException
    {
        permissionsMakePublic(self);
        return SCRIPT_CONTINUE;
    }
}
