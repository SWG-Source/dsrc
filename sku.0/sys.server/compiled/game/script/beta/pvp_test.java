package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.utils;

public class pvp_test extends script.base_script
{
    public pvp_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "PvP debug script attached.");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.startsWith("pvp "))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(toLower(text));
            String arg = st.nextToken();
            obj_id target = getLookAtTarget(self);
            if (target == null || target == obj_id.NULL_ID)
            {
                sendSystemMessageTestingOnly(self, "PVP_STATUS: no target designated. assuming target = self");
                target = self;
            }
            if (st.hasMoreTokens())
            {
                do
                {
                    arg = st.nextToken();
                    if (arg.equals("status"))
                    {
                        int type = pvpGetType(target);
                        int faction = pvpGetAlignedFaction(target);
                        String sType = "neutral";
                        String sFaction = "mercenary";
                        switch (type)
                        {
                            case PVPTYPE_NEUTRAL:
                            break;
                            case PVPTYPE_COVERT:
                            sType = "covert";
                            break;
                            case PVPTYPE_DECLARED:
                            sType = "declared";
                            break;
                            default:
                            sType = "type_other";
                            break;
                        }
                        switch (faction)
                        {
                            case 0:
                            sFaction = "mercenary";
                            break;
                            case (370444368):
                            sFaction = "rebel";
                            break;
                            case (-615855020):
                            sFaction = "imperial";
                            break;
                            default:
                            sFaction = "faction_other";
                            break;
                        }
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + ") is a " + sType + " " + sFaction);
                        displayEnemyFlags(self, target);
                    }
                    else if (arg.equals("enemyTo"))
                    {
                        displayEnemyFlags(self, target);
                    }
                    else if (arg.equals("unalign") || arg.equals("mercenary"))
                    {
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): faction set UNALIGNED");
                        pvpSetAlignedFaction(target, 0);
                    }
                    else if (arg.equals("rebel"))
                    {
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): faction set REBEL");
                        pvpSetAlignedFaction(target, (370444368));
                    }
                    else if (arg.equals("imperial"))
                    {
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): faction set IMPERIAL");
                        pvpSetAlignedFaction(target, (-615855020));
                    }
                    else if (arg.equals("neutral"))
                    {
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): type set NEUTRAL");
                        pvpMakeNeutral(target);
                    }
                    else if (arg.equals("covert"))
                    {
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): type set COVERT");
                        pvpMakeCovert(target);
                    }
                    else if (arg.equals("declared"))
                    {
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): type set DECLARED");
                        pvpMakeDeclared(target);
                    }
                    else if (arg.equals("clear"))
                    {
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): clearing temp enemy flags");
                        pvpRemoveAllTempEnemyFlags(target);
                    }
                    else if (arg.equals("faction"))
                    {
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): " + factions.getFaction(target));
                    }
                    else if (arg.equals("nonaggressive"))
                    {
                        pvpMakeNeutral(target);
                        pvpSetAlignedFaction(target, (221551254));
                        pvpMakeDeclared(target);
                        sendSystemMessageTestingOnly(self, getName(target) + "(" + target + "): " + factions.getFaction(target));
                    }
                } while (st.hasMoreTokens());
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean displayEnemyFlags(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] enemies = pvpGetEnemyFlags(target);
        if (enemies != null)
        {
            for (int i = 0; i < enemies.length; ++i)
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(enemies[i]);
                String sTarget = st.nextToken();
                String sTargetName = getPlayerName(utils.stringToObjId(sTarget));
                String sTefFac = st.nextToken();
                int iTefFac = utils.stringToInt(sTefFac);
                String sTefFacName = "<unknown>";
                if (iTefFac == (-526735576))
                {
                    sTefFacName = "battlefield";
                }
                else if (iTefFac == (1183528962))
                {
                    sTefFacName = "duel";
                }
                else if (iTefFac == (-429740311))
                {
                    sTefFacName = "bountyduel";
                }
                else if (iTefFac == (221551254))
                {
                    sTefFacName = "nonaggressive";
                }
                else if (iTefFac == (-160237431))
                {
                    sTefFacName = "unattackable";
                }
                else if (iTefFac == (84709322))
                {
                    sTefFacName = "bountytarget";
                }
                else if (iTefFac == (-1526926610))
                {
                    sTefFacName = "guildwarcooldownperiod";
                }
                else if (iTefFac == (-615855020))
                {
                    sTefFacName = "imperial";
                }
                else if (iTefFac == (370444368))
                {
                    sTefFacName = "rebel";
                }
                else if (iTefFac == (-377582139))
                {
                    sTefFacName = "bubblecombat";
                }
                String sExpiration = st.nextToken();
                sendSystemMessageTestingOnly(player, "(" + player + " " + getName(player) + ") Enemy Flag: [" + sTarget + " (" + sTargetName + "), " + sTefFac + " (" + sTefFacName + "), " + sExpiration + "ms" + "]");
            }
        }
        return true;
    }
}
