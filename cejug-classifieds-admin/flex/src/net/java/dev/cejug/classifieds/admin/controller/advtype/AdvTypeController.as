package net.java.dev.cejug.classifieds.admin.controller.advtype
{
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.events.CloseEvent;
    import mx.events.FlexEvent;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.mxml.RemoteObject;
    
    import net.java.dev.cejug.classifieds.admin.AdminService;
    import net.java.dev.cejug.classifieds.admin.view.advtype.advtype;
    import net.java.dev.cejug.classifieds.admin.util.MessageUtils;
    import net.java.dev.cejug.classifieds.server.contract.AdvertisementType;
    import net.java.dev.cejug.classifieds.server.contract.BundleRequest;
    import net.java.dev.cejug.classifieds.server.contract.CreateAdvertisementTypeParam;
    import net.java.dev.cejug.classifieds.server.contract.DeleteAdvertisementTypeParam;
    import net.java.dev.cejug.classifieds.server.contract.ServiceStatus;
    import net.java.dev.cejug.classifieds.server.contract.UpdateAdvertisementTypeParam;
    
    public class AdvTypeController
    {
        private var advtypeReference:advtype;
        private var adminService:RemoteObject;

        [Bindable]
        public var advTypeEntity:AdvertisementType;

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementType")]
        public var advtypeDataProvider:ArrayCollection = new ArrayCollection();

        private var serviceStatus:ServiceStatus;

        public function AdvTypeController()
        {
            adminService = new AdminService().getRemoteObject();
            adminService.readAdvertisementTypeBundleOperation.addEventListener("result", getAllAdvTypesResult);
            adminService.createAdvertisementTypeOperation.addEventListener("result", saveAdvTypeResult);
            adminService.updateAdvertisementTypeOperation.addEventListener("result", saveAdvTypeResult);
            adminService.deleteAdvertisementTypeOperation.addEventListener("result", saveAdvTypeResult);
            adminService.addEventListener("fault", onRemoteFault);
        }

        public function init(event:FlexEvent):void {
            advtypeReference = event.target as advtype;
            readAllAdvType();
        }

        public function resetState():void {
            cleanFields();
            advtypeReference.currentState = "";
        }

        private function cleanFields():void {
            advtypeReference.fNewAdvTypeName.text = "";
            advtypeReference.fNewAdvTypeDescription.text = "";
            advtypeReference.fNewAdvTypeMaxAttSize.text = "";
            advtypeReference.fNewAdvTypeTextLength.text = "";
        }

        /**
         * Handles errors when calling the remote operations.
         */
        private function onRemoteFault(event:FaultEvent):void {
            MessageUtils.showError(event.fault.faultString);
        }

        /**
         * Loads all the advertisement types.
         */
        public function readAllAdvType():void {
            var params:BundleRequest = new BundleRequest();
            adminService.readAdvertisementTypeBundleOperation(params);
        }
        
        /**
         * Handles the result of loading all the advertisement types.
         */
        private function getAllAdvTypesResult(event:ResultEvent):void {
            advtypeDataProvider = event.result as ArrayCollection; 
        }

        /**
         * Create a new advertisement type.
         */
        public function createAdvType():void {
            var advertisementType:AdvertisementType = new AdvertisementType();
            advertisementType.name = advtypeReference.fNewAdvTypeName.text;
            advertisementType.description = advtypeReference.fNewAdvTypeDescription.text;
            if (advtypeReference.fNewAdvTypeMaxAttSize.text.length > 0) {
                advertisementType.maxAttachmentSize = Number(advtypeReference.fNewAdvTypeMaxAttSize.text);
            }
            if (advtypeReference.fNewAdvTypeTextLength.text.length > 0) {
                advertisementType.maxTextLength = Number(advtypeReference.fNewAdvTypeTextLength.text);
            }
            
            var param:CreateAdvertisementTypeParam = new CreateAdvertisementTypeParam();
            param.advertisementType = advertisementType;
            adminService.createAdvertisementTypeOperation(param);
        }

        /**
         * Updates an advertisement type.
         */
        public function updateAdvType():void {
            var advertisementType:AdvertisementType = new AdvertisementType();
            advertisementType.entityId = advTypeEntity.entityId;
            advertisementType.name = advtypeReference.fUpdateAdvTypeName.text;
            advertisementType.description = advtypeReference.fUpdateAdvTypeDescription.text;
            
            if (advtypeReference.fUpdateAdvTypeMaxAttSize.text.length > 0) {
                advertisementType.maxAttachmentSize = Number(advtypeReference.fUpdateAdvTypeMaxAttSize.text);
            }
            if (advtypeReference.fUpdateAdvTypeTextLength.text.length > 0) {
                advertisementType.maxTextLength = Number(advtypeReference.fUpdateAdvTypeTextLength.text);
            }
            
            
            var param:UpdateAdvertisementTypeParam = new UpdateAdvertisementTypeParam();
            param.advertisementType = advertisementType;
            adminService.updateAdvertisementTypeOperation(param);
        }

        /**
         * Confirms to delete an advertisement type.
         */
        public function confirmDeleteAdvType():void {
            var row:int = advtypeReference.dgAdvType.selectedIndex;

            if (row >= 0) {
                advTypeEntity = advtypeDataProvider.getItemAt(row) as AdvertisementType;
                MessageUtils.showQuestion("Delete selected advertisement type ["+ advTypeEntity.name + "] ?", deleteAdvType);
            }
        }

        /**
         * Delete an advertisement type.
         */
        public function deleteAdvType(event:CloseEvent):void {
            if (event.detail == Alert.YES) {
                var row:int = advtypeReference.dgAdvType.selectedIndex;
    
                if (row >= 0) {
                    advTypeEntity = advtypeDataProvider.getItemAt(row) as AdvertisementType;
                    var param:DeleteAdvertisementTypeParam = new DeleteAdvertisementTypeParam();
                    param.primaryKey = advTypeEntity.entityId;
                    adminService.deleteAdvertisementTypeOperation(param);
                }
            }
        }

        /**
         * Handles the result of saving an advertisement type (Create, Update or Delete).
         */
        private function saveAdvTypeResult(event:ResultEvent):void {
            advtypeReference.currentState = "";
            readAllAdvType();
        }

        /**
         * Loads the screen to create a new advertisement type.
         */
        public function loadNewAdvType():void {
            advtypeReference.currentState = "createAdvType";
            advTypeEntity = new AdvertisementType();
            cleanFields();
        }

        /**
         * Loads the screen to update an advertisement type.
         */
        public function loadUpdateAdvType():void {
            advtypeReference.currentState = "updateAdvType";
            var row:int = advtypeReference.dgAdvType.selectedIndex;

            if (row >= 0) {
                advTypeEntity = advtypeDataProvider.getItemAt(row) as AdvertisementType;
            }
        }

        private function handleServiceStatus(serviceStatus:ServiceStatus):void {
            switch(serviceStatus.statusCode) {
                case 200: //MessageUtils.showInfo(serviceStatus.description);
                          advtypeReference.currentState = "";
                          readAllAdvType();
                          break;
                case 500: MessageUtils.showError(serviceStatus.description); break;
                default: MessageUtils.showInfo(serviceStatus.description); break;
            }
        }
    }
}