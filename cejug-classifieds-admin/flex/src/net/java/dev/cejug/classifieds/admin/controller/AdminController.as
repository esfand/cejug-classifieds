package net.java.dev.cejug.classifieds.admin.controller
{
	import net.java.dev.cejug.classifieds.admin.view.admin;
	import mx.events.FlexEvent;

    /**
     * This class contains the methods triggered from action in the admin.mxml screen.
     */
	public class AdminController
	{
	    /** Reference to the admin.mxml screen. */
		public static var adminReference:admin;
		
		/** Indicates if the login information (username) must be shown in the screen. */
        [Bindable]
        public static var loginInfoVisible:Boolean = false;

        /**
         * Default constructor.
         */
		public function AdminController()
		{
		}
		
		/**
		 * Class initialization triggered when the screen is loaded.
		 * @param event Flex event triggered by screen loading.
		 */
        public function init(event:FlexEvent):void{
            adminReference = event.target as admin;
        }

        /**
         * Take necessary actions for user logout.
         */
        public function logout():void {
            adminReference.mainScreen.currentState = "";
        	adminReference.currentState = "login";
        	loginInfoVisible = false;
        }
	}
}