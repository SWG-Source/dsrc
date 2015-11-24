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

public class university_trainer_spawner extends script.base_script
{
    public university_trainer_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnThings", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnWeaponStation(self);
        spawnDroidStation(self);
        spawnClothingStation(self);
        spawnStructureStation(self);
    }
    public void spawnMerchant(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "meetinga");
        location merchantloc = new location(12.04f, 1.13f, 5.86f, "tatooine", room);
        obj_id merchant = create.object("trainer_merchant", merchantloc);
        create.addDestroyMessage(merchant, "merchantDied", 10f, self);
        setCreatureStatic(merchant, true);
        setInvulnerable(merchant, true);
        setYaw(merchant, -171);
        return;
    }
    public void spawnArchitect(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "meetingc");
        location architectloc = new location(11.04f, 1.13f, -14.93f, "tatooine", room);
        obj_id architect = create.object("trainer_architect", architectloc);
        create.addDestroyMessage(architect, "architectDied", 10f, self);
        setCreatureStatic(architect, true);
        setInvulnerable(architect, true);
        setYaw(architect, 0);
        return;
    }
    public void spawnWeaponsmith(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "meetingd");
        location weaponsmithloc = new location(-3.18f, 1.13f, -8.19f, "tatooine", room);
        obj_id weaponsmith = create.object("trainer_weaponsmith", weaponsmithloc);
        create.addDestroyMessage(weaponsmith, "weaponsmithDied", 10f, self);
        setCreatureStatic(weaponsmith, true);
        setInvulnerable(weaponsmith, true);
        setYaw(weaponsmith, 136);
        return;
    }
    public void spawnDroidEngineer(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "meetinge");
        location engineerloc = new location(-11.08f, 1.13f, -14.76f, "tatooine", room);
        obj_id engineer = create.object("trainer_droidengineer", engineerloc);
        create.addDestroyMessage(engineer, "engineerDied", 10f, self);
        setCreatureStatic(engineer, true);
        setInvulnerable(engineer, true);
        setYaw(engineer, -3);
        return;
    }
    public void spawnArmorsmith(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "meetingb");
        location armorsmithloc = new location(-15.22f, 1.13f, 0.10f, "tatooine", room);
        obj_id armorsmith = create.object("trainer_armorsmith", armorsmithloc);
        create.addDestroyMessage(armorsmith, "armorsmithDied", 10f, self);
        setCreatureStatic(armorsmith, true);
        setInvulnerable(armorsmith, true);
        setYaw(armorsmith, 84);
        return;
    }
    public void spawnWeaponStation(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingd");
        location weaponStationLoc = new location(-1.35f, 1.13f, -14.73f, "tatooine", room);
        obj_id weaponStation = create.object("object/tangible/crafting/station/public_weapon_station.iff", weaponStationLoc);
        create.addDestroyMessage(weaponStation, "weaponStationDied", 10f, self);
        setYaw(weaponStation, 0);
        return;
    }
    public void spawnDroidStation(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetinge");
        location droidStationLoc = new location(-6.9886f, 1.13f, -11.4636f, "tatooine", room);
        obj_id droidStation = create.object("object/tangible/crafting/station/public_weapon_station.iff", droidStationLoc);
        create.addDestroyMessage(droidStation, "droidStationDied", 10f, self);
        setYaw(droidStation, -90);
        return;
    }
    public void spawnClothingStation(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingb");
        location clothingStationLoc = new location(-12.08f, 1.13f, 5.75f, "tatooine", room);
        obj_id clothingStation = create.object("object/tangible/crafting/station/public_clothing_station.iff", clothingStationLoc);
        create.addDestroyMessage(clothingStation, "clothingStationDied", 10f, self);
        setYaw(clothingStation, -90);
        return;
    }
    public void spawnStructureStation(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "meetingc");
        location structureStationLoc = new location(14.70f, 1.13f, -8.76f, "tatooine", room);
        obj_id structureStation = create.object("object/tangible/crafting/station/public_structure_station.iff", structureStationLoc);
        create.addDestroyMessage(structureStation, "structureStationDied", 10f, self);
        setYaw(structureStation, -90);
        return;
    }
    public int spawnThings(obj_id self, dictionary params) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public int merchantDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMerchant(self);
        return SCRIPT_CONTINUE;
    }
    public int architectDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnArchitect(self);
        return SCRIPT_CONTINUE;
    }
    public int weaponsmithDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnWeaponsmith(self);
        return SCRIPT_CONTINUE;
    }
    public int engineerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroidEngineer(self);
        return SCRIPT_CONTINUE;
    }
    public int armorsmithDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnArmorsmith(self);
        return SCRIPT_CONTINUE;
    }
    public int weaponStationDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnWeaponStation(self);
        return SCRIPT_CONTINUE;
    }
    public int droidStationDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDroidStation(self);
        return SCRIPT_CONTINUE;
    }
    public int clothingStationDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnClothingStation(self);
        return SCRIPT_CONTINUE;
    }
    public int structureStationDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnStructureStation(self);
        return SCRIPT_CONTINUE;
    }
}
