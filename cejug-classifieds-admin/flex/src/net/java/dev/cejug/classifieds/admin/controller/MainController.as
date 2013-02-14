package net.java.dev.cejug.classifieds.admin.controller
{
    import mx.collections.*;
    import mx.controls.Alert;
    import mx.events.FlexEvent;
    import mx.events.MenuEvent;
    import net.java.dev.cejug.classifieds.admin.view.main;

    /**
     * This class contains the methods triggered from action in the main.mxml screen, mainly the menu actions.
     */
	public class MainController
	{
	    /** Reference to the main.mxml screen. */
		private var mainReference:main;

        /** Data to build the menu. */
        [Bindable]
        public var menuBarCollection:XMLListCollection;

        /*
         * Contains the XML information to build the menu. The state attribute 
         * contains the state name in the main screen to be whown when clicking the menu.
         */
        private var menubarXML:XMLList =<>
            <menuitem label="CRUDs">
                <menuitem label="Advertisement Type" state="advtype" />
                <menuitem label="Domain" state="domain" />
                <menuitem label="Category" state="category" />
            </menuitem>
            <menuitem label="Management">
                <menuitem label="Archive Advertisements" state="advertisement" />
                <menuitem label="Check Monitor" state="monitor" />
            </menuitem>
            </>;

        /** Default constructor. */
        public function MainController() {
            
        }

        /**
         * Class initialization triggered when the screen is loaded.
         * Builds the menu.
         * @param event Flex event triggered by screen loading.
         */
        public function init(event:FlexEvent):void {
            mainReference = event.target as main;
            menuBarCollection = new XMLListCollection(menubarXML);
        }

        /**
         * Event handler for the MenuBar control's itemClick event.
         * Selects the right state to show in the main screen when a menu item is clicked.
         * @param event Event triggered when a menu item is clicked.
         */
        public function itemClickHandler(event:MenuEvent):void {
            var state:String = menubarXML.menuitem.(@label == event.label).@state;
            mainReference.currentState = state;
        }
	}
}