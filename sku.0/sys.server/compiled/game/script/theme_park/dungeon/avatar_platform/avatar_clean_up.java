package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.space_dungeon;
import script.library.create;
import script.library.utils;
import script.library.permissions;

public class avatar_clean_up extends script.base_script
{
    public avatar_clean_up()
    {
    }
    public int msgSpaceDungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "avatar_platform"))
        {
            removeObjVar(self, "avatar_platform");
        }
        if (self == null)
        {
            return SCRIPT_CONTINUE;
        }
        int x = 0;
        while (x < 200)
        {
            String variable = "spawned" + x;
            if (hasObjVar(self, variable))
            {
                obj_id thing = getObjIdObjVar(self, variable);
                destroyObject(thing);
            }
            x = x + 1;
        }
        obj_id commanddeck = getCellId(self, "commanddeck");
        obj_id commandhall08 = getCellId(self, "commandhall08");
        obj_id techhall06 = getCellId(self, "techhall06");
        obj_id techhall04 = getCellId(self, "techhall04");
        obj_id techhall01 = getCellId(self, "techhall01");
        obj_id room = getCellId(self, "room");
        obj_id entrance = getCellId(self, "entrance");
        detachScript(self, "theme_park.dungeon.avatar_platform.avatar_door_lock");
        detachScript(commanddeck, "theme_park.dungeon.avatar_platform.avatar_boss_fight_spawn");
        detachScript(commandhall08, "theme_park.dungeon.avatar_platform.avatar_boss_fight_room");
        detachScript(entrance, "theme_park.dungeon.avatar_platform.avatar_opening_chat");
        detachScript(self, "theme_park.dungeon.avatar_platform.avatar_destruction");
        permissionsMakePublic(commanddeck);
        permissionsMakePublic(commandhall08);
        permissionsMakePublic(techhall06);
        permissionsMakePublic(techhall04);
        permissionsMakePublic(techhall01);
        permissionsMakePublic(room);
        removeObjVar(self, "current_players");
        return SCRIPT_CONTINUE;
    }
}
