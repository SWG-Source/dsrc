package script.theme_park.poi.general;

import script.dictionary;
import script.library.ai_lib;
import script.library.factions;
import script.library.weapons;
import script.obj_id;

public class pirate_camp extends script.theme_park.poi.base
{
    public pirate_camp()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        initializePoi(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        initializePoi(self);
        return SCRIPT_CONTINUE;
    }
    public void initializePoi(obj_id poiBaseObject) throws InterruptedException
    {
        String diff = poiGetDifficulty();
        poiCreateObject("object/static/particle/particle_campfire_style_1.iff", 0.7f, -2.6f);
        poiCreateObject("object/tangible/camp/campfire_logs_burnt.iff", 0.7f, -2.6f);
        poiCreateObject("object/tangible/camp/camp_tent_s1.iff", 1.5f, 0.7f);
        if (rand(1, 2) == 1)
        {
            poiCreateObject("object/tangible/camp/camp_lawn_chair.iff", -2.3f, 1.8f);
        }
        if (rand(1, 2) == 1)
        {
            poiCreateObject("object/tangible/camp/camp_tent_s1.iff", 1.5f, -6.7f);
        }
        if (rand(1, 2) == 1)
        {
            poiCreateObject("object/tangible/camp/camp_tent_s1.iff", 1.5f, -10.7f);
        }
        int numGuards = 5;
        switch (diff) {
            case "medium":
                numGuards += 2;
                break;
            case "hard":
                numGuards += 4;
                break;
            case "veryHard":
                numGuards += 6;
                break;
        }
        dictionary params = new dictionary();
        for (int i = 0; i < numGuards; i++)
        {
            params.put("guardNum", i);
            messageTo(poiBaseObject, "handleSpawnGuard", params, 1 + (i * 2), false);
        }
    }
    public int handleSpawnGuard(obj_id self, dictionary params) throws InterruptedException
    {
        int guardNum = params.getInt("guardNum");
        obj_id pirate = poiCreateNpc("pirate_" + guardNum, "pirate", rand(-5, +5), rand(-5, +5));
        factions.setFaction(pirate, "pirate");
        attachScript(pirate, "ai.thug");
        ai_lib.setDefaultCalmBehavior(pirate, ai_lib.BEHAVIOR_LOITER);
        String diff = poiGetDifficulty();
        switch (guardNum)
        {
            case 5:
            case 6:
            diff = "medium";
            break;
            case 7:
            case 8:
            diff = "hard";
            break;
            case 9:
            diff = "veryHard";
        }
        params.put("guard", pirate);
        messageTo(self, "handleEquipGuard", params, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleEquipGuard(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guard = params.getObjId("guard");
        int guardNum = params.getInt("guardNum");
        obj_id inv = getObjectInSlot(guard, "inventory");
        if (inv == null)
        {
            return SCRIPT_CONTINUE;
        }
        weapons.createWeapon("object/weapon/melee/sword/sword_01.iff", inv, rand(0.6f, 1.0f));
        switch (guardNum)
        {
            case 5:
            case 6:
            weapons.createWeapon("object/weapon/ranged/pistol/pistol_dl44.iff", inv, rand(0.6f, 1.0f));
            break;
            case 7:
            case 8:
            weapons.createWeapon("object/weapon/ranged/rifle/rifle_dlt20.iff", inv, rand(0.6f, 1.0f));
            break;
            case 9:
            weapons.createWeapon("object/weapon/ranged/rifle/rifle_dlt20.iff", inv, rand(0.6f, 1.0f));
            setObjVar(guard, "ai.grenadeType", "object/weapon/ranged/grenade/grenade_fragmentation");
        }
        setupPatrol(guard);
        return SCRIPT_CONTINUE;
    }
    public void setupPatrol(obj_id npc) throws InterruptedException
    {
        if (rand(1, 3) != 1)
        {
            return;
        }
        String[] wayPoints = new String[3];
        for (int i = 0; i < wayPoints.length; i++)
        {
            wayPoints[i] = rand(-15, +15) + ", " + rand(-15, +15);
        }
        ai_lib.setPatrolPath(npc, wayPoints);
    }
}
