package script.systems.gcw;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.obj_var_list;

import java.util.Vector;

public class gcw_camp extends script.base_script
{
    public gcw_camp()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeAdvancedCamp", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int initializeAdvancedCamp(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("gcw_camp_functionality", "initializeAdvancedCamp");
        if (!hasObjVar(self, "factionFlag"))
        {
            return SCRIPT_CONTINUE;
        }
        int factionFlag = getIntObjVar(self, "factionFlag");
        if (factionFlag < 0)
        {
            return SCRIPT_CONTINUE;
        }
        LOG("gcw_camp_functionality", "initializeAdvancedCamp factionFlag: " + factionFlag);
        int numNodes = camping.getAdvancedCampNumberOfNodes(self);
        if (numNodes > 6)
        {
            numNodes = 6;
        }
        LOG("gcw_camp_functionality", "initializeAdvancedCamp - numNodes: " + numNodes);
        obj_id[] nodeObjects = new obj_id[numNodes];
        int arrayIdx = 0;
        obj_var_list ovl = getObjVarList(self, "modules");
        LOG("gcw_camp_functionality", "initializeAdvancedCamp - obj_var_list: " + ovl);
        if (ovl == null)
        {
            LOG("gcw_camp_functionality", "initializeAdvancedCamp - ovl == null");
            return SCRIPT_CONTINUE;
        }
        LOG("gcw_camp_functionality", "initializeAdvancedCamp - ovl not null");
        String[] modules = ovl.getAllObjVarNames();
        if (modules == null || modules.length <= 0)
        {
            LOG("gcw_camp_functionality", "initializeAdvancedCamp - modules == null");
            return SCRIPT_CONTINUE;
        }
        int nodeCount = 1;
        int i = 0;
        int recruitersPlaced = 0;
        while (nodeCount <= numNodes && i < modules.length)
        {
            if (modules[i] == null || modules[i].equals(""))
            {
                LOG("gcw_camp_functionality", "initializeAdvancedCamp - modules == null");
                continue;
            }
            LOG("gcw_camp_functionality", "initializeAdvancedCamp - modules[i]: " + modules[i]);
            location tempLoc = getLocation(self);
            location nodeLoc = camping.getAdvancedCampNodeLocation(self, tempLoc, nodeCount);
            int nodeYaw = camping.getAdvancedCampNodeYaw(self, nodeCount);
            String module_template = "";
            switch (modules[i]) {
                case "shuttle_beacon":
                    module_template = "object/building/poi/player_camp_shuttle_beacon.iff";
                    break;
                case "cloning_tube":
                    module_template = "object/building/poi/player_camp_clone_tube.iff";
                    break;
                case "entertainer":
                    if (factionFlag == factions.FACTION_FLAG_REBEL) {
                        LOG("gcw_camp_functionality", "initializeAdvancedCamp - Entertainment Camp is FACTION_FLAG_REBEL");
                        module_template = "object/building/poi/gcw_camp_entertainment_rebel.iff";
                    } else if (factionFlag == factions.FACTION_FLAG_IMPERIAL) {
                        LOG("gcw_camp_functionality", "initializeAdvancedCamp - Entertainment Camp is FACTION_FLAG_IMPERIAL");
                        module_template = "object/building/poi/gcw_camp_entertainment_imperial.iff";
                    } else {
                        LOG("gcw_camp_functionality", "initializeAdvancedCamp - Entertainment Camp is FACTION_FLAG_UNKNOWN");
                        module_template = "object/building/poi/player_camp_entertainment_area.iff";
                    }
                    break;
                case "junk_dealer":
                    if (factionFlag == factions.FACTION_FLAG_REBEL) {
                        LOG("gcw_camp_functionality", "initializeAdvancedCamp - Junk Dealer is FACTION_FLAG_REBEL");
                        module_template = "object/building/poi/gcw_camp_junk_dealer_rebel.iff";
                    } else if (factionFlag == factions.FACTION_FLAG_IMPERIAL) {
                        LOG("gcw_camp_functionality", "initializeAdvancedCamp - Junk Dealer is FACTION_FLAG_IMPERIAL");
                        module_template = "object/building/poi/gcw_camp_junk_dealer_imperial.iff";
                    } else {
                        LOG("gcw_camp_functionality", "initializeAdvancedCamp - Junk Dealer is FACTION_FLAG_UNKNOWN");
                        module_template = "object/building/poi/player_camp_junk_dealer.iff";
                    }
                    break;
                case "imperial":
                    module_template = "object/building/poi/gcw_camp_imperial_recruiter.iff";
                    break;
                case "rebel":
                    module_template = "object/building/poi/gcw_camp_rebel_recruiter.iff";
                    break;
                case "rebel_clone":
                    module_template = "object/building/poi/gcw_rebel_clone_tent_small.iff";
                    break;
                case "imperial_clone":
                    module_template = "object/building/poi/gcw_imperial_clone_tent_small.iff";
                    break;
                case "clothing_station":
                    module_template = "object/building/poi/player_camp_crafting_clothing.iff";
                    break;
                case "food_station":
                    module_template = "object/building/poi/player_camp_crafting_food.iff";
                    break;
                case "ship_station":
                    module_template = "object/building/poi/player_camp_crafting_space.iff";
                    break;
                case "structure_station":
                    module_template = "object/building/poi/player_camp_crafting_structure.iff";
                    break;
                case "weapon_station":
                    module_template = "object/building/poi/player_camp_crafting_weapon.iff";
                    break;
            }
            if (!module_template.equals(""))
            {
                obj_id module_id = create.object(module_template, nodeLoc);
                if (camping.isRecruiterModule(module_template))
                {
                    recruitersPlaced = recruitersPlaced + 1;
                }
                if (isIdValid(module_id))
                {
                    setYaw(module_id, nodeYaw);
                    nodeObjects[arrayIdx] = module_id;
                    arrayIdx++;
                    nodeCount++;
                }
            }
            i++;
        }
        LOG("gcw_camp_functionality", "initializeAdvancedCamp - Setting Vars!!");
        setObjVar(self, "modules.ids", nodeObjects);
        setObjVar(self, camping.VAR_CREATION_TIME, getCalendarTime());
        Vector children = getResizeableObjIdArrayObjVar(self, theater.VAR_CHILDREN);
        LOG("gcw_camp_functionality", "initializeAdvancedCamp - children.length: " + children.size());
        for (obj_id nodeObject : nodeObjects) {
            children = utils.addElement(children, nodeObject);
        }
        setObjVar(self, theater.VAR_CHILDREN, children);
        return SCRIPT_CONTINUE;
    }
}
