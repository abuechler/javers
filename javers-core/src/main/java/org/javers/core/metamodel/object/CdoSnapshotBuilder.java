package org.javers.core.metamodel.object;

import org.javers.common.exception.exceptions.JaversException;
import org.javers.common.exception.exceptions.JaversExceptionCode;
import org.javers.common.validation.Validate;
import org.javers.core.Javers;
import org.javers.core.metamodel.property.Property;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bartosz walacik
 */
public class CdoSnapshotBuilder {
    private final GlobalCdoId globalCdoId;
    private final Map<Property, Object> state = new HashMap<>();

    private CdoSnapshotBuilder(GlobalCdoId globalCdoId) {
        this.globalCdoId = globalCdoId;
    }

    public static CdoSnapshotBuilder cdoSnapshot(GlobalCdoId globalCdoId){
        Validate.argumentIsNotNull(globalCdoId);
        return new CdoSnapshotBuilder(globalCdoId);
    }

    public CdoSnapshotBuilder withPropertyValue(Property property, Object value){
        Validate.argumentIsNotNull(property);
        if (value == null){
            return this;
        }

        if (state.containsKey(property)){
            throw new JaversException(JaversExceptionCode.SNAPSHOT_STATE_VIOLATION);
        }

        state.put(property, value);
        return this;
    }

    public CdoSnapshot build(){
        return new CdoSnapshot(globalCdoId, state);
    }
}