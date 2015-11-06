package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization;

/**
 * This interface defines the functions to serialize / deserialize messages used in the messaging service(s).
 */
public interface MessageSerialization {
	<T> byte[] serialize(T data);
	<T> T deserialize(byte[] data, Class<T> type);
}