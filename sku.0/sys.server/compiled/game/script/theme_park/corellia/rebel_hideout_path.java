package script.theme_park.corellia;

import script.dictionary;
import script.library.ai_lib;
import script.location;
import script.obj_id;

public class rebel_hideout_path extends script.base_script
{
    public rebel_hideout_path()
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
        obj_id foyer1 = getCellId(bldg, "foyer1");
        obj_id mainhall = getCellId(bldg, "mainhall");
        obj_id meeting1 = getCellId(bldg, "meeting1");
        obj_id meeting2 = getCellId(bldg, "meeting2");
        obj_id meeting3 = getCellId(bldg, "meeting3");
        obj_id jailcell1 = getCellId(bldg, "jailcell1");
        obj_id jailcell2 = getCellId(bldg, "jailcell2");
        obj_id storage1 = getCellId(bldg, "storage1");
        obj_id staircase = getCellId(bldg, "staircase");
        obj_id foyer2 = getCellId(bldg, "foyer2");
        obj_id hall2 = getCellId(bldg, "hall2");
        obj_id storage2 = getCellId(bldg, "storage2");
        location patrolguard1 = new location(4.5f, 1.01f, 16.86f, "corellia", foyer1);
        location patrolguard2 = new location(-2.33f, 1.01f, 17.69f, "corellia", foyer1);
        location patrolguard3 = new location(17.39f, 1.01f, -11.38f, "corellia", mainhall);
        location patrolguard4 = new location(-17.74f, 1.01f, -11.54f, "corellia", mainhall);
        location patrolguard5 = new location(0.37f, 7.01f, -0.15f, "corellia", hall2);
        location patrolguard7 = new location(10.37f, 7.01f, 1.8f, "corellia", hall2);
        location patrolguard8 = new location(-4.19f, 7.01f, 5.71f, "corellia", storage2);
        location patroltech1 = new location(2.65f, 1.01f, 9.16f, "corellia", mainhall);
        location patroltech2 = new location(-0.55f, 1.01f, -19.07f, "corellia", storage1);
        location patroltech3 = new location(-17.95f, 1.01f, 14.48f, "corellia", meeting1);
        location patroltech4 = new location(11.1f, 1.01f, 0.99f, "corellia", meeting3);
        location patroldroid1 = new location(-3.47f, 1.01f, -19.22f, "corellia", storage1);
        location patroldroid2 = new location(18.4f, 1.01f, 13.7f, "corellia", meeting2);
        location patroldroid3 = new location(19.65f, 1.01f, 13.7f, "corellia", meeting2);
        location patroldroid4 = new location(-8.51f, 7.01f, 9.02f, "corellia", storage2);
        location patroldroid5 = new location(-1.33f, 1.01f, -0.28f, "corellia", mainhall);
        if (hasScript(self, "theme_park.corellia.patrol.trooper3"))
        {
            ai_lib.aiPathTo(self, patrolguard3);
            addLocationTarget("marker", patrolguard3, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.trooper4"))
        {
            ai_lib.aiPathTo(self, patroldroid2);
            addLocationTarget("marker", patroldroid2, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.newtech1"))
        {
            ai_lib.aiPathTo(self, patroltech2);
            addLocationTarget("marker", patroltech2, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.newtech2"))
        {
            ai_lib.aiPathTo(self, patroltech3);
            addLocationTarget("marker", patroltech3, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.mdroid"))
        {
            ai_lib.aiPathTo(self, patroldroid1);
            addLocationTarget("marker", patroldroid1, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.pdroid"))
        {
            ai_lib.aiPathTo(self, patroldroid5);
            addLocationTarget("marker", patroldroid5, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int nextSpot(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        obj_id foyer1 = getCellId(bldg, "foyer1");
        obj_id mainhall = getCellId(bldg, "mainhall");
        obj_id meeting1 = getCellId(bldg, "meeting1");
        obj_id meeting2 = getCellId(bldg, "meeting2");
        obj_id meeting3 = getCellId(bldg, "meeting3");
        obj_id jailcell1 = getCellId(bldg, "jailcell1");
        obj_id jailcell2 = getCellId(bldg, "jailcell2");
        obj_id storage1 = getCellId(bldg, "storage1");
        obj_id staircase = getCellId(bldg, "staircase");
        obj_id foyer2 = getCellId(bldg, "foyer2");
        obj_id hall2 = getCellId(bldg, "hall2");
        obj_id storage2 = getCellId(bldg, "storage2");
        location patrolguard1 = new location(4.5f, 1.01f, 16.86f, "corellia", foyer1);
        location patrolguard2 = new location(-2.33f, 1.01f, 17.69f, "corellia", foyer1);
        location patrolguard3 = new location(17.39f, 1.01f, -11.38f, "corellia", mainhall);
        location patrolguard4 = new location(-17.74f, 1.01f, -11.54f, "corellia", mainhall);
        location patrolguard5 = new location(0.37f, 7.01f, -0.15f, "corellia", hall2);
        location patrolguard7 = new location(10.37f, 7.01f, 1.8f, "corellia", hall2);
        location patrolguard8 = new location(-4.19f, 7.01f, 5.71f, "corellia", storage2);
        location patroltech1 = new location(2.65f, 1.01f, 9.16f, "corellia", mainhall);
        location patroltech2 = new location(-0.55f, 1.01f, -19.07f, "corellia", storage1);
        location patroltech3 = new location(-17.95f, 1.01f, 14.48f, "corellia", meeting1);
        location patroltech4 = new location(11.1f, 1.01f, 0.99f, "corellia", meeting3);
        location patroldroid1 = new location(-3.47f, 1.01f, -19.22f, "corellia", storage1);
        location patroldroid2 = new location(18.4f, 1.01f, 13.7f, "corellia", meeting2);
        location patroldroid3 = new location(19.65f, 1.01f, 13.7f, "corellia", meeting2);
        location patroldroid4 = new location(-8.51f, 7.01f, 9.02f, "corellia", storage2);
        location patroldroid5 = new location(-1.33f, 1.01f, -0.28f, "corellia", mainhall);
        if (hasScript(self, "theme_park.corellia.patrol.trooper3"))
        {
            ai_lib.aiPathTo(self, patrolguard4);
            addLocationTarget("marker2", patrolguard4, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.trooper4"))
        {
            ai_lib.aiPathTo(self, patrolguard2);
            addLocationTarget("marker2", patrolguard2, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.newtech1"))
        {
            ai_lib.aiPathTo(self, patroltech4);
            addLocationTarget("marker2", patroltech4, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.newtech2"))
        {
            ai_lib.aiPathTo(self, patroltech1);
            addLocationTarget("marker2", patroltech1, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.mdroid"))
        {
            ai_lib.aiPathTo(self, patroldroid3);
            addLocationTarget("marker2", patroldroid3, 1);
        }
        if (hasScript(self, "theme_park.corellia.patrol.pdroid"))
        {
            ai_lib.aiPathTo(self, patrolguard1);
            addLocationTarget("marker2", patrolguard1, 1);
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
            messageTo(self, "nextSpot2", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        if (name.equals("marker3"))
        {
            messageTo(self, "startPatrol", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int nextSpot2(obj_id self, dictionary params) throws InterruptedException
    {
        final location home = aiGetHomeLocation(self);
        ai_lib.aiPathTo(self, home);
        addLocationTarget("marker3", home, 1);
        return SCRIPT_CONTINUE;
    }
}
