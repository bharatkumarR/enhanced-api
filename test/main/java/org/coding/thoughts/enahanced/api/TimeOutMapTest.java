package org.coding.thoughts.enahanced.api;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TimeOutMapTest {
	
	@Test
	public void verifyMapIsTimedOut() throws InterruptedException {
		Map<String,String> map =  new TimeOutMap<>(new HashMap<>(),1);
		map.put("test1", "val");
		Thread.sleep(65*1000);
		Assert.assertNull(map.get("test1"));
	}
	
	@Test
	public void verifyMapIsTimedOutForPutAll() throws InterruptedException {
		Map<String,String> map =  new HashMap<>();
		map.put("test1", "val");
		map.put("test2", "val");
		map.put("test3", "val");
		Map<Object, Object> timeOutMap = new TimeOutMap<>(new HashMap<>(),1);
		timeOutMap.putAll(map);
		Thread.sleep(65*1000);
		Assert.assertTrue(timeOutMap.isEmpty());
	}

}
