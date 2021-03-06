package vg.civcraft.mc.civmodcore.serialization;

/**
 * Class to be used during testing as an example serializable.
 */
public final class ExampleSerializable extends NBTCache {

	private static final String MESSAGE_KEY = "message";

	/**
	 * Gets the message stored within the NBT directly.
	 *
	 * @return Returns the message stored within this class' NBT.
	 */
	public String getMessage() {
		return this.nbt.getString(MESSAGE_KEY);
	}

	/**
	 * Assigns a message to this class' NBT.
	 *
	 * @param message The message to assign.
	 */
	public void setMessage(String message) {
		this.nbt.setString(MESSAGE_KEY, message);
	}

}
