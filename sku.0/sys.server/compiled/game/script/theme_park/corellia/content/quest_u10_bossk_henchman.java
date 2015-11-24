package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;

public class quest_u10_bossk_henchman extends script.base_script
{
    public quest_u10_bossk_henchman()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        chat.setChatMood(self, chat.MOOD_PETULANT);
        chat.setChatType(self, chat.CHAT_COMPLAIN);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        float damageThreshold = 0.3f;
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        int currentHealth = getAttrib(self, HEALTH);
        if (currentHealth <= (maxHealth * damageThreshold))
        {
            setAttrib(self, HEALTH, maxHealth);
            setInvulnerable(self, true);
            setCombatTarget(self, null);
            stopCombat(self);
            clearHateList(self);
            chat.publicChat(self, attacker, new string_id("quest2", "quest_u10_bossk_henchman_beaten"));
            attachScript(self, "conversation.quest_u10_bossk_henchman");
            dictionary webster = new dictionary();
            webster.put("attacker", attacker);
            messageTo(self, "makeNpcStopCombat", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        setAttrib(self, HEALTH, getUnmodifiedMaxAttrib(self, HEALTH));
        setInvulnerable(self, true);
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        clearHateList(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        buff.removeAllBuffs(self);
        if (!hasScript(self, "conversation.quest_u10_bossk_henchman"))
        {
            attachScript(self, "conversation.quest_u10_bossk_henchman");
        }
        return SCRIPT_CONTINUE;
    }
    public int makeNpcStopCombat(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        stopCombat(self);
        clearHateList(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        buff.removeAllBuffs(self);
        obj_id[] players = getAllPlayers(getLocation(self), 25.0f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                obj_id player = players[i];
                if (isIdValid(player) && groundquests.isTaskActive(player, "quest_u10_03", "quest_u10_03_04"))
                {
                    groundquests.sendSignal(player, "quest_u10_03_04");
                }
            }
        }
        obj_id attacker = params.getObjId("attacker");
        if (isIdValid(attacker))
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int makeNpcDisappear(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        playClientEffectLoc(getPlayerCreaturesInRange(here, 100.0f), "appearance/pt_smoke_puff_noloop.prt", here, 0.0f);
        messageTo(self, "handleDestroySelf", null, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        stopCombat(self);
        clearHateList(self);
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 20f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                npcEndConversation(players[i]);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
