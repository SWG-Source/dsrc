package script.systems.spawning;

import script.dictionary;
import script.obj_id;

public class spawner_find_node extends script.base_script
{
    public spawner_find_node()
    {
    }
    public static final int DEFAULT_SEARCH_RADIUS = 100;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "findNode", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int findNode(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "spawner_find_node.findNode() Master Object: " + self + " is looking for its node");
        if (hasObjVar(self, "node"))
        {
            obj_id node = getObjIdObjVar(self, "node");
            if (isValidId(node) && exists(node))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (!hasObjVar(self, "nodeSearchObjVar") || (getStringObjVar(self, "nodeSearchObjVar")).length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "spawner_find_node.findNode() Master Object didn't have objvar to search for. Aborting.");
            return SCRIPT_CONTINUE;
        }
        String nodeSearchObjVar = getStringObjVar(self, "nodeSearchObjVar");
        if (nodeSearchObjVar == null || nodeSearchObjVar.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "spawner_find_node.findNode() Master Object failed validation for node objvar. Aborting.");
            return SCRIPT_CONTINUE;
        }
        int radius = DEFAULT_SEARCH_RADIUS;
        if (hasObjVar(self, "nodeSearchRadius") && getIntObjVar(self, "nodeSearchRadius") > 0)
        {
            radius = getIntObjVar(self, "nodeSearchRadius");
        }
        obj_id[] nodeList = getAllObjectsWithObjVar(getLocation(self), radius, nodeSearchObjVar);
        if (nodeList == null || nodeList.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "spawner_find_node.findNode() the nodeList for the master spawner was null or could not find a node");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "parentNode", nodeList[0]);
        return SCRIPT_CONTINUE;
    }
}
