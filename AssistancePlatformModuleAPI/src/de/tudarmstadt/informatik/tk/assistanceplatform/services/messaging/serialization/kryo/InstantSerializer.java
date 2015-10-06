package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization.kryo;

import java.time.Instant;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class InstantSerializer extends Serializer<Instant> {

	@Override
	public Instant read(Kryo arg0, Input arg1, Class<Instant> arg2) {
		return Instant.ofEpochMilli(arg1.readLong());
	}

	@Override
	public void write(Kryo arg0, Output arg1, Instant arg2) {
		arg1.writeLong(arg2.toEpochMilli());
	}

}
