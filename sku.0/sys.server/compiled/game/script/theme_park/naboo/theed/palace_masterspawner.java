package script.theme_park.naboo.theed;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class palace_masterspawner extends script.base_script
{
    public palace_masterspawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleDelayedImperialSpawn", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedImperialSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        spawnWarrenImperial(self);
        spawnLibrarian(self);
        spawnQueen(self);
        spawnComputer(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnWarrenImperial(obj_id bldg) throws InterruptedException
    {
        location spawnLoc = getLocation(bldg);
        spawnLoc.x = 8.9f;
        spawnLoc.y = 12.0f;
        spawnLoc.z = 82.39f;
        spawnLoc.cell = getCellId(bldg, "r3");
        obj_id imperialOfficer = create.createNpc("imperial_army_captain", "dressed_imperial_captain_m.iff", spawnLoc);
        setInvulnerable(imperialOfficer, true);
        setYaw(imperialOfficer, -177f);
        detachScript(imperialOfficer, "npc.faction_recruiter.faction_recruiter");
        detachScript(imperialOfficer, "npc.faction_recruiter.recruiter_setup");
        detachScript(imperialOfficer, "conversation.faction_recruiter_imperial");
        attachScript(imperialOfficer, "theme_park.warren.imperial_captain");
    }
    public void spawnLibrarian(obj_id self) throws InterruptedException
    {
        obj_id lib = getCellId(self, "r18");
        location library = new location(39.71f, 33, -96.81f, "naboo", lib);
        create.object("trivia_librarian", library);
        return;
    }
    public void spawnQueen(obj_id self) throws InterruptedException
    {
        obj_id fountain = getCellId(self, "r32");
        location queensloc = new location(36.5f, 27f, -192.9f, "naboo", fountain);
        int yaw = -53;
        obj_id queen = create.object("clone_relics_queen_kylantha", queensloc);
        setYaw(queen, yaw);
        return;
    }
    public void spawnComputer(obj_id self) throws InterruptedException
    {
        obj_id lib = getCellId(self, "r8");
        location library = new location(38.6f, 12f, 44.9f, "naboo", lib);
        obj_id computer = create.object("object/tangible/quest/rsf_computer.iff", library);
        attachScript(computer, "conversation.rsf_computer");
        return;
    }
}
