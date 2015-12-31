package sensorhandling.tucan;

import sensorhandling.preprocessing.IEventPreprocessor;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.tucan.TucanCredentialSecurity;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.tucan.TucanCredentials;

public class TucanTokenEventPreprocessor implements IEventPreprocessor<TucanCredentials> {

	@Override
	public Class<TucanCredentials> eventClassResponsibleFor() {
		return TucanCredentials.class;
	}

	@Override
	public TucanCredentials preprocessEvent(TucanCredentials event) {
		return TucanCredentialSecurity.encrpytCredentials(event, TucanSecurityConfig.getTucanSecret());
	}

}