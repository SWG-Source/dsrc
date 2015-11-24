package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class retrieve_item_on_item_camera extends script.base_script
{
    public retrieve_item_on_item_camera()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "getQuestPlayerName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int getQuestPlayerName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id questPlayer = utils.getContainingPlayer(self);
        if (isIdValid(questPlayer))
        {
            setObjVar(self, "questPlayer", questPlayer);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (hasScript(player, "quest.task.ground.retrieve_item"))
        {
            if (groundquests.playerNeedsToRetrieveThisItem(player, self))
            {
                String menuText = groundquests.getRetrieveMenuText(player, self);
                string_id menuStringId = utils.unpackString(menuText);
                int menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, menuStringId);
                menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
                menuInfoData.setServerNotify(true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (hasScript(player, "quest.task.ground.retrieve_item"))
        {
            if (groundquests.playerNeedsToRetrieveThisItem(player, self))
            {
                if (item == menu_info_types.ITEM_USE)
                {
                    if (!hasObjVar(self, "quest.cameraSubject"))
                    {
                        sendSystemMessage(player, new string_id("theme_park/corellia/quest", "camera_not_setup"));
                        return SCRIPT_CONTINUE;
                    }
                    String cameraSubject = getStringObjVar(self, "quest.cameraSubject");
                    obj_id intendedTarget = getIntendedTarget(player);
                    if (isIdValid(intendedTarget))
                    {
                        if (isMob(intendedTarget))
                        {
                            if (isIncapacitated(intendedTarget) && !hasObjVar(self, "quest.allowDeadSubject"))
                            {
                                sendSystemMessage(player, new string_id("theme_park/corellia/quest", "camera_target_is_dead"));
                                return SCRIPT_CONTINUE;
                            }
                            else 
                            {
                                String intendedCreatureName = getCreatureName(intendedTarget);
                                if (cameraSubject.equals(intendedCreatureName))
                                {
                                    setObjVar(self, "questItemUsed", true);
                                    dictionary params = new dictionary();
                                    params.put("source", self);
                                    messageTo(player, "questRetrieveItemObjectFound", params, 0, false);
                                    if (!hasObjVar(self, "doNotDestroyMe"))
                                    {
                                        messageTo(self, "destroyMe", null, 1, false);
                                    }
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                    obj_id lookatTarget = getLookAtTarget(player);
                    if (isIdValid(lookatTarget))
                    {
                        if (isMob(lookatTarget))
                        {
                            if (isIncapacitated(lookatTarget) && !hasObjVar(self, "quest.allowDeadSubject"))
                            {
                                sendSystemMessage(player, new string_id("theme_park/corellia/quest", "camera_target_is_dead"));
                                return SCRIPT_CONTINUE;
                            }
                            else 
                            {
                                String lookatCreatureName = getCreatureName(lookatTarget);
                                if (cameraSubject.equals(lookatCreatureName))
                                {
                                    setObjVar(self, "questItemUsed", true);
                                    dictionary params = new dictionary();
                                    params.put("source", self);
                                    messageTo(player, "questRetrieveItemObjectFound", params, 0, false);
                                    if (!hasObjVar(self, "doNotDestroyMe"))
                                    {
                                        messageTo(self, "destroyMe", null, 1, false);
                                    }
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                    if (!isIdValid(intendedTarget) && !isIdValid(lookatTarget))
                    {
                        sendSystemMessage(player, new string_id("theme_park/corellia/quest", "camera_no_target"));
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessage(player, new string_id("theme_park/corellia/quest", "camera_wrong_target"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyMe(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "doNotDestroyMe"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "questItemUsed"))
        {
            if (hasObjVar(self, "questPlayer"))
            {
                obj_id player = getObjIdObjVar(self, "questPlayer");
                if (isIdValid(player))
                {
                    if (groundquests.playerNeedsToRetrieveThisItem(player, self))
                    {
                        String template = getTemplateName(self);
                        obj_id newItem = createObjectInInventoryAllowOverload(template, player);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
