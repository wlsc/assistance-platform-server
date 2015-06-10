package token;

public interface TokenSerializer {
	String sign(String payload);
}