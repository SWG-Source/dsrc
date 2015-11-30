package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.skill;
import script.library.utils;
import script.library.prose;

public class terminal_skill extends script.terminal.base.terminal_add_use
{
    public terminal_skill()
    {
    }
    public static final String SKILL_TBL = "datatables/skill/skills.iff";
    public static final String SKILL_TERMINAL_TBL = "datatables/skill/skill_terminal.iff";
    public static final string_id SID_TERMINAL_PROMPT = new string_id("skill_teacher", "skill_terminal_prompt");
    public static final string_id SID_TERMINAL_TITLE = new string_id("skill_teacher", "skill_terminal_title");
    public static final string_id SID_TERMINAL_DISABLED = new string_id("skill_teacher", "skill_terminal_disabled");
    public static final string_id SID_TERMINAL_DENIED = new string_id("skill_teacher", "skill_terminal_denied");
    public static final string_id SID_TERMINAL_MAX_SKILLS = new string_id("skill_teacher", "skill_terminal_max_skills");
    public static final string_id PROSE_GRANT_SKILL = new string_id("skill_teacher", "skill_terminal_grant");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "Correspondent Terminal");
        return SCRIPT_CONTINUE;
    }
}
