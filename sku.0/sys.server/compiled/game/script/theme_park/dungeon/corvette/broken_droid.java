package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;

public class broken_droid extends script.base_script
{
    public broken_droid()
    {
    }
    public static final String MSGS = "dungeon/corvette";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "showTrap", null, 1, false);
        messageTo(self, "create3p0", null, 2, false);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(player))
        {
            string_id peace = new string_id(MSGS, "not_in_combat");
            sendSystemMessage(player, peace);
            return SCRIPT_CONTINUE;
        }
        String itemName = getTemplateName(item);
        if (itemName.equals("object/tangible/dungeon/droid_maint_module.iff"))
        {
            messageTo(self, "fixProblem", null, 1, false);
            destroyObject(item);
        }
        else 
        {
            string_id wrong = new string_id(MSGS, "wont_fit");
            sendSystemMessage(player, wrong);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("repair"))
        {
            doAnimationAction(self, "sp_10");
            messageTo(self, "clearTrap", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id buddy = getObjIdObjVar(self, "pdroid");
        obj_id trap1 = getObjIdObjVar(self, "trap1");
        obj_id trap2 = getObjIdObjVar(self, "trap2");
        obj_id trap3 = getObjIdObjVar(self, "trap3");
        obj_id trap4 = getObjIdObjVar(self, "trap4");
        obj_id trap5 = getObjIdObjVar(self, "trap5");
        destroyObject(buddy);
        destroyObject(trap1);
        destroyObject(trap2);
        destroyObject(trap3);
        destroyObject(trap4);
        destroyObject(trap5);
        return SCRIPT_CONTINUE;
    }
    public int showTrap(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        obj_id ship = getTopMostContainer(self);
        obj_id room = getCellId(ship, "thrustersubroom28");
        location trapLoc1 = new location(0, -14, -32f, planet, room);
        location trapLoc2 = new location(0, -14, -34f, planet, room);
        location trapLoc3 = new location(0, -14, -36f, planet, room);
        location trapLoc4 = new location(0, -14, -38f, planet, room);
        location trapLoc5 = new location(0, -14, -40f, planet, room);
        if (!hasObjVar(self, "trap_off"))
        {
            obj_id trap1 = createObject("object/static/particle/pt_poi_electricity_2x2.iff", trapLoc1);
            setObjVar(self, "trap1", trap1);
            attachScript(trap1, "theme_park.dungeon.corvette.electric_trap");
            obj_id trap2 = createObject("object/static/particle/pt_poi_electricity_2x2.iff", trapLoc2);
            setObjVar(self, "trap2", trap2);
            attachScript(trap2, "theme_park.dungeon.corvette.electric_trap");
            obj_id trap3 = createObject("object/static/particle/pt_poi_electricity_2x2.iff", trapLoc3);
            setObjVar(self, "trap3", trap3);
            attachScript(trap3, "theme_park.dungeon.corvette.electric_trap");
            obj_id trap4 = createObject("object/static/particle/pt_poi_electricity_2x2.iff", trapLoc4);
            setObjVar(self, "trap4", trap4);
            attachScript(trap4, "theme_park.dungeon.corvette.electric_trap");
            obj_id trap5 = createObject("object/static/particle/pt_poi_electricity_2x2.iff", trapLoc5);
            setObjVar(self, "trap5", trap5);
            attachScript(trap5, "theme_park.dungeon.corvette.electric_trap");
        }
        return SCRIPT_CONTINUE;
    }
    public int clearTrap(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        here.x = here.x + 1;
        here.z = here.z + 1;
        obj_id c3p0 = getObjIdObjVar(self, "pdroid");
        obj_id trap1 = getObjIdObjVar(self, "trap1");
        obj_id trap2 = getObjIdObjVar(self, "trap2");
        obj_id trap3 = getObjIdObjVar(self, "trap3");
        obj_id trap4 = getObjIdObjVar(self, "trap4");
        obj_id trap5 = getObjIdObjVar(self, "trap5");
        destroyObject(trap1);
        destroyObject(trap2);
        destroyObject(trap3);
        destroyObject(trap4);
        destroyObject(trap5);
        messageTo(c3p0, "woohoo", null, 1, false);
        setObjVar(c3p0, "fixed", 1);
        return SCRIPT_CONTINUE;
    }
    public int create3p0(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        obj_id ship = getTopMostContainer(self);
        obj_id room = getCellId(ship, "thrustersubroom28");
        here.x = here.x + 1;
        here.z = here.z + 2;
        obj_id pdroid = create.staticObject("protocol_droid_3po", here);
        setName(pdroid, "H-6P0");
        attachScript(pdroid, "theme_park.dungeon.corvette.pdroid");
        setObjVar(self, "pdroid", pdroid);
        setObjVar(pdroid, "r2", self);
        return SCRIPT_CONTINUE;
    }
    public int fixProblem(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        obj_id ship = getTopMostContainer(self);
        obj_id room = getCellId(ship, "thrustersubroom28");
        location broken = new location(-3f, -14, -36.35f, planet, room);
        ai_lib.aiPathTo(self, broken);
        addLocationTarget("repair", broken, 1);
        return SCRIPT_CONTINUE;
    }
}
