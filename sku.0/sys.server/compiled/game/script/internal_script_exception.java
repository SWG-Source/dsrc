/*
 Title:        internal_script_exception
 Description:  exception class to be caught inside our C code
 */

package script;

class internal_script_exception extends Exception
{
	internal_script_exception()
	{
	}

	internal_script_exception(String message)
	{
		super(message);
	}

}	// class internal_script_exception
