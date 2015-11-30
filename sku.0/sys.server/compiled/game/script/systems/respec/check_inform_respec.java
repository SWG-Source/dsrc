package script.systems.respec;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.respec;
import script.library.sui;
import script.library.utils;

public class check_inform_respec extends script.base_script
{
    public check_inform_respec()
    {
    }
    public static final string_id POPUP_RESPEC_HAS_TOKEN_PROMPT = new string_id("ui_respec", "server_inform_respec_token");
    public static final string_id POPUP_RESPEC_AUTHORIZED_PROMPT = new string_id("ui_respec", "server_inform_respec_authorized");
    public static final string_id POPUP_RESPEC_AUTHORIZED_NO_COUNT_PROMPT = new string_id("ui_respec", "server_inform_respec_authorized_no_count");
    public static final string_id POPUP_RESPEC_EXPIRING_PROMPT = new string_id("ui_respec", "server_inform_respec_expire");
    public static final string_id POPUP_RESPEC_JEDI_PROMPT = new string_id("ui_respec", "server_inform_respec_jedi");
    public static final String POPUP_LATER = "@ui_respec:later";
    public static final String POPUP_TITLE = "@ui_respec:title";
    public static final String POPUP_YES = "@ui:yes";
    public static final String HANDLER_ON_AUTHORIZED = "onAuthorized";
    public static final String HANDLER_ON_STAT_AUTHORIZED = "onStatAuthorized";
    public static final String EVENT_WIDGET_NAME = "eventWidgetName";
    public static final String EVENT_TYPE = "eventType";
    public static final String EVENT_TYPE_ON_CLOSED_OK = "onClosedOk";
    public static final String EVENT_TYPE_ON_CLOSED_CANCEL = "onClosedCancel";
    public static final String PROPERTY_SIZE = "Size";
    public static final String VALUE_SIZE = "450,375";
    public static final String SKILLS_RESPEC_ACTION = "/ui action skillsRespec";
    public int OnLogin(obj_id self) throws InterruptedException
    {
        detachScript(self, respec.SCRIPT_CHECK_INFORM);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, respec.SCRIPT_CHECK_INFORM);
        return SCRIPT_CONTINUE;
    }
}
