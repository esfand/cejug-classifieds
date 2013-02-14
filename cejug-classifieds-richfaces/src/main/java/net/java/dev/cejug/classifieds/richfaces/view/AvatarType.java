package net.java.dev.cejug.classifieds.richfaces.view;

public enum AvatarType {
	IMAGE("I"), URL("U"), GRAVATAR("G");

	private String type;

	AvatarType(final String type) {

		this.type = type;
	}

	public String getType() {

		return type;
	}

	public static AvatarType getAvatarType(final String type) {
		AvatarType avType = null;
		for (AvatarType avatarType : AvatarType.values()) {
			if (avatarType.getType().equals(type)) {
				avType = avatarType;
				break;
			}
		}

		return avType;
	}

}
