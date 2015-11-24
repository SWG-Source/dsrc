package script.theme_park.dungeon.kashyyyk_the_arena;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.utils;
import script.library.pclib;
import script.library.groundquests;

public class player extends script.base_script
{
    public player()
    {
    }
    public static final String STF = "dungeon/kash_the_arena";
    public static final String QUEST_MARK = "kash_arena.isQuestHolder";
    public static final string_id SID_DEATH_EJECT = new string_id(STF, "ejected_by_death");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        groundquests.sendSignal(self, "signalArenaChallengeAccepted");
        if (groundquests.isQuestActive(self, "ep3_forest_kerritamba_epic_6"))
        {
            utils.setScriptVar(self, QUEST_MARK, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String fname = getName(self);
        CustomerServiceLog("DUNGEON_kash_the_arena", "%TU died in the Arena, ejecting them from dungeon.", self);
        sendSystemMessage(self, SID_DEATH_EJECT);
        messageTo(self, "handleDeathFailure", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDeathFailure(obj_id self, dictionary params) throws InterruptedException
    {
        pclib.resurrectPlayer(self, true);
        groundquests.clearQuest(self, "ep3_forest_wirartu_epic_1");
        if (utils.hasScriptVar(self, QUEST_MARK))
        {
            utils.removeScriptVar(self, QUEST_MARK);
            obj_id dungeon = space_dungeon.getDungeonIdForPlayer(self);
            space_dungeon.endDungeonSession(dungeon);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        space_dungeon.verifyPlayerSession(top, self);
        if (!(getCurrentSceneName()).equals("kashyyyk_north_dungeons"))
        {
            detachScript(self, "theme_park.dungeon.kashyyyk_the_arena.player");
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
}
