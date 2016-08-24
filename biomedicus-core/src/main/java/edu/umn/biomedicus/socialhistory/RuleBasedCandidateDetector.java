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

package edu.umn.biomedicus.socialhistory;

import com.google.inject.Inject;
import edu.umn.biomedicus.application.DocumentProcessor;
import edu.umn.biomedicus.common.labels.Label;
import edu.umn.biomedicus.common.labels.Labels;
import edu.umn.biomedicus.common.types.text.*;
import edu.umn.biomedicus.exc.BiomedicusException;

public class RuleBasedCandidateDetector implements DocumentProcessor {
    private final Document document;
    private final Labels<SectionTitle> sectionTitleLabels;
    private final Labels<TermToken> termTokenLabels;

    @Inject
    public RuleBasedCandidateDetector(Document document) {
        this.document = document;
        sectionTitleLabels = document.labels(SectionTitle.class);
        termTokenLabels = document.labels(TermToken.class);
    }

    @Override
    public void process() throws BiomedicusException {
        for (Label<Section> sectionLabel : document.labels(Section.class)) {
            Section section = sectionLabel.value();
            Label<SectionTitle> sectionTitleLabel = document.labels(SectionTitle.class).insideSpan(sectionLabel)
                    .firstOptionally()
                    .orElseThrow(() -> new BiomedicusException("Section did not have a title"));

            Labels<TermToken> sectionTitleTermTokenLabels = termTokenLabels.insideSpan(sectionTitleLabel);

        }
    }
}
