/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Sun Jul 06 16:43:01 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package net.java.dev.cejug.classifieds.server.contract {

    [Bindable]
    [RemoteClass(alias="net.java.dev.cejug.classifieds.server.contract.Voucher")]
    public class Voucher {

        private var _customerDomain:String;
        private var _customerLogin:String;
        private var _pin:String;

        public function set customerDomain(value:String):void {
            _customerDomain = value;
        }
        public function get customerDomain():String {
            return _customerDomain;
        }

        public function set customerLogin(value:String):void {
            _customerLogin = value;
        }
        public function get customerLogin():String {
            return _customerLogin;
        }

        public function set pin(value:String):void {
            _pin = value;
        }
        public function get pin():String {
            return _pin;
        }

    }
}