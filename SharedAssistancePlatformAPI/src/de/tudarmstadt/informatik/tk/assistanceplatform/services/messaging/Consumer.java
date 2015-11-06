package de.tudarmstadt.informatik.tk.assistanceplatform.services.messaging;


@FunctionalInterface
public interface Consumer<T> {
	void consumeDataOfChannel(Channel<T> channel, T data);
}