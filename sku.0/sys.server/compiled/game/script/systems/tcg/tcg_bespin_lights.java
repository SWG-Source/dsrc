package script.systems.tcg;

import script.*;
import script.library.player_structure;
import script.library.utils;

public class tcg_bespin_lights extends script.base_script
{
    public tcg_bespin_lights()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if(!hasObjVar(self, "status")){
            setObjVar(self, "status", (getTemplateName(self).contains("_off") ? "off" : "on"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if(!hasObjVar(self, "status")){
            setObjVar(self, "status", (getTemplateName(self).contains("_off") ? "off" : "on"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (!isValidId(structure)
                || !exists(structure)
                || (!player_structure.isBuilding(structure) && !isPlayer(structure))
                || (player != owner && !player_structure.isAdmin(structure, player))
                || (getTopMostContainer(player) != getTopMostContainer(self)) || (getDistance(player, self) > 7.0f)) {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("tcg", (isOn(self) ? "turn_off" : "turn_on")));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id pInv = utils.getInventoryContainer(player);
            if (utils.isNestedWithin(self, pInv))
            {
                return SCRIPT_CONTINUE;
            }
            String type = getStringObjVar(self, "type");
            toggleLight(self, type, (isOn(self) ? "off" : "on"));
            sendDirtyObjectMenuNotification(self);
        }
        return SCRIPT_CONTINUE;
    }
    private boolean isOn(obj_id self){
        return getStringObjVar(self, "status").equals("on");
    }
    private void toggleLight(obj_id self, String type, String position) throws InterruptedException {
        sendSystemMessageTestingOnly(getOwner(self), "Turning light " + position);
        location currentLocation = getLocation(self);

        dictionary itemData = dataTableGetRow("datatables/item/master_item/master_item.iff", "item_tcg_loot_reward_series8_bespin_" + type + "_" + position);

        if(itemData == null) return;

        obj_id newLight = createObject(itemData.getString("template_name"), currentLocation);

        if(newLight == null){
            return;
        }
        attachScript(newLight, "systems.tcg.tcg_bespin_lights");
        setObjVar(newLight, "status", position);
        setObjVar(newLight, "type", type);
        setObjVar(newLight, "combine", "true");
        setObjVar(newLight, "tcg.combineItemTemplatePattern", "object/tangible/tcg/series8/combine_decorative_");
        setObjVar(newLight, "tcg.setName", "set8_cloud_house");
        float[] orientation = getQuaternion(self);
        setQuaternion(newLight, orientation[0], orientation[1], orientation[2], orientation[3]);
        persistObject(newLight);
        destroyObject(self);
        sendSystemMessageTestingOnly(getOwner(self), "Light created!");
    }
}
