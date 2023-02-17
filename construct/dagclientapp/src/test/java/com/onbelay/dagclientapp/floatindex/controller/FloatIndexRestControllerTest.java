package com.onbelay.dagclientapp.floatindex.controller;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagclient.floatindex.model.FloatIndexFixture;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.controller.DagControllerTestCase;
import com.onbelay.dagclientapp.floatindex.snapshot.FloatIndexCollection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class FloatIndexRestControllerTest extends DagControllerTestCase {

	@Autowired
	private FloatIndexRestController floatIndexRestController;
	
	
	public void setUp() {
		
		super.setUp();

		FloatIndexFixture.createSavedFloatIndex("specialIndex");

		flush();
	}
	
	@Test
	public void testFetchFloatIndices() throws Exception {
		
		MockMvc mockMvc = generateMockMvcGet(floatIndexRestController, "/api/floatIndices");
		
		ResultActions result = mockMvc.perform(get("/api/floatIndices"));
		MvcResult mvcResult = result.andReturn();
		String jsonString = mvcResult.getResponse().getContentAsString();
		String contentType = mvcResult.getResponse().getHeader("Content-type");
		
		assertEquals("application/json; charset=utf-8", contentType);
		
		FloatIndexCollection collection = super.objectMapper.readValue(jsonString, FloatIndexCollection.class);
		
		assertEquals(1, collection.getSnapshots().size());
		
		List<FloatIndexSnapshot> snapshots = collection.getSnapshots();

		FloatIndexSnapshot snapshot = snapshots.get(0);
	}
	
	@Test
	public void saveIndex() throws Exception {
		
		MockMvc mockMvc = generateMockMvcPost(floatIndexRestController, "/api/floatIndices");

		FloatIndexSnapshot snapshot = new FloatIndexSnapshot();

		snapshot.getDetail().setName("AlphaIndex");
		snapshot.getDetail().setType("Hub");

		String jsonString = objectMapper.writeValueAsString(snapshot);

		ResultActions result = mockMvc.perform(post("/api/floatIndices").content(jsonString));
		MvcResult mvcResult = result.andReturn();
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		String contentType = mvcResult.getResponse().getHeader("Content-type");

		TransactionResult restResult = objectMapper.readValue(jsonStringResponse, TransactionResult.class);
		assertTrue(restResult.isSuccessful());
	}
	
	@Test
	public void saveFloatIndices() throws Exception {
		
		MockMvc mockMvc = generateMockMvcPut(floatIndexRestController, "/api/floatIndices");


		FloatIndexSnapshot snapshot = new FloatIndexSnapshot();

		snapshot.getDetail().setName("AlphaIndex");
		snapshot.getDetail().setType("Hub");

		List<FloatIndexSnapshot> snapshots = new ArrayList<>();
		snapshots.add(snapshot);
		
		String jsonString = objectMapper.writeValueAsString(snapshots);

		ResultActions result = mockMvc.perform(put("/api/floatIndices").content(jsonString));
		MvcResult mvcResult = result.andReturn();
		String jsonStringResponse = mvcResult.getResponse().getContentAsString();
		String contentType = mvcResult.getResponse().getHeader("Content-type");

		TransactionResult restResult = objectMapper.readValue(jsonStringResponse, TransactionResult.class);
		assertTrue(restResult.isSuccessful());
	}
}
