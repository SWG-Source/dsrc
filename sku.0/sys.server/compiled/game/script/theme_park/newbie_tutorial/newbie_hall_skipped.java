package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class newbie_hall_skipped extends script.theme_park.newbie_tutorial.tutorial_base
{
    public newbie_hall_skipped()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "newbie.hallSetupStep", 0);
        spawnTravelTerminal(self);
        messageTo(self, "checkForDestroy", null, 300.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int checkForDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (!containsPlayer(self))
        {
            destroyObject(self);
        }
        messageTo(self, "checkForDestroy", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public void spawnTravelTerminal(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, TRAVEL_TERMINAL))
        {
            obj_id oldTerminal = getObjIdObjVar(bldg, TRAVEL_TERMINAL);
            destroyObject(oldTerminal);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = 27.76f;
        spawnLoc.y = -3.5f;
        spawnLoc.z = -167.86f;
        spawnLoc.cell = getCellId(bldg, "r1");
        obj_id terminal = create.object(TRAVEL_TERMINAL_TEMPLATE, spawnLoc);
        setObjVar(bldg, TRAVEL_TERMINAL, terminal);
        setObjVar(terminal, "newbie.skipped", true);
        attachScript(terminal, TRAVEL_TERMINAL_SCRIPT);
        detachScript(terminal, "terminal.terminal_travel");
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id room1 = getCellId(self, "r1");
        obj_id yourCell = getContainedBy(item);
        if (room1 == yourCell)
        {
            attachScript(item, NEWBIE_SCRIPT_SKIPPED);
        }
        obj_id terminal = getObjIdObjVar(self, TRAVEL_TERMINAL);
        if (terminal != null)
        {
            dictionary params = new dictionary();
            params.put("player", item);
            messageTo(terminal, "warp_player_now", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
