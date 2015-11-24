package script.working.rdelashmit;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_transition;

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
                    for (int i = 0; i < allies.length; ++i)
                    {
                        sendSystemMessageTestingOnly(self, "AlliedFaction: " + allies[i] + " (" + getFactionString(allies[i]) + ")");
                    }
                }
                if (enemies != null)
                {
                    for (int i = 0; i < enemies.length; ++i)
                    {
                        sendSystemMessageTestingOnly(self, "EnemyFaction: " + enemies[i] + " (" + getFactionString(enemies[i]) + ")");
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
        for (int i = 0; i < factionStrings.length; ++i)
        {
            if (getStringCrc(factionStrings[i]) == faction)
            {
                return factionStrings[i];
            }
        }
        return "<< unknown >>";
    }
}
