package de.tudarmstadt.informatik.tk.assistanceplatform.services.internal.http.actions;

import java.util.function.Consumer;

public interface IGetUserActivationsForModule {
	void getUserActivationsForModule(String moduleId, Consumer<long[]> activatedUserIdsCallback);
}
