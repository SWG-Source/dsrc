package script.npc.skillteacher;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class commerce_trainer_spawner extends script.base_script
{
    public commerce_trainer_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("stuff", self);
        messageTo(self, "spawnThings", params, 20, true);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnSurveyMission(self);
        spawnFoodStation(self);
        spawnClothingStation(self);
        if (utils.checkConfigFlag("Quest", "CraftingContract"))
        {
            spawnCraftingContractor(self);
        }
    }
    public void spawnArtisan(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location artisanloc = new location(0.05f, 1.13f, -14.56f, "tatooine", room);
        obj_id artisan = create.object("trainer_artisan", artisanloc);
        create.addDestroyMessage(artisan, "artisanDied", 10f, self);
        setCreatureStatic(artisan, true);
        setInvulnerable(artisan, true);
        setYaw(artisan, 5);
        return;
    }
    public void spawnSurveyMission(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location surveyloc = new location(3.29f, 1.13f, -9.58f, "tatooine", room);
        obj_id surveyor = create.object("miner", surveyloc);
        create.addDestroyMessage(surveyor, "surveyorDied", 10f, self);
        String[] strTest = new String[1];
        strTest[0] = "mission.objSurveyMission";
        setObjVar(surveyor, "mission.strMissionTypes", strTest);
        setObjVar(surveyor, "mission.intMinDifficulty", 1);
        setObjVar(surveyor, "mission.intMaxDifficulty", 10);
        setCreatureStatic(surveyor, true);
        setInvulnerable(surveyor, true);
        setYaw(surveyor, -111);
        attachScript(surveyor, "npc.converse.npc_convo");
        return;
    }
    public void spawnFoodStation(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetinge");
        location foodStationLoc = new location(-11.04f, 1.13f, -14.49f, "tatooine", room);
        obj_id foodStation = create.object("object/tangible/crafting/station/public_food_station.iff", foodStationLoc);
        create.addDestroyMessage(foodStation, "foodStationDied", 10f, self);
        setYaw(foodStation, 0);
        return;
    }
    public void spawnClothingStation(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingc");
        location clothingStationLoc = new location(11.07f, 1.13f, -14.25f, "tatooine", room);
        obj_id clothingStation = create.object("object/tangible/crafting/station/public_clothing_station.iff", clothingStationLoc);
        create.addDestroyMessage(clothingStation, "clothingStationDied", 10f, self);
        setYaw(clothingStation, 90);
        return;
    }
    public void spawnCraftingContractor(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "mainroom");
        location contractorloc = new location(-3.69f, 1.13f, -6.0f, "tatooine", room);
        obj_id contractor = create.object("crafting_contractor", contractorloc);
        create.addDestroyMessage(contractor, "contractorDied", 10f, self);
        setCreatureStatic(contractor, true);
        setInvulnerable(contractor, true);
        setYaw(contractor, 11);
        return;
    }
    public int spawnThings(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stuffs = params.getObjId("stuff");
        if (stuffs == null)
        {
            return SCRIPT_CONTINUE;
        }
        spawnEveryone(stuffs);
        return SCRIPT_CONTINUE;
    }
    public int artisanDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnArtisan(self);
        return SCRIPT_CONTINUE;
    }
    public int surveyorDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnSurveyMission(self);
        return SCRIPT_CONTINUE;
    }
    public int foodStationDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnFoodStation(self);
        return SCRIPT_CONTINUE;
    }
    public int clothingStationDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnClothingStation(self);
        return SCRIPT_CONTINUE;
    }
    public int contractorDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnCraftingContractor(self);
        return SCRIPT_CONTINUE;
    }
}
