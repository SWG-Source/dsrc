package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.chat;
import script.ai.ai_combat;

public class escapee extends script.base_script
{
    public escapee()
    {
    }
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String PASSKEYCODE = "object/tangible/mission/quest_item/warren_passkey_s01.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_WANDER);
        setCondition(self, CONDITION_CONVERSABLE);
        chat.setChatMood(self, chat.MOOD_SCARED);
        obj_id bldg = getObjIdObjVar(self, "warren.bldg");
        ai_combat.aiCombatFlee(self, bldg, 80.0f, 120.0f);
        setName(self, "");
        setName(self, new string_id("theme_park/warren/warren_system_messages", "name_ovitt"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        chat.setChatMood(self, chat.MOOD_SCARED);
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(CONVO_FILE, "escapee_start");
        string_id response[] = new string_id[1];
        response[0] = new string_id(CONVO_FILE, "escapee_reply_1");
        npcStartConversation(speaker, self, CONVO_FILE, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals(CONVO_FILE))
        {
            return SCRIPT_CONTINUE;
        }
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("escapee_reply_1"))
        {
            string_id message = new string_id(CONVO_FILE, "escapee_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "escapee_1_reply_1"));
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "escapee_1_reply_2"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("escapee_1_reply_1"))
        {
            obj_id playerInv = utils.getInventoryContainer(player);
            if (isIdValid(playerInv))
            {
                if (hasKey(playerInv))
                {
                    string_id message = new string_id(CONVO_FILE, "escapee_haskey");
                    npcSpeak(player, message);
                    return SCRIPT_CONTINUE;
                }
            }
            string_id message = new string_id(CONVO_FILE, "escapee_2");
            npcSpeak(player, message);
            obj_id warren = getObjIdObjVar(self, "warren.bldg");
            if (!isIdValid(warren))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id wayp = createWaypointInDatapad(player, warren);
            if (isIdValid(wayp))
            {
                setWaypointName(wayp, getString(new string_id(SYSTEM_MESSAGES, "waypoint_name")));
                setWaypointActive(wayp, true);
                setWaypointVisible(wayp, true);
                if (isIdValid(playerInv))
                {
                    obj_id passKey = createObject(PASSKEYCODE, playerInv, "");
                    if (isIdValid(passKey))
                    {
                        setObjVar(passKey, "warren.keycode", true);
                        setName(passKey, "");
                        setName(passKey, new string_id(SYSTEM_MESSAGES, "passkey_name"));
                    }
                }
                else 
                {
                }
            }
            else 
            {
            }
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("escapee_1_reply_2"))
        {
            string_id message = new string_id(CONVO_FILE, "escapee_3");
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id warren = getObjIdObjVar(self, "warren.bldg");
        messageTo(warren, "handleEscapeeDied", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        obj_id warren = getObjIdObjVar(self, "warren.bldg");
        messageTo(warren, "handleEscapeeDied", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public boolean hasKey(obj_id inv) throws InterruptedException
    {
        obj_id[] contents = getContents(inv);
        if (contents == null)
        {
            return false;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "warren.keycode"))
            {
                return true;
            }
        }
        return false;
    }
}
