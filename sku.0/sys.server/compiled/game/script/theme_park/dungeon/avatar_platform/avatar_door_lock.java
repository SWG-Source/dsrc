package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;

public class avatar_door_lock extends script.base_script
{
    public avatar_door_lock()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAvatarDoorLocks(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAvatarDoorLocks(self);
        return SCRIPT_CONTINUE;
    }
    public void setAvatarDoorLocks(obj_id self) throws InterruptedException
    {
        obj_id techhall06 = getCellId(self, "techhall06");
        obj_id techhall04 = getCellId(self, "techhall04");
        obj_id techhall01 = getCellId(self, "techhall01");
        obj_id room = getCellId(self, "room");
        obj_id commanddeck = getCellId(self, "commanddeck");
        permissionsMakePrivate(techhall06);
        permissionsMakePrivate(techhall04);
        permissionsMakePrivate(techhall01);
        permissionsMakePrivate(room);
        permissionsMakePrivate(commanddeck);
    }
}
