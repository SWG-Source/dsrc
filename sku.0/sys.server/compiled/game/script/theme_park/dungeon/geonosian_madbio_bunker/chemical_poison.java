package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.dot;
import script.library.colors;

public class chemical_poison extends script.base_script
{
    public chemical_poison()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("poison_trap", 4.0f, true);
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
        messageTo(self, "showGas", null, 1, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (!isPlayer(whoTriggeredMe))
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
                if (checkForGasMask(whoTriggeredMe) != true)
                {
                    string_id toxic = new string_id(MSGS, "toxic_fumes");
                    sendSystemMessage(whoTriggeredMe, toxic);
                    dot.applyDotEffect(whoTriggeredMe, self, dot.DOT_POISON, "geonosian_poison_cloud", HEALTH, 100, 150, 300);
                }
                else 
                {
                    string_id gasmask = new string_id(MSGS, "gasmask");
                    sendSystemMessage(whoTriggeredMe, gasmask);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int trapShutOff(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id trap = getObjIdObjVar(self, "trap");
        destroyObject(trap);
        obj_id clean = createObject("object/static/particle/pt_miasma_of_fog_gray.iff", getLocation(self));
        setObjVar(self, "clean", clean);
        setObjVar(self, "trap_off", 1);
        messageTo(self, "turnGasOn", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public int turnGasOn(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "trap_off"))
        {
            removeObjVar(self, "trap_off");
        }
        messageTo(self, "showGas", null, 2, true);
        return SCRIPT_CONTINUE;
    }
    public void makeShutOffSwitches(obj_id self) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        obj_id room = getCellId(top, "grandcageroom");
        location here = getLocation(self);
        String planet = here.area;
        location valve = new location(8.08f, -22f, -332f, planet, room);
        obj_id shutoff = createObject("object/tangible/dungeon/chemical_storage.iff", valve);
        attachScript(shutoff, "theme_park.dungeon.geonosian_madbio_bunker.chemical_poison_shutoff");
        setObjVar(shutoff, "trap", self);
        setObjVar(self, "shutoff1", shutoff);
        setObjVar(self, "valve", valve);
        setYaw(shutoff, 180);
        return;
    }
    public int showGas(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "trap_off"))
        {
            obj_id trap = createObject("object/static/particle/pt_green_hanging_smoke.iff", getLocation(self));
            setObjVar(self, "trap", trap);
            if (hasObjVar(self, "clean"))
            {
                obj_id clean = getObjIdObjVar(self, "clean");
                destroyObject(clean);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkForGasMask(obj_id player) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/wearables/goggles/rebreather.iff"))
                {
                    obj_id mask = objContents[intI];
                    obj_id holder = getContainedBy(mask);
                    if (holder == player)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
