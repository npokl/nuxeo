/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
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
 *
 * Contributors:
 *     Kevin Leturc
 */
package org.nuxeo.ecm.core.storage.marklogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;
import org.nuxeo.ecm.core.storage.State;

public class TestMarkLogicStateSerializer {

    private MarkLogicStateSerializer serializer = new MarkLogicStateSerializer();

    @Test
    public void testEmptyState() {
        String json = serializer.apply(new State());
        assertNotNull(json);
        assertEquals("{}", json);
    }

    @Test
    public void testStateWithNullValue() {
        State state = new State();
        state.put("ecm:id", "ID");
        state.put("subState", null);
        String json = serializer.apply(state);
        assertNotNull(json);
        assertEquals("{\"ecm:id\":\"ID\"}", json);
    }

    @Test
    public void testStateWithSimpleValue() {
        State state = new State();
        state.put("ecm:id", "ID");
        String json = serializer.apply(state);
        assertNotNull(json);
        assertEquals("{\"ecm:id\":\"ID\"}", json);
    }

    @Test
    public void testStateWithSimpleCalendarValue() {
        State state = new State();
        state.put("ecm:id", "ID");
        Calendar creationDate = Calendar.getInstance();
        creationDate.setTimeInMillis(1);
        state.put("dub:creationDate", creationDate);
        String json = serializer.apply(state);
        assertNotNull(json);
        String date = LocalDateTime.ofInstant(Instant.ofEpochMilli(1), ZoneId.systemDefault()).toString();
        assertEquals(String.format("{\"ecm:id\":\"ID\",\"dub:creationDate\":\"%s\"}", date), json);
    }

    @Test
    public void testStateWithSubState() {
        State state = new State();
        state.put("ecm:id", "ID");
        State subState = new State();
        subState.put("nbValues", 2);
        subState.put("valuesPresent", false);
        state.put("subState", subState);
        String json = serializer.apply(state);
        assertNotNull(json);
        assertEquals("{\"ecm:id\":\"ID\",\"subState\":{\"nbValues\":2,\"valuesPresent\":false}}", json);
    }

    @Test
    public void testStateWithList() {
        State state = new State();
        state.put("ecm:id", "ID");
        state.put("nbValues", 2);
        State state1 = new State();
        state1.put("item", "itemState1");
        state1.put("read", true);
        state1.put("write", true);
        State state2 = new State();
        state2.put("item", "itemState2");
        state2.put("read", true);
        state2.put("write", false);
        state.put("values", new ArrayList<>(Arrays.asList(state1, state2)));
        String json = serializer.apply(state);
        assertNotNull(json);
        assertEquals(
                "{\"ecm:id\":\"ID\",\"nbValues\":2,\"values\":[{\"item\":\"itemState1\",\"read\":true,\"write\":true},{\"item\":\"itemState2\",\"read\":true,\"write\":false}]}",
                json);
    }

    @Test
    public void testStateWithArray() {
        State state = new State();
        state.put("ecm:id", "ID");
        state.put("nbValues", 2);
        state.put("values", new Integer[] { 3, 4 });
        String json = serializer.apply(state);
        assertNotNull(json);
        assertEquals("{\"ecm:id\":\"ID\",\"nbValues\":2,\"values\":[3,4]}", json);
    }

    @Test
    public void testBijunction() {
        State state = new State();
        state.put("ecm:id", "ID");
        state.put("dub:creationDate", Calendar.getInstance());
        State subState = new State();
        subState.put("nbValues", 2);
        State state1 = new State();
        state1.put("item", "itemState1");
        state1.put("read", true);
        state1.put("write", true);
        State state2 = new State();
        state2.put("item", "itemState2");
        state2.put("read", true);
        state2.put("write", false);
        subState.put("values", new ArrayList<>(Arrays.asList(state1, state2)));
        subState.put("valuesAsArray", new Integer[] { 3, 4 });
        state.put("subState", subState);
        assertEquals(state, serializer.andThen(new MarkLogicStateDeserializer()).apply(state));
    }

}