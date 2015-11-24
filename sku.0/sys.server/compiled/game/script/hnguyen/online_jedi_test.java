package script.hnguyen;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.jedi;
import java.util.StringTokenizer;

public class online_jedi_test extends script.base_script
{
    public online_jedi_test()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.startsWith("ojt3"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String file = st.nextToken();
                if (true)
                {
                    int numOnlineJedis = 0;
                    int numOfflineJedis = 0;
                    String strOutput = "";
                    String strOffline = "offline jedi\r\n";
                    dictionary dctJediInfo = requestJedi(IGNORE_JEDI_STAT, IGNORE_JEDI_STAT, 1, 1000, IGNORE_JEDI_STAT, IGNORE_JEDI_STAT);
                    if (dctJediInfo == null)
                    {
                        strOutput = "requestJedi() returned NULL dctJediInfo\r\n";
                    }
                    else 
                    {
                        obj_id[] objJedis = dctJediInfo.getObjIdArray("id");
                        boolean[] boolOnline = dctJediInfo.getBooleanArray("online");
                        String[] strNames = dctJediInfo.getStringArray("name");
                        int[] visibility = dctJediInfo.getIntArray("visibility");
                        int[] bountyValue = dctJediInfo.getIntArray("bountyValue");
                        int[] level = dctJediInfo.getIntArray("level");
                        int[] faction = dctJediInfo.getIntArray("faction");
                        location[] loc = dctJediInfo.getLocationArray("location");
                        obj_id[][] bounties = (obj_id[][])(dctJediInfo.get("bounties"));
                        if (objJedis != null && boolOnline != null && strNames != null && visibility != null)
                        {
                            strOutput = "requestJedi() returned " + objJedis.length + " jedis\r\n";
                            for (int i = 0; i < objJedis.length; ++i)
                            {
                                if (boolOnline[i])
                                {
                                    ++numOnlineJedis;
                                    strOutput += objJedis[i];
                                    strOutput += " [";
                                    strOutput += strNames[i];
                                    strOutput += ",";
                                    strOutput += visibility[i];
                                    strOutput += ",";
                                    strOutput += bountyValue[i];
                                    strOutput += ",";
                                    strOutput += level[i];
                                    strOutput += ",";
                                    strOutput += getFactionString(faction[i]);
                                    strOutput += ",";
                                    strOutput += loc[i];
                                    strOutput += ", hunted by (";
                                    for (int j = 0; j < bounties[i].length; ++j)
                                    {
                                        if (j > 0)
                                        {
                                            strOutput += ",";
                                        }
                                        strOutput += bounties[i][j];
                                        strOutput += "-";
                                        strOutput += getPlayerName(bounties[i][j]);
                                    }
                                    strOutput += ")";
                                    strOutput += "] is online\r\n";
                                }
                                else 
                                {
                                    ++numOfflineJedis;
                                    strOffline += objJedis[i];
                                    strOffline += " [";
                                    strOffline += strNames[i];
                                    strOffline += ",";
                                    strOffline += visibility[i];
                                    strOffline += ",";
                                    strOffline += bountyValue[i];
                                    strOffline += ",";
                                    strOffline += level[i];
                                    strOffline += ",";
                                    strOffline += getFactionString(faction[i]);
                                    strOffline += ",";
                                    strOffline += loc[i];
                                    strOffline += ", hunted by (";
                                    for (int j = 0; j < bounties[i].length; ++j)
                                    {
                                        if (j > 0)
                                        {
                                            strOffline += ",";
                                        }
                                        strOffline += bounties[i][j];
                                        strOffline += "-";
                                        strOffline += getPlayerName(bounties[i][j]);
                                    }
                                    strOffline += ")";
                                    strOffline += "] is offline\r\n";
                                }
                            }
                        }
                        else 
                        {
                            strOutput = "requestJedi() returned null data\r\n";
                        }
                    }
                    strOutput += numOnlineJedis;
                    strOutput += " online jedis\r\n";
                    strOffline += numOfflineJedis;
                    strOffline += " offline jedis\r\n";
                    saveTextOnClient(self, "online_jedi" + file + ".txt", strOutput);
                    saveTextOnClient(self, "offline_jedi" + file + ".txt", strOffline);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int hnguyencrbtConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bhId = params.getObjId("hunter");
        obj_id jediId = params.getObjId("jedi");
        int bounties = params.getInt("bounties");
        String bhName = "";
        String jediName = "";
        if ((bhId != null) && (isIdValid(bhId)))
        {
            bhName = getPlayerName(bhId);
        }
        if ((jediId != null) && (isIdValid(jediId)))
        {
            jediName = getPlayerName(jediId);
        }
        sendSystemMessageTestingOnly(self, "SUCCESS - bounty created bh=(" + bhId + "," + bhName + ") jedi=(" + jediId + "," + jediName + "," + bounties + ")");
        return SCRIPT_CONTINUE;
    }
    public int hnguyencrbtFailed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bhId = params.getObjId("hunter");
        obj_id jediId = params.getObjId("jedi");
        int bounties = params.getInt("bounties");
        String bhName = "";
        String jediName = "";
        if ((bhId != null) && (isIdValid(bhId)))
        {
            bhName = getPlayerName(bhId);
        }
        if ((jediId != null) && (isIdValid(jediId)))
        {
            jediName = getPlayerName(jediId);
        }
        sendSystemMessageTestingOnly(self, "FAILED - bounty not created bh=(" + bhId + "," + bhName + ") jedi=(" + jediId + "," + jediName + "," + bounties + ")");
        return SCRIPT_CONTINUE;
    }
    public String getFactionString(int iTefFac) throws InterruptedException
    {
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
        return sTefFacName;
    }
}
