package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization;

import com.google.gson.Gson;

/**
 * Implements JSON Serialization againts the MessageSerialization interface.
 * @author bjeutter
 *
 */
public class JsonSerialization implements MessageSerialization {

	@Override
	public <T> byte[] serialize(T data) {
		return new Gson().toJson(data).getBytes();
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> type) {
		return new Gson().fromJson(new String(data), type);
	}

}
