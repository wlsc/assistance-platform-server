package token;

@FunctionalInterface
public interface TokenDeserializer {
    String deserialize(String token);
}
