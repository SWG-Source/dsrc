package script.systems.image_designer;

import script.library.utils;
import script.obj_id;

public class image_designer_cancel extends script.base_script
{
    public image_designer_cancel()
    {
    }
    public static final String VAR_IMAGE_DESIGN_CONFIRM = "image_designer.confirm";
    public static final String SCRIPT_IMAGE_DESIGNER_CANCEL = "systems.image_designer.image_designer_cancel";
    public static final String VAR_IMAGE_DESIGN_LD = "image_designer.linkdeath";
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (isIdValid(self))
        {
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_CONFIRM);
        }
        if (isIdValid(self))
        {
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_LD);
        }
        if (isIdValid(self))
        {
            detachScript(self, SCRIPT_IMAGE_DESIGNER_CANCEL);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnImageDesignCanceled(obj_id self) throws InterruptedException
    {
        if (isIdValid(self))
        {
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_CONFIRM);
        }
        if (isIdValid(self))
        {
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_LD);
        }
        if (isIdValid(self))
        {
            detachScript(self, SCRIPT_IMAGE_DESIGNER_CANCEL);
        }
        return SCRIPT_CONTINUE;
    }
}
