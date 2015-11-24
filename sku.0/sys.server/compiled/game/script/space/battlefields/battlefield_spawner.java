package script.space.battlefields;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.group;
import script.library.objvar_mangle;
import script.library.space_utils;
import script.library.space_create;
import script.library.load_test;
import script.library.prose;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.space_transition;
import script.library.space_battlefield;
import script.library.utils;
import java.lang.Math;

public class battlefield_spawner extends script.base_script
{
    public battlefield_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "strDefaultBehavior", "specialty");
        messageTo(self, "setupAttackTarget", null, 2, false);
        setupPatrolPaths(self);
        return SCRIPT_CONTINUE;
    }
    public int setupAttackTarget(obj_id self, dictionary params) throws InterruptedException
    {
        String strTargetName = getStringObjVar(self, "strTargetName");
        obj_id[] objTargets = getAllObjectsWithObjVar(getLocation(self), 320000, "strMyName");
        if (objTargets != null)
        {
            for (int intI = 0; intI < objTargets.length; intI++)
            {
                String strMyName = getStringObjVar(objTargets[intI], "strMyName");
                if (strTargetName.equals(strMyName))
                {
                    setObjVar(self, "objAttackTarget", objTargets[intI]);
                    intI = objTargets.length + 1;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int singleAttackerSpawned(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objShip = params.getObjId("objShip");
        transform[] trPath = getRandomPathsFromSpawner(self);
        transform trTest = trPath[trPath.length - 1];
        location locTest = utils.getLocationFromTransform(trTest);
        ship_ai.spacePatrol(objShip, trPath);
        return SCRIPT_CONTINUE;
    }
    public int squadAttackerSpawned(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objMembers = params.getObjIdArray("objMembers");
        int intSquadId = ship_ai.unitGetSquadId(objMembers[0]);
        transform[] trPath = getRandomPathsFromSpawner(self);
        if ((trPath == null) || (trPath.length == 0))
        {
        }
        for (int intI = 0; intI < trPath.length; intI++)
        {
            location locFoo = space_utils.getLocationFromTransform(trPath[intI]);
        }
        transform trTest = trPath[trPath.length - 1];
        location locTest = utils.getLocationFromTransform(trTest);
        ship_ai.squadAddPatrolPath(intSquadId, trPath);
        return SCRIPT_CONTINUE;
    }
    public void setupPatrolPaths(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "strPaths"))
        {
            String[] strPaths = objvar_mangle.getMangledStringArrayObjVar(self, "strPaths");
            int[] intPathCounts = getIntArrayObjVar(self, "intPathCounts");
            for (int intM = 0; intM < strPaths.length; intM++)
            {
                int intPointCount = intPathCounts[intM];
                Vector trPatrolPoints = new Vector();
                trPatrolPoints.setSize(0);
                obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/tangible/space/content_infrastructure/basic_patrol_point.iff");
                if (objTestObjects == null)
                {
                }
                for (int intI = 0; intI < intPointCount; intI++)
                {
                    for (int intJ = 0; intJ < objTestObjects.length; intJ++)
                    {
                        if (hasObjVar(objTestObjects[intJ], "strName"))
                        {
                            String strName = getStringObjVar(objTestObjects[intJ], "strName");
                            int intCheck = intI + 1;
                            String strTest = "";
                            if (intCheck < 10)
                            {
                                strTest = strPaths[intM] + "_0";
                            }
                            else 
                            {
                                strTest = strPaths[intM] + "_";
                            }
                            strTest += intCheck;
                            if (strName.equals(strTest))
                            {
                                trPatrolPoints = utils.addElement(trPatrolPoints, getTransform_o2w(objTestObjects[intJ]));
                                intJ = objTestObjects.length + 10;
                            }
                        }
                    }
                }
                for (int intI = 0; intI < trPatrolPoints.size(); intI++)
                {
                    location locTest = space_utils.getLocationFromTransform(((transform)trPatrolPoints.get(intI)));
                }
                utils.setLocalVar(self, "trPath" + strPaths[intM], trPatrolPoints);
            }
        }
        return;
    }
    public transform[] getRandomPathsFromSpawner(obj_id objSpawner) throws InterruptedException
    {
        if (!hasObjVar(objSpawner, "strPaths"))
        {
            return null;
        }
        String[] strPaths = objvar_mangle.getMangledStringArrayObjVar(objSpawner, "strPaths");
        int intRoll = rand(0, strPaths.length - 1);
        return utils.getTransformArrayLocalVar(objSpawner, "trPath" + strPaths[intRoll]);
    }
}
