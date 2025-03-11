package com.rays.pro4.Bean;

public class AttributeBean extends BaseBean {

	
		private String displayName;
		private String dataType;
		private String isActive;
		private String description;
		
		
		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			this.dataType = dataType;
		}

		public String getIsActive() {
			return isActive;
		}

		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String getkey() {
			
			return  id + "";
		}



		@Override
		public String getValue() {
			return displayName;
		}

		
}


	

	