package script.theme_park.tatooine.jabbaspawner;

import script.dictionary;
import script.library.ai_lib;
import script.location;
import script.obj_id;

public class palace_path extends script.base_script
{
    public palace_path()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int timeOut = rand(20, 40);
        messageTo(self, "startPatrol", null, timeOut, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int timeOut = rand(20, 40);
        messageTo(self, "startPatrol", null, timeOut, false);
        return SCRIPT_CONTINUE;
    }
    public int startPatrol(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        obj_id foyer = getCellId(bldg, "foyer");
        obj_id spawn1 = getCellId(bldg, "spawn1");
        obj_id spawn2 = getCellId(bldg, "spawn2");
        obj_id spawn3 = getCellId(bldg, "spawn3");
        obj_id bibspawn = getCellId(bldg, "bibspawn");
        obj_id throneroom = getCellId(bldg, "throneroom");
        obj_id garage = getCellId(bldg, "garage1");
        obj_id garage2 = getCellId(bldg, "garage2");
        obj_id stairs1 = getCellId(bldg, "stairs1");
        obj_id stairs2 = getCellId(bldg, "stairs2");
        obj_id stairs4 = getCellId(bldg, "stairs4");
        obj_id hall2 = getCellId(bldg, "hall2");
        obj_id hall4 = getCellId(bldg, "hall4");
        obj_id hall5 = getCellId(bldg, "hall5");
        obj_id hall8 = getCellId(bldg, "hall8");
        obj_id droid = getCellId(bldg, "droidtorture");
        location guard1 = new location(7.68f, 0.2f, 130.84f, "tatooine", foyer);
        location guard2 = new location(-22.22f, 10.76f, 64.30f, "tatooine", spawn1);
        location guard3 = new location(-24.89f, 6.6f, 90.77f, "tatooine", bibspawn);
        location guard4 = new location(-8.01f, 0.2f, 130.97f, "tatooine", foyer);
        location guard6 = new location(-50.89f, 0.2f, 82.37f, "tatooine", hall5);
        location guard7 = new location(-19.74f, 0.2f, 82.23f, "tatooine", stairs4);
        location guard8 = new location(-23.73f, 0.82f, 76.52f, "tatooine", stairs4);
        location guard9 = new location(-32.17f, 3, 55.89f, "tatooine", hall8);
        location guard10 = new location(-23.78f, 3.6f, 53.73f, "tatooine", throneroom);
        location guard11 = new location(11.75f, 0.2f, -3.62f, "tatooine", garage2);
        location guard12 = new location(14.12f, 5.55f, 66.95f, "tatooine", stairs2);
        location guard13 = new location(-28.84f, 8.89f, 17.1f, "tatooine", spawn2);
        location bmonk1 = new location(-8.94f, 3, 55.95f, "tatooine", hall4);
        location bmonk2 = new location(8.06f, 5.8f, 96.2f, "tatooine", stairs1);
        location bmonk4 = new location(20.44f, 11.1f, 2.09f, "tatooine", spawn3);
        location bmonk5 = new location(5.8f, 5.8f, 60.53f, "tatooine", hall2);
        location droid1 = new location(27.42f, 0.2f, 90.02f, "tatooine", droid);
        location droid4 = new location(8.97f, 0.2f, 88.26f, "tatooine", droid);
        location droid6 = new location(38.0f, 0.2f, -5.53f, "tatooine", garage);
        location random2 = new location(-7.9f, 3.0f, 59.61f, "tatooine", hall4);
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard1"))
        {
            ai_lib.aiPathTo(self, guard1);
            addLocationTarget("marker", guard1, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard2"))
        {
            ai_lib.aiPathTo(self, guard2);
            addLocationTarget("marker", guard2, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard3"))
        {
            ai_lib.aiPathTo(self, guard3);
            addLocationTarget("marker", guard3, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard4"))
        {
            ai_lib.aiPathTo(self, guard4);
            addLocationTarget("marker", guard4, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard6"))
        {
            ai_lib.aiPathTo(self, guard6);
            addLocationTarget("marker", guard6, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard7"))
        {
            ai_lib.aiPathTo(self, guard7);
            addLocationTarget("marker", guard7, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard8"))
        {
            ai_lib.aiPathTo(self, guard8);
            addLocationTarget("marker", guard8, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard9"))
        {
            ai_lib.aiPathTo(self, guard9);
            addLocationTarget("marker", guard9, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard10"))
        {
            ai_lib.aiPathTo(self, guard10);
            addLocationTarget("marker", guard10, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard11"))
        {
            ai_lib.aiPathTo(self, guard11);
            addLocationTarget("marker", guard11, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard12"))
        {
            ai_lib.aiPathTo(self, guard12);
            addLocationTarget("marker", guard12, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.gamguard13"))
        {
            ai_lib.aiPathTo(self, guard13);
            addLocationTarget("marker", guard13, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.bomarrmonk1"))
        {
            ai_lib.aiPathTo(self, bmonk1);
            addLocationTarget("marker", bmonk1, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.bomarrmonk2"))
        {
            ai_lib.aiPathTo(self, bmonk2);
            addLocationTarget("marker", bmonk2, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.bomarrmonk4"))
        {
            ai_lib.aiPathTo(self, bmonk4);
            addLocationTarget("marker", bmonk4, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.bomarrmonk5"))
        {
            ai_lib.aiPathTo(self, bmonk5);
            addLocationTarget("marker", bmonk5, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.droid4"))
        {
            ai_lib.aiPathTo(self, droid4);
            addLocationTarget("marker", droid4, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.droid1"))
        {
            ai_lib.aiPathTo(self, droid1);
            addLocationTarget("marker", droid1, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.random2"))
        {
            ai_lib.aiPathTo(self, random2);
            addLocationTarget("marker", random2, 1);
        }
        if (hasScript(self, "theme_park.tatooine.jabbaspawner.droid6"))
        {
            ai_lib.aiPathTo(self, droid6);
            addLocationTarget("marker", droid6, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("marker"))
        {
            messageTo(self, "nextSpot", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        if (name.equals("marker2"))
        {
            messageTo(self, "startPatrol", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int nextSpot(obj_id self, dictionary params) throws InterruptedException
    {
        final location home = aiGetHomeLocation(self);
        ai_lib.aiPathTo(self, home);
        addLocationTarget("marker2", home, 1);
        return SCRIPT_CONTINUE;
    }
}
