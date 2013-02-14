package net.java.dev.cejug.classifieds.admin.controller.domain
{
    import mx.collections.ArrayCollection;
    import mx.collections.ListCollectionView;
    import mx.controls.Alert;
    import mx.controls.List;
    import mx.events.CloseEvent;
    import mx.events.FlexEvent;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.mxml.RemoteObject;
    
    import net.java.dev.cejug.classifieds.admin.AdminService;
    import net.java.dev.cejug.classifieds.admin.view.domain.domain;
    import net.java.dev.cejug.classifieds.admin.util.MessageUtils;
    import net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory;
    import net.java.dev.cejug.classifieds.server.contract.BundleRequest;
    import net.java.dev.cejug.classifieds.server.contract.CategoryCollection;
    import net.java.dev.cejug.classifieds.server.contract.CreateDomainParam;
    import net.java.dev.cejug.classifieds.server.contract.DeleteDomainParam;
    import net.java.dev.cejug.classifieds.server.contract.Domain;
    import net.java.dev.cejug.classifieds.server.contract.ServiceStatus;
    import net.java.dev.cejug.classifieds.server.contract.UpdateDomainParam;
    
	public class DomainController
	{
	    private var domainReference:domain;
        private var adminService:RemoteObject;

        [Bindable]
        public var domainEntity:Domain;

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.Domain")]
        public var domainDataProvider:ArrayCollection = new ArrayCollection();

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
        public var domainCategoryDataProvider:ArrayCollection = new ArrayCollection();

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
        public var notInDomainCategoryDataProvider:ListCollectionView = new ArrayCollection();
                
        private var serviceStatus:ServiceStatus;

		public function DomainController()
		{
			adminService = new AdminService().getRemoteObject();
			adminService.readDomainBundleOperation.addEventListener("result", getAllDomainsResult);
			adminService.createDomainOperation.addEventListener("result", saveDomainResult);
			adminService.updateDomainOperation.addEventListener("result", saveDomainResult);
			adminService.deleteDomainOperation.addEventListener("result", saveDomainResult);
			adminService.readCategoryBundleOperation.addEventListener("result", loadCategoriesResult);
			adminService.addEventListener("fault", onRemoteFault);
		}

        public function init(event:FlexEvent):void {
            domainReference = event.target as domain;
            readAllDomain();
        }

        public function resetState():void {
            cleanFields();
            domainReference.currentState = "";
        }

        private function cleanFields():void {
            domainReference.fNewDomainName.text = "";
            domainReference.fNewDomainBrand.text = "";
            domainReference.fNewDomainSharedQuota.selected = false;
            // TODO timezone and categories
        }

        /**
         * Handles errors when calling the remote operations.
         */
        private function onRemoteFault(event:FaultEvent):void {
            MessageUtils.showError(event.fault.faultString);
        }

        /**
         * Loads all the domains.
         */
        public function readAllDomain():void {
            var params:BundleRequest = new BundleRequest();
            adminService.readDomainBundleOperation(params);
        }
        
        /**
         * Handles the result of loading all the domains.
         */
        private function getAllDomainsResult(event:ResultEvent):void {
            domainDataProvider = event.result as ArrayCollection;
        }

        /**
         * Create a new domain.
         */
        public function createDomain():void {
            var domain:Domain = new Domain();
            domain.uri = domainReference.fNewDomainName.text;
            domain.brand = domainReference.fNewDomainBrand.text;
            domain.sharedQuota = domainReference.fNewDomainSharedQuota.selected;

            if (domain.advCategory == null) {
                domain.advCategory = new ArrayCollection();
            } else {
                domain.advCategory.removeAll();
            }
            for (var i:int = 0; i < domainCategoryDataProvider.length; i++) {
                domain.advCategory.addItem(domainCategoryDataProvider.getItemAt(i));
            }

            var param:CreateDomainParam = new CreateDomainParam();
            param.domain = domain;
            adminService.createDomainOperation(param);
        }

        /**
         * Updates a domain.
         */
        public function updateDomain():void {
            var domain:Domain = new Domain();
            domain.entityId = domainEntity.entityId;
            domain.uri = domainReference.fUpdateDomainName.text;
            domain.brand = domainReference.fUpdateDomainBrand.text;
            domain.sharedQuota = domainReference.fUpdateDomainSharedQuota.selected;

            if (domain.advCategory == null) {
                domain.advCategory = new ArrayCollection();
            } else {
                domain.advCategory.removeAll();
            }
            for (var i:int = 0; i < domainCategoryDataProvider.length; i++) {
                domain.advCategory.addItem(domainCategoryDataProvider.getItemAt(i));
            }
                
            var param:UpdateDomainParam = new UpdateDomainParam();
            param.domain = domain;
            adminService.updateDomainOperation(param);
        }

        /**
         * Confirms to delete a domain.
         */
        public function confirmDeleteDomain():void {
            var row:int = domainReference.dgDomains.selectedIndex;

            if (row >= 0) {
                domainEntity = domainDataProvider.getItemAt(row) as Domain;
                MessageUtils.showQuestion("Delete selected domain ["+ domainEntity.uri + "] ?", deleteDomain);
            }
        }
        /**
         * Delete a domain.
         */
        public function deleteDomain(event:CloseEvent):void {
            if (event.detail == Alert.YES) {
                var row:int = domainReference.dgDomains.selectedIndex;
    
                if (row >= 0) {
                    domainEntity = domainDataProvider.getItemAt(row) as Domain;
                    var param:DeleteDomainParam = new DeleteDomainParam();
                    param.primaryKey = domainEntity.entityId;
                    adminService.deleteDomainOperation(param);
                }
            }
        }

        /**
         * Handles the result of saving a domain (Create, Update or Delete).
         */
        private function saveDomainResult(event:ResultEvent):void {
            domainReference.currentState = "";
            readAllDomain();
        }

        /**
         * Loads the screen to create a new domain.
         */
        public function loadNewDomain():void {
            domainReference.currentState = "createDomain";
            loadCategories();

            domainEntity = new Domain();
            domainCategoryDataProvider = new ArrayCollection();
            loadCategories();

            cleanFields();
        }

        /**
         * Loads the screen to update a domain.
         */
        public function loadUpdateDomain():void {

            var row:int = domainReference.dgDomains.selectedIndex;

            if (row >= 0) {
                domainEntity = domainDataProvider.getItemAt(row) as Domain;
                var collection:ArrayCollection = domainEntity.advCategory as ArrayCollection;
                domainCategoryDataProvider.removeAll();
                for (var i:int = 0; i < collection.length; i++) {
                    domainCategoryDataProvider.addItem(collection.getItemAt(i));
                }
                
                loadCategories();
                domainReference.currentState = "updateDomain";
            }
        }

        /**
         * Removes the categories associated to the domain from the notInDomainCategoryDataProvider.
         */
        private function removeCategories():void {
            // TODO: Improve this search
            var categoryInDomain: AdvertisementCategory;
            var categoryNotInDomain: AdvertisementCategory;
            for (var i:int = 0; i < notInDomainCategoryDataProvider.length; i++) {
                categoryNotInDomain = notInDomainCategoryDataProvider.getItemAt(i) as AdvertisementCategory;
                for (var j:int = 0; j < domainCategoryDataProvider.length; j++) {
                    categoryInDomain = domainCategoryDataProvider.getItemAt(j) as AdvertisementCategory;
                    if (categoryNotInDomain.entityId == categoryInDomain.entityId) {
                        notInDomainCategoryDataProvider.removeItemAt(i);
                        i--;
                        break;
                    }
                }
            }            
        }

        /**
         * Loads all the categories.
         */
        private function loadCategories():void {
            var params:BundleRequest = new BundleRequest();
            adminService.readCategoryBundleOperation(params);
        }

        /**
         * Gets the result of loading all the categories
         */
        private function loadCategoriesResult(event:ResultEvent):void {
            var catCol:CategoryCollection = event.result as CategoryCollection;
            if (catCol != null && catCol.advCategory != null) {
                notInDomainCategoryDataProvider = catCol.advCategory;
            } else {
                notInDomainCategoryDataProvider = new ArrayCollection();
            }

            removeCategories();
        }

        /**
         * Adds the selected categories to the domain
         */
        public function addCategory(list:List):void {
            var selIndices:Array = list.selectedIndices;
            if (selIndices != null && selIndices.length > 0) {
                for (var i:int = 0; i < selIndices.length; i++) { 
                   domainCategoryDataProvider.addItem(notInDomainCategoryDataProvider.getItemAt(selIndices[i]));
                }
                for (var j:int = 0; j < selIndices.length; j++) { 
                   notInDomainCategoryDataProvider.removeItemAt(selIndices[j]);
                }
            }
        }

        /**
         * Removes the selected categories from the domain
         */
        public function removeCategory(list:List):void {
            var selIndices:Array = list.selectedIndices;
            if (selIndices != null && selIndices.length > 0) {
                for (var i:int = 0; i < selIndices.length; i++) { 
                   notInDomainCategoryDataProvider.addItem(domainCategoryDataProvider.getItemAt(selIndices[i]));
                }
                for (var j:int = 0; j < selIndices.length; j++) { 
                   domainCategoryDataProvider.removeItemAt(selIndices[j]);
                }
            }
        }

        /**
         * Adds all the categories to the domain
         */
        public function addAllCategory():void {
            for (var i:int = 0; i < notInDomainCategoryDataProvider.length; i++) {
                domainCategoryDataProvider.addItem(notInDomainCategoryDataProvider.getItemAt(i));
            }
            notInDomainCategoryDataProvider.removeAll();
        }

        /**
         * Removes all the categories from the domain
         */
        public function removeAllCategory():void {
            for (var i:int = 0; i < domainCategoryDataProvider.length; i++) {
                notInDomainCategoryDataProvider.addItem(domainCategoryDataProvider.getItemAt(i));
            }
            domainCategoryDataProvider.removeAll();
        }

        private function handleServiceStatus(serviceStatus:ServiceStatus):void {
            switch(serviceStatus.statusCode) {
                case 200: //MessageUtils.showInfo(serviceStatus.description);
                          domainReference.currentState = "";
                          readAllDomain();
                          break;
                case 500: MessageUtils.showError(serviceStatus.description); break;
                default: MessageUtils.showInfo(serviceStatus.description); break;
            }
        }
	}
}