/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Sun Jul 06 16:42:59 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package net.java.dev.cejug.classifieds.server.contract {

    [Bindable]
    [RemoteClass(alias="net.java.dev.cejug.classifieds.server.contract.ReadCategoryBundleParam")]
    public class ReadCategoryBundleParam {

        private var _bundleRequest:BundleRequest;

        public function set bundleRequest(value:BundleRequest):void {
            _bundleRequest = value;
        }
        public function get bundleRequest():BundleRequest {
            return _bundleRequest;
        }

    }
}