/*
 * Copyright (c) 2016 Regents of the University of Minnesota.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.umn.biomedicus.uima.rtf;

import edu.umn.biomedicus.rtf.exc.RtfReaderException;
import edu.umn.biomedicus.rtf.reader.OutputDestination;
import edu.umn.biomedicus.rtf.reader.OutputDestinationFactory;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Type;
import org.apache.uima.jcas.JCas;

import java.util.List;
import java.util.Map;

/**
 * Creates CAS views for RTF data.
 *
 * @author Ben Knoll
 * @since 1.3.0
 */
class CasOutputDestinationFactory implements OutputDestinationFactory {
    /**
     * List of cas mappings.
     */
    private final List<DestinationCasMapping> destinationCasMappings;

    /**
     * List of annotation type for control word symbols.
     */
    private final Map<String, Type> annotationTypeForSymbolName;

    /**
     * The list of annotation property watchers.
     */
    private final List<PropertyCasMapping> propertyCasMappings;

    /**
     * The parent view to create new views in.
     */
    private final JCas jCas;

    /**
     * Constructs a new factory for {@link CasOutputDestination} objects.
     *
     * @param destinationCasMappings      the cas mappings to find the destination in.
     * @param annotationTypeForSymbolName the annotation type for control word symbols.
     * @param propertyCasMappings         the property watchers which create annotations for those properties.
     * @param jCas                        the parent view to create new views in.
     */
    CasOutputDestinationFactory(List<DestinationCasMapping> destinationCasMappings,
                                Map<String, Type> annotationTypeForSymbolName,
                                List<PropertyCasMapping> propertyCasMappings,
                                JCas jCas) {
        this.destinationCasMappings = destinationCasMappings;
        this.annotationTypeForSymbolName = annotationTypeForSymbolName;
        this.propertyCasMappings = propertyCasMappings;
        this.jCas = jCas;
    }

    @Override
    public OutputDestination create(String destinationName) throws RtfReaderException {
        DestinationCasMapping matchingCasMapping = null;
        for (DestinationCasMapping destinationCasMapping : destinationCasMappings) {
            if (destinationName.equals(destinationCasMapping.getDestinationName())) {
                matchingCasMapping = destinationCasMapping;
            }
        }
        if (matchingCasMapping == null) {
            return new IgnoreOutputDestination(destinationName);
        }

        JCas newView;
        try {
            newView = jCas.createView(matchingCasMapping.getViewName());
        } catch (CASException e) {
            throw new RtfReaderException(e);
        }
        return new CasOutputDestination(newView, propertyCasMappings, annotationTypeForSymbolName,
                destinationName);
    }
}
