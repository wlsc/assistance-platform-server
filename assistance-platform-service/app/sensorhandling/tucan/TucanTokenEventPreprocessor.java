package sensorhandling.tucan;

import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.tucan.TucanCredentialSecurity;
import de.tudarmstadt.informatik.tk.assistanceplatform.data.sensor.social.tucan.TucanCredentials;
import sensorhandling.preprocessing.IEventPreprocessor;

public class TucanTokenEventPreprocessor implements IEventPreprocessor<TucanCredentials> {

    @Override
    public Class<TucanCredentials> eventClassResponsibleFor() {
        return TucanCredentials.class;
    }

    @Override
    public TucanCredentials preprocessEvent(TucanCredentials event) throws Exception {
        return TucanCredentialSecurity.encrpytCredentials(event, TucanSecurityConfig.getTucanSecret());
    }

}