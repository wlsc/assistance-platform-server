package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization;

import org.json4s.jackson.Json;

import com.google.gson.Gson;

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
