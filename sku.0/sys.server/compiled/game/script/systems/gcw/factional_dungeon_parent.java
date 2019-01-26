package script.systems.gcw;

import script.dictionary;
import script.library.factions;
import script.library.hq;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class factional_dungeon_parent extends script.base_script
{
    public factional_dungeon_parent()
    {
    }
    public static final int OBJECTIVE_RESPAWN_TIME = 28800;
    public static final String IMPERIAL_TO_REBEL_FILENAME = "datatables/poi/imperial_rebel_lookup/imperial_to_rebel.iff";
    public static final String REBEL_TO_IMPERIAL_FILENAME = "datatables/poi/imperial_rebel_lookup/rebel_to_imperial.iff";
    public int resetDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        String strFaction = getStringObjVar(self, "strFaction");
        int intFaction = factions.getFactionNumber(strFaction);
        factions.setFaction(self, strFaction, false);
        Vector objTerminals = new Vector();
        objTerminals.setSize(0);
        Vector strTerminals = new Vector();
        strTerminals.setSize(0);
        Vector locTerminals = new Vector();
        locTerminals.setSize(0);
        Vector fltYaw = new Vector();
        fltYaw.setSize(0);
        obj_id[] objSpawners = getFactionalDungeonSpawners(self);
        for (obj_id objSpawner : objSpawners) {
            if (hasObjVar(objSpawner, "intBanner")) {
                String strOldFaction = getStringObjVar(objSpawner, "type");
                if (!strOldFaction.equals(strFaction)) {
                    messageTo(objSpawner, "swapFlag", null, 0, true);
                }
            } else if (hasObjVar(objSpawner, "intTerminal")) {
                if (hasObjVar(self, "intCompleted")) {
                    destroyObject(objSpawner);
                } else {
                    objTerminals = utils.addElement(objTerminals, objSpawner);
                    strTerminals = utils.addElement(strTerminals, getTemplateName(objSpawner));
                    locTerminals = utils.addElement(locTerminals, getLocation(objSpawner));
                    fltYaw = utils.addElement(fltYaw, getYaw(objSpawner));
                    removeObjVar(objSpawner, "hq");
                    setObjVar(objSpawner, "objParent", self);
                    factions.setFaction(objSpawner, strFaction, false);
                }
            } else {
                changeMobs(objSpawner, strFaction);
            }
        }
        if (!hasObjVar(self, "intCompleted"))
        {
            if ((locTerminals.size() > 0) && (strTerminals.size() > 0) && (objTerminals.size() > 0) && (fltYaw.size() > 0))
            {
                setObjVar(self, "locTerminals", locTerminals);
                setObjVar(self, "strTerminals", strTerminals);
                setObjVar(self, "objTerminals", objTerminals);
                setObjVar(self, "fltYaw", fltYaw);
                setupTerminals(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void setupTerminals(obj_id self) throws InterruptedException
    {
        obj_id[] objTerminals = getObjIdArrayObjVar(self, "objTerminals");
        String[] strTerminals = getStringArrayObjVar(self, "strTerminals");
        obj_id[] objChildTerminals = new obj_id[4];
        if ((objTerminals == null) || (strTerminals == null))
        {
            return;
        }
        for (int intI = 0; intI < objTerminals.length; intI++)
        {
            String strTest = strTerminals[intI];
            switch (strTest) {
                case "object/tangible/gcw/uplink_terminal.iff":
                    objChildTerminals[0] = objTerminals[intI];
                    break;
                case "object/tangible/gcw/security_terminal.iff":
                    objChildTerminals[1] = objTerminals[intI];
                    break;
                case "object/tangible/gcw/override_terminal.iff":
                    objChildTerminals[2] = objTerminals[intI];
                    break;
                case "object/tangible/gcw/power_regulator.iff":
                    objChildTerminals[3] = objTerminals[intI];
                    break;
            }
        }
        setObjVar(self, hq.VAR_OBJECTIVE_ID, objChildTerminals);
    }
    public int objectiveCompleted(obj_id self, dictionary params) throws InterruptedException
    {
        String strRebel = factions.FACTION_REBEL;
        String strImperial = factions.FACTION_IMPERIAL;
        if (!hasObjVar(self, "intCompleted"))
        {
            setObjVar(self, "intCompleted", 1);
            String strFaction = getStringObjVar(self, "strFaction");
            int intPoints = getIntObjVar(self, "intPoints");
            if (intPoints < 1)
            {
                intPoints = 1;
            }
            if (strFaction.equals(strImperial))
            {
                setObjVar(self, "strFaction", factions.FACTION_REBEL);
                factions.shiftPointsTo(factions.FACTION_REBEL, intPoints);
                factions.setFaction(self, factions.FACTION_REBEL, false);
            }
            else if (strFaction.equals(strRebel))
            {
                setObjVar(self, "strFaction", factions.FACTION_IMPERIAL);
                factions.shiftPointsTo(factions.FACTION_IMPERIAL, intPoints);
                factions.setFaction(self, factions.FACTION_IMPERIAL, false);
            }
            obj_id[] objSpawners = getFactionalDungeonSpawners(self);
            for (obj_id objSpawner : objSpawners) {
                if (hasObjVar(objSpawner, "intBanner")) {
                    messageTo(objSpawner, "swapFlag", null, 0, true);
                } else if (hasObjVar(objSpawner, "intTerminal")) {
                } else {
                    swapMobs(objSpawner, strFaction);
                }
            }
            obj_id[] objTerminals = getObjIdArrayObjVar(self, "objTerminals");
            for (obj_id objTerminal : objTerminals) {
                destroyObject(objTerminal);
            }
            messageTo(self, "respawnObjective", null, OBJECTIVE_RESPAWN_TIME, true);
        }
        removeObjVar(self, "hq");
        return SCRIPT_CONTINUE;
    }
    public int respawnObjective(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "intCompleted");
        location[] locSpawners = getLocationArrayObjVar(self, "locTerminals");
        String[] strTerminals = getStringArrayObjVar(self, "strTerminals");
        float[] fltYaw = getFloatArrayObjVar(self, "fltYaw");
        Vector objTerminals = new Vector();
        objTerminals.setSize(0);
        for (int intI = 0; intI < locSpawners.length; intI++)
        {
            obj_id objTerminal = createObject(strTerminals[intI], locSpawners[intI]);
            objTerminals = utils.addElement(objTerminals, objTerminal);
            setObjVar(objTerminal, "objParent", self);
            setYaw(objTerminal, fltYaw[intI]);
            persistObject(objTerminal);
        }
        if (objTerminals.size() > 0)
        {
            setObjVar(self, "objTerminals", objTerminals);
            setupTerminals(self);
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getFactionalDungeonSpawners(obj_id objParent) throws InterruptedException
    {
        Vector objSpawners = new Vector();
        objSpawners.setSize(0);
        obj_id[] objObjects = getAllObjectsWithObjVar(getLocation(objParent), 250, "type");
        for (obj_id objObject : objObjects) {
            obj_id[] objContents = getContents(objObject);
            if (objContents != null) {
                for (obj_id objContent : objContents) {
                    if (hasObjVar(objContent, "type")) {
                        objSpawners = utils.addElement(objSpawners, objContent);
                    }
                }
            } else {
                objSpawners = utils.addElement(objSpawners, objObject);
            }
        }
        obj_id[] _objSpawners = new obj_id[0];
        if (objSpawners != null)
        {
            _objSpawners = new obj_id[objSpawners.size()];
            objSpawners.toArray(_objSpawners);
        }
        return _objSpawners;
    }
    public void swapMobs(obj_id objSpawner, String strFaction) throws InterruptedException
    {
        if (!isIdValid(objSpawner) || strFaction == null || strFaction.equals(""))
        {
            return;
        }
        String strRebel = factions.FACTION_REBEL;
        String strImperial = factions.FACTION_IMPERIAL;
        String strType = getStringObjVar(objSpawner, "type");
        if (strType == null || strType.equals(""))
        {
            return;
        }
        if (strFaction.equals(strRebel))
        {
            dictionary dctSwitchInfo = dataTableGetRow(REBEL_TO_IMPERIAL_FILENAME, strType);
            String strNewType = dctSwitchInfo.getString("Imperial");
            setObjVar(objSpawner, "type", strNewType);
            if (strNewType.equals("at_st"))
            {
                setObjVar(objSpawner, "pop", 1);
            }
        }
        else if (strFaction.equals(strImperial))
        {
            dictionary dctSwitchInfo = dataTableGetRow(IMPERIAL_TO_REBEL_FILENAME, strType);
            String strNewType = dctSwitchInfo.getString("Rebel");
            if (strType.equals("at_st"))
            {
                setObjVar(objSpawner, "pop", 5);
            }
            setObjVar(objSpawner, "type", strNewType);
        }
        return;
    }
    public void changeMobs(obj_id objSpawner, String strFaction) throws InterruptedException
    {
        String strType = getStringObjVar(objSpawner, "type");
        String strImperial = factions.FACTION_IMPERIAL;
        String strRebel = factions.FACTION_REBEL;
        dictionary dctInfo = dataTableGetRow(IMPERIAL_TO_REBEL_FILENAME, strType);
        String strMob = null;
        if (dctInfo != null)
        {
            strMob = dctInfo.getString("Imperial");
        }
        if (strMob != null)
        {
            if (!strFaction.equals(strImperial))
            {
                swapMobs(objSpawner, factions.FACTION_IMPERIAL);
            }
        }
        else 
        {
            if (!strFaction.equals(strRebel))
            {
                swapMobs(objSpawner, factions.FACTION_REBEL);
            }
        }
    }
    public void validateMobs(obj_id objSpawner, String strType, String strFaction) throws InterruptedException
    {
        String strImperial = factions.FACTION_IMPERIAL;
        String strRebel = factions.FACTION_REBEL;
        if (strFaction.equals(strImperial))
        {
            dictionary dctInfo = dataTableGetRow(IMPERIAL_TO_REBEL_FILENAME, strType);
            if (dctInfo == null)
            {
                changeMobs(objSpawner, strFaction);
            }
        }
        else if (strFaction.equals(strRebel))
        {
            dictionary dctInfo = dataTableGetRow(REBEL_TO_IMPERIAL_FILENAME, strType);
            if (dctInfo == null)
            {
                changeMobs(objSpawner, strFaction);
            }
        }
    }
    public int checkSpawner(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objSpawner = params.getObjId("objSpawner");
        String strFaction = getStringObjVar(self, "strFaction");
        String strType = getStringObjVar(objSpawner, "type");
        validateMobs(objSpawner, strType, strFaction);
        return SCRIPT_CONTINUE;
    }
    public int checkBanner(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objSpawner = params.getObjId("objSpawner");
        String strType = getStringObjVar(objSpawner, "type");
        String strFaction = getStringObjVar(self, "strFaction");
        if (!strType.equals(strFaction))
        {
            messageTo(objSpawner, "swapFlag", null, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
}
