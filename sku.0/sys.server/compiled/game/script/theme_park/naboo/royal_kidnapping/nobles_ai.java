package script.theme_park.naboo.royal_kidnapping;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.ai.ai_combat;
import script.library.chat;
import script.library.xp;

public class nobles_ai extends script.base_script
{
    public nobles_ai()
    {
    }
    public static final String CONVO_FILE = "theme_park/royal_kidnapping/nobles";
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final String XP_REWARD_TYPE = "combat_general";
    public static final int XP_REWARD_AMOUNT = 250;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "awardGranted"))
        {
            int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
            menu_info_data mdata = mi.getMenuItemById(mnu);
            mdata.setServerNotify(false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "awardGranted"))
        {
            return SCRIPT_CONTINUE;
        }
        chat.setChatMood(self, chat.MOOD_SCARED);
        stop(self);
        faceToBehavior(self, speaker);
        messageTo(self, "handleAngryAnim", null, 2, false);
        string_id greeting = new string_id(CONVO_FILE, "start");
        string_id response[] = new string_id[1];
        response[0] = new string_id(CONVO_FILE, "reply_1");
        npcStartConversation(speaker, self, CONVO_FILE, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int handleAngryAnim(obj_id self, dictionary params) throws InterruptedException
    {
        doAnimationAction(self, "angry");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals(CONVO_FILE))
        {
            return SCRIPT_CONTINUE;
        }
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("reply_1"))
        {
            doAnimationAction(self, "nod_head_multiple");
            chat.setChatMood(self, chat.MOOD_HAPPY);
            string_id message = new string_id(CONVO_FILE, "noble_1");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(CONVO_FILE, "noble_1_reply_1"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("noble_1_reply_1"))
        {
            doAnimationAction(self, "celebrate");
            string_id message = new string_id(CONVO_FILE, "noble_2");
            npcSpeak(player, message);
            utils.setScriptVar(self, "awardGranted", true);
            clearCondition(self, CONDITION_CONVERSABLE);
            xp.grantCombatStyleXp(player, XP_REWARD_TYPE, XP_REWARD_AMOUNT);
            npcEndConversation(player);
            messageTo(self, "handleRunAway", null, 6, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRunAway(obj_id self, dictionary params) throws InterruptedException
    {
        location destLoc = getBuildingEjectLocation(getTopMostContainer(self));
        if (destLoc == null)
        {
            destLoc = getLocation(getTopMostContainer(self));
            destLoc.x += 60.0f;
            destLoc.z += 60.0f;
        }
        removeTriggerVolume(ALERT_VOLUME_NAME);
        setWantSawAttackTriggers(self, false);
        setMovementRun(self);
        pathTo(self, destLoc);
        messageTo(self, "handleDelayedDestruct", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "awardGranted"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
