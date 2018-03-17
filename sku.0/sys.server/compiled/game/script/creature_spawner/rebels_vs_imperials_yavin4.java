package script.creature_spawner;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.location;
import script.obj_id;

public class rebels_vs_imperials_yavin4 extends script.base_script
{
    public float[][] imperialStartPoints = {
            {4042, 0, -6278},
            {4021, 0, -6278},
            {4031, 0, -6274},
            {4003, 0, -6261},
            {4042, 0, -6262},
            {4056, 0, -6246},
            {4004, 0, -6242},
            {4019, 0, -6263},
            {4021, 0, -6260},
            {4022, 0, -6261},
            {4005, 0, -6263},
            {4021, 0, -6256}
    };
    public float[][] rebelStartPoints = {
            {4017, 34, -6284},
            {4039, 34, -6835},
            {4028, 34, -6334}
    };
    public float[][] conflictPoints = {
            {4017, 34, -6300},
            {4044, 34, -6300},
            {4030, 34, -6300}
    };
    public String[] rebelTypes = {
            "rebel_corporal",
            "rebel_sergeant",
            "rebel_general",
            "rebel_major_general",
            "rebel_commando"
    };
    public String[] imperialTypes = {
            "scout_trooper",
            "stormtrooper",
            "stormtrooper_squad_leader",
            "stormtrooper_sniper",
            "stormtrooper_medic",
            "imperial_colonel"
    };

    public rebels_vs_imperials_yavin4()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "imperialPoints", 0);
        setObjVar(self, "rebelPoints", 0);
        messageTo(self, "startOver", null, 0, true);
        return SCRIPT_CONTINUE;
    }
    private obj_id spawnImperial(location loc, String type, obj_id self) throws InterruptedException{
        obj_id npc = spawnNpc(loc, type, "imperialDied", self);
        ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
        return npc;
    }
    private obj_id spawnNpc(location loc, String type, String message, obj_id self) throws InterruptedException{
        obj_id npc = create.object(type, loc);
        create.addDestroyMessage(npc, message, 10, self);
        return npc;
    }
    private obj_id[] spawnRebelGroup(location loc, String[] types, String message, obj_id self) throws InterruptedException{
        obj_id[] members = new obj_id[types.length];
        for(int i=0; i < types.length; i++){
            members[i] = spawnNpc(loc, types[i], message, self);
            setMovementRun(members[i]);
            if(i % 2 == 0){
                loc.z -= 2;
            }
            else{
                loc.x += 5;
            }
        }
        return members;
    }
    private void moveGroup(location loc, obj_id[] members, String offset) throws InterruptedException{
        for (obj_id member : members){
            ai_lib.aiPathTo(member, loc);
            if(offset.equals("x")) {
                loc.x++;
            }
            else{
                loc.z++;
            }
        }
    }
    public int startOver(obj_id self, dictionary params) throws InterruptedException
    {
        String[] rebel1 = {"rebel_corporal", "rebel_first_lieutenant", "rebel_sergeant", "rebel_commando"};
        String[] rebel2 = {"rebel_commando", "rebel_trooper", "rebel_major_general", "rebel_general"};

        // spawn the rebel troops - 3 sets, 2 of the sets use the same types.
        obj_id[] group = spawnRebelGroup(getRebelStart(0), rebel1, "rebelDied", self);
        moveGroup(getConflictPoint(0), group, "x");

        group = spawnRebelGroup(getRebelStart(1), rebel2, "rebelDied", self);
        moveGroup(getConflictPoint(1), group, "z");

        group = spawnRebelGroup(getRebelStart(2), rebel2, "rebelDied", self);
        moveGroup(getConflictPoint(2), group, "x");

        // spawn the imperial troops - all are spread out so each troop has its own
        // starting location to be spawned at, but will be grouped together to head
        // to the conflict point.
        obj_id stormtrooper1 = spawnImperial(getImperialLocation(0), "stormtrooper_squad_leader", self);
        obj_id stormtrooper2 = spawnImperial(getImperialLocation(1), "stormtrooper", self);
        obj_id stormtrooper4 = spawnImperial(getImperialLocation(3), "stormtrooper", self);
        obj_id stormtrooper5 = spawnImperial(getImperialLocation(4), "stormtrooper", self);
        obj_id stormtrooper6 = spawnImperial(getImperialLocation(5), "stormtrooper", self);
        obj_id stormtrooper7 = spawnImperial(getImperialLocation(6), "stormtrooper", self);
        obj_id stormtrooper8 = spawnImperial(getImperialLocation(7), "imperial_colonel", self);
        obj_id stormtrooper9 = spawnImperial(getImperialLocation(8), "stormtrooper_medic", self);
        obj_id stormtrooper10 = spawnImperial(getImperialLocation(9), "stormtrooper_sniper", self);
        obj_id stormtrooper11 = spawnImperial(getImperialLocation(10), "scout_trooper", self);
        obj_id stormtrooper12 = spawnImperial(getImperialLocation(11), "scout_trooper", self);

        // spawn an at_st at the site.
        spawnATST(self, null);

        setObjVar(self, "rebels", 8);
        setObjVar(self, "imperials", 7);
        setObjVar(self, "atst", 1);

        // send imperial troops to conflict points based on where each is supposed to go.
        moveGroup(getConflictPoint(0), new obj_id[] {stormtrooper1, stormtrooper2, stormtrooper4}, "x");
        moveGroup(getConflictPoint(1), new obj_id[] {stormtrooper5, stormtrooper6, stormtrooper7, stormtrooper8}, "z");
        moveGroup(getConflictPoint(2), new obj_id[] {stormtrooper9, stormtrooper10, stormtrooper11, stormtrooper12}, "x");

        return SCRIPT_CONTINUE;
    }
    public int imperialDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (getIntObjVar(self, "imperials") < 12)
        {
            location imp = getImperialLocation();
            imp.x += rand(-3, 3);
            imp.z += rand(-3, 3);
            ai_lib.aiPathTo(spawnImperial(imp, getImperial(), self), getConflictPoint());
        }
        setObjVar(self, "rebelPoints", getIntObjVar(self, "rebelPoints") + 1);
        destroyObject(params.getObjId("object"));
        return SCRIPT_CONTINUE;
    }
    public int rebelDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (getIntObjVar(self, "rebels") < 12)
        {
            obj_id rebel = spawnNpc(getRebelStart(), getRebel(), "rebelDied", self);
            setMovementRun(rebel);
            ai_lib.aiPathTo(rebel, getConflictPoint());
        }
        setObjVar(self, "imperialPoints", getIntObjVar(self, "imperialPoints") + 1);
        destroyObject(params.getObjId("object"));
        return SCRIPT_CONTINUE;
    }
    public int atstDied(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "rebelPoints", getIntObjVar(self, "rebelPoints") + 1);
        messageTo(self, "spawnATST", null, 8, true);
        return SCRIPT_CONTINUE;
    }
    public int spawnATST(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id at_st = create.object("at_st", getImperialLocation(2));
        create.addDestroyMessage(at_st, "atstDied", 10, self);
        attachScript(at_st, "creature.yavin4_atst");

        if(params != null) destroyObject(params.getObjId("object"));
        return SCRIPT_CONTINUE;
    }
    public String getRebel() throws InterruptedException
    {
        return rebelTypes[rand(0, rebelTypes.length - 1)];
    }
    public location getRebelStart() throws InterruptedException{
        return getRebelStart(null);
    }
    public location getRebelStart(Integer loc) throws InterruptedException{
        if(loc == null) {
            loc = rand(0, rebelStartPoints.length - 1);
        }
        return new location(rebelStartPoints[loc][0], rebelStartPoints[loc][1], rebelStartPoints[loc][2], "yavin4", null);
    }
    public location getConflictPoint() throws InterruptedException{
        return getConflictPoint(null);
    }
    public location getConflictPoint(Integer loc) throws InterruptedException
    {
        if(loc == null) {
            loc = rand(0, conflictPoints.length - 1);
        }
        return new location(conflictPoints[loc][0], conflictPoints[loc][1], conflictPoints[loc][2], "yavin4", null);
    }
    public location getImperialLocation() throws InterruptedException{
        return getImperialLocation(null);
    }
    public location getImperialLocation(Integer loc) throws InterruptedException{
        if(loc == null) {
            loc = rand(0, imperialStartPoints.length - 1);
        }
        return new location(imperialStartPoints[loc][0], imperialStartPoints[loc][1], imperialStartPoints[loc][2], "yavin4", null);
    }
    public String getImperial() throws InterruptedException
    {
        return imperialTypes[rand(1, imperialTypes.length - 1)];
    }
}
