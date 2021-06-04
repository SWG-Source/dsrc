package script.object;

import script.library.smuggler;
import script.obj_id;

public class buyback extends script.base_script {

	/**
	 * Triggered when this item is about to be transferred from the buyback container
	 * potentially into another container (like player inventory), in which case we will
	 * first validate that the item has actually been bought back.
	 */
	public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException {

		if(hasObjVar(self, smuggler.BUYBACK_OBJ_SOLD)) {
			if(getTemplateName(destContainer).equalsIgnoreCase(smuggler.BUY_BACK_CONTROL_DEVICE_TEMPLATE)) {
				return SCRIPT_CONTINUE;
			} else {
				return SCRIPT_OVERRIDE;
			}
		}
		return SCRIPT_CONTINUE;
	}

}