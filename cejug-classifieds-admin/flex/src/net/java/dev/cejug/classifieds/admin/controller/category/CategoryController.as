package net.java.dev.cejug.classifieds.admin.controller.category
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
    import net.java.dev.cejug.classifieds.admin.view.category.category;
    import net.java.dev.cejug.classifieds.admin.util.MessageUtils;
    import net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory;
    import net.java.dev.cejug.classifieds.server.contract.BundleRequest;
    import net.java.dev.cejug.classifieds.server.contract.CategoryCollection;
    import net.java.dev.cejug.classifieds.server.contract.CreateCategoryParam;
    import net.java.dev.cejug.classifieds.server.contract.DeleteCategoryParam;
    import net.java.dev.cejug.classifieds.server.contract.ServiceStatus;
    import net.java.dev.cejug.classifieds.server.contract.UpdateCategoryParam;
    
	public class CategoryController
	{
	    private var categoryReference:category;
        private var adminService:RemoteObject;
        private var adminAuxService:RemoteObject;

        [Bindable]
        public var categoryEntity:AdvertisementCategory;

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
        public var categoryDataProvider:ListCollectionView = new ArrayCollection();

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
        public var parentDataProvider:ListCollectionView = new ArrayCollection();
        
        private var serviceStatus:ServiceStatus;

		public function CategoryController()
		{
			adminService = new AdminService().getRemoteObject();
			adminService.readCategoryBundleOperation.addEventListener("result", getAllCategoriesResult);
			adminService.createCategoryOperation.addEventListener("result", saveCategoryResult);
			adminService.updateCategoryOperation.addEventListener("result", saveCategoryResult);
			adminService.deleteCategoryOperation.addEventListener("result", saveCategoryResult);
			adminService.addEventListener("fault", onRemoteFault);
			
			adminAuxService = new AdminService().getRemoteObject();
            adminAuxService.readCategoryBundleOperation.addEventListener("result", getParentResult);
            adminAuxService.addEventListener("fault", onRemoteFault);
		}

        public function init(event:FlexEvent):void {
            categoryReference = event.target as category;
            readAllCategory();
        }

        public function resetState():void {
            cleanFields();
            categoryReference.currentState = "";
        }

        private function cleanFields():void {
            categoryReference.fNewCategoryName.text = "";
            categoryReference.fNewCategoryDescription.text = "";
            categoryReference.fNewCategoryParent.selectedIndex = 0;
        }

        /**
         * Handles errors when calling the remote operations.
         */
        private function onRemoteFault(event:FaultEvent):void {
            MessageUtils.showError(event.fault.faultString);
        }

        /**
         * Loads all the categories.
         */
        public function readAllCategory():void {
            var params:BundleRequest = new BundleRequest();
            adminService.readCategoryBundleOperation(params);
        }
        
        /**
         * Handles the result of loading all the categories.
         */
        private function getAllCategoriesResult(event:ResultEvent):void {
            var catCol:CategoryCollection = event.result as CategoryCollection;
            if (catCol != null) {
                categoryDataProvider = catCol.advCategory;
            } else {
                categoryDataProvider = new ArrayCollection();
            }
        }
        
        /**
         * Loads all the parents categories.
         */
        public function getParents():void {
            var params:BundleRequest = new BundleRequest();
            adminAuxService.readCategoryBundleOperation(params);
        }
        
        /**
         * Handles the result of loading all the parents categories.
         */
        private function getParentResult(event:ResultEvent):void {

            var item:Object = new Object();
            item['name'] = 'Select...';

            var catCol:CategoryCollection = event.result as CategoryCollection;
            if (catCol != null && catCol.advCategory != null) {
                parentDataProvider = catCol.advCategory;
                parentDataProvider.addItemAt(item, 0);
            } else {
                parentDataProvider = new ArrayCollection();
                parentDataProvider.addItem(item);
            }




            if (categoryEntity != null) {
                removeCurrentFromParent();
                selectParent();
            } else {
                categoryReference.fNewCategoryParent.selectedIndex = 0;
            }
        }

        private function selectParent():void {
            var index:int = 0;
            if (categoryEntity != null && categoryEntity.advSubCategory != null) {
                for (var i:int = 1; i < parentDataProvider.length; i++) {
                    if (parentDataProvider[i].entityId == categoryEntity.advSubCategory.entityId) {
                        index = i;
                        break;
                    }
                }
            }
            categoryReference.fUpdateCategoryParent.selectedIndex = index;
        }

        private function removeCurrentFromParent():void {
            for (var i:int = 0; i < parentDataProvider.length; i++) {
                if (parentDataProvider[i].entityId == categoryEntity.entityId) {
                    parentDataProvider.removeItemAt(i);
                    break;
                }
            }
        }
        /**
         * Create a new category.
         */
        public function createCategory():void {
            var advertisementCategory:AdvertisementCategory = new AdvertisementCategory();
            advertisementCategory.name = categoryReference.fNewCategoryName.text;
            advertisementCategory.description = categoryReference.fNewCategoryDescription.text;
            var index:int = categoryReference.fNewCategoryParent.selectedIndex; 
            if (index > 0) {
                advertisementCategory.advSubCategory = parentDataProvider.getItemAt(index) as AdvertisementCategory; 
            }
            var param:CreateCategoryParam = new CreateCategoryParam();
            param.advCategory = advertisementCategory;
            adminService.createCategoryOperation(param);
        }

        /**
         * Updates a category.
         */
        public function updateCategory():void {
            var advertisementCategory:AdvertisementCategory = new AdvertisementCategory();
            advertisementCategory.entityId = categoryEntity.entityId;
            advertisementCategory.name = categoryReference.fUpdateCategoryName.text;
            advertisementCategory.description = categoryReference.fUpdateCategoryDescription.text;
            var index:int = categoryReference.fUpdateCategoryParent.selectedIndex; 
            if (index > 0) {
                advertisementCategory.advSubCategory = parentDataProvider.getItemAt(index) as AdvertisementCategory; 
            }
            var param:UpdateCategoryParam = new UpdateCategoryParam();
            param.advCategory = advertisementCategory;
            adminService.updateCategoryOperation(param);
        }

        /**
         * Confirms to delete a category.
         */
        public function confirmDeleteCategory():void {
            var row:int = categoryReference.dgCategories.selectedIndex;

            if (row >= 0) {
                categoryEntity = categoryDataProvider.getItemAt(row) as AdvertisementCategory;
                MessageUtils.showQuestion("Delete selected category ["+ categoryEntity.name + "] ?", deleteCategory);
            }
        }
        /**
         * Delete a category.
         */
        public function deleteCategory(event:CloseEvent):void {
            if (event.detail == Alert.YES) {
                var row:int = categoryReference.dgCategories.selectedIndex;
    
                if (row >= 0) {
                    categoryEntity = categoryDataProvider.getItemAt(row) as AdvertisementCategory;
                    var param:DeleteCategoryParam = new DeleteCategoryParam();
                    param.primaryKey = categoryEntity.entityId;
                    adminService.deleteCategoryOperation(param);
                }
            }
        }

        /**
         * Handles the result of saving a category (Create, Update or Delete).
         */
        private function saveCategoryResult(event:ResultEvent):void {
            categoryReference.currentState = "";
            readAllCategory();
        }

        /**
         * Loads the screen to create a new category.
         */
        public function loadNewCategory():void {
            categoryReference.currentState = "createCategory";
            categoryEntity = null; //new AdvertisementCategory();
            getParents();
            cleanFields();
        }

        /**
         * Loads the screen to update a category.
         */
        public function loadUpdateCategory():void {
            var row:int = categoryReference.dgCategories.selectedIndex;

            if (row >= 0) {
                categoryReference.currentState = "updateCategory";
                categoryEntity = categoryDataProvider.getItemAt(row) as AdvertisementCategory;
                getParents();
            }
        }

        private function handleServiceStatus(serviceStatus:ServiceStatus):void {
            switch(serviceStatus.statusCode) {
                case 200: //MessageUtils.showInfo(serviceStatus.description);
                          categoryReference.currentState = "";
                          readAllCategory();
                          break;
                case 500: MessageUtils.showError(serviceStatus.description); break;
                default: MessageUtils.showInfo(serviceStatus.description); break;
            }
        }
	}
}