package script.theme_park.dungeon.hracca_monster_island;

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
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id controller = space_dungeon.getDungeonIdForPlayer(self);
        String dungeonType = getStringObjVar(controller, "space_dungeon.name");
        setObjVar(self, "hracca_glade.dungeon_id", controller);
        listenToMessage(controller, "fiveMinuteTimer");
        listenToMessage(controller, "dungeonEnds");
        CustomerServiceLog("DUNGEON_AvatarPlatform", "*Entered_AvatarPlatform - %TU", self);
        return SCRIPT_CONTINUE;
    }
    public int fiveMinuteTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int timeLeft = params.getInt("timeLeft");
        sendSystemMessage(self, "You have " + timeLeft + " minutes left to complete your assignment.", null);
        return SCRIPT_CONTINUE;
    }
    public int dungeonEnds(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int setUpDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void groupSetObjVar(obj_id player, String objVarName) throws InterruptedException
    {
        setObjVar(player, objVarName, 1);
        obj_id[] members = getGroupMemberIds(player);
        if (members == null)
        {
            return;
        }
        int numInGroup = members.length;
        if (numInGroup == 0)
        {
            return;
        }
        for (int i = 0; i < numInGroup; i++)
        {
            obj_id thisMember = members[i];
            setObjVar(thisMember, objVarName, 1);
        }
        return;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        CustomerServiceLog("DUNGEON_HraccaGlade", "*Player Died: %TU died in the Hracca Glade.", self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id controller = space_dungeon.getDungeonIdForPlayer(self);
        if (!space_dungeon.verifyPlayerSession(controller, self))
        {
            return SCRIPT_CONTINUE;
        }
        String dungeonType = getStringObjVar(controller, "space_dungeon.name");
        if (dungeonType == null || dungeonType.equals(""))
        {
            messageTo(self, "recheckDungeonType", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        LOG("space_dungeon", "*%TU had the Timer script detached from them.", self);
        obj_id controller = getObjIdObjVar(self, "hracca_glade.dungeon_id");
        stopListeningToMessage(controller, "fiveMinuteTimer");
        removeObjVar(self, "hracca_glade");
        destroyExtras(self);
        return SCRIPT_CONTINUE;
    }
    public void destroyExtras(obj_id self) throws InterruptedException
    {
        return;
    }
    public int recheckDungeonType(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = space_dungeon.getDungeonIdForPlayer(self);
        String dungeonType = getStringObjVar(controller, "space_dungeon.name");
        if (dungeonType == null || dungeonType.equals(""))
        {
            CustomerServiceLog("DUNGEON_HraccaGlade", "*%TU was not set declared because the dungeon (" + controller + ") didn't have a space_dungeon.name objVar.", self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDungeonEjectConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space_dungeon", "theme_park.dungeon.hracca_monster_island.player.msgDungeonEjectConfirmed()");
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
        LOG("space_dungeon", "theme_park.dungeon.hracca_monster_island.player.msgDungeonLaunchConfirmed()");
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel"))
        {
            return SCRIPT_CONTINUE;
        }
        space_dungeon.launchPlayerFromDungeon(self);
        return SCRIPT_CONTINUE;
    }
}
