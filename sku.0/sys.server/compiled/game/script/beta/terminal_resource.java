package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.resource;
import script.library.sui;
import script.library.utils;
import script.library.prose;

public class terminal_resource extends script.terminal.base.terminal_add_use
{
    public terminal_resource()
    {
    }
    public static final int AMT = 100000;
    public static final String[] RESOURCE_BASE_TYPES = 
    {
        "creature_resources",
        "flora_resources",
        "chemical",
        "water",
        "mineral",
        "gas",
        "energy",
        "space_metal",
        "space_gas",
        "space_chemical",
        "space_gem"
    };
    public static final String[] RESOURCE_PLANETS = 
    {
        "current",
        "tatooine",
        "naboo",
        "corellia",
        "rori",
        "talus",
        "endor",
        "dantooine",
        "dathomir",
        "lok",
        "yavin4"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isGod(player) && !hasObjVar(player, "beta.terminal_ok"))
        {
            return SCRIPT_CONTINUE;
        }
        int use = -1;
        menu_info_data[] root = mi.getRootMenuItems();
        for (int i = 0; i < root.length; i++)
        {
            if (root[i].getType() == menu_info_types.ITEM_USE)
            {
                use = root[i].getId();
            }
        }
        if (use != -1)
        {
            mi.addSubMenu(use, menu_info_types.SERVER_MENU1, new string_id("beta", "resource_standard"));
            mi.addSubMenu(use, menu_info_types.SERVER_MENU2, new string_id("beta", "resource_planet"));
            mi.addSubMenu(use, menu_info_types.SERVER_MENU3, new string_id("beta", "resource_set_planet"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isGod(player) || hasObjVar(player, "beta.terminal_ok"))
        {
            if ((item == menu_info_types.ITEM_USE) || (item == menu_info_types.SERVER_MENU1))
            {
                String prompt = "Select the desired resource category";
                String title = "BETA - Resource Dispenser";
                sui.listbox(self, player, prompt, sui.OK_CANCEL, title, RESOURCE_BASE_TYPES, "handleCategorySelection", true, false);
            }
            if (item == menu_info_types.SERVER_MENU2)
            {
                String prompt = "Select the desired planet";
                String title = "BETA - Resource Dispenser";
                sui.listbox(self, player, prompt, sui.OK_CANCEL, title, RESOURCE_PLANETS, "handlePlanetSelection", true, false);
            }
            if (item == menu_info_types.SERVER_MENU3)
            {
                String prompt = "Select the desired planet";
                String title = "BETA - Resource Dispenser";
                sui.listbox(self, player, prompt, sui.OK_CANCEL, title, RESOURCE_PLANETS, "handleSetPlanet", true, false);
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Only authorized users may access this terminal.");
            return SCRIPT_CONTINUE;
        }
    }
    public int handleCategorySelection(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(player);
        String planet = "current";
        if (utils.hasScriptVar(self, "planet"))
        {
            planet = utils.getStringScriptVar(self, "planet");
        }
        else if (hasObjVar(self, "planet"))
        {
            planet = getStringObjVar(self, "planet");
        }
        if (!planet.equals("current") && !planet.equals("all"))
        {
            loc = new location(0, 0, 0, planet);
        }
        if (loc == null)
        {
            sendSystemMessageTestingOnly(player, "Error retrieving planet data");
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        String[] resourceList = null;
        if (planet.equals("all"))
        {
            resourceList = buildAllAvailableResourceTree(RESOURCE_BASE_TYPES[idx]);
        }
        else 
        {
            resourceList = buildAvailableResourceTree(loc, RESOURCE_BASE_TYPES[idx]);
        }
        String prompt = "Select the desired resource category";
        String title = "BETA - Resource Dispenser";
        sui.listbox(self, player, prompt, sui.OK_CANCEL, title, resourceList, "handleResourceSelection", true, false);
        utils.setScriptVar(self, "resourceList", resourceList);
        return SCRIPT_CONTINUE;
    }
    public int handlePlanetSelection(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        String planet = RESOURCE_PLANETS[idx];
        utils.setScriptVar(self, "planet", planet);
        String prompt = "Select the desired resource category";
        String title = "BETA - Resource Dispenser";
        sui.listbox(self, player, prompt, sui.OK_CANCEL, title, RESOURCE_BASE_TYPES, "handleCategorySelection", true, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSetPlanet(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        String planet = RESOURCE_PLANETS[idx];
        setObjVar(self, "planet", planet);
        sendSystemMessageTestingOnly(player, "Planet set to " + planet);
        return SCRIPT_CONTINUE;
    }
    public int handleResourceSelection(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        String[] resourceList = utils.getStringArrayScriptVar(self, "resourceList");
        String resourceName = resourceList[idx].trim();
        if (resourceName.startsWith("\\#"))
        {
            resourceName = resourceName.substring(13, resourceName.length() - 3);
        }
        obj_id resourceId = getResourceTypeByName(resourceName);
        if (resourceId == null)
        {
            resourceId = pickRandomNonDepeletedResource(resourceName);
        }
        obj_id inv = utils.getInventoryContainer(player);
        obj_id resourceCrate = createResourceCrate(resourceId, AMT, inv);
        if (resourceCrate == null)
        {
            sendSystemMessageTestingOnly(player, "Resource grant failed. It is likely your inventory is full.");
        }
        else 
        {
            resourceName = " \\#pcontrast1 " + getResourceName(resourceId) + "\\#. a type of " + getClassString(getResourceClass(resourceId));
            prose_package proseSuccess = prose.getPackage(resource.SID_SAMPLE_LOCATED, resourceName, AMT);
            sendSystemMessageProse(player, proseSuccess);
        }
        return SCRIPT_CONTINUE;
    }
    public String[] buildAvailableResourceTree(location loc, String topParent) throws InterruptedException
    {
        resource_density[] resources = requestResourceList(loc, 0.0f, 1.0f, topParent);
        String[] resourceTree = buildSortedResourceTree(resources, topParent, 0);
        return resourceTree;
    }
    public String[] buildAllAvailableResourceTree(String topParent) throws InterruptedException
    {
        Vector allResources = new Vector();
        allResources.setSize(0);
        for (int i = 1; i < (RESOURCE_PLANETS.length - 1); i++)
        {
            resource_density[] resources = requestResourceList(new location(0, 0, 0, RESOURCE_PLANETS[i]), 0.0f, 1.0f, topParent);
            allResources = utils.concatArrays(allResources, resources);
        }
        resource_density[] resources = new resource_density[allResources.size()];
        allResources.toArray(resources);
        String[] resourceTree = buildSortedResourceTree(resources, topParent, 0);
        return resourceTree;
    }
    public String[] buildSortedResourceTree(resource_density[] resources, String topParent, int branchLevel) throws InterruptedException
    {
        Vector resourceTree = new Vector();
        resourceTree.setSize(0);
        if (resources != null)
        {
            for (int i = 0; i < resources.length; i++)
            {
                if (!isResourceDerivedFrom(resources[i].getResourceType(), topParent))
                {
                    continue;
                }
                String parent = getResourceClass(resources[i].getResourceType());
                String child = null;
                if (parent == null)
                {
                    continue;
                }
                while (!parent.equals(topParent))
                {
                    child = parent;
                    parent = getResourceParentClass(child);
                }
                if (child == null)
                {
                    child = "\\#pcontrast1 " + getResourceName(resources[i].getResourceType()) + "\\#.";
                }
                for (int j = 0; j < branchLevel; j++)
                {
                    child = "    " + child;
                }
                if (resourceTree.indexOf(child) == -1)
                {
                    resourceTree.add(child);
                }
            }
        }
        for (int i = 0; i < resourceTree.size(); i++)
        {
            String parent = ((String)resourceTree.get(i)).trim();
            String[] childBranch = buildSortedResourceTree(resources, parent, branchLevel + 1);
            for (int j = 0; j < childBranch.length; j++)
            {
                resourceTree.add(++i, childBranch[j]);
            }
        }
        String[] _resourceTree = new String[0];
        if (resourceTree != null)
        {
            _resourceTree = new String[resourceTree.size()];
            resourceTree.toArray(_resourceTree);
        }
        return _resourceTree;
    }
    public void cleanScriptVars() throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVar(self, "resourceList");
        utils.removeScriptVar(self, "planet");
    }
    public String getClassString(String className) throws InterruptedException
    {
        final String resourceTable = "datatables/resource/resource_tree.iff";
        String classString = "";
        int row = dataTableSearchColumnForString(className, 1, resourceTable);
        int column = 2;
        while ((classString == null || classString.length() == 0) && column <= 9)
        {
            classString = dataTableGetString(resourceTable, row, column);
            column++;
        }
        return classString;
    }
}
