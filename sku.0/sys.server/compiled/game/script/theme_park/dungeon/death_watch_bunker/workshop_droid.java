package script.theme_park.dungeon.death_watch_bunker;

import script.dictionary;
import script.library.ai_lib;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class workshop_droid extends script.base_script
{
    public workshop_droid()
    {
    }
    public static final String FACETO_VOLUME_NAME = "faceToTriggerVolume";
    public static final string_id BATTERY_CLEANED = new string_id("dungeon/death_watch", "battery_cleaned");
    public static final string_id NEED_BATTERY = new string_id("dungeon/death_watch", "need_battery");
    public static final string_id NOT_AUTHORIZED = new string_id("dungeon/death_watch", "not_authorized");
    public static final String CONVO = "dungeon/death_watch";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "conversation.death_watch_treadwell");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(self, true);
        createTriggerVolume(FACETO_VOLUME_NAME, 8.0f, true);
        setName(self, "WED15-I643 (a workshop droid)");
        return SCRIPT_CONTINUE;
    }
    public int handleCleanBattery(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, "death_watch.drill_02"))
        {
            sendSystemMessage(player, NOT_AUTHORIZED);
            return SCRIPT_CONTINUE;
        }
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (obj_id objContent : objContents) {
                String strItemTemplate = getTemplateName(objContent);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/drill_battery.iff")) {
                    obj_id battery = objContent;
                    destroyObject(battery);
                    obj_id playerInv = getObjectInSlot(player, "inventory");
                    if (isIdValid(playerInv)) {
                        obj_id item = createObject("object/tangible/dungeon/death_watch_bunker/drill_battery_clean.iff", playerInv, "");
                        if (isIdValid(item)) {
                            sendSystemMessage(player, BATTERY_CLEANED);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        sendSystemMessage(player, NEED_BATTERY);
        return SCRIPT_CONTINUE;
    }
}
