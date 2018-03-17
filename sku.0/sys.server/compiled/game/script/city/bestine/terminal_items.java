package script.city.bestine;

import script.*;
import script.library.utils;

public class terminal_items extends script.base_script
{
    public terminal_items()
    {
    }
    public static final String CONVO = "city/bestine/terminal_items";
    public static final String DATATABLE_NAME = "datatables/city/bestine/terminal_items.iff";
    public static final String GET_QUEST_ITEM_VOLUME_NAME = "getQuestItemTriggerVolume";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume(GET_QUEST_ITEM_VOLUME_NAME, 2.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(GET_QUEST_ITEM_VOLUME_NAME))
        {
            String template = getStringObjVar(self, "disk");
            if ((template != null) && (!template.equals("")) && template.indexOf("history") > -1 && isPlayer(breacher) && canSearch(self, breacher))
            {
                if (hasObjVar(breacher, "bestine.searched"))
                {
                    sendSystemMessage(breacher, new string_id(CONVO, "history_disk_found_already"));
                    return SCRIPT_CONTINUE;
                }
                obj_id playerInv = utils.getInventoryContainer(breacher);
                if (isIdValid(playerInv))
                {
                    int free_space = getVolumeFree(playerInv);
                    if (free_space > 0)
                    {
                        obj_id objectReceived = createObject(template, playerInv, "");
                        if (isIdValid(objectReceived))
                        {
                            sendSystemMessage(breacher, new string_id(CONVO, "history_disk_found"));
                            setObjVar(breacher, "bestine.searched", 1);
                        }
                    }
                    else
                    {
                        sendSystemMessage(breacher, new string_id(CONVO, "inv_full"));
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String template = getStringObjVar(self, "disk");
        if(template != null && template.indexOf("history") >= 0){
            return SCRIPT_CONTINUE;
        }
        if (canSearch(self, player))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(CONVO, "download"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (canSearch(self, player))
            {
                if (hasObjVar(player, "bestine.searched"))
                {
                    sendSystemMessage(player, new string_id(CONVO, "searched"));
                    return SCRIPT_CONTINUE;
                }
                String template = getStringObjVar(self, "disk");
                if ((template != null) && (!template.equals("")))
                {
                    obj_id playerInv = utils.getInventoryContainer(player);
                    if (isIdValid(playerInv))
                    {
                        int free_space = getVolumeFree(playerInv);
                        if (free_space > 0)
                        {
                            obj_id objectReceived = createObject(template, playerInv, "");
                            if (isIdValid(objectReceived))
                            {
                                sendSystemMessage(player, new string_id(CONVO, "download_complete"));
                                setObjVar(player, "bestine.searched", 1);
                            }
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id(CONVO, "inv_full"));
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSearch(obj_id self, obj_id player) throws InterruptedException
    {
        boolean result = false;
        if (hasObjVar(self, "disk"))
        {
            String gatingObjVar = dataTableGetRow(DATATABLE_NAME, getStringObjVar(self, "disk")).getString("gatingObjVar");
            if (hasObjVar(player, gatingObjVar))
            {
                result = true;
            }
        }
        return result;
    }
}
