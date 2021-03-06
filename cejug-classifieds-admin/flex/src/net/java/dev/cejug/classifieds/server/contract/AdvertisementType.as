/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Sun Jul 06 16:42:56 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package net.java.dev.cejug.classifieds.server.contract {

    [Bindable]
    [RemoteClass(alias="net.java.dev.cejug.classifieds.server.contract.AdvertisementType")]
    public class AdvertisementType {

        private var _description:String;
        private var _entityId:int;
        private var _maxAttachmentSize:Number;
        private var _maxTextLength:Number;
        private var _name:String;

        public function set description(value:String):void {
            _description = value;
        }
        public function get description():String {
            return _description;
        }

        public function set entityId(value:int):void {
            _entityId = value;
        }
        public function get entityId():int {
            return _entityId;
        }

        public function set maxAttachmentSize(value:Number):void {
            _maxAttachmentSize = value;
        }
        public function get maxAttachmentSize():Number {
            return _maxAttachmentSize;
        }

        public function set maxTextLength(value:Number):void {
            _maxTextLength = value;
        }
        public function get maxTextLength():Number {
            return _maxTextLength;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }
    }
}