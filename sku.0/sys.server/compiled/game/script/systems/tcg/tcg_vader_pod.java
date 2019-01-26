package script.systems.tcg;

import script.library.player_structure;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class tcg_vader_pod extends script.base_script
{
    public tcg_vader_pod()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if(!hasObjVar(self, "status")){
            setObjVar(self, "status", "open");
        }
        else{
            if(!isOpen(self)){
                closePod(self, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if(!hasObjVar(self, "status")){
            setObjVar(self, "status", "open");
        }
        else{
            if(!isOpen(self)){
                closePod(self, null);
            }
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
        mi.addRootMenu(menu_info_types.ITEM_USE, (isOpen(self) ? new string_id("ui", "close") : new string_id("tcg", "open")));
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
            if(isOpen(self))
                closePod(self, player);
            else
                openPod(self, player);
            sendDirtyObjectMenuNotification(self);
        }
        return SCRIPT_CONTINUE;
    }
    private boolean isOpen(obj_id self){
        return getStringObjVar(self, "status").equals("open");
    }
    private void openPod(obj_id self, obj_id player) {
        playClientEffectObj(player, "clienteffect/vader_pod.cef", self, "");
        doAnimationAction(self, "trick_2");
        setObjVar(self, "status", "open");
    }
    private void closePod(obj_id self, obj_id player) {
        playClientEffectObj(player, "clienteffect/vader_pod_close.cef", self, "");
        doAnimationAction(self, "trick_1");
        setObjVar(self, "status", "closed");
    }
}
