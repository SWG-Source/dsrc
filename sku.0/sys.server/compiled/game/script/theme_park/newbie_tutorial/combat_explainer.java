package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.weapons;

public class combat_explainer extends script.theme_park.newbie_tutorial.tutorial_base
{
    public combat_explainer()
    {
    }
    public int handlePlayerArrival(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        faceToBehavior(self, player);
        messageTo(self, "handleGreetPlayer", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        removeStaticWaypoint(self);
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        faceToBehavior(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleGreetPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (!isInRoom(player, "r7"))
        {
            doAnimationAction(self, "salute1");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(player, "newbie.hasWeapon"))
        {
            string_id greeting = new string_id(NEWBIE_CONVO, "convo_4_repeat");
            chat.chat(self, greeting);
            return SCRIPT_CONTINUE;
        }
        doAnimationAction(self, "slow_down");
        string_id greeting = new string_id(NEWBIE_CONVO, "convo_4_start");
        chat.chat(self, greeting);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        doAnimationAction(self, "slow_down");
        utils.setScriptVar(speaker, "newbie.alreadyTalkedToCombatExplainer", true);
        if (hasObjVar(speaker, "newbie.hasWeapon"))
        {
            if (hasObjVar(speaker, "newbie.killedPirate"))
            {
                string_id greeting = new string_id(NEWBIE_CONVO, "good_job");
                chat.chat(self, greeting);
            }
            else 
            {
                string_id greeting = new string_id(NEWBIE_CONVO, "convo_4_repeat");
                chat.chat(self, greeting);
            }
            return SCRIPT_CONTINUE;
        }
        string_id greeting = new string_id(NEWBIE_CONVO, "convo_4_start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(NEWBIE_CONVO, "convo_4_reply_1");
        response[1] = new string_id(NEWBIE_CONVO, "convo_4_reply_2");
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        npcRemoveConversationResponse(player, response);
        if (!convo.equals(CONVO))
        {
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("convo_4_reply_1"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_4_explain_1");
            npcSpeak(player, message);
            doAnimationAction(self, "point_away");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_4_reply_3"));
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_4_reply_4"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("convo_4_reply_2"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_4_explain_2");
            npcSpeak(player, message);
            doAnimationAction(self, "shrug_hands");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_4_reply_5"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("convo_4_reply_3"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_4_explain_3");
            npcSpeak(player, message);
            doAnimationAction(self, "point_to_self");
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("convo_4_reply_4") || (response.getAsciiId()).equals("convo_4_reply_5"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_4_weapon_1");
            npcSpeak(player, message);
            giveWeapon(self, player);
            doAnimationAction(self, "wave_finger_warning");
            string_id[] responses = new string_id[1];
            responses[0] = new string_id(NEWBIE_CONVO, "convo_4_reply_7");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("convo_4_reply_7"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_4_end");
            npcSpeak(player, message);
            doAnimationAction(self, "wave_on_dismissing");
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void giveWeapon(obj_id npc, obj_id player) throws InterruptedException
    {
        obj_id weapon = weapons.createWeapon(NEWBIE_WEAPON, npc, 0.75f);
        dictionary parms = new dictionary();
        if (weapon != null)
        {
            setObjVar(weapon, "newbie.item", true);
            parms.put("player", player);
            parms.put("weapon", weapon);
        }
        messageTo(npc, "handleGiveWeapon", parms, 2, false);
    }
    public int handleGiveWeapon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id weapon = params.getObjId("weapon");
        if (player != null && weapon != null)
        {
            if (hasObjVar(player, "newbie.hasWeapon"))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id playerInv = getObjectInSlot(player, "inventory");
            obj_id myInv = getObjectInSlot(self, "inventory");
            putIn(weapon, myInv);
            destroyObject(weapon);
            weapon = weapons.createWeapon(NEWBIE_WEAPON, playerInv, 0.75f);
            setObjVar(weapon, "newbie.item", true);
            equip(weapon, player);
            setObjVar(player, "newbie.hasWeapon", true);
        }
        return SCRIPT_CONTINUE;
    }
}
