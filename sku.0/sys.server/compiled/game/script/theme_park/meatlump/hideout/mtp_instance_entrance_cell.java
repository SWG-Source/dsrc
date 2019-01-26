package script.theme_park.meatlump.hideout;

import script.dictionary;
import script.library.instance;
import script.obj_id;

public class mtp_instance_entrance_cell extends script.base_script
{
    public mtp_instance_entrance_cell()
    {
    }
    public static final String LEVEL_CONTROL_TEMPLATE = "object/tangible/meatlump/hideout/mtp_hideout_instance_entryb_controller.iff";
    public int OnReceivedItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id building = getContainedBy(self);
        if (isIdValid(building))
        {
            if (instance.getInstanceOwner(building) == item)
            {
                dictionary webster = new dictionary();
                webster.put("player", item);
                webster.put("playerCombatLevel", getLevel(item));
                messageTo(self, "handleInstanceCombatLevel", webster, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInstanceCombatLevel(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id building = getContainedBy(self);
        if (!isIdValid(player) || !isIdValid(building))
        {
            return SCRIPT_CONTINUE;
        }
        if (instance.getInstanceOwner(building) != player)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id levelControlObject = getInstanceCombatLevelController(self);
        if (!isIdValid(levelControlObject))
        {
            messageTo(self, "handleInstanceCombatLevel", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(levelControlObject, "mtp_instanceCombatLevel"))
        {
            return SCRIPT_CONTINUE;
        }
        int combatLevel = params.getInt("playerCombatLevel");
        if (combatLevel < 65)
        {
            combatLevel = 65;
        }
        setObjVar(levelControlObject, "mtp_instanceCombatLevel", combatLevel);
        messageTo(building, "setMtpInstanceCombatLevel", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public obj_id getInstanceCombatLevelController(obj_id self) throws InterruptedException
    {
        obj_id[] myContents = getContents(self);
        if (myContents != null && myContents.length > 0)
        {
            for (obj_id myContent : myContents) {
                if ((getTemplateName(myContent)).equals(LEVEL_CONTROL_TEMPLATE)) {
                    return myContent;
                }
            }
        }
        return obj_id.NULL_ID;
    }
}
