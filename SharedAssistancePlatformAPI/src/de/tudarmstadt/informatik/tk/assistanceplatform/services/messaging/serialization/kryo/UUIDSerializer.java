package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization.kryo;

import java.util.UUID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Used to serialize UUIDs for Kryo
 * 
 * @author bjeutter
 *
 */
public class UUIDSerializer extends Serializer<UUID> {

  @Override
  public UUID read(Kryo arg0, Input arg1, Class<UUID> arg2) {
    long least = arg1.readLong();
    long most = arg1.readLong();

    return new UUID(most, least);
  }

  @Override
  public void write(Kryo arg0, Output arg1, UUID arg2) {
    long least = arg2.getLeastSignificantBits();
    long most = arg2.getMostSignificantBits();

    arg1.writeLong(least);
    arg1.writeLong(most);
  }

}
