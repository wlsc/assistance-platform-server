package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization.kryo;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.UUID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging.serialization.MessageSerialization;

/**
 * Implements rudimentary binary object serialization via Kryo Library
 */
public class KryoMessageSerialization implements MessageSerialization {
  private final KryoPool pool;

  public KryoMessageSerialization() {
    KryoFactory factory = new KryoFactory() {
      public Kryo create() {
        Kryo kryo = new Kryo();
        kryo.addDefaultSerializer(UUID.class, UUIDSerializer.class);
        kryo.addDefaultSerializer(Instant.class, InstantSerializer.class);
        // configure kryo instance, customize settings
        return kryo;
      }
    };
    // Build pool with SoftReferences enabled (optional)
    pool = new KryoPool.Builder(factory).softReferences().build();
  }

  @Override
  public <T> byte[] serialize(T data) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Output output = new Output(outputStream);

    Kryo kryo = pool.borrow();

    kryo.writeObject(output, data);

    pool.release(kryo);

    byte[] result = output.getBuffer();

    output.close();

    return result;
  }

  @Override
  public <T> T deserialize(byte[] data, Class<T> type) {
    Input input = new Input(data);

    Kryo kryo = pool.borrow();

    T obj = kryo.readObject(input, type);

    pool.release(kryo);

    input.close();

    return obj;
  }

}
