package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.guild;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class terminal_guild_cartridge extends script.base_script
{
    public terminal_guild_cartridge()
    {
    }
    public static final string_id SID_GUILD_LOAD_CARTRIDGE = new string_id("guild", "load_cartridge");
    public static final string_id SID_LOAD_CARTRIDGE_PROMPT = new string_id("guild", "load_cartridge_prompt");
    public static final string_id SID_LOAD_CARTRIDGE_TITLE = new string_id("guild", "load_cartridge_title");
    public static final String STR_GUILD_CREATE_NAME_PROMPT = "@guild:create_name_prompt";
    public static final String STR_GUILD_CREATE_NAME_TITLE = "@guild:create_name_title";
    public static final String STR_GUILD_CREATE_ABBREV_PROMPT = "@guild:create_abbrev_prompt";
    public static final String STR_GUILD_CREATE_ABBREV_TITLE = "@guild:create_abbrev_title";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int playerGuildId = getGuildId(player);
        if (playerGuildId != 0 && utils.isNestedWithinAPlayer(self))
        {
            if (player == guildGetLeader(playerGuildId))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_GUILD_LOAD_CARTRIDGE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        int playerGuildId = getGuildId(player);
        if (playerGuildId != 0 && utils.isNestedWithinAPlayer(self))
        {
            if (player == guildGetLeader(playerGuildId))
            {
                int pid = sui.msgbox(self, player, "@" + SID_LOAD_CARTRIDGE_PROMPT, sui.YES_NO, "@" + SID_LOAD_CARTRIDGE_TITLE, "handleLoadCartridge");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLoadCartridge(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int guildId = getGuildId(player);
        if (player != guildGetLeader(guildId))
        {
            return SCRIPT_CONTINUE;
        }
        if (guild.loadCartridge(player, self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
