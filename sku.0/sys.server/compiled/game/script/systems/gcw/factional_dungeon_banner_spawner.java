package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;

public class factional_dungeon_banner_spawner extends script.base_script
{
    public factional_dungeon_banner_spawner()
    {
    }
    public int swapFlag(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objFlag = getObjIdObjVar(self, "objFlag");
        destroyObject(objFlag);
        String strType = getStringObjVar(self, "type");
        String strImperial = factions.FACTION_IMPERIAL;
        String strRebel = factions.FACTION_REBEL;
        if (strType.equals(strImperial))
        {
            setObjVar(self, "type", factions.FACTION_REBEL);
        }
        else if (strType.equals(strRebel))
        {
            setObjVar(self, "type", factions.FACTION_IMPERIAL);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        makeFlag(self);
        return SCRIPT_CONTINUE;
    }
    public void checkFactionalSpawners(obj_id self) throws InterruptedException
    {
        obj_id objContainer = getTopMostContainer(self);
        location locTest = getLocation(self);
        if (objContainer != self)
        {
            locTest = getLocation(objContainer);
        }
        obj_id[] objParents = getAllObjectsWithScript(locTest, 250, "systems.gcw.factional_dungeon_parent");
        if (objParents != null)
        {
            if (objParents.length > 0)
            {
                dictionary dctParams = new dictionary();
                dctParams.put("objSpawner", self);
                messageTo(objParents[0], "validateBanner", dctParams, 1, false);
            }
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "intBanner", 1);
        makeFlag(self);
        checkFactionalSpawners(self);
        return SCRIPT_CONTINUE;
    }
    public void makeFlag(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "objFlag"))
        {
            obj_id objTest = getObjIdObjVar(self, "objFlag");
            if (exists(objTest))
            {
                return;
            }
        }
        String strFaction = getStringObjVar(self, "type");
        String strTemplate = "";
        LOG("gcw", "Faction si " + strFaction);
        LOG("gcw", "FACTION_REBEL is " + factions.FACTION_REBEL);
        String strRebel = factions.FACTION_REBEL;
        String strImperial = factions.FACTION_IMPERIAL;
        if (strFaction.equals(strRebel))
        {
            strTemplate = "object/static/structure/general/banner_rebel_style_01.iff";
        }
        else if (strFaction.equals(strImperial))
        {
            strTemplate = "object/static/structure/general/banner_imperial_style_01.iff";
        }
        else 
        {
            LOG("gcw", "object " + self + " at location " + getLocation(self) + " has a bad faction set on it");
            return;
        }
        LOG("gcw", "Making flag");
        obj_id objFlag = createObject(strTemplate, getLocation(self));
        setYaw(objFlag, getYaw(self));
        setObjVar(self, "objFlag", objFlag);
    }
}
