package script.theme_park.dungeon.avatar_platform;

import script.dictionary;
import script.library.create;
import script.library.groundquests;
import script.library.player_structure;
import script.location;
import script.obj_id;
import script.string_id;

public class avatar_boss_fight_spawn extends script.base_script
{
    public avatar_boss_fight_spawn()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final String TBL_BOSS_FIGHT = "datatables/dungeon/avatar_platform/ep3_avatar_platform_boss_trando.iff";
    public static final string_id TAUNT = new string_id(STF, "harwakokok_taunt");
    public int OnReceivedItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isTaskActive(item, "ep3_trando_hssissk_zssik_10", "killHarwakokok"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        obj_id commanddeck = getCellId(structure, "commanddeck");
        if (hasObjVar(structure, "avatar_platform.boss_fight"))
        {
            return SCRIPT_CONTINUE;
        }
        spawnBossFight(commanddeck, item);
        beginTaunt(self, item);
        setObjVar(structure, "avatar_platform.boss_fight", 1);
        return SCRIPT_CONTINUE;
    }
    public void spawnBossFight(obj_id commanddeck, obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id structure = getTopMostContainer(self);
        int defenderCreatures = dataTableGetNumRows(TBL_BOSS_FIGHT);
        int x = 0;
        while (x < defenderCreatures)
        {
            String spawn = dataTableGetString(TBL_BOSS_FIGHT, x, "spawns");
            float xCoord = dataTableGetFloat(TBL_BOSS_FIGHT, x, "loc_x");
            float yCoord = dataTableGetFloat(TBL_BOSS_FIGHT, x, "loc_y");
            float zCoord = dataTableGetFloat(TBL_BOSS_FIGHT, x, "loc_z");
            location myself = getLocation(player);
            String planet = myself.area;
            obj_id top = getTopMostContainer(player);
            String spawnRoom = dataTableGetString(TBL_BOSS_FIGHT, x, "room");
            obj_id room = getCellId(structure, spawnRoom);
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object(spawn, spawnPoint);
            if (spawn.equals("ep3_avatar_harwakokok_mighty"))
            {
                attachScript(spawnedCreature, "theme_park.dungeon.avatar_platform.avatar_boss_clean_up");
            }
            else 
            {
                attachScript(spawnedCreature, "theme_park.dungeon.avatar_platform.avatar_minon_clean_up");
            }
            x = x + 1;
        }
        return;
    }
    public void beginTaunt(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id[] players = player_structure.getPlayersInBuilding(structure);
        if (players == null)
        {
            return;
        }
        int numPlayers = players.length;
        if (numPlayers > 0)
        {
            for (obj_id player1 : players) {
                groundquests.grantQuest(player1, "ep3_avatar_boss_taunt");
            }
        }
        messageTo(self, "handleOpenDoor", null, 10.0f, false);
        return;
    }
    public int handleOpenDoor(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id commanddeck = getCellId(structure, "commanddeck");
        permissionsMakePublic(commanddeck);
        return SCRIPT_CONTINUE;
    }
}
