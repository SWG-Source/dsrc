package script.developer;

import script.location;
import script.obj_id;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Simple tool for getting around dungeons quickly.
 *
 * Usage: attach developer.dungeon_utility
 * Be standing in the dungeon. Then say in spatial:
 *
 * "dungeonBottom" -> warp to bottom most cell
 * "dungeonTop" -> warp to ejection point
 * "dungeonClean" -> kill all mobs in dungeon
 * "dungeonSpawn" -> start spawn process for all dungeon mobs
 * "dungeonJumpMob (mob)" -> warps you to the first instance we can find of X creature name
 * 		(this is helpful for jumping to the room of a specific NPC)
 *
 * @since SWG Source 3.1 - September 2021
 * @author Aconite
 */
public class dungeon_utility extends script.base_script
{

	public dungeon_utility()
	{
	}

	public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
	{
		if (!isGod(objSpeaker))
		{
			return SCRIPT_CONTINUE;
		}

		final obj_id dungeon = getTopMostContainer(self);
		if(!isIdValid(dungeon))
		{
			sendSystemMessageTestingOnly(self, "ERROR: You must be standing inside the dungeon to use this command.");
			return SCRIPT_CONTINUE;
		}
		if(!hasScript(dungeon, "theme_park.dungeon.generic_spawner"))
		{
			sendSystemMessageTestingOnly(self, "ERROR: This structure isn't a dungeon or doesn't use a compatible dungeon spawning script.");
			return SCRIPT_CONTINUE;
		}

		StringTokenizer st = new StringTokenizer(strText);
		if(!st.hasMoreTokens())
		{
			return SCRIPT_CONTINUE;
		}
		strText = st.nextToken();

		if(strText.equalsIgnoreCase("dungeonTop"))
		{
			sendSystemMessageTestingOnly(self, "Sending you to the ejection point of the dungeon...");
			warpPlayer(self, getBuildingEjectLocation(dungeon), null, false);
		}

		if(strText.equalsIgnoreCase("dungeonBottom"))
		{
			final String[] names = getCellNames(dungeon);
			if(names == null || names.length < 2)
			{
				sendSystemMessageTestingOnly(self, "ERROR: No cells found to warp you to.");
				return SCRIPT_CONTINUE;
			}
			final location dloc = getLocation(dungeon);
			final location cloc = getLocation(getCellId(dungeon, names[names.length-1]));
			sendSystemMessageTestingOnly(self, "Sending you to the lowest point of the dungeon...");
			warpPlayer(self, dloc.area, dloc.x, dloc.y, dloc.z, dungeon, names[names.length-1], cloc.x, cloc.y, cloc.z, "", false);
		}

		if(strText.equalsIgnoreCase("dungeonJumpMob"))
		{
			if(!st.hasMoreTokens())
			{
				sendSystemMessageTestingOnly(self, "ERROR: Specify a creature type, like \"dugeonJumpMob nightsister_elder\"");
			}
			final String mob = st.nextToken();
			final String[] values = Arrays.stream(getPackedObjvars(dungeon, "spawned").split("\\|"))
					.filter(v -> v.matches("^[0-9]*$"))
					.filter(v -> Long.parseLong(v) > 100000).toArray(String[]::new);
			for (String value : values) {
				final obj_id id = obj_id.getObjId(Long.parseLong(value));
				if(isIdValid(id) && isMob(id))
				{
					if(getCreatureName(id).equalsIgnoreCase(mob))
					{
						sendSystemMessageTestingOnly(self, "Found mob of type "+mob+" with ID "+id+" so warping you to them...");
						warpPlayer(self, getLocation(id), null, false);
						return SCRIPT_CONTINUE;
					}
				}
			}
			sendSystemMessageTestingOnly(self, "ERROR: Couldn't find a mob of name "+mob+" in dungeon "+dungeon);
			return SCRIPT_CONTINUE;
		}

		if(strText.equalsIgnoreCase("dungeonClean"))
		{
			messageTo(self, "dungeonCleanup", null, 0f, false);
			sendSystemMessageTestingOnly(self, "Starting cleanup process for dungeon "+dungeon+"...");
			return SCRIPT_CONTINUE;
		}

		if(strText.equalsIgnoreCase("dungeonSpawn"))
		{
			messageTo(self, "beginSpawn", null, 0f, false);
			sendSystemMessageTestingOnly(self, "Starting spawn process for dungeon "+dungeon+"...");
			return SCRIPT_CONTINUE;
		}

		return SCRIPT_CONTINUE;
	}

}
