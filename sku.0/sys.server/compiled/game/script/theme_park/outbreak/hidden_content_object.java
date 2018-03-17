package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.groundquests;

public class hidden_content_object extends script.base_script
{
    public hidden_content_object()
    {
    }
    public static final String MENU_STRING_FILE = "theme_park/outbreak/outbreak";
    public static final String MENU_OBJ_VAR = "menu_string";
    public static final String QUEST_OBJ_VAR = "questName";
    public static final String SPAWNER_OBJ_VAR = "mySpawner";
    public static final string_id SID_YOU_FIND_NOTHING = new string_id(MENU_STRING_FILE, "you_find_nothing");
    public static final string_id SID_THIS_NOT_FOR_YOU = new string_id(MENU_STRING_FILE, "this_isnt_for_you");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "knockDown", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "knockDown", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, MENU_OBJ_VAR))
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_object.OnObjectMenuRequest() No MENU_OBJ_VAR for : " + self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, QUEST_OBJ_VAR))
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_object.OnObjectMenuRequest() No QUEST_OBJ_VAR for : " + self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, SPAWNER_OBJ_VAR))
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_object.OnObjectMenuRequest() No SPAWNER_OBJ_VAR for : " + self);
            return SCRIPT_CONTINUE;
        }
        String menuObjVar = getStringObjVar(self, MENU_OBJ_VAR);
        if (menuObjVar == null || menuObjVar.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data data = mi.getMenuItemByType(menu_info_types.SERVER_MENU2);
        mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id(MENU_STRING_FILE, menuObjVar));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, MENU_OBJ_VAR))
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_object.OnObjectMenuRequest() No MENU_OBJ_VAR for : " + self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, QUEST_OBJ_VAR))
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_object.OnObjectMenuRequest() No QUEST_OBJ_VAR for : " + self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, SPAWNER_OBJ_VAR))
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_object.OnObjectMenuRequest() No SPAWNER_OBJ_VAR for : " + self);
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.SERVER_MENU2)
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, QUEST_OBJ_VAR);
        if (questName == null || questName.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "hidden_content_object.OnObjectMenuRequest() QUEST_OBJ_VAR invalid for : " + self);
            return SCRIPT_CONTINUE;
        }
        if (groundquests.isQuestActiveOrComplete(player, questName))
        {
            sendSystemMessage(player, SID_THIS_NOT_FOR_YOU);
            CustomerServiceLog("outbreak_themepark", "hidden_content_object.OnObjectMenuRequest() Player: " + player + " already has quest for object: " + self);
            return SCRIPT_CONTINUE;
        }
        groundquests.grantQuest(player, questName);
        messageTo(self, "destroySelf", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int knockDown(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.aiSetPosture(self, POSTURE_KNOCKED_DOWN);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "mySpawner"))
        {
            obj_id mySpawner = getObjIdObjVar(self, "mySpawner");
            if (isValidId(mySpawner) && exists(mySpawner))
            {
                destroyObject(mySpawner);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
