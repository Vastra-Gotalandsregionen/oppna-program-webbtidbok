/**
 * Copyright 2010 Västra Götalandsregionen
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
 *
 */

package se.vgregion.webbtidbok.gui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import org.junit.Before;
import org.junit.Test;

import se.vgregion.webbtidbok.domain.Surgery;

public class LocationHolderFilterTest {

    private LocationHolder holder;
    private Surgery s1,s2,s3,s4;
    private List<Surgery> surgeries;
    private static final String[] EMPTY_STRING_ARRAY = {};
    
    
    @Before
    public void setUp() throws Exception {
        holder = new LocationHolder();
        surgeries = new ArrayList<Surgery>();
        s1 = new Surgery();
        s2 = new Surgery();
        s3 = new Surgery();
        s4 = new Surgery();
        
        s1.setSurgeryName("Test 1");
        s2.setSurgeryName("Test 2");
        s3.setSurgeryName("Test 3");
        s4.setSurgeryName("Test 4");
        
        s1.setSurgeryId("TEST1");
        s2.setSurgeryId("TEST2");
        s3.setSurgeryId("TEST3");
        s4.setSurgeryId("TEST4"); 
        surgeries.add(s1);
        surgeries.add(s2);
        surgeries.add(s3);
        surgeries.add(s4);
        holder.setAvailableLocations(surgeries);
    }

    @Test
    public void testIncludeFilter() {
    	String[] includes = {"TEST1", "TEST2"};
    	holder.setIncludeClinics(includes);
    	assertTrue(surgeryIdEquality(includes, holder.getSelectItems()));
    	assertEquals(holder.getSelectItems().size(), includes.length);

    	includes = new String[]{"TEST7"};
    	holder.setIncludeClinics(includes);
    	assertTrue(surgeryIdEquality(EMPTY_STRING_ARRAY, holder.getSelectItems()));
    	assertEquals(holder.getSelectItems().size(), 0);
    }
    
    @Test
    public void testExcludeFilter() {
    	String[] excludes = {"TEST1", "TEST2"};
    	String[] expectedIds = {"TEST3", "TEST4"};
    	holder.setExcludeClinics(excludes);
    	assertTrue(surgeryIdEquality(expectedIds, holder.getSelectItems()));
    	assertEquals(holder.getSelectItems().size(), expectedIds.length);

    	excludes = new String[]{"TEST5"};
    	expectedIds = surgeryToSurgeryIdStrings(surgeries);
    	holder.setExcludeClinics(excludes);
    	assertTrue(surgeryIdEquality(expectedIds, holder.getSelectItems()));
    	assertEquals(holder.getSelectItems().size(), expectedIds.length);
    }
    
    @Test
    public void testExcludeIncludeFilter() {
    	String[] includes = {"TEST1", "TEST2"};
    	String[] excludes = {"TEST2"};
    	String[] expectedIds = {"TEST1"};
    	holder.setExcludeClinics(excludes);
    	holder.setIncludeClinics(includes);
    	assertTrue(surgeryIdEquality(expectedIds, holder.getSelectItems()));
    	assertEquals(holder.getSelectItems().size(), expectedIds.length);
    }
    
    @Test
    public void testNullAndEmptyFilters(){
    	holder.setExcludeClinics(null);
    	holder.setIncludeClinics(null);
    	
    	assertTrue(surgeryIdEquality(surgeryToSurgeryIdStrings(surgeries), holder.getSelectItems()));
    	
    	holder.setExcludeClinics(EMPTY_STRING_ARRAY);
    	holder.setIncludeClinics(EMPTY_STRING_ARRAY);
    	
    	assertTrue(surgeryIdEquality(surgeryToSurgeryIdStrings(surgeries), holder.getSelectItems()));
    	
    }
    
    private boolean surgeryIdEquality(String[] ids, List<SelectItem> surgeries){
    	List<String> idsList = Arrays.asList(ids);
    	List<String> sIds = new ArrayList<String>();
    	for(SelectItem s : surgeries){
    		sIds.add((String) s.getValue());
    	}
    	
    	return idsList.containsAll(sIds) && sIds.containsAll(idsList);
    }
    
    private String[] surgeryToSurgeryIdStrings(List<Surgery> surgeries){
    	List<String> sIds = new ArrayList<String>();
    	for(Surgery s : surgeries){
    		sIds.add((String) s.getSurgeryId());
    	}
    	return sIds.toArray(new String[0]);
    }
}
