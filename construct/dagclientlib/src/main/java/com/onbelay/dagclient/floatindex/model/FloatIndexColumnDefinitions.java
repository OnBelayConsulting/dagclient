/*
 Copyright 2019, OnBelay Consulting Ltd.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.  
 */
package com.onbelay.dagclient.floatindex.model;

import java.util.HashMap;
import java.util.Map;

import com.onbelay.core.query.enums.ColumnDataType;
import com.onbelay.core.query.model.ColumnDefinition;
import com.onbelay.core.query.model.ColumnDefinitions;
import org.springframework.stereotype.Component;


@Component(value = "floatIndexColumnDefinitions")
public class FloatIndexColumnDefinitions implements ColumnDefinitions {

	public Map<String, ColumnDefinition> definitionsMap = new HashMap<String, ColumnDefinition>();
	
	public static final ColumnDefinition id = new ColumnDefinition(
			"id",
			ColumnDataType.INTEGER,
			"id");

	public static final ColumnDefinition name = new ColumnDefinition(
			"name",
			ColumnDataType.STRING,
			"detail.name");

	public static final ColumnDefinition type = new ColumnDefinition(
			"type",
			ColumnDataType.STRING,
			"detail.type");

	public FloatIndexColumnDefinitions() {
		add(id);
		add(name);
		add(type);
	}

	@Override
	public String getCodeName() {
		return name.getPath();
	}

	@Override
	public String getDescriptionName() {
		return name.getPath();
	}

	public ColumnDefinition get(String name) {
		return definitionsMap.get(name);
	}
	
	private void add(ColumnDefinition definition) {
		definitionsMap.put(definition.getName(), definition);
	}
	
}
