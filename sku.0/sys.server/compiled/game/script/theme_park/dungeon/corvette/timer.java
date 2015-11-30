package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.player_structure;
import script.library.badge;
import script.library.utils;

public class timer extends script.base_script
{
    public timer()
    {
    }
    public static final String MSGS = "dungeon/corvette";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUpDungeon", null, 3, false);
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        String dungeonType = getStringObjVar(top, "space_dungeon.name");
        if (!dungeonType.equals("corvette_neutral"))
        {
            if (pvpGetType(self) == PVPTYPE_COVERT)
            {
                pvpMakeDeclared(self);
                setObjVar(self, "corl_corvette.made_overt", 1);
            }
        }
        setObjVar(self, "corl_corvette.id", top);
        listenToMessage(top, "fiveMinuteTimer");
        listenToMessage(top, "dungeonEnds");
        CustomerServiceLog("DUNGEON_CorellianCorvette", "*Entered_Corvette - %TU", self);
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
        obj_id dungeon = space_dungeon.getDungeonIdForPlayer(self);
        location podLanding = new location();
        if (!hasObjVar(self, "podLanding"))
        {
            podLanding = pickLandingLocation();
            setObjVar(self, "podLanding", podLanding);
        }
        else 
        {
            podLanding = getLocationObjVar(self, "podLanding");
        }
        if (hasObjVar(self, "corl_corvette.mission_complete"))
        {
            messageTo(self, "rewardTime", null, 3, false);
            setObjVar(self, "corl_corvette.got_reward", 1);
        }
        else 
        {
            messageTo(self, "removeMe", null, 10, false);
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
    public int youWin(obj_id self, dictionary params) throws InterruptedException
    {
        string_id escape = new string_id(MSGS, "escape_pods");
        sendSystemMessage(self, escape);
        groupSetObjVar(self, "corl_corvette.mission_complete");
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
    public int rewardTime(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(self);
        string_id reward = new string_id(MSGS, "reward");
        sendSystemMessage(self, reward);
        giveBadge(self);
        destroyExtras(self);
        CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Reward: Player %TU has earned an AV-21 schematic, but must get it from the quest NPC.", self);
        space_dungeon.ejectPlayerFromDungeon(self);
        return SCRIPT_CONTINUE;
    }
    public int removeMe(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String fname = getName(self);
        CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Died: %TU died on the Corellian Corvette.", self);
        return SCRIPT_CONTINUE;
    }
    public void giveBadge(obj_id self) throws InterruptedException
    {
        String variable = "Corvette Badge";
        if (hasObjVar(self, "corl_corvette.imperial_destroy_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_imp_destroy");
            variable = "Imperial Destroy";
            setObjVar(self, "corvette.imperial_destroy.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        if (hasObjVar(self, "corl_corvette.imperial_rescue_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_imp_rescue");
            variable = "Imperial Rescue";
            setObjVar(self, "corvette.imperial_rescue.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        if (hasObjVar(self, "corl_corvette.imperial_assassin_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_imp_assassin");
            variable = "Imperial Assassin";
            setObjVar(self, "corvette.imperial_assassin.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        if (hasObjVar(self, "corl_corvette.neutral_destroy_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_neutral_destroy");
            variable = "Neutral Destroy";
            setObjVar(self, "corvette.neutral_destroy.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        if (hasObjVar(self, "corl_corvette.neutral_rescue_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_neutral_rescue");
            variable = "Neutral Rescue";
            setObjVar(self, "corvette.neutral_rescue.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        if (hasObjVar(self, "corl_corvette.neutral_assassin_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_neutral_assassin");
            variable = "Neutral Assassin";
            setObjVar(self, "corvette.neutral_assassin.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        if (hasObjVar(self, "corl_corvette.rebel_destroy_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_reb_destroy");
            variable = "Rebel Destroy";
            setObjVar(self, "corvette.rebel_destroy.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        if (hasObjVar(self, "corl_corvette.rebel_rescue_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_reb_rescue");
            variable = "Rebel Rescue";
            setObjVar(self, "corvette.rebel_rescue.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        if (hasObjVar(self, "corl_corvette.rebel_assassin_mission"))
        {
            badge.grantBadge(self, "bdg_corvette_reb_assassin");
            variable = "Rebel Assassin";
            setObjVar(self, "corvette.rebel_assassin.finished", 1);
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Badge: Gave badge to %TU for completing " + variable, self);
            return;
        }
        return;
    }
    public location pickLandingLocation() throws InterruptedException
    {
        location landing = new location();
        int randomSpot = rand(1, 5);
        switch (randomSpot)
        {
            case 1:
            landing = new location(-3931, 35, -4320, "tatooine", null);
            break;
            case 2:
            landing = new location(-1635, 0, -42, "corellia", null);
            break;
            case 3:
            landing = new location(-3392, 100, -6152, "rori", null);
            break;
            case 4:
            landing = new location(-2431, 0, -2471, "naboo", null);
            break;
            case 5:
            landing = new location(-5998, 27, 5935, "talus", null);
            break;
            default:
            landing = new location(0, 0, 0, "dantooine", null);
            break;
        }
        return landing;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        String dungeonType = getStringObjVar(top, "space_dungeon.name");
        if (dungeonType == null || dungeonType.equals(""))
        {
            messageTo(self, "recheckDungeonType", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        if (!dungeonType.equals("corvette_neutral"))
        {
            if (pvpGetType(self) == PVPTYPE_COVERT)
            {
                pvpMakeDeclared(self);
                setObjVar(self, "corl_corvette.made_overt", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "corl_corvette.made_overt"))
        {
            pvpMakeCovert(self);
        }
        obj_id top = getObjIdObjVar(self, "corl_corvette.id");
        LOG("space_dungeon", "*%TU was had the Timer script detached from themselves.", self);
        stopListeningToMessage(top, "fiveMinuteTimer");
        removeObjVar(self, "corl_corvette");
        destroyExtras(self);
        return SCRIPT_CONTINUE;
    }
    public void destroyExtras(obj_id self) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(self, "object/tangible/dungeon/droid_maint_module.iff"))
        {
            obj_id cleanup = utils.getItemPlayerHasByTemplate(self, "object/tangible/dungeon/droid_maint_module.iff");
            if (cleanup != null)
            {
                destroyObject(cleanup);
            }
        }
        if (utils.playerHasItemByTemplate(self, "object/tangible/loot/dungeon/corellian_corvette/bootdisk.iff"))
        {
            obj_id clean2 = utils.getItemPlayerHasByTemplate(self, "object/tangible/loot/dungeon/corellian_corvette/bootdisk.iff");
            if (clean2 != null)
            {
                destroyObject(clean2);
            }
        }
        return;
    }
    public int recheckDungeonType(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = space_dungeon.getDungeonIdForPlayer(self);
        String dungeonType = getStringObjVar(top, "space_dungeon.name");
        if (dungeonType == null || dungeonType.equals(""))
        {
            CustomerServiceLog("DUNGEON_CorellianCorvette", "*%TU was not set declared because the dungeon didn't have a space_dungeon.name objVar.", self);
            return SCRIPT_CONTINUE;
        }
        if (!dungeonType.equals("corvette_neutral"))
        {
            if (pvpGetType(self) == PVPTYPE_COVERT)
            {
                pvpMakeDeclared(self);
                setObjVar(self, "corl_corvette.made_overt", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
