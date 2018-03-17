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

public class terminal_guild_registry extends script.base_script
{
    public terminal_guild_registry()
    {
    }
    public static final string_id SID_GUILD_CREATE = new string_id("guild", "menu_create");
    public static final String STR_GUILD_CREATE_NAME_PROMPT = "@guild:create_name_prompt";
    public static final String STR_GUILD_CREATE_NAME_TITLE = "@guild:create_name_title";
    public static final String STR_GUILD_CREATE_ABBREV_PROMPT = "@guild:create_abbrev_prompt";
    public static final String STR_GUILD_CREATE_ABBREV_TITLE = "@guild:create_abbrev_title";
    public static final string_id STR_GUILD_OPEN_REGISTRY = new string_id("guild", "open_registry");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int playerGuildId = getGuildId(player);
        if (playerGuildId == 0 && utils.isNestedWithinAPlayer(self))
        {
            mi.addRootMenu(menu_info_types.SERVER_GUILD_CREATE, SID_GUILD_CREATE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        int playerGuildId = getGuildId(player);
        if (playerGuildId == 0)
        {
            sui.inputbox(self, player, STR_GUILD_CREATE_NAME_PROMPT, sui.OK_CANCEL, STR_GUILD_CREATE_NAME_TITLE, sui.INPUT_NORMAL, null, "onGuildCreateNameResponse");
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildCreateNameResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String newGuildName = sui.getInputBoxText(params);
            setMenuContextString(self, player, "guildCreateName", newGuildName);
            sui.inputbox(self, player, STR_GUILD_CREATE_ABBREV_PROMPT, sui.OK_CANCEL, STR_GUILD_CREATE_ABBREV_TITLE, sui.INPUT_NORMAL, null, "onGuildCreateAbbrevResponse");
        }
        return SCRIPT_CONTINUE;
    }
    public int onGuildCreateAbbrevResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String newGuildName = getMenuContextString(self, player, "guildCreateName");
        removeMenuContextVar(self, player, "guildCreateName");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String newGuildAbbrev = sui.getInputBoxText(params);
            int guildId = guild.create(player, newGuildName, newGuildAbbrev);
            if (guildId != 0)
            {
                obj_id pInv = utils.getInventoryContainer(player);
                obj_id guildScreen = createObjectOverloaded(guild.GUILD_SCREEN_TEMPLATE, pInv);
                if (isIdValid(guildScreen))
                {
                    obj_id guildLeaderPDA = guild.getGuildRemoteDevice(player);
                    setOwner(guildScreen, player);
                    if (isIdValid(guildLeaderPDA) && exists(guildLeaderPDA))
                    {
                        setObjVar(guildLeaderPDA, guild.GUILD_SCREEN_ID, guildScreen);
                        setObjVar(guildScreen, guild.GUILD_SCREEN_ID, guildLeaderPDA);
                        setObjVar(guildScreen, guild.REGISTERED_GUILD, guildId);
                        destroyObject(self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String getMenuContextString(obj_id self, obj_id player, String varName) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        return dd.getString("guildMenu." + player + "." + varName);
    }
    public void setMenuContextString(obj_id self, obj_id player, String varName, String value) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        dd.put("guildMenu." + player + "." + varName, value);
    }
    public void removeMenuContextVar(obj_id self, obj_id player, String varName) throws InterruptedException
    {
        deltadictionary dd = self.getScriptVars();
        dd.remove("guildMenu." + player + "." + varName);
    }
}
