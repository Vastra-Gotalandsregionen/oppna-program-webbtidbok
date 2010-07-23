/**
 * Copyright 2009 Vastra Gotalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 */
package se.vgregion.webbtidbok.flows;

import org.springframework.binding.mapping.Mapper;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;

public class FlowTestHelper {

    /**
     * This methods return a subflow which does not validate input parameters.
     * @param flowName The name of the flow to be mocked.
     * @param endState The target end state of the mocked flow.
     * @return The mock flow object.
     */
    public static Flow createMockSubflow(String flowName, String endState) {
        return createMockSubflow(flowName, endState, new Mapper() {
            public MappingResults map(Object source, Object target) {
                return null;
            }
        });
    }

    /**
     * This method returns a subflow with a specific mapper.
     * The mapper can be used to validate properties on the subflow input.
     * The subflow will immediately end once entered.
     * @param flowName The name of the flow to be mocked.
     * @param endState The target end state of the mocked flow.
     * @param mapper A mapper used to validate input properties.
     * @return The mock flow object.
     */
    public static Flow createMockSubflow(String flowName, String endState, Mapper mapper) {
        Flow mockBookingFlow = new Flow("display");
        mockBookingFlow.setInputMapper(mapper);
        new EndState(mockBookingFlow, "logout");
        return mockBookingFlow;
    }

}
