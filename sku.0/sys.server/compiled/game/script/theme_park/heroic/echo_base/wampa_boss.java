package script.theme_park.heroic.echo_base;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

public class wampa_boss extends script.base_script
{
    public wampa_boss()
    {
    }
    public static final String ADDS_VAR_BASE = "uncleJoeAdds";
    public static final String TEN_PERCENT = ADDS_VAR_BASE + ".tenPercent";
    public static final String TWENTY_PERCENT = ADDS_VAR_BASE + ".twentyPercent";
    public static final String FORTY_PERCENT = ADDS_VAR_BASE + ".fortyPercent";
    public static final String SIXTY_PERCENT = ADDS_VAR_BASE + ".sixtyPercent";
    public static final String EIGHTY_PERCENT = ADDS_VAR_BASE + ".eightyPercent";
    public static final String WAMPA_DNA_LOOT_ITEM = "item_cs_dna_wampa";
    public static final int WAMPA_DNA_LOOT_CHANCE = 5;
    public static final int UNCLE_JOE_MAX_DISTANCE = 104;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_UNCLE_JOE);
        applySkillStatisticModifier(self, "expertise_glancing_blow_reduction", 100);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "open_balance_buff", -1.0f);
        obj_id[] players = getPlayerCreaturesInRange(self, 250.0f);
        if (players != null && players.length > 0)
        {
            for (obj_id player : players) {
                if (isIdValid(player) && exists(player) && !isIncapacitated(player) && !isDead(player)) {
                    addHate(self, player, 1000.0f);
                    startCombat(self, player);
                }
            }
        }
        messageTo(self, "handleUncleJoeDistanceCheck", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handleUncleJoeDistanceCheck(obj_id self, dictionary params) throws InterruptedException
    {
        location currentLoc = getLocation(self);
        location creationLoc = aiGetHomeLocation(self);
        float distanceCheck = utils.getDistance2D(currentLoc, creationLoc);
        if (distanceCheck > UNCLE_JOE_MAX_DISTANCE)
        {
            int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
            setAttrib(self, HEALTH, maxHealth);
            setInvulnerable(self, true);
            stopCombat(self);
        }
        else
        {
            messageTo(self, "handleUncleJoeDistanceCheck", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetAdded(obj_id self, obj_id target) throws InterruptedException
    {
        reportHateListTesting(self, "OnHateTargetAdded");
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetChanged(obj_id self, obj_id target) throws InterruptedException
    {
        reportHateListTesting(self, "OnHateTargetChanged");
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetRemoved(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isInvulnerable(self))
        {
            dictionary webster = new dictionary();
            webster.put("target", target);
            messageTo(self, "handleUncleJoePutsYouBackIntoCombat", webster, 2, false);
        }
        reportHateListTesting(self, "OnHateTargetRemoved");
        return SCRIPT_CONTINUE;
    }
    public int handleUncleJoePutsYouBackIntoCombat(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        if (!isInvulnerable(self))
        {
            obj_id target = params.getObjId("target");
            if (isIdValid(target))
            {
                if (!isDead(target) && !isIncapacitated(target) && pvpCanAttack(self, target))
                {
                    stealth.checkForAndMakeVisible(target);
                    addHate(self, target, 1000.0f);
                    startCombat(self, target);
                }
            }
            reportHateListTesting(self, "handleUncleJoePutsYouBackIntoCombat");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleUncleJoeHateTesting(obj_id self, dictionary params) throws InterruptedException
    {
        reportHateListTesting(self, "handleUncleJoeHateTesting");
        messageTo(self, "handleUncleJoeHateTesting", null, 19, false);
        return SCRIPT_CONTINUE;
    }
    public void reportHateListTesting(obj_id self, String from) throws InterruptedException
    {
        String hateReport = "Uncle Joe Hate List \n";
        hateReport += "(" + from + ") \n";
        hateReport += "------------------- \n";
        obj_id[] uncleJoeHateList = getHateList(self);
        if (uncleJoeHateList != null && uncleJoeHateList.length > 0)
        {
            for (int j = 0; j < uncleJoeHateList.length; j++)
            {
                obj_id hated = uncleJoeHateList[j];
                String hatedName = getName(hated);
                hateReport += "# " + j + ": " + hatedName + " (" + hated + ") \n";
            }
        }
        obj_id[] players = getPlayerCreaturesInRange(self, 250.0f);
        if (players != null && players.length > 0)
        {
            for (obj_id player : players) {
                if (isGod(player) && hasObjVar(player, "uncleJoeHateTesting")) {
                    sendSystemMessage(player, hateReport, "");
                }
            }
        }
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        reportHateListTesting(self, "OnExitedCombat");
        if (utils.hasScriptVarTree(self, ADDS_VAR_BASE))
        {
            utils.removeScriptVarTree(self, ADDS_VAR_BASE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        int maxHealth = getUnmodifiedMaxAttrib(self, HEALTH);
        int currentHealth = getAttrib(self, HEALTH);
        if (currentHealth <= maxHealth * 0.1 && !utils.hasScriptVar(self, TEN_PERCENT))
        {
            messageTo(self, "summon_adds", null, 0.0f, false);
            utils.setScriptVar(self, TEN_PERCENT, 1);
        }
        else if (currentHealth <= maxHealth * 0.2 && !utils.hasScriptVar(self, TWENTY_PERCENT))
        {
            messageTo(self, "summon_adds", null, 0.0f, false);
            utils.setScriptVar(self, TWENTY_PERCENT, 1);
        }
        else if (currentHealth <= maxHealth * 0.4 && !utils.hasScriptVar(self, FORTY_PERCENT))
        {
            messageTo(self, "summon_adds", null, 0.0f, false);
            utils.setScriptVar(self, FORTY_PERCENT, 1);
        }
        else if (currentHealth <= maxHealth * 0.6 && !utils.hasScriptVar(self, SIXTY_PERCENT))
        {
            messageTo(self, "summon_adds", null, 0.0f, false);
            utils.setScriptVar(self, SIXTY_PERCENT, 1);
        }
        else if (currentHealth <= maxHealth * 0.8 && !utils.hasScriptVar(self, EIGHTY_PERCENT))
        {
            messageTo(self, "summon_adds", null, 0.0f, false);
            utils.setScriptVar(self, EIGHTY_PERCENT, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int summon_adds(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id parent = trial.getParent(self);
        if (isIdValid(parent))
        {
            dictionary dict = trial.getSessionDict(parent);
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "summon_wampas");
            messageTo(parent, "triggerFired", dict, 0.0f, false);
            messageTo(self, "wampaBossLinkAgro", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int wampaBossLinkAgro(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        ai_lib.establishAgroLink(self, 250.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id[] children = trial.getChildArray(self);
        if (children != null && children.length > 0)
        {
            for (obj_id child : children) {
                if (isIdValid(child) && exists(child)) {
                    trial.cleanupObject(child);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chanceDna = rand(1, 100);
        if (chanceDna > WAMPA_DNA_LOOT_CHANCE)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        obj_id dna = static_item.createNewItemFunction(WAMPA_DNA_LOOT_ITEM, inv);
        return SCRIPT_CONTINUE;
    }
}
