package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.combat;
import script.library.buff;
import script.ai.ai_combat;
import script.library.utils;

public class pirate_leader extends script.base_script
{
    public pirate_leader()
    {
    }
    public static final String STF_FILE = "quest/pirates";
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        final float DAMAGE_THRESHOLD = 0.50f;
        boolean check = false;
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        int currentHealth = getAttrib(self, HEALTH);
        if (currentHealth <= (maxHealth * DAMAGE_THRESHOLD))
        {
            check = true;
        }
        if (check)
        {
            if (!hasObjVar(self, "quest.hero_of_tatooine.attacked"))
            {
                setInvulnerable(self, true);
                messageTo(self, "makePirateLeaderInvulnerable", null, 1, false);
                messageTo(self, "makePirateLeaderAttackable", null, 189, false);
                chat.chat(self, new string_id(STF_FILE, "dont_hurt_us"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int attackPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        clearCondition(self, CONDITION_INTERESTING);
        detachScript(self, "conversation.quest_hero_of_tatooine_pirate_leader");
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            setInvulnerable(self, false);
            startCombat(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int makePirateLeaderAttackable(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInNpcConversation(self))
        {
            clearCondition(self, CONDITION_CONVERSABLE);
            clearCondition(self, CONDITION_INTERESTING);
            if (hasScript(self, "conversation.quest_hero_of_tatooine_pirate_leader"))
            {
                detachScript(self, "conversation.quest_hero_of_tatooine_pirate_leader");
            }
            setInvulnerable(self, false);
        }
        else 
        {
            messageTo(self, "makePirateLeaderAttackable", null, 99, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int makePirateLeaderInvulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        stopCombat(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        buff.removeAllBuffs(self);
        clearHateList(self);
        setCondition(self, CONDITION_CONVERSABLE);
        attachScript(self, "conversation.quest_hero_of_tatooine_pirate_leader");
        setObjVar(self, "quest.hero_of_tatooine.attacked", true);
        return SCRIPT_CONTINUE;
    }
}
