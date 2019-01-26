package script.working.rdelashmit;

import script.library.space_transition;
import script.obj_id;

public class ship_faction_debug extends script.base_script
{
    public ship_faction_debug()
    {
    }
    public static final String[] factionStrings = 
    {
        "aynat",
        "beta_neutral",
        "blacksun",
        "borvo",
        "civilian",
        "corsec",
        "hutt",
        "imperial",
        "jabba",
        "merchant",
        "nym",
        "pirate",
        "rebel",
        "rebel_suspect",
        "rsf",
        "smuggler",
        "spynet",
        "station",
        "true_neutral",
        "unattackable",
        "valarian"
    };
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equalsIgnoreCase("targetfactioninfo"))
        {
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Target something, then say targetfactioninfo again.");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Target Faction Info for " + target);
                int faction = shipGetSpaceFaction(target);
                int[] allies = shipGetSpaceFactionAllies(target);
                int[] enemies = shipGetSpaceFactionEnemies(target);
                boolean isAggro = shipGetSpaceFactionIsAggro(target);
                boolean isEnemy = pvpIsEnemy(target, space_transition.getContainingShip(self));
                sendSystemMessageTestingOnly(self, "Faction: " + faction + " (" + getFactionString(faction) + ")");
                if (allies != null)
                {
                    for (int ally : allies) {
                        sendSystemMessageTestingOnly(self, "AlliedFaction: " + ally + " (" + getFactionString(ally) + ")");
                    }
                }
                if (enemies != null)
                {
                    for (int enemy : enemies) {
                        sendSystemMessageTestingOnly(self, "EnemyFaction: " + enemy + " (" + getFactionString(enemy) + ")");
                    }
                }
                sendSystemMessageTestingOnly(self, "IsAggro: " + isAggro + " isEnemy: " + isEnemy);
            }
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public String getFactionString(int faction) throws InterruptedException
    {
        for (String factionString : factionStrings) {
            if (getStringCrc(factionString) == faction) {
                return factionString;
            }
        }
        return "<< unknown >>";
    }
}
