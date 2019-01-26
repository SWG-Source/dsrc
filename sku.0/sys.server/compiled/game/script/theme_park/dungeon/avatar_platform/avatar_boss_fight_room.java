package script.theme_park.dungeon.avatar_platform;

import script.dictionary;
import script.obj_id;

public class avatar_boss_fight_room extends script.base_script
{
    public avatar_boss_fight_room()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleDoorLock", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDoorLock(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id commandhall = getCellId(structure, "commandhall08");
        if (!hasObjVar(structure, "avatar_platform.boss_fight_door_locked"))
        {
            permissionsMakePrivate(commandhall);
            setObjVar(structure, "avatar_platform.boss_fight_door_locked", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
