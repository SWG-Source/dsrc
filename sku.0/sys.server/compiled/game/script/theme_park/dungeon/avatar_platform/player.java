package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.player_structure;
import script.library.utils;

public class player extends script.base_script
{
    public player()
    {
    }
    public static final String MSGS = "dungeon/corvette";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUpDungeon", null, 3, false);
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        String dungeonType = getStringObjVar(top, "space_dungeon.name");
        setObjVar(self, "corl_corvette.id", top);
        CustomerServiceLog("DUNGEON_AvatarPlatform", "*Entered_AvatarPlatform - %TU", self);
        return SCRIPT_CONTINUE;
    }
    public int dungeonEnds(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id dungeon = space_dungeon.getDungeonIdForPlayer(self);
        location podLanding = new location();
        if (!hasObjVar(self, "podLanding"))
        {
        }
        else 
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int setUpDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        if (hasScript(top, "theme_park.dungeon.corvette.spawned"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(top, "beginSpawn", null, 1, false);
            attachScript(top, "theme_park.dungeon.corvette.spawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String fname = getName(self);
        CustomerServiceLog("DUNGEON_AvatarPlatform", "*Corvette Died: %TU died on the Corellian Corvette.", self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        if (!space_dungeon.verifyPlayerSession(top, self))
        {
            return SCRIPT_CONTINUE;
        }
        String dungeonType = getStringObjVar(top, "space_dungeon.name");
        if (dungeonType == null || dungeonType.equals(""))
        {
            messageTo(self, "recheckDungeonType", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        obj_id top = getObjIdObjVar(self, "corl_corvette.id");
        LOG("space_dungeon", "*%TU was had the Timer script detached from themselves.", self);
        removeObjVar(self, "corl_corvette");
        return SCRIPT_CONTINUE;
    }
    public int recheckDungeonType(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        String dungeonType = getStringObjVar(top, "space_dungeon.name");
        if (dungeonType == null || dungeonType.equals(""))
        {
            CustomerServiceLog("DUNGEON_AvatarPlatform", "*%TU was not set declared because the dungeon (" + top + ") didn't have a space_dungeon.name objVar.", self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDungeonEjectConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space_dungeon", "theme_park.dungeon.avatar_platform.player.msgDungeonEjectConfirmed()");
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel"))
        {
            return SCRIPT_CONTINUE;
        }
        space_dungeon.ejectPlayerFromDungeon(self);
        return SCRIPT_CONTINUE;
    }
    public int msgDungeonLaunchConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space_dungeon", "theme_park.dungeon.avatar_platform.player.msgDungeonLaunchConfirmed()");
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel"))
        {
            return SCRIPT_CONTINUE;
        }
        space_dungeon.launchPlayerFromDungeon(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnsticking(obj_id self) throws InterruptedException
    {
        if ((getLocation(self).area).equals("dungeon1"))
        {
            detachScript(self, "theme_park.dungeon.avatar_platform");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
}
