package script.theme_park.dungeon.death_watch_bunker;

import script.dictionary;
import script.library.ai_lib;
import script.library.utils;
import script.obj_id;

public class death_watch_death extends script.base_script
{
    public death_watch_death()
    {
    }
    public static final String TBL_DEATH_LOOT = "datatables/loot/dungeon/death_watch_bunker.iff";
    public static final String TBL_DEATH_SPAWN = "datatables/spawning/dungeon/death_watch_bunker.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "mom");
        if (mom == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        if (spawnNum < 0)
        {
            return SCRIPT_OVERRIDE;
        }
        int respawnTime = dataTableGetInt(TBL_DEATH_SPAWN, spawnNum, "respawn_time");
        if (respawnTime == 0)
        {
            respawnTime = 30;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("spawnMob", self);
        messageTo(mom, "tellingMomIDied", info, respawnTime, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "mom");
        if (mom == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        if (spawnNum == 0)
        {
            return SCRIPT_OVERRIDE;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("spawnMob", self);
        messageTo(mom, "tellingMomIDied", info, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        createMyLoot(self);
        return SCRIPT_CONTINUE;
    }
    public void createMyLoot(obj_id self) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return;
        }
        String mobType = ai_lib.getCreatureName(self);
        if (mobType == null)
        {
            return;
        }
        String creatureName = ai_lib.getCreatureName(self);
        String realName = getEncodedName(self);
        switch (creatureName) {
            case "mand_bunker_dthwatch_gold": {
                int x = rand(1, 1000);
                if (x < 2500) {
                    String myLoot = "object/tangible/loot/dungeon/death_watch_bunker/jetpack_stabilizer.iff";
                    createObject(myLoot, corpseInventory, "");
                    CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    return;
                }
                break;
            }
            case "mand_bunker_dthwatch_silver": {
                int x = rand(1, 1000);
                if (x < 35) {
                    String[] items = dataTableGetStringColumnNoDefaults(TBL_DEATH_LOOT, "dthwatch_lvl2");
                    int lootItems = items.length - 1;
                    int y = rand(0, lootItems);
                    String myLoot = dataTableGetString(TBL_DEATH_LOOT, y, "dthwatch_lvl2");
                    if (myLoot == null) {
                        return;
                    }
                    if (myLoot.equals("object/tangible/loot/dungeon/death_watch_bunker/pistol_de10_barrel.iff")) {
                        createObject(myLoot, corpseInventory, "");
                        CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    } else {
                        createObject(myLoot, corpseInventory, "");
                        CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    }
                    return;
                }
                break;
            }
            case "mand_bunker_dthwatch_grey":
            case "mand_bunker_dthwatch_red": {
                int x = rand(1, 1000);
                if (x < 25) {
                    String[] items = dataTableGetStringColumnNoDefaults(TBL_DEATH_LOOT, "dthwatch_lvl1");
                    int lootItems = items.length - 1;
                    int y = rand(0, lootItems);
                    String myLoot = dataTableGetString(TBL_DEATH_LOOT, y, "dthwatch_lvl1");
                    if (myLoot == null) {
                        return;
                    }
                    if (myLoot.equals("object/tangible/loot/dungeon/death_watch_bunker/pistol_de10_barrel.iff")) {
                        createObject(myLoot, corpseInventory, "");
                        CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    } else {
                        createObject(myLoot, corpseInventory, "");
                        CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    }
                    return;
                }
                break;
            }
            case "death_watch_miner": {
                int x = rand(1, 1000);
                if (x < 250) {
                    String[] items = dataTableGetStringColumnNoDefaults(TBL_DEATH_LOOT, "dthwatch_miner");
                    int lootItems = items.length - 1;
                    int y = rand(0, lootItems);
                    String myLoot = dataTableGetString(TBL_DEATH_LOOT, y, "dthwatch_miner");
                    if (myLoot == null) {
                        return;
                    }
                    createObject(myLoot, corpseInventory, "");
                    CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    return;
                }
                break;
            }
            case "mand_bunker_blksun_henchman_int":
            case "mand_bunker_blksun_thug_int":
            case "mand_bunker_blksun_guard_int": {
                int x = rand(1, 1000);
                if (x < 25) {
                    String[] items = dataTableGetStringColumnNoDefaults(TBL_DEATH_LOOT, "blksun_interior");
                    int lootItems = items.length - 1;
                    int y = rand(0, lootItems);
                    String myLoot = dataTableGetString(TBL_DEATH_LOOT, y, "blksun_interior");
                    if (myLoot == null) {
                        return;
                    }
                    if (myLoot.equals("object/tangible/loot/dungeon/death_watch_bunker/pistol_de10_barrel.iff")) {
                        createObject(myLoot, corpseInventory, "");
                        CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    } else {
                        createObject(myLoot, corpseInventory, "");
                        CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    }
                    return;
                }
                break;
            }
            case "mand_bunker_blksun_assassin_int": {
                int x = rand(1, 1000);
                if (x < 45) {
                    String[] items = dataTableGetStringColumnNoDefaults(TBL_DEATH_LOOT, "blksun_interior");
                    int lootItems = items.length - 1;
                    int y = rand(0, lootItems);
                    String myLoot = dataTableGetString(TBL_DEATH_LOOT, y, "blksun_interior");
                    if (myLoot == null) {
                        return;
                    }
                    if (myLoot.equals("object/tangible/loot/dungeon/death_watch_bunker/pistol_de10_barrel.iff")) {
                        createObject(myLoot, corpseInventory, "");
                        CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    } else {
                        createObject(myLoot, corpseInventory, "");
                        CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    }
                    return;
                }
                break;
            }
            case "death_watch_quenker":
            case "blastromech":
                return;
            case "mand_bunker_battle_dorid":
            case "mand_bunker_super_battle_droid":
                return;
            default: {
                int x = rand(1, 1000);
                if (x < 25) {
                    String[] items = dataTableGetStringColumnNoDefaults(TBL_DEATH_LOOT, "dthwatch_civilian");
                    int lootItems = items.length - 1;
                    int y = rand(0, lootItems);
                    String myLoot = dataTableGetString(TBL_DEATH_LOOT, y, "dthwatch_civilian");
                    if (myLoot == null) {
                        return;
                    }
                    createObject(myLoot, corpseInventory, "");
                    CustomerServiceLog("DEATH WATCH BUNKER:", "LOOT CREATED: " + realName + " created object in inventory " + myLoot);
                    return;
                }
                break;
            }
        }
        return;
    }
}
