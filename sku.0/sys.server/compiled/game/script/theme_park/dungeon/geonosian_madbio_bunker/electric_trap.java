package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.dot;
import script.library.colors;

public class electric_trap extends script.base_script
{
    public electric_trap()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("geonosian_electric_trap", 4.0f, true);
        if (hasObjVar(self, "shutoff1"))
        {
            obj_id shutoffCheck = getObjIdObjVar(self, "shutoff1");
            if (!exists(shutoffCheck))
            {
                makeShutOffSwitches(self);
            }
        }
        else 
        {
            makeShutOffSwitches(self);
        }
        messageTo(self, "showTrap", null, 1, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id player) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (hasObjVar(self, "trap_off"))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                int dmgLocation = 0;
                int dmgAmt = rand(300, 700);
                int hitLoc = rand(1, 4);
                switch (hitLoc)
                {
                    case 1:
                    dmgLocation = HIT_LOCATION_R_ARM;
                    break;
                    case 2:
                    dmgLocation = HIT_LOCATION_L_ARM;
                    break;
                    case 3:
                    dmgLocation = HIT_LOCATION_BODY;
                    break;
                    case 4:
                    dmgLocation = HIT_LOCATION_R_LEG;
                    break;
                    default:
                    dmgLocation = HIT_LOCATION_L_LEG;
                    break;
                }
                string_id electric = new string_id(MSGS, "shock");
                sendSystemMessage(player, electric);
                damage(player, DAMAGE_ELEMENTAL_ELECTRICAL, dmgLocation, dmgAmt);
                playClientEffectLoc(player, "clienteffect/trap_electric_01.cef", getLocation(self), 0.0f);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int trapShutOff(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id trap = getObjIdObjVar(self, "trap");
        destroyObject(trap);
        setObjVar(self, "trap_off", 1);
        messageTo(self, "turnTrapOn", null, 27, true);
        return SCRIPT_CONTINUE;
    }
    public int turnTrapOn(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "trap_off"))
        {
            removeObjVar(self, "trap_off");
        }
        messageTo(self, "showTrap", null, 2, true);
        return SCRIPT_CONTINUE;
    }
    public void makeShutOffSwitches(obj_id self) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        obj_id room = getCellId(top, "operatingroom2");
        location here = getLocation(self);
        String planet = here.area;
        location valve = new location(-122.5f, -34f, -182.8f, planet, room);
        obj_id shutoff = createObject("object/tangible/dungeon/wall_terminal_s1.iff", valve);
        attachScript(shutoff, "theme_park.dungeon.geonosian_madbio_bunker.electric_trap_shutoff");
        setObjVar(shutoff, "trap", self);
        setObjVar(self, "shutoff1", shutoff);
        setObjVar(self, "valve", valve);
        setName(shutoff, "Electrical Power Switch");
        setYaw(shutoff, 270);
        obj_id room2 = getCellId(top, "transition9");
        location valve2 = new location(-122.11f, -34, -266.7f, planet, room2);
        obj_id shutoff2 = createObject("object/tangible/dungeon/wall_terminal_s1.iff", valve2);
        attachScript(shutoff2, "theme_park.dungeon.geonosian_madbio_bunker.electric_trap_shutoff");
        setObjVar(shutoff2, "trap", self);
        setObjVar(self, "shutoff2", shutoff2);
        setObjVar(self, "valve", valve);
        setName(shutoff2, "Electrical Power Switch");
        setYaw(shutoff2, 180);
        return;
    }
    public int showTrap(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "trap_off"))
        {
            obj_id trap = createObject("object/static/particle/pt_poi_electricity_2x2.iff", getLocation(self));
            setObjVar(self, "trap", trap);
        }
        return SCRIPT_CONTINUE;
    }
}
