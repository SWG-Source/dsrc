package script.event.ewok_festival;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.prose_package;
import script.string_id;

public class mister_hate extends script.base_script
{
    public mister_hate()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleFarlesFaceTo", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleFarlesFaceTo(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.setMood(self, chat.MOOD_ANGRY);
        chat.setChatMood(self, chat.MOOD_ANGRY);
        obj_id player = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (isIdValid(player))
        {
            faceTo(self, player);
            startCombat(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawEmote(obj_id self, obj_id emoteSayer, String emoteSeen) throws InterruptedException
    {
        checkForTheLove(self, emoteSayer, emoteSeen);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        checkForTheLove(self, speaker, text);
        return SCRIPT_CONTINUE;
    }
    public void checkForTheLove(obj_id self, obj_id player, String text) throws InterruptedException
    {
        if (!isPlayer(player) || isIncapacitated(player) || isDead(player))
        {
            return;
        }
        if (!groundquests.isQuestActive(player, "loveday_disillusion_mr_hate_v2") && !groundquests.isQuestActive(player, "loveday_disillusion_mr_hate_v2_noloot"))
        {
            return;
        }
        obj_id emoteTarget = getIntendedTarget(player);
        if (!isIdValid(emoteTarget))
        {
            return;
        }
        obj_id eventPlayer = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(eventPlayer))
        {
            messageTo(self, "handleDestroySelf", null, 30, false);
            return;
        }
        if (emoteTarget == self && player == eventPlayer)
        {
            if (isInvulnerable(self))
            {
                return;
            }
            String theLove = toLower(text);
            if (theLove.equals("love") || theLove.contains("i love you"))
            {
                int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
                setAttrib(self, HEALTH, maxHealth);
                setInvulnerable(self, true);
                ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
                detachScript(self, "ai.creature_combat");
                attachScript(self, "conversation.loveday_disillusion_mr_hate");
                groundquests.sendSignal(player, "loveday_disillusion_mr_hate_02");
                faceTo(self, player);
                prose_package pp = new prose_package();
                pp.stringId = new string_id("event/love_day", "disillusion_mr_hate_loved");
                String emoterName = getName(player);
                prose.setTO(pp, emoterName);
                chat.publicChat(self, player, null, null, pp);
                doAnimationAction(self, "taken_aback");
                messageTo(self, "makeNpcStopCombat", null, 1, false);
            }
        }
    }
    public int makeNpcStopCombat(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        stopCombat(self);
        clearHateList(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        buff.removeAllBuffs(self);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        setAttrib(self, HEALTH, getUnmodifiedMaxAttrib(self, HEALTH));
        return SCRIPT_CONTINUE;
    }
    public int handleDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        stopCombat(self);
        clearHateList(self);
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 20f);
        if (players != null && players.length > 0)
        {
            for (obj_id player : players) {
                npcEndConversation(player);
            }
        }
        obj_id parent = trial.getParent(self);
        dictionary webster = trial.getSessionDict(parent);
        messageTo(parent, "defaultEventReset", webster, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
}
