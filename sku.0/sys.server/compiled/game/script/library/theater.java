package script.library;

import script.*;

import java.util.Vector;

public class theater extends script.base_script
{
    public theater()
    {
    }
    public static final float BASE_ITEM_AREA = 3f;
    public static final int PERSIST_TIME = 604800;
    public static final String DICT_MASTER = "master";
    public static final String DICT_CHILDREN = "children";
    public static final int SPAWN_RADIAL = 0;
    public static final int SPAWN_DATATABLE_OFFSET = 1;
    public static final int PERSIST_NONE = 0;
    public static final int PERSIST_ALL = 1;
    public static final int PERSIST_SELF_ONLY = 2;
    public static final String TEMPLATE = "TEMPLATE";
    public static final String X = "X";
    public static final String Y = "Y";
    public static final String Z = "Z";
    public static final String YAW = "YAW";
    public static final String VAR_THEATER_BASE = "theater";
    public static final String VAR_TBL = VAR_THEATER_BASE + ".tbl";
    public static final String VAR_SPAWN_METHOD = VAR_THEATER_BASE + ".spawnMethod";
    public static final String VAR_PERSIST = VAR_THEATER_BASE + ".persist";
    public static final String VAR_PERSIST_TIME = VAR_THEATER_BASE + ".persistTime";
    public static final String VAR_STAMP = VAR_THEATER_BASE + ".stamp";
    public static final String VAR_RING_BASE = VAR_THEATER_BASE + ".ring";
    public static final String VAR_RING_MIN = VAR_RING_BASE + ".min";
    public static final String VAR_RING_MAX = VAR_RING_BASE + ".max";
    public static final String VAR_SPAWN_LIST = VAR_THEATER_BASE + ".spawnList";
    public static final String VAR_CHILDREN = VAR_THEATER_BASE + ".children";
    public static final String VAR_BUILDINGS = VAR_THEATER_BASE + ".buildings";
    public static final String VAR_PARENT = VAR_THEATER_BASE + ".parent";
    public static final String VAR_BEEN_INITIALIZED = "beenInitialized";
    public static final String HANDLE_THEATER_SETUP = "handleTheaterSetup";
    public static final String HANDLE_THEATER_COMPLETE = "handleTheaterComplete";
    public static final int NUM_TO_SPAWN = 3;
    public static final float DELAY_TO_SPAWN = .50f;
    public static boolean canSpawnTheaterAtLocation(location loc) throws InterruptedException
    {
        region[] regionList = getRegionsAtPoint(loc);
        if (regionList != null)
        {
            for (region aRegionList : regionList) {
                if (aRegionList != null) {
                    if (aRegionList.getPvPType() == regions.PVP_REGION_TYPE_BATTLEFIELD_PVP) {
                        return false;
                    }
                    if (aRegionList.getPvPType() == regions.PVP_REGION_TYPE_BATTLEFIELD_PVE) {
                        return false;
                    }
                    if (aRegionList.getGeographicalType() == regions.GEO_CITY) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean spawn(obj_id target) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return false;
        }
        if (!hasObjVar(target, VAR_THEATER_BASE))
        {
            return false;
        }
        int spawnMethod = getIntObjVar(target, VAR_SPAWN_METHOD);
        obj_id[] children = new obj_id[0];
        switch (spawnMethod)
        {
            case SPAWN_RADIAL:
                break;
            case SPAWN_DATATABLE_OFFSET:
                children = spawnDatatableOffset(target);
                break;
            default:
                return false;
        }
        dictionary d = new dictionary();
        if ((children != null) && (children.length != 0)) {
            obj_id[] buildings = utils.getBuildingsInObjIdList(children);
            if ((buildings != null) && (buildings.length != 0)) {
                d.put(DICT_CHILDREN, buildings);
            }
        }
        messageTo(target, HANDLE_THEATER_COMPLETE, d, 2, false);
        return true;
    }
    public static obj_id[] spawnRadial(obj_id target) throws InterruptedException
    {
        float fltSize = 0;
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return null;
        }
        String tbl = getStringObjVar(target, VAR_TBL);
        if ((tbl == null) || (tbl.equals("")))
        {
            return null;
        }
        location myLoc = getLocation(target);
        if (!hasObjVar(target, VAR_RING_BASE))
        {
            return null;
        }
        float ringMin = (float)getIntObjVar(target, VAR_RING_MIN);
        float ringMax = (float)getIntObjVar(target, VAR_RING_MAX);
        if ((ringMin < 0.0f) || (ringMax <= 0.0f) || (ringMin >= ringMax))
        {
            return null;
        }
        float iArea = BASE_ITEM_AREA;
        float sArea = (ringMax - ringMin) / 2;
        if (!hasObjVar(target, VAR_SPAWN_LIST))
        {
            return null;
        }
        obj_var_list spawnList = getObjVarList(target, VAR_SPAWN_LIST);
        if (spawnList == null)
        {
            return null;
        }
        int numListItems = spawnList.getNumItems();
        if (numListItems < 1)
        {
            return null;
        }
        Vector children = new Vector();
        children.setSize(0);
        if (!dataTableOpen(tbl))
        {
            return null;
        }
        String faction = "";
        if (hasObjVar(target, factions.FACTION))
        {
            faction = getStringObjVar(target, factions.FACTION);
        }
        int persistFlag = getIntObjVar(target, VAR_PERSIST);
        switch (persistFlag)
        {
            case PERSIST_ALL:
            case PERSIST_SELF_ONLY:
            persistObject(target);
            break;
            default:
            break;
        }
        obj_var ov;
        String name;
        String[] entries;
        String tpf;
        location spawnLoc;
        location goodLoc;
        obj_id child;

        for (int i = 0; i < numListItems; i++)
        {
            ov = spawnList.getObjVar(i);
            name = ov.getName();
            int amt = ov.getIntData();
            if (dataTableHasColumn(tbl, name))
            {
                entries = dataTableGetStringColumn(tbl, name);
                if ((entries != null) && (entries.length != 0)) {
                    for (int n = 0; n < amt; n++)
                    {
                        int roll = rand(0, entries.length - 1);
                        tpf = entries[roll];
                        while (tpf.equals(""))
                        {
                            roll = rand(0, entries.length - 1);
                            tpf = entries[roll];
                        }
                        spawnLoc = utils.getRandomLocationInRing(myLoc, ringMin, ringMax);
                        if (spawnLoc != null) {
                            goodLoc = locations.getGoodLocationAroundLocation(spawnLoc, iArea, iArea, sArea, sArea);
                            if (goodLoc != null) {
                                spawnLoc = (location)goodLoc.clone();
                            }
                            if (spawnLoc.cell == obj_id.NULL_ID)
                            {
                                spawnLoc.y = getHeightAtLocation(spawnLoc.x, spawnLoc.z);
                            }
                            float fltNewSize = getDistance(myLoc, spawnLoc);
                            if (fltNewSize > fltSize)
							{
								fltSize = fltNewSize;
							}
                            child = createObject(tpf, spawnLoc);
                            if ((child != null) && (child != obj_id.NULL_ID)) {
								setYaw(child, rand(-180, 180));
								children = utils.addElement(children, child);
								if (!faction.equals(""))
								{
									factions.setFaction(child, faction);
								}
								switch (persistFlag)
								{
									case PERSIST_ALL:
									break;
								}
								setObjVar(child, VAR_PARENT, target);
							}
                        }
                    }
                }
            }
        }
        setObjVar(target, "poi.fltSize", fltSize + 20);
        if ((children == null) || (children.size() == 0))
        {
            return null;
        }
        else 
        {
            setObjVar(target, VAR_CHILDREN, children);
        }
        obj_id[] _children = new obj_id[children.size()];
        children.toArray(_children);
        return _children;
    }
    public static obj_id[] spawnDatatableOffset(obj_id target) throws InterruptedException
    {
        float fltSize = 0;
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return null;
        }
        String tbl = getStringObjVar(target, VAR_TBL);
        if ((tbl == null) || (tbl.equals("")))
        {
            return null;
        }
        if (!dataTableOpen(tbl))
        {
            return null;
        }
        String faction;
        if (hasObjVar(target, factions.FACTION))
        {
            faction = getStringObjVar(target, factions.FACTION);
        }
        else 
        {
            faction = utils.getFactionSubString(tbl);
            if (faction == null)
            {
                faction = "";
            }
        }
        int persistFlag = getIntObjVar(target, VAR_PERSIST);
        switch (persistFlag)
        {
            case PERSIST_ALL:
            case PERSIST_SELF_ONLY:
            break;
            default:
            break;
        }
        location myLoc = getLocation(target);
        float tYaw = getYaw(target);
        Vector children = new Vector();
        children.setSize(0);
        Vector objMobSpawners = new Vector();
        objMobSpawners.setSize(0);
        Vector objLargeMobSpawners = new Vector();
        objLargeMobSpawners.setSize(0);
        Vector objObjectiveSpawners = new Vector();
        objObjectiveSpawners.setSize(0);
        Vector objWaypoints = new Vector();
        objWaypoints.setSize(0);
        float[] fltX = dataTableGetFloatColumn(tbl, X);
        float[] fltZ = dataTableGetFloatColumn(tbl, Z);
        float[] fltYaw = dataTableGetFloatColumn(tbl, YAW);
        String[] strTemplates = dataTableGetStringColumn(tbl, TEMPLATE);
        int numRows = strTemplates.length;
        String tpf;
        location here;
        obj_id child;
        for (int i = 0; i < numRows; i++)
        {
            tpf = strTemplates[i];
            float dx = fltX[i];
            float dz = fltZ[i];
            float yaw = fltYaw[i];
            here = (location) myLoc.clone();
            here = player_structure.transformDeltaWorldCoord(here, dx, dz, tYaw);
            float fltNewSize = getDistance(myLoc, here);
            if (fltNewSize > fltSize)
            {
                fltSize = fltNewSize;
            }
            child = createObject(tpf, here);
            if (isIdValid(child)) {
                switch (tpf) {
                    case "object/tangible/poi/theater/poi_mob_spawner.iff":
                        objMobSpawners = utils.addElement(objMobSpawners, child);
                        break;
                    case "object/tangible/poi/theater/poi_objective_spawner.iff":
                        objObjectiveSpawners = utils.addElement(objObjectiveSpawners, child);
                        break;
                    case "object/path_waypoint/path_waypoint.iff":
                        objWaypoints = utils.addElement(objWaypoints, child);
                        break;
                    case "object/tangible/poi/theater/poi_mob_spawner_large.iff":
                        objLargeMobSpawners = utils.addElement(objLargeMobSpawners, child);
                        break;
                }
                setYaw(child, yaw + tYaw);
                children = utils.addElement(children, child);
                if (!faction.equals(""))
                {
                    factions.setFaction(child, faction);
                }
                switch (persistFlag)
                {
                    case PERSIST_ALL:
                    break;
                }
                setObjVar(child, VAR_PARENT, target);
            }
        }
        setObjVar(target, "poi.fltSize", fltSize + 20);
        if (objMobSpawners != null && objMobSpawners.size() > 0)
        {
            setObjVar(target, "theater.objMobSpawners", objMobSpawners);
        }
        if (objMobSpawners != null && objObjectiveSpawners.size() > 0)
        {
            setObjVar(target, "theater.objObjectiveSpawners", objObjectiveSpawners);
        }
        if (objMobSpawners != null && objLargeMobSpawners.size() > 0)
        {
            setObjVar(target, "theater.objLargeMobSpawners", objLargeMobSpawners);
        }
        if ((children == null) || (children.size() == 0))
        {
            return null;
        }
        else 
        {
            setObjVar(target, VAR_CHILDREN, children);
        }
        obj_id[] _children = new obj_id[children.size()];
        children.toArray(_children);
        return _children;
    }
    public static void spawnDatatableOffsetQueued(obj_id target, int intIndex) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return;
        }
        String tbl = getStringObjVar(target, VAR_TBL);
        if ((tbl == null) || (tbl.equals("")))
        {
            return;
        }
        if (!dataTableOpen(tbl))
        {
            return;
        }
        String faction;
        if (hasObjVar(target, factions.FACTION))
        {
            faction = getStringObjVar(target, factions.FACTION);
        }
        else 
        {
            faction = utils.getFactionSubString(tbl);
            if (faction == null)
            {
                faction = "";
            }
        }
        location myLoc = getLocation(target);
        float tYaw = getYaw(target);
        if (!hasObjVar(target, "intPrespawned"))
        {
            setObjVar(target, "intPrespawned", 1);
            Vector objMobSpawners = new Vector();
            objMobSpawners.setSize(0);
            Vector objLargeMobSpawners = new Vector();
            objLargeMobSpawners.setSize(0);
            Vector objObjectiveSpawners = new Vector();
            objObjectiveSpawners.setSize(0);
            Vector objWaypoints = new Vector();
            objWaypoints.setSize(0);
            Vector children = new Vector();
            children.setSize(0);
            float[] fltX = dataTableGetFloatColumn(tbl, X);
            float[] fltZ = dataTableGetFloatColumn(tbl, Z);
            float[] fltYaw = dataTableGetFloatColumn(tbl, YAW);
            String[] strTemplates = dataTableGetStringColumn(tbl, TEMPLATE);
            int numRows = strTemplates.length;
            obj_id child;
            String tpf;
            location here;

            for (int i = 0; i < numRows; i++)
            {
                tpf = strTemplates[i];
                switch (tpf) {
                    case "object/tangible/poi/theater/poi_mob_spawner.iff": {
                        float dx = fltX[i];
                        float dz = fltZ[i];
                        here = (location) myLoc.clone();
                        here = player_structure.transformDeltaWorldCoord(here, dx, dz, tYaw);
                        child = createObject(tpf, here);
                        if (isIdValid(child)) {
                            objMobSpawners = utils.addElement(objMobSpawners, child);
                            children = utils.addElement(children, child);
                        }
                        break;
                    }
                    case "object/tangible/poi/theater/poi_objective_spawner.iff": {
                        float dx = fltX[i];
                        float dz = fltZ[i];
                        here = (location) myLoc.clone();
                        here = player_structure.transformDeltaWorldCoord(here, dx, dz, tYaw);
                        child = createObject(tpf, here);
                        if (isIdValid(child)) {
                            objObjectiveSpawners = utils.addElement(objObjectiveSpawners, child);
                            children = utils.addElement(children, child);
                        }
                        break;
                    }
                    case "object/path_waypoint/path_waypoint.iff": {
                        float dx = fltX[i];
                        float dz = fltZ[i];
                        here = (location) myLoc.clone();
                        here = player_structure.transformDeltaWorldCoord(here, dx, dz, tYaw);
                        child = createObject(tpf, here);
                        if (isIdValid(child)) {
                            objWaypoints = utils.addElement(objWaypoints, child);
                            children = utils.addElement(children, child);
                        }
                        break;
                    }
                    case "object/tangible/poi/theater/poi_mob_spawner_large.iff": {
                        float dx = fltX[i];
                        float dz = fltZ[i];
                        here = (location) myLoc.clone();
                        here = player_structure.transformDeltaWorldCoord(here, dx, dz, tYaw);
                        child = createObject(tpf, here);
                        if (isIdValid(child)) {
                            objLargeMobSpawners = utils.addElement(objLargeMobSpawners, child);
                            children = utils.addElement(children, child);
                        }
                        break;
                    }
                    default:
                        int intStringIndex = tpf.indexOf("corral");
                        if (intStringIndex > -1) {
                            float dx = fltX[i];
                            float dz = fltZ[i];
                            float yaw = fltYaw[i];
                            here = (location) myLoc.clone();
                            here = player_structure.transformDeltaWorldCoord(here, dx, dz, tYaw);
                            child = createObject(tpf, here);
                            if (isIdValid(child)) {
                                setYaw(child, yaw + tYaw);
                                setObjVar(child, VAR_PARENT, target);
                                children = utils.addElement(children, child);
                            }
                        }
                        break;
                }
            }
            if (objMobSpawners.size() > 0)
            {
                setObjVar(target, "theater.objMobSpawners", objMobSpawners);
            }
            if (objObjectiveSpawners.size() > 0)
            {
                setObjVar(target, "theater.objObjectiveSpawners", objObjectiveSpawners);
            }
            if (objLargeMobSpawners.size() > 0)
            {
                setObjVar(target, "theater.objLargeMobSpawners", objLargeMobSpawners);
            }
            if (children.size() > 0)
            {
                setObjVar(target, VAR_CHILDREN, children);
            }
            dictionary dctParams = new dictionary();
            dctParams.put("master", target);
            messageTo(target, HANDLE_THEATER_COMPLETE, dctParams, .50f, false);
            dctParams = new dictionary();
            dctParams.put("intIndex", 0);
            messageTo(target, "continueTheater", dctParams, DELAY_TO_SPAWN, false);
            return;
        }
        else 
        {
            Vector children = getResizeableObjIdArrayObjVar(target, VAR_CHILDREN);
            float[] fltX = dataTableGetFloatColumn(tbl, X);
            float[] fltY = dataTableGetFloatColumn(tbl, Y);
            float[] fltZ = dataTableGetFloatColumn(tbl, Z);
            float[] fltYaw = dataTableGetFloatColumn(tbl, YAW);
            String[] strTemplates = dataTableGetStringColumn(tbl, TEMPLATE);
            int numRows = strTemplates.length;
            for (int i = intIndex; i < intIndex + NUM_TO_SPAWN; i++)
            {
                if (i >= numRows)
                {
                    if (children != null && children.size() > 0)
                    {
                        setObjVar(target, VAR_CHILDREN, children);
                    }
                    else 
                    {
                        if (hasObjVar(target, VAR_CHILDREN))
                        {
                            removeObjVar(target, VAR_CHILDREN);
                        }
                    }
                    dictionary dctParams = new dictionary();
                    dctParams.put("objTheater", target);
                    messageTo(target, "theaterFinished", dctParams, 0, false);
                    return;
                }
                String tpf = strTemplates[i];
                if (shouldSpawn(tpf))
                {
                    float dx = fltX[i];
                    float dy = fltY[i];
                    float dz = fltZ[i];
                    float yaw = fltYaw[i];
                    float fltRadians = (float)Math.toRadians((tYaw * -1f));
                    float fltC = (float)Math.cos(fltRadians);
                    float fltS = (float)Math.sin(fltRadians);
                    location here = (location)myLoc.clone();
                    here.x += (dx * fltC) - (dz * fltS);
                    here.y += dy;
                    here.z += (dx * fltS) + (dz * fltC);
                    obj_id child = createObject(tpf, here);
                    debugSpeakMsg(target, "a child " + child);
                    if ((child != null) && (child != obj_id.NULL_ID)) {
                        setYaw(child, yaw + tYaw);
                        children = utils.addElement(children, child);
                        if (!faction.equals(""))
                        {
                            factions.setFaction(child, faction);
                        }
                        setObjVar(child, VAR_PARENT, target);
                    }
                }
            }
            setObjVar(target, "poi.fltSize", 82);
            if (children != null && children.size() > 0)
            {
                setObjVar(target, VAR_CHILDREN, children);
            }
            else 
            {
                if (hasObjVar(target, VAR_CHILDREN))
                {
                    removeObjVar(target, VAR_CHILDREN);
                }
            }
        }
        dictionary dctParams = new dictionary();
        dctParams.put("intIndex", intIndex + NUM_TO_SPAWN);
        messageTo(target, "continueTheater", dctParams, DELAY_TO_SPAWN, false);
    }
    public static boolean shouldSpawn(String tpf) throws InterruptedException
    {
        switch (tpf) {
            case "object/tangible/poi/theater/poi_mob_spawner.iff":
            case "object/tangible/poi/theater/poi_objective_spawner.iff":
            case "object/path_waypoint/path_waypoint.iff":
            case "object/tangible/poi/theater/poi_mob_spawner_large.iff":
                return false;
            default:
                int intStringIndex = tpf.indexOf("corral");
                if (intStringIndex > -1) {
                    return false;
                }
                break;
        }
        return true;
    }
    public static boolean cleanup(obj_id target) throws InterruptedException
    {
        location locTest = getLocation(target);
        region rgnPathRegion = getRegion(target.toString(), locTest.area);
        if (rgnPathRegion != null) {
            deleteRegion(rgnPathRegion);
        }
        if ((target == obj_id.NULL_ID))
        {
            return false;
        }
        if (!hasObjVar(target, VAR_CHILDREN))
        {
            return false;
        }
        obj_id[] children = getObjIdArrayObjVar(target, VAR_CHILDREN);
        if ((children == null) || (children.length == 0))
        {
            return false;
        }
        for (obj_id child : children) {
            if ((child != null) && (child != obj_id.NULL_ID)) {
                destroyObject(child);
            }
        }
        return true;
    }
}
