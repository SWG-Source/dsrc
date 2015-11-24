package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.ai_lib;
import script.library.chat;

public class herald_boss extends script.base_script
{
    public herald_boss()
    {
    }
    public static final String TBL_BACKUP = "datatables/dungeon/death_watch/boss_backup.iff";
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(item, "death_watch_herald.destroy"))
        {
            if (hasObjVar(self, "death_watch_herald.boss.spawned"))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "death_watch_herald.boss.died"))
            {
                int died = getIntObjVar(self, "death_watch_herald.boss.died");
                int gametime = getGameTime();
                if ((died + 1800) > gametime)
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    removeObjVar(self, "death_watch_herald.boss.died");
                }
            }
            setObjVar(self, "death_watch_herald.player01", item);
            messageTo(self, "SpawnBoss", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int SpawnBoss(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        location goldbossloc = new location(114.27f, -64.00f, -99.77f, "endor", getCellId(building, "smallroom35"));
        obj_id goldboss = create.object("mand_bunker_dthwatch_gold", goldbossloc);
        location backup01Location = new location(114.10f, -64f, -89.08f, "endor", getCellId(building, "smallroom35"));
        obj_id backup01 = create.object("mand_bunker_super_battle_droid", backup01Location);
        setObjVar(backup01, "spawner", self);
        attachScript(backup01, "theme_park.dungeon.death_watch_bunker.boss_cleanup");
        location backup02Location = new location(116.29f, -64f, -100.58f, "endor", getCellId(building, "smallroom35"));
        obj_id backup02 = create.object("mand_bunker_blksun_assassin_int", backup02Location);
        setObjVar(backup02, "spawner", self);
        attachScript(backup02, "theme_park.dungeon.death_watch_bunker.boss_cleanup");
        location backup03Location = new location(117.02f, -64f, -106.03f, "endor", getCellId(building, "smallroom35"));
        obj_id backup03 = create.object("mand_bunker_battle_droid", backup03Location);
        setObjVar(backup03, "spawner", self);
        attachScript(backup03, "theme_park.dungeon.death_watch_bunker.boss_cleanup");
        location backup04Location = new location(112.46f, -64f, -104.34f, "endor", getCellId(building, "smallroom35"));
        obj_id backup04 = create.object("mand_bunker_dthwatch_red", backup04Location);
        setObjVar(backup04, "spawner", self);
        attachScript(backup04, "theme_park.dungeon.death_watch_bunker.boss_cleanup");
        if (isIdValid(goldboss))
        {
            attachScript(goldboss, "theme_park.dungeon.death_watch_bunker.boss_loot");
            attachScript(goldboss, "theme_park.dungeon.death_watch_bunker.leader_cleanup");
            setObjVar(goldboss, "spawner", self);
            setObjVar(self, "death_watch_herald.boss.spawned", goldboss);
        }
        return SCRIPT_CONTINUE;
    }
}
