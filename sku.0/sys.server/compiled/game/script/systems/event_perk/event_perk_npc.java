package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.chat;
import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.ai_lib;

public class event_perk_npc extends script.base_script
{
    public event_perk_npc()
    {
    }
    public static final string_id SID_AREABARKS_ENABLED = new string_id("event_perk_npc_actor", "areabarks_enabled");
    public static final string_id SID_AREABARKS_DISABLED = new string_id("event_perk_npc_actor", "areabarks_disabled");
    public static final string_id SID_OBSCENE = new string_id("event_perk_npc_actor", "obscene");
    public static final string_id SID_ACTOR_CONTROL = new string_id("event_perk_npc_actor", "actor_control");
    public static final string_id SID_CUSTOMIZE_VENDOR = new string_id("event_perk_npc_actor", "customize_actor");
    public static final string_id SID_VENDOR_AREABARKS_ON = new string_id("event_perk_npc_actor", "actor_areabarks_on");
    public static final string_id SID_VENDOR_AREABARKS_OFF = new string_id("event_perk_npc_actor", "actor_areabarks_off");
    public static final String TBL_VENDOR_ANIMS = "datatables/event_perk/npc_actor_anims.iff";
    public static final String TBL_VENDOR_MOODS = "datatables/event_perk/npc_actor_moods.iff";
    public static final String ALERT_VOLUME_NAME = "npcActorTriggerVolume";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id ownerId = getObjIdObjVar(self, "event_perk.owner");
        if (player == ownerId)
        {
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_ACTOR_CONTROL);
            if (!hasObjVar(self, "event_perk.areaBarks"))
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, SID_VENDOR_AREABARKS_ON);
            }
            else 
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, SID_VENDOR_AREABARKS_OFF);
            }
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU8, SID_CUSTOMIZE_VENDOR);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id ownerId = getObjIdObjVar(self, "event_perk.owner");
        if (player != ownerId)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            if (hasObjVar(self, "event_perk.areaBarks"))
            {
                removeObjVar(self, "event_perk.areaBarks");
                clearCondition(self, CONDITION_CONVERSABLE);
                sendSystemMessage(player, SID_AREABARKS_DISABLED);
            }
            else 
            {
                String[] rawAnims = dataTableGetStringColumn(TBL_VENDOR_ANIMS, 0);
                String[] anims = new String[rawAnims.length];
                for (int i = 0; i < rawAnims.length; i++)
                {
                    anims[i] = "@event_perk_npc_actor:" + rawAnims[i];
                }
                sui.listbox(self, player, "@event_perk_npc_actor:actor_anim_d", sui.OK_CANCEL, "@event_perk_npc_actor:actor_anim_t", anims, "handleVendorAnimSelect", true);
            }
            sendDirtyObjectMenuNotification(self);
        }
        else if (item == menu_info_types.SERVER_MENU8)
        {
            String templateName = getTemplateName(self);
            if (templateName.indexOf("ithorian") > 0)
            {
                chat._chat(self, null, chat.CHAT_SAY, chat.MOOD_STUBBORN, null, new string_id("event_perk_npc_actor", "wear_no_ithorian"), null);
                doAnimationAction(self, "wave_on_dismissing");
            }
            else 
            {
                chat._chat(self, null, chat.CHAT_SAY, chat.MOOD_CHEERFUL, null, new string_id("event_perk_npc_actor", "wear_how"), null);
                doAnimationAction(self, "slow_down");
            }
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        obj_id ownerId = getObjIdObjVar(self, "event_perk.owner");
        if (player != ownerId)
        {
            return SCRIPT_CONTINUE;
        }
        String templateName = getTemplateName(self);
        String itemName = getTemplateName(item);
        if (itemName.startsWith("object/tangible/wearables/robe"))
        {
            chat._chat(self, null, chat.CHAT_SAY, chat.MOOD_CHEERFUL, null, new string_id("event_perk_npc_actor", "wear_no"), null);
            doAnimationAction(self, "slow_down");
            return SCRIPT_CONTINUE;
        }
        else if (templateName.indexOf("wookiee") == -1 && itemName.indexOf("wke_") > 0)
        {
            chat._chat(self, null, chat.CHAT_SAY, chat.MOOD_STUBBORN, null, new string_id("event_perk_npc_actor", "wear_no"), null);
            doAnimationAction(self, "wave_on_dismissing");
            return SCRIPT_CONTINUE;
        }
        else if (templateName.indexOf("ithorian") > 0)
        {
            chat._chat(self, null, chat.CHAT_SAY, chat.MOOD_STUBBORN, null, new string_id("event_perk_npc_actor", "wear_no"), null);
            doAnimationAction(self, "wave_on_dismissing");
            return SCRIPT_CONTINUE;
        }
        if (equipOverride(item, self))
        {
            if (isGameObjectTypeOf(item, GOT_weapon))
            {
                chat._chat(self, null, chat.CHAT_SAY, chat.MOOD_HAPPY, null, new string_id("event_perk_npc_actor", "wear_yes_weapon"), null);
            }
            else 
            {
                chat._chat(self, null, chat.CHAT_SAY, chat.MOOD_HAPPY, null, new string_id("event_perk_npc_actor", "wear_yes"), null);
            }
            doAnimationAction(self, "pose_proudly");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            chat._chat(self, null, chat.CHAT_SAY, chat.MOOD_CHEERFUL, null, new string_id("event_perk_npc_actor", "wear_no"), null);
            doAnimationAction(self, "slow_down");
            return SCRIPT_CONTINUE;
        }
    }
    public int handleVendorAnimSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] rawAnims = dataTableGetStringColumn(TBL_VENDOR_ANIMS, 0);
        setObjVar(self, "event_perk.barkAnim", rawAnims[idx]);
        String[] rawMoods = dataTableGetStringColumn(TBL_VENDOR_MOODS, 0);
        String[] moods = new String[rawMoods.length];
        for (int i = 0; i < rawMoods.length; i++)
        {
            moods[i] = "@event_perk_npc_actor:" + rawMoods[i];
        }
        sui.listbox(self, player, "@event_perk_npc_actor:actor_moods_d", sui.OK_CANCEL, "@event_perk_npc_actor:actor_moods_t", moods, "handleVendorMoodSelect", true);
        return SCRIPT_CONTINUE;
    }
    public int handleVendorMoodSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] rawMoods = dataTableGetStringColumn(TBL_VENDOR_MOODS, 0);
        setObjVar(self, "event_perk.barkMood", rawMoods[idx]);
        sui.inputbox(self, player, "@event_perk_npc_actor:cust_d", sui.OK_CANCEL, "@event_perk_npc_actor:cust_t", sui.INPUT_NORMAL, null, "handleSetCustomBark", null);
        return SCRIPT_CONTINUE;
    }
    public int handleSetCustomBark(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String customBark = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if ((customBark.equals("")) || !isAppropriateText(customBark))
        {
            sendSystemMessage(player, SID_OBSCENE);
            sui.inputbox(self, player, "@event_perk_npc_actor:cust_d", sui.OK_CANCEL, "@event_perk_npc_actor:cust_t", sui.INPUT_NORMAL, null, "handleSetCustomBark", null);
            return SCRIPT_CONTINUE;
        }
        if (customBark.length() > 140)
        {
            String playerName = getName(player);
            customBark = customBark.substring(0, 139);
            CustomerServiceLog("EventPerk", "(NPC Actor) Player [" + playerName + "] OID [" + player + "] set a custom bark with the text [" + customBark + "].");
        }
        setObjVar(self, "event_perk.customBark", customBark);
        setObjVar(self, "event_perk.areaBarks", 1);
        setCondition(self, CONDITION_CONVERSABLE);
        sendSystemMessage(player, SID_AREABARKS_ENABLED);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String anim = getStringObjVar(self, "event_perk.barkAnim");
        String mood = getStringObjVar(self, "event_perk.barkMood");
        String strcat = getStringObjVar(self, "event_perk.barkStrCat");
        String custbark = getStringObjVar(self, "event_perk.customBark");
        chat._chat(self, speaker, chat.CHAT_SAY, mood, chat.ChatFlag_targetOnly, custbark, null, null);
        doAnimationAction(self, anim);
        return SCRIPT_CONTINUE;
    }
}
