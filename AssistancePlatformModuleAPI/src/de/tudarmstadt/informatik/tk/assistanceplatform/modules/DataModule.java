package de.tudarmstadt.informatik.tk.assistanceplatform.modules;

import de.tudarmstadt.informatik.tk.assistanceplatform.services.data.spark.ISparkService;

public abstract class DataModule extends Module {
	private ISparkService sparkService;
	
	public DataModule() {
		super();
	}
	
	public void setSparkService(ISparkService service) {
		this.sparkService = service;
	}
	
	public ISparkService getSparkService() {
		return sparkService;
	}
}