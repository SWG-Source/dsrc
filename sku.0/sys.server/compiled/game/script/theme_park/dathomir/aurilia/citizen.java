package script.theme_park.dathomir.aurilia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.prose;
import script.library.utils;

public class citizen extends script.base_script
{
    public citizen()
    {
    }
    public static final String SOCIAL_VOLUME = "npc_socialization";
    public static final float SOCIAL_RANGE = 8f;
    public static final int NUM_UTTERANCE_OPTIONS = 15;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeSocialVolume", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int initializeSocialVolume(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            if (hasScript(self, "ai.ai"))
            {
                detachScript(self, "ai.ai");
            }
            if (hasScript(self, "ai.creature_combat"))
            {
                detachScript(self, "ai.creature_combat");
            }
            if (hasScript(self, "systems.combat.combat_actions"))
            {
                detachScript(self, "systems.combat.combat_actions");
            }
        }
        createTriggerVolume(SOCIAL_VOLUME, SOCIAL_RANGE, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isMob(breacher) || breacher == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(SOCIAL_VOLUME))
        {
            if (isPlayer(breacher))
            {
                npcUtterance(self, breacher);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void npcUtterance(obj_id npc, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(npc))
        {
            return;
        }
        if (isGod(player))
        {
            return;
        }
        if (utils.hasScriptVar(npc, "recentAuriliaUtterance"))
        {
            if (getGameTime() < utils.getIntScriptVar(npc, "recentAuriliaUtterance"))
            {
                return;
            }
        }
        if (utils.hasScriptVar(player, "recentlyUtteredTo"))
        {
            if (getGameTime() < utils.getIntScriptVar(npc, "recentlyUtteredTo"))
            {
                return;
            }
        }
        utils.setScriptVar(npc, "recentAuriliaUtterance", getGameTime() + rand(29, 42));
        utils.setScriptVar(player, "recentlyUtteredTo", getGameTime() + rand(19, 33));
        int chance = rand(0, 10);
        if (hasObjVar(npc, "alwaysUtter"))
        {
            chance = 10;
        }
        if (chance < 9)
        {
            return;
        }
        String[] utteranceTypes = 
        {
            "normal",
            "normal",
            "normal"
        };
        if (badge.hasBadge(player, "bdg_jedi_elder"))
        {
            if (rand(1, 2) == 1)
            {
                utteranceTypes[1] = "elder_jedi";
            }
        }
        String heroicUtterance = chooseHeroicString(player);
        if (heroicUtterance != null && heroicUtterance.length() > 0)
        {
            utteranceTypes[2] = heroicUtterance;
        }
        int pickAnUtterance = rand(0, (utteranceTypes.length - 1));
        int index = rand(1, NUM_UTTERANCE_OPTIONS);
        String utterance = utteranceTypes[pickAnUtterance] + "_" + index;
        string_id message = new string_id("utterance/aurilia", utterance);
        prose_package pp = prose.getPackage(message, player, player);
        prose.setTO(pp, getName(player));
        prose.setTT(pp, getGenderString(player));
        chat.chat(npc, player, pp);
        String npcType = getCreatureName(npc);
        String animationType = "salute" + rand(1, 2);
        if (!utterance.startsWith("normal") && npcType.equals("fs_villager_militia") && !utils.hasScriptVar(npc, "patrolPoints"))
        {
            doAnimationAction(npc, animationType);
        }
        return;
    }
    public String chooseHeroicString(obj_id player) throws InterruptedException
    {
        String heroicChoice = "";
        Vector heroicChoices = new Vector();
        heroicChoices.setSize(0);
        if (getCollectionSlotValue(player, "heroic_axkva_min_01") > 0)
        {
            heroicChoices = utils.addElement(heroicChoices, "heroic_axkva");
        }
        if (getCollectionSlotValue(player, "heroic_tusken_king_01") > 0)
        {
            heroicChoices = utils.addElement(heroicChoices, "heroic_tusken");
        }
        if (getCollectionSlotValue(player, "heroic_ig88_01") > 0)
        {
            heroicChoices = utils.addElement(heroicChoices, "heroic_ig88");
        }
        if (heroicChoices != null && heroicChoices.size() > 0)
        {
            int pickOne = rand(0, (heroicChoices.size() - 1));
            heroicChoice = ((String)heroicChoices.get(pickOne));
        }
        return heroicChoice;
    }
    public String getGenderString(obj_id player) throws InterruptedException
    {
        String genderString = "it";
        int gender = getGender(player);
        switch (gender)
        {
            case GENDER_FEMALE:
            genderString = "she";
            break;
            case GENDER_MALE:
            default:
            genderString = "he";
            break;
        }
        return genderString;
    }
}
