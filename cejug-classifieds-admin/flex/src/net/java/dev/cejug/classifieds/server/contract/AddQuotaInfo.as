/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Sun Jul 06 16:42:58 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package net.java.dev.cejug.classifieds.server.contract {

    [Bindable]
    [RemoteClass(alias="net.java.dev.cejug.classifieds.server.contract.AddQuotaInfo")]
    public class AddQuotaInfo {

        private var _notifyDomainOwner:Boolean;
        private var _quota:Quota;

        public function set notifyDomainOwner(value:Boolean):void {
            _notifyDomainOwner = value;
        }
        public function get notifyDomainOwner():Boolean {
            return _notifyDomainOwner;
        }

        public function set quota(value:Quota):void {
            _quota = value;
        }
        public function get quota():Quota {
            return _quota;
        }
    }
}