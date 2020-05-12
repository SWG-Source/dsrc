/**************************************************************************
 * Java-side combat processing.
 * @todo: where do error messages from this class go? Right now we're just
 * printing to stderr, but do we want to hook in with our logging code?
 *************************************************************************/

package script;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class gcw_score
{
	private static Hashtable m_GcwScoreCache = new Hashtable();
	private static int invasionPhase = 0;
	private static int invasionWinner = 0;

	// Player GCW score data

	public static gcw_data getPlayerGcwData(obj_id player)
	{
		if(m_GcwScoreCache.containsKey("" + player))
		{
			return gcw_data.clone((gcw_data)m_GcwScoreCache.get("" + player));
		}

		return null;
	}

	public static String[][] getAllGcwData()
	{
		if(m_GcwScoreCache == null || m_GcwScoreCache.isEmpty())
		{
			return null;
		}
	
		int size = m_GcwScoreCache.size();
		String[][] allData = new String[m_GcwScoreCache.size()][10];
        Iterator iterator = m_GcwScoreCache.values().iterator();
		
		for(int i = 0; iterator.hasNext(); i++)
		{
			gcw_data playerGcwData = (gcw_data) iterator.next();
			
			if(playerGcwData != null && i < size)
			{
				allData[i][0] = playerGcwData.playerName;
				allData[i][1] = playerGcwData.playerFaction;
				allData[i][2] = playerGcwData.playerProfession;
				allData[i][3] = Integer.toString(playerGcwData.playerLevel);
				allData[i][4] = Integer.toString(playerGcwData.playerGCW);
				allData[i][5] = Integer.toString(playerGcwData.playerPvpKills);
				allData[i][6] = Integer.toString(playerGcwData.playerKills);
				allData[i][7] = Integer.toString(playerGcwData.playerAssists);
				allData[i][8] = Integer.toString(playerGcwData.playerCraftedItems);
				allData[i][9] = Integer.toString(playerGcwData.playerDestroyedItems);
			}
		}
	
		return allData;
	}

	public static void setGcwPhase(int phase)
	{
		invasionPhase = phase;
	}

	public static int getGcwPhase()
	{
		return invasionPhase;
	}
	
	public static void setGcwWinner(int winner)
	{
		invasionWinner = winner;
	}

	public static int getGcwWinner()
	{
		return invasionWinner;
	}

	public static void setPlayerGcwData(obj_id player, String playerName, String playerFaction, String playerProfession, int playerLevel, int playerGCW, int playerPvpKills, int playerKills, int playerAssists, int playerCraftedItems, int playerDestroyedItems)
	{
		gcw_data playerGcwData = new gcw_data();
		
		playerGcwData.playerName = playerName;
		playerGcwData.playerFaction = playerFaction;
		playerGcwData.playerProfession = playerProfession;
		playerGcwData.playerLevel = playerLevel;
		playerGcwData.playerGCW = playerGCW;
		playerGcwData.playerPvpKills = playerPvpKills;
		playerGcwData.playerKills = playerKills;
		playerGcwData.playerAssists = playerAssists;
		playerGcwData.playerCraftedItems = playerCraftedItems;
		playerGcwData.playerDestroyedItems = playerDestroyedItems;
		
		m_GcwScoreCache.put("" + player, playerGcwData);
	}

	public static void clearGcwData()
	{
		m_GcwScoreCache.clear();
		invasionPhase = 0;
		invasionWinner = 0;
	}

	public static class gcw_data implements java.lang.Cloneable
	{
		public static gcw_data clone(gcw_data template)
		{
			try
			{
				gcw_data cd = (gcw_data)template.clone();
				return cd;
			}
			catch(java.lang.CloneNotSupportedException exc)
			{
				return null;
			}
		}

		public String 	playerName 		= "";
		public String 	playerFaction		= "";
		public String	playerProfession	= "";
		public int	playerLevel		= 0;
		public int	playerGCW		= 0;
		public int	playerPvpKills		= 0;
		public int 	playerKills		= 0;
		public int 	playerAssists		= 0;
		public int 	playerCraftedItems	= 0;
		public int 	playerDestroyedItems	= 0;
	}
}	// class gcw_score



