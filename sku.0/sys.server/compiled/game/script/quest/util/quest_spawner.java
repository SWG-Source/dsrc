package script.quest.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.theater;
import script.library.utils;

public class quest_spawner extends script.base_script
{
    public quest_spawner()
    {
    }
    public static final String SCRIPT_CREATURE = "quest.util.quest_creature";
    public static final String VAR_SPAWNER_MAX_POPULATION = "quest_spawner.max_pop";
    public static final String VAR_SPAWNER_MAX = "quest_spawner.max_spawn";
    public static final String VAR_SPAWNER_CURRENT_POPULATION = "quest_spawner.current_pop";
    public static final String VAR_SPAWNER_CURRENT = "quest_spawner.current_spawn";
    public static final String VAR_SPAWNER_TYPE = "quest_spawner.type";
    public static final String VAR_SPAWNER_DATATABLE = "quest_spawner.datatable";
    public static final String VAR_SPAWNER_TIME_EXPIRED = "quest_spawner.time_expired";
    public static final String VAR_SPAWNED_BY = "quest_spawner.spawned_by";
    public static final String VAR_THEATER = "quest_spawner.theater";
    public static final String VAR_PULSE = "quest_spawner.pulse";
    public static final String BATCH_VAR_SPAWNED_MOBS = "quest_spawner.spawned_mobs";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "msgQuestSpawnerPulse", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgQuestSpawnerPulse(obj_id self, dictionary params) throws InterruptedException
    {
        if (createSpawn(self))
        {
            LOG("quest", "quest_spawner.msgQuestSpawnerPulse -- createSpawn success.");
        }
        else 
        {
            LOG("quest", "quest_spawner.msgQuestSpawnerPulse -- createSpawn failed.");
        }
        int time_expired = getIntObjVar(self, VAR_SPAWNER_TIME_EXPIRED);
        if (getGameTime() > time_expired)
        {
            LOG("quest", "quest_spawner.msgQuestSpawnerPulse -- time has expired for " + self);
            messageTo(self, "msgQuestDestroySpawner", null, 10.0f, false);
            return SCRIPT_CONTINUE;
        }
        int current_spawn = getIntObjVar(self, VAR_SPAWNER_CURRENT);
        int max_spawn = getIntObjVar(self, VAR_SPAWNER_MAX);
        if (current_spawn >= max_spawn)
        {
            LOG("quest", "quest_spawner.msgQuestSpawnerPulse -- spawn limits of " + max_spawn + "/" + current_spawn + " has been reached for " + self);
            messageTo(self, "msgQuestDestroySpawner", null, 10.0f, false);
            return SCRIPT_CONTINUE;
        }
        int pulse = getIntObjVar(self, VAR_PULSE);
        if (pulse < 1)
        {
            pulse = 30;
        }
        messageTo(self, "msgQuestSpawnerPulse", null, pulse, false);
        return SCRIPT_CONTINUE;
    }
    public int msgQuestDestroySpawner(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "quest_spawner.parent"))
        {
            obj_id parent = getObjIdObjVar(self, "quest_spawner.parent");
            if (hasObjVar(parent, "attack_spawners"))
            {
                Vector spawners = getResizeableObjIdArrayObjVar(parent, "attack_spawners");
                spawners.remove(self);
                if (spawners.size() > 0)
                {
                    setObjVar(parent, "attack_spawners", spawners);
                }
                else 
                {
                    removeObjVar(parent, "attack_spawners");
                }
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public dictionary getSpawnerData(String spawner_name, String datatable) throws InterruptedException
    {
        if (spawner_name == null)
        {
            LOG("quest", "quest_spawner.getSpawnerData -- spawner_name is null");
            return null;
        }
        int idx = dataTableSearchColumnForString(spawner_name, "type", datatable);
        if (idx == -1)
        {
            LOG("quest", "quest_spawner.getSpawnerData -- unable to find an entry for " + spawner_name);
            return null;
        }
        int num_items = dataTableGetNumRows(datatable);
        Vector spawn_template = new Vector();
        spawn_template.setSize(0);
        Vector min_number = new Vector();
        min_number.setSize(0);
        Vector max_number = new Vector();
        max_number.setSize(0);
        Vector weight = new Vector();
        weight.setSize(0);
        Vector script = new Vector();
        script.setSize(0);
        for (int i = idx + 1; i < num_items; i++)
        {
            dictionary row = dataTableGetRow(datatable, i);
            if ((row.getString("type")).length() > 0)
            {
                break;
            }
            else 
            {
                spawn_template = utils.addElement(spawn_template, row.getString("spawn_template"));
                min_number = utils.addElement(min_number, row.getInt("min_number"));
                max_number = utils.addElement(max_number, row.getInt("max_number"));
                weight = utils.addElement(weight, row.getInt("weight"));
                if (row.getString("script") != null)
                {
                    script = utils.addElement(script, row.getString("script"));
                }
                else 
                {
                    script = utils.addElement(script, "none");
                }
            }
        }
        dictionary spawn_data = new dictionary();
        spawn_data.put("spawn_template", spawn_template);
        spawn_data.put("min_number", min_number);
        spawn_data.put("max_number", max_number);
        spawn_data.put("weight", weight);
        spawn_data.put("script", script);
        return spawn_data;
    }
    public boolean createSpawn(obj_id spawner) throws InterruptedException
    {
        LOG("quest", "quest_spawner.createSpawn -- spawner ->" + spawner);
        if (!isIdValid(spawner))
        {
            LOG("quest", "quest_spawner.createSpawn -- spawner is null.");
            return false;
        }
        int max_population = getIntObjVar(spawner, VAR_SPAWNER_MAX_POPULATION);
        int max_spawn = getIntObjVar(spawner, VAR_SPAWNER_MAX);
        int current_population = 0;
        if (hasObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION))
        {
            current_population = getIntObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION);
        }
        int current_spawn = 0;
        if (hasObjVar(spawner, VAR_SPAWNER_CURRENT))
        {
            current_spawn = getIntObjVar(spawner, VAR_SPAWNER_CURRENT);
        }
        if (current_population >= max_population)
        {
            LOG("quest", "quest_spawner.createSpawn -- Max population already reached for " + spawner);
            return false;
        }
        if (current_spawn >= max_spawn)
        {
            LOG("quest", "quest_spawner.createSpawn -- Max number of spawns already reached for " + spawner);
            return false;
        }
        String spawner_type = getStringObjVar(spawner, VAR_SPAWNER_TYPE);
        String datatable = getStringObjVar(spawner, VAR_SPAWNER_DATATABLE);
        dictionary spawn_data = getSpawnerData(spawner_type, datatable);
        if (spawn_data == null)
        {
            LOG("quest", "quest_spawner.createSpawn -- can't find spawn data for spawner " + spawner + " in datatable " + datatable);
            return false;
        }
        if (spawn_data == null)
        {
            LOG("quest", "quest_spawner.createSpawn -- spawn_data is null for " + spawner);
            return false;
        }
        String[] spawn_template = spawn_data.getStringArray("spawn_template");
        int[] min_number = spawn_data.getIntArray("min_number");
        int[] max_number = spawn_data.getIntArray("max_number");
        int[] weight = spawn_data.getIntArray("weight");
        String[] spawn_script = spawn_data.getStringArray("script");
        if (spawn_template == null || min_number == null || max_number == null || weight == null)
        {
            LOG("quest", "quest_spawner.createSpawn -- spawn data contains a null array.");
            return false;
        }
        int total_weight = 0;
        for (int i = 0; i < weight.length; i++)
        {
            total_weight = total_weight + weight[i];
        }
        if (total_weight < 1)
        {
            LOG("quest", "quest_spawner.createSpawn -- total probility weight is < 1 for " + spawner);
            return false;
        }
        int weight_index = rand(1, total_weight);
        int array_index = -1;
        for (int i = 0; i < weight.length; i++)
        {
            weight_index = weight_index - weight[i];
            if (weight_index < 1)
            {
                array_index = i;
                break;
            }
        }
        if (array_index == -1)
        {
            LOG("quest", "quest_spawner.createSpawn -- unable to find an array index for " + spawner);
            return false;
        }
        int num_spawn = rand(min_number[array_index], max_number[array_index]);
        String template = spawn_template[array_index];
        String script = spawn_script[array_index];
        location spawner_loc = getLocation(spawner);
        while (num_spawn > 0)
        {
            if (max_population > current_population)
            {
                location spawn_loc = utils.getRandomAwayLocation(spawner_loc, 5.0f, 10.0f);
                obj_id mob = create.createCreature(template, spawn_loc, true);
                if (!isIdValid(mob))
                {
                    LOG("quest", "quest_spawner.createSpawn -- unable to spawn creature " + template + " from spawner " + spawner);
                }
                setObjVar(mob, VAR_SPAWNED_BY, spawner);
                if (hasObjVar(spawner, theater.VAR_PARENT))
                {
                    obj_id parent = getObjIdObjVar(spawner, theater.VAR_PARENT);
                    if (isIdValid(parent))
                    {
                        setObjVar(mob, VAR_THEATER, parent);
                    }
                }
                attachScript(mob, SCRIPT_CREATURE);
                if (script != null && script.length() > 0 && !script.equals("none"))
                {
                    attachScript(mob, script);
                }
                current_population++;
                num_spawn--;
            }
            else 
            {
                break;
            }
        }
        setObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION, current_population);
        setObjVar(spawner, VAR_SPAWNER_CURRENT, current_spawn + 1);
        return true;
    }
}
