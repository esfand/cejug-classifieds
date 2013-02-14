package net.java.dev.cejug.classifieds.admin.controller.login
{
	import net.java.dev.cejug.classifieds.admin.controller.AdminController;
	import net.java.dev.cejug.classifieds.admin.view.login.login;
	import net.java.dev.cejug.classifieds.admin.AdminService;
	import net.java.dev.cejug.classifieds.admin.util.MessageUtils;
	import mx.events.FlexEvent;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import mx.rpc.remoting.mxml.RemoteObject;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;

	public class LoginController extends EventDispatcher
	{
		private var loginReference:login;
        private var adminService:RemoteObject;

        public function LoginController() {
            adminService = new AdminService().getRemoteObject();
            adminService.executeLogin.addEventListener("result", loginResult);
            adminService.addEventListener("fault", onRemoteFault);
        }

		public function doLogin():void {
		    var username:String = loginReference.username.text;
		    var password:String = loginReference.passwd.text;
		    adminService.executeLogin(username, password);
		}

        private function loginResult(event:ResultEvent):void {
            var ok:Boolean = event.result as Boolean;
            loginReference.username.text = "";
            loginReference.passwd.text = "";
            if (ok) {
                AdminController.adminReference.currentState = "main";
                AdminController.loginInfoVisible = true;
            } else {
                MessageUtils.showError("Ivalid login");
            }
        }

	    public function init(event:FlexEvent):void{
            loginReference = event.target as login;
        }

        /**
         * Handles errors when calling the remote operations.
         */
        private function onRemoteFault(event:FaultEvent):void {
            MessageUtils.showError(event.fault.faultString);
        }
	}
}