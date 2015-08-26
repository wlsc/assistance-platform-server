package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization;

import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Implements rudimentary binary object serialization via Kryo Library
 */
public class KryoMessageSerialization implements MessageSerialization {
	@Override
	public <T> byte[] serialize(T data) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Output output = new Output(outputStream);
		
		new Kryo().writeObject(output, data);
		
		byte[] result = output.getBuffer();
		
		output.close();
		
		return result;
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> type) {		
		Input input = new Input(data);
		
		T obj = new Kryo().readObject(input, type);
		
		input.close();
		
		return obj;
	}

}
