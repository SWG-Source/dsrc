package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.attrib;
import script.library.create;
import script.library.groundquests;
import script.library.locations;
import script.library.utils;

public class dynamic_spawn_off_quest_item extends script.base_script
{
    public dynamic_spawn_off_quest_item()
    {
    }
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public int handleQuestFlavorObject(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() Initialized!");
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() Params invalid.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("player"))
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() player param not found.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() player OID invalid.");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "creatureName"))
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() No creature name var on object: " + self);
            return SCRIPT_CONTINUE;
        }
        String creatureName = getStringObjVar(self, "creatureName");
        if (creatureName == null || creatureName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() Creature var INVALID on object: " + self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "spawnCount"))
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() No creature spawn count var on object: " + self);
            return SCRIPT_CONTINUE;
        }
        int spawnCount = getIntObjVar(self, "spawnCount");
        if (spawnCount <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() Creature spawn count var INVALID on object: " + self);
            return SCRIPT_CONTINUE;
        }
        int combatLevel = getLevel(player);
        if (combatLevel < 0 || combatLevel > 90)
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() Player " + player + " has a level that is invalid. Player level is: " + combatLevel + ". Aborting the camp defense.");
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < spawnCount; i++)
        {
            obj_id mob = create.object(creatureName, getLocation(self));
            if (!isValidId(mob) || !exists(mob))
            {
                CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() Mob: " + mob + " could not be created!");
                return SCRIPT_CONTINUE;
            }
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() Mob level is set to match player level");
            setObjVar(mob, create.INITIALIZE_CREATURE_DO_NOT_SCALE_OBJVAR, 1);
            dictionary creatureDict = utils.dataTableGetRow(CREATURE_TABLE, creatureName);
            if (creatureDict != null)
            {
                create.initializeCreature(mob, creatureName, creatureDict, combatLevel);
            }
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() Mob level is set to match player level.");
            setInvulnerable(mob, false);
            clearCondition(mob, CONDITION_CONVERSABLE);
            setMovementRun(mob);
            setBaseRunSpeed(mob, (getBaseRunSpeed(self) - 8));
            setAttributeAttained(mob, attrib.OUTBREAK_AFFLICTED);
            setObjVar(mob, "owner", player);
            attachScript(mob, "theme_park.outbreak.dynamic_enemy");
            startCombat(mob, player);
            CustomerServiceLog("outbreak_themepark", "dynamic_spawn_off_quest_item.handleQuestFlavorObject() the NPC is attacking player: " + player);
        }
        return SCRIPT_CONTINUE;
    }
}
