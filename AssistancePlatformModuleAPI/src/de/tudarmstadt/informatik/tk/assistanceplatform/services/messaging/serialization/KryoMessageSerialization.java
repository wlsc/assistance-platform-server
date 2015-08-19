package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization;

import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Implements rudimentary binary object serialization via Kryo Library
 */
public class KryoMessageSerialization implements MessageSerialization {
	private Kryo kryo = new Kryo();
	
	@Override
	public <T> byte[] serialize(T data) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Output output = new Output(outputStream);
		
		kryo.writeObject(output, data);
		
		return output.getBuffer();
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> type) {		
		Input input = new Input(data);
		
		T obj = (T)kryo.readObject(input, type);
		
		return obj;
	}

}
