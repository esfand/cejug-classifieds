package net.java.dev.cejug.classifieds.admin.controller.advertisement
{
    import mx.collections.ArrayCollection;
    import mx.collections.ListCollectionView;
    import mx.controls.Alert;
    import mx.events.CloseEvent;
    import mx.events.FlexEvent;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.mxml.RemoteObject;
    
    import net.java.dev.cejug.classifieds.admin.AdminService;
    import net.java.dev.cejug.classifieds.admin.view.advertisement.advertisement;
    import net.java.dev.cejug.classifieds.admin.util.MessageUtils;
    import net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory;
    import net.java.dev.cejug.classifieds.server.contract.CategoryCollection;
    import net.java.dev.cejug.classifieds.server.contract.AdvertisementRef;
    import net.java.dev.cejug.classifieds.server.contract.ReadAdvertisementReferencesParam;
    import net.java.dev.cejug.classifieds.server.contract.AdvertisementRefBundle;
    import net.java.dev.cejug.classifieds.server.contract.BundleRequest;
    import net.java.dev.cejug.classifieds.server.contract.ServiceStatus;
    
    public class AdvController
    {
        private var advReference:advertisement;
        private var adminService:RemoteObject;

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
        public var categoryDataProvider:ListCollectionView = new ArrayCollection();

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementRef")]
        public var advertisementDataProvider:ArrayCollection = new ArrayCollection();

        private var serviceStatus:ServiceStatus;

        public function AdvController()
        {
            adminService = new AdminService().getRemoteObject();
            adminService.readCategoryBundleOperation.addEventListener("result", getAllCategoriesResult);
            adminService.readAdvertisementReferencesOperation.addEventListener("result", searchAdvertisementsResult);
            adminService.updateAdvertisementStatusOperation.addEventListener("result", updateAdvertisementsResult);
            adminService.addEventListener("fault", onRemoteFault);
        }

        public function init(event:FlexEvent):void {
            advReference = event.target as advertisement;
            readAllCategory();
        }

        public function resetState():void {
            cleanFields();
        }

        private function cleanFields():void {
            advReference.fCategory.selectedIndex = 0;
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
        public function readAllCategory():void {
            var params:BundleRequest = new BundleRequest();
            adminService.readCategoryBundleOperation(params);
        }
        
        /**
         * Handles the result of loading all the categories.
         */
        private function getAllCategoriesResult(event:ResultEvent):void {
            var item:Object = new Object();
            item['name'] = 'Select...';
            
            var catCol:CategoryCollection = event.result as CategoryCollection;
            if (catCol != null) {
                categoryDataProvider = catCol.advCategory;
                categoryDataProvider.addItemAt(item, 0);
            } else {
                categoryDataProvider = new ArrayCollection();
                categoryDataProvider.addItem(item);
            }
            advReference.fCategory.selectedIndex = 0;
        }

        /**
         * Confirms to delete the selected advertisements.
         */
        public function confirmDeleteAdvertisement():void {
            var advs:Array = advReference.dgAdvertisement.selectedIndices;

            if (advs.length >= 0) {
                MessageUtils.showQuestion("Archive selected advertisements ?", deleteAdvertisements);
            }
        }

        /**
         * Delete an advertisement type.
         */
        public function deleteAdvertisements(event:CloseEvent):void {
            if (event.detail == Alert.YES) {
                var advs:Array = advReference.dgAdvertisement.selectedIndices;
    
                if (advs.length >= 0) {
                    var bundle:AdvertisementRefBundle = new AdvertisementRefBundle();
                    bundle.advertisementRef = new ArrayCollection();
                    var ref:AdvertisementRef;
                    for (var i:int = 0; i < advs.length; i++) {
                        
                        ref = advertisementDataProvider.getItemAt(advs[i]) as AdvertisementRef;
                        bundle.advertisementRef.addItem(ref);
                    }
                    
                    adminService.updateAdvertisementStatusOperation(bundle);
                }
            }
        }

        /**
         * Searches the advertisements.
         */
        public function searchAdvertisements():void {
            var index:int = advReference.fCategory.selectedIndex;
            var selectedCategory:AdvertisementCategory = null; 
            var param:ReadAdvertisementReferencesParam = new ReadAdvertisementReferencesParam();
            if (index > 0) {
                selectedCategory = categoryDataProvider.getItemAt(index) as AdvertisementCategory;
                param.primaryKey = selectedCategory.entityId;
                adminService.readAdvertisementReferencesOperation(param);
            } else {
                MessageUtils.showError("Select one category.");
            }
        }
        
        /**
         * Handles the result of searching advertisements.
         */
        private function searchAdvertisementsResult(event:ResultEvent):void {
            advertisementDataProvider = event.result as ArrayCollection;
            if (advertisementDataProvider == null || advertisementDataProvider.length == 0) {
                MessageUtils.showError("No advertisements found.");
            }
        }

        /**
         * Handles the result of updating advertisements. 
         */
        private function updateAdvertisementsResult(event:ResultEvent):void {
            var serviceStatus:ServiceStatus = event.result as ServiceStatus;
            if (serviceStatus.statusCode == 200) {
                MessageUtils.showInfo("Advertisements archived");
            } 
        }

        private function handleServiceStatus(serviceStatus:ServiceStatus):void {
            switch(serviceStatus.statusCode) {
                case 200: //MessageUtils.showInfo(serviceStatus.description);
                          break;
                case 500: MessageUtils.showError(serviceStatus.description); break;
                default: MessageUtils.showInfo(serviceStatus.description); break;
            }
        }
    }
}