package script.theme_park.heroic.axkva_min;

import script.dictionary;
import script.library.chat;
import script.library.create;
import script.library.trial;
import script.location;
import script.obj_id;

public class axkva extends script.base_script
{
    public axkva()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_AXKVA);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            addHate(self, player, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int contagion_bomb(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        queueCommand(self, (-1658679050), player, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int do_victory_warp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objects = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id wp = null;
        obj_id warden = null;
        for (obj_id object : objects) {
            if ((getStringObjVar(object, "spawn_id")).equals("axkva_warpTo")) {
                wp = object;
            }
            if ((getStringObjVar(object, "spawn_id")).equals("warden")) {
                warden = object;
            }
        }
        if (!isIdValid(warden) || !isIdValid(wp))
        {
            return SCRIPT_CONTINUE;
        }
        setLocation(self, getLocation(wp));
        faceTo(self, warden);
        return SCRIPT_CONTINUE;
    }
    public int attack_nearest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getClosestPlayer(getLocation(self));
        if (isIdValid(player))
        {
            addHate(self, player, 1.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int axkva_dictate(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r1");
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            queueCommand(self, (-645052268), player, "", COMMAND_PRIORITY_DEFAULT);
        }
        chat.chat(self, "Leaving so soon?");
        return SCRIPT_CONTINUE;
    }
    public int axkva_force_whirl(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        location loc1 = new location(-63.6f, 14.4f, 36.4f, "dungeon1", getCellId(trial.getTop(self), "r2"));
        location loc2 = new location(-69.8f, 13.6f, 9.6f, "dungeon1", getCellId(trial.getTop(self), "r2"));
        location loc3 = new location(-44.4f, 7.0f, 1.5f, "dungeon1", getCellId(trial.getTop(self), "r2"));
        location loc4 = new location(-42.7f, 8.3f, 20.9f, "dungeon1", getCellId(trial.getTop(self), "r2"));
        location loc5 = new location(-55.0f, 10.0f, 18.0f, "dungeon1", getCellId(trial.getTop(self), "r2"));
        location loc6 = new location(-49.0f, 10.0f, 13.0f, "dungeon1", getCellId(trial.getTop(self), "r2"));
        location[] path1 = 
        {
            loc1,
            loc2,
            loc3,
            loc4,
            loc5,
            loc6
        };
        location[] path2 = 
        {
            loc2,
            loc3,
            loc4,
            loc1,
            loc5,
            loc6
        };
        location[] path3 = 
        {
            loc3,
            loc4,
            loc1,
            loc2,
            loc5,
            loc6
        };
        location[] path4 = 
        {
            loc4,
            loc1,
            loc2,
            loc3,
            loc5,
            loc6
        };
        obj_id mob1 = create.object("axkva_force_whirl", loc1);
        obj_id mob2 = create.object("axkva_force_whirl", loc2);
        obj_id mob3 = create.object("axkva_force_whirl", loc3);
        obj_id mob4 = create.object("axkva_force_whirl", loc4);
        setMaster(mob1, self);
        trial.markAsTempObject(mob1, true);
        setObjVar(mob1, "path", path1);
        setMaster(mob2, self);
        trial.markAsTempObject(mob2, true);
        setObjVar(mob2, "path", path2);
        setMaster(mob3, self);
        trial.markAsTempObject(mob3, true);
        setObjVar(mob3, "path", path3);
        setMaster(mob4, self);
        trial.markAsTempObject(mob4, true);
        setObjVar(mob4, "path", path4);
        return SCRIPT_CONTINUE;
    }
}
