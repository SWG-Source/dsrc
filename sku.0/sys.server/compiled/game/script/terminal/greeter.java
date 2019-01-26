package script.terminal;

import script.*;
import script.library.*;

import java.util.Vector;

public class greeter extends script.terminal.base.base_terminal
{
    public greeter()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "greeter";
    public static final string_id SID_GREETER_STATUS = new string_id("player_structure", "greeter_status");
    public static final string_id SID_GREETER_INIT = new string_id("player_structure", "greeter_init");
    public static final string_id SID_GREETER_INITIALIZED = new string_id("player_structure", "greeter_initialized_message");
    public static final string_id SID_GREETER_CONTROL = new string_id("player_structure", "greeter_control");
    public static final string_id SID_REMOVE_GREETER = new string_id("player_structure", "remove_greeter");
    public static final string_id SID_CUSTOMIZE_GREETER = new string_id("player_structure", "customize_greeter");
    public static final string_id SID_CHANGE_NAME = new string_id("player_structure", "greeter_change_name");
    public static final string_id SID_GREETER_PREVIEW_ALL = new string_id("player_structure", "greeter_preview_all");
    public static final string_id SID_GREETER_MODIFY_DELAY = new string_id("player_structure", "greeter_modify_greeting_delay");
    public static final string_id SID_GREETER_SAVE_GREET = new string_id("player_structure", "greeter_save_greet");
    public static final string_id SID_GREETER_LOAD_GREET = new string_id("player_structure", "greeter_load_greet");
    public static final string_id SID_GREETER_ACTIVE_ON = new string_id("player_structure", "greeter_activation_on");
    public static final string_id SID_GREETER_ACTIVE_OFF = new string_id("player_structure", "greeter_activation_off");
    public static final string_id SID_GREETER_VOICE_ON = new string_id("player_structure", "greeter_voicing_on");
    public static final string_id SID_GREETER_VOICE_OFF = new string_id("player_structure", "greeter_voicing_off");
    public static final string_id SID_GREETER_ANIMATING_ON = new string_id("player_structure", "greeter_animating_on");
    public static final string_id SID_GREETER_ANIMATING_OFF = new string_id("player_structure", "greeter_animating_off");
    public static final string_id SID_GREETER_SOUND_ON = new string_id("player_structure", "greeter_sounds_on");
    public static final string_id SID_GREETER_SOUND_OFF = new string_id("player_structure", "greeter_sounds_off");
    public static final string_id SID_GREETER_MOOD_ON = new string_id("player_structure", "greeter_mood_on");
    public static final string_id SID_GREETER_MOOD_OFF = new string_id("player_structure", "greeter_mood_off");
    public static final string_id SID_GREETER_EFFECT_ON = new string_id("player_structure", "greeter_effects_on");
    public static final string_id SID_GREETER_EFFECT_OFF = new string_id("player_structure", "greeter_effects_off");
    public static final string_id SID_GREETER_STATEMENT_ON = new string_id("player_structure", "greeter_statement_on");
    public static final string_id SID_GREETER_STATEMENT_OFF = new string_id("player_structure", "greeter_statement_off");
    public static final string_id SID_COLOR = new string_id("player_structure", "greeter_color");
    public static final string_id SID_GREETER_WAS_RENAMED = new string_id("player_structure", "greeter_renamed");
    public static final string_id SID_ACTIVATION_ENABLED = new string_id("player_structure", "greeter_activation_enabled");
    public static final string_id SID_ACTIVATION_DISABLED = new string_id("player_structure", "greeter_activation_disabled");
    public static final string_id SID_VOICING_ENABLED = new string_id("player_structure", "greeter_voice_enabled");
    public static final string_id SID_VOICING_DISABLED = new string_id("player_structure", "greeter_voice_disabled");
    public static final string_id SID_ANIMATING_ENABLED = new string_id("player_structure", "greeter_animations_enabled");
    public static final string_id SID_ANIMATING_DISABLED = new string_id("player_structure", "greeter_animations_disabled");
    public static final string_id SID_SOUNDS_ENABLED = new string_id("player_structure", "greeter_sounds_enabled");
    public static final string_id SID_SOUNDS_DISABLED = new string_id("player_structure", "greeter_sounds_disabled");
    public static final string_id SID_MOOD_ENABLED = new string_id("player_structure", "greeter_mood_enabled");
    public static final string_id SID_MOOD_DISABLED = new string_id("player_structure", "greeter_mood_disabled");
    public static final string_id SID_EFFECT_ENABLED = new string_id("player_structure", "greeter_effects_enabled");
    public static final string_id SID_EFFECT_DISABLED = new string_id("player_structure", "greeter_effects_disabled");
    public static final string_id SID_STATEMENT_ENABLED = new string_id("player_structure", "greeter_statement_enabled");
    public static final string_id SID_STATEMENT_DISABLED = new string_id("player_structure", "greeter_statement_disabled");
    public static final string_id SID_GREETER_DRESSING_NOT_AVAIL = new string_id("player_structure", "greeter_dressing_not_avail");
    public static final string_id SID_MIN_FIVE_TIMER = new string_id("player_structure", "greeter_min_delay");
    public static final string_id SID_MAX_TIMER = new string_id("player_structure", "greeter_max_delay");
    public static final string_id SID_MIN_RADIUS = new string_id("player_structure", "greeter_min_radius");
    public static final string_id SID_MAX_RADIUS = new string_id("player_structure", "greeter_max_radius");
    public static final string_id SID_GREETER_SAVE_MAX = new string_id("player_structure", "greeter_max_saves");
    public static final string_id SID_NAME_ALREADY_EXISTS = new string_id("player_structure", "greeter_name_already_exists");
    public static final string_id SID_GREETER_RANDOM_GREETING_SELECTED = new string_id("player_structure", "greeter_random_selected");
    public static final string_id SID_OBSCENE = new string_id("player_structure", "greeter_obscene");
    public static final string_id SID_CANT_MOVE = new string_id("player_structure", "greeter_cant_move");
    public static final string_id SID_GREETER_NOT_IN_SHIP = new string_id("player_structure", "greeter_not_in_ship");
    public static final string_id SID_GREETER_PUBLIC_ONLY = new string_id("player_structure", "greeter_public_only");
    public static final string_id SID_NO_SAVED_GREETS = new string_id("player_structure", "greeter_no_saves");
    public static final string_id SID_GREETING_LOADED = new string_id("player_structure", "greeter_greeting_loaded");
    public static final string_id SID_GREETING_RADIUS_CHANGED = new string_id("player_structure", "greeter_radius_changed");
    public static final string_id SID_GREETING_DELAY_CHANGED = new string_id("player_structure", "greeter_delay_changed");
    public static final string_id SID_RANDOMIZED_GREETINGS_OFF = new string_id("player_structure", "greeter_random_greetings_off");
    public static final string_id SID_RANDOMIZED_GREETINGS_ON = new string_id("player_structure", "greeter_random_greetings_on");
    public static final string_id SID_NO_SAY_CHAT_DATA = new string_id("player_structure", "greeter_no_say_chat_data_found");
    public static final string_id SID_NO_SOUND_VO_EFFECT_DATA = new string_id("player_structure", "greeter_no_sound_vo_effect_data_found");
    public static final String SUI_GREETER_INSTRUCTIONS_TITLE = "@player_structure:greeter_main_instructions_title";
    public static final String SUI_GREETER_INSTRUCTIONS_DESCR = "@player_structure:greeter_main_instructions_description";
    public static final String SUI_GREETER_COLOR_DESC = "@player_structure:greeter_color_description";
    public static final String SUI_GREETER_COLOR_TITLE = "@player_structure:greeter_color_title";
    public static final String SUI_GREETER_LOAD_DESCR = "@player_structure:greeter_load_description";
    public static final String SUI_GREETER_LOAD_TITLE = "@player_structure:greeter_load_title";
    public static final String SUI_GREETER_SAVE_NAME_DESCR = "@player_structure:greeter_save_name_description";
    public static final String SUI_GREETER_SAVE_NAME_TITLE = "@player_structure:greeter_save_name_title";
    public static final String GREETER_RADIUS_DESCRIPTION = "@player_structure:greeter_radius_instructions_description";
    public static final String GREETER_RADIUS_TITLE = "@player_structure:greeter_radius_instructions_title";
    public static final String GREETER_DELAY_DESCRIPTION = "@player_structure:greeter_delay_instructions_description";
    public static final String GREETER_DELAY_TITLE = "@player_structure:greeter_delay_instructions_title";
    public static final String MENU_CUSTOMIZE_GREETER = "@player_structure:customize_greeter";
    public static final String MENU_CHANGE_NAME = "@player_structure:greeter_change_name";
    public static final String MENU_RANDOMIZED_GREETINGS_ON = "@player_structure:greeter_randomized_greeting_on";
    public static final String MENU_RANDOMIZED_GREETINGS_OFF = "@player_structure:greeter_randomized_greeting_off";
    public static final String MENU_GREETER_RANDOM_ALL = "@player_structure:greeter_random_greeting";
    public static final String MENU_GREETER_PREVIEW_ALL = "@player_structure:greeter_preview_all";
    public static final String MENU_GREETER_MODIFY_DELAY = "@player_structure:greeter_modify_greeting_delay";
    public static final String MENU_GREETER_SAVE_GREET = "@player_structure:greeter_save_greet";
    public static final String MENU_GREETER_LOAD_GREET = "@player_structure:greeter_load_greet";
    public static final String MENU_GREETER_LIST_GREET = "@player_structure:greeter_list_greet";
    public static final String MENU_GREETER_ACTIVE_ON = "@player_structure:greeter_activation_on";
    public static final String MENU_GREETER_ACTIVE_OFF = "@player_structure:greeter_activation_off";
    public static final String MENU_GREETER_VOICE_ON = "@player_structure:greeter_voicing_on";
    public static final String MENU_GREETER_VOICE_OFF = "@player_structure:greeter_voicing_off";
    public static final String MENU_GREETER_ANIMATING_ON = "@player_structure:greeter_animating_on";
    public static final String MENU_GREETER_ANIMATING_OFF = "@player_structure:greeter_animating_off";
    public static final String MENU_GREETER_SOUND_ON = "@player_structure:greeter_sounds_on";
    public static final String MENU_GREETER_SOUND_OFF = "@player_structure:greeter_sounds_off";
    public static final String MENU_GREETER_MOOD_ON = "@player_structure:greeter_mood_on";
    public static final String MENU_GREETER_MOOD_OFF = "@player_structure:greeter_mood_off";
    public static final String MENU_GREETER_EFFECT_ON = "@player_structure:greeter_effects_on";
    public static final String MENU_GREETER_EFFECT_OFF = "@player_structure:greeter_effects_off";
    public static final String MENU_GREETER_STATEMENT_ON = "@player_structure:greeter_statement_on";
    public static final String MENU_GREETER_STATEMENT_OFF = "@player_structure:greeter_statement_off";
    public static final String MENU_COLOR = "@player_structure:greeter_color";
    public static final String MENU_GREETER_MODIFY_TRIGGER_VOL_RADIUS = "@player_structure:greeter_trigger_radius";
    public static final String MENU_GREETER_CONVERSE_OTHER_GREETER = "@player_structure:greeter_converse_other_greeter";
    public static final String MENU_GREETER_STOP_CONVERSE_OTHER_GREETER = "@player_structure:greeter_stop_converse_other_greeter";
    public static final String MENU_GREETER_STATEMENT_EDIT = "@player_structure:greeter_statement_edit";
    public static final String MENU_GREETER_SOUND_EDIT = "@player_structure:greeter_sound_edit";
    public static final String MENU_GREETER_MOOD_EDIT = "@player_structure:greeter_mood_edit";
    public static final String MENU_GREETER_ANIMATING_EDIT = "@player_structure:greeter_animating_edit";
    public static final String MENU_GREETER_VOICE_EDIT = "@player_structure:greeter_voice_edit";
    public static final String MENU_GREETER_EFFECT_EDIT = "@player_structure:greeter_effect_edit";
    public static final String ALERT_VOLUME_NAME = "vendorTriggerVolume";
    public static final String VOICE_OPTION = "voice";
    public static final String SOUND_OPTION = "sound";
    public static final String VOICE_STRING = "voice_string";
    public static final String SOUND_STRING = "sound_string";
    public static final String GREETER_INITIALIZE_SCRVAR = "greeter.oninitialize";
    public static final String GREETER_VOICING_ENABLED = "voice_off";
    public static final String GREETER_VOICING_DISABLED = "voice_on";
    public static final String GREETER_ANIMATING_ENABLED = "anims_off";
    public static final String GREETER_ANIMATING_DISABLED = "anims_on";
    public static final String GREETER_SOUNDS_ENABLED = "sounds_off";
    public static final String GREETER_SOUNDS_DISABLED = "sounds_on";
    public static final String GREETER_MOOD_ENABLED = "mood_off";
    public static final String GREETER_MOOD_DISABLED = "mood_on";
    public static final String GREETER_STATEMENT_ENABLED = "spatial_off";
    public static final String GREETER_STATEMENT_DISABLED = "spatial_on";
    public static final String GREETER_CHANGE_NAME = "name_change";
    public static final String GREETER_RANDOMIZED_GREETINGS_ON = "randomized_greetings_on";
    public static final String GREETER_RANDOMIZED_GREETINGS_OFF = "randomized_greetings_off";
    public static final String GREETER_COLOR_CHANGE = "color_change";
    public static final String GREETER_RANDOM = "random_greeting";
    public static final String GREETER_PREVIEW = "preview_all";
    public static final String GREETER_SAVE_GREET = "save_greet";
    public static final String GREETER_LIST_GREET = "list_greet";
    public static final String GREETER_MODIFY_DELAY = "modify_delay";
    public static final String GREETER_MODIFY_TRIGGER_VOL_RADIUS = "modify_trigger";
    public static final String GREETER_GET_OTHER_GREETER = "get_other_greeter";
    public static final String GREETER_START_ITERACTION = "greeter_interact";
    public static final String GREETER_STOP_ITERACTION = "greeter_stop_interact";
    public static final String LAST_OTHER_GREETER = "last_other_greeter";
    public static final String GREETER_STATEMENT_EDIT = "edit_current_statement";
    public static final String GREETER_MOOD_EDIT = "edit_current_mood";
    public static final String GREETER_SOUNDS_EDIT = "edit_current_sounds";
    public static final String GREETER_VOICING_EDIT = "edit_current_voicing";
    public static final String GREETER_ANIMATING_EDIT = "edit_current_animating";
    public static final String SAVE_SAY_CHAT = "say_chat=";
    public static final String SAVE_VOICE = "voice=";
    public static final String SAVE_MOOD = "mood=";
    public static final String SAVE_SOUND = "sound=";
    public static final String SAVE_EFFECT = "effect=";
    public static final String SAVE_ANIM = "say_anim=";
    public static final String COMMA = ",";
    public static final String PIPE = "|";
    public static final int GREETER_BARK_TIMER_MAX = 60;
    public static final int GREETER_BARK_TIMER_MIN = 5;
    public static final int GREETER_COLOR_MAX = 4;
    public static final int GREETER_TRIGGER_VOL_MAX = 15;
    public static final int GREETER_TRIGGER_VOL_DEFAULT = 4;
    public static final int GREETER_TRIGGER_VOL_MIN = 1;
    public static final int GREETER_SAVE_MAX = 10;
    public static final int GREETER_TIMER_DELAY = 60;
    public static final int GREETER_CONVERSATION_LOOP_TIME = 3;
    public static final int GREETER_CONVERSATION_PAUSE_TIME = 10;
    public static final int GREETER_DELAY_AT_ONINITIALIZE = 3;
    public static final float MAX_GREETER_CONV_RANGE_FLOAT = 2.0f;
    public static final String CATEGORY_ANIM_COL_NAMES_SCRVAR = vendor_lib.GREETER_VAR_PREFIX + ".greeterAnimCategories";
    public static final String CATEGORY_ANIM_COL_STRINGS_SCRVAR = vendor_lib.GREETER_VAR_PREFIX + ".greeterAnimCategoriesStrings";
    public static final String SELECTED_CAT_ANIM_SCRVAR = vendor_lib.GREETER_VAR_PREFIX + ".greeterAnimCategorySelected";
    public static final String SELECTED_CAT_ANIM_STR_SCRVAR = vendor_lib.GREETER_VAR_PREFIX + ".greeterAnimCategorySelectedStrings";
    public static final String CATEGORY_MOOD_COL_NAMES_SCRVAR = vendor_lib.GREETER_VAR_PREFIX + ".greeterMoodCategories";
    public static final String CATEGORY_MOOD_COL_STRINGS_SCRVAR = vendor_lib.GREETER_VAR_PREFIX + ".greeterMoodCategoriesStrings";
    public static final String SELECTED_CAT_MOOD_SCRVAR = vendor_lib.GREETER_VAR_PREFIX + ".greeterMoodCategorySelected";
    public static final String SELECTED_CAT_MOOD_STR_SCRVAR = vendor_lib.GREETER_VAR_PREFIX + ".greeterMoodCategoryStringSelected";
    public static final String PLAYER_SEL_GREETER_EFFECT_LIST = vendor_lib.GREETER_VAR_PREFIX + ".greeterEffectListSelected";
    public static final String PLAYER_SEL_GREETER_EFFECT_TYPE = vendor_lib.GREETER_VAR_PREFIX + ".greeterEffectTypeSelected";
    public static final String PLAYER_SEL_GREETER_STATEMENT_LIST = vendor_lib.GREETER_VAR_PREFIX + ".greeterStatementSelected";
    public static final String PLAYER_SEL_GREETER_SAVE_ELEMENT = vendor_lib.GREETER_VAR_PREFIX + ".greeterSaveElementSelected";
    public static final String PLAYER_SEL_GREETER_SOUND_VO_STRING_LIST = vendor_lib.GREETER_VAR_PREFIX + ".greeterSoundVOStringList";
    public static final String PLAYER_SEL_GREETER_SOUND_VO_SELECTION = vendor_lib.GREETER_VAR_PREFIX + ".greeterSoundVOStringSelected";
    public static final String GREETER_EFFECT_PID = "greeter_pid.effect_pid";
    public static final String GREETER_MOOD_PID = "greeter_pid.mood_pid";
    public static final String GREETER_ANIM_PID = "greeter_pid.anim_pid";
    public static final String GREETER_MOOD_CATEGORY_PID = "greeter_pid.mood_cat_pid";
    public static final String GREETER_ANIM_CATEGORY_PID = "greeter_pid.anim_cat_pid";
    public static final String GREETER_STATEMENT_PID = "greeter_pid.statement_pid";
    public static final String GREETER_ANIM_PARENT_PID = "greeter_pid.anim_parent_pid";
    public static final String GREETER_PROGRAM_MAIN_PID = "greeter_pid.anim_main_menu_pid";
    public static final String GREETER_BARK_PID = "greeter_pid.bark_pid";
    public static final String GREETER_NAME_PID = "greeter_pid.name_pid";
    public static final String GREETER_COLOR_PID = "greeter_pid.color_pid";
    public static final String GREETER_RADIUS_PID = "greeter_pid.radius_pid";
    public static final String GREETER_LOAD_GREETING_PID = "greeter_pid.load_greeting_pid";
    public static final String GREETER_SAVE_NAME_PID = "greeter_pid.save_greeting_pid";
    public static final String GREETER_GREETING_SELECT_PID = "greeter_pid.greeting_select_pid";
    public static final String GREETER_GET_SAVED_GREETING_LIST_PID = "greeter_pid.get_saved_greeting_list_pid";
    public static final String GREETER_GET_RANDOM_PID = "greeter_pid.get_random_pid";
    public static final String GREETER_MAIN_MENU_STRINGS = "greeter_data.greeter_menu_strings";
    public static final String GREETER_MAIN_MENU_DATA = "greeter_data.greeter_menu_data";
    public static final String GREETER_COLOR_SECONDARY = "controller.color.secondary";
    public static final String GREETER_COLOR_PRIMARY = "controller.color.primary";
    public static final String GREETER_DELAY_TIMER_CURRENT = "controller.delay_current";
    public static final String GREETER_TRIGGER_VOL_CURRENT = "controller.trigger_current";
    public static final String GREETER_SAVE_DATA = "controller.save_data";
    public static final String GREETER_SAVE_GREETING = "controller.save_greeting";
    public static final String GREETER_SAVED_GREET_DATA = "controller.saved_data";
    public static final String GREETER_SAVED_GREET_NAMES = "controller.saved_names";
    public static final String FOUND_OTHER_GREETERS = "controller.other_greeter_found";
    public static final String FOUND_OTHER_GREETER_TIMER = "controller.other_greeter_search_timer";
    public static final String[] PLAYER_GREETING_MAINT_MENU = 
    {
        "Load",
        "Delete"
    };
    public static final String[] HUMANOID_ANIMATIONS = 
    {
        "cough_polite",
        "point_to_self",
        "nod_head_once",
        "clap_rousing",
        "laugh_cackle",
        "nod_head_multiple",
        "rub_chin_thoughtful",
        "shake_head_no",
        "check_wrist_device",
        "hair_flip",
        "taunt1",
        "taunt2",
        "shake_head_disgust",
        "wave_finger_warning",
        "wave_on_dismissing",
        "embarrassed",
        "point_away",
        "point_down",
        "point_forward",
        "point_left",
        "point_right",
        "point_up",
        "shrug_hands",
        "shrug_shoulders",
        "scratch_head",
        "conversation_1",
        "conversation_2",
        "conversation_1",
        "conversation_2",
        "conversation_1",
        "conversation_2",
        "conversation_1",
        "conversation_2"
    };
    public static final String[] JAWA_ANIMATIONS = 
    {
        "cough_polite",
        "alert",
        "angry",
        "conversation_1",
        "conversation_2",
        "laugh_cackle",
        "nervous",
        "point_forward",
        "shake_head_no",
        "shrug_hands",
        "sp_01",
        "sp_02",
        "threaten",
        "yes"
    };
    public static final String[] EWOK_ANIMATIONS = 
    {
        "cough_polite",
        "alert",
        "angry",
        "conversation_1",
        "explain",
        "greet",
        "laugh_cackle",
        "nervous",
        "point_forward",
        "ewok_pound_fist_chest",
        "shake_head_no",
        "shrug_shoulders",
        "smell_air",
        "ewok_sp_01",
        "threaten",
        "threaten_combat",
        "vocalize",
        "yes"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        blog("vendor.greeter.OnAttach init");
        removeTriggerVolume("alertTriggerVolume");
        setInvulnerable(self, true);
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controller, vendor_lib.GREETER_COLOR_OBJVAR) && getBooleanObjVar(controller, vendor_lib.GREETER_COLOR_OBJVAR) == true)
        {
            messageTo(self, "colorGreeter", null, 0, false);
        }
        if (hasObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR))
        {
            utils.setScriptVar(self, GREETER_INITIALIZE_SCRVAR, true);
            blog("vendor.greeter.OnAttach Setting up Trigger Volume");
            messageTo(self, "handleCallVolumeToggleOnInitialized", null, GREETER_DELAY_AT_ONINITIALIZE, false);
        }
        conversableGreeterInRange(self);
        if (!hasObjVar(controller, FOUND_OTHER_GREETERS) || getBooleanObjVar(controller, FOUND_OTHER_GREETERS) != true)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controller, vendor_lib.GREETER_CONVERSE) && getBooleanObjVar(controller, vendor_lib.GREETER_CONVERSE) == true)
        {
            messageTo(self, "handleConverseWithOtherGreeter", null, GREETER_CONVERSATION_LOOP_TIME, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        blog("vendor.greeter.OnInitialize init");
        if (isObjectPersisted(self))
        {
            blog("vendor.greeter.OnInitialize Greeter is persisted");
            messageTo(self, "handleCallGreeterUpdate", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        blog("vendor.greeter.OnInitialize set invuln");
        setInvulnerable(self, true);
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        blog("vendor.greeter.OnInitialize has controller ID");
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controller, vendor_lib.GREETER_COLOR_OBJVAR) && getBooleanObjVar(controller, vendor_lib.GREETER_COLOR_OBJVAR) == true)
        {
            messageTo(self, "colorGreeter", null, 0, false);
        }
        blog("vendor.greeter.OnInitialize controller ID received");
        if (hasObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR))
        {
            blog("vendor.greeter.OnInitialize Greeter is already active");
            utils.setScriptVar(self, GREETER_INITIALIZE_SCRVAR, true);
            blog("vendor.greeter.OnInitialize Setting up Trigger Volume");
            messageTo(self, "handleCallVolumeToggleOnInitialized", null, GREETER_DELAY_AT_ONINITIALIZE, false);
        }
        blog("vendor.greeter.OnInitialize Trigger Done checking controller");
        conversableGreeterInRange(self);
        if (!hasObjVar(controller, FOUND_OTHER_GREETERS) || getBooleanObjVar(controller, FOUND_OTHER_GREETERS) != true)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controller, vendor_lib.GREETER_CONVERSE) && getBooleanObjVar(controller, vendor_lib.GREETER_CONVERSE) == true)
        {
            messageTo(self, "handleConverseWithOtherGreeter", null, GREETER_CONVERSATION_LOOP_TIME, false);
        }
        return super.OnInitialize(self);
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("vendor.greeter.OnObjectMenuRequest init");
        obj_id ownerId = getOwner(self);
        if (!isValidId(ownerId) || !exists(ownerId))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        blog("vendor.greeter.OnObjectMenuRequest Owner received");
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isValidId(pInv) || !exists(pInv))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        if (player != ownerId)
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        blog("vendor.greeter.OnObjectMenuRequest validation complete, controller ID received");
        if (contains(pInv, self))
        {
            messageTo(self, "handleDestroyGreeter", null, 0, false);
            return super.OnObjectMenuRequest(self, player, mi);
        }
        conversableGreeterInRange(self);
        blog("vendor.greeter.OnObjectMenuRequest Creating Menu");
        int menu = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_GREETER_CONTROL);
        if (hasObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR))
        {
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU3, SID_GREETER_ACTIVE_ON);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, SID_CUSTOMIZE_GREETER);
        }
        else 
        {
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU3, SID_GREETER_ACTIVE_OFF);
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_REMOVE_GREETER);
        blog("vendor.greeter.OnObjectMenuRequest Menu created");
        return super.OnObjectMenuRequest(self, player, mi);
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        blog("vendor.greeter.OnObjectMenuSelect init");
        obj_id ownerId = getOwner(self);
        if (!isValidId(ownerId) || !exists(ownerId))
        {
            return SCRIPT_CONTINUE;
        }
        else if (player != ownerId)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("vendor.greeter.OnObjectMenuSelect initial validation done, including the controller ID");
        obj_id pInv = utils.getInventoryContainer(ownerId);
        if (!isValidId(pInv) || !exists(pInv))
        {
            return SCRIPT_CONTINUE;
        }
        blog("vendor.greeter.OnObjectMenuSelect owner inv. id recieved");
        sendDirtyObjectMenuNotification(self);
        blog("vendor.greeter.OnObjectMenuSelect item: " + item);
        blog("vendor.greeter.OnObjectMenuSelect looking for: " + menu_info_types.SERVER_MENU4);
        if (item == menu_info_types.SERVER_MENU1)
        {
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.ITEM_DESTROY && contains(pInv, self))
        {
            blog("terminal.greeter.OnObjectMenuSelect: player selected greeter destroy option while in player inventory.");
            messageTo(self, "handleDestroyGreeter", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            blog("terminal.greeter.OnObjectMenuSelect: player selected greeter destroy option after greeter placed.");
            messageTo(self, "handleDestroyGreeter", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            blog("terminal.greeter.OnObjectMenuSelect: player selected item == menu_info_types.SERVER_MENU3.");
            blog("terminal.greeter.OnObjectMenuSelect: player has: vendor_lib.GREETER_ACTIVE_OBJVAR");
            if (hasObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR))
            {
                blog("terminal.greeter.OnObjectMenuSelect: changing to: DEACTIVATE");
                removeObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR);
                toggleTriggerVolume(self);
                sendSystemMessage(player, SID_ACTIVATION_DISABLED);
            }
            else 
            {
                setObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR, true);
                toggleTriggerVolume(self);
                sendSystemMessage(player, SID_ACTIVATION_ENABLED);
            }
        }
        else if (item == menu_info_types.SERVER_MENU4)
        {
            blog("terminal.greeter.OnObjectMenuSelect: SERVER_MENU4");
            buildMainControlMenu(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id dest, obj_id transferer) throws InterruptedException
    {
        blog("vendor.greeter.OnAboutToBeTransferred init");
        obj_id ownerId = getOwner(self);
        if (!isValidId(ownerId) || !exists(ownerId))
        {
            messageTo(self, "handleDestroyGreeter", null, 0, false);
            return SCRIPT_OVERRIDE;
        }
        else if (transferer != ownerId)
        {
            sendSystemMessage(transferer, SID_CANT_MOVE);
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(self, vendor_lib.GREETER_INIT_OBJVAR))
        {
            sendSystemMessage(transferer, SID_CANT_MOVE);
            return SCRIPT_OVERRIDE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pInv = utils.getInventoryContainer(ownerId);
        if (dest == ownerId || dest == pInv)
        {
            blog("vendor.greeter.OnAboutToBeTransferred Somehow the mob object is being transferred");
            toggleTriggerVolume(self);
            vendor_lib.removeObjectFromController(controller, self);
            return SCRIPT_CONTINUE;
        }
        else if (getContainedBy(transferer) == dest)
        {
            obj_id structure = getTopMostContainer(dest);
            int got = getGameObjectType(structure);
            if (isGameObjectTypeOf(got, GOT_building) && !permissionsIsPublic(structure))
            {
                sendSystemMessage(transferer, SID_GREETER_PUBLIC_ONLY);
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        String msg = "Greeters may only reside in the owner's inventory or in structures the owner has vendor permissions in.";
        String title = "WARNING";
        sui.msgbox(ownerId, ownerId, msg, sui.OK_ONLY, title, "noHandler");
        return SCRIPT_OVERRIDE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        blog("vendor.greeter.OnTriggerVolumeEntered init");
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        blog("vendor.greeter.OnTriggerVolumeEntered hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR)");
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("vendor.greeter.OnTriggerVolumeEntered controller valid");
        if (hasObjVar(controller, vendor_lib.GREETER_CONVERSE) && getBooleanObjVar(controller, vendor_lib.GREETER_CONVERSE) == true)
        {
            blog("vendor.greeter.OnTriggerVolumeEntered: GREETER_CONVERSE");
            removeObjVar(controller, vendor_lib.GREETER_CONVERSE);
            blog("vendor.greeter.OnTriggerVolumeEntered: GREETER_CONVERSE removed");
            conversableGreeterInRange(self);
            if (!hasObjVar(controller, FOUND_OTHER_GREETERS) || getBooleanObjVar(controller, FOUND_OTHER_GREETERS) != true)
            {
                blog("vendor.greeter.OnTriggerVolumeEntered: no greeters in area");
                return SCRIPT_CONTINUE;
            }
            messageTo(self, "handleConverseWithOtherGreeterAfterGreetDelay", null, GREETER_CONVERSATION_PAUSE_TIME, false);
        }
        vendor_lib.colorizeGreeterFromController(controller, self);
        if (hasObjVar(breacher, "gm"))
        {
            blog("vendor.greeter.OnTriggerVolumeEntered: GM tripped");
            return SCRIPT_CONTINUE;
        }
        else if (breacher == self)
        {
            return SCRIPT_CONTINUE;
        }
        else if (isIncapacitated(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!isMob(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        else if (ai_lib.isMonster(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!hasObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        else if (utils.hasScriptVar(self, vendor_lib.GREETER_ALREADY_BARKED_SCRVAR))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!volumeName.equals(ALERT_VOLUME_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        blog("vendor.greeter.OnTriggerVolumeEntered: TRIGGER PASSED THE TESTS ----GIVE THE GREETER LIFE");
        if (!bringGreeterToLife(self, controller, breacher))
        {
            blog("vendor.greeter.OnTriggerVolumeEntered: bringGreeterToLife failed somehow.");
        }
        else 
        {
            blog("vendor.greeter.OnTriggerVolumeEntered: bringGreeterToLife shows success.");
        }
        if (hasObjVar(controller, vendor_lib.GREETER_CURRENTLY_RANDOMIZED_GREET) && getBooleanObjVar(controller, vendor_lib.GREETER_CURRENTLY_RANDOMIZED_GREET) == true)
        {
            blog("terminal.greeter:handlePlayerProgramSelect: I AM A RANDOMIZED GREETER!!!!!!!!!!!!!!!!!!!!!!!!!!!!.");
            if (!getRandomGreeting(self, controller))
            {
                blog("terminal.greeter:handlePlayerProgramSelect: getRandomGreeting failed.");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedMainControlMenu(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handleDelayedMainControlMenu: init");
        obj_id ownerId = getOwner(self);
        if (!isValidId(ownerId) || !exists(ownerId))
        {
            return SCRIPT_CONTINUE;
        }
        buildMainControlMenu(self, ownerId);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerProgramSelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerProgramSelect: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerProgramSelect: prelim validation completed.");
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        String[] availableOptionStrings = utils.getStringArrayScriptVar(player, GREETER_MAIN_MENU_DATA);
        if (availableOptionStrings == null)
        {
            return SCRIPT_CONTINUE;
        }
        String choice = availableOptionStrings[idx];
        if (choice == null || choice.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerProgramSelect: choice: " + choice);
        if (choice.startsWith(GREETER_CHANGE_NAME))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_CHANGE_NAME");
            if (sui.hasPid(player, GREETER_NAME_PID))
            {
                int pid = sui.getPid(player, GREETER_NAME_PID);
                forceCloseSUIPage(pid);
            }
            int pid = sui.inputbox(self, player, "@player_structure:greeter_name_d", sui.OK_CANCEL, "@player_structure:greeter_name_t", sui.INPUT_NORMAL, null, "handleSetVendorName", null);
            sui.setPid(player, pid, GREETER_NAME_PID);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_RANDOMIZED_GREETINGS_ON))
        {
            setObjVar(controller, vendor_lib.GREETER_CURRENTLY_RANDOMIZED_GREET, true);
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_RANDOMIZED_GREETINGS_ON");
            if (!getRandomGreeting(self, controller))
            {
                blog("terminal.greeter:handlePlayerProgramSelect: getRandomGreeting failed.");
                buildMainControlMenu(self, player);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, SID_RANDOMIZED_GREETINGS_ON);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_RANDOMIZED_GREETINGS_OFF))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_RANDOMIZED_GREETINGS_OFF");
            removeObjVar(controller, vendor_lib.GREETER_CURRENTLY_RANDOMIZED_GREET);
            sendSystemMessage(player, SID_RANDOMIZED_GREETINGS_OFF);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_ANIMATING_DISABLED) || choice.startsWith(GREETER_ANIMATING_EDIT))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_ANIMATING_DISABLED");
            if (!getGreeterAnimCategorySelect(self, controller, player))
            {
                CustomerServiceLog("vendor", "terminal.greeter:getGreeterAnimCategorySelect: player/Owner: " + getName(player) + " " + player + " Could not turn animations on for Greeter: " + self);
            }
            if (choice.startsWith(GREETER_ANIMATING_DISABLED))
            {
                sendSystemMessage(player, SID_ANIMATING_ENABLED);
            }
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_ANIMATING_ENABLED))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_ANIMATING_ENABLED");
            removeObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR);
            sendSystemMessage(player, SID_ANIMATING_DISABLED);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_VOICING_DISABLED) || choice.startsWith(GREETER_VOICING_EDIT))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_VOICING_DISABLED");
            if (!getGreeterSoundVoiceList(self, controller, player, VOICE_OPTION))
            {
                CustomerServiceLog("vendor", "terminal.greeter:getGreeterSoundVoiceList: player/Owner: " + getName(player) + " " + player + " Could not turn on " + VOICE_OPTION + " for Greeter: " + self);
            }
            if (choice.startsWith(GREETER_VOICING_DISABLED))
            {
                sendSystemMessage(player, SID_VOICING_ENABLED);
            }
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_VOICING_ENABLED))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_VOICING_ENABLED");
            removeObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR);
            sendSystemMessage(player, SID_VOICING_DISABLED);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_SOUNDS_DISABLED) || choice.startsWith(GREETER_SOUNDS_EDIT))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_SOUNDS_DISABLED");
            if (!getGreeterSoundVoiceList(self, controller, player, SOUND_OPTION))
            {
                CustomerServiceLog("vendor", "terminal.greeter:getGreeterSoundVoiceList: player/Owner: " + getName(player) + " " + player + " Could not turn on " + SOUND_OPTION + " for Greeter: " + self);
            }
            if (choice.startsWith(GREETER_SOUNDS_DISABLED))
            {
                sendSystemMessage(player, SID_SOUNDS_ENABLED);
            }
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_SOUNDS_ENABLED))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_SOUNDS_ENABLED");
            removeObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR);
            sendSystemMessage(player, SID_SOUNDS_DISABLED);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_MOOD_DISABLED) || choice.startsWith(GREETER_MOOD_EDIT))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_MOOD_DISABLED");
            if (!getGreeterMoodCategorySelect(self, controller, player))
            {
                CustomerServiceLog("vendor", "terminal.greeter:getGreeterMoodCategorySelect: player/Owner: " + getName(player) + " " + player + " Could not turn moods on for Greeter: " + self);
            }
            if (choice.startsWith(GREETER_MOOD_DISABLED))
            {
                sendSystemMessage(player, SID_MOOD_ENABLED);
            }
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_MOOD_ENABLED))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_MOOD_ENABLED");
            removeObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR);
            sendSystemMessage(player, SID_MOOD_DISABLED);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_STATEMENT_DISABLED) || choice.startsWith(GREETER_STATEMENT_EDIT))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_STATEMENT_DISABLED");
            if (!getGreeterStatementSelection(self, controller, player))
            {
                CustomerServiceLog("vendor", "terminal.greeter:getGreeterMoodCategorySelect: player/Owner: " + getName(player) + " " + player + " Could not turn moods on for Greeter: " + self);
            }
            if (choice.startsWith(GREETER_STATEMENT_DISABLED))
            {
                sendSystemMessage(player, SID_STATEMENT_ENABLED);
            }
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_STATEMENT_ENABLED))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_STATEMENT_ENABLED");
            blog("HAS HAS HAS vendor_lib.GREETER_HAS_STATEMENT_OBJVAR");
            removeObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR);
            sendSystemMessage(player, SID_STATEMENT_DISABLED);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_PREVIEW))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_PREVIEW");
            bringGreeterToLife(self, controller, player);
            messageTo(self, "handleDelayedMainControlMenu", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_MODIFY_DELAY))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_MODIFY_DELAY");
            if (sui.hasPid(player, GREETER_BARK_PID))
            {
                int pid = sui.getPid(player, GREETER_BARK_PID);
                forceCloseSUIPage(pid);
            }
            int delay = GREETER_BARK_TIMER_MAX;
            if (hasObjVar(controller, GREETER_DELAY_TIMER_CURRENT) && getIntObjVar(controller, GREETER_DELAY_TIMER_CURRENT) >= GREETER_BARK_TIMER_MIN)
            {
                delay = getIntObjVar(controller, GREETER_DELAY_TIMER_CURRENT);
            }
            int pid = sui.transfer(self, player, GREETER_DELAY_DESCRIPTION, GREETER_DELAY_TITLE, "Maximum", (GREETER_BARK_TIMER_MAX - delay), "Current", delay, "changeGreeterDelay");
            sui.showSUIPage(pid);
            sui.setPid(player, pid, GREETER_BARK_PID);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_COLOR_CHANGE))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_COLOR_CHANGE");
            ranged_int_custom_var[] palColors = hue.getPalcolorVars(self);
            if ((palColors == null) || (palColors.length == 0))
            {
                return SCRIPT_CONTINUE;
            }
            int palColorsLength = palColors.length;
            blog("handlePlayerProgramSelect - palColors.length: " + palColorsLength);
            String[] indexName = new String[GREETER_COLOR_MAX];
            blog("handlePlayerProgramSelect - indexName: " + indexName.length);
            int loop = GREETER_COLOR_MAX;
            if (palColorsLength < GREETER_COLOR_MAX)
            {
                for (int i = 0; i < GREETER_COLOR_MAX; i++)
                {
                    indexName[i] = "";
                }
                loop = palColorsLength;
            }
            for (int i = 0; i < loop; i++)
            {
                ranged_int_custom_var ri = palColors[i];
                if (ri != null)
                {
                    blog("handlePlayerProgramSelect - indexName[" + i + "]: " + indexName[i]);
                    String customizationVar = ri.getVarName();
                    if (customizationVar.startsWith("/"))
                    {
                        customizationVar = customizationVar.substring(1);
                    }
                    indexName[i] = customizationVar;
                }
            }
            if (indexName[0].equals(""))
            {
                CustomerServiceLog("vendor", "Coloring UI for Greeter: " + self + " by player " + player + " has failed because the 1st color was null.");
                return SCRIPT_CONTINUE;
            }
            CustomerServiceLog("vendor", "Coloring UI for greeter: " + self + " by player " + player);
            blog("handlePlayerProgramSelect - Greeter getting color UI: " + self);
            utils.setScriptVar(player, vendor_lib.GREETER_PLAYTERCOLOR_SCRVAR, true);
            openCustomizationWindow(player, self, indexName[0], -1, -1, indexName[1], -1, -1, indexName[2], -1, -1, indexName[3], -1, -1);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_MODIFY_TRIGGER_VOL_RADIUS))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_MODIFY_TRIGGER_VOL_RADIUS");
            if (sui.hasPid(player, GREETER_RADIUS_PID))
            {
                int pid = sui.getPid(player, GREETER_RADIUS_PID);
                forceCloseSUIPage(pid);
            }
            int radius = GREETER_TRIGGER_VOL_DEFAULT;
            if (hasObjVar(controller, GREETER_TRIGGER_VOL_CURRENT) && getIntObjVar(controller, GREETER_TRIGGER_VOL_CURRENT) >= GREETER_TRIGGER_VOL_MIN)
            {
                radius = getIntObjVar(controller, GREETER_TRIGGER_VOL_CURRENT);
            }
            int pid = sui.transfer(self, player, GREETER_RADIUS_DESCRIPTION, GREETER_RADIUS_TITLE, "Maximum", (GREETER_TRIGGER_VOL_MAX - radius), "Current", radius, "changeTriggerRadius");
            sui.showSUIPage(pid);
            sui.setPid(player, pid, GREETER_RADIUS_PID);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_SAVE_GREET))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_SAVE_GREET");
            if (sui.hasPid(player, GREETER_SAVE_NAME_PID))
            {
                int pid = sui.getPid(player, GREETER_SAVE_NAME_PID);
                forceCloseSUIPage(pid);
            }
            int pid = sui.inputbox(self, player, SUI_GREETER_SAVE_NAME_DESCR, sui.OK_CANCEL, SUI_GREETER_SAVE_NAME_TITLE, sui.INPUT_NORMAL, null, "handleSetSaveName", null);
            sui.setPid(player, pid, GREETER_SAVE_NAME_PID);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_LIST_GREET))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_LIST_GREET");
            if (sui.hasPid(player, GREETER_GET_SAVED_GREETING_LIST_PID))
            {
                int pid = sui.getPid(player, GREETER_GET_SAVED_GREETING_LIST_PID);
                forceCloseSUIPage(pid);
            }
            if (!getSavedGreetings(self, player, controller))
            {
                blog("terminal.greeter:handlePlayerProgramSelect: getSavedGreetings failed.");
                buildMainControlMenu(self, player);
                return SCRIPT_CONTINUE;
            }
            String[] menu = utils.getStringArrayScriptVar(player, GREETER_SAVED_GREET_NAMES);
            if (menu == null)
            {
                blog("terminal.greeter:handlePlayerProgramSelect: GREETER_SAVED_GREET_NAMES not found.");
                buildMainControlMenu(self, player);
                return SCRIPT_CONTINUE;
            }
            int pid = sui.listbox(self, player, SUI_GREETER_LOAD_DESCR, sui.OK_CANCEL, SUI_GREETER_LOAD_TITLE, menu, "handlePlayerGreetingSelect", true);
            sui.setPid(player, pid, GREETER_GET_SAVED_GREETING_LIST_PID);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_RANDOM))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_RANDOM");
            setObjVar(controller, vendor_lib.GREETER_RANDOM_TEST_FIRE, true);
            if (!getRandomGreeting(self, controller))
            {
                blog("terminal.greeter:handlePlayerProgramSelect: getRandomGreeting failed.");
                buildMainControlMenu(self, player);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, SID_GREETER_RANDOM_GREETING_SELECTED);
            bringGreeterToLife(self, controller, player);
            messageTo(self, "handleDelayedMainControlMenu", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        if (choice.startsWith(GREETER_START_ITERACTION))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_START_ITERACTION");
            setObjVar(controller, vendor_lib.GREETER_CONVERSE, true);
            messageTo(self, "handleConverseWithOtherGreeter", null, 0, false);
        }
        if (choice.startsWith(GREETER_STOP_ITERACTION))
        {
            blog("terminal.greeter:handlePlayerProgramSelect: GREETER_STOP_ITERACTION");
            removeObjVar(controller, vendor_lib.GREETER_CONVERSE);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCallGreeterUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handleCallGreeterUpdate: init.");
        if (!vendor_lib.updateGreeterFunctionality(self))
        {
            blog("terminal.greeter:handleCallGreeterUpdate: Greeter FAILED UPDATE");
            CustomerServiceLog("vendor", "terminal.greeter:OnInitialize: GREETER UPDATE FAILED: " + getName(self) + " " + self + " " + getLocation(self));
        }
        else 
        {
            blog("terminal.greeter:handleCallGreeterUpdate: Greeter Updated!!!");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCallVolumeToggleOnInitialized(obj_id self, dictionary params) throws InterruptedException
    {
        toggleTriggerVolume(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCallVolumeToggleNoLife(obj_id self, dictionary params) throws InterruptedException
    {
        toggleTriggerVolume(self, false);
        return SCRIPT_CONTINUE;
    }
    public int colorGreeter(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        vendor_lib.colorizeGreeterFromController(controller, self);
        return SCRIPT_CONTINUE;
    }
    public int handleConverseWithOtherGreeterAfterGreetDelay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(controller, vendor_lib.GREETER_CONVERSE, true);
        messageTo(self, "handleConverseWithOtherGreeter", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleConverseWithOtherGreeter(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handleConverseWithOtherGreeter: init.");
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(controller, vendor_lib.GREETER_CONVERSE) || getBooleanObjVar(controller, vendor_lib.GREETER_CONVERSE) != true)
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handleConverseWithOtherGreeter: Controller id found.");
        obj_id ownerId = getObjIdObjVar(self, vendor_lib.GREETER_OWNER_OBJVAR);
        if (!isValidId(ownerId))
        {
            blog("terminal.greeter:handleConverseWithOtherGreeter: GREETER HAD NO OWNER! Aborting initialization process and deleting greeter.");
            ownerId = vendor_lib.getObjectOwner(controller);
            if (!isValidId(ownerId))
            {
                blog("terminal.greeter:handleConverseWithOtherGreeter: GREETER HAD NO OWNER! Aborting initialization process and deleting greeter.");
                CustomerServiceLog("vendor", "terminal.greeter:handleGreeterInitialize: GREETER HAD NO OWNER! Aborting initialization process and deleting Greeter: " + self);
                messageTo(self, "handleDestroyGreeter", null, 0, false);
                return SCRIPT_CONTINUE;
            }
        }
        blog("terminal.greeter:handleConverseWithOtherGreeter: Owner id found.  Checking for other greeter var.");
        obj_id otherGreeter = getConversableGreeter(self, ownerId, controller);
        if (!isValidId(otherGreeter) || !exists(otherGreeter))
        {
            blog("terminal.greeter:handleConverseWithOtherGreeter: No other greeter var. Removing");
            removeObjVar(controller, FOUND_OTHER_GREETERS);
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handleConverseWithOtherGreeter: FOUND other greeter: " + otherGreeter);
        faceTo(self, otherGreeter);
        String creatureType = getStringObjVar(controller, vendor_lib.CREATURE_TYPE);
        if (creatureType == null || creatureType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handleConverseWithOtherGreeter: FOUND creature Type of greeter: " + creatureType);
        if (creatureType.equals("ewok"))
        {
            String anim = EWOK_ANIMATIONS[rand(0, JAWA_ANIMATIONS.length - 1)];
            if (anim != null || !anim.equals(""))
            {
                doAnimationAction(self, anim);
            }
            messageTo(self, "handleConverseWithOtherGreeter", null, GREETER_CONVERSATION_LOOP_TIME, false);
            return SCRIPT_CONTINUE;
        }
        if (creatureType.equals("greeter_jawa"))
        {
            String anim = JAWA_ANIMATIONS[rand(0, JAWA_ANIMATIONS.length - 1)];
            if (anim != null || !anim.equals(""))
            {
                doAnimationAction(self, anim);
            }
            messageTo(self, "handleConverseWithOtherGreeter", null, GREETER_CONVERSATION_LOOP_TIME, false);
            return SCRIPT_CONTINUE;
        }
        String anim = HUMANOID_ANIMATIONS[rand(0, HUMANOID_ANIMATIONS.length - 1)];
        if (anim != null || !anim.equals(""))
        {
            doAnimationAction(self, anim);
        }
        messageTo(self, "handleConverseWithOtherGreeter", null, GREETER_CONVERSATION_LOOP_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSetSaveName(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handleSetSaveName: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        String saveName = sui.getInputBoxText(params);
        if (saveName == null || saveName.equals(""))
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (saveName == null || saveName.equals("") || isNameReserved(saveName))
        {
            blog("terminal.greeter:handleSetSaveName: handleSetSaveName");
            sendSystemMessage(player, SID_OBSCENE);
            int pid = sui.inputbox(self, player, SUI_GREETER_SAVE_NAME_DESCR, sui.OK_CANCEL, SUI_GREETER_SAVE_NAME_TITLE, sui.INPUT_NORMAL, null, "handleSetSaveName", null);
            sui.setPid(player, pid, GREETER_SAVE_NAME_PID);
            return SCRIPT_CONTINUE;
        }
        if (saveName.length() > 10)
        {
            saveName = saveName.substring(0, 9);
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (!saveGreeting(self, player, controller, saveName))
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerGreetingSelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerGreetingSelect: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.hasPid(player, GREETER_GREETING_SELECT_PID))
        {
            int pid = sui.getPid(player, GREETER_GREETING_SELECT_PID);
            forceCloseSUIPage(pid);
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreetingSelect: initial validation completed");
        int idx = sui.getListboxSelectedRow(params);
        blog("terminal.greeter:handlePlayerGreetingSelect: idx: " + idx);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        String[] loadList = getStringArrayObjVar(controller, GREETER_SAVE_DATA);
        if (loadList == null)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreetingSelect: loadList.length: " + loadList.length);
        String selection = loadList[idx];
        if (selection == null || selection.equals(""))
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreetingSelect: +++++++SELECTION: " + selection);
        utils.setScriptVar(player, PLAYER_SEL_GREETER_SAVE_ELEMENT, selection);
        int pid = sui.listbox(self, player, SUI_GREETER_LOAD_DESCR, sui.OK_CANCEL, SUI_GREETER_LOAD_TITLE, PLAYER_GREETING_MAINT_MENU, "handlePlayerGreetingMaintenanceSelect", true);
        sui.setPid(player, pid, GREETER_GET_SAVED_GREETING_LIST_PID);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerGreetingMaintenanceSelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: initial validation completed");
        int idx = sui.getListboxSelectedRow(params);
        blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: idx: " + idx);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
            blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: CASE 0");
            messageTo(self, "handlePlayerLoadGreetingSelect", params, 0, false);
            break;
            case 1:
            blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: CASE 1");
            String selection = utils.getStringScriptVar(player, PLAYER_SEL_GREETER_SAVE_ELEMENT);
            blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: -----------SELECTION: " + selection);
            if (selection == null || selection.equals(""))
            {
                blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: selection name failed: " + selection);
                buildMainControlMenu(self, player);
                return SCRIPT_CONTINUE;
            }
            String[] saveData = split(selection, '-');
            if (saveData == null)
            {
                blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: saveData failed: " + saveData.length);
                buildMainControlMenu(self, player);
                return SCRIPT_CONTINUE;
            }
            if (!deleteGreeting(self, player, controller, saveData[0]))
            {
                blog("terminal.greeter:handlePlayerGreetingMaintenanceSelect: deleteGreeting failed");
                buildMainControlMenu(self, player);
                return SCRIPT_CONTINUE;
            }
            break;
            default:
            messageTo(self, "handlePlayerLoadGreetingSelect", params, 0, false);
            break;
        }
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerLoadGreetingSelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerLoadGreetingSelect: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerLoadGreetingSelect: initial validation completed");
        int idx = sui.getListboxSelectedRow(params);
        blog("terminal.greeter:handlePlayerLoadGreetingSelect: idx: " + idx);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        String selection = utils.getStringScriptVar(player, PLAYER_SEL_GREETER_SAVE_ELEMENT);
        if (selection == null || selection.equals(""))
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerLoadGreetingSelect: selection: " + selection);
        if (!loadSavedData(self, player, controller, selection))
        {
            blog("terminal.greeter:handlePlayerLoadGreetingSelect: failed loadSavedData");
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, SID_GREETING_LOADED);
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int changeTriggerRadius(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:changeTriggerRadius: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        int radius = sui.getTransferInputTo(params);
        if (radius < GREETER_TRIGGER_VOL_MIN)
        {
            radius = 1;
            sendSystemMessage(player, SID_MIN_RADIUS);
        }
        if (radius > GREETER_TRIGGER_VOL_MAX)
        {
            radius = GREETER_TRIGGER_VOL_MAX;
            sendSystemMessage(player, SID_MAX_RADIUS);
        }
        setObjVar(controller, GREETER_TRIGGER_VOL_CURRENT, radius);
        sendSystemMessage(player, SID_GREETING_RADIUS_CHANGED);
        messageTo(self, "handleCallVolumeToggleNoLife", null, 1, false);
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int changeGreeterDelay(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:changeGreeterDelay: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        int delayAmt = sui.getTransferInputTo(params);
        if (delayAmt < GREETER_BARK_TIMER_MIN)
        {
            delayAmt = 5;
            sendSystemMessage(player, SID_MIN_FIVE_TIMER);
        }
        if (delayAmt > GREETER_BARK_TIMER_MAX)
        {
            delayAmt = GREETER_BARK_TIMER_MAX;
            sendSystemMessage(player, SID_MAX_TIMER);
        }
        setObjVar(controller, GREETER_DELAY_TIMER_CURRENT, delayAmt);
        sendSystemMessage(player, SID_GREETING_DELAY_CHANGED);
        messageTo(self, "handleCallVolumeToggleNoLife", null, 1, false);
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleDestroyGreeter(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handleDestroyGreeter: init");
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controller, FOUND_OTHER_GREETERS))
        {
            removeObjVar(controller, FOUND_OTHER_GREETERS);
        }
        vendor_lib.removeObjectFromController(controller, self);
        return SCRIPT_CONTINUE;
    }
    public int handleGreeterInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ownerId = getObjIdObjVar(self, vendor_lib.GREETER_OWNER_OBJVAR);
        if (!isValidId(ownerId) || !exists(ownerId))
        {
            blog("terminal.greeter:handleGreeterInitialize: GREETER HAD NO OWNER! Aborting initialization process and deleting greeter.");
            CustomerServiceLog("vendor", "terminal.greeter:handleGreeterInitialize: GREETER HAD NO OWNER! Aborting initialization process and deleting Greeter: " + self);
            messageTo(self, "handleDestroyGreeter", null, 0, false);
            return SCRIPT_OVERRIDE;
        }
        removeObjVar(controller, vendor_lib.GREETER_NOT_INIT_OBJVAR);
        setObjVar(controller, vendor_lib.GREETER_INIT_OBJVAR, true);
        blog("terminal.greeter:handleGreeterInitialize: GREETER INITIALIZED BY OWNER!");
        CustomerServiceLog("vendor", "terminal.greeter:handleGreeterInitialize: GREETER INITIALIZED BY OWNER: " + getName(ownerId) + " " + ownerId + " Greeter: " + self);
        return SCRIPT_CONTINUE;
    }
    public int handleSetVendorName(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String greeterName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (greeterName == null || greeterName.equals("") || isNameReserved(greeterName))
        {
            sendSystemMessage(player, SID_OBSCENE);
            sui.inputbox(self, player, "@player_structure:name_d", sui.OK_CANCEL, "@player_structure:name_t", sui.INPUT_NORMAL, null, "handleSetVendorName", null);
            return SCRIPT_CONTINUE;
        }
        if (greeterName.length() > 40)
        {
            greeterName = greeterName.substring(0, 39);
        }
        setName(self, "Greeter: " + greeterName);
        sendSystemMessage(player, SID_GREETER_WAS_RENAMED);
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerGreeterAnimCategorySelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerGreeterAnimCategorySelect: init.");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.hasPid(player, GREETER_ANIM_CATEGORY_PID))
        {
            int pid = sui.getPid(player, GREETER_ANIM_CATEGORY_PID);
            forceCloseSUIPage(pid);
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!utils.hasScriptVar(player, CATEGORY_ANIM_COL_NAMES_SCRVAR))
        {
            blog("terminal.greeter:handlePlayerGreeterAnimCategorySelect: Player somehow lost the anim category list given in previous handler.");
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterAnimCategorySelect: prevalidation completed.");
        String[] greeterAnimCategories = utils.getStringArrayScriptVar(player, CATEGORY_ANIM_COL_NAMES_SCRVAR);
        if (greeterAnimCategories == null)
        {
            return SCRIPT_CONTINUE;
        }
        String greeterAnimCategory = greeterAnimCategories[idx];
        if (greeterAnimCategory == null || greeterAnimCategory.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String[] categoryAnims = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_ANIMS, greeterAnimCategory);
        if (categoryAnims == null)
        {
            return SCRIPT_CONTINUE;
        }
        String[] categoryAnimStrings = new String[categoryAnims.length];
        for (int i = 0; i < categoryAnims.length; i++)
        {
            categoryAnimStrings[i] = "@player_structure:" + categoryAnims[i];
        }
        if (categoryAnimStrings == null)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(player, SELECTED_CAT_ANIM_SCRVAR, categoryAnims);
        utils.setScriptVar(player, SELECTED_CAT_ANIM_STR_SCRVAR, categoryAnimStrings);
        int pid = sui.listbox(self, player, "@player_structure:greeter_anim_category_d", sui.OK_CANCEL, "@player_structure:greeter_anim_category_t", categoryAnimStrings, "handlePlayerGreeterAnimSelect", true);
        sui.setPid(player, pid, GREETER_ANIM_CATEGORY_PID);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerGreeterAnimSelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerGreeterAnimSelect: init.");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!utils.hasScriptVar(player, SELECTED_CAT_ANIM_SCRVAR))
        {
            blog("terminal.greeter:handlePlayerGreeterAnimSelect: Player somehow lost the anim category selected in previous handler.");
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterAnimSelect: prevalidation completed.");
        String[] animCategoryList = utils.getStringArrayScriptVar(player, SELECTED_CAT_ANIM_SCRVAR);
        if (animCategoryList == null)
        {
            return SCRIPT_CONTINUE;
        }
        String greeterAnimSelected = animCategoryList[idx];
        if (greeterAnimSelected == null || greeterAnimSelected.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterAnimSelect: We have Anim. Getting Controller");
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterAnimSelect: We have Controller. Setting Anim");
        setObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION, greeterAnimSelected);
        setObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION_STRING, "@player_structure:" + greeterAnimSelected);
        setObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR, true);
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerGreeterMoodCategorySelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerGreeterMoodCategorySelect: init.");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.hasPid(player, GREETER_MOOD_CATEGORY_PID))
        {
            int pid = sui.getPid(player, GREETER_MOOD_CATEGORY_PID);
            forceCloseSUIPage(pid);
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!utils.hasScriptVar(player, CATEGORY_MOOD_COL_NAMES_SCRVAR) || !utils.hasScriptVar(player, CATEGORY_MOOD_COL_STRINGS_SCRVAR))
        {
            blog("terminal.greeter:handlePlayerGreeterMoodCategorySelect: Player somehow lost the mood category list given in previous handler.");
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterMoodCategorySelect: prevalidation completed.");
        String[] greeterMoodCategories = utils.getStringArrayScriptVar(player, CATEGORY_MOOD_COL_NAMES_SCRVAR);
        if (greeterMoodCategories == null)
        {
            return SCRIPT_CONTINUE;
        }
        String greeterMoodCategory = greeterMoodCategories[idx];
        if (greeterMoodCategory == null || greeterMoodCategory.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String[] categoryMoods = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_MOODS, greeterMoodCategory);
        if (categoryMoods == null)
        {
            return SCRIPT_CONTINUE;
        }
        String[] categoryMoodStrings = new String[categoryMoods.length];
        for (int i = 0; i < categoryMoods.length; i++)
        {
            categoryMoodStrings[i] = "@player_structure:" + categoryMoods[i];
        }
        utils.setScriptVar(player, SELECTED_CAT_MOOD_SCRVAR, categoryMoods);
        int pid = sui.listbox(self, player, "@player_structure:greeter_mood_category_d", sui.OK_CANCEL, "@player_structure:greeter_mood_category_t", categoryMoodStrings, "handlePlayerGreeterMoodSelect", true);
        sui.setPid(player, pid, GREETER_MOOD_CATEGORY_PID);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerGreeterMoodSelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerGreeterMoodSelect: init.");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!utils.hasScriptVar(player, SELECTED_CAT_MOOD_SCRVAR))
        {
            blog("terminal.greeter:handlePlayerGreeterMoodSelect: Player somehow lost the mood category selected in previous handler.");
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterMoodSelect: prevalidation completed.");
        String[] moodCategoryList = utils.getStringArrayScriptVar(player, SELECTED_CAT_MOOD_SCRVAR);
        if (moodCategoryList == null)
        {
            return SCRIPT_CONTINUE;
        }
        String greeterMoodSelected = moodCategoryList[idx];
        if (greeterMoodSelected == null || greeterMoodSelected.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterMoodSelect: We have Mood. Getting Controller");
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterMoodSelect: We have Controller. Setting Mood");
        setObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD, greeterMoodSelected);
        setObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD_STRING, "@player_structure:" + greeterMoodSelected);
        setObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR, true);
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerEffectSelection(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerEffectSelection: init.");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!utils.hasScriptVar(player, PLAYER_SEL_GREETER_EFFECT_LIST) || !utils.hasScriptVar(player, PLAYER_SEL_GREETER_EFFECT_TYPE))
        {
            blog("terminal.greeter:handlePlayerEffectSelection: Player somehow lost the effect or effect type selected in previous handler.");
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerEffectSelection: prevalidation completed.");
        String[] effectList = utils.getStringArrayScriptVar(player, PLAYER_SEL_GREETER_EFFECT_LIST);
        String[] menuList = utils.getStringArrayScriptVar(player, PLAYER_SEL_GREETER_SOUND_VO_STRING_LIST);
        String effectType = utils.getStringScriptVar(player, PLAYER_SEL_GREETER_EFFECT_TYPE);
        blog("greeter:handlePlayerEffectSelection: effectList: " + effectList);
        blog("greeter:handlePlayerEffectSelection: effectType: " + effectType);
        if (effectList == null)
        {
            return SCRIPT_CONTINUE;
        }
        else if (effectType == null || effectType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!effectType.equals(SOUND_OPTION) && !effectType.equals(VOICE_OPTION))
        {
            return SCRIPT_CONTINUE;
        }
        blog("greeter:handlePlayerEffectSelection: effectList and  effectType attained.");
        String effectSelected = effectList[idx];
        if (effectSelected == null || effectSelected.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        blog("greeter:handlePlayerEffectSelection: We have Effect. Getting Controller");
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("greeter:handlePlayerEffectSelection: We have Controller. Setting Effect");
        String menuStrSelected = menuList[idx];
        if (menuStrSelected == null || menuStrSelected.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR, menuStrSelected);
        if (effectType.equals(SOUND_OPTION))
        {
            blog("greeter:handlePlayerEffectSelection: Setting SOUND");
            setObjVar(controller, vendor_lib.GREETER_SOUNDS_OBJVAR, effectSelected);
            setObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR, true);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        else if (effectType.equals(VOICE_OPTION))
        {
            blog("greeter:handlePlayerEffectSelection: Setting VOICE");
            setObjVar(controller, vendor_lib.GREETER_VOICES_OBJVAR, effectSelected);
            setObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR, true);
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerEffectSelection: FAILED TO RECEIVE EFFECT.");
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerStatementSelection(obj_id self, dictionary params) throws InterruptedException
    {
        blog("terminal.greeter:handlePlayerStatementSelection: init.");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            buildMainControlMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!utils.hasScriptVar(player, PLAYER_SEL_GREETER_STATEMENT_LIST))
        {
            blog("terminal.greeter:handlePlayerStatementSelection: Player somehow lost the statement for greeter previous handler.");
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerStatementSelection: prelim validation completed.");
        String[] statementList = utils.getStringArrayScriptVar(player, PLAYER_SEL_GREETER_STATEMENT_LIST);
        if (statementList == null)
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerStatementSelection: statementList: " + statementList);
        String finalStatement = localize(new string_id("player_structure", statementList[idx]));
        if (finalStatement == null || finalStatement.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterAnimSelect: We have Statement. Getting Controller");
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("terminal.greeter:handlePlayerGreeterAnimSelect: We have Controller. Setting Statement");
        setObjVar(controller, vendor_lib.GREETER_ACTUAL_STATEMENT, finalStatement);
        setObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR, true);
        blog("terminal.greeter:handlePlayerStatementSelection: has GREETER_HAS_STATEMENT_OBJVAR: " + hasObjVar(self, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR));
        blog("terminal.greeter:handlePlayerStatementSelection: setObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR, true): " + getBooleanObjVar(self, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR));
        buildMainControlMenu(self, player);
        return SCRIPT_CONTINUE;
    }
    public int resetBarkTimer(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, vendor_lib.GREETER_ALREADY_BARKED_SCRVAR);
        return SCRIPT_CONTINUE;
    }
    public boolean getGreeterAnimCategorySelect(obj_id self, obj_id controller, obj_id player) throws InterruptedException
    {
        blog("terminal.greeter:getGreeterAnimCategorySelect: init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return false;
        }
        if (!hasObjVar(controller, vendor_lib.GREETER_HAS_ANIMS_OBJVAR))
        {
            blog("terminal.greeter:getGreeterAnimCategorySelect: Greeter somehow had animation radial option even though no animations are allowed.");
            return false;
        }
        if (sui.hasPid(player, GREETER_ANIM_PID))
        {
            int pid = sui.getPid(player, GREETER_ANIM_PID);
            forceCloseSUIPage(pid);
        }
        blog("terminal.greeter:getGreeterAnimCategorySelect: prevalidation completed.");
        String[] greeterAnimCategories = vendor_lib.getAllGreeterDatatableColumnNames(self, vendor_lib.TBL_GREETER_ANIMS);
        if (greeterAnimCategories == null)
        {
            return false;
        }
        blog("terminal.greeter:getGreeterAnimCategorySelect: Animations received.");
        int greeterAnimCategoriesLen = greeterAnimCategories.length;
        if (greeterAnimCategoriesLen < 0)
        {
            return false;
        }
        blog("terminal.greeter:getGreeterAnimCategorySelect: Animations length ok.");
        String creatureType = getStringObjVar(controller, vendor_lib.CREATURE_TYPE);
        if (creatureType == null || creatureType.equals(""))
        {
            return false;
        }
        blog("terminal.greeter:getGreeterAnimCategorySelect: creatureType: " + creatureType);
        String greeterType = "greeter_" + creatureType;
        blog("terminal.greeter:getGreeterAnimCategorySelect: greeterType: " + greeterType);
        if (!greeterType.equals("greeter_ewok") && !greeterType.equals("greeter_jawa") && !greeterType.equals("greeter_gamorrean") && !greeterType.equals("greeter_battle_droid") && !greeterType.equals("greeter_toydarian") && !greeterType.equals("greeter_pa_lowick"))
        {
            blog("terminal.greeter:getGreeterAnimCategorySelect: NO JAWA OR EWOK FOUND: " + greeterType);
            if (sui.hasPid(player, GREETER_ANIM_PARENT_PID))
            {
                int pid = sui.getPid(player, GREETER_ANIM_PARENT_PID);
                forceCloseSUIPage(pid);
            }
            Vector greeterAnimCategoriesRaw = new Vector();
            greeterAnimCategoriesRaw.setSize(0);
            Vector greeterAnimCategoriesStrings = new Vector();
            greeterAnimCategoriesStrings.setSize(0);
            for (String greeterAnimCategory : greeterAnimCategories) {
                if (greeterAnimCategory.startsWith("greeter_")) {
                    utils.addElement(greeterAnimCategoriesRaw, greeterAnimCategory);
                    utils.addElement(greeterAnimCategoriesStrings, "@player_structure:" + greeterAnimCategory);
                }
            }
            if (greeterAnimCategoriesRaw.isEmpty() || greeterAnimCategoriesStrings.isEmpty())
            {
                return false;
            }
            else if (greeterAnimCategoriesRaw.size() != greeterAnimCategoriesStrings.size())
            {
                return false;
            }
            String[] greeterAnimStringMenu = new String[greeterAnimCategoriesStrings.size()];
            String[] greeterAnimRawData = new String[greeterAnimCategoriesRaw.size()];
            greeterAnimCategoriesStrings.toArray(greeterAnimStringMenu);
            greeterAnimCategoriesRaw.toArray(greeterAnimRawData);
            utils.setScriptVar(player, CATEGORY_ANIM_COL_NAMES_SCRVAR, greeterAnimRawData);
            blog("terminal.greeter:getGreeterAnimCategorySelect: sending player sui.");
            int pid = sui.listbox(self, player, "@player_structure:greeter_anim_parent_d", sui.OK_CANCEL, "@player_structure:greeter_anim_parent_t", greeterAnimStringMenu, "handlePlayerGreeterAnimCategorySelect", true);
            sui.setPid(player, pid, GREETER_ANIM_PARENT_PID);
            return true;
        }
        blog("terminal.greeter:getGreeterAnimCategorySelect: EWOK OR JAWA FOUND: " + greeterType);
        String[] jawaOrEwokEmoteList = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_ANIMS, "emote_" + greeterType);
        if (jawaOrEwokEmoteList == null)
        {
            return false;
        }
        int listLength = jawaOrEwokEmoteList.length;
        if (listLength <= 0)
        {
            return false;
        }
        String[] jawaOrEwokEmoteStrings = new String[listLength];
        for (int i = 0; i < listLength; i++)
        {
            jawaOrEwokEmoteStrings[i] = "@player_structure:" + jawaOrEwokEmoteList[i];
        }
        if (jawaOrEwokEmoteStrings == null)
        {
            return false;
        }
        utils.setScriptVar(player, SELECTED_CAT_ANIM_SCRVAR, jawaOrEwokEmoteList);
        int pid = sui.listbox(self, player, "@player_structure:greeter_anim_category_d", sui.OK_CANCEL, "@player_structure:greeter_anim_category_t", jawaOrEwokEmoteStrings, "handlePlayerGreeterAnimSelect", true);
        sui.setPid(player, pid, GREETER_ANIM_PID);
        return true;
    }
    public boolean getGreeterMoodCategorySelect(obj_id self, obj_id controller, obj_id player) throws InterruptedException
    {
        blog("terminal.greeter:getGreeterMoodCategorySelect: init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return false;
        }
        if (!hasObjVar(controller, vendor_lib.GREETER_MOODS_OBJVAR))
        {
            blog("terminal.greeter:getGreeterMoodCategorySelect: Greeter somehow had animation radial option even though no animations are allowed.");
            return false;
        }
        if (sui.hasPid(player, GREETER_MOOD_PID))
        {
            int pid = sui.getPid(player, GREETER_MOOD_PID);
            forceCloseSUIPage(pid);
        }
        blog("terminal.greeter:getGreeterMoodCategorySelect: prevalidation completed.");
        String[] greeterMoodCategories = vendor_lib.getAllGreeterDatatableColumnNames(self, vendor_lib.TBL_GREETER_MOODS);
        if (greeterMoodCategories == null)
        {
            return false;
        }
        int greeterMoodCategoriesLen = greeterMoodCategories.length;
        if (greeterMoodCategoriesLen < 0)
        {
            return false;
        }
        String[] greeterMoodCategoriesStrings = new String[greeterMoodCategoriesLen];
        for (int i = 0; i < greeterMoodCategoriesLen; i++)
        {
            greeterMoodCategoriesStrings[i] = "@player_structure:" + greeterMoodCategories[i];
        }
        if (greeterMoodCategoriesStrings == null)
        {
            return false;
        }
        utils.setScriptVar(player, CATEGORY_MOOD_COL_NAMES_SCRVAR, greeterMoodCategories);
        utils.setScriptVar(player, CATEGORY_MOOD_COL_STRINGS_SCRVAR, greeterMoodCategoriesStrings);
        blog("terminal.greeter:getGreeterMoodCategorySelect: sending player sui.");
        int pid = sui.listbox(self, player, "@player_structure:greeter_mood_parent_d", sui.OK_CANCEL, "@player_structure:greeter_mood_parent_t", greeterMoodCategoriesStrings, "handlePlayerGreeterMoodCategorySelect", true);
        sui.setPid(player, pid, GREETER_MOOD_PID);
        return true;
    }
    public boolean getGreeterSoundVoiceList(obj_id self, obj_id controller, obj_id player, String effectType) throws InterruptedException
    {
        blog("terminal.greeter:getGreeterSoundVoiceList: init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        else if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        else if (effectType == null || effectType.equals(""))
        {
            return false;
        }
        else if (!validateAllImperativeGreeterVars(self, player))
        {
            return false;
        }
        else if (!effectType.equals(SOUND_OPTION) && !effectType.equals(VOICE_OPTION))
        {
            blog("terminal.greeter:getGreeterSoundVoiceList: Player somehow lost their effect type.");
            return false;
        }
        else if (effectType.equals(SOUND_OPTION) && !hasObjVar(controller, vendor_lib.GREETER_HAS_SOUND_OBJVAR))
        {
            blog("terminal.greeter:getGreeterSoundVoiceList: Player has " + SOUND_OPTION + " but the greeter doesn't allow for " + SOUND_OPTION);
            return false;
        }
        else if (effectType.equals(VOICE_OPTION) && !hasObjVar(controller, vendor_lib.GREETER_HAS_VO_OBJVAR))
        {
            blog("terminal.greeter:getGreeterSoundVoiceList: Player has " + VOICE_OPTION + " but the greeter doesn't allow for " + VOICE_OPTION);
            return false;
        }
        if (sui.hasPid(player, GREETER_EFFECT_PID))
        {
            int pid = sui.getPid(player, GREETER_EFFECT_PID);
            forceCloseSUIPage(pid);
        }
        blog("terminal.greeter:getGreeterSoundVoiceList: prevalidation completed.");
        String creatureType = getStringObjVar(controller, vendor_lib.CREATURE_TYPE);
        if (creatureType == null || creatureType.equals(""))
        {
            return false;
        }
        String greeterType = "greeter_" + creatureType;
        String greeterSoundVoColName = greeterType + "_" + effectType;
        String greeterSoundVoColNameString = greeterType + "_" + effectType + "_string";
        if (hasObjVar(self, "greeter.sound_vo") && hasObjVar(self, "greeter.sound_vo_string"))
        {
            String soundVo = getStringObjVar(self, "greeter.sound_vo");
            String soundVoString = getStringObjVar(self, "greeter.sound_vo_string");
            if (soundVo != null && !soundVo.equals("") && soundVoString != null && !soundVoString.equals(""))
            {
                greeterSoundVoColName = soundVo;
                greeterSoundVoColNameString = soundVoString;
            }
        }
        String[] effectList = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_SOUND_VOICE_EFFECT, greeterSoundVoColName);
        if (effectList == null)
        {
            blog("terminal.greeter:getGreeterSoundVoiceList: Could not find the following col in greeter_sound_voice_effect.tab: " + greeterSoundVoColName);
            sendSystemMessage(player, SID_NO_SOUND_VO_EFFECT_DATA);
            return false;
        }
        int effectListLen = effectList.length;
        if (effectListLen < 0)
        {
            return false;
        }
        String[] menuStringData = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_SOUND_VOICE_EFFECT, greeterSoundVoColNameString);
        if (menuStringData == null)
        {
            return false;
        }
        int menuStringDatalength = menuStringData.length;
        if (menuStringDatalength < 0)
        {
            return false;
        }
        String[] menuStrings = new String[menuStringDatalength];
        for (int i = 0; i < menuStringDatalength; i++)
        {
            menuStrings[i] = "@player_vendor:" + menuStringData[i];
        }
        if (menuStrings == null)
        {
            return false;
        }
        blog("terminal.greeter:getGreeterSoundVoiceList: has effect list.");
        utils.setScriptVar(player, PLAYER_SEL_GREETER_EFFECT_LIST, effectList);
        utils.setScriptVar(player, PLAYER_SEL_GREETER_EFFECT_TYPE, effectType);
        utils.setScriptVar(player, PLAYER_SEL_GREETER_SOUND_VO_STRING_LIST, menuStrings);
        int pid = sui.listbox(self, player, "@player_structure:greeter_effect_list_d", sui.OK_CANCEL, "@player_structure:greeter_effect_list_t", menuStrings, "handlePlayerEffectSelection", true);
        sui.setPid(player, pid, GREETER_EFFECT_PID);
        return true;
    }
    public boolean getGreeterStatementSelection(obj_id self, obj_id controller, obj_id player) throws InterruptedException
    {
        blog("terminal.greeter:getGreeterStatementSelection: init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!validateAllImperativeGreeterVars(self, player))
        {
            return false;
        }
        if (!hasObjVar(controller, vendor_lib.GREETER_HAS_CHAT_OBJVAR))
        {
            blog("terminal.greeter:getGreeterStatementSelection: Player somehow received a statement option when the greeter cannot make statements.");
            return false;
        }
        if (sui.hasPid(player, GREETER_STATEMENT_PID))
        {
            int pid = sui.getPid(player, GREETER_STATEMENT_PID);
            forceCloseSUIPage(pid);
        }
        blog("terminal.greeter:getGreeterStatementSelection: prevalidation completed.");
        String creatureType = getStringObjVar(controller, vendor_lib.CREATURE_TYPE);
        if (creatureType == null || creatureType.equals(""))
        {
            return false;
        }
        String greeterType = "greeter_" + creatureType;
        String[] statementList = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_SAY_CHAT, greeterType);
        if (statementList == null)
        {
            blog("terminal.greeter:getGreeterStatementSelection: Could not find the following col in greeter_say_chat.tab: " + greeterType);
            sendSystemMessage(player, SID_NO_SAY_CHAT_DATA);
            return false;
        }
        blog("terminal.greeter:getGreeterStatementSelection: has effect list.");
        String[] combinedStrings = new String[statementList.length];
        for (int i = 0; i < statementList.length; i++)
        {
            combinedStrings[i] = "@player_structure:" + statementList[i];
        }
        int statementListLen = statementList.length;
        if (statementListLen < 0)
        {
            return false;
        }
        utils.setScriptVar(player, PLAYER_SEL_GREETER_STATEMENT_LIST, statementList);
        int pid = sui.listbox(self, player, "@player_structure:greeter_statement_list_d", sui.OK_CANCEL, "@player_structure:greeter_statement_list_t", combinedStrings, "handlePlayerStatementSelection", true);
        sui.setPid(player, pid, GREETER_STATEMENT_PID);
        return true;
    }
    public boolean destroyGreeter(obj_id self) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        blog("terminal.greeter:destroyGreeter: Greeter Destroy Initiated.");
        CustomerServiceLog("vendor", "terminal.greeter:destroyGreeter: Greeter Destroy Initiated. Object: " + self);
        obj_id ownerId = getObjIdObjVar(self, vendor_lib.GREETER_OWNER_OBJVAR);
        if (!isIdValid(ownerId) && !exists(ownerId))
        {
            blog("terminal.greeter:destroyGreeter: Greeter Destroy could not find owner ID. Destroy object NOW!");
            CustomerServiceLog("vendor", "terminal.greeter:destroyGreeter: Greeter Destroy could not find owner ID for object: " + self + " Destroy object NOW!");
            destroyObject(self);
            return false;
        }
        if (hasObjVar(ownerId, vendor_lib.GREETER_NOT_INIT_OBJVAR))
        {
            removeObjVar(ownerId, vendor_lib.GREETER_NOT_INIT_OBJVAR);
        }
        String ownerName = getName(ownerId);
        if (ownerName == null || ownerName.equals(""))
        {
            blog("terminal.greeter:destroyGreeter: Greeter Destroy could not find owner name with the owner oid. Destroy object NOW!");
            CustomerServiceLog("vendor", "terminal.greeter:destroyGreeter: Greeter Destroy could not find the owner name with the owner oid: " + ownerId + " for object: " + self + " Destroy object NOW!");
            destroyObject(self);
            return false;
        }
        location loc = getLocation(self);
        if (loc == null)
        {
            blog("terminal.greeter:destroyGreeter: Greeter Destroy could not find greeter location. Destroy object NOW!");
            CustomerServiceLog("vendor", "terminal.greeter:destroyGreeter: Greeter Destroy could not find the owner name with the owner oid: " + ownerId + " for object: " + self + " Destroy object NOW!");
            destroyObject(self);
            return false;
        }
        blog("terminal.greeter:destroyGreeter: Attempting to delete.");
        CustomerServiceLog("vendor", "terminal.greeter:destroyGreeter: Destroying Greeter. Owner: " + ownerName + " " + ownerId + " Object: " + self + " Location: " + loc);
        destroyObject(self);
        return true;
    }
    public boolean toggleTriggerVolume(obj_id self) throws InterruptedException
    {
        return toggleTriggerVolume(self, true);
    }
    public boolean toggleTriggerVolume(obj_id self, boolean giveLife) throws InterruptedException
    {
        blog("terminal.greeter:toggleTriggerVolume init: GIVE LIFE = " + giveLife);
        blog("terminal.greeter:toggleTriggerVolume: Greeter Initiated Trigger Volume Toggle.");
        CustomerServiceLog("vendor", "terminal.greeter:handleGreeterInitialize: Greeter Initiated Trigger Volume Toggle. Greeter: " + self);
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        blog("terminal.greeter:toggleTriggerVolume: Greeter ID is valid.");
        obj_id ownerId = getObjIdObjVar(self, vendor_lib.GREETER_OWNER_OBJVAR);
        if (!isIdValid(ownerId) && !exists(ownerId))
        {
            return false;
        }
        blog("terminal.greeter:toggleTriggerVolume: Trigger Volume Owner ID validation complete.");
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return false;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        blog("terminal.greeter:toggleTriggerVolume: Greeter has Controller ID.");
        if (!hasObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR))
        {
            removeTriggerVolume(ALERT_VOLUME_NAME);
            blog("terminal.greeter:toggleTriggerVolume: Greeter Removing Trigger Volume.");
            CustomerServiceLog("vendor", "terminal.greeter:toggleTriggerVolume: Greeter Removing Trigger Volume. OWNER: " + getName(ownerId) + " " + ownerId + " Greeter: " + self);
            removeObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR);
            return true;
        }
        blog("terminal.greeter:toggleTriggerVolume: The greeter is currently active.");
        location loc = getLocation(self);
        if (loc == null)
        {
            blog("terminal.greeter:toggleTriggerVolume: Trigger Toggler could not find greeter location.");
            CustomerServiceLog("vendor", "terminal.greeter:toggleTriggerVolume: Trigger Toggler could not find greeter location for: " + self + " owner oid: " + ownerId);
        }
        blog("terminal.greeter:toggleTriggerVolume: Trigger Volume location received.");
        int radius = GREETER_TRIGGER_VOL_DEFAULT;
        if (hasObjVar(controller, GREETER_TRIGGER_VOL_CURRENT) && getIntObjVar(controller, GREETER_TRIGGER_VOL_CURRENT) >= GREETER_TRIGGER_VOL_MIN)
        {
            radius = getIntObjVar(controller, GREETER_TRIGGER_VOL_CURRENT);
        }
        createTriggerVolume(ALERT_VOLUME_NAME, radius, true);
        blog("terminal.greeter:toggleTriggerVolume: Greeter Creating Trigger Volume.");
        CustomerServiceLog("vendor", "terminal.greeter:toggleTriggerVolume: Greeter Creating Trigger Volume. OWNER: " + getName(ownerId) + " " + ownerId + " Greeter: " + self + " Location: " + loc);
        if (utils.hasScriptVar(self, GREETER_INITIALIZE_SCRVAR))
        {
            utils.removeScriptVar(self, GREETER_INITIALIZE_SCRVAR);
        }
        if (utils.hasScriptVar(self, vendor_lib.GREETER_ALREADY_BARKED_SCRVAR))
        {
            utils.removeScriptVar(self, vendor_lib.GREETER_ALREADY_BARKED_SCRVAR);
        }
        if (giveLife)
        {
            blog("terminal.greeter:toggleTriggerVolume: GIVE LIFE = " + giveLife);
            bringGreeterToLife(self);
        }
        return true;
    }
    public boolean validateAllImperativeGreeterVars(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        else if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return false;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        obj_id ownerId = getObjIdObjVar(self, vendor_lib.GREETER_OWNER_OBJVAR);
        if (!isValidId(ownerId) || !exists(ownerId))
        {
            blog("terminal.greeter:handleVendorAnimSelect: Greeter somehow didn't have a greeter ownerId objvar. Aborting.");
            return false;
        }
        else if (ownerId != player)
        {
            blog("terminal.greeter:handleVendorAnimSelect: Greeter is being accessed by a player that is not owner. Aborting.");
            return false;
        }
        else if (!hasObjVar(controller, vendor_lib.GREETER_IS_ACTIVATED_OBJVAR))
        {
            blog("terminal.greeter:handleVendorAnimSelect: Greeter somehow didn't have a greeter activation objvar. Aborting.");
            return false;
        }
        else if (!hasObjVar(controller, vendor_lib.CREATURE_TYPE))
        {
            blog("terminal.greeter:handleVendorAnimSelect: Greeter somehow didn't have a creature type objvar. Aborting.");
            return false;
        }
        else if (hasObjVar(controller, vendor_lib.GREETER_NOT_INIT_OBJVAR))
        {
            blog("terminal.greeter:handleVendorAnimSelect: Greeter not initialized. Aborting.");
            return false;
        }
        else if (hasObjVar(controller, vendor_lib.GREETER_NOT_INIT_OBJVAR))
        {
            blog("terminal.greeter:handleVendorAnimSelect: Owner using Greeter still has the not initialized var. Aborting.");
            return false;
        }
        return true;
    }
    public boolean bringGreeterToLife(obj_id self) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!hasObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return false;
        }
        obj_id ownerId = getObjIdObjVar(self, vendor_lib.GREETER_OWNER_OBJVAR);
        if (!isIdValid(ownerId))
        {
            return false;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        location loc = getLocation(self);
        if (loc == null)
        {
            return false;
        }
        int radius = GREETER_TRIGGER_VOL_DEFAULT;
        if (hasObjVar(controller, GREETER_TRIGGER_VOL_CURRENT) && getIntObjVar(controller, GREETER_TRIGGER_VOL_CURRENT) >= GREETER_TRIGGER_VOL_MIN)
        {
            radius = getIntObjVar(controller, GREETER_TRIGGER_VOL_CURRENT);
        }
        if (radius <= 0)
        {
            return false;
        }
        obj_id[] objects = getPlayerCreaturesInRange(loc, radius);
        if (objects == null || objects.length == 0)
        {
            return false;
        }
        obj_id playerToGreet = objects[0];
        bringGreeterToLife(self, controller, playerToGreet);
        return true;
    }
    public boolean bringGreeterToLife(obj_id self, obj_id controller, obj_id player) throws InterruptedException
    {
        blog("vendor.greeter.bringGreeterToLife: init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        blog("vendor.greeter.bringGreeterToLife: validation complete. Player: " + player);
        String anim = "";
        String voice = "";
        String mood = "friendly";
        String sound = "";
        String effect = "";
        String say_chat = "";
        blog("vendor.greeter.bringGreeterToLife: initialized greeter vars ");
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_ANIMS_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR))
        {
            anim = getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION);
            blog("vendor.greeter.bringGreeterToLife: has anim: " + anim);
        }
        blog("vendor.greeter.bringGreeterToLife: VOICE VAR: " + utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_VO_OBJVAR));
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_VO_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR))
        {
            voice = getStringObjVar(controller, vendor_lib.GREETER_VOICES_OBJVAR);
            blog("vendor.greeter.bringGreeterToLife: has voice: " + voice);
        }
        blog("vendor.greeter.bringGreeterToLife: SOUND VAR: " + utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_SOUND_OBJVAR));
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_SOUND_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR))
        {
            sound = getStringObjVar(controller, vendor_lib.GREETER_SOUNDS_OBJVAR);
            blog("vendor.greeter.bringGreeterToLife: has sound: " + sound);
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_MOODS_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR))
        {
            mood = getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD);
            blog("vendor.greeter.bringGreeterToLife: has mood: " + mood);
        }
        else 
        {
            mood = ai_lib.MOOD_CALM;
            blog("vendor.greeter.bringGreeterToLife: has anim: " + mood);
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_CHAT_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR))
        {
            say_chat = getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_STATEMENT);
            blog("vendor.greeter.bringGreeterToLife: has say_chat: " + say_chat);
        }
        blog("vendor.greeter.bringGreeterToLife: trigger passed more tests");
        blog("vendor.greeter.bringGreeterToLife: anim: " + anim);
        blog("vendor.greeter.bringGreeterToLife: voice: " + voice);
        blog("vendor.greeter.bringGreeterToLife: sound: " + sound);
        blog("vendor.greeter.bringGreeterToLife: say_chat: " + say_chat);
        faceTo(self, player);
        if (voice != null && !voice.equals(""))
        {
            blog("vendor.greeter.bringGreeterToLife: trigger VOICE using voice: " + voice);
            if (!play2dNonLoopingSound(player, voice))
            {
                blog("vendor.greeter.bringGreeterToLife: trigger FAILED VOICE");
            }
        }
        if (say_chat != null && !say_chat.equals(""))
        {
            blog("vendor.greeter.bringGreeterToLife: trigger CHATTING");
            chat._chat(self, player, chat.CHAT_SAY, mood, say_chat, null, null);
        }
        if (anim != null && !anim.equals(""))
        {
            blog("vendor.greeter.bringGreeterToLife: trigger ANIMATING using anim: " + anim);
            doAnimationAction(self, anim);
        }
        if (sound != null && !sound.equals(""))
        {
            blog("vendor.greeter.bringGreeterToLife: trigger SOUNDS using sound: " + sound);
            playClientEffectObj(self, sound, player, "");
        }
        blog("vendor.greeter.bringGreeterToLife: trigger should have done it all");
        utils.setScriptVar(self, vendor_lib.GREETER_ALREADY_BARKED_SCRVAR, 1);
        int delay = GREETER_BARK_TIMER_MAX;
        if (hasObjVar(controller, GREETER_DELAY_TIMER_CURRENT) && getIntObjVar(controller, GREETER_DELAY_TIMER_CURRENT) >= GREETER_BARK_TIMER_MIN)
        {
            delay = getIntObjVar(controller, GREETER_DELAY_TIMER_CURRENT);
        }
        messageTo(self, "resetBarkTimer", null, delay, false);
        return true;
    }
    public boolean deleteGreeting(obj_id self, obj_id player, obj_id controller, String saveName) throws InterruptedException
    {
        blog("vendor.greeter.deleteGreeting: init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (saveName == null || saveName.equals(""))
        {
            return false;
        }
        blog("vendor.greeter.deleteGreeting: initial validation completed");
        String[] allSaves = getStringArrayObjVar(controller, GREETER_SAVE_DATA);
        if (allSaves == null)
        {
            return false;
        }
        blog("vendor.greeter.deleteGreeting: allSaves.length: " + allSaves.length);
        int listLength = allSaves.length;
        if (listLength == 1 && allSaves[0].startsWith(saveName + "-"))
        {
            blog("vendor.greeter.deleteGreeting: List LENGTH == 1, removing OBJVAR");
            removeObjVar(controller, GREETER_SAVE_DATA);
            return true;
        }
        blog("vendor.greeter.deleteGreeting: List longer than 1, saveName:" + saveName);
        int row = -1;
        for (int a = 0; a < listLength; a++)
        {
            blog("vendor.greeter.deleteGreeting: Loop found: " + allSaves[a]);
            if (allSaves[a].startsWith(saveName + "-"))
            {
                blog("vendor.greeter.deleteGreeting: Loop FOUND MATCH: " + allSaves[a]);
                row = a;
                break;
            }
        }
        if (row < 0)
        {
            return false;
        }
        blog("vendor.greeter.deleteGreeting: row: " + row);
        String[] newSaveList = new String[listLength - 1];
        blog("vendor.greeter.deleteGreeting: newSaveList.length(): " + newSaveList.length);
        int i = 0;
        for (int b = 0; b < listLength; b++)
        {
            if (b == row)
            {
                continue;
            }
            blog("vendor.greeter.deleteGreeting: allSaves[b]: " + allSaves[b] + " being saved to location: " + i + " in array newSaveList");
            newSaveList[i] = allSaves[b];
            i++;
        }
        blog("vendor.greeter.deleteGreeting: array copy skipping deleted complete");
        setObjVar(controller, GREETER_SAVE_DATA, newSaveList);
        return true;
    }
    public boolean getSavedGreetings(obj_id self, obj_id player, obj_id controller) throws InterruptedException
    {
        blog("vendor.greeter.getSavedGreetings: init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        blog("vendor.greeter.getSavedGreetings: initial validation completed.");
        String[] allSaves = getStringArrayObjVar(controller, GREETER_SAVE_DATA);
        if (allSaves == null || allSaves.length == 0)
        {
            sendSystemMessage(player, SID_NO_SAVED_GREETS);
            return false;
        }
        blog("vendor.greeter.getSavedGreetings: Saved Data List Length: " + allSaves.length);
        blog("vendor.greeter.getSavedGreetings: allSaves[0]: " + allSaves[0]);
        Vector allNamesVector = new Vector();
        Vector allDataVector = new Vector();
        Vector combinedDataVector = new Vector();
        for (String allSave : allSaves) {
            blog("vendor.greeter.getSavedGreetings: Loop found: " + allSave);
            if (allSave == null || allSave.length() == 0) {
                continue;
            }
            blog("vendor.greeter.getSavedGreetings: allSaves[i].length(): " + allSave.length());
            String[] data = split(allSave, '-');
            if (data == null) {
                break;
            }
            if (data[0] == null || data[1] == null) {
                continue;
            }
            blog("vendor.greeter.getSavedGreetings: Split [0] found: " + data[0]);
            blog("vendor.greeter.getSavedGreetings: Split [1] found: " + data[1]);
            allNamesVector.add(data[0]);
            allDataVector.add(data[1]);
            combinedDataVector.add(allSave);
        }
        blog("vendor.greeter.getSavedGreetings: Name List Length: " + allNamesVector.size());
        blog("vendor.greeter.getSavedGreetings: Data List Length: " + allDataVector.size());
        if (allNamesVector.size() <= 0 || allDataVector.size() <= 0 || combinedDataVector.size() <= 0)
        {
            return false;
        }
        if (allNamesVector.size() != allDataVector.size() || allNamesVector.size() != combinedDataVector.size())
        {
            return false;
        }
        String[] allNamesArray = new String[allNamesVector.size()];
        allNamesVector.toArray(allNamesArray);
        String[] allDataArray = new String[allDataVector.size()];
        allDataVector.toArray(allDataArray);
        if (allSaves.length != combinedDataVector.size())
        {
            String[] newAllSaves = new String[combinedDataVector.size()];
            combinedDataVector.toArray(newAllSaves);
            setObjVar(controller, GREETER_SAVE_DATA, newAllSaves);
        }
        blog("vendor.greeter.getSavedGreetings: Saved Data Conversion to arrays completed");
        utils.setScriptVar(player, GREETER_SAVED_GREET_NAMES, allNamesArray);
        utils.setScriptVar(player, GREETER_SAVED_GREET_DATA, allDataArray);
        return true;
    }
    public boolean saveGreeting(obj_id self, obj_id player, obj_id controller, String saveName) throws InterruptedException
    {
        blog("vendor.greeter.saveGreeting: init.");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (saveName == null || saveName.equals(""))
        {
            return false;
        }
        blog("vendor.greeter.saveGreeting: inital validation completed.");
        String[] allSaves = getStringArrayObjVar(controller, GREETER_SAVE_DATA);
        if (allSaves == null)
        {
            blog("vendor.greeter.saveGreeting: GREETER_SAVE_DATA had to be created.");
            allSaves = new String[1];
        }
        else if (allSaves.length < GREETER_SAVE_MAX)
        {
            blog("vendor.greeter.saveGreeting: allSaves.length: " + allSaves.length);
            int currentSaveArrayLenth = allSaves.length;
            String[] tempList = new String[currentSaveArrayLenth + 1];
            for (int i = 0; i < currentSaveArrayLenth; i++)
            {
                if (allSaves[i].startsWith(saveName + "-"))
                {
                    sendSystemMessage(player, SID_NAME_ALREADY_EXISTS);
                    return false;
                }
                blog("vendor.greeter.saveGreeting: Loop: " + i);
                if (allSaves[i] == null)
                {
                    blog("vendor.greeter.saveGreeting: &&&&&&&&&&&&EXISTING SAVE DATA HAD NULL ENTRY.");
                }
                blog("vendor.greeter.saveGreeting: allSaves[i]: " + allSaves[i]);
                blog("vendor.greeter.saveGreeting: allSaves[i].length(): " + allSaves[i].length());
                tempList[i] = allSaves[i];
            }
            allSaves = tempList;
        }
        else 
        {
            sendSystemMessage(player, SID_GREETER_SAVE_MAX);
            return false;
        }
        blog("vendor.greeter.saveGreeting: validation complete. Player: " + player);
        String sayChatSlot = "";
        String animSlot = "";
        String voiceSlot = "";
        String moodSlot = "";
        String soundSlot = "";
        String soundString = "";
        String effectSlot = "";
        String saveString = saveName + "-";
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_ANIMS_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR))
        {
            animSlot = getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION);
            if (animSlot == null || animSlot.equals(""))
            {
                return false;
            }
            saveString += SAVE_ANIM + animSlot + COMMA;
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_VO_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR))
        {
            voiceSlot = getStringObjVar(controller, vendor_lib.GREETER_VOICES_OBJVAR);
            if (voiceSlot == null || voiceSlot.equals(""))
            {
                return false;
            }
            soundString = getStringObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR);
            if (soundString == null || soundString.equals(""))
            {
                return false;
            }
            saveString += SAVE_VOICE + voiceSlot + PIPE + soundString + COMMA;
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_SOUND_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR))
        {
            soundSlot = getStringObjVar(controller, vendor_lib.GREETER_SOUNDS_OBJVAR);
            if (soundSlot == null || soundSlot.equals(""))
            {
                return false;
            }
            soundString = getStringObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR);
            if (soundString == null || soundString.equals(""))
            {
                return false;
            }
            saveString += SAVE_SOUND + soundSlot + PIPE + soundString + COMMA;
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_MOODS_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR))
        {
            moodSlot = getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD);
            if (moodSlot == null || moodSlot.equals(""))
            {
                return false;
            }
            saveString += SAVE_MOOD + moodSlot + COMMA;
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_CHAT_OBJVAR) == vendor_lib.VAR_TRUE && hasObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR))
        {
            sayChatSlot = getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_STATEMENT);
            if (sayChatSlot == null || sayChatSlot.equals(""))
            {
                return false;
            }
            saveString += SAVE_SAY_CHAT + sayChatSlot + COMMA;
        }
        if (saveString == null || saveString.equals(""))
        {
            return false;
        }
        allSaves[allSaves.length - 1] = saveString;
        setObjVar(controller, GREETER_SAVE_DATA, allSaves);
        return true;
    }
    public boolean getMainProgramUIListOptions(obj_id self, obj_id player, obj_id controller) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        Vector dynMenuStrings = new Vector();
        Vector dynMenuData = new Vector();
        dynMenuStrings.add(MENU_CHANGE_NAME);
        dynMenuData.add(GREETER_CHANGE_NAME);
        if (hasObjVar(controller, vendor_lib.GREETER_CURRENTLY_RANDOMIZED_GREET))
        {
            dynMenuStrings.add(MENU_RANDOMIZED_GREETINGS_OFF);
            dynMenuData.add(GREETER_RANDOMIZED_GREETINGS_OFF);
        }
        else 
        {
            dynMenuStrings.add(MENU_RANDOMIZED_GREETINGS_ON);
            dynMenuData.add(GREETER_RANDOMIZED_GREETINGS_ON);
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_ANIMS_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            if (hasObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR))
            {
                dynMenuStrings.add(MENU_GREETER_ANIMATING_ON);
                dynMenuData.add(GREETER_ANIMATING_ENABLED);
                dynMenuStrings.add(MENU_GREETER_ANIMATING_EDIT + " " + getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION_STRING));
                dynMenuData.add(GREETER_ANIMATING_EDIT);
            }
            else 
            {
                dynMenuStrings.add(MENU_GREETER_ANIMATING_OFF);
                dynMenuData.add(GREETER_ANIMATING_DISABLED);
            }
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_VO_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            if (hasObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR))
            {
                dynMenuStrings.add(MENU_GREETER_VOICE_ON);
                dynMenuData.add(GREETER_VOICING_ENABLED);
                dynMenuStrings.add(MENU_GREETER_VOICE_EDIT + " " + getStringObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR));
                dynMenuData.add(GREETER_VOICING_EDIT);
            }
            else 
            {
                dynMenuStrings.add(MENU_GREETER_VOICE_OFF);
                dynMenuData.add(GREETER_VOICING_DISABLED);
            }
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_SOUND_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            if (hasObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR))
            {
                dynMenuStrings.add(MENU_GREETER_SOUND_ON);
                dynMenuData.add(GREETER_SOUNDS_ENABLED);
                dynMenuStrings.add(MENU_GREETER_SOUND_EDIT + " " + getStringObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR));
                dynMenuData.add(GREETER_SOUNDS_EDIT);
            }
            else 
            {
                dynMenuStrings.add(MENU_GREETER_SOUND_OFF);
                dynMenuData.add(GREETER_SOUNDS_DISABLED);
            }
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_MOODS_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            if (hasObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR))
            {
                dynMenuStrings.add(MENU_GREETER_MOOD_ON);
                dynMenuData.add(GREETER_MOOD_ENABLED);
                dynMenuStrings.add(MENU_GREETER_MOOD_EDIT + " " + getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD_STRING));
                dynMenuData.add(GREETER_MOOD_EDIT);
            }
            else 
            {
                dynMenuStrings.add(MENU_GREETER_MOOD_OFF);
                dynMenuData.add(GREETER_MOOD_DISABLED);
            }
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_CHAT_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            if (hasObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR))
            {
                dynMenuStrings.add(MENU_GREETER_STATEMENT_ON);
                dynMenuData.add(GREETER_STATEMENT_ENABLED);
                dynMenuStrings.add(MENU_GREETER_STATEMENT_EDIT + " " + getStringObjVar(controller, vendor_lib.GREETER_ACTUAL_STATEMENT));
                dynMenuData.add(GREETER_STATEMENT_EDIT);
            }
            else 
            {
                dynMenuStrings.add(MENU_GREETER_STATEMENT_OFF);
                dynMenuData.add(GREETER_STATEMENT_DISABLED);
            }
        }
        if (hasObjVar(controller, vendor_lib.GREETER_COLOR_OBJVAR) && getBooleanObjVar(controller, vendor_lib.GREETER_COLOR_OBJVAR) == true)
        {
            dynMenuStrings.add(MENU_COLOR);
            dynMenuData.add(GREETER_COLOR_CHANGE);
        }
        dynMenuStrings.add(MENU_GREETER_RANDOM_ALL);
        dynMenuData.add(GREETER_RANDOM);
        if (hasObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR) || hasObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR) || hasObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR) || hasObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR) || hasObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR))
        {
            dynMenuStrings.add(MENU_GREETER_PREVIEW_ALL);
            dynMenuData.add(GREETER_PREVIEW);
        }
        dynMenuStrings.add(MENU_GREETER_MODIFY_DELAY);
        dynMenuData.add(GREETER_MODIFY_DELAY);
        dynMenuStrings.add(MENU_GREETER_MODIFY_TRIGGER_VOL_RADIUS);
        dynMenuData.add(GREETER_MODIFY_TRIGGER_VOL_RADIUS);
        if (hasObjVar(controller, FOUND_OTHER_GREETERS))
        {
            blog("terminal.greeter.getMainProgramUIListOptions: FOUND_OTHER_GREETERS");
            if (getBooleanObjVar(controller, FOUND_OTHER_GREETERS) == true && getBooleanObjVar(controller, vendor_lib.GREETER_CONVERSE) == true)
            {
                blog("terminal.greeter.getMainProgramUIListOptions: FOUND_OTHER_GREETERS && GREETER_CONVERSE");
                dynMenuStrings.add(MENU_GREETER_STOP_CONVERSE_OTHER_GREETER);
                dynMenuData.add(GREETER_STOP_ITERACTION);
            }
            else 
            {
                blog("terminal.greeter.getMainProgramUIListOptions: FOUND_OTHER_GREETERS BUT NOT GREETER_CONVERSE");
                dynMenuStrings.add(MENU_GREETER_CONVERSE_OTHER_GREETER);
                dynMenuData.add(GREETER_START_ITERACTION);
            }
        }
        if (hasObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR) || hasObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR) || hasObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR) || hasObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR) || hasObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR))
        {
            dynMenuStrings.add(MENU_GREETER_SAVE_GREET);
            dynMenuData.add(GREETER_SAVE_GREET);
        }
        if (hasObjVar(controller, GREETER_SAVE_DATA))
        {
            String[] allData = getStringArrayObjVar(controller, GREETER_SAVE_DATA);
            if (allData.length > 0)
            {
                dynMenuStrings.add(MENU_GREETER_LIST_GREET);
                dynMenuData.add(GREETER_LIST_GREET);
            }
        }
        String[] availableOptionStrings = new String[dynMenuStrings.size()];
        dynMenuStrings.toArray(availableOptionStrings);
        String[] availableDataOptions = new String[dynMenuData.size()];
        dynMenuData.toArray(availableDataOptions);
        utils.setScriptVar(player, GREETER_MAIN_MENU_STRINGS, availableOptionStrings);
        utils.setScriptVar(player, GREETER_MAIN_MENU_DATA, availableDataOptions);
        return true;
    }
    public boolean buildMainControlMenu(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (sui.hasPid(player, GREETER_PROGRAM_MAIN_PID))
        {
            int pid = sui.getPid(player, GREETER_PROGRAM_MAIN_PID);
            forceCloseSUIPage(pid);
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!getMainProgramUIListOptions(self, player, controller))
        {
            blog("terminal.greeter.OnObjectMenuSelect: List failed");
            return false;
        }
        String[] menuStrings = utils.getStringArrayScriptVar(player, GREETER_MAIN_MENU_STRINGS);
        if (menuStrings == null)
        {
            return false;
        }
        int pid = sui.listbox(self, player, SUI_GREETER_INSTRUCTIONS_DESCR, sui.OK_CANCEL, SUI_GREETER_INSTRUCTIONS_TITLE, menuStrings, "handlePlayerProgramSelect", true);
        sui.setPid(player, pid, GREETER_PROGRAM_MAIN_PID);
        return true;
    }
    public boolean loadSavedData(obj_id self, obj_id player, obj_id controller, String selection) throws InterruptedException
    {
        blog("terminal.greeter.loadSavedData: init");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (selection == null || selection.equals(""))
        {
            return false;
        }
        blog("terminal.greeter.loadSavedData: initial validation completed");
        String[] fileName = split(selection, '-');
        if (fileName == null || fileName.length <= 0)
        {
            return false;
        }
        blog("terminal.greeter.loadSavedData: fileNme: " + fileName[0]);
        blog("terminal.greeter.loadSavedData: data: " + fileName[1]);
        String[] data = split(fileName[1], ',');
        if (data == null || data.length <= 0)
        {
            return false;
        }
        blog("terminal.greeter.loadSavedData: selection: " + selection);
        blog("terminal.greeter.loadSavedData: data.length: " + data.length);
        blog("terminal.greeter.loadSavedData: data[0]: " + data[0]);
        blog("terminal.greeter.loadSavedData: data[1]: " + data[1]);
        String sayChatSlot = "";
        String animSlot = "";
        String voiceSlot = "";
        String moodSlot = "";
        String soundSlot = "";
        removeObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR);
        removeObjVar(controller, vendor_lib.GREETER_VOICES_OBJVAR);
        removeObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR);
        removeObjVar(controller, vendor_lib.GREETER_SOUNDS_OBJVAR);
        removeObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR);
        removeObjVar(controller, vendor_lib.GREETER_CURRENTLY_RANDOMIZED_GREET);
        for (String datum : data) {
            if (datum == null || datum.length() == 0) {
                continue;
            }
            if (datum.startsWith(SAVE_SAY_CHAT)) {
                blog("terminal.greeter.loadSavedData: SAVE_SAY_CHAT");
                String[] sayChatData = split(datum, '=');
                if (sayChatData == null || sayChatData.length <= 0) {
                    continue;
                }
                blog("terminal.greeter.loadSavedData: sayChatData: " + sayChatData[1]);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_STATEMENT, sayChatData[1]);
                setObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR, true);
                continue;
            }
            if (datum.startsWith(SAVE_VOICE)) {
                blog("terminal.greeter.loadSavedData: SAVE_VOICE");
                String[] voiceData = split(datum, '=');
                if (voiceData == null || voiceData.length <= 0) {
                    continue;
                }
                blog("terminal.greeter.loadSavedData: voiceData: " + voiceData[1]);
                String[] voiceAndStringData = split(voiceData[1], '|');
                if (voiceAndStringData == null || voiceAndStringData.length <= 0) {
                    continue;
                }
                setObjVar(controller, vendor_lib.GREETER_VOICES_OBJVAR, voiceAndStringData[0]);
                setObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR, voiceAndStringData[1]);
                setObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR, true);
                continue;
            }
            if (datum.startsWith(SAVE_MOOD)) {
                blog("terminal.greeter.loadSavedData: SAVE_MOOD");
                String[] moodData = split(datum, '=');
                if (moodData == null || moodData.length <= 0) {
                    continue;
                }
                blog("terminal.greeter.loadSavedData: moodData: " + moodData[1]);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD, moodData[1]);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD_STRING, "@player_structure:" + moodData[1]);
                setObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR, true);
                continue;
            }
            if (datum.startsWith(SAVE_SOUND)) {
                blog("terminal.greeter.loadSavedData: SAVE_SOUND");
                String[] soundData = split(datum, '=');
                if (soundData == null || soundData.length <= 0) {
                    continue;
                }
                blog("terminal.greeter.loadSavedData: soundData: " + soundData[1]);
                String[] soundAndStringData = split(soundData[1], '|');
                if (soundAndStringData == null || soundAndStringData.length <= 0) {
                    continue;
                }
                blog("terminal.greeter.loadSavedData: soundData: " + soundAndStringData[0]);
                setObjVar(controller, vendor_lib.GREETER_SOUNDS_OBJVAR, soundAndStringData[0]);
                setObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR, soundAndStringData[1]);
                setObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR, true);
                continue;
            }
            if (datum.startsWith(SAVE_ANIM)) {
                blog("terminal.greeter.loadSavedData: SAVE_ANIM");
                String[] animData = split(datum, '=');
                if (animData == null || animData.length <= 0) {
                    continue;
                }
                blog("terminal.greeter.loadSavedData: animData: " + animData[1]);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION, animData[1]);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION_STRING, "@player_structure:" + animData[1]);
                setObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR, true);
                continue;
            }
        }
        return true;
    }
    public boolean getRandomGreeting(obj_id self, obj_id controller) throws InterruptedException
    {
        blog("terminal.greeter.getRandomGreeting: init");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        blog("terminal.greeter.getRandomGreeting: initial validation completed");
        if (!hasObjVar(controller, vendor_lib.GREETER_CURRENTLY_RANDOMIZED_GREET) || getBooleanObjVar(controller, vendor_lib.GREETER_CURRENTLY_RANDOMIZED_GREET) == false)
        {
            if (!hasObjVar(controller, vendor_lib.GREETER_RANDOM_TEST_FIRE) || getBooleanObjVar(controller, vendor_lib.GREETER_RANDOM_TEST_FIRE) == false)
            {
                blog("terminal.greeter.getRandomGreeting: I AM NOT LABELED AS A RANDOMIZED GREETER");
                return false;
            }
        }
        blog("terminal.greeter.getRandomGreeting: I AM STILL LABELED AS A RANDOMIZED GREETER");
        String creatureType = getStringObjVar(controller, vendor_lib.CREATURE_TYPE);
        if (creatureType == null || creatureType.equals(""))
        {
            return false;
        }
        blog("terminal.greeter.getRandomGreeting: creatureType: " + creatureType);
        String greeterType = "greeter_" + creatureType;
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_ANIMS_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            blog("terminal.greeter.getRandomGreeting: GREETER_ANIMATES_OBJVAR");
            if (greeterType.equals("greeter_ewok") || greeterType.equals("greeter_jawa") || greeterType.equals("greeter_battle_droid") || greeterType.equals("greeter_toydarian") || greeterType.equals("greeter_pa_lowick"))
            {
                blog("terminal.greeter.getRandomGreeting: Is an ewok or jawa");
                String[] jawaOrEwokEmoteList = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_ANIMS, "emote_" + greeterType);
                if (jawaOrEwokEmoteList == null)
                {
                    return false;
                }
                int listLength = jawaOrEwokEmoteList.length;
                if (listLength <= 0)
                {
                    return false;
                }
                int randomAnimInt = rand(0, listLength - 1);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION, jawaOrEwokEmoteList[randomAnimInt]);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION_STRING, "@player_structure:" + jawaOrEwokEmoteList[randomAnimInt]);
                blog("terminal.greeter.getRandomGreeting: randAnim: " + jawaOrEwokEmoteList[randomAnimInt]);
                setObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR, true);
            }
            else 
            {
                blog("terminal.greeter.getRandomGreeting: not an ewok or jawa");
                String[] greeterAnimCategories = vendor_lib.getAllGreeterDatatableColumnNames(self, vendor_lib.TBL_GREETER_ANIMS);
                if (greeterAnimCategories == null)
                {
                    return false;
                }
                int greeterAnimCategoriesLen = greeterAnimCategories.length;
                if (greeterAnimCategoriesLen <= 0)
                {
                    return false;
                }
                int randomCategory = rand(0, greeterAnimCategoriesLen - 1);
                String animCategory = greeterAnimCategories[randomCategory];
                String[] categoryAnims = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_ANIMS, animCategory);
                if (categoryAnims == null)
                {
                    return false;
                }
                int categoryAnimslength = categoryAnims.length;
                if (categoryAnimslength <= 0)
                {
                    return false;
                }
                int randomAnimInt = rand(0, categoryAnimslength - 1);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION, categoryAnims[randomAnimInt]);
                setObjVar(controller, vendor_lib.GREETER_ACTUAL_ANIMATION_STRING, "@player_structure:" + categoryAnims[randomAnimInt]);
                blog("terminal.greeter.getRandomGreeting: randAnim: " + categoryAnims[randomAnimInt]);
                setObjVar(controller, vendor_lib.GREETER_ANIMATING_OBJVAR, true);
            }
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_VO_OBJVAR) == vendor_lib.VAR_TRUE || utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_SOUND_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            blog("greeter.getRandomGreeting: GREETER_HAS_SOUND_OBJVAR or GREETER_HAS_VO_OBJVAR");
            String greeterSoundVoColName = "";
            String greeterSoundVoColNameString = "";
            if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_SOUND_OBJVAR) == vendor_lib.VAR_TRUE)
            {
                blog("greeter.getRandomGreeting: GREETER_HAS_SOUND_OBJVAR");
                greeterSoundVoColName = greeterType + "_" + SOUND_OPTION;
                greeterSoundVoColNameString = greeterType + "_" + SOUND_STRING;
            }
            else 
            {
                blog("greeter.getRandomGreeting: GREETER_HAS_VO_OBJVAR");
                greeterSoundVoColName = greeterType + "_" + VOICE_OPTION;
                greeterSoundVoColNameString = greeterType + "_" + VOICE_STRING;
            }
            if (hasObjVar(self, "greeter.sound_vo"))
            {
                String soundVo = getStringObjVar(self, "greeter.sound_vo");
                String soundVoString = getStringObjVar(self, "greeter.sound_vo_string");
                if (soundVo != null && !soundVo.equals("") && soundVoString != null && !soundVoString.equals(""))
                {
                    greeterSoundVoColName = soundVo;
                    greeterSoundVoColNameString = soundVoString;
                }
            }
            blog("greeter.getRandomGreeting: greeterSoundVoColName: " + greeterSoundVoColName);
            String[] effectList = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_SOUND_VOICE_EFFECT, greeterSoundVoColName);
            if (effectList == null || effectList.length <= 0)
            {
                return false;
            }
            int effectListLen = effectList.length;
            String[] stringList = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_SOUND_VOICE_EFFECT, greeterSoundVoColNameString);
            if (stringList == null || stringList.length <= 0 || stringList.length != effectListLen)
            {
                return false;
            }
            int randNumber = rand(0, effectListLen - 1);
            if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_SOUND_OBJVAR) == vendor_lib.VAR_TRUE)
            {
                blog("greeter.getRandomGreeting: got a sound:" + effectList[randNumber]);
                setObjVar(controller, vendor_lib.GREETER_SOUNDS_OBJVAR, effectList[randNumber]);
                blog("terminal.greeter.getRandomGreeting: randSound: " + effectList[randNumber]);
                setObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR, "@player_vendor:" + stringList[randNumber]);
                setObjVar(controller, vendor_lib.GREETER_SOUNDING_OBJVAR, true);
            }
            else 
            {
                blog("greeter.getRandomGreeting: got a VO: " + effectList[randNumber]);
                setObjVar(controller, vendor_lib.GREETER_VOICES_OBJVAR, effectList[randNumber]);
                blog("terminal.greeter.getRandomGreeting: randVO: " + effectList[randNumber]);
                setObjVar(controller, vendor_lib.GREETER_SOUNDS_VO_MENU_OBJVAR, "@player_vendor:" + stringList[randNumber]);
                setObjVar(controller, vendor_lib.GREETER_VOICING_OBJVAR, true);
            }
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_MOODS_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            blog("terminal.greeter.getRandomGreeting: GREETER_MOODS_OBJVAR");
            String[] greeterMoodCategories = vendor_lib.getAllGreeterDatatableColumnNames(self, vendor_lib.TBL_GREETER_MOODS);
            if (greeterMoodCategories == null)
            {
                return false;
            }
            int greeterMoodCategoriesLen = greeterMoodCategories.length;
            if (greeterMoodCategoriesLen <= 0)
            {
                return false;
            }
            int randNumber = rand(0, greeterMoodCategoriesLen - 1);
            String randMoodCat = greeterMoodCategories[randNumber];
            String[] categoryMoods = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_MOODS, randMoodCat);
            if (categoryMoods == null)
            {
                return false;
            }
            int moodListLength = categoryMoods.length;
            if (moodListLength <= 0)
            {
                return false;
            }
            int randMoodInt = rand(0, moodListLength - 1);
            setObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD, categoryMoods[randMoodInt]);
            setObjVar(controller, vendor_lib.GREETER_ACTUAL_MOOD_STRING, "@player_structure:" + categoryMoods[randMoodInt]);
            blog("terminal.greeter.getRandomGreeting: randMood: " + categoryMoods[randMoodInt]);
            setObjVar(controller, vendor_lib.GREETER_HAS_MOOD_OBJVAR, true);
        }
        if (utils.getIntObjVar(controller, vendor_lib.GREETER_HAS_CHAT_OBJVAR) == vendor_lib.VAR_TRUE)
        {
            blog("terminal.greeter.getRandomGreeting: GREETER_STATEMENT_OBJVAR");
            String[] statementList = dataTableGetStringColumnNoDefaults(vendor_lib.TBL_GREETER_SAY_CHAT, greeterType);
            if (statementList == null)
            {
                return false;
            }
            int statementListLen = statementList.length;
            if (statementListLen < 0)
            {
                return false;
            }
            int randNumber = rand(0, statementListLen - 1);
            setObjVar(controller, vendor_lib.GREETER_ACTUAL_STATEMENT, localize(new string_id("player_structure", statementList[randNumber])));
            blog("terminal.greeter.getRandomGreeting: randMood: " + statementList[randNumber]);
            setObjVar(controller, vendor_lib.GREETER_HAS_STATEMENT_OBJVAR, true);
        }
        blog("terminal.greeter.getRandomGreeting: RETURNING TRUE");
        return true;
    }
    public boolean conversableGreeterInRange(obj_id self) throws InterruptedException
    {
        blog("terminal.greeter.conversableGreeterInRange: init");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        obj_id controller = getObjIdObjVar(self, vendor_lib.CNTRLR_GREETER_NONVENDOR_ID_OBJVAR);
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        blog("terminal.greeter.conversableGreeterInRange: controller received");
        if (hasObjVar(controller, FOUND_OTHER_GREETER_TIMER))
        {
            blog("terminal.greeter.conversableGreeterInRange: controller has FOUND_OTHER_GREETER_TIMER");
            int gameTime = getGameTime();
            int gameTimeSaved = getIntObjVar(self, FOUND_OTHER_GREETER_TIMER);
            int diff = gameTime - gameTimeSaved;
            if (diff < 0)
            {
                removeObjVar(controller, FOUND_OTHER_GREETER_TIMER);
            }
            if (diff < GREETER_TIMER_DELAY)
            {
                return false;
            }
            blog("terminal.greeter.conversableGreeterInRange: controller removing FOUND_OTHER_GREETER_TIMER");
            removeObjVar(controller, FOUND_OTHER_GREETER_TIMER);
        }
        location loc = getLocation(self);
        obj_id[] allGreeters = getAllObjectsWithScript(loc, MAX_GREETER_CONV_RANGE_FLOAT, "terminal.greeter");
        if (allGreeters == null || allGreeters.length <= 0)
        {
            return false;
        }
        if (allGreeters.length == 1 && allGreeters[0] == self)
        {
            return false;
        }
        setObjVar(controller, FOUND_OTHER_GREETERS, true);
        setObjVar(controller, FOUND_OTHER_GREETER_TIMER, getGameTime());
        return true;
    }
    public obj_id getConversableGreeter(obj_id self, obj_id player, obj_id controller) throws InterruptedException
    {
        blog("terminal.greeter.getConversableGreeter: Init");
        if (!isValidId(self) || !exists(self))
        {
            return null;
        }
        if (!isValidId(player))
        {
            return null;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return null;
        }
        blog("terminal.greeter.getConversableGreeter: validation completed");
        if (!hasObjVar(controller, FOUND_OTHER_GREETERS))
        {
            return null;
        }
        blog("terminal.greeter.getConversableGreeter: has correct variable");
        location loc = getLocation(self);
        if (loc == null)
        {
            return null;
        }
        blog("terminal.greeter.getConversableGreeter: locaiton of self attained.");
        obj_id[] allGreeters = getAllObjectsWithScript(loc, MAX_GREETER_CONV_RANGE_FLOAT, "terminal.greeter");
        if (allGreeters == null || allGreeters.length <= 0 || (allGreeters.length == 1 && allGreeters[0] == self))
        {
            blog("terminal.greeter.getConversableGreeter: No other greeters found. Removing variable from controller.");
            removeObjVar(controller, FOUND_OTHER_GREETERS);
            return null;
        }
        blog("terminal.greeter.getConversableGreeter: At least one other greeters found! #: " + allGreeters.length);
        Vector greeterList = new Vector();
        for (obj_id allGreeter : allGreeters) {
            if (allGreeter != self && isValidId(allGreeter) && exists(allGreeter)) {
                greeterList.add(allGreeter);
            }
        }
        if (greeterList.size() < 0)
        {
            return null;
        }
        obj_id[] newGreeterList = new obj_id[greeterList.size()];
        greeterList.toArray(newGreeterList);
        if (newGreeterList.length == 1)
        {
            return newGreeterList[0];
        }
        blog("terminal.greeter.getConversableGreeter: New list of greeters is more than 1 in size");
        return newGreeterList[rand(0, newGreeterList.length - 1)];
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
