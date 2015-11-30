package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.utils;

public class intellect_spawner extends script.base_script
{
    public intellect_spawner()
    {
    }
    public static final String SPAWNER_DATATABLE = "datatables/quest/hero_of_tatooine/intellect_spawner.iff";
    public static final String SPAWNER_OBJVAR = "quest.hero_of_tatooine.intellect.spawner";
    public static final String SPAWNER_CONTROLLER = SPAWNER_OBJVAR + ".controller";
    public static final String SPAWNER_NUMBER = SPAWNER_OBJVAR + ".number";
    public static final String SPAWNER_NPC = SPAWNER_OBJVAR + ".npc";
    public static final String SPAWNER_ACTIVE = SPAWNER_OBJVAR + ".active";
    public static final String SPAWNER_LIARS = SPAWNER_ACTIVE + ".liars";
    public static final String SPAWNER_BH = SPAWNER_ACTIVE + ".bh";
    public static final string_id CAPTAIN_NAME = new string_id("quest/hero_of_tatooine/npc_names", "himfan");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, SPAWNER_ACTIVE))
        {
            if (!hasObjVar(self, SPAWNER_NUMBER) || !hasObjVar(self, SPAWNER_CONTROLLER))
            {
                destroySpawnedObjects(self);
                removeObjVar(self, SPAWNER_OBJVAR);
                return SCRIPT_CONTINUE;
            }
            int number = getIntObjVar(self, SPAWNER_NUMBER);
            obj_id controller = getObjIdObjVar(self, SPAWNER_CONTROLLER);
            if (!spawnObjects(self, controller, number))
            {
                destroySpawnedObjects(self);
                removeObjVar(self, SPAWNER_OBJVAR);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleValidate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = params.getObjId("controller");
        int number = params.getInt("number");
        dictionary d = new dictionary();
        d.put("number", number);
        if (hasObjVar(self, SPAWNER_ACTIVE))
        {
            if (validateActiveSpawn(self))
            {
                d.put("spawn", 1);
            }
            else 
            {
                destroySpawnedObjects(self);
                removeObjVar(self, SPAWNER_OBJVAR);
                d.put("spawn", 0);
            }
        }
        else 
        {
            cleanupStaleObjects(self);
            if (hasObjVar(self, SPAWNER_OBJVAR))
            {
                removeObjVar(self, SPAWNER_OBJVAR);
            }
            d.put("spawn", 0);
        }
        messageTo(controller, "handleValidation", d, 0.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = params.getObjId("controller");
        int number = params.getInt("number");
        dictionary d = new dictionary();
        d.put("number", number);
        if (hasObjVar(self, SPAWNER_ACTIVE))
        {
            if (validateActiveSpawn(self))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                destroySpawnedObjects(self);
                removeObjVar(self, SPAWNER_OBJVAR);
            }
        }
        if (spawnObjects(self, controller, number))
        {
            d.put("spawn", 1);
        }
        else 
        {
            destroySpawnedObjects(self);
            d.put("spawn", 0);
        }
        messageTo(controller, "handleValidation", d, 0.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int handleComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, SPAWNER_NUMBER) || !hasObjVar(self, SPAWNER_CONTROLLER))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, SPAWNER_CONTROLLER);
        int number = getIntObjVar(self, SPAWNER_NUMBER);
        if (!isIdValid(controller))
        {
            messageTo(self, "handleCleanup", null, 30.0f, false);
            return SCRIPT_CONTINUE;
        }
        removeObjVar(self, SPAWNER_OBJVAR);
        dictionary d = new dictionary();
        d.put("number", number);
        messageTo(controller, "handleCompletion", d, 15.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroySpawnedObjects(self);
        return SCRIPT_CONTINUE;
    }
    public int handleLocationRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id control = params.getObjId("controller");
        location loc = getLocation(self);
        params.put("loc", loc);
        messageTo(control, "handleLocationResponse", params, 0.0f, true);
        return SCRIPT_CONTINUE;
    }
    public boolean spawnObjects(obj_id self, obj_id controller, int number) throws InterruptedException
    {
        location center = getLocation(self);
        dictionary loc_info = dataTableGetRow(SPAWNER_DATATABLE, self.toString());
        if (loc_info == null)
        {
            return false;
        }
        location center1 = new location(loc_info.getFloat("center1_x"), loc_info.getFloat("center1_y"), loc_info.getFloat("center1_z"), center.area);
        location center2 = new location(loc_info.getFloat("center2_x"), loc_info.getFloat("center2_y"), loc_info.getFloat("center2_z"), center.area);
        location liar1_loc = new location((center1.x - 0.75f), center1.y, (center1.z + 1.3f), center1.area);
        location liar2_loc = new location((center1.x - 0.75f), center1.y, (center1.z - 1.3f), center1.area);
        location liar3_loc = new location((center1.x + 1.5f), center1.y, center1.z, center1.area);
        location liar4_loc = new location((center2.x - 0.75f), center2.y, (center2.z + 1.3f), center2.area);
        location liar5_loc = new location((center2.x - 0.75f), center2.y, (center2.z - 1.3f), center2.area);
        location liar6_loc = new location((center2.x + 1.5f), center2.y, center2.z, center2.area);
        obj_id bh = create.object("quest_hero_of_tatooine_liar_bounty_hunter", center);
        Vector liars = new Vector();
        liars.setSize(0);
        liars = utils.addElement(liars, create.object("smuggler", liar1_loc));
        liars = utils.addElement(liars, create.object("smuggler", liar2_loc));
        liars = utils.addElement(liars, create.object("smuggler", liar3_loc));
        liars = utils.addElement(liars, create.object("smuggler", liar4_loc));
        liars = utils.addElement(liars, create.object("smuggler", liar5_loc));
        liars = utils.addElement(liars, create.object("smuggler", liar6_loc));
        liars = shuffleList(liars);
        namePirates(liars);
        if (liars == null || liars.size() == 0)
        {
            return false;
        }
        boolean success = true;
        for (int i = 0; i < liars.size(); i++)
        {
            success &= isIdValid(((obj_id)liars.get(i)));
        }
        success &= isIdValid(bh);
        setObjVar(self, SPAWNER_CONTROLLER, controller);
        setObjVar(self, SPAWNER_NUMBER, number);
        setObjVar(self, SPAWNER_BH, bh);
        setObjVar(self, SPAWNER_LIARS, liars);
        if (!success)
        {
            return success;
        }
        for (int i = 0; i < liars.size(); i++)
        {
            dictionary d = new dictionary();
            if (i < 3)
            {
                d.put("loc", center1);
            }
            else 
            {
                d.put("loc", center2);
            }
            setObjVar(((obj_id)liars.get(i)), SPAWNER_NPC, i);
            setObjVar(((obj_id)liars.get(i)), SPAWNER_CONTROLLER, self);
            ai_lib.setDefaultCalmBehavior(((obj_id)liars.get(i)), ai_lib.BEHAVIOR_SENTINEL);
            setInvulnerable(((obj_id)liars.get(i)), true);
            attachScript(((obj_id)liars.get(i)), "quest.hero_of_tatooine.intellect_liar");
            messageTo(((obj_id)liars.get(i)), "handleSetYaw", d, 3.0f, true);
        }
        setName(((obj_id)liars.get(0)), CAPTAIN_NAME);
        setObjVar(bh, SPAWNER_NPC, -1);
        setObjVar(bh, SPAWNER_CONTROLLER, self);
        ai_lib.setDefaultCalmBehavior(bh, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(bh, true);
        attachScript(bh, "conversation.quest_hero_of_tatooine_bounty_hunter");
        return success;
    }
    public boolean validateActiveSpawn(obj_id self) throws InterruptedException
    {
        obj_id[] liars = getObjIdArrayObjVar(self, SPAWNER_LIARS);
        obj_id bh = getObjIdObjVar(self, SPAWNER_BH);
        boolean validSuccess = true;
        boolean existSuccess = true;
        for (int i = 0; i < liars.length; i++)
        {
            validSuccess &= isIdValid(liars[i]);
        }
        validSuccess &= isIdValid(bh);
        for (int i = 0; i < liars.length; i++)
        {
            existSuccess &= exists(liars[i]);
        }
        existSuccess &= exists(bh);
        validSuccess &= existSuccess;
        return validSuccess;
    }
    public void destroySpawnedObjects(obj_id self) throws InterruptedException
    {
        obj_id[] liars = getObjIdArrayObjVar(self, SPAWNER_LIARS);
        obj_id bh = getObjIdObjVar(self, SPAWNER_BH);
        if (liars == null || liars.length == 0 || !isIdValid(bh))
        {
            cleanupStaleObjects(self);
        }
        for (int i = 0; i < liars.length; i++)
        {
            if (isIdValid(liars[i]) && exists(liars[i]))
            {
                destroyObject(liars[i]);
            }
        }
        if (isIdValid(bh) && exists(bh))
        {
            destroyObject(bh);
        }
    }
    public void cleanupStaleObjects(obj_id self) throws InterruptedException
    {
        obj_id[] npcs = getAllObjectsWithObjVar(getLocation(self), 50.0f, SPAWNER_NPC);
        if (npcs == null || npcs.length == 0)
        {
            return;
        }
        for (int i = 0; i < npcs.length; i++)
        {
            destroyObject(npcs[i]);
        }
    }
    public Vector shuffleList(Vector list) throws InterruptedException
    {
        Vector newList = new Vector();
        int size = list.size();
        while (size > 0)
        {
            int i = rand(0, (size - 1));
            newList = utils.addElement(newList, list.get(i));
            list = utils.removeElementAt(list, i);
            size--;
        }
        if (newList == null || newList.size() == 0)
        {
            return null;
        }
        return newList;
    }
    public void namePirates(Vector pirates) throws InterruptedException
    {
        string_id[] names = 
        {
            new string_id("quest/hero_of_tatooine/npc_names", "pirate_0"),
            new string_id("quest/hero_of_tatooine/npc_names", "pirate_1"),
            new string_id("quest/hero_of_tatooine/npc_names", "pirate_2"),
            new string_id("quest/hero_of_tatooine/npc_names", "pirate_3"),
            new string_id("quest/hero_of_tatooine/npc_names", "pirate_4"),
            new string_id("quest/hero_of_tatooine/npc_names", "pirate_5")
        };
        obj_id[] pirateObjs = new obj_id[names.length];
        pirates.toArray(pirateObjs);
        for (int i = 0; i < names.length; ++i)
        {
            setName(pirateObjs[i], "");
            setName(pirateObjs[i], names[i]);
        }
    }
}
