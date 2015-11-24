package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.pet_lib;
import script.library.create;

public class droid_vendor extends script.base_script
{
    public droid_vendor()
    {
    }
    public static final String DROID_VENDOR_CONVO = "beta/droid_vendor";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(self, "ai.diction", "townperson");
        setName(self, "Travelling Droid Merchant (beta)");
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
        chat.setGoodMood(self);
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(DROID_VENDOR_CONVO, "start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(DROID_VENDOR_CONVO, "yes");
        response[1] = new string_id(DROID_VENDOR_CONVO, "no");
        npcStartConversation(speaker, self, DROID_VENDOR_CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals(DROID_VENDOR_CONVO))
        {
            return SCRIPT_CONTINUE;
        }
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("yes"))
        {
            if (pet_lib.hasMaxPets(player, pet_lib.PET_TYPE_DROID))
            {
                string_id message = new string_id(DROID_VENDOR_CONVO, "toomany");
                npcSpeak(player, message);
                npcEndConversation(player);
            }
            npcRemoveConversationResponse(player, new string_id(DROID_VENDOR_CONVO, "no"));
            string_id message = new string_id(DROID_VENDOR_CONVO, "choose");
            npcSpeak(player, message);
            string_id[] responses = new string_id[8];
            responses[0] = new string_id(DROID_VENDOR_CONVO, "droideka");
            responses[1] = new string_id(DROID_VENDOR_CONVO, "eg6_power_droid");
            responses[2] = new string_id(DROID_VENDOR_CONVO, "mouse_droid");
            responses[3] = new string_id(DROID_VENDOR_CONVO, "probot");
            responses[4] = new string_id(DROID_VENDOR_CONVO, "protocol_droid_3po");
            responses[5] = new string_id(DROID_VENDOR_CONVO, "r2");
            responses[6] = new string_id(DROID_VENDOR_CONVO, "surgical_droid_21b");
            responses[7] = new string_id(DROID_VENDOR_CONVO, "le_repair_droid");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).equals("no"))
        {
            npcRemoveConversationResponse(player, new string_id(DROID_VENDOR_CONVO, "yes"));
            string_id message = new string_id(DROID_VENDOR_CONVO, "goodbye");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        String droidType = response.getAsciiId();
        String diction = "";
        if (droidType.equals("r2"))
        {
            droidType = "r" + rand(2, 5);
        }
        else if (droidType.equals("protocol_droid_3po"))
        {
            switch (rand(1, 3))
            {
                case 1:
                droidType = "protocol_droid_3po";
                break;
                case 2:
                droidType = "protocol_droid_3po_red";
                break;
                case 3:
                droidType = "protocol_droid_3po_silver";
                break;
            }
            switch (rand(1, 6))
            {
                case 1:
                diction = "droid_geek";
                break;
                case 2:
                diction = "droid_prissy";
                break;
                case 3:
                diction = "droid_sarcastic";
                break;
                case 4:
                diction = "droid_slang";
                break;
                case 5:
                diction = "droid_stupid";
                break;
                case 6:
                diction = "droid_worshipful";
                break;
            }
        }
        location spawnLoc = getLocation(self);
        spawnLoc.x += 2;
        spawnLoc.z += 2;
        playClientEffectLoc(player, "clienteffect/combat_explosion_lair_large.cef", spawnLoc, 0);
        obj_id droid = create.object(droidType, spawnLoc);
        pet_lib.makePet(droid, player);
        if (!diction.equals(""))
        {
            setObjVar(droid, "ai.diction", diction);
        }
        string_id message = new string_id(DROID_VENDOR_CONVO, "ok");
        npcSpeak(player, message);
        npcEndConversation(player);
        return SCRIPT_CONTINUE;
    }
}
