package script.systems.storyteller.events;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.create;
import script.library.storyteller;
import script.library.static_item;
import script.library.utils;
import script.library.performance;

public class figrin_dan_band_spawner extends script.base_script
{
    public figrin_dan_band_spawner()
    {
    }
    public static final String DATATABLE_BASE = "datatables/spawning/holiday/";
    public static final String DATATABLE_OBJVAR = "figrinDatatable";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Initialized Figrin Dan Band spawner script");
        messageTo(self, "spawnFigrinDanBand", null, 9, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id breecher) throws InterruptedException
    {
        if (!isPlayer(breecher))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (!hasObjVar(self, DATATABLE_OBJVAR))
            {
                return SCRIPT_CONTINUE;
            }
            String datatable = DATATABLE_BASE + getStringObjVar(self, DATATABLE_OBJVAR) + ".iff";
            if (dataTableHasColumn(datatable, "badge"))
            {
                String badgeName = dataTableGetString(datatable, 0, "badge");
                if (badgeName != null || badgeName.length() > 0)
                {
                    if (!badge.hasBadge(breecher, badgeName))
                    {
                        dictionary explorerBadges = new dictionary();
                        explorerBadges.put("badgeName", badgeName);
                        messageTo(breecher, "explorerBadge", explorerBadges, 0, false);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnBithBand(self);
        return;
    }
    public void spawnBithBand(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, DATATABLE_OBJVAR))
        {
            return;
        }
        String datatable = DATATABLE_BASE + getStringObjVar(self, DATATABLE_OBJVAR) + ".iff";
        location here = getLocation(self);
        float myYaw = getYaw(self);
        String[] bandMembers = dataTableGetStringColumn(datatable, "spawn_who");
        if (bandMembers == null || bandMembers.length < 1)
        {
            return;
        }
        for (int i = 0; i < bandMembers.length; i++)
        {
            dictionary webster = dataTableGetRow(datatable, i);
            float x_offset = webster.getFloat("x_offset");
            float y_offset = webster.getFloat("y_offset");
            float z_offset = webster.getFloat("z_offset");
            location tempLoc = new location((here.x + x_offset), here.y, (here.z + z_offset), here.area, null);
            float bandMemberYaw = webster.getFloat("yaw");
            location bandMemberLoc = (location)tempLoc.clone();
            location omni_box_loc = (location)tempLoc.clone();
            if (myYaw != 0)
            {
                bandMemberLoc = storyteller.rotateLocationXZ(here, tempLoc, myYaw);
                bandMemberYaw -= (0 - myYaw);
            }
            obj_id bandMember = create.object(bandMembers[i], bandMemberLoc);
            ai_lib.setDefaultCalmBehavior(bandMember, ai_lib.BEHAVIOR_SENTINEL);
            if (dataTableHasColumn(datatable, "objvar_name"))
            {
                String objvar = webster.getString("objvar_name");
                if (dataTableHasColumn(datatable, "objvar_value"))
                {
                    int objvarInt = webster.getInt("objvar_value");
                    if (objvarInt > 0)
                    {
                        setObjVar(bandMember, objvar, objvarInt);
                    }
                    else if (objvar != null && !objvar.equals("none"))
                    {
                        setObjVar(bandMember, objvar, 1);
                    }
                }
                else if (objvar != null && !objvar.equals("none"))
                {
                    setObjVar(bandMember, objvar, 1);
                }
            }
            setObjVar(bandMember, performance.NPC_ENTERTAINMENT_NO_ENTERTAIN, 1);
            setYaw(bandMember, bandMemberYaw);
            if (dataTableHasColumn(datatable, "creatureStatic"))
            {
                int objvarInt = webster.getInt("creatureStatic");
                if (objvarInt == 1)
                {
                    setCreatureStatic(bandMember, true);
                }
            }
            else 
            {
                setCreatureStatic(bandMember, true);
            }
            setInvulnerable(bandMember, true);
            setName(bandMember, webster.getString("name"));
            String npcDefaultMood = webster.getString("defaultCalmMood");
            if (npcDefaultMood != null && npcDefaultMood.length() > 0)
            {
                ai_lib.setDefaultCalmMood(bandMember, npcDefaultMood);
            }
            String instrumentTemplate = webster.getString("instrument");
            int instrumentEquipped = webster.getInt("instrumentEquipped");
            if (instrumentTemplate != null && instrumentTemplate.length() > 0 && !instrumentTemplate.equals("none"))
            {
                if (instrumentEquipped > 0)
                {
                    obj_id instrument = obj_id.NULL_ID;
                    if (static_item.isStaticItem(instrumentTemplate))
                    {
                        obj_id inv = utils.getInventoryContainer(bandMember);
                        instrument = static_item.createNewItemFunction(instrumentTemplate, inv);
                    }
                    else 
                    {
                        instrument = createObject(instrumentTemplate, bandMember, "");
                    }
                    equip(instrument, bandMember);
                }
                else 
                {
                    float instrument_x_offset = webster.getFloat("instrument_x_offset");
                    float instrument_z_offset = webster.getFloat("instrument_z_offset");
                    omni_box_loc.x = omni_box_loc.x + instrument_x_offset;
                    omni_box_loc.z = omni_box_loc.z + instrument_z_offset;
                    if (myYaw != 0)
                    {
                        omni_box_loc = storyteller.rotateLocationXZ(here, omni_box_loc, myYaw);
                    }
                    obj_id omni_box = createObject(instrumentTemplate, omni_box_loc);
                    setYaw(omni_box, bandMemberYaw);
                    setObjVar(self, "spawned.instrument" + i, omni_box);
                }
            }
            String varPrefix = "spawned.";
            String varPostfix = "bandMember" + i;
            if (webster.containsKey("tracking_name"))
            {
                String trackName = webster.getString("tracking_name");
                if (trackName != null && !trackName.equals(""))
                {
                    varPostfix = trackName;
                }
            }
            setObjVar(self, varPrefix + varPostfix, bandMember);
        }
        return;
    }
    public location rotateXZ(location locOrigin, location locPoint, float fltAngle) throws InterruptedException
    {
        float dx = locPoint.x - locOrigin.x;
        float dz = locPoint.z - locOrigin.z;
        float fltRadians = (float)Math.toRadians(fltAngle);
        float fltC = (float)Math.cos(fltRadians);
        float fltS = (float)Math.sin(fltRadians);
        location locNewOffset = (location)locOrigin.clone();
        locNewOffset.x += (dx * fltC) + (dz * fltS);
        locNewOffset.y = locPoint.y;
        locNewOffset.z += -(dx * fltS) + (dz * fltC);
        return locNewOffset;
    }
    public void destroyTheBand(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, DATATABLE_OBJVAR))
        {
            return;
        }
        String datatable = DATATABLE_BASE + getStringObjVar(self, DATATABLE_OBJVAR) + ".iff";
        location here = getLocation(self);
        float myYaw = getYaw(self);
        String[] bandMembers = dataTableGetStringColumn(datatable, "spawn_who");
        if (bandMembers == null || bandMembers.length < 1)
        {
            return;
        }
        for (int i = 0; i < bandMembers.length; i++)
        {
            dictionary webster = dataTableGetRow(datatable, i);
            String bandMemberPrefix = "spawned.";
            String bandMemberPostfix = "bandMember" + i;
            if (webster.containsKey("tracking_name"))
            {
                String trackName = webster.getString("tracking_name");
                if (trackName != null && !trackName.equals(""))
                {
                    bandMemberPostfix = trackName;
                }
            }
            if (hasObjVar(self, bandMemberPrefix + bandMemberPostfix))
            {
                obj_id bandMember = getObjIdObjVar(self, bandMemberPrefix + bandMemberPostfix);
                if (isIdValid(bandMember) && exists(bandMember))
                {
                    destroyObject(bandMember);
                    removeObjVar(self, "spawned.bandMember" + i);
                }
                if (hasObjVar(self, "spawned.instrument" + i))
                {
                    obj_id instrument = getObjIdObjVar(self, "spawned.instrument" + i);
                    if (isIdValid(instrument) && exists(instrument))
                    {
                        destroyObject(instrument);
                        removeObjVar(self, "spawned.instrument" + i);
                    }
                }
            }
        }
        return;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        destroyTheBand(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnFigrinDanBand(obj_id self, dictionary params) throws InterruptedException
    {
        createTriggerVolume("figrin_dan_band_badge", 10f, true);
        destroyTheBand(self);
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public int setOriginalMood(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id soldier = params.getObjId("soldierObjId");
        int soldierNumber = params.getInt("objvarValue");
        String datatable = DATATABLE_BASE + getStringObjVar(self, DATATABLE_OBJVAR) + ".iff";
        int row = dataTableSearchColumnForInt(soldierNumber, "objvar_value", datatable);
        if (soldierNumber < 1 || !isIdValid(soldier))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = dataTableGetRow(datatable, row);
        String npcDefaultMood = webster.getString("defaultCalmMood");
        if (npcDefaultMood != null && npcDefaultMood.length() > 0)
        {
            ai_lib.setDefaultCalmMood(soldier, npcDefaultMood);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isGod(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("let_the_band_play"))
        {
            destroyTheBand(self);
            spawnEveryone(self);
            sendSystemMessage(speaker, "let_the_band_play...spawning the band.", "");
            return SCRIPT_OVERRIDE;
        }
        if (text.equals("the_band_is_done"))
        {
            destroyTheBand(self);
            sendSystemMessage(speaker, "the_band_is_done...destroying the band.", "");
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
