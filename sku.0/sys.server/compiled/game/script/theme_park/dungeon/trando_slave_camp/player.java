package script.theme_park.dungeon.trando_slave_camp;

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
import script.library.groundquests;

public class player extends script.base_script
{
    public player()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUpDungeon", null, 3, false);
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        String dungeonType = getStringObjVar(top, "space_dungeon.name");
        messageTo(self, "finishEntryQuest", null, 10, true);
        CustomerServiceLog("DUNGEON_BlackscaleCompound", "*Entered_BlackscaleCompound - %TU", self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        String scene = getCurrentSceneName();
        if (!scene.equals("kashyyyk_north_dungeons"))
        {
            detachScript(self, "theme_park.dungeon.trando_slave_camp.player");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        groundquests.clearQuest(self, "ep3_slave_camp_control_room_access");
        return SCRIPT_CONTINUE;
    }
    public int setUpDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = space_dungeon.getDungeonIdForPlayer(self);
        messageTo(controller, "beginSpawn", null, 1, false);
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
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel"))
        {
            return SCRIPT_CONTINUE;
        }
        space_dungeon.launchPlayerFromDungeon(self);
        return SCRIPT_CONTINUE;
    }
    public int finishEntryQuest(obj_id self, dictionary params) throws InterruptedException
    {
        groundquests.sendSignal(self, "signalSlaverCampEntered");
        return SCRIPT_CONTINUE;
    }
}
