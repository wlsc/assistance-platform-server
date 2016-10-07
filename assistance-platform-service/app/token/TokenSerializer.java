package token;

@FunctionalInterface
public interface TokenSerializer {
    String sign(String payload);
}