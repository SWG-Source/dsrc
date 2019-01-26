package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.dictionary;
import script.library.dot;
import script.library.utils;
import script.location;
import script.obj_id;
import script.string_id;

public class poison extends script.base_script
{
    public poison()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("geonosian_poison_gas", 4.0f, true);
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
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume("geonosian_poison_gas", 4.0f, true);
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
        obj_id invisible = getObjIdObjVar(self, "invisible");
        if (isIdValid(invisible))
        {
            destroyObject(invisible);
        }
        obj_id[] players = getAllPlayers(getLocation(self), 40.0f);
        for (obj_id player : players) {
            playClientEffectObj(player, "clienteffect/item_gas_leak_trap_off.cef", self, "");
        }
        invisible = createObject("object/tangible/theme_park/invisible_object.iff", getLocation(self));
        setObjVar(self, "invisible", invisible);
        setObjVar(self, "trap_off", 1);
        messageTo(self, "turnGasOn", null, 7, true);
        return SCRIPT_CONTINUE;
    }
    public int turnGasOn(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "trap_off"))
        {
            removeObjVar(self, "trap_off");
        }
        obj_id[] players = getAllPlayers(getLocation(self), 40.0f);
        for (obj_id player : players) {
            playClientEffectObj(player, "clienteffect/item_gas_leak_trap_on.cef", self, "");
        }
        messageTo(self, "showGas", null, 2, true);
        return SCRIPT_CONTINUE;
    }
    public void makeShutOffSwitches(obj_id self) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        obj_id room = getCellId(top, "hall1");
        location here = getLocation(self);
        String planet = here.area;
        obj_id room2 = getCellId(top, "spiralhallway");
        location valve = new location(29.89f, 9.68f, -8.53f, planet, room);
        location valve2 = new location(1.9f, -2.28f, -15.34f, planet, room2);
        obj_id shutoff = createObject("object/tangible/dungeon/wall_terminal_s1.iff", valve);
        obj_id shutoff2 = createObject("object/tangible/dungeon/wall_terminal_s1.iff", valve2);
        attachScript(shutoff, "theme_park.dungeon.geonosian_madbio_bunker.shutoff");
        setObjVar(shutoff, "trap", self);
        setObjVar(self, "shutoff1", shutoff);
        setObjVar(self, "valve", valve);
        attachScript(shutoff2, "theme_park.dungeon.geonosian_madbio_bunker.shutoff");
        setObjVar(shutoff2, "trap", self);
        setObjVar(self, "valve2", valve2);
        setYaw(shutoff, 180);
        setYaw(shutoff2, 270);
        return;
    }
    public int showGas(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "trap_off"))
        {
            obj_id trap = createObject("object/static/particle/pt_green_hanging_smoke.iff", getLocation(self));
            obj_id invisible = getObjIdObjVar(self, "invisible");
            if (isIdValid(invisible))
            {
                obj_id[] players = getAllPlayers(getLocation(self), 40.0f);
                for (obj_id player : players) {
                    playClientEffectObj(player, "clienteffect/item_gas_leak_trap_lp.cef", invisible, "");
                }
            }
            setObjVar(self, "trap", trap);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkForGasMask(obj_id player) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (obj_id objContent : objContents) {
                String strItemTemplate = getTemplateName(objContent);
                if (strItemTemplate.equals("object/tangible/wearables/goggles/rebreather.iff")) {
                    obj_id mask = objContent;
                    obj_id holder = getContainedBy(mask);
                    if (holder == player) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
